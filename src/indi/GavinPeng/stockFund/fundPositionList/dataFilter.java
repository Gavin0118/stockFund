package indi.GavinPeng.stockFund.fundPositionList;

import indi.GavinPeng.stockFund.file.outputTxt;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;

class dataFilter {

    void dataFilterThreadF(String stockFund_code,Document doc) {
        Element yearData;
        String date; //股票投资明细截止
        Elements quarterData;//存储每个季度的数据
        Elements elementstemp;
        //JSONObject jsonObject; //整个JSON对象
        //JSONArray jsonJSONArray_arryear;//取数组
        Elements contents = doc.getElementsByClass("box");
        //Elements contentstemp = doc.getElementsByTag("body");
        String strtemp;
        String sqlstr;
        //System.out.println(doc.toString());

        for (int i = 0; i < contents.size(); i++) {
            yearData = contents.get(i);
            date = yearData.getElementsByClass("px12").get(0).text();//提取数据截止日期
            //System.out.println("\n"+date);
            quarterData = yearData.getElementsByTag("tr");
            for (int j = 1; j < quarterData.size(); j++) {
                strtemp = "\""+stockFund_code+"\",\""+date+"\"";
                strtemp = strtemp + ",\"" + quarterData.get(j).getElementsByTag("td").get(0).text()+"\""; //序号
                strtemp = strtemp + ",\"" + quarterData.get(j).getElementsByTag("td").get(1).getElementsByTag("a").text()+"\"";//股票代码
                strtemp = strtemp + ",\"" + quarterData.get(j).getElementsByTag("td").get(2).getElementsByTag("a").text()+"\"";//股票名称

                elementstemp = quarterData.get(j).getElementsByClass("tor");

                try {
                    for (int k = elementstemp.size() - 3; k < elementstemp.size(); k++) {
                        strtemp = strtemp + ",\"" + elementstemp.get(k).text() + "\"";//占净值比例 //持股数(万股) //持仓市值(万元)
                    }
                }catch (IndexOutOfBoundsException e){
                    outputTxt.logFileWrite(e.toString()+"边界异常"+stockFund_code,1);
                }
                sqlstr = "insert into fund_position_list_tables(fund_code,date,ranking,stock_code,stock_name,zjzbl,position_count,position_market_value) values("
                        +strtemp+");";
                dbtpr.new function().addInsertTask(sqlstr);
            }
            //System.out.println();
        }
//        String str = contentstemp.toString();
//        String strtemp1;
//        strtemp1 = str.substring(str.indexOf("curyear"),str.indexOf("}"));//输出当前年份
//        //System.out.println(strtemp1+"\n");
//        strtemp1 = str.substring(str.indexOf("arryear"),str.indexOf("]")+1);//输出所有年份
//        //System.out.println(strtemp1+"\n");
//        strtemp1 = "{"+strtemp1+"}";
//        jsonObject = JSONObject.fromObject(strtemp1);
//        jsonJSONArray_arryear = jsonObject.getJSONArray("arryear");
//        for(int l=0;l<jsonJSONArray_arryear.size();l++){
//            //System.out.println(jsonJSONArray_arryear.get(l));
//        }
        //System.out.println('\n');

    }
}
