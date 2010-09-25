package ths.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式输出的辅助类
 * 
 * @author sulinchong.pt
 * 
 */
public class DateUtil {

	/**
	 * 将Date类型的时间转换为以"yyyy-MM-dd HH:mm:ss.SSS"为格式的字符串形式。
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return ymdhmsFormat.format(date);
	}

	/**
	 * 将以"yyyy-MM-dd HH:mm:ss"为格式的字符串转换为Date类型的时间。
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String str) throws ParseException {
		DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ymdhmsFormat.parse(str);
	}

	/**
	 * 将格式为yyyy-MM-dd的字符串转换为Date类型。
	 * 
	 * @param dateStr 格式为yyyy-MM-dd的字符串
	 * @return 对应字符串格式的Date类型
	 * @author fengsheng
	 * @throws ParseException 字符串格式出错
	 */
	public static Date strToShortDate(String dateStr) throws ParseException {
		DateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
		return ymdFormat.parse(dateStr);
	}

}
