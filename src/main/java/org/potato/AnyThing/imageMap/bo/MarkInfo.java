package org.potato.AnyThing.imageMap.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**标签信息
 * Created by potato on 2018/4/19.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkInfo {

    Point coord;//坐标
    String title;//标题
    String time;//时间
    String picUrl;//缩略图url
    Integer offsetX;
    Integer offsetY;

    public MarkInfo(Point coord, String title, String time, String picUrl) {
        this.coord = coord;
        this.title = title;
        this.time = time;
        this.picUrl = picUrl;
    }
}
