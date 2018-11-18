package dataBase;

import java.sql.ResultSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class dataBaseThreadPoolRunnable implements Runnable {

    int maximumPoolSize = 10;
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, maximumPoolSize, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(5));

    dataBaseArray[] dba = new dataBaseArray[maximumPoolSize]; //数组

    @Override
    public void run() {
        //数组初始化
        for (int i = 0; i < maximumPoolSize; i++) {
            dba[i].type = 0;
            dba[i].code = null;
            dba[i].insertReturnValue = false;
            dba[i].qureyReturnValue = null;
        }

    }

    public void addQueryTask(String queryCode) throws ExecutionException, InterruptedException {
        queryRunnable qr = new queryRunnable(queryCode);
        try {
            while (executor.getQueue().size() >= 5) {
                Thread.currentThread().sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        executor.execute(qr);
    }

    public void addInsertTask(String insertCode) throws ExecutionException, InterruptedException {
        insertRunnable ir = new insertRunnable(insertCode);
        try {
            while (executor.getQueue().size() >= 5) {
                Thread.currentThread().sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        executor.execute(ir);
    }

    public ResultSet getQuerytResult(String queryCode) {
        ResultSet rs = null;
        return rs;
    }

    public boolean getInsertResult(String insertCode) {
        return false ;
    }

    public void putQuerytOrInsertCode(String QueryOrInsertCode){

    }

    public void putQuerytResult(String queryCode,ResultSet rs){

    }

    public void putInsertResult(String insertCode,boolean result){

    }

    public void threadPoolRunableClose() {
        executor.shutdown();
    }

    public class dataBaseArray {
        String code;// 保存查询或插入语句执行代码 默认null
        int type; //区分是查询还是插入语句 1：查询 2：插入 0:空
        boolean insertReturnValue; //插入返回的结果保存  默认false
        ResultSet qureyReturnValue;//查询返回的结果保存  默认为null
    }
}
