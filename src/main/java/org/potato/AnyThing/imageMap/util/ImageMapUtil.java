package org.potato.AnyThing.imageMap.util;

import org.potato.AnyThing.imageMap.bo.MarkInfo;
import org.potato.AnyThing.imageMap.bo.Point;
import org.potato.AnyThing.phoenix.config.properties.ImageMapProperties;
import org.potato.AnyThing.phoenix.dto.req.ImageMapReq;
import org.potato.AnyThing.phoenix.dto.req.ImageMarkMapReq;
import org.potato.AnyThing.phoenix.exception.Exceptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by potato on 2018/3/7.
 */
@Service
public class ImageMapUtil {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImageMapUtil.class);

    @Autowired
    ImageMapProperties prop;
    /**
     * 根据地图范围+图片尺寸获取拼接后的谷歌瓦片截图
     * @param req
     * @return
     */
    public byte[] getImageMap(ImageMapReq req){
        byte[] imageByte = null;
        Map map = new Map(req,prop);
        int re = 0;
        try {
            re = map.bulkDownload2();
            if(re==-1){
                throw Exceptions.GOOGLE_NO_IMAGE.exception();
            }
            else if(re==-2){
                throw Exceptions.GOOGLE_TIME_OUT.exception();
            }
            imageByte = map.mergeImagesToBuffer(null);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw Exceptions.IMAGE_MAP_ERROR.exception();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw Exceptions.IMAGE_MAP_ERROR.exception();
        }
        if(imageByte==null){
            throw Exceptions.IMAGE_MAP_ERROR.exception();
        }
        return imageByte;
    }
    /**
     * 根据mark获取地图+标记
     * @param req
     * @return
     */
    public byte[] getImageMarkMap(ImageMarkMapReq req){
        Long startTime = System.currentTimeMillis();
        byte[] imageByte = null;
        if(req.getMarks()==null||req.getMarks().size()<=0){
            throw Exceptions.MARKS_EMPTY.exception();
        }

        List<Point>  markPoints = new LinkedList<>();
        for(MarkInfo mark:req.getMarks()){
            markPoints.add(mark.getCoord());
        }
        BoundingBox bBox = BoundingBox.getSimpleBoundingBox(markPoints,0.03);
        Map map = new Map(bBox.getNorth(),bBox.getWest(),bBox.getSouth(),bBox.getEast(),req.getHeight(),req.getWidth(),prop);

        int re = 0;
        try {
            re = map.bulkDownload2();
            Long nowTime1 = System.currentTimeMillis();
            System.out.println("下载瓦片用时："+(nowTime1-startTime));
            if(re==-1){
                throw Exceptions.GOOGLE_NO_IMAGE.exception();
            }
            else if(re==-2){
                throw Exceptions.GOOGLE_TIME_OUT.exception();
            }

            imageByte = map.mergeImagesToBuffer(req.getMarks());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw Exceptions.IMAGE_MAP_ERROR.exception();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw Exceptions.IMAGE_MAP_ERROR.exception();
        }
        if(imageByte==null){
            throw Exceptions.IMAGE_MAP_ERROR.exception();
        }
        System.out.println("总用时："+(System.currentTimeMillis()-startTime));
        return imageByte;
    }
}
