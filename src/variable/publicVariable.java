package variable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class publicVariable {

	// 时间公共变量
	public final static DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//
	public final static int commentTimeForTimeoutWaiting = 2000;
	
	//取基金代码及月度、 年度数据链接
	public final static String fund_month_year_tables_url_before = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&pi=";
	public final static String fund_month_year_tables_url_after = "&pn=1000&dx=1";
	public final static String fund_month_year_tables_referrerUrl = new String("http://fund.eastmoney.com/data/fundranking.html");
	
	//东方财富 天天基金网 通过基金代码得到基金日数据表的链接拼凑
	//http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000
	public final static String fund_day_data_tables_url_before = "http://api.fund.eastmoney.com/f10/lsjz?fundCode=";		
	public final static String fund_day_data_tables_url_middle = "&pageIndex=";
	public final static String fund_day_data_tables_url_after  = "&pageSize=2000";
	public final static String fund_day_data_tables_referrerUrl_before = "http://fund.eastmoney.com/f10/jjjz_";
	public final static String fund_day_data_tables_referrerUrl_after = ".htm";
	
	//
	public final static int cancelFlag=0;
	
	//基金日数据使用数组
	//"http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000";
	public final static String[] api_fund_eastmoney_com = {"Data","ErrCode","ErrMsg","TotalCount","Expansion","PageSize","PageIndex"};
	public final static String[] api_fund_eastmoney_com_Data = {"LSJZList","FundType","SYType","isNewType","Feature"};
	public final static String[] api_fund_eastmoney_com_Data_LSJZList = {"FSRQ", "DWJZ","LJJZ","SDATE","ACTUALSYI","NAVTYPE","JZZZL","SGZT","SHZT","FHFCZ","FHFCBZ","DTYPE","FHSP"};
	
	public static int count=0; //取基金代码临时变量，计数用
	
	//计算基金月、年数据的条数，然后计算月年数据有几页的变量，和function.littleFunction.allRecords对应
	//public static int allRecordsCount =5;
	
	
	
	
	//http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&sd=2017-11-09&ed=2018-11-09&qdii=&tabSubtype=,,,,,&pi=3&pn=50&dx=1
	//String url = "http://hq.sinajs.cn/list=f_519706";
	//String url = "http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18309432006518676861_1541251757118&fundCode=519706&pageIndex=1&pageSize=20&startDate=2018-10-01&endDate=2018-10-31&_=1541251772452";
	//http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000
	//http://fund.eastmoney.com/f10/jjjz_270042.html
	//String url = "http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml";
}
