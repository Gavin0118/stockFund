package indi.GavinPeng.stockFund.fundDayData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import static indi.GavinPeng.stockFund.main.Main.dbtpr;

class jsonFiltersFundHistoryDay {

    //基金日数据使用数组
    //"http://api.fund.eastmoney.com/f10/lsjz?fundCode=270042&pageIndex=1&pageSize=2000";
    private final static String[] api_fund_eastmoney_com = {"Data", "ErrCode", "ErrMsg", "TotalCount", "Expansion", "PageSize", "PageIndex"};
    private final static String[] api_fund_eastmoney_com_Data = {"LSJZList", "FundType", "SYType", "isNewType", "Feature"};
    private final static String[] api_fund_eastmoney_com_Data_LSJZList = {"FSRQ", "DWJZ", "LJJZ", "SDATE", "ACTUALSYI", "NAVTYPE", "JZZZL", "SGZT", "SHZT", "FHFCZ", "FHFCBZ", "DTYPE", "FHSP"};

    void jsonFiltersFundHistoryDayF(String stockFund_code, String str) {
        JSONObject jsonObject; //整个JSON对象
        JSONObject jsonObject_Data; //JSON对象的子对象Data
        JSONArray jsonArray_Data_LSJZList; //Data下数组对象LSJZList
        JSONObject jsonArray_LSJZList_son;
        jsonObject = JSONObject.fromObject(str);

        String sqlstr1;
        String sqlMiddle_1 = "";

        String sqlstr2;
        String sqlMiddle_2;

        jsonObject_Data = (JSONObject) jsonObject.get("Data");

        //提取与Data平级的其他数据，页面校验数据，不存放于数据库
//		for(int i=1;i<publicVariable.api_fund_eastmoney_com.length;i++) {
//			System.out.print(jsonObject.getString(publicVariable.api_fund_eastmoney_com[i])+"\t");
//		}

        //提取Data下除LSJZList下数组以外的数据存，存放于表 stock_fund_code
        for (int i = 1; i < api_fund_eastmoney_com_Data.length; i++) {
            sqlMiddle_1 = sqlMiddle_1 + "," + "\"" + jsonObject_Data.getString(api_fund_eastmoney_com_Data[i]) + "\"";
        }
        sqlstr1 = "call p_insert_fund_details_tables(\"" + stockFund_code + "\"" + sqlMiddle_1 + ");";

//        while (dbtpr.getQueueSizeBalance() <= 0) {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        dbtpr.new function().addInsertTask(sqlstr1);

        //提取Data下数组对象LSJZList里面数组数据，存放于表 day_data_tables里面
        jsonArray_Data_LSJZList = jsonObject_Data.getJSONArray("LSJZList");

        for (int i = 0; i < jsonArray_Data_LSJZList.size(); i++) {
            jsonArray_LSJZList_son = JSONObject.fromObject(jsonArray_Data_LSJZList.get(i));
            sqlMiddle_2 = "";
            for (int j = 0; j < api_fund_eastmoney_com_Data_LSJZList.length; j++) {
                sqlMiddle_2 = sqlMiddle_2 + "," + "\"" + jsonArray_LSJZList_son.getString(api_fund_eastmoney_com_Data_LSJZList[j]) + "\"";
            }
            sqlstr2 = "call p_insert_fund_day_data_tables(\"" + stockFund_code + "\"" + sqlMiddle_2 + ");";

//            while (dbtpr.getQueueSizeBalance() <= 0) {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            dbtpr.new function().addInsertTask(sqlstr2);
        }
    }


}
