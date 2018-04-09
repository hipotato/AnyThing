package org.potato.AnyThing.phoenix.controller;

import org.potato.AnyThing.phoenix.dao.generator.model.Poiph;
import org.potato.AnyThing.phoenix.dto.req.GetPoiReq;
import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.potato.AnyThing.phoenix.dto.resp.NormalPoiResp;
import org.potato.AnyThing.phoenix.service.PhoenixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by potato on 2018/4/2.
 */
@RestController
@RequestMapping("phoenix")
public class PhoenixController {

    @Autowired
    private PhoenixService phoenixService;

    @RequestMapping(value = "/getPoi")
    BaseResp getPoi(@Validated GetPoiReq req){
        return phoenixService.getPoi(req);
    }
    @RequestMapping(value = "/addPoi")
    String addPoi(){

        phoenixService.addPoi();
        return  "done";
    }
    @RequestMapping(value = "/getPoiByTypeCode")
    public NormalPoiResp getPoiByOtherId(String typecode){
        return  phoenixService.getPoiByTypeCode(typecode);
    }

}
