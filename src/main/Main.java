package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataBase.dataBaseClass;
import org.jsoup.nodes.Document;

//import org.jsoup.nodes.Document;

import variable.publicVariable;

public class Main {

	public static void main(String[] args) {
		 //TODO Auto-generated method stub
		String jsonSelectresult = "";
		Document doc;
		String referrerUrl="";
		String url = "";
		ResultSet rs = null;
		String stockFund_code_string;

		dataBaseClass db = new dataBaseClass();
		dataBaseClass db1 = new dataBaseClass();
		//db.dataBaseInit();
		//db1.dataBaseInit();
		
		//�õ���������������������
		int i=0;
		
		System.out.println("ȡ������뼰�����������ݿ�ʼ��");
		do {
			++i;
			url = new String(publicVariable.fund_month_year_tables_url_before+i+publicVariable.fund_month_year_tables_url_after);
			doc = net.netConnection.JsoupNetConnection(url,publicVariable.fund_month_year_tables_referrerUrl);
			jsonSelectresult = doc.text();
			Parser.jsonFilters.jsonFiltersFundForCodeMonthYear(jsonSelectresult);
		}while(i<function.littleFunction.allRecords(jsonSelectresult));
		
		System.out.println("ȡ������뼰�����������ݽ�����");
		
		//ʹ�û������õ�����������
		rs = db.query("SELECT stockFund_code FROM stock_fund_code_tables where type=\"fund\" and todayUpdate = 0 ;");
		
		System.out.println("ȡ���������ݿ�ʼ��");
		int count=0;
		
		try {
			while(rs.next()) {
				stockFund_code_string = rs.getString("stockFund_code");
				i=0; //ҳ����0
				do {
					++i;
					url = new String(publicVariable.fund_day_data_tables_url_before 
							+ stockFund_code_string 
							+publicVariable.fund_day_data_tables_url_middle
							+i
							+publicVariable.fund_day_data_tables_url_after);	
					referrerUrl = new String(publicVariable.fund_day_data_tables_referrerUrl_before + stockFund_code_string + publicVariable.fund_day_data_tables_referrerUrl_after);
					doc = net.netConnection.JsoupNetConnection(url,referrerUrl);
					jsonSelectresult = doc.text();
					Parser.jsonFilters.jsonFiltersFundHistoryDay(stockFund_code_string,jsonSelectresult);
					file.txt.logFileWrite(stockFund_code_string+" ҳ����"+i+"/"+function.littleFunction.TotalCountCalculate(jsonSelectresult));
				}while(i<function.littleFunction.TotalCountCalculate(jsonSelectresult));
				
				db1.insert("update stock_fund_code_tables set todayUpdate = 1 where stockFund_code = \""+stockFund_code_string+"\";");
				System.out.println(++count+" ������룺"+stockFund_code_string+" �����");
				
			}
		} catch (SQLException e) {
			file.txt.logFileWrite(e.toString());
		}
	
		db.queryCodeClose();
		db1.queryCodeClose();
	}

}
