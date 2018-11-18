package main;

import dataBase.dataBaseThreadPoolRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static dataBaseThreadPoolRunnable dbtpr = new dataBaseThreadPoolRunnable();//创建数据库读写线程池(线程)

    public static void main(String[] args) {
        //TODO Auto-generated method stub

        dbtpr.start();//开启数据库读写线程池并初始化

        ResultSet rs;
        dbtpr.new function().addQueryTask("SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;");
        rs = dbtpr.new function().getQuerytResult("SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;");

        try {
            rs.next();
            System.out.println(rs.getString("stockFund_code"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbtpr.new function().addInsertTask("INSERT INTO stock_fund_Code_tables (stockFund_code,type) values(\"0000007777\",\"test\");");


        try {
            while (true) {
                dbtpr.state();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //new fund.fundMonthYear().fundMothYearF();//取基金月数据及基金代码

        //new fund.fundDay().fundDayF();//取基金日数据

    }

}
