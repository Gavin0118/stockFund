package indi.GavinPeng.stockFund.net;

import indi.GavinPeng.stockFund.file.outputTxt;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

class netConnectionClass {

    private Document doc = null;
    private Connection connection;

    netConnectionClass() {

    }

    Document JsoupNetConnection(String url, String referrerUrl) {
        try {
            connection = Jsoup.connect(url);
            connection.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
            connection.timeout(200000); //设置连接超时时间
            if (!referrerUrl.equals(null)) {
                connection.referrer(referrerUrl);
                connection.ignoreContentType(true);
            }
            doc = connection.get();
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            outputTxt.logFileWrite(e.toString(),1);
        } catch (IOException e) {
            outputTxt.logFileWrite(e.toString(),1);
        } catch (Exception e) {
            outputTxt.logFileWrite(e.toString(),1);
        }
        return doc;
    }
}