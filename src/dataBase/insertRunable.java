package dataBase;

public class insertRunable implements Runnable {

    dataBaseClass db = new dataBaseClass();
    String st = null;

    public insertRunable(String str){
        st = str;
    }

    public void run(){
        db.insert(st);
    }
}
