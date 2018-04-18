package org.potato.AnyThing.hbase;

import org.apache.hadoop.hbase.util.Bytes;

/**环保项目表rowkey工具
 * Created by potato on 2018/4/11.
 */
public class EpRowkeyUtil {
    public static byte[] getEPTableRowKey(String geoNum, int geoLevel, int sourceId, int smid) {
        if (geoNum == null || geoNum.isEmpty()) {
            return null;
        }
        long lGeoNum = 0L;
        try {
            lGeoNum = Long.parseLong(geoNum);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        byte[] bLevel = new byte[1];
        bLevel[0] = (byte) (geoLevel);
        byte[] bGeoNum = Bytes.toBytes(lGeoNum);
        byte[] bSourceId = Bytes.toBytes(sourceId);
        byte[] bSMid = Bytes.toBytes(smid);
        int len = bGeoNum.length + bLevel.length + bSourceId.length + bSMid.length;
        byte[] res = new byte[len];
        System.arraycopy(bGeoNum, 0, res, 0, bGeoNum.length);
        System.arraycopy(bLevel, 0, res, bGeoNum.length, bLevel.length);
        System.arraycopy(bSourceId, 0, res, bGeoNum.length + bLevel.length, bSourceId.length);
        System.arraycopy(bSMid, 0, res, bGeoNum.length + bLevel.length + bSourceId.length, bSMid.length);
        return res;
    }
}
