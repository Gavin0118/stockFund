package indi.GavinPeng.stockFund.fundCodeType;

import indi.GavinPeng.stockFund.file.outputTxt;
import indi.GavinPeng.stockFund.function.littleFunction;
import org.jsoup.nodes.Document;

import static indi.GavinPeng.stockFund.main.Main.*;

public class fundCodeTypeThread extends Thread {

    //取基金代码及月度、 年度数据链接
    private final static String fund_month_year_type_tables_url_before = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&pn=1000&dx=1&rs=&gs=0&sc=zzf&st=desc";
    private final static String fund_month_year_type_tables_referrerUrl = "http://fund.eastmoney.com/data/fundranking.html";

    //ALL:all 股票型：gp  混合型：hh  债卷型：zq  指数型：zs  保本型：bb  QDII:qdii   LOF: lof   FOF: fof
    private final static String fundTypeMy[] = {"gp", "hh", "zq", "zs", "bb", "qdii", "lof", "fof"};

    static int count = 0; //取基金代码临时变量，计数用

    public void run() {
        String jsonSelectresult;
        Document doc;
        String url;
        int i = 0;//数组下标志
        int page; //页数
        outputTxt.logFileWrite("取基金代码及基金年月类型数据开始了", 0);
        while (i < fundTypeMy.length) {
            page = 0;
            do {
                ++page;
                url = fund_month_year_type_tables_url_before + "&ft=" + fundTypeMy[i] + "&pi=" + page;
                while (nctpr.getQueueSizeBalance() <= 0) {
                    try {
                        Thread.sleep(threadSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                nctpr.new function().addNetConnectionTask(url, fund_month_year_type_tables_referrerUrl);
                doc = nctpr.new function().getNetConnectionResult(url);
                jsonSelectresult = doc.text();
                new jsonFiltersFundForCodeType().jsonFiltersFundForCodeMonthYearF(jsonSelectresult, fundTypeMy[i]);
            } while (page < new littleFunction().allRecords(jsonSelectresult));
            i++;
        }
        outputTxt.logFileWrite("取基金代码及基金年月类型数据结束了", 0);
        fundMonthYearDataIsOk = 1;
    }

}
