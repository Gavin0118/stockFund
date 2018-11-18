package dataBase;

import java.sql.ResultSet;
import java.util.concurrent.*;

public class dataBaseThreadPool {

    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

    public ResultSet addQueryTask(String queryCode) throws ExecutionException, InterruptedException {
        queryRunable qr = new queryRunable(queryCode);
        try {
            while (executor.getQueue().size() >= 5) {
                Thread.currentThread().sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        executor.execute(qr);
        Future f1 = executor.submit(qr);

        ResultSet rs = (ResultSet)f1.get();

        return rs;
    }

    public void close(){
        executor.shutdown();
    }
}
