package indi.GavinPeng.stockFund.netConnectionThreadPool;

import indi.GavinPeng.stockFund.abstractClass.threadPool;
import org.jsoup.nodes.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class netConnectionThreadPoolThread extends threadPool {

    private DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public netConnectionThreadPoolThread() {
        super(100, 200, 200, TimeUnit.MILLISECONDS, 100);
    }

    public void run() {
        setthreadName("netConnectionThreadPoolThread ");
        new threadPoolFunction().arrayClean();
    }

    @Override
    public void returnExe() {
        for (int i = 0; i < maximumPoolSize; i++) {
            if (tpa[i].TimeoutFlag == 1) {
                tpa[i].TimeoutFlag = 0;
                new function().addNetConnectionTask(tpa[i].url, tpa[i].referrerUrl);
            }
        }
    }

    public class function {
        public void addNetConnectionTask(String url, String referrerUrl) {
            netConnectionRunnable ncr = new netConnectionRunnable(url, referrerUrl);
            try {
                while (executor.getQueue().size() >= queueSize) {
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
                for (int i = 0; i < maximumPoolSize; i++) {
                    if (tpa[i].url.equals(url)) {
                        while (tpa[i].outputValueUpdate == 0) {
                            Thread.currentThread().sleep(1000);
                        }
                        doc = tpa[i].doc;
                        new threadPool.threadPoolFunction().dabRecordRecover(i);
                        break;
                    }
                    if (i == (maximumPoolSize - 1)) {
                        i = -1;
                        Thread.currentThread().sleep(1000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return doc;
        }


        void putUrlAndReferrerUrl(String url, String referrerUrl) {
            try {
                for (int i = 0; i < maximumPoolSize; i++) {
                    if (tpa[i].url.equals("")) {
                        tpa[i].url = url;
                        tpa[i].referrerUrl = referrerUrl;
                        tpa[i].inputValueTime = dateTimeformat.parse(dateTimeformat.format(new Date()));
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        void putNetReturnResult(String url, Document doc) {
            for (int i = 0; i < maximumPoolSize; i++) {
                if (tpa[i].url.equals(url)) {
                    tpa[i].doc = doc;
                    tpa[i].outputValueUpdate = 1;
                    break;
                }
            }

        }
    }
}
