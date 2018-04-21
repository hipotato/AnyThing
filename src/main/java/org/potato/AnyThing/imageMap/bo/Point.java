package org.potato.AnyThing.imageMap.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by potato on 2018/4/19.
 */
public class Point {
    @Override
    public String toString() {
        return "Point [lat=" + lat + ", lng=" + lng + "]";
    }

    public Double lat;  // 纬度
    public Double lng;  // 经度

    /**
     *
     */
    public Point() {
        super();
    }
    /**
     * @param lat
     * @param lng
     */
    public Point(Double lat, Double lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }
    /**
     * @return the lat
     */
    public Double getLat() {
        return lat;
    }
    /**
     * @param lat the lat to set
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }
    /**
     * @return the lng
     */
    public Double getLng() {
        return lng;
    }
    /**
     * @param lng the lng to set
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    //判断当前坐标是否合法
    @JsonIgnore
    public Boolean isValidate(){
        if(lng==null||lat==null){
            return false;
        }
        if(lng>=-180&&lng<=180&&lat>=-90&&lat<=90){
            return true;
        }
        return false;
    }
}
