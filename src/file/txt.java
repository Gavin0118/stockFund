package file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class txt {

    // 时间公共变量
    public final static DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static DateFormat dateTimeformatForfileName = new SimpleDateFormat("yyyy-MM-dd");

    //日志文件写入函数
    public static int logFileWrite(String str) {
        String fileOutputPathAndName = null;
        try {
            fileOutputPathAndName = "D:\\LOG\\stockFund_" + dateTimeformatForfileName.format(new Date()) + ".log";
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
