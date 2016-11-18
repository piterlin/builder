package piterlin.builder;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 林子龙
 * 
 */
public class RegexUtils {
	/**
	 * 获取目标字符串
	 */
	public static String getString(String input, String regex) {
		Pattern pat = Pattern.compile(regex, Pattern.DOTALL);
		Matcher mat = pat.matcher(input);
		if (mat.find()) {
			return mat.group(1);
		} else {
			throw new RuntimeException("没有找到目标字符串");
		}
	}

	/**
	 * 获取目标字符串，如果没有，即返回null
	 */
	public static String getStringWithNoException(String input, String regex) {
		try {
			return getString(input, regex).trim();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取多个目标字符串
	 */
	public static List<String> getSrings(String input, String regex) {
		List<String> result = new LinkedList<String>();
		Pattern pat = Pattern.compile(regex, Pattern.DOTALL);
		Matcher mat = pat.matcher(input);
		while (mat.find()) {
			result.add(mat.group(1));
		}
		return result;
	}
}
