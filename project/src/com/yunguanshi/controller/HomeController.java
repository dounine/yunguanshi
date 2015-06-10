package com.yunguanshi.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yunguanshi.service.IMsgService;
import com.yunguanshi.service.rbac.IRoleService;

/**
 * 首页
 * @author huanghuanlai
 *
 */
@Controller
public class HomeController {
	@Resource
	IMsgService msgService;
	@Resource
	IRoleService roleService;
	
	@RequestMapping(value="index.html",method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		if(SecurityUtils.getSubject().isAuthenticated()){
			modelAndView.setViewName("admin/index");
		}else{
			modelAndView.setViewName("admin/login");
		}

		return modelAndView;
	}
	
	@RequestMapping("admin/index.html")
	public ModelAndView loginas(){
		ModelAndView modelAndView = new ModelAndView();
		if(SecurityUtils.getSubject().isAuthenticated()){
			modelAndView.setViewName("admin/index");
		}else{
			modelAndView.setViewName("admin/login");
		}
		
		return modelAndView;
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value="admin/login.html")
	public String isloginget(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return "redirect:/admin/index.html";
		}
		return "admin/login";
	}
	
}
