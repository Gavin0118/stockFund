package indi.GavinPeng.stockFund.fundPositionList;

import org.jsoup.nodes.Document;

import static indi.GavinPeng.stockFund.main.Main.nctpr;

public class fundPositionListNetConThread extends Thread {

    private String stockFund_code;

    private int year = 2018;//默认为今年，BUG后期修补

    private String url;

    private String jsonSelectresult;
    private Document doc;

    //&year=
    private String urlBefore = "http://fund.eastmoney.com/f10/FundArchivesDatas.aspx?type=jjcc&topline=10&code=";

    fundPositionListNetConThread(String stockFund_code){
        this.stockFund_code = stockFund_code;

    }

    @Override
    public void run() {
        url = urlBefore+"000046"+"&year="+year;
        nctpr.new function().addNetConnectionTask(url, null);
        doc = nctpr.new function().getNetConnectionResult(url);
        jsonSelectresult = doc.text();
        new dataFilterThread().dataFilterThreadF(doc);
    }
}
