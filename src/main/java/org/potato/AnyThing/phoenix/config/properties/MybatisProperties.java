package org.potato.AnyThing.phoenix.config.properties;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
@Data
public class MybatisProperties {
    private final static Logger logger = LoggerFactory.getLogger(MybatisProperties.class);
    public static final String PREFIX = "mybatis";

    private String[] mapperLocations; // mapper

    /**
     * mapperLocation
     * @return
     */
    public Resource[] resolveMapperLocations() {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            int length = mapperLocations.length;

            for (int i = 0; i < length; i++) {
                String mapperLocation = mapperLocations[i];

                try {
                    Resource[] mappers = (new PathMatchingResourcePatternResolver()).getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    logger.error("mapper-locations 获取异常！", e);
                }
            }
        }

        Resource[] result = new Resource[resources.size()];
        result = (Resource[]) resources.toArray(result);
        return result;
    }
}
