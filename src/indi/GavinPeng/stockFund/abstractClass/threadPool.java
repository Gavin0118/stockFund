package indi.GavinPeng.stockFund.abstractClass;

import java.sql.ResultSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class threadPool extends Thread {

    private ThreadPoolExecutor executor; //线程池定义
    private threadPoolArray[] tpa ;//数组定义
    private int maximumPoolSize;

    threadPool(){

    }

    threadPool(int corePoolSize, int maximumPoolSize, int keepAliveTime,TimeUnit unit,int queueSize) {

        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
        this.maximumPoolSize = maximumPoolSize;

        tpa = new threadPoolArray[maximumPoolSize];//数组定义

        //数组初始化
        for (int i = 0; i < maximumPoolSize; i++) {
            tpa[i] = new threadPoolArray();
            tpa[i].code = "";
            tpa[i].qureyReturnValue = null;
            tpa[i].update = 0;
        }
    }

    //打印线程池状态
    public void state() {
        System.out.println("dataBaseThreadPoolRunnable  " + "线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
    }

    //关闭线程池
    public void threadPoolRunableClose() {
        executor.shutdown();
    }

    //数组定义
    public class threadPoolArray {
        String code;// 保存查询语句执行代码 默认"null"
        ResultSet qureyReturnValue;//查询返回的结果保存  默认为null
        int update; //记录返回值数据是否更新 0：默认 1：已更新
    }

    public class function {

        //数组此条清空,恢复初始状态
        void dabRecordRecover(int number) {
            tpa[number].code = "";
            tpa[number].qureyReturnValue = null;
            tpa[number].update = 0;
        }
    }


}
