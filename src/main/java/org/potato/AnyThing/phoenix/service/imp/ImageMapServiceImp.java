package org.potato.AnyThing.phoenix.service.imp;

import org.potato.AnyThing.imageMap.bo.TileInfo;
import org.potato.AnyThing.imageMap.util.ImageMapUtil;
import org.potato.AnyThing.phoenix.dto.req.ImageMapReq;
import org.potato.AnyThing.phoenix.dto.req.ImageMarkMapReq;
import org.potato.AnyThing.phoenix.service.ImageMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by potato on 2018/4/19.
 */
@Service
public class ImageMapServiceImp implements ImageMapService {
    @Autowired
    private ImageMapUtil imageMapUtil;

    @Override
    public byte[] getImageMap(ImageMapReq req) {
        return  imageMapUtil.getImageMap(req);
    }

    @Override
    public byte[] getImageMarkMap(ImageMarkMapReq req) {
        return  imageMapUtil.getImageMarkMap(req);
    }
}
