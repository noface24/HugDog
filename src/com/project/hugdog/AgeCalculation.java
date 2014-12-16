package com.project.hugdog;

import java.util.Calendar;

public class AgeCalculation {
	private int startYear;
	private int startMonth;
	private int startDay;
	private int endYear;
	private int endMonth;
	private int endDay;
	private int resYear;
	private int resMonth;
	private int resDay;

	public String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		endYear = c.get(Calendar.YEAR);
		endMonth = c.get(Calendar.MONTH);
		endMonth++;
		endDay = c.get(Calendar.DAY_OF_MONTH);
		return endDay + ":" + endMonth + ":" + endYear;
	}

	public void setDateOfBirth(int sYear, int sMonth, int sDay) {
		startYear = sYear;
		startMonth = sMonth;
		startMonth++;
		startDay = sDay;

	}

	public int calcualteYear() {
		resYear = endYear - startYear;
		if (endYear < startYear) {
			resYear--;
		}
		Integer ageInt = new Integer(resYear);
		// String ageS = ageInt.toString();

		return resYear;

	}

	public int calcualteMonth() {
		if (endMonth >= startMonth) {
			resMonth = endMonth - startMonth;
		} else {
			resMonth = endMonth - startMonth;
			resMonth = 12 + resMonth;
			resYear--;
		}
		return resMonth;
	}

	public int calcualteDay() {

		if (endDay >= startDay) {
			resDay = endDay - startDay;// ;
		} else {
			resDay = endDay - startDay;
			resDay = 30 + resDay;
			resMonth--;
		}
		return resDay;
	}

	public String getResult() {
		return resYear + " ปี " + resMonth + " เดือน " + resDay + " วั น";
	}

}
