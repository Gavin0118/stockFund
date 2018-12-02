package indi.GavinPeng.stockFund.fundDayData;

import indi.GavinPeng.stockFund.abstractClass.fundCodeCirculate;

public class fundCodeCirculateDayThread extends fundCodeCirculate {
    /*
     * 基金日历史数据循环取类
     * */
    public fundCodeCirculateDayThread(){
        super("基金日数据开始了",5);

    }

    public void run() {
        circulate();
    }

    @Override
    public void netConnection(String stockFund_code) {
        new fundDayNetConnectionThread(stockFund_code).start();
    }
}
