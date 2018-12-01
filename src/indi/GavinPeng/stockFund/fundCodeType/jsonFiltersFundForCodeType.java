package indi.GavinPeng.stockFund.fundCodeType;

import indi.GavinPeng.stockFund.file.outputTxt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;

class jsonFiltersFundForCodeType {

    void jsonFiltersFundForCodeMonthYearF(String jsonSelectresult,String fundTypeMy) {

        JSONObject jsonObject; //存整个JSON对象
        JSONArray jsonArray_datas ; //存datas数组
        String strtemp1 ;  //临时存datas数组中每条数据
        String strtemp2 ;  //临时存放从strtemp1中取出来的字符串
        String sqlmiddle ;  //临时存放将strtemp2重新处理后的数据
        String sqlstr ;


        jsonObject = JSONObject.fromObject(jsonSelectresult.substring(jsonSelectresult.indexOf('{'), jsonSelectresult.indexOf('}')+1));

        jsonArray_datas = jsonObject.getJSONArray("datas");

        for(int i=0;i<jsonArray_datas.size();i++) {
            strtemp1 = jsonArray_datas.get(i).toString();
            sqlmiddle = "";
            for(int j=0;j<2;j++) {
                strtemp2 = strtemp1.substring(0,strtemp1.indexOf(','));
                sqlmiddle = sqlmiddle +"\""+strtemp2+"\""+",";
                strtemp1 = strtemp1.substring(strtemp1.indexOf(',')+1);
            }
            sqlstr = "call p_insert_fundCode_fundType_tables("+sqlmiddle+"\""+fundTypeMy+"\");";
            dbtpr.new function().addInsertTask(sqlstr);
            ++fundCodeTypeThread.count;
            outputTxt.logFileWrite("月数据基金数据计数 "+fundCodeTypeThread.count+" ",0);
        }
    }
}
