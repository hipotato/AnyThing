package org.potato.AnyThing.imageMap.util;

import org.potato.AnyThing.imageMap.bo.Point;

public class MapTile {
	int x, y, z;

	MapTile(int x, int y, int zoom) {
        this.x = x;
        this.y = y;
        z = zoom;
    }

	MapTile(double lat, double lon, int zoom) {
        if (lat > 90 || lat < -90 || lon > 180 || lon < -180)
            throw new IllegalArgumentException();
        x = (int)(Math.pow(2, zoom)*(lon+180)/360/2);
        y = (int)(-(.5*Math.log((1+Math.sin(Math.toRadians(lat)))/(1-Math.sin(Math.toRadians(lat))))/Math.PI-1)*
                Math.pow(2, zoom-1)/2);
        z = zoom;
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
//        return "http://khms" + (int)(Math.random()*4) +
//                ".google.com/kh/v=149&src=app&x=" + x + "&y=" + y + "&z=" + (z-1) + "&s=";
    	/*
    	 * lyrs值的意义：
    	m：路线图  
    	t：地形图  
    	p：带标签的地形图  
    	s：卫星图  
    	y：带标签的卫星图  
    	h：标签层（路名、地名等）  
    	*/
    	//有标注的
    	String url = "http://mt3.google.cn/vt/lyrs=m@258000000&hl=zh-CN&gl=CN&src=app"
    			+ "&x="+x+"&y="+y+"&z="+(z-1)+"&s=Ga";
    	//无标注的
    	String url2 = "http://mt2.google.cn/vt/lyrs=s@258000000&hl=zh-CN&gl=CN&src=app"
    			+ "&x="+x+"&y="+y+"&z="+(z-1)+"&s=Ga";
    	
    	//d
    	String url3 = "http://khms" + (int)(Math.random()*4) +
                ".google.com/kh/v=149&src=app&x=" + x + "&y=" + y + "&z=" + (z-1) + "&s=";

    	return url;
    }

}
