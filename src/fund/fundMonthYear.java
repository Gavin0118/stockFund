package fund;

import org.jsoup.nodes.Document;

public class fundMonthYear {

    //取基金代码及月度、 年度数据链接
    public final static String fund_month_year_tables_url_before = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&pi=";
    public final static String fund_month_year_tables_url_after = "&pn=1000&dx=1";
    public final static String fund_month_year_tables_referrerUrl = new String("http://fund.eastmoney.com/data/fundranking.html");

    public void fundMothYearF(){
        String jsonSelectresult = "";
        Document doc;
        String url = "";

        int i=0; //页数

        System.out.println("取基金代码及基金年月数据开始了");
        do {
            ++i;
            url = new String(fund_month_year_tables_url_before+i+fund_month_year_tables_url_after);
            doc = net.netConnection.JsoupNetConnection(url,fund_month_year_tables_referrerUrl);
            jsonSelectresult = doc.text();
            new Parser.jsonFiltersFundForCodeMonthYear().jsonFiltersFundForCodeMonthYearF(jsonSelectresult);
        }while(i<function.littleFunction.allRecords(jsonSelectresult));
        System.out.println("取基金代码及基金年月数据结束了");
    }

}
