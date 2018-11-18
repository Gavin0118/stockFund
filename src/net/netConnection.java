package net;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.ConnectException;

public class netConnection {

	public static Document JsoupNetConnection(String url, String referrerUrl) {
		Document doc = null;
		try {
			Connection connection = Jsoup.connect(url);
			connection.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)");
			connection.timeout(20000); //设置连接超时时间
			if (!referrerUrl.equals(null)) {
				connection.referrer(referrerUrl);
				connection.ignoreContentType(true);
			}
			doc = connection.get();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}