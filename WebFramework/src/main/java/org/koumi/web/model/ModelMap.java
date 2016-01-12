package org.koumi.web.model;

import java.util.HashMap;
import java.util.Map;

public class ModelMap {
	private Map<String, Object> requestMap = new HashMap<String, Object>();
	private Map<String,Object> sessionMap = new HashMap<String, Object>();
	private String result;
	
	public ModelMap() {
	}
	public ModelMap(String result) {
		this.result = result;
	}

	public void putRequsetAttribute(String key, Object value) {
		requestMap.put(key, value);
	}

	public Object getRequsetAttribute(String key) {
		return requestMap.get(key);
	}
	
	public void putSessionAttribute(String key,Object value){
		sessionMap.put(key, value);
	}

	public Object getSessionetAttribute(String key) {
		return sessionMap.get(key);
	}
	
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

}
