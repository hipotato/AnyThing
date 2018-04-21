package org.potato.AnyThing.phoenix.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.potato.AnyThing.imageMap.bo.MarkInfo;
import org.potato.AnyThing.phoenix.config.annotation.JsonRequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Created by potato on 2018/3/7.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageMarkMapReq {
    //网格码

    @NotNull
    private Integer width;
    @NotNull
    private Integer height;
    @NotNull
    @JsonRequestParam
    List<MarkInfo> marks;

}
