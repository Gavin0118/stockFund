package dataBase;

import java.sql.ResultSet;

import static main.Main.dbtpr;

public class queryRunnable implements Runnable {

    dataBaseClass db = new dataBaseClass();
    String str = null;
    ResultSet rs = null;

    public queryRunnable(String str){
        this.str = str;
    }

    public void run(){
        dbtpr.putQuerytOrInsertCode(str);
        rs = db.query(str);
        dbtpr.putQuerytResult(str,rs);
    }

}
