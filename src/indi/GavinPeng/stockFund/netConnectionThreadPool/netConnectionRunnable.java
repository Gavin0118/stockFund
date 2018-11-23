package indi.GavinPeng.stockFund.netConnectionThreadPool;

import org.jsoup.nodes.Document;

import static indi.GavinPeng.stockFund.main.Main.nctpr;

public class netConnectionRunnable implements Runnable {
    private netConnectionClass nc = new netConnectionClass();
    private String url;
    private String referrerUrl;
    private Document doc;

    netConnectionRunnable(String url,String referrerUrl) {
        this.url = url;
        this.referrerUrl = referrerUrl;

    }

    public void run() {
        nctpr.new function().putUrlAndReferrerUrl(url,referrerUrl);
        doc = nc.JsoupNetConnection(url,referrerUrl);
        nctpr.new function().putNetReturnResult(url,doc);
    }
}
