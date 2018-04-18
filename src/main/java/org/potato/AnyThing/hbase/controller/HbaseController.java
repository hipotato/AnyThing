package org.potato.AnyThing.hbase.controller;

import org.potato.AnyThing.hbase.service.HbaseService;
import org.potato.AnyThing.phoenix.config.properties.HbaseProperties;
import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by potato on 2018/4/2.
 */
@RestController
@RequestMapping("hbase")
@EnableConfigurationProperties(HbaseProperties.class)
public class HbaseController {
    @Autowired
    HbaseService hbaseService;
    @RequestMapping(value = "/test")
    BaseResp test(Integer sourceId,Integer smId){
        return BaseResp.success().map().append("name","potato");
    }
}
