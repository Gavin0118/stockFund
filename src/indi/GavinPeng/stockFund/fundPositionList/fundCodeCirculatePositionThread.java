package indi.GavinPeng.stockFund.fundPositionList;

import indi.GavinPeng.stockFund.abstractClass.fundCodeCirculate;

public class fundCodeCirculatePositionThread extends fundCodeCirculate {


    public fundCodeCirculatePositionThread() {
        super("基金持仓列表数据开始了",3);
    }

    public void run() {
        circulate();
    }

    @Override
    public void netConnection(String stockFund_code) {
        new fundPositionListNetConThread(stockFund_code).start();
    }
}
