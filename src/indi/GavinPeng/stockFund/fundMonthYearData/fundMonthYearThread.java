package indi.GavinPeng.stockFund.fundMonthYearData;

import indi.GavinPeng.stockFund.file.outputTxt;
import indi.GavinPeng.stockFund.function.littleFunction;
import org.jsoup.nodes.Document;

import static indi.GavinPeng.stockFund.main.Main.*;

public class fundMonthYearThread extends Thread {

    //取基金代码及月度、 年度数据链接
    private final static String fund_month_year_type_tables_url_before = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&pi=";
    private final static String fund_month_year_type_tables_url_after = "&pn=1000&dx=1";
    private final static String fund_month_year_type_tables_referrerUrl = "http://fund.eastmoney.com/data/fundranking.html";

    public void run() {
        String jsonSelectresult;
        Document doc;
        String url;
        int page = 0; //页数
        outputTxt.logFileWrite("取基金代码及基金年月类型数据开始了", 0);
            do {
                ++page;
                url = fund_month_year_type_tables_url_before + page + fund_month_year_type_tables_url_after;

                while(nctpr.getQueueSizeBalance() <= 0){
                    try {
                        Thread.sleep(threadSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                nctpr.new function().addNetConnectionTask(url, fund_month_year_type_tables_referrerUrl);
                doc = nctpr.new function().getNetConnectionResult(url);
                jsonSelectresult = doc.text();
                new jsonFiltersFundForCodeMonthYear().jsonFiltersFundForCodeMonthYearF(jsonSelectresult);
            } while (page < new littleFunction().allRecords(jsonSelectresult));
        outputTxt.logFileWrite("取基金代码及基金年月类型数据结束了", 0);
        fundMonthYearDataIsOk = 1;
    }

}
