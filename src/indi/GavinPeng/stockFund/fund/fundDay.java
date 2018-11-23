package indi.GavinPeng.stockFund.fund;

import indi.GavinPeng.stockFund.file.outputTxt;

import java.sql.ResultSet;
import java.sql.SQLException;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;
import static indi.GavinPeng.stockFund.main.Main.nctpr;

public class fundDay extends Thread{

    /*
    * 基金日历史数据循环取类
    * */

    public void run(){
        ResultSet rs;

        //使用基金代码得到基金日数据
        String queryCode = "SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;";
        dbtpr.new function().addQueryTask(queryCode);
        rs = dbtpr.new function().getQuerytResult(queryCode);

        System.out.println("取基金日数据开始了");

        try {
            while(rs.next()) {
                while(nctpr.getQueueSizeBalance()==0){
                  Thread.sleep(1000);
                }
                new fundDayThread(rs).start();
            }
        } catch (SQLException e) {
            outputTxt.logFileWrite(e.toString(),1);
        } catch (InterruptedException e) {
            outputTxt.logFileWrite(e.toString(),1);
        }
    }
}
