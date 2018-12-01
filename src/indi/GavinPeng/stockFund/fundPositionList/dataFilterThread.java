package indi.GavinPeng.stockFund.fundPositionList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class dataFilterThread {

    void dataFilterThreadF(Document doc) {
        //System.out.println(doc.toString());
        //JSONObject jsonObject; //整个JSON对象
        Elements contents = doc.getElementsByClass("box");
        String strtemp;
        //Elements contentstemp = doc.getElementsByTag("body");
        //String str = contentstemp.toString();
        //str = str.substring(str.indexOf("curyear"),str.indexOf("}"));
        //System.out.println(str+"\n");
        //jsonObject = JSONObject.fromObject(str);
        //System.out.println(jsonObject.get(1));
        Element yearData;
        String date; //股票投资明细截止
        Elements quarterData;//存储每个季度的数据
        Elements elementstemp;

        for (int i = 0; i < contents.size(); i++) {
            yearData = contents.get(i);
            date = yearData.getElementsByClass("px12").get(0).text();//提取数据截止日期
            System.out.println("\n"+date);

            quarterData = yearData.getElementsByTag("tr");
            for (int j = 1; j < quarterData.size(); j++) {
                strtemp = quarterData.get(j).getElementsByTag("td").get(0).text(); //序号
                strtemp = strtemp + "\t" + quarterData.get(j).getElementsByTag("td").get(1).getElementsByTag("a").text();//股票代码
                strtemp = strtemp + "\t" + quarterData.get(j).getElementsByTag("td").get(2).getElementsByTag("a").text();//股票名称

                elementstemp = quarterData.get(j).getElementsByClass("tor");
                for(int k=elementstemp.size()-3;k<elementstemp.size();k++){
                    strtemp = strtemp + "\t" + elementstemp.get(k).text();//占净值比例/持股数(万股)/持仓市值(万元)
                }
                System.out.println(strtemp);
            }
            System.out.println();
        }

    }
}
