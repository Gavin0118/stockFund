package dataBase;

class insertRunnable implements Runnable {

    private dataBaseClass db = new dataBaseClass();
    private String str;

    insertRunnable(String str) {
        this.str = str;
    }

    public void run() {
        db.insert(str);
    }
}
