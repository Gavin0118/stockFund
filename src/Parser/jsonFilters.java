package Parser;

import dataBase.dataBaseClass;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import variable.publicVariable;

public class jsonFilters {
	
	public static void jsonFiltersFundHistoryDay(String stockFund_code,String str) {
		JSONObject jsonObject; //整个JSON对象
		JSONObject jsonObject_Data; //JSON对象的子对象Data 
		JSONArray jsonArray_Data_LSJZList = null; //Data下数组对象LSJZList
		JSONObject jsonArray_LSJZList_son; 
		jsonObject = JSONObject.fromObject(str);
		
		String sqlstr1 ="";
		String sqlBefore_1 = new String("INSERT INTO fund_details_tables(fund_code,FundType,SYType,isNewType,Feature) VALUES (\""+stockFund_code+"\"");
		String sqlMiddle_1 = "";
		String sqlAfter_1 = ");";
		
		String sqlstr2 ="";
		String sqlBefore_2 = new String("INSERT INTO fund_day_data_tables (fund_code,date,DWJZ,LJJZ,SDATE,ACTUALSYI,NAVTYPE,JZZZL,SGZT,SHZT,FHFCZ,FHFCBZ,DTYPE,FHSP) VALUES (\""+stockFund_code+"\"");
		String sqlMiddle_2 = "";
		String sqlAfter_2 = ");";
		
		dataBaseClass db = new dataBaseClass();
		//db.dataBaseInit();
		
		jsonObject_Data = (JSONObject) jsonObject.get("Data");
		
		//提取与Data平级的其他数据，页面校验数据，不存放于数据库
//		for(int i=1;i<publicVariable.api_fund_eastmoney_com.length;i++) {
//			System.out.print(jsonObject.getString(publicVariable.api_fund_eastmoney_com[i])+"\t");
//		}	
		
		//提取Data下除LSJZList下数组以外的数据存，存放于表 stock_fund_code
		for(int i=1;i<publicVariable.api_fund_eastmoney_com_Data.length;i++) {
			sqlMiddle_1 = new String(sqlMiddle_1+","+"\""+jsonObject_Data.getString(publicVariable.api_fund_eastmoney_com_Data[i])+"\"");
		}
		sqlstr1 = new String(sqlBefore_1+sqlMiddle_1+sqlAfter_1);
		db.insert(sqlstr1);
		
		//提取Data下数组对象LSJZList里面数组数据，存放于表 day_data_tables里面	
		jsonArray_Data_LSJZList = jsonObject_Data.getJSONArray("LSJZList");
		for(int i=0;i<jsonArray_Data_LSJZList.size();i++) {
			jsonArray_LSJZList_son=JSONObject.fromObject(jsonArray_Data_LSJZList.get(i));
			sqlMiddle_2 = new String("");
			for(int j=0;j<publicVariable.api_fund_eastmoney_com_Data_LSJZList.length;j++) {
				//System.out.print(jsonArray_LSJZList_son.getString(publicVariable.api_fund_eastmoney_com_Data_LSJZList[j])+"\t");
				//jsonArray_LSJZList_son.getString(publicVariable.api_fund_eastmoney_com_Data_LSJZList[j]);
				sqlMiddle_2 = new String(sqlMiddle_2+","+"\""+jsonArray_LSJZList_son.getString(publicVariable.api_fund_eastmoney_com_Data_LSJZList[j])+"\"");
			}
			sqlstr2 = new String(sqlBefore_2+sqlMiddle_2+sqlAfter_2);
			db.insert(sqlstr2);
		}	
		db.queryCodeClose();
	}

	public static void jsonFiltersFundForCodeMonthYear(String str) {
		JSONObject jsonObject; //存整个JSON对象
		JSONArray jsonArray_datas = null; //存datas数组
		String strtemp1 = "";  //临时存datas数组中每条数据
		String strtemp2 = "";  //临时存放从strtemp1中取出来的字符串
		String sqlmiddle = "";  //临时存放将strtemp2重新处理后的数据
		String sqlstr = "";
		String sqlstrBefore ="INSERT INTO fund_month_year_net_tables (fund_code,fund_name,fund_name_code,date,DWJZ,NJJZ,RZZL,Nearly_a_week,Nearly_a_month,Nearly_three_month,Nearly_six_month," + 
				"Nearly_a_year,Nearly_two_year,Nearly_three_year,since_this_year,since_the_found,date_fund_found,stranger1,stranger2,stranger3,Service_charge1,stranger4,Service_charge2," + 
				"stranger5,stranger6)VALUES (";
		int n =0 ;//计算字符串中逗号个数
		
		dataBaseClass db = new dataBaseClass();
		//db.dataBaseClass();
		
		jsonObject = JSONObject.fromObject(str.substring(str.indexOf('{'), str.indexOf('}')+1));
		
		jsonArray_datas = jsonObject.getJSONArray("datas");
	
		for(int i=0;i<jsonArray_datas.size();i++) {
			strtemp1 = jsonArray_datas.get(i).toString();
			n=function.littleFunction.search(strtemp1,",");
			sqlmiddle = "";
			for(int j=0;j<n;j++) {
				strtemp2 = strtemp1.substring(0,strtemp1.indexOf(','));
				if(j==0) {
					db.insert("INSERT INTO stock_fund_Code_tables (stockFund_code,type) values(\""+strtemp2+"\",\"fund\");");
				}
				sqlmiddle = new String(sqlmiddle +"\""+strtemp2+"\""+",");
				strtemp1 = strtemp1.substring(strtemp1.indexOf(',')+1,strtemp1.length());
			}
			sqlstr = new String(sqlstrBefore+sqlmiddle+"\""+strtemp1+"\");");
			db.insert(sqlstr);
			
			file.txt.logFileWrite(++publicVariable.count+" ");
			if(publicVariable.count%50==0)
				file.txt.logFileWrite("\n");
		}
		db.queryCodeClose();
	}
}
