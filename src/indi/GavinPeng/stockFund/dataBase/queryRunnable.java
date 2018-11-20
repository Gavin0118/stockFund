package indi.GavinPeng.stockFund.dataBase;

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
        dbtpr.new function().putQuerytCode(str);
        rs = db.query(str);
        dbtpr.new function().putQuerytResult(str,rs);
    }

}
