package org.potato.AnyThing.phoenix.dao.manual;

import org.potato.AnyThing.phoenix.dao.generator.model.Poiph;
import org.potato.AnyThing.phoenix.dto.req.GetPoiSqlReq;

import java.util.List;

public interface PoiphPhoenixMapper {

    /**
     * 从Phoenix中查询poi信息
     * @param req
     * @return
     */
    List<Poiph> getPois(GetPoiSqlReq req);

}