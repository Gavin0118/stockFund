package Parser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static main.Main.dbtpr;

public class jsonFiltersFundForCodeMonthYear {

    public void jsonFiltersFundForCodeMonthYearF(String str) {

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
        int count=0; //取基金代码临时变量，计数用

        //dataBaseClass db = new dataBaseClass();
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
                    dbtpr.new function().addInsertTask("INSERT INTO stock_fund_Code_tables (stockFund_code,type) values(\""+strtemp2+"\",\"fund\");");
                    //db.insert("INSERT INTO stock_fund_Code_tables (stockFund_code,type) values(\""+strtemp2+"\",\"fund\");");
                }
                sqlmiddle = new String(sqlmiddle +"\""+strtemp2+"\""+",");
                strtemp1 = strtemp1.substring(strtemp1.indexOf(',')+1,strtemp1.length());
            }
            sqlstr = new String(sqlstrBefore+sqlmiddle+"\""+strtemp1+"\");");
            dbtpr.new function().addInsertTask(sqlstr);
            //db.insert(sqlstr);

            file.txt.logFileWrite(++count+" ");
            if(count%50==0)
                file.txt.logFileWrite("\n");
        }
        //db.queryCodeClose();
    }
}
