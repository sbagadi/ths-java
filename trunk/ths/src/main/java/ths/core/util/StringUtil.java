package ths.core.util;

/**
 * 字符串处理的工具类
 */
public class StringUtil {
	/**
	 * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * @param str 要检查的字符串
	 * @return 如果为空, 则返回<code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

}
