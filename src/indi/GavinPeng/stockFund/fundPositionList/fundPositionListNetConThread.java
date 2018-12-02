package indi.GavinPeng.stockFund.fundPositionList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.nodes.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;
import static indi.GavinPeng.stockFund.main.Main.nctpr;

public class fundPositionListNetConThread extends Thread {

    private String stockFund_code;
    private final static DateFormat dateTimeformat = new SimpleDateFormat("yyyy");
    private String year = dateTimeformat.format(new Date());//默认为今年
    private String url;
    private Document doc;

    //&year=
    private String urlBefore = "http://fund.eastmoney.com/f10/FundArchivesDatas.aspx?type=jjcc&topline=10&code=";

    fundPositionListNetConThread(String stockFund_code){
        this.stockFund_code = stockFund_code;

    }

    @Override
    public void run() {
        netconnection();
        String str = doc.getElementsByTag("body").toString(); //提取标签body之间的内容
        String jsonArrayStr = str.substring(str.indexOf("arryear"),str.indexOf("]")+1);//输出所有年份
        System.out.println(stockFund_code+"  "+jsonArrayStr);
        jsonArrayStr = "{"+jsonArrayStr+"}";
        JSONObject jsonObject = JSONObject.fromObject(jsonArrayStr); //整个JSON对象
        JSONArray jsonJSONArray_arryear = jsonObject.getJSONArray("arryear"); //取数组
        for(int i=0;i<jsonJSONArray_arryear.size();i++){
            year = jsonJSONArray_arryear.get(i).toString();
            netconnection();
            new dataFilter().dataFilterThreadF(stockFund_code, doc);
        }
//        int l=-1;
//        do {
//            ++l;
//            url = urlBefore + stockFund_code + "&year=" + year;
//            nctpr.new function().addNetConnectionTask(url, null);
//
//            doc = nctpr.new function().getNetConnectionResult(url);
//            //jsonSelectresult = doc.text();
//            new dataFilter().dataFilterThreadF(stockFund_code, doc);
//
//
//            if(l==0){
//                str = doc.getElementsByTag("body").toString();
//                strtemp1 = str.substring(str.indexOf("arryear"),str.indexOf("]")+1);//输出所有年份
//                System.out.println(stockFund_code+"  "+strtemp1);
//                strtemp1 = "{"+strtemp1+"}";
//                jsonObject = JSONObject.fromObject(strtemp1);
//                jsonJSONArray_arryear = jsonObject.getJSONArray("arryear");
//            }
//            if(l<jsonJSONArray_arryear.size())
//                year = jsonJSONArray_arryear.get(l).toString();
//        }while(l<jsonJSONArray_arryear.size());

        fundCodeCirculatePositionThread.count = fundCodeCirculatePositionThread.count-1; //内存中的数量减少1
        dbtpr.new function().addInsertTask("update stock_fund_code_tables set todayUpdate = 1 where stockFund_code = \""
                + stockFund_code + "\";");
    }

    //
    private void netconnection(){
        url = urlBefore + stockFund_code + "&year=" + year;
        nctpr.new function().addNetConnectionTask(url, null);
        doc = nctpr.new function().getNetConnectionResult(url);
    }
}
