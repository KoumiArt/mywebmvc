package org.koumi.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.koumi.annotation.Controller;
import org.koumi.annotation.RequestMapping;
import org.koumi.web.model.ModelMap;

@Controller
public class UserController {
	
	@RequestMapping("/list")
	public ModelMap userList(){
		ModelMap modelMap = new ModelMap();
		List<String> lst = new ArrayList<String>();
		lst.add("����");
		lst.add("����");
		lst.add("����");			
		modelMap.putRequsetAttribute("lst", lst);
		modelMap.setResult("/index.jsp");
		return modelMap;
	}
}
