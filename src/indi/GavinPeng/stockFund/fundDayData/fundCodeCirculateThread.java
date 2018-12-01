package indi.GavinPeng.stockFund.fundDayData;

import indi.GavinPeng.stockFund.abstractClass.codeCirculate;

public class fundCodeCirculateThread extends codeCirculate {
    /*
     * 基金日历史数据循环取类
     * */
    public void run() {
        circulate();
    }

    @Override
    public void netConnection(String str) {
        new fundDayNetConnectionThread(str).start();
    }
}
