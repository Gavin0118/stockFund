package indi.GavinPeng.stockFund.dataBase;

import indi.GavinPeng.stockFund.file.outputTxt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class dataBaseClass {
    private Statement st;
    private Connection con;
    private ResultSet rs;

    dataBaseClass() {
        //String driver = "com.mysql.jdbc.Driver";
        String driver = "com.mysql.cj.jdbc.Driver";
        //jdbc.url=jdbc:mysql://192.168.0.109:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false
        String url = "jdbc:mysql://127.0.0.1:3306/stockfund?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
        String username = "root";
        String password = "545638";
        try {
            Class.forName(driver);// 加载驱动程序，此处运用隐式注册驱动程序的方法
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, username, password);// 创建连接对象
            st = con.createStatement();// 创建sql执行对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ResultSet query(String queryCode) {
        try {
            rs = st.executeQuery(queryCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    boolean insert(String insertCode) {
        boolean b = false;
        try {
            b = st.execute(insertCode);
        } catch (SQLIntegrityConstraintViolationException e) {
            outputTxt.logFileWrite(e.toString() + " " + "重复：" + insertCode,1);
        } catch (SQLException e) {
            outputTxt.logFileWrite(e.toString() + " " + "其他异常：" + insertCode,1);
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
}
