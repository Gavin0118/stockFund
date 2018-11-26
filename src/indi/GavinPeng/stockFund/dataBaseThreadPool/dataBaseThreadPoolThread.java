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
        super(40, 50, 200, TimeUnit.MILLISECONDS, 20);
    }

    @Override
    public void run() {
        setthreadName("dataBaseThreadPoolThread ");
        new threadPoolFunction().arrayClean();
    }

    @Override
    public void returnExe() {
        for (int number = 0; number < maximumPoolSize; number++) {
            if (tpa[number].TimeoutFlag == 1) {
                tpa[number].TimeoutFlag = 0;
                if(tpa[number].type == 1){
                    new function().addQueryTask(tpa[number].code);
                }else if(tpa[number].type == 2){
                    new function().addInsertTask(tpa[number].code);
                }
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
                for (int number = 0; number < maximumPoolSize; number++) {
                    if (tpa[number].code.equals(queryCode)) {
                        while (tpa[number].outputValueUpdate == 0) {
                            Thread.currentThread().sleep(1000);
                        }
                        rs = tpa[number].qureyReturnValue;
                        new threadPool.threadPoolFunction().dabRecordRecover(number);
                        break;
                    }
                    if (number == (maximumPoolSize - 1)) {
                        number = -1;
                        Thread.currentThread().sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rs;
        }

        //将查询数据库任务放上数组
        void putQueryOrInsertCode(String QueryOrInsertCode, int type) {
            DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //注意：不能做成静态的和公共的，不稳定
            try {
                for (int number = 0; number < maximumPoolSize; number++) {
                    if (tpa[number].code.equals("")) {
                        tpa[number].type = type;
                        tpa[number].code = QueryOrInsertCode;
                        tpa[number].inputValueTime = dateTimeformat.parse(dateTimeformat.format(new Date()));
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //将查询结果放入数组
        void putQuerytResult(String queryCode, ResultSet rs) {
            for (int number = 0; number < maximumPoolSize; number++) {
                if (tpa[number].code.equals(queryCode)) {
                    tpa[number].qureyReturnValue = rs;
                    tpa[number].outputValueUpdate = 1;
                    break;
                }
            }

        }

        //将查询结果放入数组
        void putInsertDown(String insertCode) {
            for (int number = 0; number < maximumPoolSize; number++) {
                if (tpa[number].code.equals(insertCode)) {
                    new threadPool.threadPoolFunction().dabRecordRecover(number);
                    break;
                }
            }

        }
    }
}
