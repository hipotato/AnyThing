package org.potato.AnyThing.phoenix.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.potato.AnyThing.phoenix.config.properties.HbaseProperties;
import org.potato.AnyThing.phoenix.config.properties.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * ---------------------------------------------------------------------------
 *
 * -------------------------------------------------------------------------------------------------------------------------------------------------------
 * @author: potato
 * @time:2017/8/7 17:54
 * ---------------------------------------------------------------------------
 */
@Configuration
@AutoConfigureAfter(DruidConfig.class)
@EnableConfigurationProperties(MybatisProperties.class)
@MapperScan(basePackages = {"org.potato.AnyThing.phoenix.dao"})
public class MybatisConfig {
    /**
     * SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource, @Autowired MybatisProperties properties) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 1.设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 2.设置mapper信息
        sqlSessionFactoryBean.setMapperLocations(properties.resolveMapperLocations());

        // !!!! callSettersOnNulls
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        // 驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }
}