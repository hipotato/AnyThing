package org.potato.AnyThing.phoenix.service.imp;

import org.potato.AnyThing.phoenix.dao.generator.PoiphMapper;
import org.potato.AnyThing.phoenix.dao.generator.example.PoiphExample;
import org.potato.AnyThing.phoenix.dao.generator.model.Poiph;
import org.potato.AnyThing.phoenix.dao.manual.PoiphPhoenixMapper;
import org.potato.AnyThing.phoenix.dto.req.GetPoiReq;
import org.potato.AnyThing.phoenix.dto.req.GetPoiSqlReq;
import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.potato.AnyThing.phoenix.dto.resp.NormalPoiResp;
import org.potato.AnyThing.phoenix.service.PhoenixService;
import org.potato.AnyThing.phoenix.util.HBaseRowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by potato on 2018/4/2.
 */
@Service
public class PhoenixServiceImp implements PhoenixService {

    @Autowired
    private PoiphMapper poiMapper;

    @Autowired
    private PoiphPhoenixMapper poiphPhoenixMapper;

    @Override
    public BaseResp getPoi(GetPoiReq req) {
        long geoNumMin = req.getGeoNum();
        long a = -1L;
        long geoNumMax = geoNumMin|(a>>>req.getLevel()*2);
        if(req.getTypes()==null){
            req.setTypes(new ArrayList<>());
        }
        GetPoiSqlReq req2 =  new GetPoiSqlReq();
        req2.setTypes(req.getTypes());
        req2.setGeoNumMax(geoNumMax);
        req2.setGeoNumMin(geoNumMin);
        req2.setLimit(req.getLimit());
        req2.setOffset(req.getOffset());

        return  BaseResp.map().append("data",poiphPhoenixMapper.getPois(req2));

    }

    @Override
    public Boolean addPoi() {
        String geeNum = "526548410296434688";
        Integer geoLevel = 25;
        return null;
    }

    @Override
    public NormalPoiResp getPoiByTypeCode(String typecode) {
        NormalPoiResp.NormalPoiRespBuilder builder = NormalPoiResp.builder();
        List<Poiph> poiSet = poiMapper.selectByExample(new PoiphExample().limit(50).createCriteria().andTypecodeEqualTo(typecode).example());
        return  builder.pois(poiSet).build();
    }
}
