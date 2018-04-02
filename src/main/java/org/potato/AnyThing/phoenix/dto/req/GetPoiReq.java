package org.potato.AnyThing.phoenix.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by potato on 2018/4/2.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPoiReq {

    @NotNull
    private Long geoNum;
    @NotNull
    private Integer level;
    @NotNull
    @Range(min=0,max=Long.MAX_VALUE)
    private Integer offset;
    @NotNull
    @Range(min=1,max=Long.MAX_VALUE)
    private Integer limit;
    private List<String> types;
}
