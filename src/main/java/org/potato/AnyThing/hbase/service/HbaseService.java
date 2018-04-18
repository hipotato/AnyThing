package org.potato.AnyThing.hbase.service;

import org.potato.AnyThing.phoenix.dto.resp.BaseResp;

/**
 * Created by potato on 2018/4/11.
 */
public interface HbaseService {

    BaseResp test(Integer sourceId,Integer smId);
}
