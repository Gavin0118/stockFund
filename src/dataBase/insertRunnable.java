package dataBase;

import static main.Main.dbtpr;

public class insertRunnable implements Runnable {

    dataBaseClass db = new dataBaseClass();
    String str = null;
    boolean result = false;

    public insertRunnable(String str){
        this.str = str;
    }

    public void run(){
        dbtpr.putQuerytOrInsertCode(str);
        result = db.insert(str);
        dbtpr.putInsertResult(str,result);
    }
}
