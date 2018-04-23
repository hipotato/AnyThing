package org.potato.AnyThing.phoenix.config;


import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import org.potato.AnyThing.phoenix.config.properties.HbaseProperties;
import org.potato.AnyThing.phoenix.config.properties.ImageMapProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * ---------------------------------------------------------------------------
 *
 * -------------------------------------------------------------------------------------------------------------------------------------------------------
 * @author: potato
 * @time:2017/8/7 17:54
 * ---------------------------------------------------------------------------
 */
@Configuration
@EnableConfigurationProperties(HbaseProperties.class)
public class HbaseConfig {
    /**
     * SqlSessionFactory
     */
    @Bean
    public Connection connectionFactory(@Autowired HbaseProperties properties) throws Exception {
        org.apache.hadoop.conf.Configuration conf=new org.apache.hadoop.conf.Configuration();

        conf.set("hbase.client.operation.timeout", "3000");
        conf.set("hbase.client.retries.number", "2");
        conf.set("zookeeper.recovery.retry", "1");
        conf.set("hbase.rpc.timeout", "1000");
        conf.set("zookeeper.session.timeout", "10000");
        conf.set("hbase.zookeeper.quorum ", properties.getZkHost());
        conf.set("hbase.zookeeper.property.clientPort", properties.getZkPort());
        try {
            return ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return  null;
        }
    }
}