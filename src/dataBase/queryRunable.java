package dataBase;

import java.sql.ResultSet;

public class queryRunable implements Runnable {

    dataBaseClass db = new dataBaseClass();
    String str = null;
    ResultSet rs = null;

    public queryRunable(String str){
        this.str = str;
    }

    public void run(){
        rs = db.query(str);
    }

    public Object call() throws Exception {
        return rs ;
    }
}
