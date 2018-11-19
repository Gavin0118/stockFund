package net;

import org.jsoup.nodes.Document;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class netConnectionThreadPoolRunnable extends Thread {

    private int maximumPoolSize = 200;//线程池最大线程限制
    private int queueSize = 100;//等待线程限制
    //线程池定义
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(100, maximumPoolSize, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(queueSize));
    private netConnectionArray[] nca = new netConnectionArray[maximumPoolSize];//数组定义

    @Override
    public void run() {
        //数组初始化
        for (int i = 0; i < maximumPoolSize; i++) {
            nca[i] = new netConnectionArray();
            nca[i].url = "";
            nca[i].referrerUrl = "";
            nca[i].doc = null;
            nca[i].update = 0;
        }

    }

    public void state() {
        System.out.println("netConnectionThreadPoolRunnable  "+"线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
    }

    public class netConnectionArray {
        String url;         //网络连接用的URL
        String referrerUrl; //网络连接用的referrerUrl
        Document doc;       //网络返回值
        int update;         //记录返回值数据是否更新 0：默认 1：已更新
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
                    if (nca[i].url.equals(url)) {
                        while (nca[i].update==0) {
                            Thread.currentThread().sleep(1000);
                        }
                        doc = nca[i].doc;
                        new function().ncaRecordRecover(i);
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


        public void putUrlAndReferrerUrl(String url,String referrerUrl){
            for (int i = 0; i < maximumPoolSize; i++) {
                if (nca[i].url.equals("")) {
                    nca[i].url = url;
                    nca[i].referrerUrl = referrerUrl;
                    break;
                }
            }

        }

        public void putNetReturnResult(String url,Document doc){
            for (int i = 0; i < maximumPoolSize; i++) {
                if (nca[i].url.equals(url)) {
                    nca[i].doc = doc;
                    nca[i].update = 1;
                    break;
                }
            }

        }

        public void ncaRecordRecover(int number){
            nca[number].url = "";
            nca[number].referrerUrl = "";
            nca[number].doc = null;
            nca[number].update = 0;
        }

    }
}
