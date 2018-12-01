package indi.GavinPeng.stockFund.fundPositionList;

import indi.GavinPeng.stockFund.abstractClass.codeCirculate;

public class codeCirculateThread extends codeCirculate {


    public codeCirculateThread() {
        super("基金持仓列表数据开始了");
    }

    public void run() {
        circulate();
    }

    @Override
    public void netConnection(String stockFund_code) {
        new fundPositionListNetConThread(stockFund_code).start();
    }
}
