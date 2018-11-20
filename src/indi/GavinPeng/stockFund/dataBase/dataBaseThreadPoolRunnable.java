package indi.GavinPeng.stockFund.dataBase;

import java.sql.ResultSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class dataBaseThreadPoolRunnable extends Thread {

    private int maximumPoolSize = 200;//线程池最大线程限制
    private int queueSize = 100;//等待线程限制
    //线程池定义
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(100, maximumPoolSize, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(queueSize));
    private dataBaseArray[] dba = new dataBaseArray[maximumPoolSize];//数组定义

    @Override
    public void run() {
        //数组初始化
        for (int i = 0; i < maximumPoolSize; i++) {
            dba[i] = new dataBaseArray();
            dba[i].code = "";
            dba[i].qureyReturnValue = null;
            dba[i].update = 0;
        }
    }

    //打印线程池状态
    public void state() {
        System.out.println("dataBaseThreadPoolRunnable  "+"线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
    }

    //关闭线程池
    public void threadPoolRunableClose() {
        executor.shutdown();
    }

    //数组定义
    public class dataBaseArray {
        String code;// 保存查询语句执行代码 默认"null"
        ResultSet qureyReturnValue;//查询返回的结果保存  默认为null
        int update; //记录返回值数据是否更新 0：默认 1：已更新
    }


    public class function {

        //增加查询任务
        public void addQueryTask(String queryCode) {
            queryRunnable qr = new queryRunnable(queryCode);
            try {
                while (executor.getQueue().size() >= queueSize) {
                    Thread.currentThread().sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            executor.execute(qr);
        }

        //增加插入任务
        public void addInsertTask(String insertCode) {
            insertRunnable ir = new insertRunnable(insertCode);
            try {
                while (executor.getQueue().size() >= queueSize) {
                    Thread.currentThread().sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            executor.execute(ir);
        }

        //获取查询结果
        public ResultSet getQuerytResult(String queryCode) {
            ResultSet rs = null;
            try {
                for (int i = 0; i < maximumPoolSize; i++) {
                    if (dba[i].code.equals(queryCode)) {
                        while (dba[i].update==0) {
                            Thread.currentThread().sleep(1000);
                        }
                        rs = dba[i].qureyReturnValue;
                        new function().dabRecordRecover(i);
                        break;
                    }
                    if (i == (maximumPoolSize - 1)) {
                        i = -1;
                        Thread.currentThread().sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rs;
        }

        //将查询数据库代码放上数组
        void putQuerytCode(String QueryOrInsertCode) {
            for (int i = 0; i < maximumPoolSize; i++) {
                if (dba[i].code.equals("")) {
                    dba[i].code = QueryOrInsertCode;
                    break;
                }
            }
        }

        //将查询结果放入数组
        void putQuerytResult(String queryCode, ResultSet rs) {
            for (int i = 0; i < maximumPoolSize; i++) {
                if (dba[i].code.equals(queryCode)) {
                    dba[i].qureyReturnValue = rs;
                    dba[i].update = 1;
                    break;
                }
            }

        }

        //数组此条清空,恢复初始状态
        void dabRecordRecover(int number) {
            dba[number].code = "";
            dba[number].qureyReturnValue = null;
            dba[number].update = 0;
        }
    }

}
