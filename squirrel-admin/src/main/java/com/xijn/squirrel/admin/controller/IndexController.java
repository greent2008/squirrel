package com.xijn.squirrel.admin.controller;

import com.xijn.squirrel.admin.controller.annotation.PermissionLimit;
import com.xijn.squirrel.admin.core.model.ReturnT;
import com.xijn.squirrel.admin.core.model.SquirrelUser;
import com.xijn.squirrel.admin.core.util.tool.StringTool;
import com.xijn.squirrel.admin.service.impl.LoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * index controller
 */
@Controller
public class IndexController {

	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Resource
	private LoginService loginService;

	@RequestMapping("/")
	@PermissionLimit(limit=false)
	public String index(Model model, HttpServletRequest request) {
		SquirrelUser loginUser = loginService.ifLogin(request);
		if (loginUser == null) {
			return "redirect:/toLogin";
		}
		return "redirect:/user";
	}
	
	@RequestMapping("/toLogin")
	@PermissionLimit(limit=false)
	public String toLogin(Model model, HttpServletRequest request) {
		SquirrelUser loginUser = loginService.ifLogin(request);
		if (loginUser != null) {
			return "redirect:/";
		}
		return "login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	@PermissionLimit(limit=false)
	public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String ifRemember, String userName, String password){
		// param
		boolean ifRem = false;
		if (StringTool.isNotBlank(ifRemember) && "on".equals(ifRemember)) {
			ifRem = true;
		}

		// do login
		ReturnT<String> loginRet = loginService.login(response, userName, password, ifRem);
		return loginRet;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.POST)
	@ResponseBody
	@PermissionLimit(limit=false)
	public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
		loginService.logout(request, response);
		return ReturnT.SUCCESS;
	}
	
}