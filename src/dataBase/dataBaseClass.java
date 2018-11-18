package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class dataBaseClass {
	Statement st;
	Connection con;
	ResultSet rs;

	public dataBaseClass() {
		//String driver = "com.mysql.jdbc.Driver";
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/stockfund";
		String username = "root";
		String password = "545638";
		try {
			Class.forName(driver);// �����������򣬴˴�������ʽע����������ķ���
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, username, password);// �������Ӷ���
			st = con.createStatement();// ����sqlִ�ж���
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String queryCode) {
		try {
			rs = st.executeQuery(queryCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean insert(String insertCode) {
		boolean b = false;
		try {
			b = st.execute(insertCode);
		} catch (SQLIntegrityConstraintViolationException e) {
			file.txt.logFileWrite(e.toString()+" "+"�ظ���"+insertCode);
		}catch (SQLException e) {
			file.txt.logFileWrite(e.toString()+" "+"�����쳣��"+insertCode);
		}
		
		return b;
	}

	public void queryCodeClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void run() {

	}
}
