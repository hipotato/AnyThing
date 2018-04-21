package org.potato.AnyThing.imageMap.bo;

/**
 * Created by potato on 2018/3/7.
 */
public class TileInfo {

    /**图片标注物最小外包矩形的四至 */
    private double minLng;   		//最小经度
    private double minlat;   		//最小纬度
    private double maxLng;  		//最大经度
    private double maxLat;   		//最大纬度

    /**输出图片的尺寸*/
    private int width;              //图片宽度
    private int height;             //图片高度

    /**其他属性*/
    private int zoom;    		//瓦片级别

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private boolean hasMark; 		//是否有标注物

    public double getMinLng() {
        return minLng;
    }

    public void setMinLng(double minLng) {
        this.minLng = minLng;
    }

    public double getMinlat() {
        return minlat;
    }

    public void setMinlat(double minlat) {
        this.minlat = minlat;
    }

    public double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(double maxLng) {
        this.maxLng = maxLng;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public boolean isHasMark() {
        return hasMark;
    }

    public void setHasMark(boolean hasMark) {
        this.hasMark = hasMark;
    }
}
