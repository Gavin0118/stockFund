package fund;

import Parser.jsonFiltersFundHistoryDay;
import dataBase.dataBaseClass;
import org.jsoup.nodes.Document;

import java.sql.ResultSet;
import java.sql.SQLException;

import static main.Main.dbtpr;
import static main.Main.nctpr;

public class fundDay extends Thread{

    /*
    * 基金日历史数据循环取类
    * */

    //东方财富 天天基金网 通过基金代码得到基金日数据表的链接拼凑
    //http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000
    public final static String fund_day_data_tables_url_before = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=";
    public final static String fund_day_data_tables_url_middle = "&pageIndex=";
    public final static String fund_day_data_tables_url_after  = "&pageSize=2000";
    public final static String fund_day_data_tables_referrerUrl_before = "http://fund.eastmoney.com/f10/jjjz_";
    public final static String fund_day_data_tables_referrerUrl_after = ".htm";

    public void run(){

        dataBaseClass db = new dataBaseClass();
        dataBaseClass db1 = new dataBaseClass();
        ResultSet rs = null;

        String jsonSelectresult = "";
        Document doc;
        String stockFund_code_string;

        String referrerUrl="";
        String url = "";

        int i=0;//页数


        //使用基金代码得到基金日数据
        String queryCode = "SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;";
        dbtpr.new function().addQueryTask(queryCode);
        rs = dbtpr.new function().getQuerytResult(queryCode);

        // rs = db.query("SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;");

        System.out.println("取基金日数据开始了");
        int count=0;

        try {
            while(rs.next()) {
                stockFund_code_string = rs.getString("stockFund_code");
                i=0; //页数置0
                do {
                    ++i;
                    url = new String(fund_day_data_tables_url_before
                            + stockFund_code_string
                            +fund_day_data_tables_url_middle
                            +i
                            +fund_day_data_tables_url_after);
                    referrerUrl = new String(fund_day_data_tables_referrerUrl_before
                            + stockFund_code_string
                            + fund_day_data_tables_referrerUrl_after);

                    //doc = netConnectionClass.JsoupNetConnection(url,referrerUrl);

                    nctpr.new function().addNetConnectionTask(url,referrerUrl);
                    doc =  nctpr.new function().getNetConnectionResult(url);

                    jsonSelectresult = doc.text();
                    new jsonFiltersFundHistoryDay().jsonFiltersFundHistoryDayF(stockFund_code_string,jsonSelectresult);
                    file.txt.logFileWrite(stockFund_code_string+" 页数："
                            +i
                            +"/"
                            +function.littleFunction.TotalCountCalculate(jsonSelectresult));
                }while(i<function.littleFunction.TotalCountCalculate(jsonSelectresult));


                dbtpr.new function().addInsertTask("update stock_fund_code_tables set todayUpdate = 1 where stockFund_code = \""
                        +stockFund_code_string
                        +"\";");
//                db1.insert("update stock_fund_code_tables set todayUpdate = 1 where stockFund_code = \""
//                        +stockFund_code_string
//                        +"\";");
                System.out.println(++count+" 基金代码："+stockFund_code_string+" 完成了");

            }
        } catch (SQLException e) {
            file.txt.logFileWrite(e.toString());
        }

        db.queryCodeClose();
        db1.queryCodeClose();
    }
}
