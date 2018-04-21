package org.potato.AnyThing.imageMap.util;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class Proxy {
	private static Logger logger = LoggerFactory.getLogger(Proxy.class);
    private final static String IP = getOwnIP();
    private static int page = 1;
    private static ArrayList<String> proxies;
    private static int usedProxy = 0;
    //已废弃
    private static ArrayList<String> getList0() {
        usedProxy = 0;
        proxies = new ArrayList<String>();
        Document doc;
        
        try {
        	 if (page == 1) {
                 //doc = Jsoup.connect("http://proxylist.hidemyass.com/").timeout(50000).get();
                 doc = Jsoup.connect("http://www.xicidaili.com/nn/").get();
             }
             else {
                 doc = Jsoup.connect("http://proxylist.hidemyass.com/" + page + "#listable").timeout(50000).get();
             }
        	 page++;
             Elements trs = doc.select("#listable tbody > tr");
             for (Element tr : trs)
             {
                 String port = null;
                 String ip = null;

                 Elements tds = tr.select("td");
                 for (Element td : tds)
                 {
                     Elements style = td.select("style");
                     if(style.isEmpty())
                     {
                         // if text inside <td></td> is number - it is port
                         if (td.text().matches("\\d+"))
                             port = td.text();
                     }
                     else
                     {
                         // find invisible classes
                         String invisClasses = style.first().data()
                                 .replaceAll("\\..*?\\{display:inline\\}|\n|\r", "")
                                 .replaceAll("\\{.*?\\}|^\\.", "")
                                 .replaceAll("\\.", "|");
                         // Remove noise, invisible elements, get IP
                         Element els = td.select("span").first();
                         ip = els.html()
                                 .replaceAll("\n|\r", "")
                                 .replaceAll("\\<style\\>.*?\\</style\\>|\\<(span|div) style=\"display:none\"\\>.*?\\</(span|div)\\>", "")
                                 .replaceAll("\\<(span|div) class=\"(" + invisClasses + ")\"\\>.*?\\</(span|div)\\>", "")
                                 .replaceAll("\\<.*?\\>| ", "");
                     }
                 }
                 proxies.add(ip + ":" + port);
             }
		} catch (IOException e) {
			// TODO: handle exception
			 System.out.println(e.toString());
		}
        return proxies;
    }

    private static ArrayList<String> getList() {
        usedProxy = 0;
        proxies = new ArrayList<String>();
        Document doc;
        int size = 0;
        try {
        	System.out.println("getList");
        	logger.debug("GetList was called when SetProxy");
             doc = Jsoup.connect("http://www.xicidaili.com/nn").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
             
             //Elements trs = doc.select("#listable tbody > tr");
             Element list = doc.getElementById("ip_list");
             list = list.child(0);
             Elements trs = list.select("tr");
             for(int i=0;i<trs.size();++i){
            	 Element tr = trs.get(i);
            	 Element ip = tr.child(1);
            	 if (ip.toString().contains("IP")) {
 					continue;
 				}
            	 Element port = tr.child(2);
            	 Element speed = tr.child(6);
            	 Element conTime = tr.child(7);
            	 speed = speed.child(0);
            	 conTime = conTime.child(0);
            	 try {
            		 String strSpeed = speed.attr("title");
                	 strSpeed = strSpeed.substring(0, strSpeed.length()-1);
                	 String strConTime = conTime.attr("title");
                	 strConTime = strConTime.substring(0, strSpeed.length()-1);
                	 double dSpeed,dConTime;
                	 dSpeed = Double.parseDouble(strSpeed);
                	 dConTime = Double.parseDouble(strConTime);
                	 if(dSpeed>1.0||dConTime>1.0){
                		 continue;
                	 }
				} catch (Exception e) {
					// TODO: handle exception
					continue;
				}
            	 proxies.add(ip.text() + ":" + port.text());
             }
             
		} catch (IOException e) {
			// TODO: handle exception
			 System.out.println(e.toString());
		}
        return proxies;
    }
    
    
    public static void setProxy() throws IOException {
        if (proxies == null)
            proxies = getList();
        boolean alreadyChanged = false;
        while (!alreadyChanged || !isWorking()) {
            System.setProperty("http.proxyHost", proxies.get(usedProxy).split(":")[0]);
            System.setProperty("http.proxyPort", proxies.get(usedProxy).split(":")[1]);
            usedProxy++;
            if (usedProxy == proxies.size()-1) {
                proxies = getList();
                usedProxy = 0;
            }
            alreadyChanged = true;
        }
        String log = "Change proxy to:"+proxies.get(usedProxy-1);
        System.out.println(log);
        logger.debug("【PROXY】:"+log);
    }

    public static void clearProxy(){
    	System.getProperties().remove("http.proxyHost");
        System.getProperties().remove("http.proxyPort");
    }
    
    
    private final static String getOwnIP() {
        try {
            Document doc;
            doc = Jsoup.connect("http://www.whatismyip.com.tw/").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
   		 	Element ip = doc.body();
   		 	ip = ip.child(1);
   		 	return ip.text();
        } catch (IOException e) {
            try {
            	 Document doc;
            	 doc = Jsoup.connect("http://2ip.ru/").get();
                 Element ip = doc.select("big").first();
                 return ip.text();
            } catch (IOException ex) { return null; }
        }
    }

    private static String getIP() throws IOException {
        Document doc;
        int attempt = 0;
        while (attempt < 5) {
            try {
            		 doc = Jsoup.connect("http://www.whatismyip.com.tw/").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
            		 Element ip = doc.body();
            		 ip = ip.child(1);
            		 return ip.text();
            } catch (IOException e) {
                try {
                	 doc = Jsoup.connect("http://2ip.ru/").get();
                     Element ip = doc.select("big").first();
                     return ip.text();
                } catch (Exception ex) { }
            }
            attempt++;
        }
        return IP; // return your own IP => isWorking() -> False
    }

    private static boolean isWorking() throws IOException {
        return !getIP().equals(IP);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Your IP: " + IP);
            proxies = getList();
            for (String p : proxies)
                System.out.println(p);
            setProxy();
            System.out.println("Your current IP: " + getIP());
            System.out.println("Proxy is working: " + isWorking());
            setProxy();
            System.out.println("\nChanged proxy info");
            System.out.println("Your current IP: " + getIP());
            System.out.println("Proxy is working: " + isWorking());
        } catch (IOException e) {
            System.out.println("Error! " + e.toString());
        }
    }
}