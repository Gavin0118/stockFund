package indi.GavinPeng.stockFund.netConnectionThreadPool;

import indi.GavinPeng.stockFund.file.outputTxt;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class netConnectionClass {

    private Document doc = null;
    private Connection connection;

    Document JsoupNetConnection(String url, String referrerUrl) {
        try {
            connection = Jsoup.connect(url);
            connection.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
            connection.timeout(200000); //设置连接超时时间
            connection.ignoreContentType(true);
            if(referrerUrl!=null){
                connection.referrer(referrerUrl);
            }
            doc = connection.get();
        }  catch (IOException e) {
            outputTxt.logFileWrite(e.toString(),1);
        }
        return doc;
    }
}