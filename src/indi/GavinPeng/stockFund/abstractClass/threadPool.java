package indi.GavinPeng.stockFund.abstractClass;

import org.jsoup.nodes.Document;

import java.sql.ResultSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class threadPool extends Thread {

    protected ThreadPoolExecutor executor; //线程池定义
    protected threadPoolArray[] tpa ;//数组定义
    protected int corePoolSize;
    protected int maximumPoolSize;
    protected int queueSize;
    private String threadName;

    public threadPool(){

    }

    public threadPool(int corePoolSize, int maximumPoolSize, int keepAliveTime,TimeUnit unit,int queueSize) {

        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.queueSize = queueSize;

        tpa = new threadPoolArray[maximumPoolSize];//数组定义

        //数组初始化
        for (int i = 0; i < maximumPoolSize; i++) {
            tpa[i] = new threadPoolArray();
            //数据库相关
            tpa[i].code = "";
            tpa[i].qureyReturnValue = null;
            //网络相关
            tpa[i].url = "";
            tpa[i].referrerUrl = "";
            tpa[i].doc = null;
            //公共
            tpa[i].update = 0;
        }
    }

    public void setthreadName(String threadName){
        this.threadName = threadName;
    }
    //打印线程池状态
    public void state() {
        System.out.println(threadName + "线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
    }

    //线程池中线程数目
    public int getPoolSizeBalance(){
        return corePoolSize - executor.getPoolSize();
    }

    //队列中等待执行的任务数目
    public int getQueueSizeBalance(){
        return queueSize - executor.getQueue().size();
    }

    //已执行完的任务数目
    public long getCompletedTaskCount(){
        return executor.getCompletedTaskCount();
    }


    //关闭线程池
    public void threadPoolRunableClose() {
        executor.shutdown();
    }

    //数组定义
    public class threadPoolArray {
        //数据库线程池使用
        public  String code;// 保存查询语句执行代码 默认"null"
        public  ResultSet qureyReturnValue;//查询返回的结果保存  默认为null

        //网络线程池使用
        public String url;         //网络连接用的URL
        public  String referrerUrl; //网络连接用的referrerUrl
        public  Document doc;       //网络返回值

        //共用
        public  int update; //记录返回值数据是否更新 0：默认 1：已更新
    }


    //数组此条清空,恢复初始状态
    public void dabRecordRecover(int number) {
        //数据库相关
        tpa[number].code = "";
        tpa[number].qureyReturnValue = null;

        //网络相关
        tpa[number].url = "";
        tpa[number].referrerUrl = "";
        tpa[number].doc = null;

        //公共
        tpa[number].update = 0;
    }



}
