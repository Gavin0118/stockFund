package indi.GavinPeng.stockFund.dataBase;

import indi.GavinPeng.stockFund.abstractClass.threadPool;

import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class dataBaseThreadPoolRunnable extends threadPool {

    public dataBaseThreadPoolRunnable() {
        super(100,200,200, TimeUnit.MILLISECONDS,100);
    }

    @Override
    public void run() {
        setthreadName("dataBaseThreadPoolRunnable ");
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
                        while (tpa[i].update==0) {
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

        //将查询数据库代码放上数组
        void putQuerytCode(String QueryOrInsertCode) {
            for (int i = 0; i < maximumPoolSize; i++) {
                if (tpa[i].code.equals("")) {
                    tpa[i].code = QueryOrInsertCode;
                    break;
                }
            }
        }

        //将查询结果放入数组
        void putQuerytResult(String queryCode, ResultSet rs) {
            for (int i = 0; i < maximumPoolSize; i++) {
                if (tpa[i].code.equals(queryCode)) {
                    tpa[i].qureyReturnValue = rs;
                    tpa[i].update = 1;
                    break;
                }
            }

        }
    }
}
