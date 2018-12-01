package indi.GavinPeng.stockFund.main;

import indi.GavinPeng.stockFund.dataBaseThreadPool.dataBaseThreadPoolThread;
import indi.GavinPeng.stockFund.fundCodeType.fundCodeTypeThread;
import indi.GavinPeng.stockFund.netConnectionThreadPool.netConnectionThreadPoolThread;

public class Main {

    public static int fundCount = 0;
    public static int fundMonthYearDataIsOk = 0;

    public static long threadSleepTime = 10;

    public static dataBaseThreadPoolThread dbtpr = new dataBaseThreadPoolThread();//创建数据库读写线程池(线程)
    public static netConnectionThreadPoolThread nctpr = new netConnectionThreadPoolThread();//创建网络读写线程池(线程)

    public static void main(String[] args) {
        //TODO Auto-generated method stub

        dbtpr.start();//开启数据库读写线程池并初始化
        nctpr.start();//开启网络读写线程池并初始化

        //new fundMonthYearThread().start();//取基金月、年、基金代码

        new fundCodeTypeThread().start();//取基金基金代码、基金类型

//        while(fundMonthYearDataIsOk==0){
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        //new fundCodeCirculateThread().start();//取基金日数据

        try {
            while (true) {
                nctpr.state();
                dbtpr.state();
                System.out.println();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
