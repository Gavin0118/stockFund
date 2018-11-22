package indi.GavinPeng.stockFund.fund;

import indi.GavinPeng.stockFund.Parser.jsonFiltersFundHistoryDay;
import indi.GavinPeng.stockFund.file.txt;
import indi.GavinPeng.stockFund.function.littleFunction;
import org.jsoup.nodes.Document;

import java.sql.ResultSet;
import java.sql.SQLException;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;
import static indi.GavinPeng.stockFund.main.Main.nctpr;
import static indi.GavinPeng.stockFund.main.Main.fundCount;

public class fundDayThread extends Thread {

    //东方财富 天天基金网 通过基金代码得到基金日数据表的链接拼凑
    //http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000
    public final static String fund_day_data_tables_url_before = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=";
    public final static String fund_day_data_tables_url_middle = "&pageIndex=";
    public final static String fund_day_data_tables_url_after = "&pageSize=2000";
    public final static String fund_day_data_tables_referrerUrl_before = "http://fund.eastmoney.com/f10/jjjz_";
    public final static String fund_day_data_tables_referrerUrl_after = ".htm";

    private String url;
    private String referrerUrl;
    private String stockFund_code_string;
    private String jsonSelectresult;
    private Document doc;
    private int i;
    private ResultSet rs;


    public fundDayThread(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public void run() {
        try {
            stockFund_code_string = rs.getString("stockFund_code");
            i = 0; //页数置0
            do {
                ++i;
                url = fund_day_data_tables_url_before
                        + stockFund_code_string
                        + fund_day_data_tables_url_middle
                        + i
                        + fund_day_data_tables_url_after;
                referrerUrl = new String(fund_day_data_tables_referrerUrl_before
                        + stockFund_code_string
                        + fund_day_data_tables_referrerUrl_after);

                nctpr.new function().addNetConnectionTask(url, referrerUrl);
                doc = nctpr.new function().getNetConnectionResult(url);

                jsonSelectresult = doc.text();
                new jsonFiltersFundHistoryDay().jsonFiltersFundHistoryDayF(stockFund_code_string, jsonSelectresult);
                txt.logFileWrite(stockFund_code_string + " 页数："
                        + i
                        + "/"
                        + littleFunction.TotalCountCalculate(jsonSelectresult));
            } while (i < littleFunction.TotalCountCalculate(jsonSelectresult));


            dbtpr.new function().addInsertTask("update stock_fund_code_tables set todayUpdate = 1 where stockFund_code = \""
                    + stockFund_code_string
                    + "\";");

            System.out.println(++fundCount + " 基金代码：" + stockFund_code_string + " 完成了");
        } catch (SQLException e) {
            txt.logFileWrite(e.toString());
        }

    }
}
