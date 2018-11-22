package indi.GavinPeng.stockFund.net;

import indi.GavinPeng.stockFund.abstractClass.threadPool;
import org.jsoup.nodes.Document;

import java.util.concurrent.TimeUnit;

public class netConnectionThreadPoolRunnable extends threadPool {

    public netConnectionThreadPoolRunnable() {
        super(100,200,200, TimeUnit.MILLISECONDS,100);
    }

    public void run(){
        setthreadName("netConnectionThreadPoolRunnable ");
    }
    public class function {

        public void addNetConnectionTask(String url,String referrerUrl){
            netConnectionRunnable ncr = new netConnectionRunnable(url,referrerUrl);
            try {
                while (executor.getQueue().size() >= queueSize) {
                    Thread.currentThread().sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            executor.execute(ncr);
        }

        public Document getNetConnectionResult(String url){
            Document doc = null;
            try {
                for (int i = 0; i < maximumPoolSize; i++) {
                    if (tpa[i].url.equals(url)) {
                        while (tpa[i].update==0) {
                            Thread.currentThread().sleep(1000);
                        }
                        doc = tpa[i].doc;
                        dabRecordRecover(i);
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


        void putUrlAndReferrerUrl(String url,String referrerUrl){
            for (int i = 0; i < maximumPoolSize; i++) {
                if (tpa[i].url.equals("")) {
                    tpa[i].url = url;
                    tpa[i].referrerUrl = referrerUrl;
                    break;
                }
            }

        }

        void putNetReturnResult(String url,Document doc){
            for (int i = 0; i < maximumPoolSize; i++) {
                if (tpa[i].url.equals(url)) {
                    tpa[i].doc = doc;
                    tpa[i].update = 1;
                    break;
                }
            }

        }
    }
}
