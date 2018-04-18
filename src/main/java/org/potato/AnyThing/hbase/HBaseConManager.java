package org.potato.AnyThing.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;

import org.potato.AnyThing.phoenix.config.properties.HbaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@EnableConfigurationProperties(HbaseProperties.class)
public class HBaseConManager {

	@Autowired
	HbaseProperties hbaseProperties;

	private static HTable table = null;
	//中转库连接配置
	private Configuration transferConf=null;

	private Connection transConnection=null;
	
	private static HBaseConManager instance=new HBaseConManager();
	public static HBaseConManager getInstance(){
		return instance;
	}
	
	private HBaseConManager(){
		transferConf=new Configuration();

		transferConf= HBaseConfiguration.create(transferConf);
		transferConf.set("hbase.client.operation.timeout", "3000"); 
		transferConf.set("hbase.client.retries.number", "2");
		transferConf.set("zookeeper.recovery.retry", "1");
		transferConf.set("hbase.rpc.timeout", "1000");
		transferConf.set("zookeeper.session.timeout", "10000");
		transferConf.set("hbase.zookeeper.quorum ", hbaseProperties.getZkHost());
		transferConf.set("hbase.zookeeper.property.clientPort", hbaseProperties.getZkPort());

		try {
			transConnection = ConnectionFactory.createConnection(transferConf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 获取HTable示例
	 * @param name：表名
	 * @return HTable示例
	 * @throws IOException
	 */
	public Table getHTableInstanceByName(String name) throws IOException{
		return transConnection.getTable(TableName.valueOf(name));
	}
}
 