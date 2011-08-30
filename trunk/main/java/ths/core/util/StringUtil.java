package ths.core.util;

import static ths.core.lang.BasicConstant.*;
import static ths.core.util.ArrayUtil.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 字符串处理的工具类
 */
public class StringUtil {
	/**
	 * 取得字符串的长度。
	 * 
	 * @param str
	 *            要取长度的字符串
	 * @return 如果字符串为<code>null</code>，则返回<code>0</code>。否则返回字符串的长度。
	 */
	public static int getLength(String str) {
		return str == null ? 0 : str.length();
	}

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
	 * @param str
	 *            要检查的字符串
	 * @return 如果为空, 则返回<code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank("")        = true
	 * StringUtil.isBlank(" ")       = true
	 * StringUtil.isBlank("bob")     = false
	 * StringUtil.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return 如果为空白, 则返回<code>true</code>
	 */
	public static boolean isBlank(String str) {
		int length;

		if (str == null || (length = str.length()) == 0) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 如果字符串是<code>null</code>或空字符串<code>""</code>，则返回指定默认字符串，否则返回字符串本身。
	 * 
	 * <pre>
	 * StringUtil.defaultIfEmpty(null, "default")  = "default"
	 * StringUtil.defaultIfEmpty("", "default")    = "default"
	 * StringUtil.defaultIfEmpty("  ", "default")  = "  "
	 * StringUtil.defaultIfEmpty("bat", "default") = "bat"
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	/**
	 * 如果字符串是<code>null</code>或空字符串<code>""</code>，则返回指定默认字符串，否则返回字符串本身。
	 * 
	 * <pre>
	 * StringUtil.defaultIfBlank(null, "default")  = "default"
	 * StringUtil.defaultIfBlank("", "default")    = "default"
	 * StringUtil.defaultIfBlank("  ", "default")  = "default"
	 * StringUtil.defaultIfBlank("bat", "default") = "bat"
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串<code>""</code>，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.trimToNull(null)          = null
	 * StringUtil.trimToNull("")            = null
	 * StringUtil.trimToNull("     ")       = null
	 * StringUtil.trimToNull("abc")         = "abc"
	 * StringUtil.trimToNull("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str) {
		if (str == null) {
			return null;
		}

		String result = str.trim();

		if (result.length() == 0) {
			return null;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，则返回空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.trimToEmpty(null)          = ""
	 * StringUtil.trimToEmpty("")            = ""
	 * StringUtil.trimToEmpty("     ")       = ""
	 * StringUtil.trimToEmpty("abc")         = "abc"
	 * StringUtil.trimToEmpty("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str) {
		if (str == null) {
			return EMPTY_STRING;
		}

		return str.trim();
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.trim(null)          = null
	 * StringUtil.trim("")            = ""
	 * StringUtil.trim("     ")       = ""
	 * StringUtil.trim("abc")         = "abc"
	 * StringUtil.trim("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 除去字符串头尾部的空白
	 * 
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果字符串是<code>null</code>，则返回<code>null</code>。
	 */
	public static String trimWhitespace(String str) {
		return trim(str, null, 0);
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = null
	 * StringUtil.trim("", *)            = ""
	 * StringUtil.trim("abc", null)      = "abc"
	 * StringUtil.trim("  abc", null)    = "abc"
	 * StringUtil.trim("abc  ", null)    = "abc"
	 * StringUtil.trim(" abc ", null)    = "abc"
	 * StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String trim(String str, String stripChars) {
		return trim(str, stripChars, 0);
	}

	/**
	 * 除去字符串头部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * StringUtil.leftTrim(null)         = null
	 * StringUtil.leftTrim("")           = ""
	 * StringUtil.leftTrim("abc")        = "abc"
	 * StringUtil.leftTrim("  abc")      = "abc"
	 * StringUtil.leftTrim("abc  ")      = "abc  "
	 * StringUtil.leftTrim(" abc ")      = "abc "
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>则返回<code>null</code>
	 */
	public static String leftTrim(String str) {
		return trim(str, null, -1);
	}

	/**
	 * 除去字符串头部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.leftTrim(null, *)          = null
	 * StringUtil.leftTrim("", *)            = ""
	 * StringUtil.leftTrim("abc", "")        = "abc"
	 * StringUtil.leftTrim("abc", null)      = "abc"
	 * StringUtil.leftTrim("  abc", null)    = "abc"
	 * StringUtil.leftTrim("abc  ", null)    = "abc  "
	 * StringUtil.leftTrim(" abc ", null)    = "abc "
	 * StringUtil.leftTrim("yxabc  ", "xyz") = "abc  "
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String leftTrim(String str, String stripChars) {
		return trim(str, stripChars, -1);
	}

	/**
	 * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * StringUtil.rightTrim(null)       = null
	 * StringUtil.rightTrim("")         = ""
	 * StringUtil.rightTrim("abc")      = "abc"
	 * StringUtil.rightTrim("  abc")    = "  abc"
	 * StringUtil.rightTrim("abc  ")    = "abc"
	 * StringUtil.rightTrim(" abc ")    = " abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String rightTrim(String str) {
		return trim(str, null, 1);
	}

	/**
	 * 除去字符串尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.rightTrim(null, *)          = null
	 * StringUtil.rightTrim("", *)            = ""
	 * StringUtil.rightTrim("abc", "")        = "abc"
	 * StringUtil.rightTrim("abc", null)      = "abc"
	 * StringUtil.rightTrim("  abc", null)    = "  abc"
	 * StringUtil.rightTrim("abc  ", null)    = "abc"
	 * StringUtil.rightTrim(" abc ", null)    = " abc"
	 * StringUtil.rightTrim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String rightTrim(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}

	/**
	 * 除去字符串头尾部的空白，如果结果字符串是空字符串<code>""</code>，则返回<code>null</code>。
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = null
	 * StringUtil.trim("", *)            = null
	 * StringUtil.trim("abc", null)      = "abc"
	 * StringUtil.trim("  abc", null)    = "abc"
	 * StringUtil.trim("abc  ", null)    = "abc"
	 * StringUtil.trim(" abc ", null)    = "abc"
	 * StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
	 */
	public static String trimToNull(String str, String stripChars) {
		String result = trim(str, stripChars);

		if (result == null || result.length() == 0) {
			return null;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的空白，如果字符串是<code>null</code>，则返回空字符串<code>""</code>。
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = ""
	 * StringUtil.trim("", *)            = ""
	 * StringUtil.trim("abc", null)      = "abc"
	 * StringUtil.trim("  abc", null)    = "abc"
	 * StringUtil.trim("abc  ", null)    = "abc"
	 * StringUtil.trim(" abc ", null)    = "abc"
	 * StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str, String stripChars) {
		String result = trim(str, stripChars);

		if (result == null) {
			return EMPTY_STRING;
		}

		return result;
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是<code>null</code>，依然返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = null
	 * StringUtil.trim("", *)            = ""
	 * StringUtil.trim("abc", null)      = "abc"
	 * StringUtil.trim("  abc", null)    = "abc"
	 * StringUtil.trim("abc  ", null)    = "abc"
	 * StringUtil.trim(" abc ", null)    = "abc"
	 * StringUtil.trim("  abcyx", "xyz") = "  abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为<code>null</code>表示除去空白字符
	 * @param mode
	 *            <code>-1</code>表示trimStart，<code>0</code>表示trim全部，
	 *            <code>1</code>表示trimEnd
	 * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
	 */
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while (start < end && Character.isWhitespace(str.charAt(start))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while (start < end
						&& stripChars.indexOf(str.charAt(start)) != -1) {
					start++;
				}
			}
		}

		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null) {
				while (start < end
						&& Character.isWhitespace(str.charAt(end - 1))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while (start < end
						&& stripChars.indexOf(str.charAt(end - 1)) != -1) {
					end--;
				}
			}
		}

		if (start > 0 || end < length) {
			return str.substring(start, end);
		}

		return str;
	}

	/**
	 * 将字符串的首字符转成大写（<code>Character.toTitleCase</code>），其它字符不变。
	 * <p>
	 * 如果字符串是<code>null</code>则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.firstToUpCase(null)  = null
	 * StringUtil.firstToUpCase("")    = ""
	 * StringUtil.firstToUpCase("cat") = "Cat"
	 * StringUtil.firstToUpCase("cAt") = "CAt"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 首字符为大写的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String firstToUpCase(String str) {
		int length;

		if (str == null || (length = str.length()) == 0) {
			return str;
		}

		return new StringBuilder(length).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 将字符串的首字符转成小写，其它字符不变。
	 * <p>
	 * 如果字符串是<code>null</code>则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.firstToLowCase(null)  = null
	 * StringUtil.firstToLowCase("")    = ""
	 * StringUtil.firstToLowCase("Cat") = "cat"
	 * StringUtil.firstToLowCase("CAT") = "CAT"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 首字符为小写的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String firstToLowCase(String str) {
		int length;

		if (str == null || (length = str.length()) == 0) {
			return str;
		}

		if (length > 1 && Character.isUpperCase(str.charAt(1))
				&& Character.isUpperCase(str.charAt(0))) {
			return str;
		}

		return new StringBuilder(length).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 反转字符串的大小写。
	 * <p>
	 * 如果字符串是<code>null</code>则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.swapCase(null)                 = null
	 * StringUtil.swapCase("")                   = ""
	 * StringUtil.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 大小写被反转的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String swapCase(String str) {
		int length;

		if (str == null || (length = str.length()) == 0) {
			return str;
		}

		StringBuilder buffer = new StringBuilder(length);
		char ch = 0;

		for (int i = 0; i < length; i++) {
			ch = str.charAt(i);

			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}

			buffer.append(ch);
		}

		return buffer.toString();
	}

	// ==========================================================================
	// 字符串分割函数。
	//
	// 将字符串按指定分隔符分割。
	// ==========================================================================

	/**
	 * 将字符串按指定字符分割。
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.split(null, *)         = null
	 * StringUtil.split("", *)           = []
	 * StringUtil.split("a.b.c", '.')    = ["a", "b", "c"]
	 * StringUtil.split("a..b.c", '.')   = ["a", "b", "c"]
	 * StringUtil.split("a:b:c", '.')    = ["a:b:c"]
	 * StringUtil.split("a b c", ' ')    = ["a", "b", "c"]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param separatorChar
	 *            分隔符
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String[] split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return new String[0];
		}

		List<String> list = new LinkedList<String>();
		int i = 0;
		int start = 0;
		boolean match = false;

		while (i < length) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}

				start = ++i;
				continue;
			}

			match = true;
			i++;
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * 将字符串按指定字符分割。
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.split(null, *)                = null
	 * StringUtil.split("", *)                  = []
	 * StringUtil.split("abc def", null)        = ["abc", "def"]
	 * StringUtil.split("abc def", " ")         = ["abc", "def"]
	 * StringUtil.split("abc  def", " ")        = ["abc", "def"]
	 * StringUtil.split(" ab:  cd::ef  ", ":")  = ["ab", "cd", "ef"]
	 * StringUtil.split("abc.def", "")          = ["abc.def"]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param separatorChars
	 *            分隔符
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String[] split(String str, String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * 将字符串按指定字符分割。
	 * <p>
	 * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为<code>null</code>，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.split(null, *, *)                 = null
	 * StringUtil.split("", *, *)                   = []
	 * StringUtil.split("ab cd ef", null, 0)        = ["ab", "cd", "ef"]
	 * StringUtil.split("  ab   cd ef  ", null, 0)  = ["ab", "cd", "ef"]
	 * StringUtil.split("ab:cd::ef", ":", 0)        = ["ab", "cd", "ef"]
	 * StringUtil.split("ab:cd:ef", ":", 2)         = ["ab", "cdef"]
	 * StringUtil.split("abc.def", "", 2)           = ["abc.def"]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param separatorChars
	 *            分隔符
	 * @param max
	 *            返回的数组的最大个数，如果小于等于0，则表示无限制
	 * @return 分割后的字符串数组，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String[] split(String str, String separatorChars, int max) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return new String[0];
		}

		List<String> list = new LinkedList<String>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;

		if (separatorChars == null) {
			// null表示使用空白作为分隔符
			while (i < length) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// 优化分隔符长度为1的情形
			char sep = separatorChars.charAt(0);

			while (i < length) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else {
			// 一般情形
			while (i < length) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return list.toArray(new String[list.size()]);
	}

	// ==========================================================================
	// 字符串连接函数。
	//
	// 将多个对象按指定分隔符连接成字符串。
	// ==========================================================================

	/**
	 * 将数组中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * StringUtil.join(null, *)                = null
	 * StringUtil.join([], *)                  = ""
	 * StringUtil.join([null], *)              = ""
	 * StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtil.join(["a", "b", "c"], null)  = "abc"
	 * StringUtil.join(["a", "b", "c"], "")    = "abc"
	 * StringUtil.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 * 
	 * @param array
	 *            要连接的数组
	 * @param separator
	 *            分隔符
	 * @return 连接后的字符串，如果原数组为<code>null</code>，则返回<code>null</code>
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}

		if (separator == null) {
			separator = EMPTY_STRING;
		}

		int arraySize = array.length;
		int bufSize;

		if (arraySize == 0) {
			bufSize = 0;
		} else {
			int firstLength = array[0] == null ? 16
					: array[0].toString().length();
			bufSize = arraySize * (firstLength + separator.length());
		}

		StringBuilder buf = new StringBuilder(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (separator != null && i > 0) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/**
	 * 将<code>Iterator</code>中的元素连接成一个字符串。
	 * 
	 * <pre>
	 * StringUtil.join(null, *)                = null
	 * StringUtil.join([], *)                  = ""
	 * StringUtil.join([null], *)              = ""
	 * StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtil.join(["a", "b", "c"], null)  = "abc"
	 * StringUtil.join(["a", "b", "c"], "")    = "abc"
	 * StringUtil.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 * 
	 * @param iterator
	 *            要连接的<code>Iterator</code>
	 * @param separator
	 *            分隔符
	 * @return 连接后的字符串，如果原数组为<code>null</code>，则返回<code>null</code>
	 */
	public static String join(Iterable<?> list, String separator) {
		if (list == null) {
			return null;
		}

		StringBuilder buf = new StringBuilder(256); // Java默认值是16, 可能偏小

		for (Iterator<?> i = list.iterator(); i.hasNext();) {
			Object obj = i.next();

			if (obj != null) {
				buf.append(obj);
			}

			if (separator != null && i.hasNext()) {
				buf.append(separator);
			}
		}

		return buf.toString();
	}

	// ==========================================================================
	// 字符串查找函数
	// ==========================================================================

	/**
	 * 在字符串中查找指定字符集合中的字符，并返回第一个匹配的起始索引。 如果字符串为<code>null</code>，则返回
	 * <code>-1</code>。 如果字符集合为<code>null</code>或空，也返回<code>-1</code>。
	 * 
	 * <pre>
	 * StringUtil.indexOfAny(null, *)                = -1
	 * StringUtil.indexOfAny("", *)                  = -1
	 * StringUtil.indexOfAny(*, null)                = -1
	 * StringUtil.indexOfAny(*, [])                  = -1
	 * StringUtil.indexOfAny("zzabyycdxx",['z','a']) = 0
	 * StringUtil.indexOfAny("zzabyycdxx",['b','y']) = 3
	 * StringUtil.indexOfAny("aba", ['z'])           = -1
	 * </pre>
	 * 
	 * @param str
	 *            要扫描的字符串
	 * @param searchChars
	 *            要搜索的字符集合
	 * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
	 */
	public static int indexOfAny(String str, char[] searchChars) {
		if (str == null || str.length() == 0 || searchChars == null
				|| searchChars.length == 0) {
			return -1;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			for (char searchChar : searchChars) {
				if (searchChar == ch) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * 取得指定子串在字符串中出现的次数。
	 * <p>
	 * 如果字符串为<code>null</code>或空，则返回<code>0</code>。
	 * 
	 * <pre>
	 * StringUtil.countMatches(null, *)       = 0
	 * StringUtil.countMatches("", *)         = 0
	 * StringUtil.countMatches("abba", null)  = 0
	 * StringUtil.countMatches("abba", "")    = 0
	 * StringUtil.countMatches("abba", "a")   = 2
	 * StringUtil.countMatches("abba", "ab")  = 1
	 * StringUtil.countMatches("abba", "xxx") = 0
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要扫描的字符串
	 * @param subStr
	 *            子字符串
	 * @return 子串在字符串中出现的次数，如果字符串为<code>null</code>或空，则返回<code>0</code>
	 */
	public static int countMatches(String str, String subStr) {
		if (str == null || str.length() == 0 || subStr == null
				|| subStr.length() == 0) {
			return 0;
		}

		int count = 0;
		int index = 0;

		while ((index = str.indexOf(subStr, index)) != -1) {
			count++;
			index += subStr.length();
		}

		return count;
	}

	// ==========================================================================
	// 取子串函数。
	// ==========================================================================

	/**
	 * 取得长度为指定字符数的最左边的子串。
	 * 
	 * <pre>
	 * StringUtil.left(null, *)    = null
	 * StringUtil.left(*, -ve)     = ""
	 * StringUtil.left("", *)      = ""
	 * StringUtil.left("abc", 0)   = ""
	 * StringUtil.left("abc", 2)   = "ab"
	 * StringUtil.left("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            最左子串的长度
	 * @return 子串，如果原始字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}

		if (len < 0) {
			return EMPTY_STRING;
		}

		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * 取得长度为指定字符数的最右边的子串。
	 * 
	 * <pre>
	 * StringUtil.right(null, *)    = null
	 * StringUtil.right(*, -ve)     = ""
	 * StringUtil.right("", *)      = ""
	 * StringUtil.right("abc", 0)   = ""
	 * StringUtil.right("abc", 2)   = "bc"
	 * StringUtil.right("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            最右子串的长度
	 * @return 子串，如果原始字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}

		if (len < 0) {
			return EMPTY_STRING;
		}

		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * 取得从指定索引开始计算的、长度为指定字符数的子串。
	 * 
	 * <pre>
	 * StringUtil.mid(null, *, *)    = null
	 * StringUtil.mid(*, *, -ve)     = ""
	 * StringUtil.mid("", 0, *)      = ""
	 * StringUtil.mid("abc", 0, 2)   = "ab"
	 * StringUtil.mid("abc", 0, 4)   = "abc"
	 * StringUtil.mid("abc", 2, 4)   = "c"
	 * StringUtil.mid("abc", 4, 2)   = ""
	 * StringUtil.mid("abc", -2, 2)  = "ab"
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @param pos
	 *            起始索引，如果为负数，则看作<code>0</code>
	 * @param len
	 *            子串的长度，如果为负数，则看作长度为<code>0</code>
	 * @return 子串，如果原始字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}

		if (len < 0 || pos > str.length()) {
			return EMPTY_STRING;
		}

		if (pos < 0) {
			pos = 0;
		}

		if (str.length() <= pos + len) {
			return str.substring(pos);
		} else {
			return str.substring(pos, pos + len);
		}
	}

	// ==========================================================================
	// 搜索并取子串函数。
	// ==========================================================================

	/**
	 * 取得第一个出现的分隔子串之前的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * 
	 * <pre>
	 * StringUtil.substringBefore(null, *)      = null
	 * StringUtil.substringBefore("", *)        = ""
	 * StringUtil.substringBefore("abc", "a")   = ""
	 * StringUtil.substringBefore("abcba", "b") = "a"
	 * StringUtil.substringBefore("abc", "c")   = "ab"
	 * StringUtil.substringBefore("abc", "d")   = "abc"
	 * StringUtil.substringBefore("abc", "")    = ""
	 * StringUtil.substringBefore("abc", null)  = "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringBefore(String str, String separator) {
		if (str == null || separator == null || str.length() == 0) {
			return str;
		}

		if (separator.length() == 0) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 取得第一个出现的分隔子串之后的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * 
	 * <pre>
	 * StringUtil.substringAfter(null, *)      = null
	 * StringUtil.substringAfter("", *)        = ""
	 * StringUtil.substringAfter(*, null)      = ""
	 * StringUtil.substringAfter("abc", "a")   = "bc"
	 * StringUtil.substringAfter("abcba", "b") = "cba"
	 * StringUtil.substringAfter("abc", "c")   = ""
	 * StringUtil.substringAfter("abc", "d")   = ""
	 * StringUtil.substringAfter("abc", "")    = "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringAfter(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}

		if (separator == null) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 取得最后一个的分隔子串之前的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * 
	 * <pre>
	 * StringUtil.substringBeforeLast(null, *)      = null
	 * StringUtil.substringBeforeLast("", *)        = ""
	 * StringUtil.substringBeforeLast("abcba", "b") = "abc"
	 * StringUtil.substringBeforeLast("abc", "c")   = "ab"
	 * StringUtil.substringBeforeLast("a", "a")     = ""
	 * StringUtil.substringBeforeLast("a", "z")     = "a"
	 * StringUtil.substringBeforeLast("a", null)    = "a"
	 * StringUtil.substringBeforeLast("a", "")      = "a"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringBeforeLast(String str, String separator) {
		if (str == null || separator == null || str.length() == 0
				|| separator.length() == 0) {
			return str;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 取得最后一个的分隔子串之后的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * 或未找到该子串，则返回原字符串。
	 * 
	 * <pre>
	 * StringUtil.substringAfterLast(null, *)      = null
	 * StringUtil.substringAfterLast("", *)        = ""
	 * StringUtil.substringAfterLast(*, "")        = ""
	 * StringUtil.substringAfterLast(*, null)      = ""
	 * StringUtil.substringAfterLast("abc", "a")   = "bc"
	 * StringUtil.substringAfterLast("abcba", "b") = "a"
	 * StringUtil.substringAfterLast("abc", "c")   = ""
	 * StringUtil.substringAfterLast("a", "a")     = ""
	 * StringUtil.substringAfterLast("a", "z")     = ""
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param separator
	 *            要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
	 */
	public static String substringAfterLast(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}

		if (separator == null || separator.length() == 0) {
			return EMPTY_STRING;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1 || pos == str.length() - separator.length()) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 取得指定分隔符的前两次出现之间的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * ，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.substringBetween(null, *)            = null
	 * StringUtil.substringBetween("", "")             = ""
	 * StringUtil.substringBetween("", "tag")          = null
	 * StringUtil.substringBetween("tagabctag", null)  = null
	 * StringUtil.substringBetween("tagabctag", "")    = ""
	 * StringUtil.substringBetween("tagabctag", "tag") = "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param tag
	 *            要搜索的分隔子串
	 * @return 子串，如果原始串为<code>null</code>或未找到分隔子串，则返回<code>null</code>
	 */
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag, 0);
	}

	/**
	 * 取得两个分隔符之间的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * ，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.substringBetween(null, *, *)          = null
	 * StringUtil.substringBetween("", "", "")          = ""
	 * StringUtil.substringBetween("", "", "tag")       = null
	 * StringUtil.substringBetween("", "tag", "tag")    = null
	 * StringUtil.substringBetween("yabcz", null, null) = null
	 * StringUtil.substringBetween("yabcz", "", "")     = ""
	 * StringUtil.substringBetween("yabcz", "y", "z")   = "abc"
	 * StringUtil.substringBetween("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param open
	 *            要搜索的分隔子串1
	 * @param close
	 *            要搜索的分隔子串2
	 * @return 子串，如果原始串为<code>null</code>或未找到分隔子串，则返回<code>null</code>
	 */
	public static String substringBetween(String str, String open, String close) {
		return substringBetween(str, open, close, 0);
	}

	/**
	 * 取得两个分隔符之间的子串。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 如果分隔子串为<code>null</code>
	 * ，则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.substringBetween(null, *, *)          = null
	 * StringUtil.substringBetween("", "", "")          = ""
	 * StringUtil.substringBetween("", "", "tag")       = null
	 * StringUtil.substringBetween("", "tag", "tag")    = null
	 * StringUtil.substringBetween("yabcz", null, null) = null
	 * StringUtil.substringBetween("yabcz", "", "")     = ""
	 * StringUtil.substringBetween("yabcz", "y", "z")   = "abc"
	 * StringUtil.substringBetween("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @param open
	 *            要搜索的分隔子串1
	 * @param close
	 *            要搜索的分隔子串2
	 * @param fromIndex
	 *            从指定index处搜索
	 * @return 子串，如果原始串为<code>null</code>或未找到分隔子串，则返回<code>null</code>
	 */
	public static String substringBetween(String str, String open,
			String close, int fromIndex) {
		if (str == null || open == null || close == null) {
			return null;
		}

		int start = str.indexOf(open, fromIndex);

		if (start != -1) {
			int end = str.indexOf(close, start + open.length());

			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}

		return null;
	}

	// ==========================================================================
	// 删除字符。
	// ==========================================================================

	/**
	 * 删除所有在<code>Character.isWhitespace(char)</code>中所定义的空白。
	 * 
	 * <pre>
	 * StringUtil.deleteWhitespace(null)         = null
	 * StringUtil.deleteWhitespace("")           = ""
	 * StringUtil.deleteWhitespace("abc")        = "abc"
	 * StringUtil.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 去空白后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String deleteWhitespace(String str) {
		if (str == null) {
			return null;
		}

		int sz = str.length();
		StringBuilder buffer = new StringBuilder(sz);

		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				buffer.append(str.charAt(i));
			}
		}

		return buffer.toString();
	}

	// ==========================================================================
	// 替换子串。
	// ==========================================================================

	/**
	 * 替换指定的子串，替换所有出现的子串。
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>，如果指定子串为<code>null</code>
	 * ，则返回原字符串。
	 * 
	 * <pre>
	 * StringUtil.replace(null, *, *)        = null
	 * StringUtil.replace("", *, *)          = ""
	 * StringUtil.replace("aba", null, null) = "aba"
	 * StringUtil.replace("aba", null, null) = "aba"
	 * StringUtil.replace("aba", "a", null)  = "aba"
	 * StringUtil.replace("aba", "a", "")    = "b"
	 * StringUtil.replace("aba", "a", "z")   = "zbz"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param text
	 *            要扫描的字符串
	 * @param repl
	 *            要搜索的子串
	 * @param with
	 *            替换字符串
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * 替换指定的子串，替换指定的次数。
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>，如果指定子串为<code>null</code>
	 * ，则返回原字符串。
	 * 
	 * <pre>
	 * StringUtil.replace(null, *, *, *)         = null
	 * StringUtil.replace("", *, *, *)           = ""
	 * StringUtil.replace("abaa", null, null, 1) = "abaa"
	 * StringUtil.replace("abaa", null, null, 1) = "abaa"
	 * StringUtil.replace("abaa", "a", null, 1)  = "abaa"
	 * StringUtil.replace("abaa", "a", "", 1)    = "baa"
	 * StringUtil.replace("abaa", "a", "z", 0)   = "abaa"
	 * StringUtil.replace("abaa", "a", "z", 1)   = "zbaa"
	 * StringUtil.replace("abaa", "a", "z", 2)   = "zbza"
	 * StringUtil.replace("abaa", "a", "z", -1)  = "zbzz"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param text
	 *            要扫描的字符串
	 * @param repl
	 *            要搜索的子串
	 * @param with
	 *            替换字符串
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if no
	 *            maximum
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || repl == null || with == null || repl.length() == 0
				|| max == 0) {
			return text;
		}

		StringBuilder buf = new StringBuilder(text.length());
		int start = 0;
		int end = 0;

		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}

		buf.append(text.substring(start));

		return buf.toString();
	}

	/**
	 * 将字符串中所有指定的字符，替换成另一个。
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>。
	 * 
	 * <pre>
	 * StringUtil.replaceChars(null, *, *)        = null
	 * StringUtil.replaceChars("", *, *)          = ""
	 * StringUtil.replaceChars("abcba", 'b', 'y') = "aycya"
	 * StringUtil.replaceChars("abcba", 'z', 'y') = "abcba"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要扫描的字符串
	 * @param searchChar
	 *            要搜索的字符
	 * @param replaceChar
	 *            替换字符
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replaceChar(String str, char searchChar,
			char replaceChar) {
		if (str == null) {
			return null;
		}

		return str.replace(searchChar, replaceChar);
	}

	/**
	 * 将字符串中所有指定的字符，替换成另一个。
	 * <p>
	 * 如果字符串为<code>null</code>则返回<code>null</code>。如果搜索字符串为<code>null</code>
	 * 或空，则返回原字符串。
	 * </p>
	 * <p>
	 * 例如：
	 * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>
	 * 。
	 * </p>
	 * <p>
	 * 通常搜索字符串和替换字符串是等长的，如果搜索字符串比替换字符串长，则多余的字符将被删除。 如果搜索字符串比替换字符串短，则缺少的字符将被忽略。
	 * 
	 * <pre>
	 * StringUtil.replaceChars(null, *, *)           = null
	 * StringUtil.replaceChars("", *, *)             = ""
	 * StringUtil.replaceChars("abc", null, *)       = "abc"
	 * StringUtil.replaceChars("abc", "", *)         = "abc"
	 * StringUtil.replaceChars("abc", "b", null)     = "ac"
	 * StringUtil.replaceChars("abc", "b", "")       = "ac"
	 * StringUtil.replaceChars("abcba", "bc", "yz")  = "ayzya"
	 * StringUtil.replaceChars("abcba", "bc", "y")   = "ayya"
	 * StringUtil.replaceChars("abcba", "bc", "yzx") = "ayzya"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要扫描的字符串
	 * @param searchChars
	 *            要搜索的字符串
	 * @param replaceChars
	 *            替换字符串
	 * @return 被替换后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String replaceChars(String str, String searchChars,
			String replaceChars) {
		if (str == null || str.length() == 0 || searchChars == null
				|| searchChars.length() == 0) {
			return str;
		}

		char[] chars = str.toCharArray();
		int len = chars.length;
		boolean modified = false;

		for (int i = 0, isize = searchChars.length(); i < isize; i++) {
			char searchChar = searchChars.charAt(i);

			if (replaceChars == null || i >= replaceChars.length()) {
				// 删除
				int pos = 0;

				for (int j = 0; j < len; j++) {
					if (chars[j] != searchChar) {
						chars[pos++] = chars[j];
					} else {
						modified = true;
					}
				}

				len = pos;
			} else {
				// 替换
				for (int j = 0; j < len; j++) {
					if (chars[j] == searchChar) {
						chars[j] = replaceChars.charAt(i);
						modified = true;
					}
				}
			}
		}

		if (!modified) {
			return str;
		}

		return new String(chars, 0, len);
	}

	/**
	 * 将指定的子串用另一指定子串覆盖。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。 负的索引值将被看作<code>0</code>
	 * ，越界的索引值将被设置成字符串的长度相同的值。
	 * 
	 * <pre>
	 * StringUtil.overlay(null, *, *, *)            = null
	 * StringUtil.overlay("", "abc", 0, 0)          = "abc"
	 * StringUtil.overlay("abcdef", null, 2, 4)     = "abef"
	 * StringUtil.overlay("abcdef", "", 2, 4)       = "abef"
	 * StringUtil.overlay("abcdef", "", 4, 2)       = "abef"
	 * StringUtil.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
	 * StringUtil.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
	 * StringUtil.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
	 * StringUtil.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
	 * StringUtil.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
	 * StringUtil.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要扫描的字符串
	 * @param overlay
	 *            用来覆盖的字符串
	 * @param start
	 *            起始索引
	 * @param end
	 *            结束索引
	 * @return 被覆盖后的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String overlay(String str, String overlay, int start, int end) {
		if (str == null) {
			return null;
		}

		if (overlay == null) {
			overlay = EMPTY_STRING;
		}

		int len = str.length();

		if (start < 0) {
			start = 0;
		}

		if (start > len) {
			start = len;
		}

		if (end < 0) {
			end = 0;
		}

		if (end > len) {
			end = len;
		}

		if (start > end) {
			int temp = start;

			start = end;
			end = temp;
		}

		return new StringBuilder(len + start - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
	}

	/**
	 * 替换字符串中所有非数字字符
	 * 
	 * @param str
	 *            要替换的字符串
	 * @return 返回替换后的字符串
	 */
	public static String removeNonDigits(String str) {
		if (str == null) {
			return null;
		}

		return str.replaceAll("[^\\d]", "");
	}

	// ==========================================================================
	// 重复字符串。
	// ==========================================================================

	/**
	 * 将指定字符串重复n遍。
	 * 
	 * <pre>
	 * StringUtil.repeat(null, 2)   = null
	 * StringUtil.repeat("", 0)     = ""
	 * StringUtil.repeat("", 2)     = ""
	 * StringUtil.repeat("a", 3)    = "aaa"
	 * StringUtil.repeat("ab", 2)   = "abab"
	 * StringUtil.repeat("abcd", 2) = "abcdabcd"
	 * StringUtil.repeat("a", -2)   = ""
	 * </pre>
	 * 
	 * @param str
	 *            要重复的字符串
	 * @param repeat
	 *            重复次数，如果小于<code>0</code>，则看作<code>0</code>
	 * @return 重复n次的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String repeat(String str, int repeat) {
		if (str == null) {
			return null;
		}

		if (repeat <= 0) {
			return EMPTY_STRING;
		}

		int inputLength = str.length();

		if (repeat == 1 || inputLength == 0) {
			return str;
		}

		int outputLength = inputLength * repeat;

		switch (inputLength) {
		case 1:

			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];

			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}

			return new String(output1);

		case 2:

			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];

			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}

			return new String(output2);

		default:

			StringBuilder buf = new StringBuilder(outputLength);

			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}

			return buf.toString();
		}
	}

	// ==========================================================================
	// Perl风格的chomp和chop函数。
	// ==========================================================================

	/**
	 * 删除字符串末尾的换行符。如果字符串不以换行结尾，则什么也不做。
	 * <p>
	 * 换行符有三种情形：&quot;<code>\n</code>&quot;、&quot;<code>\r</code>&quot;、&quot;
	 * <code>\r\n</code>&quot;。
	 * 
	 * <pre>
	 * StringUtil.chomp(null)          = null
	 * StringUtil.chomp("")            = ""
	 * StringUtil.chomp("abc \r")      = "abc "
	 * StringUtil.chomp("abc\n")       = "abc"
	 * StringUtil.chomp("abc\r\n")     = "abc"
	 * StringUtil.chomp("abc\r\n\r\n") = "abc\r\n"
	 * StringUtil.chomp("abc\n\r")     = "abc\n"
	 * StringUtil.chomp("abc\n\rabc")  = "abc\n\rabc"
	 * StringUtil.chomp("\r")          = ""
	 * StringUtil.chomp("\n")          = ""
	 * StringUtil.chomp("\r\n")        = ""
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 不以换行结尾的字符串，如果原始字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String chomp(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		if (str.length() == 1) {
			char ch = str.charAt(0);

			if (ch == '\r' || ch == '\n') {
				return EMPTY_STRING;
			} else {
				return str;
			}
		}

		int lastIdx = str.length() - 1;
		char last = str.charAt(lastIdx);

		if (last == '\n') {
			if (str.charAt(lastIdx - 1) == '\r') {
				lastIdx--;
			}
		} else if (last == '\r') {
		} else {
			lastIdx++;
		}

		return str.substring(0, lastIdx);
	}

	/**
	 * 删除字符串末尾的指定字符串。如果字符串不以该字符串结尾，则什么也不做。
	 * 
	 * <pre>
	 * StringUtil.chomp(null, *)         = null
	 * StringUtil.chomp("", *)           = ""
	 * StringUtil.chomp("foobar", "bar") = "foo"
	 * StringUtil.chomp("foobar", "baz") = "foobar"
	 * StringUtil.chomp("foo", "foo")    = ""
	 * StringUtil.chomp("foo ", "foo")   = "foo "
	 * StringUtil.chomp(" foo", "foo")   = " "
	 * StringUtil.chomp("foo", "foooo")  = "foo"
	 * StringUtil.chomp("foo", "")       = "foo"
	 * StringUtil.chomp("foo", null)     = "foo"
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param separator
	 *            要删除的字符串
	 * @return 不以指定字符串结尾的字符串，如果原始字串为<code>null</code>，则返回<code>null</code>
	 */
	public static String chomp(String str, String separator) {
		if (str == null || str.length() == 0 || separator == null) {
			return str;
		}

		if (str.endsWith(separator)) {
			return str.substring(0, str.length() - separator.length());
		}

		return str;
	}

	/**
	 * 删除最后一个字符。
	 * <p>
	 * 如果字符串以<code>\r\n</code>结尾，则同时删除它们。
	 * 
	 * <pre>
	 * StringUtil.chop(null)          = null
	 * StringUtil.chop("")            = ""
	 * StringUtil.chop("abc \r")      = "abc "
	 * StringUtil.chop("abc\n")       = "abc"
	 * StringUtil.chop("abc\r\n")     = "abc"
	 * StringUtil.chop("abc")         = "ab"
	 * StringUtil.chop("abc\nabc")    = "abc\nab"
	 * StringUtil.chop("a")           = ""
	 * StringUtil.chop("\r")          = ""
	 * StringUtil.chop("\n")          = ""
	 * StringUtil.chop("\r\n")        = ""
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 删除最后一个字符的字符串，如果原始字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String chop(String str) {
		if (str == null) {
			return null;
		}

		int strLen = str.length();

		if (strLen < 2) {
			return EMPTY_STRING;
		}

		int lastIdx = strLen - 1;
		String ret = str.substring(0, lastIdx);
		char last = str.charAt(lastIdx);

		if (last == '\n') {
			if (ret.charAt(lastIdx - 1) == '\r') {
				return ret.substring(0, lastIdx - 1);
			}
		}

		return ret;
	}

	// ==========================================================================
	// 反转字符串。
	// ==========================================================================

	/**
	 * 反转字符串中的字符顺序。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverse(null)  = null
	 * StringUtil.reverse("")    = ""
	 * StringUtil.reverse("bat") = "tab"
	 * </pre>
	 * 
	 * @param str
	 *            要反转的字符串
	 * @return 反转后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String reverse(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * 反转指定分隔符分隔的各子串的顺序。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverseDelimited(null, *)      = null
	 * StringUtil.reverseDelimited("", *)        = ""
	 * StringUtil.reverseDelimited("a.b.c", 'x') = "a.b.c"
	 * StringUtil.reverseDelimited("a.b.c", '.') = "c.b.a"
	 * </pre>
	 * 
	 * @param str
	 *            要反转的字符串
	 * @param separatorChar
	 *            分隔符
	 * @return 反转后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String reverseDelimited(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		String[] strs = split(str, separatorChar);

		arrayReverse(strs);

		return join(strs, String.valueOf(separatorChar));
	}

	/**
	 * 反转指定分隔符分隔的各子串的顺序。
	 * <p>
	 * 如果字符串为<code>null</code>，则返回<code>null</code>。
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverseDelimited(null, *, *)          = null
	 * StringUtil.reverseDelimited("", *, *)            = ""
	 * StringUtil.reverseDelimited("a.b.c", null, null) = "a.b.c"
	 * StringUtil.reverseDelimited("a.b.c", "", null)   = "a.b.c"
	 * StringUtil.reverseDelimited("a.b.c", ".", ",")   = "c,b,a"
	 * StringUtil.reverseDelimited("a.b.c", ".", null)  = "c b a"
	 * </pre>
	 * 
	 * @param str
	 *            要反转的字符串
	 * @param separatorChars
	 *            分隔符，如果为<code>null</code>，则默认使用空白字符
	 * @param separator
	 *            用来连接子串的分隔符，如果为<code>null</code>，默认使用空格
	 * @return 反转后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
	 */
	public static String reverseDelimited(String str, String separatorChars,
			String separator) {
		if (str == null) {
			return null;
		}

		String[] strs = split(str, separatorChars);

		arrayReverse(strs);

		if (separator == null) {
			return join(strs, " ");
		}

		return join(strs, separator);
	}

	// ==========================================================================
	// 取得字符串的缩略。
	// ==========================================================================

	/**
	 * 截取指定长度的字符（中文字符长度为2）
	 * 
	 * <pre>
	 * StringUtil.abbreviate("abcABC", 4) = "abcA"
	 * StringUtil.abbreviate("我是中国人", 2) = "我"
	 * StringUtil.abbreviate("我A是中国人", 3) = "我A"
	 * StringUtil.abbreviate("我A是中国人", 4) = "我A"
	 * </pre>
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param length
	 *            截取的长度
	 * @return 截取后的字符串
	 */
	public static String abbreviate(String str, int length) {
		if (str == null) {
			return null;
		}

		if (length > str.length()) {
			return str;
		}

		int count = 0;
		int assic = 0x80;
		int pos = 0;

		for (; count < length; pos++) {
			if (str.charAt(pos) / assic == 0) {
				count += 1;
			} else {
				count += 2;
			}
		}

		if (count > length) {
			pos--;
		}

		return str.substring(0, pos);
	}

	// ==========================================================================
	// 将数字或字节转换成ASCII字符串的函数。
	// ==========================================================================

	private static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] DIGITS_NOCASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	/**
	 * 将一个长整形转换成62进制的字符串。
	 */
	public static String longToString(long longValue) {
		return longToString(longValue, false);
	}

	/**
	 * 将一个长整形转换成62进制的字符串。
	 */
	public static String longToString(long longValue, boolean noCase) {
		char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
		int digitsLength = digits.length;

		if (longValue == 0) {
			return String.valueOf(digits[0]);
		}

		if (longValue < 0) {
			longValue = -longValue;
		}

		StringBuilder strValue = new StringBuilder();

		while (longValue != 0) {
			int digit = (int) (longValue % digitsLength);
			longValue = longValue / digitsLength;

			strValue.append(digits[digit]);
		}

		return strValue.toString();
	}

	/**
	 * 将一个byte数组转换成62进制的字符串。
	 */
	public static String bytesToString(byte[] bytes) {
		return bytesToString(bytes, false);
	}

	/**
	 * 将一个byte数组转换成62进制的字符串。
	 */
	public static String bytesToString(byte[] bytes, boolean noCase) {
		char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
		int digitsLength = digits.length;

		if (isEmptyArray(bytes)) {
			return String.valueOf(digits[0]);
		}

		StringBuilder strValue = new StringBuilder();
		int value = 0;
		int limit = Integer.MAX_VALUE >>> 8;
		int i = 0;

		do {
			while (i < bytes.length && value < limit) {
				value = (value << 8) + (0xFF & bytes[i++]);
			}

			while (value >= digitsLength) {
				strValue.append(digits[value % digitsLength]);
				value = value / digitsLength;
			}
		} while (i < bytes.length);

		if (value != 0 || strValue.length() == 0) {
			strValue.append(digits[value]);
		}

		return strValue.toString();
	}
}