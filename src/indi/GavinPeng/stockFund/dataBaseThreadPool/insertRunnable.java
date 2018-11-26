package indi.GavinPeng.stockFund.dataBaseThreadPool;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;

class insertRunnable implements Runnable {

    private dataBaseClass db = new dataBaseClass();
    private String str;

    insertRunnable(String str) {
        this.str = str;
    }

    public void run() {
        dbtpr.new function().putQueryOrInsertCode(str,2);//将任务放进数组
        db.insert(str);
        dbtpr.new function().putInsertDown(str);
        db.close();
    }
}
