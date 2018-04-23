package org.potato.AnyThing.imageMap.util;

import org.potato.AnyThing.phoenix.config.properties.ImageMapProperties;


public class MapTile {
	int x, y, z;

	private ImageMapProperties properties;

	/**
	 *
	 * @param x
	 * @param y
	 * @param zoom
	 */
	MapTile(int x, int y, int zoom,ImageMapProperties imProp) {
        this.x = x;
        this.y = y;
        z = zoom;
		properties = imProp;
    }




	MapTile(double lat, double lon, int zoom,ImageMapProperties imProp) {
        if (lat > 90 || lat < -90 || lon > 180 || lon < -180)
            throw new IllegalArgumentException();
        x = (int)(Math.pow(2, zoom)*(lon+180)/360/2);
        y = (int)(-(.5*Math.log((1+Math.sin(Math.toRadians(lat)))/(1-Math.sin(Math.toRadians(lat))))/Math.PI-1)*
                Math.pow(2, zoom-1)/2);
        z = zoom;
		properties = imProp;
    }

	public double getStartLon()
	{
		double n = Math.pow(2, this.z);
		double lng = x / n * 360.0*2 - 180.0;
		return lng;
	}



	public double getEndLon()
	{
		double n = Math.pow(2, this.z);
		double lng = (x+1) / n * 360.0*2 - 180.0;
		return lng;
	}
	
	public double getStartLat(){
		double n = Math.pow(2, this.z-1);
		 double lat = Math.atan(Math.sinh(Math.PI * (1 - 2 * y / n)));
	        lat = lat * 180.0 / Math.PI;
	    return lat;
	}
	
	public double getEndLat(){
		double n = Math.pow(2, this.z-1);
		 double lat = Math.atan(Math.sinh(Math.PI * (1 - 2 * (y+1) / n)));
	        lat = lat * 180.0 / Math.PI;
	    return lat;
	}

    public String tileURL() {
        /**
         * @return full link to tile image from google maps
         */
        String url =null;
		Integer lan = properties.getLang();
		Integer style = properties.getStyle();

        if(properties.getSource().equals("google")){
        	/* 谷歌地图
    	 * lyrs值的意义：
    	m：路线图
    	t：地形图
    	p：带标签的地形图
    	s：卫星图
    	y：带标签的卫星图
    	h：标签层（路名、地名等）
    	*/
			//有标注的

			String lyrs = "m";
			String sLan = "zh-CN";
			if(style.equals(1)){
				lyrs ="s";
			}
			if(lan.equals(1)){
				sLan = "en";
			}

			url = "http://mt3.google.cn/vt/lyrs="+lyrs+"&hl="+sLan+"&gl=CN&src=app"
					+ "&x="+x+"&y="+y+"&z="+(z-1)+"&s=Ga";
		}else {
        	//高德地图
			//style设置影像和路网，style=6为影像图，style=7为矢量路网，style=8为影像路网
			String sStyle ="7";
			if(style.equals(1)){
				sStyle="6";
			}
			String sLan = "zh_cn";
			if(lan.equals(1)){
				sLan = "en";
			}
			url = "http://wprd01.is.autonavi.com/appmaptile?&x="+x+"&y="+y+"&z="+(z-1)+"&lang="+sLan+"&size=1&scl=1&style="+sStyle;
		}
		return  url;

    }

}
