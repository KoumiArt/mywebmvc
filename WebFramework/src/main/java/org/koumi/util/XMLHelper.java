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
	 * 读取指定配置文件
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
	 * 默认读取classpath下面的config.xml
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
	 * 获取参数的text
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
	 * 获取指定的标签，得到指定属性值 例如：<web-controller package="org.koumi.web.controller.*Controller"/>
	 * 
	 * @param labelName
	 *            标签名（例如：web-controller）
	 * @param attributeName
	 *            属性名（例如：package）
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
	 * 获取指定的标签下的，所有子标签相同的属性值的值 (支持多个相同属性)。 
	 * 多个属性返回的格式Map:key是“标签名称”，value是“属性值,属性值”的值
	 * 
	 * @param labelName
	 *            标签名（例如：web-model-resolve）
	 * @param attributeName
	 *            属性名（例如：class）
	 * @return
	 */
	public static Map<String, String> getChildNodeAttributes(String labelName,
			String... attributeNames) {
		return getAttributesByParent(labelName, attributeNames);
	}
	
	/**
	 * 获取指定的标签的指定属性的值(支持多个属性，支持多个标签)
	 * 多个属性返回的格式Map:key是“标签名称”，value是“属性值,属性值”的值
	 * @param labelName
	 * @param attributeNames
	 * @return
	 */
	public static Map<String,String> getNodesAttributes(String labelName,String...attributeNames){
		return getAttributesByNode(labelName, attributeNames);
	}
	
	/**
	 * 查询Node的属性
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
	 * 循环List<? extends Node>,取得属性放入Map中(支持多个属性)
	 * 多个属性返回的格式Map:key是“标签名称”，value是“属性值,属性值”的值
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
	 * 找父标签下的所有子标签
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
	 * 获取Node的属性
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
	 * 获取相同标签 中相同的 属性值。 如：
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
