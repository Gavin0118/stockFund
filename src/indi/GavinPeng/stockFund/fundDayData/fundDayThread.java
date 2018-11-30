package indi.GavinPeng.stockFund.fundDayData;

import indi.GavinPeng.stockFund.file.outputTxt;
import indi.GavinPeng.stockFund.function.littleFunction;
import org.jsoup.nodes.Document;

import static indi.GavinPeng.stockFund.main.Main.*;

public class fundDayThread extends Thread {

    //东方财富 天天基金网 通过基金代码得到基金日数据表的链接拼凑
    //http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000
    private final static String fund_day_data_tables_url_before = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=";
    private final static String fund_day_data_tables_url_middle = "&pageIndex=";
    private final static String fund_day_data_tables_url_after = "&pageSize=2000";
    private final static String fund_day_data_tables_referrerUrl_before = "http://fund.eastmoney.com/f10/jjjz_";
    private final static String fund_day_data_tables_referrerUrl_after = ".htm";

    private String url;
    private String referrerUrl;
    private String stockFund_code_string;
    private String jsonSelectresult;
    private Document doc;
    private int i;
    //private ResultSet rs;


    fundDayThread(String stockFund_code_string) {
        this.stockFund_code_string = stockFund_code_string;
    }

    @Override
    public void run() {
        try {
            //stockFund_code_string = rs.getString("stockFund_code");
            i = 0; //页数置0
            do {
                ++i;
                url = fund_day_data_tables_url_before + stockFund_code_string + fund_day_data_tables_url_middle
                        + i + fund_day_data_tables_url_after;
                referrerUrl = fund_day_data_tables_referrerUrl_before + stockFund_code_string
                        + fund_day_data_tables_referrerUrl_after;

//                while (nctpr.getQueueSizeBalance() <= 0) {
//                    Thread.sleep(10);
//                }

                nctpr.new function().addNetConnectionTask(url, referrerUrl);
                doc = nctpr.new function().getNetConnectionResult(url);
                jsonSelectresult = doc.text();
                new jsonFiltersFundHistoryDay().jsonFiltersFundHistoryDayF(stockFund_code_string, jsonSelectresult);
                outputTxt.logFileWrite(stockFund_code_string + " 页数："
                        + i + "/" + new littleFunction().TotalCountCalculate(jsonSelectresult),0);

            } while (i < new littleFunction().TotalCountCalculate(jsonSelectresult));

//            while (dbtpr.getQueueSizeBalance() <= 0) {
//                Thread.sleep(10);
//            }

            dbtpr.new function().addInsertTask("update stock_fund_code_tables set todayUpdate = 1 where stockFund_code = \""
                    + stockFund_code_string + "\";");

            outputTxt.logFileWrite(++fundCount + " 基金代码：" + stockFund_code_string + " 完成了",0);
            fundCodeCirculateThread.count = fundCodeCirculateThread.count-1; //内存中的数量减少1
        } catch (NullPointerException e) {
            outputTxt.logFileWrite(e.toString(),1);
        }

    }
}
