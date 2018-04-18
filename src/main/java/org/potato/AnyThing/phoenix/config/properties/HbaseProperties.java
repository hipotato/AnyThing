package org.potato.AnyThing.phoenix.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = HbaseProperties.PREFIX)
@Data
public class HbaseProperties {
    public static final String PREFIX = "hbase";
    private String zkHost;
    private String zkPort;
    private String table;
}
