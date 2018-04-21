package org.potato.AnyThing.imageMap.util;

import org.potato.AnyThing.imageMap.bo.DownLoadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;


public class TimeoutURL2 implements Callable<DownLoadInfo> {
	private static Logger logger = LoggerFactory.getLogger(TimeoutURL2.class);
    private URL url;

    public TimeoutURL2(String spec) throws MalformedURLException {
        url = new URL(spec);
    }

    @Override
    public DownLoadInfo call() throws Exception {
    	
    	while(true){
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
    		//con.setRequestProperty("User-Agent","Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1b2) Gecko/20060823 SeaMonkey/1.1a");
        	
    		int code = new Integer(con.getResponseCode());
        	InputStream stream = null;
        	if(code==403||code==400){
        		Proxy.setProxy();
        		System.out.println("DownLoad error "+code +"Switch Proxy now...");
        		continue;
        	}
        	if(code==200){
        		stream = con.getInputStream();
        	}
        	DownLoadInfo info= new DownLoadInfo();

        	info.setResultCode(code);
        	info.setInputStream(stream);
        	logger.debug("【获取影像图服务】Google服务器ResponseCode: "+code);
            return info;
    	}
    }
}