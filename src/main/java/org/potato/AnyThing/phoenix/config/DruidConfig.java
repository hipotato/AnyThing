package org.potato.AnyThing.phoenix.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ---------------------------------------------------------------------------
 * druid 数据源配置
 * ---------------------------------------------------------------------------
 */
@Configuration
public class DruidConfig {

	private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    /**
     * 返回数据源
     * @return
     */
	@Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "db.datasource")
	public DruidDataSource dataSource(){
	    logger.info("配置数据源datasource......");
		return (DruidDataSource)DataSourceBuilder.create().type(DruidDataSource.class).build();
	}
}
