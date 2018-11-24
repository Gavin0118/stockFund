package indi.GavinPeng.stockFund.dataBaseThreadPool;

import indi.GavinPeng.stockFund.abstractClass.threadPool;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class dataBaseThreadPoolThread extends threadPool {

    public dataBaseThreadPoolThread() {
        super(100, 200, 200, TimeUnit.MILLISECONDS, 100);
    }

    @Override
    public void run() {
        setthreadName("dataBaseThreadPoolThread ");
        new threadPoolFunction().arrayClean();
    }

    @Override
    public void returnExe() {
        for (int i = 0; i < maximumPoolSize; i++) {
            if (tpa[i].TimeoutFlag == 1) {
                tpa[i].TimeoutFlag = 0;
                new function().addQueryTask(tpa[i].code);
            }
        }
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
                    if (tpa[i].code.equals(queryCode)) {
                        while (tpa[i].outputValueUpdate == 0) {
                            Thread.currentThread().sleep(1000);
                        }
                        rs = tpa[i].qureyReturnValue;
                        new threadPool.threadPoolFunction().dabRecordRecover(i);
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

        //将查询数据库任务放上数组
        void putQuerytCode(String QueryOrInsertCode) {
            DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //注意：不能做成静态的和公共的，不稳定
            try {
                for (int i = 0; i < maximumPoolSize; i++) {
                    if (tpa[i].code.equals("")) {
                        tpa[i].code = QueryOrInsertCode;
                        tpa[i].inputValueTime = dateTimeformat.parse(dateTimeformat.format(new Date()));
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //将查询结果放入数组
        void putQuerytResult(String queryCode, ResultSet rs) {
            for (int i = 0; i < maximumPoolSize; i++) {
                if (tpa[i].code.equals(queryCode)) {
                    tpa[i].qureyReturnValue = rs;
                    tpa[i].outputValueUpdate = 1;
                    break;
                }
            }

        }
    }
}
