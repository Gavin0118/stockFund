package indi.GavinPeng.stockFund.abstractClass;

import indi.GavinPeng.stockFund.file.outputTxt;
import org.jsoup.nodes.Document;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class threadPool extends Thread {

    protected ThreadPoolExecutor executor; //线程池定义
    protected threadPoolArray[] tpa;//数组定义
    private int corePoolSize;
    protected int maximumPoolSize;
    protected int queueSize;
    private String threadName;

    public threadPool(int corePoolSize, int maximumPoolSize, int keepAliveTime, TimeUnit unit, int queueSize) {

        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize));
        tpa = new threadPoolArray[maximumPoolSize];//数组定义
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.queueSize = queueSize;

        //数组初始化
        for (int number = 0; number < maximumPoolSize; number++) {
            tpa[number] = new threadPoolArray();
            //数据库相关
            tpa[number].code = "";
            tpa[number].qureyReturnValue = null;
            //网络相关
            tpa[number].url = "";
            tpa[number].referrerUrl = "";
            tpa[number].doc = null;
            //公共
            tpa[number].TimeoutFlag =0;
            tpa[number].TimeoutFlag = 0;
            tpa[number].inputValueTime = null;
        }
    }

    protected void setthreadName(String threadName) {
        this.threadName = threadName;
    }

    //打印线程池状态
    public void state() {
//        String str;
        System.out.println(threadName + "线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());

//        for (int number = 0; number < maximumPoolSize; number++) {
//            str = tpa[number].code + tpa[number].qureyReturnValue + tpa[number].url+tpa[number].referrerUrl+ tpa[number].doc
//            +tpa[number].TimeoutFlag+tpa[number].outputValueUpdate+tpa[number].inputValueTime ;
//            outputTxt.logFileWrite(str,2);
//        }
    }

    //线程池中线程数目
    public int getPoolSizeBalance() {
        return corePoolSize - executor.getPoolSize();
    }

    //队列中等待执行的任务数目
    public int getQueueSizeBalance() {
        return queueSize - executor.getQueue().size();
    }

    //已执行完的任务数目
    public long getCompletedTaskCount() {
        return executor.getCompletedTaskCount();
    }

    //关闭线程池
    public void threadPoolRunableClose() {
        executor.shutdown();
    }

    //超时任务重新调用
    public abstract void returnExe();


    //数组定义
    public class threadPoolArray {
        //数据库线程池使用
        public String code;// 保存查询语句执行代码 默认"null"
        public ResultSet qureyReturnValue;//查询返回的结果保存  默认为null
        //网络线程池使用
        public String url;         //网络连接用的URL
        public String referrerUrl; //网络连接用的referrerUrl
        public Document doc;       //网络返回值
        //共用
        public int TimeoutFlag;//记录输入值是否可被执行线程调用，0：默认值，未超时 1：已超时
        public Date inputValueTime;//记录输入值时间
        public int outputValueUpdate; //记录返回值数据是否更新 0：默认 1：已更新
    }


    public class threadPoolFunction {

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
            tpa[number].TimeoutFlag =0;
            tpa[number].inputValueTime = null;
            tpa[number].outputValueUpdate = 0;
        }

        //清理线程或使用线程再次执行
        public void arrayClean() {
            DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long minutes;
            Date now ;
            while (true) {
                try {
                    for (int i = 0; i < maximumPoolSize; i++) {
                        if (tpa[i].inputValueTime != null) {
                            now = dateTimeformat.parse(dateTimeformat.format(new Date()));
                            minutes = (now.getTime() - tpa[i].inputValueTime.getTime()) / 1000;//得到分钟差值
                            if (minutes > 480) {//大于6分钟，直接清除
                                dabRecordRecover(i);
                                outputTxt.logFileWrite(threadName+" 数组中有任务已经删除",0);
                            } else if (minutes > 120) {//大于2分钟，状态改为可以被执行线程调用
                                tpa[i].TimeoutFlag = 1;
                                returnExe();
                            }
                        }
                    }
                    Thread.sleep(2000);//检查一轮后休息2秒
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (NullPointerException e) {
                    outputTxt.logFileWrite(e.toString(),1);
                }

            }
        }
    }
}
