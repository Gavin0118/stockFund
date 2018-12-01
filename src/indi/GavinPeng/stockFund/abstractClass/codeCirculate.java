package indi.GavinPeng.stockFund.abstractClass;

import indi.GavinPeng.stockFund.file.outputTxt;
import indi.GavinPeng.stockFund.main.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;
import static indi.GavinPeng.stockFund.main.Main.nctpr;

public abstract class codeCirculate extends Thread {
    /*
     * 基金日历史数据循环取类
     * */
    private ResultSet rs;
    public static int count = 0;//计数，现阶段在内存计算的基金代码的数量

    //使用基金代码得到基金日数据
    private String queryCode = "SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;";

    public void circulate() {
        try {
            while (dbtpr.getQueueSizeBalance() <= 0) {
                Thread.sleep(10);
            }
            dbtpr.new function().addQueryTask(queryCode);
            rs = dbtpr.new function().getQuerytResult(queryCode);
            outputTxt.logFileWrite("取基金日数据开始了", 0);
            while (rs.next()) {
                while (nctpr.getQueueSizeBalance() <= 0 || count >= 5) {
                    Thread.sleep(Main.threadSleepTime);
                }
                netConnection(rs.getString("stockFund_code"));
                count = count + 1; //计数+1
            }
        } catch (SQLException e) {
            outputTxt.logFileWrite(e.toString(), 1);
        } catch (InterruptedException e) {
            outputTxt.logFileWrite(e.toString(), 1);
        }
    }

    public abstract void  netConnection(String str);
}
