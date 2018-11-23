package indi.GavinPeng.stockFund.function;

import net.sf.json.JSONObject;

public class littleFunction {

	// 查找字符串里与指定字符串相同的个数
	public int search(String str, String strRes) {
		int n = 0;// 计数器

		while (str.indexOf(strRes) != -1) {
			int i = str.indexOf(strRes);
			n++;
			str = str.substring(i + 1);
		}
		return n;
	}

	// 辅助计算基金年月页数、基金日数据页数
	private int countPage(int n, int subNum) {
		int rn;
		if (n % subNum == 0) {
			rn = n / subNum;
		} else {
			rn = n / subNum + 1;
		}
		return rn;
	}

	// 计算基金日数据条数，然后计算日数据有几页
	public int TotalCountCalculate(String str) {
		JSONObject jsonObject; // 整个JSON对象
		jsonObject = JSONObject.fromObject(str);
		return countPage(jsonObject.getInt("TotalCount"), 2000);
	}

	// 计算基金月、年数据的条数，然后计算月年数据有几页
	public int allRecords(String jsonSelectresult) {
		JSONObject jsonObject;
		jsonObject = JSONObject.fromObject(
				jsonSelectresult.substring(jsonSelectresult.indexOf('{'), jsonSelectresult.indexOf('}') + 1));
		return countPage(jsonObject.getInt("allRecords"), 1000);
	}

}
