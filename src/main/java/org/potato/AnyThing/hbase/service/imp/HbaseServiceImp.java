package org.potato.AnyThing.hbase.service.imp;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.util.Pair;
import org.potato.AnyThing.hbase.EpRowkeyUtil;
import org.potato.AnyThing.hbase.HBaseConManager;
import org.potato.AnyThing.hbase.service.HbaseService;
import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by potato on 2018/4/11.
 */
@Component
public class HbaseServiceImp implements HbaseService {
    @Autowired
    Connection connection;
    //@Autowired
    //HBaseConManager conManager;
    @Override
    public BaseResp test(Integer sourceId,Integer smId) {
        return DeleteEp(sourceId,smId);
    }
    public BaseResp DeleteEp(Integer sourceId,Integer smId){
        Table table = null;
        int count = 0;
        Long startTime = System.currentTimeMillis();
        Map<String,Object> reMap = new HashMap<>();
        try {
            table = connection.getTable(TableName.valueOf("EP"));
            //table = conManager.getHTableInstanceByName("EP");
            byte[] tempRowkey = EpRowkeyUtil.getEPTableRowKey("01",23,sourceId,smId);
            Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(tempRowkey, new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 });
            List<Pair<byte[], byte[]>> list = new ArrayList<Pair<byte[], byte[]>>();
            list.add(pair);
            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            FuzzyRowFilter rowFilter = new FuzzyRowFilter(list);
            filterList.addFilter(rowFilter);
            filterList.addFilter( new FirstKeyOnlyFilter());
            Scan scan = new Scan();
            scan.setFilter(filterList);
            ResultScanner rs = table.getScanner(scan);
            while (rs.next()!=null){
                count++;
            }
            table.close();
            Long dureTime = (System.currentTimeMillis()-startTime)/1000;
            String out = new StringBuilder("数据量：").append(count).append("  用时：").append(dureTime).toString();
            System.out.println(out);
            reMap.put("number:",count);
            reMap.put("dureTime:",dureTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  BaseResp.map().append("data",reMap);
    }
}
