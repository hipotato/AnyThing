package org.potato.AnyThing.phoenix.service.imp;

import org.potato.AnyThing.phoenix.dao.generator.PoiphMapper;
import org.potato.AnyThing.phoenix.dao.generator.example.PoiphExample;
import org.potato.AnyThing.phoenix.dao.generator.model.Poiph;
import org.potato.AnyThing.phoenix.dto.req.GetPoiReq;
import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.potato.AnyThing.phoenix.dto.resp.NormalPoiResp;
import org.potato.AnyThing.phoenix.service.PhoenixService;
import org.potato.AnyThing.phoenix.util.HBaseRowkeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by potato on 2018/4/2.
 */
@Service
public class PhoenixServiceImp implements PhoenixService {

    @Autowired
    private PoiphMapper poiMapper;

    @Override
    public String getPoi(GetPoiReq req) {
        return null;
    }

    @Override
    public Boolean addPoi() {
        String geeNum = "526548410296434688";
        Integer geoLevel = 25;
        byte[] rowkey = HBaseRowkeyUtil.getRowkeyIwhereId(geeNum,geoLevel,1,"140100",(short)1);

        Poiph poi = Poiph.builder().rowkey(rowkey).geonum(geeNum).level(geoLevel.toString())
                .name("八达岭长城").typecode("140100").otherid("B000A45467").starting("001").build();
        int re = poiMapper.upsertWithBLOBs(poi);
        System.out.print("a");
        return null;
    }

    @Override
    public NormalPoiResp getPoiByTypeCode(String typecode) {
        NormalPoiResp.NormalPoiRespBuilder builder = NormalPoiResp.builder();
        List<Poiph> poiSet = poiMapper.selectByExample(new PoiphExample().limit(50).createCriteria().andTypecodeEqualTo(typecode).example());
        return  builder.pois(poiSet).build();
    }
}
