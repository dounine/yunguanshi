package com.yunguanshi.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.auth.PasswordHash;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.rbac.IUserService;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.StringUtil;
import com.yunguanshi.utils.ValidateUtil;

@Controller
@RequestMapping("android")
@Scope("prototype")
@SuppressWarnings("all")
public class AndroidController {
	private ValidateUtil validateUtil = new ValidateUtil();
	private StringUtil stringUtil = new StringUtil();
	private JsonBuilder jsonBuilder = new JsonBuilder();
	@Resource
	private IUserService userService;

	@RequestMapping(value="login.html")
	@ResponseBody
	public String login(User user){
		if(!stringUtil.isNotEmpty(user.getUsername())||!stringUtil.isNotEmpty(user.getPassword())){
			return jsonBuilder.returnFailureJson("用户名或者邮箱不能为空", null);
		}
		if(!validateUtil.isEmail(user.getUsername())){
			return jsonBuilder.returnFailureJson("请您检查您的邮箱格式是否正确", null);
		}
		User _user = userService.findByEmail(user.getUsername());
		if(_user==null){
			return jsonBuilder.returnFailureJson("用户名或密码错误", null);
		}else{
			try {
				if(PasswordHash.validatePassword(user.getPassword(), _user.getPassword())){
					return jsonBuilder.returnSuccessJson("登录成功", null);
				}else{
					return jsonBuilder.returnFailureJson("用户名或密码错误", null);
				}
			} catch (NoSuchAlgorithmException e) {
				return jsonBuilder.returnFailureJson("用户名或密码错误", null);
			} catch (InvalidKeySpecException e) {
				return jsonBuilder.returnFailureJson("用户名或密码错误", null);
			}
		}
	}
}
