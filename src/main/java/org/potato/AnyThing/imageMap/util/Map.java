package org.potato.AnyThing.imageMap.util;


import org.potato.AnyThing.imageMap.bo.*;
import org.potato.AnyThing.imageMap.bo.Point;
import org.potato.AnyThing.phoenix.dto.req.ImageMapReq;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 地图控制类
 */
public class Map {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Map.class);
    //一个瓦片的像素尺寸，市面上的互联网地图瓦片尺寸目前均为256*256
    private static final int pixelNumOfOneTile = 256;
    //图片边缘需要留下border
    private static final int borderPixel = 300;
	MapTile start, end;
    private int height, width;
    private int imgHeight,imgWidth;
    private double startLat,startLon,endLat,endLon;
    //ArrayList<String> images;
    java.util.Map images;
    ArrayList<BufferedImage> imagesList;
    int zoom;
    public Map(MapTile start, MapTile end, String dest) {
        this.start = start;
        this.end = end;
        if (start.x > end.x || start.y > end.y)
            throw new IllegalArgumentException();
        height = end.y - start.y + 1;
        width = end.x - start.x + 1;
        
    }
    //谷歌zoom自适应：根据输入的经纬度范围、图片尺寸，自动确定高德瓦片层级
    private int GetAutoZoom(){
    	MapTile mStart, mEnd;
    	int temZoom = 22;
    	for(int i = temZoom;i>4;--i){
    	    //获取经度纬度跨度
    		mStart = new MapTile(startLat, startLon, i);
    		mEnd = new MapTile(endLat, endLon, i);
    		int height = mEnd.y - mStart.y + 1;
    	    int width = Math.abs(mEnd.x - mStart.x) + 1;
    	    if(height==1||width==1){
    	        continue;
            }
            if((height*pixelNumOfOneTile<imgHeight)&&(width*pixelNumOfOneTile<imgWidth)){
    	        return i;
            }
    	}
    	return 4;
    }

    //自适应
    private void Adjust(){
        this.zoom = GetAutoZoom();
        double centerLng = (this.startLon+this.endLon)/2.0;
        double centerLat = (this.startLat+this.endLat)/2.0;
        MapTile centerTile= new MapTile(centerLat,centerLng,this.zoom);
        this.start = new MapTile(centerTile.x-((imgWidth/pixelNumOfOneTile)/2)-1,centerTile.y-((imgHeight/pixelNumOfOneTile)/2)-1,this.zoom);
        this.end = new MapTile(centerTile.x+((imgWidth/pixelNumOfOneTile)/2)+1,centerTile.y+((imgHeight/pixelNumOfOneTile)/2)+1,this.zoom);
    }
    
    public Map(double startLat, double startLon, double endLat,
               double endLon, int imgHeight,int imgWidth) throws IllegalArgumentException {
        if (startLat < endLat || startLon > endLon)
            throw new IllegalArgumentException();
        this.startLat = startLat;
        this.endLon = endLon;
        this.startLon = startLon;
        this.endLat = endLat;
        this.imgHeight = imgHeight;
        this.imgWidth = imgWidth;

        Adjust();

        height = end.y - start.y + 1;
        width = end.x - start.x + 1;
    }
       
    public Map(ImageMapReq req, String dest)
    {
    	this(req.getTop(),req.getLeft(),req.getBottom(),req.getRight(),req.getHeight(),req.getWidth());
    }


    public byte[] mergeImagesToBuffer(List<MarkInfo> marks) throws IOException{
    	 if (imagesList == null)
             return null;
         BufferedImage result = new BufferedImage(width*256, height*256, BufferedImage.TYPE_INT_RGB);
         Graphics2D g = result.createGraphics();
         int x = 0;
         int y = 0;
         
         System.out.println("Merging tiles into one image....");
         System.out.println("TIME:" +System.currentTimeMillis());
        // logger.debug("【获取影像图服务】:Merging tiles into one image....");
         
         for (BufferedImage image : imagesList) {
             //File current = new File(destinationFolder + tempFolder + image);
             //BufferedImage bi = ImageIO.read(current);
             g.drawImage(image, x, y, null);
             y += 256;
             if (y >= result.getHeight()){
                 y = 0;
                 x += image.getWidth();
             }
             //current.delete();
         }


//        marks.add( new MarkInfo(new Point(39.908675,116.397426),"天安门","9:52","E:\\足迹册\\testPic\\tiananmen.jpg"));
//        marks.add( new MarkInfo(new Point(39.99310,116.396919)," 鸟 巢 ","11:25","E:\\足迹册\\testPic\\niaochao.jpg"));
//        marks.add( new MarkInfo(new Point(40.000101,116.274944),"颐和园","14:02","E:\\足迹册\\testPic\\yiheyuan.jpg"));

        if(!(marks==null||marks.isEmpty())){
            DrawMarks(g,marks);
        }

         //g.drawImage(mark,500,500,null);
         BufferedImage cutImg = CutImage(result);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ImageIO.write( cutImg, "jpg", baos );
         baos.flush();
         byte[] imageInByte = baos.toByteArray();
         baos.close();
         System.out.println("Done. Merged image");
         logger.debug("【获取影像图服务】:Merge image finshed");
         System.out.println("TIME:" +System.currentTimeMillis());
         return imageInByte;
    }
    Integer getMarkOffsetX(MarkInfo mark){
        if(mark.getOffsetX()==null){
            MapTile tile = new MapTile(mark.getCoord().getLat(),mark.getCoord().getLng(),this.zoom);
            int xOffset =(int)(pixelNumOfOneTile*(mark.getCoord().getLng()-tile.getStartLon())/(tile.getEndLon()-tile.getStartLon()));
            xOffset+=pixelNumOfOneTile*(tile.x-this.start.x);
            mark.setOffsetX(xOffset);
        }
        return mark.getOffsetX();
    }
    Integer getMarkOffsetY(MarkInfo mark){
        if(mark.getOffsetY()==null){
            MapTile tile = new MapTile(mark.getCoord().getLat(),mark.getCoord().getLng(),this.zoom);
            int yOffset =(int)(pixelNumOfOneTile*(mark.getCoord().getLat()-tile.getStartLat())/(tile.getEndLat()-tile.getStartLat()));
            yOffset+=pixelNumOfOneTile*(tile.y-this.start.y);
            mark.setOffsetY(yOffset);
        }
        return  mark.getOffsetY();
    }

    //绘制mark
    void DrawMarks(Graphics2D graph,List<MarkInfo> marks){
        Color color = ColorTranfer.toColorFromString("000000");
        //先渲染连接线
        for( int i = 0 ; i < marks.size()-1 ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            MarkInfo mark = marks.get(i);
            MarkInfo markNext = marks.get(i+1);
            Stroke   st   =   graph.getStroke();
            Stroke   bs;
            //LINE_TYPE_DASHED
            bs = new BasicStroke(5,   BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,   0,
                    new   float[]{25,   25},   0);
            graph.setStroke(bs);
            graph.setColor(color);
            graph.drawLine(getMarkOffsetX(mark),   getMarkOffsetY(mark), getMarkOffsetX(markNext),   getMarkOffsetY(markNext));
        }

        //再渲染mark
        for(MarkInfo mark:marks){

            int xOffset = mark.getOffsetX();
            int yOffset = mark.getOffsetY();

            BufferedImage bf = ImageMarkUtil.renderFootNailMark(mark.getTitle(),mark.getTime(),mark.getPicUrl());
            int x = xOffset -ImageMarkUtil.getMarkWidth()/2;
            int y = yOffset -ImageMarkUtil.getMarkHeight()+10;
            x= (x>0)? x:0;
            y = (y>0)? y:0;
            graph.drawImage(bf,x,y,null);
        }
    }

    private BufferedImage CutImage(BufferedImage oldImg)
    {
        int startX = Math.abs(oldImg.getWidth()-imgWidth)/2;
        int startY =Math.abs(oldImg.getHeight()-imgHeight)/2;
    	BufferedImage outImage = oldImg.getSubimage(startX,startY,imgWidth,imgHeight);
    	return outImage;
    }
    
    public long getNumTiles() {
        return Math.abs( (end.y - start.y + 1) * (end.x - start.x + 1) );
    }

    public int getWidth() {
        return width*256;
    }

    public int getHeight() {
        return height*256;
    }
    public int getMaxZoom(double lat, double lon, int zoom){
    	MapTile tile =null;
    	int temZoom = zoom;
    	while(temZoom>5){
    		tile= new MapTile(lat,lon,temZoom);
    		if(isNetFileAvailable(tile.tileURL())){
    			System.out.println("getMaxZoom Lon:"+lon+"Lat:"+lat+ "处的最大zoom："+temZoom);
    			return temZoom;
    		}
    		temZoom--;
    	}
    	return -1;
    }
    //判断url是否存在资源
    public boolean isNetFileAvailable(String strUrl) {
		InputStream netFileInputStream = null;
		URL url;
		HttpURLConnection urlConn;
		while(true){
			try {
				url = new URL(strUrl);
				urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setReadTimeout(2000);
				String code = new Integer(urlConn.getResponseCode()).toString();
				System.out.println("ResponseCode:"+code);
				if(code.equals("403")){
					System.out.println("isNetFileAvailable-Url Error 403:"+strUrl);
					Proxy.setProxy();
					continue;
				}
				if(code.equals("404")){
					System.out.println("isNetFileAvailable-Url Error 404:"+strUrl);
					return false;
				}
				netFileInputStream = urlConn.getInputStream();
		
				if (null != netFileInputStream) {
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				return false;
			} finally {
				try {
					if (netFileInputStream != null)
						netFileInputStream.close();
				} catch (IOException e) {
				}
			}
			
		}
	}
    
    //异步下载图片
    public int bulkDownload2(boolean proxyEnabled)
            throws IOException, InterruptedException {
        /**
         * @param proxyEnabled - boolean, uses proxy to download maps if set to true
         * downloads images of tiles in a given area from west to east from north to south
         * uses proxy to bypass banning of IP
         */
//    	if (proxyEnabled)
//            Proxy.setProxy();
        int latCounter = 0;
        int lonCounter = 0;

        imagesList = new ArrayList<>();
        MapTile current;

        long total = this.getNumTiles();
        int c = 1;

        ArrayList<Future<DownLoadInfo>> futurs = new ArrayList<>();
          
        System.out.println("Downloading images...");
        System.out.println("TIME:" +System.currentTimeMillis());
       
        //logger.debug("【获取影像图服务】:Downloading images...");
        //两层循环，开启所有的future，让多个瓦片下载线程同时开启
        for (int i = start.x; i <= end.x; i++) {
            for (int j = start.y; j <= end.y; j++) {
                current = new MapTile(i, j, start.z);
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<DownLoadInfo> future = executor.submit(new TimeoutURL2(current.tileURL()));
                System.out.println("Downloading image " + c + " out of " + total);
                logger.debug("【获取影像图服务】:Download image {} out of {} started",c,total);
                System.out.println("TIME:" +System.currentTimeMillis());
                futurs.add(future);
                c++;
            }
        }
        //循环获取多线程下载的结果    
        for(int i=0;i<futurs.size();++i){
        	DownLoadInfo info;
			try {
				info = futurs.get(i).get(10, TimeUnit.SECONDS);
			} catch (ExecutionException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Download image " + (i+1) + " timeout! ");
	            logger.debug("【获取影像图服务】:Download image {} timeout",(i+1));
	            System.out.println("Downloading images failed by timeout ");
	            logger.debug("【获取影像图服务】:Downloading images failed by timeout");
				return -2;
			}
			if(info.getResultCode()!=200){
				if(info.getResultCode()==404){
					return -1;
				}
			}
        	BufferedImage image = ImageIO.read(info.getInputStream());
        	System.out.println("Download image " + (i+1) + " fineshed ");
            logger.debug("【获取影像图服务】:Download image {} out of {} fineshed",(i+1),total);
        	imagesList.add(image);
        }
        System.out.println("Downloading images all finshed");
        logger.debug("【获取影像图服务】:Downloading images all finshed");
        return 0;
    }
  
}
