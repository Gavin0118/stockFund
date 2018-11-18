package function;

import net.sf.json.JSONObject;

public class littleFunction {

	public static int search(String str, String strRes) {// 查找字符串里与指定字符串相同的个数
		int n = 0;// 计数器
//      for(int i = 0;i<str.length();i++) {
//
//      }
		while (str.indexOf(strRes) != -1) {
			int i = str.indexOf(strRes);
			n++;
			str = str.substring(i + 1);
		}
		return n;
	}

	// 计算基金月、年数据的条数，然后计算月年数据有几页
	public static int allRecords(String jsonSelectresult) {
		JSONObject jsonObject;
		jsonObject = JSONObject.fromObject(
				jsonSelectresult.substring(jsonSelectresult.indexOf('{'), jsonSelectresult.indexOf('}') + 1));
		return new littleFunction().new function().countPage(jsonObject.getInt("allRecords"), 1000);
	}

	// 计算基金日数据条数，然后计算日数据有几页
	public static int TotalCountCalculate(String str) {
		JSONObject jsonObject; // 整个JSON对象
		jsonObject = JSONObject.fromObject(str);
		return new littleFunction().new function().countPage(jsonObject.getInt("TotalCount"), 2000);
	}

	class function {

		// 辅助计算基金年月页数、基金日数据页数
		public int countPage(int n, int subNum) {
			int rn;
			if (n % subNum == 0) {
				rn = n / subNum;
			} else {
				rn = n / subNum + 1;
			}
			return rn;
		}

	}
}
