package file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import variable.publicVariable;

public class txt {

	private static String fileOutputPathAndName = null;
	//��־�ļ�д�뺯��
	public static int logFileWrite(String str) {
		try {
			fileOutputPathAndName = "logFile.log";
			FileOutputStream fos = new FileOutputStream(fileOutputPathAndName, true);// true��ʾ���ļ�ĩβ׷��
			str = publicVariable.dateTimeformat.format(new Date()) + " " + str + "\r\n";
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
