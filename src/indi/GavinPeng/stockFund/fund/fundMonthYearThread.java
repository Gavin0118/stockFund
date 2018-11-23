package indi.GavinPeng.stockFund.fund;

import indi.GavinPeng.stockFund.Parser.jsonFiltersFundForCodeMonthYear;
import indi.GavinPeng.stockFund.function.littleFunction;
import org.jsoup.nodes.Document;

import static indi.GavinPeng.stockFund.main.Main.nctpr;

public class fundMonthYearThread extends Thread {

    //取基金代码及月度、 年度数据链接
    private final static String fund_month_year_tables_url_before = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&pi=";
    private final static String fund_month_year_tables_url_after = "&pn=1000&dx=1";
    private final static String fund_month_year_tables_referrerUrl = "http://fund.eastmoney.com/data/fundranking.html";

    public void run(){
        String jsonSelectresult;
        Document doc;
        String url;
        int i=0; //页数
        System.out.println("取基金代码及基金年月数据开始了");
        do {
            ++i;
            url = fund_month_year_tables_url_before+i+fund_month_year_tables_url_after;
            nctpr.new function().addNetConnectionTask(url,fund_month_year_tables_referrerUrl);
            doc =  nctpr.new function().getNetConnectionResult(url);
            jsonSelectresult = doc.text();
            new jsonFiltersFundForCodeMonthYear().jsonFiltersFundForCodeMonthYearF(jsonSelectresult);
        }while(i< new littleFunction().allRecords(jsonSelectresult));
        System.out.println("取基金代码及基金年月数据结束了");
    }

}
