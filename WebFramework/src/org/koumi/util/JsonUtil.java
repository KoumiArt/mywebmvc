package org.koumi.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author smshen
 * 
 */
public final class JsonUtil {

	/**
	 * 
	 * ���� :������ת��ΪJSON�ַ���
	 * 
	 * ������smshen 2011-8-23
	 * 
	 * @param o
	 *            Object
	 * @return JSON�ַ���
	 */
	@SuppressWarnings("unchecked")
	public static String toJson(final Object o) {
		if (o == null) {
			return "null";
		}
		// String
		if (o instanceof String) {
			return string2Json((String) o);
		}
		// Boolean
		if (o instanceof Boolean) {
			return boolean2Json((Boolean) o);
		}
		// Number
		if (o instanceof Number) {
			return number2Json((Number) o);
		}
		// Map
		if (o instanceof Map) {
			return map2Json((Map<String, Object>) o);
		}
		// List Set
		if (o instanceof Collection) {
			return collection2Json((Collection<?>) o);
		}
		// ������������
		if (o instanceof Date) {
			return date2Json((Date) o);
		}
		// ��������
		if (o instanceof Object[]) {
			return array2Json((Object[]) o);
		}
		// ������������
		if (o instanceof int[]) {
			return intArray2Json((int[]) o);
		}
		// ������������
		if (o instanceof boolean[]) {
			return booleanArray2Json((boolean[]) o);
		}
		// ������������
		if (o instanceof long[]) {
			return longArray2Json((long[]) o);
		}
		// ������������
		if (o instanceof float[]) {
			return floatArray2Json((float[]) o);
		}
		// ������������
		if (o instanceof double[]) {
			return doubleArray2Json((double[]) o);
		}
		// ������������
		if (o instanceof short[]) {
			return shortArray2Json((short[]) o);
		}
		// ������������
		if (o instanceof byte[]) {
			return byteArray2Json((byte[]) o);
		}
		// ������β����
		if (o instanceof Object) {
			return object2Json(o);
		}

		throw new RuntimeException("��֧�ֵ�����: " + o.getClass().getName());
	}

	/**
	 * �� String �������Ϊ JSON��ʽ��ֻ�账��������ַ�
	 * 
	 * @param s
	 *            String ����
	 * @return JSON��ʽ
	 */
	public static String string2Json(final String s) {
		final StringBuilder sb = new StringBuilder(s.length() + 20);
		sb.append('\"');
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		sb.append('\"');
		return sb.toString();
	}

	/**
	 * �� Number ��ʾΪ JSON��ʽ
	 * 
	 * @param number
	 *            Number
	 * @return JSON��ʽ
	 */
	public static String date2Json(final Date date) {
		return "\""+new SimpleDateFormat("yyyy-MM-dd").format(date)+"\"";
	}
	
	/**
	 * �� Number ��ʾΪ JSON��ʽ
	 * 
	 * @param number
	 *            Number
	 * @return JSON��ʽ
	 */
	public static String number2Json(final Number number) {
		return number.toString();
	}

	/**
	 * �� Boolean ��ʾΪ JSON��ʽ
	 * 
	 * @param bool
	 *            Boolean
	 * @return JSON��ʽ
	 */
	public static String boolean2Json(final Boolean bool) {
		return bool.toString();
	}

	/**
	 * �� Collection ����Ϊ JSON ��ʽ (List,Set)
	 * 
	 * @param c
	 * @return JSON��ʽ
	 */
	public static String collection2Json(final Collection<?> c) {
		final Object[] arrObj = c.toArray();
		return toJson(arrObj);
	}

	/**
	 * �� Map<String, Object> ����Ϊ JSON ��ʽ
	 * 
	 * @param map
	 * @return JSON��ʽ
	 */
	public static String map2Json(final Map<String, Object> map) {
		if (map.isEmpty()) {
			return "{}";
		}
		// 4�η�
		final StringBuilder sb = new StringBuilder(map.size() << 4);
		sb.append('{');
		final Set<String> keys = map.keySet();
		for (final String key : keys) {
			final Object value = map.get(key);
			sb.append('\"');
			// ���ܰ��������ַ�
			sb.append(key);
			sb.append('\"');
			sb.append(':');
			// ѭ�����õĶ�����������޵ݹ�
			sb.append(toJson(value));
			sb.append(',');
		}
		// ������ ',' ��Ϊ '}':
		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	/**
	 * ���������Ϊ JSON ��ʽ
	 * 
	 * @param array
	 *            ����
	 * @return JSON ��ʽ
	 */
	public static String array2Json(final Object[] array) {
		if (array.length == 0) {
			return "[]";
		}
		// 4�η�
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final Object o : array) {
			sb.append(toJson(o));
			sb.append(',');
		}
		// �������ӵ� ',' ��Ϊ ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String intArray2Json(final int[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final int o : array) {
			sb.append(Integer.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String longArray2Json(final long[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final long o : array) {
			sb.append(Long.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String booleanArray2Json(final boolean[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final boolean o : array) {
			sb.append(Boolean.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String floatArray2Json(final float[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final float o : array) {
			sb.append(Float.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String doubleArray2Json(final double[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final double o : array) {
			sb.append(Double.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String shortArray2Json(final short[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final short o : array) {
			sb.append(Short.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String byteArray2Json(final byte[] array) {
		if (array.length == 0) {
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final byte o : array) {
			sb.append(Byte.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String object2Json(final Object bean) {
		// ���ݼ��
		if (bean == null) {
			return "{}";
		}
		// ��������
		final Method[] methods = bean.getClass().getMethods();
		// 4�η�
		final StringBuilder sb = new StringBuilder(methods.length << 4);
		sb.append('{');

		for (final Method method : methods) {
			try {
				final String name = method.getName();
				String key = "";
				if (name.startsWith("get")) {
					key = name.substring(3);

					// ����ѭ��
					final String[] arrs = { "Class" };
					boolean bl = false;
					for (final String s : arrs) {
						if (s.equals(key)) {
							bl = true;
							continue;
						}
					}
					if (bl) {
						// ����ѭ��
						continue;
					}
				} else if (name.startsWith("is")) {
					key = name.substring(2);
				}
				if (key.length() > 0 && Character.isUpperCase(key.charAt(0))
						&& method.getParameterTypes().length == 0) {
					if (key.length() == 1) {
						key = key.toLowerCase();
					} else if (!Character.isUpperCase(key.charAt(1))) {
						key = key.substring(0, 1).toLowerCase()
								+ key.substring(1);
					}
					final Object elementObj = method.invoke(bean);

					// System.out.println("###" + key + ":" +
					// elementObj.toString());

					sb.append('\"');
					// ���ܰ��������ַ�
					sb.append(key);
					sb.append('\"');
					sb.append(':');
					// ѭ�����õĶ�����������޵ݹ�
					sb.append(toJson(elementObj));
					sb.append(',');
				}
			} catch (final Exception e) {
				// e.getMessage();
				throw new RuntimeException("�ڽ�bean��װ��JSON��ʽʱ�쳣��"
						+ e.getMessage(), e);
			}
		}
		if (sb.length() == 1) {
			return bean.toString();
		} else {
			sb.setCharAt(sb.length() - 1, '}');
			return sb.toString();
		}
	}

	private JsonUtil() {
	}
}