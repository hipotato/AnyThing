package org.potato.AnyThing.phoenix.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = ImageMapProperties.PREFIX)
@Data
public class ImageMapProperties {
    public static final String PREFIX = "imageMap";
    private String source;
    private Integer style;
    private Integer lang;
}
