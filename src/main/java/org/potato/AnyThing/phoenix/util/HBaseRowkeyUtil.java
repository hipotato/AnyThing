package org.potato.AnyThing.phoenix.util;

//import org.apache.hadoop.hbase.util.Bytes;


public class HBaseRowkeyUtil
{
	
  //私有的构造方法 ，防止类被实例化
  private HBaseRowkeyUtil() throws Exception{
	  
	  throw new Exception("HBaseRowkeyUtil can not be initialized");
  }

  //用于新型rowkey结构：网格码（8byte）+网格层级（2byte）+高德类型（6byte）+高德ID（10byte）
  public static byte[] getNewRowkey(String geoNum, int geo_level, String type,String id)
  {
    long a = Long.valueOf(geoNum).longValue();
    byte[] geo = BytesUtil.toBytes(a);
    String layer = String.valueOf(geo_level);
    while(layer.length() < 2) {
      layer = "0" + layer;
    }
    byte[] level = BytesUtil.toBytes(layer);
    while(type.length()<6){
    	type=type+"0";
    }
    byte[] bType = BytesUtil.toBytes(type);
    byte[] bId = BytesUtil.toBytes(id);
    int len = geo.length + level.length + bType.length + bId.length;
    byte[] res = new byte[len];
    System.arraycopy(geo, 0, res, 0, geo.length);
    System.arraycopy(level, 0, res, geo.length, level.length);
    System.arraycopy(bType, 0, res, geo.length + level.length, bType.length);
    System.arraycopy(bId, 0, res, geo.length + level.length + bType.length, bId.length);
    return res;
  }


  public static byte[] getFootTrackRowkey(int appId,long userId, long time){
	  byte[] bAppId = BytesUtil.toBytes(appId);
	  byte[] bId = BytesUtil.toBytes(userId);
	  byte[] bTime = BytesUtil.toBytes(time);
	  int len =bAppId.length+bId.length+bTime.length;
	  byte[] res = new byte[len];
	  System.arraycopy(bAppId, 0, res, 0, bAppId.length);
	  System.arraycopy(bId, 0, res, bAppId.length, bId.length);
	  System.arraycopy(bTime, 0, res, bAppId.length+bId.length, bTime.length);
	  return res;
  }
  
//用于IwhereId结构：网格码（8byte）+网格层级（1byte）+楼层（1byte）+poi类型（3byte）+iwhereIndex（2byte）
  public static byte[] getRowkeyIwhereId(String geoNum, int geo_level, int floor,String type,short index)
  {  
	//geoNum直接用Long格式  
    long a = Long.valueOf(geoNum).longValue();
    byte[] geo = BytesUtil.toBytes(a);
    //geo_leve的实际范围是0~32，在byte的范围内的，我们之间将int强制转换成byte
    byte[] bLayer = new byte[1];
    
    bLayer[0] = (byte)(geo_level);
    
    //楼层的实际范围大概在-20~200之间,byte的范围是-128~127，我们将实际楼层减去80，存入byte，这样实际存储的数值范围为-100~120
    byte[] bFloor = new byte[1];
    bFloor[0] = (byte)(floor-80);
    
    //类型是6位字符，前两位是高德大类，中间两位是高德中类，最后两位是高德小类，每类用一个byte表示，一共3byte
    while(type.length()<6){
    	type=type+"0";
    }
    int typePre = Integer.parseInt(type.substring(0, 2));
    int typeMid = Integer.parseInt(type.substring(2, 4));
    int typeSuf = Integer.parseInt(type.substring(4, 6));
    byte[] bType = new byte[3];
    bType[0]=(byte)typePre;
    bType[1]=(byte)typeMid;
    bType[2]=(byte)typeSuf;
    //因为ID是非纯数字字符，只能按长度转成byte数组
    byte[] bIndex = BytesUtil.toBytes(index);
    int len = geo.length + bLayer.length + bFloor.length + bType.length + bIndex.length;
    byte[] res = new byte[len];
    System.arraycopy(geo, 0, res, 0, geo.length);
    System.arraycopy(bLayer, 0, res, geo.length, bLayer.length);
    System.arraycopy(bFloor, 0, res, geo.length+bLayer.length, bFloor.length);
    System.arraycopy(bType, 0, res, geo.length+bLayer.length+bFloor.length, bType.length);
    System.arraycopy(bIndex, 0, res, geo.length+bLayer.length+bFloor.length + bType.length,bIndex.length);
    return res;
    }

}
