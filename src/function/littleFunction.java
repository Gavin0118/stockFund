package function;

import net.sf.json.JSONObject;

public class littleFunction {

	public static int search(String str, String strRes) {// �����ַ�������ָ���ַ�����ͬ�ĸ���
		int n = 0;// ������
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

	// ��������¡������ݵ�������Ȼ��������������м�ҳ
	public static int allRecords(String jsonSelectresult) {
		JSONObject jsonObject;
		jsonObject = JSONObject.fromObject(
				jsonSelectresult.substring(jsonSelectresult.indexOf('{'), jsonSelectresult.indexOf('}') + 1));
		return new littleFunction().new function().countPage(jsonObject.getInt("allRecords"), 1000);
	}

	// �������������������Ȼ������������м�ҳ
	public static int TotalCountCalculate(String str) {
		JSONObject jsonObject; // ����JSON����
		jsonObject = JSONObject.fromObject(str);
		return new littleFunction().new function().countPage(jsonObject.getInt("TotalCount"), 2000);
	}

	class function {

		// ���������������ҳ��������������ҳ��
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
