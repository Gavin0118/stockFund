package indi.GavinPeng.stockFund.netConnectionThreadPool;

import indi.GavinPeng.stockFund.abstractClass.threadPool;
import org.jsoup.nodes.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class netConnectionThreadPoolThread extends threadPool {

    public netConnectionThreadPoolThread() {
        super(20,30 , 200, TimeUnit.MILLISECONDS, 10);
    }

    public void run() {
        setthreadName("netConnectionThreadPoolThread ");
        new threadPoolFunction().arrayClean();
    }

    @Override
    public void returnExe() {
        for (int number = 0; number < maximumPoolSize; number++) {
            if (tpa[number].TimeoutFlag == 1) {
                tpa[number].TimeoutFlag = 0;
                new function().addNetConnectionTask(tpa[number].url, tpa[number].referrerUrl);
            }
        }
    }

    public class function {
        public void addNetConnectionTask(String url, String referrerUrl) {
            netConnectionRunnable ncr = new netConnectionRunnable(url, referrerUrl);
            try {
                while (getQueueSizeBalance()<=0) {
                    Thread.currentThread().sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.execute(ncr);
        }

        public Document getNetConnectionResult(String url) {
            Document doc = null;
            try {
                for (int number = 0; number < maximumPoolSize; number++) {
                    if (tpa[number].url.equals(url)) {
                        while (tpa[number].outputValueUpdate == 0) {
                            Thread.currentThread().sleep(1000);
                        }
                        doc = tpa[number].doc;
                        new threadPool.threadPoolFunction().dabRecordRecover(number);
                        break;
                    }
                    if (number == (maximumPoolSize - 1)) {
                        number = -1;
                        Thread.currentThread().sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return doc;
        }


        void putUrlAndReferrerUrl(String url, String referrerUrl) {
            DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //注意：不能做成静态的和公共的，不稳定
            try {
                for (int number = 0; number < maximumPoolSize; number++) {
                    if (tpa[number].url.equals("")) {
                        tpa[number].url = url;
                        tpa[number].referrerUrl = referrerUrl;
                        tpa[number].inputValueTime = dateTimeformat.parse(dateTimeformat.format(new Date()));
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        void putNetReturnResult(String url, Document doc) {
            for (int number = 0; number < maximumPoolSize; number++) {
                if (tpa[number].url.equals(url)) {
                    tpa[number].doc = doc;
                    tpa[number].outputValueUpdate = 1;
                    break;
                }
            }

        }
    }
}
