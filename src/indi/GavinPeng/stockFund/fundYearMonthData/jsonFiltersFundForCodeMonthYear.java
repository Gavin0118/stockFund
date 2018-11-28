package indi.GavinPeng.stockFund.fundYearMonthData;

import indi.GavinPeng.stockFund.file.outputTxt;
import indi.GavinPeng.stockFund.function.littleFunction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;

public class jsonFiltersFundForCodeMonthYear {

    public void jsonFiltersFundForCodeMonthYearF(String str) {

        JSONObject jsonObject; //存整个JSON对象
        JSONArray jsonArray_datas ; //存datas数组
        String strtemp1 ;  //临时存datas数组中每条数据
        String strtemp2 ;  //临时存放从strtemp1中取出来的字符串
        String sqlmiddle ;  //临时存放将strtemp2重新处理后的数据
        String sqlstr ;
        int n ;//计算字符串中逗号个数
        int count=0; //取基金代码临时变量，计数用

        jsonObject = JSONObject.fromObject(str.substring(str.indexOf('{'), str.indexOf('}')+1));

        jsonArray_datas = jsonObject.getJSONArray("datas");

        for(int i=0;i<jsonArray_datas.size();i++) {
            strtemp1 = jsonArray_datas.get(i).toString();
            n= new littleFunction().search(strtemp1,",");
            sqlmiddle = "";
            for(int j=0;j<n;j++) {
                strtemp2 = strtemp1.substring(0,strtemp1.indexOf(','));
//                if(j==0) {
//                    dbtpr.new function().addInsertTask("INSERT INTO stock_fund_Code_tables (stockFund_code,type) values(\""+strtemp2+"\",\"fundDayData\");");
//                }
                sqlmiddle = sqlmiddle +"\""+strtemp2+"\""+",";
                strtemp1 = strtemp1.substring(strtemp1.indexOf(',')+1);
            }
            sqlstr = "call p_insert_fund_month_year_net_tables("+sqlmiddle+"\""+strtemp1+"\");";
            while (dbtpr.getQueueSizeBalance() > 0) {
                dbtpr.new function().addInsertTask(sqlstr);
            }
            ++count;
            outputTxt.logFileWrite("月数据基金数据计数 "+count+" ",0);
        }
    }
}
