package main;

import dataBase.dataBaseThreadPoolRunnable;

public class Main {

	public static dataBaseThreadPoolRunnable dbtpr = new dataBaseThreadPoolRunnable();//创建数据库读写线程池(线程)
	public static void main(String[] args) {
		//TODO Auto-generated method stub

		new fund.fundMonthYear().fundMothYearF();//取基金月数据及基金代码

		new fund.fundDay().fundDayF();//取基金日数据

	}

}
