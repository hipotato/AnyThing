package org.potato.AnyThing.phoenix.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;


/**
 * Created by potato on 2018/3/7.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageMapReq {
    //网格码
    @NotNull
    private Double left;
    @NotNull
    private Double right;
    @NotNull
    private Double top;
    @NotNull
    private Double bottom;

    private Integer width;
    private Integer height;

}
