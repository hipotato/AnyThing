package org.potato.AnyThing.phoenix.dto.resp;

import lombok.Builder;
import lombok.Data;
import org.potato.AnyThing.phoenix.dao.generator.model.Poiph;

import java.util.List;

/**
 * Created by potato on 2018/4/4.
 */
@Data
@Builder
public class NormalPoiResp extends BaseResp{
    private  List<Poiph> pois;
}
