package org.potato.AnyThing.phoenix.controller;

import org.potato.AnyThing.phoenix.dto.req.GetPoiReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by potato on 2018/4/2.
 */
@RestController
@RequestMapping("phoenix")
public class PhoenixController {
    @RequestMapping(value = "/getPoi")
    String getPoi(@Validated GetPoiReq req){


        return "hello:"+System.currentTimeMillis();
    }
}
