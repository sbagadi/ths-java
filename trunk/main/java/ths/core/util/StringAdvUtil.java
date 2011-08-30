package ths.core.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAdvUtil {

	/**
	 * 半角转全角的函数
	 * 
	 * 全角空格为12288，半角空格为32 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 * 
	 * @param input
	 * @return
	 */
	public static String toSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}

		return new String(c);
	}

	/**
	 * 全角转半角的函数
	 * 
	 * @param input
	 * @return
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}

		return new String(c);
	}

	/**
	 * 将模板中{$...} 的内容用data中的数据替换
	 * 
	 * @param template
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String composeMessage(String template,
			Map<String, String> data) throws Exception {
		String regex = "\\$\\{(.+?)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(template);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String name = matcher.group(1); // 键名
			String value = data.get(name); // 键值
			if (value == null) {
				value = "";
			} else {
				value = value.replaceAll("\\$", "\\\\\\$");
			}
			matcher.appendReplacement(sb, value);
		}
		matcher.appendTail(sb);

		return sb.toString();
	}
}
