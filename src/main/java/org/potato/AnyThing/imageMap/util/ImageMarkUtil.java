package org.potato.AnyThing.imageMap.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**给图片添加文字水印和图片水印
 * Created by potato on 2018/4/19.
 */
public class ImageMarkUtil {

    public static void main(String[] args) {
        Font font = new Font("Sans Serif", Font.PLAIN, 48);
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        //new ImageMarkUtil().mark("E:\\足迹册\\testPic\\地图钉子-背景.png", "E:\\足迹册\\testPic\\地图钉子-背景2.png","大理三塔", font, ColorTranfer.toColorFromString("323333"), 79, 79);
        // 增加图片水印
        //new ImageMarkUtil().mark("E:\\足迹册\\testPic\\地图钉子-背景2.png", "E:\\足迹册\\testPic\\timg.jpg", "E:\\足迹册\\testPic\\地图钉子-背景3.png", 297, 150, 26, 153);
        //new ImageMarkUtil().renderFootNailMark("大理三塔","14:33","E:\\足迹册\\testPic\\timg.jpg");
         new ImageMarkUtil().renderFootNailMark("大理三塔","14:33","E:\\足迹册\\testPic\\timg.jpg");

    }

    public static int  getMarkWidth()
    {
        return 358;
    }
    public static  int getMarkHeight()
    {
        return 388;
    }

    // 加文字水印
    public void mark(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y) {
        Graphics2D g = bufImg.createGraphics();




        BufferedImage to = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = to.createGraphics();
        to = g2d.getDeviceConfiguration().createCompatibleImage(bufImg.getWidth(), bufImg.getHeight(),
                Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = to.createGraphics();
        g2d.drawImage(bufImg,0,0,bufImg.getWidth(), bufImg.getHeight(), null);


        //g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    // 加图片水印
    public void mark(BufferedImage bufImg, Image img, Image markImg, int width, int height, int x, int y) {
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.drawImage(markImg, x, y, width, height, null);
        g.dispose();
    }
    /**
     * 给图片增加文字水印
     *
     * @param imgPath -要添加水印的图片路径
     * @param outImgPath -输出路径
     * @param text-文字
     * @param font-字体
     * @param color-颜色
     * @param x -文字位于当前图片的横坐标
     * @param y -文字位于当前图片的竖坐标
     */
    public void mark(String imgPath, String outImgPath, String text, Font font, Color color, int x, int y) {
        try {
            // 读取原图片信息
            File imgFile = null;
            Image img = null;
            if (imgPath != null) {
                imgFile = new File(imgPath);
            }
            if (imgFile != null && imgFile.exists() && imgFile.isFile() && imgFile.canRead()) {
                img = ImageIO.read(imgFile);
            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            mark(bufImg, img, text, font, color, x, y);
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "png", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片增加图片水印
     *
     * @param inputImg
     *            -源图片，要添加水印的图片
     * @param markImg
     *            - 水印图片
     * @param outputImg
     *            -输出图片(可以是源图片)
     * @param width
     *            - 水印图片宽度
     * @param height
     *            -水印图片高度
     * @param x
     *            -横坐标，相对于源图片
     * @param y
     *            -纵坐标，同上
     */
    public void mark(String inputImg, String markImg, String outputImg, int width, int height, int x, int y) {
        // 读取原图片信息
        File inputImgFile = null;
        File markImgFile = null;
        Image img = null;
        Image mark = null;
        try {
            if (inputImg != null && markImg != null) {
                inputImgFile = new File(inputImg);
                markImgFile = new File(markImg);
            }
            if (inputImgFile != null && inputImgFile.exists() && inputImgFile.isFile() && inputImgFile.canRead()) {
                img = ImageIO.read(inputImgFile);
            }
            if (markImgFile != null && markImgFile.exists() && markImgFile.isFile() && markImgFile.canRead()) {
                mark = ImageIO.read(markImgFile);
            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            mark(bufImg, img, mark, width, height, x, y);
            FileOutputStream outImgStream = new FileOutputStream(outputImg);
            ImageIO.write(bufImg, "png", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成单个标注
     * @param title 标注标题
     * @param time 标注时间
     * @param picPath 标注中缩略图的路径
     * @return
     */
    public static BufferedImage renderFootNailMark(String title,String time,String picPath){

        Font titleFont = new Font("微软雅黑", Font.PLAIN, 48);
        Font timeFont = new Font("微软雅黑",  Font.PLAIN, 45);
        FontMetrics fmTitle = sun.font.FontDesignMetrics.getMetrics(titleFont);
        Color titleColor = ColorTranfer.toColorFromString("323333");
        Color timeColor = ColorTranfer.toColorFromString("999999");
        // 读取原图片信息
        URL urlImgBack = ImageMarkUtil.class.getResource("/img/nail.png");
        Image imgBack = null;
        Image imgPic = null;
        File imgPicFile = null;
        try {
            //加载背景图片
            if (urlImgBack != null) {
                imgBack = ImageIO.read(urlImgBack);
                if(imgBack==null){
                    throw new IllegalArgumentException("标签背景图片加载失败！");
                }
            }
            //加载缩略图
            if (picPath != null ) {
                imgPicFile = new File(picPath);
            }
            if (imgPicFile != null && imgPicFile.exists() && imgPicFile.isFile() && imgPicFile.canRead()) {
                imgPic = ImageIO.read(imgPicFile);
                if(imgPic==null){
                    throw new IllegalArgumentException("缩略图加载失败！");
                }
            }

            /**加title和time文字*/
            int imgWidth = imgBack.getWidth(null);
            int imgHeight = imgBack.getHeight(null);

            BufferedImage bufimg = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_INT_RGB);
            Graphics2D g=bufimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            bufimg=g.getDeviceConfiguration().createCompatibleImage(imgWidth, imgHeight,Transparency.TRANSLUCENT);
            g.dispose();
            g=bufimg.createGraphics();
            g.drawImage(imgBack,0,0,null);

            //绘制title
            g.setColor(titleColor);
            g.setFont(titleFont);
            String newTitle = ReBuildTitle(title);
            g.drawString(newTitle,(358-fmTitle.stringWidth(newTitle))/2,79);

            //绘制time
            g.setColor(timeColor);
            g.setFont(timeFont);
            g.drawString(time,127,135);
            //绘制缩略图
            g.drawImage(imgPic,26, 153,297, 150, null);

            g.dispose();
            FileOutputStream outImgStream = new FileOutputStream("E:\\足迹册\\testPic\\aaa.png");
            ImageIO.write(bufimg, "png",outImgStream );
            return bufimg;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重构title。title长度超过5，则取前5个字符，在最后加..
     * @param oriTitle
     * @return
     */
    private  static  String ReBuildTitle(String oriTitle){
        int len = oriTitle.length();
        if(len<=5){
            return oriTitle;
        }else {
            return oriTitle.substring(0,5)+"..";
        }
    }
}
