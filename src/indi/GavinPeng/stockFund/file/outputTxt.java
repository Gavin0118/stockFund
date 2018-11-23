package indi.GavinPeng.stockFund.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class outputTxt {

    // 时间公共变量
    private final static DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static DateFormat dateTimeformatForfileName = new SimpleDateFormat("yyyy-MM-dd");

    //日志文件写入函数 logType 0：正常日志记录 1：Exception
    public static int logFileWrite(String str,int logType) {
        String fileOutputPathAndName;
        try {
            if (logType==0){
                fileOutputPathAndName = "D:\\LOG\\stockFund_" + dateTimeformatForfileName.format(new Date()) + ".log";
            }
            else{
                fileOutputPathAndName = "D:\\LOG\\stockFund_" + dateTimeformatForfileName.format(new Date()) +"_Exception"+ ".log";
            }
            FileOutputStream fos = new FileOutputStream(fileOutputPathAndName, true);// true表示在文件末尾追加
            str = dateTimeformat.format(new Date()) + " " + str + "\r\n";
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
