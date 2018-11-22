package indi.GavinPeng.stockFund.main;

import indi.GavinPeng.stockFund.dataBase.dataBaseThreadPoolRunnable;
import indi.GavinPeng.stockFund.fund.fundDay;
import indi.GavinPeng.stockFund.fund.fundMonthYear;
import indi.GavinPeng.stockFund.net.netConnectionThreadPoolRunnable;

public class Main {

    public static int fundCount = 0;

    public static dataBaseThreadPoolRunnable dbtpr = new dataBaseThreadPoolRunnable();//创建数据库读写线程池(线程)
    public static netConnectionThreadPoolRunnable nctpr = new netConnectionThreadPoolRunnable();//创建网络读写线程池(线程)

    public static void main(String[] args) {
        //TODO Auto-generated method stub

        dbtpr.start();//开启数据库读写线程池并初始化
        nctpr.start();//开启网络读写线程池并初始化

        new fundMonthYear().start();//取基金月数据及基金代码

        new fundDay().start();//取基金日数据

        try {
            while (true) {
                System.out.println();
                dbtpr.state();
                nctpr.state();
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
