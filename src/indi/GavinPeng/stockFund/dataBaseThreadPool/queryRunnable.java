package indi.GavinPeng.stockFund.dataBaseThreadPool;

import java.sql.ResultSet;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;

class queryRunnable implements Runnable {

    private dataBaseClass db = new dataBaseClass();
    private String str;
    private ResultSet rs = null;

    queryRunnable(String str){
        this.str = str;
    }

    public void run(){
        dbtpr.new function().putQueryOrInsertCode(str,1);//将任务放进数组
        rs = db.query(str);
        dbtpr.new function().putQuerytResult(str,rs);//将结果放进数据
        //db.close();
    }

}
