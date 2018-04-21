package org.potato.AnyThing.phoenix.service;

import org.potato.AnyThing.phoenix.dto.req.ImageMapReq;
import org.potato.AnyThing.phoenix.dto.req.ImageMarkMapReq;

/**
 * Created by potato on 2018/4/19.
 */
public interface ImageMapService {
    /**
     * 通过经纬度四至+图片尺寸获取地图拼接图
     * @param req
     * @return
     */
    public byte[] getImageMap(ImageMapReq req);

    /**
     * 获取地图拼接图，含标记点
     * @param req
     * @return
     */
    public byte[] getImageMarkMap(ImageMarkMapReq req);
}
