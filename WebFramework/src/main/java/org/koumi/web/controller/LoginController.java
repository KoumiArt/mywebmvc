package org.koumi.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koumi.annotation.Controller;
import org.koumi.annotation.Param;
import org.koumi.annotation.RequestMapping;
import org.koumi.annotation.ResponseBody;
import org.koumi.model.User;
import org.koumi.web.model.ModelMap;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/login")
	public ModelMap login(@Param("user")User user,Date date , List list) {
		ModelMap modelMap = new ModelMap("/login.jsp");
		if (user == null || user.getUserName() == null
				|| user.getUserName().trim().length() <= 0
				|| user.getUserPwd() == null
				|| user.getUserPwd().trim().length() <= 0){
			modelMap.putRequsetAttribute("msg", "用户名或密码不能为空!");
		} else if (user.getUserName().equals("admin")
				&& user.getUserPwd().equals("admin")) {
			modelMap.putSessionAttribute("userName", user.getUserName());
			modelMap.setResult("redirect:/list.do");
			return modelMap;
		} else {
			modelMap.putRequsetAttribute("msg", "用户名或密码错误!");
		}
		return modelMap;
	}
	@RequestMapping("/getJSON")
	@ResponseBody
	public User getJSON(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("test1", "value1");
		map.put("test2", "value2");
		map.put("test3", "value3");
		map.put("test14", "value4");
		User user = new User();
		user.setId(1);
		user.setUserName("test");
		user.setUserPwd("密码");
		return user;
	}
	
	@RequestMapping("/getParam")
	@ResponseBody
	public <E> User getParam(List<E>[] list){
		Map<String,String> map = new HashMap<String, String>();
		map.put("test1", "value1");
		map.put("test2", "value2");
		map.put("test3", "value3");
		map.put("test14", "value4");
		User user = new User();
		user.setId(1);
		user.setUserName("test");
		user.setUserPwd("密码");
		return user;
	}
}
