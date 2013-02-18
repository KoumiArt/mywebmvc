package org.koumi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLHelper {
	public static String configLocation;

	/**
	 * ��ȡָ�������ļ�
	 * 
	 * @param configLocation
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document getDocument(String configLocation)
			throws DocumentException, IOException {
		XMLHelper.configLocation = configLocation;
		return getDocument();
	}

	/**
	 * Ĭ�϶�ȡclasspath�����config.xml
	 * 
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document getDocument() throws DocumentException, IOException {
		if (configLocation == null || configLocation.trim().length() == 0) {
			configLocation = "config.xml";
		}
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(Resources
				.getResurceAsStream(configLocation));
		return doc;
	}

	/**
	 * ��ȡ������text
	 * @param labelName
	 * @return
	 */
	public static String getParamText(String labelName) {
		try {
			Node node = getRootElement().selectSingleNode("//"+labelName+"[1]");
			return node.getText();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param docuement
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Element getRootElement() throws DocumentException,
			IOException {
		return getDocument().getRootElement();
	}

	/**
	 * ��ȡָ���ı�ǩ���õ�ָ������ֵ ���磺<web-controller package="org.koumi.web.controller.*Controller"/>
	 * 
	 * @param labelName
	 *            ��ǩ�������磺web-controller��
	 * @param attributeName
	 *            �����������磺package��
	 * @return
	 */
	public static String getParamAttribute(String labelName, String attributeName) {
		try {
			Node node = getRootElement().selectSingleNode("//"+labelName+"[1]");
			if(node == null)
				return null;
			return getNodeAttribute(node, attributeName);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡָ���ı�ǩ�µģ������ӱ�ǩ��ͬ������ֵ��ֵ (֧�ֶ����ͬ����)�� 
	 * ������Է��صĸ�ʽMap:key�ǡ���ǩ���ơ���value�ǡ�����ֵ,����ֵ����ֵ
	 * 
	 * @param labelName
	 *            ��ǩ�������磺web-model-resolve��
	 * @param attributeName
	 *            �����������磺class��
	 * @return
	 */
	public static Map<String, String> getChildNodeAttributes(String labelName,
			String... attributeNames) {
		return getAttributesByParent(labelName, attributeNames);
	}
	
	/**
	 * ��ȡָ���ı�ǩ��ָ�����Ե�ֵ(֧�ֶ�����ԣ�֧�ֶ����ǩ)
	 * ������Է��صĸ�ʽMap:key�ǡ���ǩ���ơ���value�ǡ�����ֵ,����ֵ����ֵ
	 * @param labelName
	 * @param attributeNames
	 * @return
	 */
	public static Map<String,String> getNodesAttributes(String labelName,String...attributeNames){
		return getAttributesByNode(labelName, attributeNames);
	}
	
	/**
	 * ��ѯNode������
	 * @param labelName
	 * @param attributeNames
	 * @return
	 */
	private static Map<String, String> getAttributesByNode(String labelName,
			String... attributeNames) {
		Map<String, String> map = null;
		try {
			Element element = getRootElement();
			List<? extends Node> nodeList = element.selectNodes("//" + labelName);
			map = forNodeList(nodeList, attributeNames);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * ѭ��List<? extends Node>,ȡ�����Է���Map��(֧�ֶ������)
	 * ������Է��صĸ�ʽMap:key�ǡ���ǩ���ơ���value�ǡ�����ֵ,����ֵ����ֵ
	 * @param map
	 * @param nodeList
	 * @param attributeNames
	 * @return
	 */
	private static Map<String, String> forNodeList(List<? extends Node> nodeList, String... attributeNames) {
		Map<String, String> map= null;
		if (nodeList != null && nodeList.size() > 0) {
			map = new HashMap<String, String>();
			for (Node itemNode : nodeList) {
				String key = itemNode.getName();
				String value = "";
				for (int i = 0; i < attributeNames.length; i++) {
					if (itemNode.valueOf("@" + attributeNames[i]) != null
							&& itemNode.valueOf("@" + attributeNames[i])
									.trim().length() > 0) {
						value += itemNode.valueOf("@"+attributeNames[i]) + ",";
					}
				}
				if (value != null && value.trim().length() > 0) {
					value = value.substring(0, value.length() - 1);
					map.put(key, value);
				}
			}
		}
		return map;
	}

	/**
	 * �Ҹ���ǩ�µ������ӱ�ǩ
	 * @param labelName
	 * @param attributeNames
	 * @return
	 */
	private static Map<String, String> getAttributesByParent(String labelName,
			String... attributeNames) {
		Map<String, String> map = null;
		try {
			Element element = getRootElement();
			Node node = element.selectSingleNode("//" + labelName);
			if(node == null)
				return null;
			List<? extends Node> nodeList = node.selectNodes("./*");
			map = forNodeList(nodeList, attributeNames);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	public static String getNodeAsXML(String labelName) throws DocumentException, IOException{
		Element element = getRootElement();
		Node node = element.selectSingleNode("//" + labelName);		
		return node.asXML();
	}
	
	/**
	 * ��ȡNode������
	 * @param node
	 * @param attributeName
	 * @return
	 */
	private static String getNodeAttribute(Node node,
			String attributeName) {
		return node.valueOf("@"+attributeName);
	}
	
	public static void main(String[] args) {
		try {
			List<String> interceptors = XMLHelper.getAttributes("web-interceptor", "class");
			System.out.println(interceptors);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��ͬ��ǩ ����ͬ�� ����ֵ�� �磺
	 *  <web-interceptor class="org.koumi.web.interceptor.LoginInterceptor"></web-interceptor>
	 *  <web-interceptor class="org.koumi.web.interceptor.DataInterceptor"></web-interceptor> 
	 * @return
	 */
	public static List<String> getAttributes(String labelName, String attributeName) {
		List<String> attributes = null;
		try {
			attributes = findNodes(labelName, attributeName);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return attributes;
	}
	
	/**
	 * 
	 * @param labelName
	 * @param attributeName
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	private static List<String> findNodes(String labelName,String attributeName) throws DocumentException, IOException {
		List<String> attributes = null;
		List<? extends Node> nodeList = getDocument().selectNodes("//"+labelName);
		if (nodeList != null && nodeList.size() > 0) {
			attributes = new ArrayList<String>();
			for (Node itemNode : nodeList) {
				String value = itemNode.valueOf("@" + attributeName);
				if (value != null && value.trim().length() > 0) {
					attributes.add(value);
				}
			}
		}
		return attributes;
	}
}
