package com.equinox.util;


import com.equinox.util.exception.ProgramException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	private static final DecimalFormat decimalFormat;

	final public static char COMMA = ',';

	final public static String COMMA_STR = ",";

	final public static char ESCAPE_CHAR = '\\';
    private static final String PREFIX = "\\u";
    private static final String VERTICAL_LINE_REGEX = "\\|";
	private static final String BLANK_STR = " ";
	
	private static final String EMPTY_STR = "";

	public static String speed(long bytes, long ms) {

		double sec = (double) ms / 1000;
		double bytesPerSec = bytes / sec;
		return StringUtil.byteDesc((long) bytesPerSec) + "/s";
	}

	static {
		NumberFormat numberFormat = NumberFormat
				.getNumberInstance(Locale.ENGLISH);
		decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.applyPattern("#.##");
	}

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };
	/**
	 * Split a string using the default separator
	 * 
	 * @param str
	 *            a string that may have escaped separator
	 * @return an array of strings
	 */
	public static String[] split(String str) {

		return split(str, ESCAPE_CHAR, COMMA);
	}

	/**
	 * Split a string using the given separator
	 * 
	 * @param str
	 *            a string that may have escaped separator
	 * @param escapeChar
	 *            a char that be used to escape the separator
	 * @param separator
	 *            a separator char
	 * @return an array of strings
	 */
	public static String[] split(String str, char escapeChar, char separator) {

		if (str == null) {
			return null;
		}
		ArrayList<String> strList = new ArrayList<String>();
		StringBuilder split = new StringBuilder();
		int index = 0;
		while ((index = findNext(str, separator, escapeChar, index, split)) >= 0) {
			++index; // move over the separator for next search
			strList.add(split.toString());
			split.setLength(0); // reset the buffer
		}
		strList.add(split.toString());
		// remove trailing empty split(s)
		int last = strList.size(); // last split
		while (--last >= 0 && "".equals(strList.get(last))) {
			strList.remove(last);
		}
		return strList.toArray(new String[strList.size()]);
	}

	/**
	 * Finds the first occurrence of the separator character ignoring the
	 * escaped separators starting from the index. Note the substring between
	 * the index and the position of the separator is passed.
	 * 
	 * @param str
	 *            the source string
	 * @param separator
	 *            the character to find
	 * @param escapeChar
	 *            character used to escape
	 * @param start
	 *            from where to search
	 * @param split
	 *            used to pass back the extracted string
	 */
	public static int findNext(String str, char separator, char escapeChar,
			int start, StringBuilder split) {

		int numPreEscapes = 0;
		for (int i = start; i < str.length(); i++) {
			char curChar = str.charAt(i);
			if (numPreEscapes == 0 && curChar == separator) { // separator
				return i;
			} else {
				split.append(curChar);
				numPreEscapes = (curChar == escapeChar) ? (++numPreEscapes) % 2
						: 0;
			}
		}
		return -1;
	}

	/**
	 * Escape commas in the string using the default escape char
	 * 
	 * @param str
	 *            a string
	 * @return an escaped string
	 */
	public static String escapeString(String str) {

		return escapeString(str, ESCAPE_CHAR, COMMA);
	}

	/**
	 * Escape <code>charToEscape</code> in the string with the escape char
	 * <code>escapeChar</code>
	 * 
	 * @param str
	 *            string
	 * @param escapeChar
	 *            escape char
	 * @param charToEscape
	 *            the char to be escaped
	 * @return an escaped string
	 */
	public static String escapeString(String str, char escapeChar,
			char charToEscape) {

		return escapeString(str, escapeChar, new char[] { charToEscape });
	}

	// check if the character array has the character
	private static boolean hasChar(char[] chars, char character) {

		for (char target : chars) {
			if (character == target) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param charsToEscape
	 *            array of characters to be escaped
	 */
	public static String escapeString(String str, char escapeChar,
			char[] charsToEscape) {

		if (str == null) {
			return null;
		}
		int len = str.length();
		// Let us specify good enough capacity to constructor of StringBuilder
		// sothat
		// resizing would not be needed(to improve perf).
		StringBuilder result = new StringBuilder((int) (len * 1.5));

		for (int i = 0; i < len; i++) {
			char curChar = str.charAt(i);
			if (curChar == escapeChar || hasChar(charsToEscape, curChar)) {
				// special char
				result.append(escapeChar);
			}
			result.append(curChar);
		}
		return result.toString();
	}

	/**
	 * Unescape commas in the string using the default escape char
	 * 
	 * @param str
	 *            a string
	 * @return an unescaped string
	 */
	public static String unEscapeString(String str) {

		return unEscapeString(str, ESCAPE_CHAR, COMMA);
	}

	/**
	 * Unescape <code>charToEscape</code> in the string with the escape char
	 * <code>escapeChar</code>
	 * 
	 * @param str
	 *            string
	 * @param escapeChar
	 *            escape char
	 * @param charToEscape
	 *            the escaped char
	 * @return an unescaped string
	 */
	public static String unEscapeString(String str, char escapeChar,
			char charToEscape) {

		return unEscapeString(str, escapeChar, new char[] { charToEscape });
	}

	/**
	 * @param charsToEscape
	 *            array of characters to unescape
	 */
	public static String unEscapeString(String str, char escapeChar,
			char[] charsToEscape) {

		if (str == null) {
			return null;
		}
		StringBuilder result = new StringBuilder(str.length());
		boolean hasPreEscape = false;
		for (int i = 0; i < str.length(); i++) {
			char curChar = str.charAt(i);
			if (hasPreEscape) {
				if (curChar != escapeChar && !hasChar(charsToEscape, curChar)) {
					// no special char
					throw new IllegalArgumentException(
							"Illegal escaped string " + str + " unescaped "
									+ escapeChar + " at " + (i - 1));
				}
				// otherwise discard the escape char
				result.append(curChar);
				hasPreEscape = false;
			} else {
				if (hasChar(charsToEscape, curChar)) {
					throw new IllegalArgumentException(
							"Illegal escaped string " + str + " unescaped "
									+ curChar + " at " + i);
				} else if (curChar == escapeChar) {
					hasPreEscape = true;
				} else {
					result.append(curChar);
				}
			}
		}
		if (hasPreEscape) {
			throw new IllegalArgumentException("Illegal escaped string " + str
					+ ", not expecting " + escapeChar + " in the end.");
		}
		return result.toString();
	}

	/**
	 * Return hostname without throwing exception.
	 * 
	 * @return hostname
	 */
	public static String getHostname() {

		try {
			return "" + InetAddress.getLocalHost();
		} catch (UnknownHostException uhe) {
			return "" + uhe;
		}
	}

	/**
	 * Return a message for logging.
	 * 
	 * @param prefix
	 *            prefix keyword for the message
	 * @param msg
	 *            content of the message
	 * @return a message for logging
	 */
	private static String toStartupShutdownString(String prefix, String[] msg) {

		StringBuffer b = new StringBuffer(prefix);
		b.append("\n/************************************************************");
		for (String s : msg)
			b.append("\n" + prefix + s);
		b.append("\n************************************************************/");
		return b.toString();
	}

	/**
	 * The traditional binary prefixes, kilo, mega, ..., exa, which can be
	 * represented by a 64-bit integer. TraditionalBinaryPrefix symbol are case
	 * insensitive.
	 */
	public static enum TraditionalBinaryPrefix {
		KILO(1024), MEGA(KILO.value << 10), GIGA(MEGA.value << 10), TERA(
				GIGA.value << 10), PETA(TERA.value << 10), EXA(PETA.value << 10);

		public final long value;

		public final char symbol;

		TraditionalBinaryPrefix(long value) {

			this.value = value;
			this.symbol = toString().charAt(0);
		}

		/**
		 * @return The TraditionalBinaryPrefix object corresponding to the
		 *         symbol.
		 */
		public static TraditionalBinaryPrefix valueOf(char symbol) {

			symbol = Character.toUpperCase(symbol);
			for (TraditionalBinaryPrefix prefix : TraditionalBinaryPrefix
					.values()) {
				if (symbol == prefix.symbol) {
					return prefix;
				}
			}
			throw new IllegalArgumentException("Unknown symbol '" + symbol
					+ "'");
		}

		/**
		 * Convert a string to long. The input string is first be trimmed and
		 * then it is parsed with traditional binary prefix.
		 * <p/>
		 * For example, "-1230k" will be converted to -1230 * 1024 = -1259520;
		 * "891g" will be converted to 891 * 1024^3 = 956703965184;
		 * 
		 * @param s
		 *            input string
		 * @return a long value represented by the input string.
		 */
		public static long string2long(String s) {

			s = s.trim();
			final int lastpos = s.length() - 1;
			final char lastchar = s.charAt(lastpos);
			if (Character.isDigit(lastchar))
				return Long.parseLong(s);
			else {
				long prefix = TraditionalBinaryPrefix.valueOf(lastchar).value;
				long num = Long.parseLong(s.substring(0, lastpos));
				if (num > (Long.MAX_VALUE / prefix)
						|| num < (Long.MIN_VALUE / prefix)) {
					throw new IllegalArgumentException(s
							+ " does not fit in a Long");
				}
				return num * prefix;
			}
		}
	}

	/**
	 * Escapes HTML Special characters present in the string.
	 * 
	 * @param string
	 * @return HTML Escaped String representation
	 */
	public static String escapeHTML(String string) {

		if (string == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		boolean lastCharacterWasSpace = false;
		char[] chars = string.toCharArray();
		for (char c : chars) {
			if (c == ' ') {
				if (lastCharacterWasSpace) {
					lastCharacterWasSpace = false;
					sb.append("&nbsp;");
				} else {
					lastCharacterWasSpace = true;
					sb.append(" ");
				}
			} else {
				lastCharacterWasSpace = false;
				switch (c) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}

		return sb.toString();
	}

	/**
	 * Return an abbreviated English-language desc of the byte length
	 */
	public static String byteDesc(long len) {

		double val = 0.0;
		String ending = "";
		if (len < 1024 * 1024) {
			val = (1.0 * len) / 1024;
			ending = " KB";
		} else if (len < 1024 * 1024 * 1024) {
			val = (1.0 * len) / (1024 * 1024);
			ending = " MB";
		} else if (len < 1024L * 1024 * 1024 * 1024) {
			val = (1.0 * len) / (1024 * 1024 * 1024);
			ending = " GB";
		} else if (len < 1024L * 1024 * 1024 * 1024 * 1024) {
			val = (1.0 * len) / (1024L * 1024 * 1024 * 1024);
			ending = " TB";
		} else {
			val = (1.0 * len) / (1024L * 1024 * 1024 * 1024 * 1024);
			ending = " PB";
		}
		return limitDecimalTo2(val) + ending;
	}

	/**
	 * Capitalize a word
	 * 
	 * @param s
	 *            the input string
	 * @return capitalized string
	 */
	public static String capitalize(String s) {

		int len = s.length();
		if (len == 0)
			return s;
		return new StringBuilder(len)
				.append(Character.toTitleCase(s.charAt(0)))
				.append(s.substring(1)).toString();
	}

	/**
	 * Convert SOME_STUFF to SomeStuff
	 * 
	 * @param s
	 *            input string
	 * @return camelized string
	 */
	public static String camelize(String s) {

		StringBuilder sb = new StringBuilder();
		String[] words = split(s.toLowerCase(Locale.US), ESCAPE_CHAR, '_');

		for (String word : words)
			sb.append(capitalize(word));

		return sb.toString();
	}

	public static synchronized String limitDecimalTo2(double d) {

		return decimalFormat.format(d);
	}

	public static String md5(String str) {

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(str.getBytes("UTF-8"));
			byte[] hash = digest.digest();
			char[] md5 = new char[32];
			int i = 0;
			for (byte b : hash) {
				md5[i++] = digits[b >> 4 & 0xF];
				md5[i++] = digits[b & 0xF];
			}
			return new String(md5);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Make a string representation of the exception.
	 * 
	 * @param e
	 *            The exception to stringify
	 * @return A string with exception name and call stack.
	 */
	public static String stringifyException(Throwable e) {

		StringWriter stm = new StringWriter();
		PrintWriter wrt = new PrintWriter(stm);
		e.printStackTrace(wrt);
		wrt.close();
		return stm.toString();
	}

	public static String unicode(String str) {

		StringBuilder buffer = new StringBuilder();
		char[] tmpUnicode = new char[4];
		for (char c : str.toCharArray()) {
			if (c == 34 || c == 92 || c == 47) {
				buffer.append("\\").append(c);
			} else if (c == 8) {
				buffer.append("\\b");
			} else if (c == 12) {
				buffer.append("\\f");
			} else if (c == 10) {
				buffer.append("\\n");
			} else if (c == 13) {
				buffer.append("\\r");
			} else if (c == 9) {
				buffer.append("\\t");
			} else if (c > 127) {
				// System.out.println("Integer.toHexString(c) = " +
				// Integer.toHexString(c));
				tmpUnicode[3] = digits[c & 0xF];
				tmpUnicode[2] = digits[c >> 4 & 0xF];
				tmpUnicode[1] = digits[c >> 8 & 0xF];
				tmpUnicode[0] = digits[c >> 12 & 0xF];
				// System.out.println("tmpUnicode(c):" +
				// Arrays.toString(tmpUnicode));
				buffer.append("\\u").append(tmpUnicode);
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	public static long toLong(String str) {

		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static Float toFloat(String str) {

		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return 0f;
		}
	}

	public static int toInt(String str) {

		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}


	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean notEmpty(String str) {
		return str != null && str.length() > 0;
	}

	public static List<String> toStringList(String str) {

		return Arrays.asList(str.split(","));
	}

	public static List<Long> toLongList(String str) {

		if (isEmpty(str))
			return Collections.emptyList();

		ArrayList<Long> longList = new ArrayList<Long>();
		for (String s : str.split(",")) {
			longList.add(Long.valueOf(s));
		}
		return longList;
	}

	public static List<String> split2List(String src, String splitter) {
		ArrayList results = new ArrayList();
		if(src == null) {
			return results;
		} else {
			String[] temp = src.split(splitter);

			for(int i = 0; i < temp.length; ++i) {
				if(!temp[i].trim().equals("")) {
					results.add(temp[i].trim());
				}
			}

			return results;
		}
	}

	public static String toString(List<?> longList) {

		if (longList == null || longList.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		for (Object aLong : longList) {
			if (sb.length() > 0)
				sb.append(',');
			sb.append(aLong);
		}
		return sb.toString();
	}

	public static String join(char sp, List<?> parameters) {

		StringBuilder sb = new StringBuilder();

		for (Object i : parameters) {
			if (i == null) {
				continue;
			}
			if (sb.length() > 0)
				sb.append(sp);
			sb.append("'").append(i).append("'");
		}
		return sb.toString();
	}

	public static String genRandom(int len) {

		char[] randomArray = new char[len];
		Random r = new Random();
		for (int i = 0; i < len; i++) {
			randomArray[i] = digits[Math.abs(r.nextInt(digits.length))];
		}
		return new String(randomArray);
	}

	public static String getNewIconName(String base, String end, String oldIcon) {

		if (StringUtil.isEmpty(oldIcon) || !oldIcon.contains(base)) {
			return base + "_1" + end;
		}

		Pattern pattern = Pattern.compile(base + "_([0-9]+)" + end);

		Matcher matcher = pattern.matcher(oldIcon);
		if (!matcher.matches()) {
			return base + "_1" + end;
		}

		int i = Integer.parseInt(matcher.group(1));
		return base + "_" + (i + 1) + end;

	}

	public static String getSuffix(String name) {

		if (isEmpty(name))
			return "";
		int dot = name.lastIndexOf('.');
		if (dot == -1)
			return "";
		return name.substring(dot);
	}

	public static String fixJSONStr(String str) {

		return str.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\")
				.replaceAll("\\\\\\\\/", "/").replaceAll("[\"][{]", "{")
				.replaceAll("[}][\"]", "}");
	}



	public static boolean allowVersion(String allowVersion, String clientVersion) {

		try {
			allowVersion = allowVersion.replace("-r", "");
			clientVersion = clientVersion.replace("-r", "");
			if ("20120410167".equals(clientVersion))
				return true;
			if (StringUtil.toLong(allowVersion) <= StringUtil
					.toLong(clientVersion))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取DRM contentId 如果提供超过16位的源则截取前16位
	 * 
	 * @param ObjectId
	 * @return
	 */
	public static String getDRMContentId(String ObjectId) {

		int size = 32;
		if (ObjectId != null && ObjectId.length() >= size) {
			return ObjectId.substring(0, size);
		} else {
			return getRandomString("1234567890abcdef", size);
		}
	}

	/**
	 * 获取DRM key 如果提供超过16位的源则截取后16位
	 * 
	 * @param ObjectId
	 * @return
	 */
	public static String getDRMKey(String ObjectId) {

		int size = 32;
		if (ObjectId != null && ObjectId.length() >= size) {
			int len = ObjectId.length();
			return ObjectId.substring(len - size, len - 1);
		} else {
			return getRandomString("1234567890abcdef", size);
		}
	}

	/**
	 * 获取随机字符串
	 * 
	 * @param o
	 * @param length
	 * @return
	 */
	public static String getRandomString(String o, int length) {

		if (StringUtil.isEmpty(o)) {
			o = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		}
		int range = o.length();
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(range);
			buf.append(o.charAt(num));
		}

		return buf.toString();
	}

	public static String firstCharUpper(String name) {
		// name = name.substring(0, 1).toUpperCase() + name.substring(1);
		// return name;
		if(isEmpty(name)){
			return name;
		}
		char[] cs = name.toCharArray();
		if(cs[0]>96 && cs[0]<123){
			cs[0] -= 32;
		}
		return String.valueOf(cs);

	}
	
	public static String firstCharLower(String name) {
		if(isEmpty(name)){
			return name;
		}
		char[] cs = name.toCharArray();
		if(cs[0]>64 && cs[0]<91){
			cs[0] += 32;
		}
		return String.valueOf(cs);
	}
	
	@Deprecated
    private static char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException(
                    "Ascii string of a native character must be 6 character.");
        }
        if (!PREFIX.equals(str.substring(0, 2))) {
            throw new IllegalArgumentException(
                    "Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

    @Deprecated
    public static String native2Ascii(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append(char2Ascii(chars[i]));
        }
        return sb.toString();
    }

    /**
     * Native character to ascii string.
     *
     * @param c native character
     * @return ascii string
     */
    @Deprecated
    private static String char2Ascii(char c) {
        if (c > 255) {
            StringBuilder sb = new StringBuilder();
            sb.append(PREFIX);
            int code = (c >> 8);
            String tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            code = (c & 0xFF);
            tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            return sb.toString();
        } else {
            return Character.toString(c);
        }
    }

    @Deprecated
    public static String ascii2Native(String str) {
    	if(isEmpty(str)){
    		return EMPTY_STR;
    	}
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf(PREFIX);
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf(PREFIX, begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }
    
    public static String nullToStr(String str) {
        return str == null ? EMPTY_STR : str;
    }
//	public static void main(String[] args) {
//		String s = "\u7687\u5bb6\u9a6c\u5fb7\u91cc";
//		System.out.println(ascii2Native(s));
//		System.out.println(firstCharUpper("aBc"));
//	}

	public static boolean equals(String a, String b) {
		// TODO Auto-generated method stub
		if(a==null){
			return false;
		}
		return a.equals(b);
	}
	
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

	public static String generateZeroStr(int length) {
		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < length; ++i) {
			sb.append('0');
		}

		return sb.toString();
	}


	public static String toFixdLengthStr(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if(fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroStr(fixdlenth - strNum.length()));
			sb.append(strNum);
			return sb.toString();
		} else {
			throw new ProgramException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
	}

	/**
	 * 将对象转换为字符串，如果对象为null，则返回null
	 *
	 * @param obj 对象
	 * @return
	 */
	public static String object2Str(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	public static boolean isEquals(String s1, String s2) {
		return s1 == null && s2 == null?true:(s1 != null && s2 != null?s1.equals(s2):false);
	}

}
