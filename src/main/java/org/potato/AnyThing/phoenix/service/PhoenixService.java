package org.potato.AnyThing.phoenix.service;

import org.potato.AnyThing.phoenix.dao.generator.model.Poiph;
import org.potato.AnyThing.phoenix.dto.req.GetPoiReq;
import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.potato.AnyThing.phoenix.dto.resp.NormalPoiResp;

import java.util.List;

/**
 * Created by potato on 2018/4/2.
 */
public interface PhoenixService {
    BaseResp getPoi(GetPoiReq req);
    Boolean addPoi();

    public NormalPoiResp getPoiByTypeCode(String typecode);

}
