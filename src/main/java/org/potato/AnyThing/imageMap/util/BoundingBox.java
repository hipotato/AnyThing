package org.potato.AnyThing.imageMap.util;


import org.potato.AnyThing.imageMap.bo.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * 球面的最小外包矩形，非传统二维平面矩形。可以跨越0°和180°
 * @author YangGH
 *
 */
public class BoundingBox {
	
	private Double west;	//最西方
	private Double east;	//最东方
	private Double south;	//最北方
	private Double north;	//最南方
	
	public Double getWest() {
		return west;
	}
	public void setWest(Double west) {
		this.west = west;
	}
	
	public Double getEast() {
		return east;
	}
	public void setEast(Double east) {
		this.east = east;
	}
	public Double getSouth() {
		return south;
	}
	public void setSouth(Double south) {
		this.south = south;
	}
	public Double getNorth() {
		return north;
	}
	public void setNorth(Double north) {
		this.north = north;
	}
	
	//带参数的构造函数
	public BoundingBox(Double west, Double east, Double south, Double north) {
		super();
		this.west = west;
		this.east = east;
		this.south = south;
		this.north = north;
	}
	/**
	 * 获取最小外包矩形（单个半球的模式）
	 * @param points
	 * @param minDeta 最小跨度，如果东西跨度或者南北跨度小于minDeta，则将跨度设为minDeta
	 * @return
	 */
	public static BoundingBox getSimpleBoundingBox(List<Point> points,Double minDeta){
		
		if(points==null||points.isEmpty()){
			return null;
		}
		Double west = Double.MAX_VALUE;
		Double east = -Double.MAX_VALUE;
		Double north = -Double.MAX_VALUE;
		Double south = Double.MAX_VALUE;
		
		for(Point pt:points){
			if(pt.getLng()<west){
				west=pt.getLng();
			}
			if(pt.getLng()>east){
				east=pt.getLng();
			}
			if(pt.getLat()<south){
				south=pt.getLat();
			}
			if(pt.getLat()>north){
				north=pt.getLat();
			}
		}
		if(minDeta!=null){
			if(east-west<minDeta){
				east = (east+west)/2 + minDeta/2;
				west = (east+west)/2 - minDeta/2;
			}
			if(north-south<minDeta){
				north = (north+south)/2 + minDeta/2;
				south = (north+south)/2 - minDeta/2;
			}
		}

		return new BoundingBox(west, east, south, north);
	}
	
	public Boolean isInBox(Point pt){
		//如果跨越180°
		if(this.west>this.east){
			if(pt.getLat()>=south&&pt.getLat()<=north&&(pt.getLng()>=west||pt.getLng()<=east)){
				return true;
			}
		}
		//不跨越180°
		else {
			if(pt.getLat()>=south&&pt.getLat()<=north&&pt.getLng()>=west&&pt.getLng()<=east){
				return true;
			}
		}
		return false;
	}
	
	public static BoundingBox getBoundingBox(List<Point> points){
		if(points.isEmpty()){
			return null;
		}
		
		/**东半球集合*/
		List<Point> eastPoint = new ArrayList<>();
		/**西半球集合*/
		List<Point> westPoint = new ArrayList<>();
		
		for(Point point:points){
			if(!point.isValidate()){
				continue;
			}
			if(point.getLng()>=0){
				eastPoint.add(point);
			}else {
				westPoint.add(point);
			}
		}
		/**如果用户分处不同半球,计算跨0度和跨180度两种情况外包矩形，取最小的外包矩形*/
		if((!eastPoint.isEmpty())&&(!westPoint.isEmpty())){
			BoundingBox eastBox = BoundingBox.getSimpleBoundingBox(eastPoint,null);
			BoundingBox westBox = BoundingBox.getSimpleBoundingBox(westPoint,null);
			//如果跨0°经线外包矩形宽度大于跨180°经线的外包矩形，则取跨0°的结果
			if(eastBox.getEast()-westBox.getWest()<(180-eastBox.getWest()+westBox.getEast()-(-180))){
				return new BoundingBox(westBox.getWest(), eastBox.getEast(), 
						Double.min(eastBox.getSouth(),westBox.getSouth()), Double.max(eastBox.getNorth(), westBox.getNorth()));
			}
			//如果跨0°经线外包矩形宽度小于跨180°经线的外包矩形，则取跨180°的结果
			else {
				return new BoundingBox(eastBox.getWest(), westBox.getEast(), 
						Double.min(eastBox.getSouth(),westBox.getSouth()), Double.max(eastBox.getNorth(), westBox.getNorth()));
			}	
			
		}else {
			return BoundingBox.getSimpleBoundingBox(points,null);
		}
	}

	/**
	 * 获取最小外包矩形的中心点坐标
	 * @return 中心点坐标
	 */
	public Point getCentrePos(){
		Double midLng =Double.MAX_VALUE;
		//对于跨越180的情况
		if(west>east){
			//横向跨度
			Double width = 360-west+east;
			//中心线
			midLng = (west+east<0? west+width/2.0:east-width/2.0);
		}else {
			midLng = (west+east)/2.0;
		}
		return new Point((north+south)/2.0,midLng);
	}
	/**
	 * 获取外包矩形最大边长的跨度
	 * @return 最大边长的跨度，单位度
	 */
	public Double getMaxEdgeLength(){
		if(west>east){
			return Double.max(360-west+east, north-south);
		}else {
			return Double.max(east-west, north-south);
		}
	}

	/**
	 * 获取外包矩形南北跨度
	 * @return
	 */
	public Double getLatitudeDelta(){
		return  north-south;
	}
	/**
	 * 获取外包矩形南北跨度
	 * @return
	 */
	public Double getLongitudeDelta(){
		if(west>east){
			return 360-west+east;
		}else {
			return east-west;
		}
	}
	/**
	 * 判断是否与其他BoudingBox相交
	 * @param other
	 * @return 如果两个Box有公共区域，则返回true，否则返回false
	 */
	public Boolean isIntersect(BoundingBox other){
		List<BoundingBox> thisList = new ArrayList<>();
		List<BoundingBox> otherList = new ArrayList<>();
		
		if(this.west>this.east){
			thisList.add(new BoundingBox(this.west, 180.0, this.south, this.north));
			thisList.add(new BoundingBox(-180.0, this.east, this.south, this.north));
		}else {
			thisList.add(this);
		}
		if(other.west>other.east){
			otherList.add(new BoundingBox(other.west, 180.0, other.south, this.north));
			otherList.add(new BoundingBox(-180.0, other.east, other.south, other.north));
		}else {
			otherList.add(other);
		}
		for(BoundingBox a:thisList){
			for(BoundingBox b:otherList){
				if(this.simpleRectIntersect(a, b)){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断两个box是否相交，条件是两个box均不跨越180度
	 * @param boxA
	 * @param boxB
	 * @return
	 */
	private Boolean simpleRectIntersect(BoundingBox boxA,BoundingBox boxB){
		Double minx = Double.max(boxA.west, boxB.west);
		Double miny = Double.max(boxA.south, boxB.south);
		Double maxx = Double.min(boxA.east, boxB.east);
		Double maxy = Double.min(boxA.north, boxB.north);
		if(minx>=maxx||miny>=maxy){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		BoundingBox box1 =new BoundingBox(175.1, -175.0, 32.0, 40.0);
		BoundingBox box2 =new BoundingBox(172.0, 175.0,35.0, 39.0);
		System.out.println(box1.isIntersect(box2));
	}

	/**
	 * 扩展Box，使得传入的坐标为box的中心点
	 * @param box 原始的box
	 * @param centre 新box的中心点
	 * @return
	 */
	public static BoundingBox centralizationBoundingBox(BoundingBox box,Point centre){
		Double halfWidth = ((centre.getLng()-box.getWest())>(box.getEast()-centre.getLng())?centre.getLng()-box.getWest():box.getEast()-centre.getLng());
		Double halfHeight = ((centre.getLat()-box.getSouth())>(box.getNorth()-centre.getLat())?centre.getLat()-box.getSouth():box.getNorth()-centre.getLat());
		return new BoundingBox(centre.getLng()-halfWidth,centre.getLng()+halfWidth,centre.getLat()-halfHeight,centre.getLat()+halfHeight);
	}


	@Override
	public String toString() {
		return "BoundingBox [west=" + west + ", east=" + east + ", south=" + south + ", north=" + north + "]";
	}
}
