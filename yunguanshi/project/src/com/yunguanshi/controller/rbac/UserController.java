package com.yunguanshi.controller.rbac;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.exception.ErrorsMsgException;
import com.yunguanshi.exception.InfoNotEditException;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.Kaptcha;
import com.yunguanshi.model.Locked;
import com.yunguanshi.model.rbac.Department;
import com.yunguanshi.model.rbac.Role;
import com.yunguanshi.model.rbac.User;
import com.yunguanshi.service.IKaptchaService;
import com.yunguanshi.service.ILockedService;
import com.yunguanshi.service.rbac.IDepartmentService;
import com.yunguanshi.service.rbac.IRoleService;
import com.yunguanshi.service.rbac.IUserService;
import com.yunguanshi.utils.DateUtil;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.PropUtil;
import com.yunguanshi.utils.StringUtil;
import com.yunguanshi.utils.ValidateUtil;
import com.yunguanshi.utils.hibernate.Criterias;
import com.yunguanshi.utils.hibernate.Pagination;

/**
 * 用户
 * @author huanghuanlai
 *
 */
@Controller
@RequestMapping("user")
@Scope("prototype")//非单例安全，因为有成员变量（功能带有增删除修改查）
public class UserController {
	
	private static final Logger console = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	IUserService userService;
	@Resource
	IKaptchaService kaptchaService;
	@Resource
	ILockedService lockedService;
	@Resource
	IDepartmentService departmentService;
	@Resource
	IRoleService roleService;
	
	private ValidateUtil validateUtil = new ValidateUtil();
	
	private JsonBuilder jsonBuilder = new JsonBuilder();
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping(value="register.html",method=RequestMethod.GET)
	public String register(String mm){
		console.info("进入注册页面");
		return "register";
	}
	
	
	/**
	 * 退出页面
	 * @return
	 */
	@RequestMapping("logout.html")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			subject.logout();
		}
		console.info("退出系统");
		return "redirect:/admin/index.html";
	}
	
	/**
	 * 读取用户信息列表
	 * @param start
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="read.html",method=RequestMethod.GET)
	@ResponseBody//返回JSON数据专用
	public String read(Integer start,Integer page,String whereSql,Integer limit,String username){
		console.info("传的值:"+whereSql);
		Criterias criterias = new Criterias();
		console.info(StringUtil.isNotEmpty(username)+"");
		if(StringUtil.isNotEmpty(username)){
			criterias.getCriterions().add(Restrictions.like("username", username+"%"));//模糊查询用户列表
		}
		if(StringUtil.isNotEmpty(whereSql)){
			criterias.getCriterions().add(Restrictions.in("department.deptId", whereSql.split(",")));
		}
		try {
			criterias.getCriterions().add(Restrictions.ne("userId", userService.findByName(PropUtil.get("administrator")).getUserId()));
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(),null);
		}
		Long total = 0l;
		Pagination<User> pagination = new Pagination<User>(page,limit,(total=userService.getCount()),criterias);
		List<User> users = userService.findUsers(pagination).getRecordList();
		return jsonBuilder.buildList(total,"users",users, "password,parent,roles,children");
	}
	
	/**
	 * 管理员修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="edit.html")
	@ResponseBody
	public String edit(@Valid User user,BindingResult result){
		console.info("进来了");
		try {
			StringUtil.validateErrorsMsg(result);
		} catch (ErrorsMsgException e1) {
			return jsonBuilder.returnFailureJson(e1.getMessage(),null);
		}
		User _user=null;
		try {
			user.setDepartment(new Department(user.getDeptId()));
			Set<Role> roles = new HashSet<Role>();
			for(String roleId : user.getRolearrays()){
				roles.add(new Role(roleId));
			}
			user.setRoles(roles);
			_user = userService.update(user);
		}catch (InfoNotEditException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(),null);
		} catch (ValidateFailureException e) {
			return jsonBuilder.returnFailureJson(e.getMessage(),null);
		}
		_user.setPassword(null);
		_user.setRoles(null);
		return jsonBuilder.returnSuccessJson("修改成功",_user);
	}
	
	/**
	 * 管理员删除多个用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="destory.html")
	@ResponseBody
	public String deleteUser(String ids){
		try {
			userService.delete(ids);
		} catch (ValidateFailureException e) {
			console.info(e.getMessage());
			return jsonBuilder.returnFailureJson(e.getMessage(),null);
		}
		return jsonBuilder.returnSuccessJson("删除成功",null);
	}
	
	/**
	 * 管理员添加一个用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="add.html",method=RequestMethod.POST)
	@ResponseBody
	public String add(@Valid User user,BindingResult result){
		try {
			StringUtil.validateErrorsMsg(result);
		} catch (ErrorsMsgException e1) {
			return jsonBuilder.returnFailureJson(e1.getMessage(),null);
		}
		Set<Role> roles = new HashSet<Role>();
		for(String id : user.getRolearrays()){
			Role role = new Role();
			role.setRoleId(id);
			roles.add(role);
		}
		user.setRoles(roles);
		User _user=null;
		try {
			_user = userService.add(user);
		}catch (ValidateFailureException e) {
			console.info(e.getMessage());
			return jsonBuilder.returnFailureJson(e.getMessage(),null);
		}
		_user.setPassword(null);
		_user.setRoles(null);
		return jsonBuilder.returnSuccessJson("添加成功",_user);
	}
	
	/**
	 * 开发注册用户
	 * @param user
	 * @param captcha
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="register.html",method=RequestMethod.POST)
	@ResponseBody
	public String register1(User user,String captcha,HttpServletRequest request){
		console.info("进行注册操作");
		if(captcha==null||(captcha!=null&&captcha.trim().length()==0)){
			return jsonBuilder.returnFailureJson("验证码不能为空", null);
		}
		if(!validateUtil.isEmail(user.getEmail())){
			return jsonBuilder.returnFailureJson("邮箱格式不正确", null);
		}
		String ipAdress = request.getHeader("x-forwarded-for");
       if(ipAdress == null || ipAdress.length() == 0 || "unknown".equalsIgnoreCase(ipAdress)) { 
    	   ipAdress = request.getHeader("Proxy-Client-IP"); 
       } 
       if(ipAdress == null || ipAdress.length() == 0 || "unknown".equalsIgnoreCase(ipAdress)) { 
    	   ipAdress = request.getHeader("WL-Proxy-Client-IP"); 
       } 
       if(ipAdress == null || ipAdress.length() == 0 || "unknown".equalsIgnoreCase(ipAdress)) { 
    	   ipAdress = request.getRemoteAddr(); 
       }
		List<Kaptcha> kaptchas = kaptchaService.findKaptchas(user.getEmail(), ipAdress);
		List<Locked> lockeds = lockedService.findLockeds(ipAdress);
		if(lockeds!=null&&lockeds.size()>0){
			if(lockeds.get(0).getErrorCount()>=10){
				return jsonBuilder.returnFailureJson("怀疑您使用暴力攻击软件,系统锁定您 "+lockeds.get(0).getLongTime()+" 分钟。", null);
			}
		}
		if(kaptchas!=null&&kaptchas.size()>0){
			boolean kaptchaPass = false;
			for(Kaptcha kaptcha : kaptchas){//验证中的某一个通过验证 
				if(kaptcha.getKaptcha().equals(captcha)&&kaptchas.get(0).getErrorCount()<3){
					kaptchaPass = true;
					break;
				}
			}
			if(!kaptchaPass){
				if(kaptchas.get(0).getErrorCount()>=3){
					lockedService.updateErrorCount(ipAdress,3);
					return jsonBuilder.returnFailureJson("验证码输入错误超3次已无效、请重新获取", null);
				}else{
					kaptchaService.updateErrorCount(user.getEmail(), ipAdress);//更新验证错误次数
					return jsonBuilder.returnFailureJson("验证码错误、请重新输入", null);
				}
			}
			try {
				Set<Role> roles = new HashSet<Role>();
				roles.add(new Role(roleService.findByNameId("普通用户")));
				user.setRoles(roles);
				user.setBirthday(DateUtil.getCurLongDateTime());
				user.setUsername(user.getEmail());
				user.setDepartment(departmentService.findByName("普通会员"));
				userService.add(user);
				kaptchaService.deleteAll(user.getEmail(), ipAdress);//清空验证码信息
				lockedService.deleteAll(ipAdress);//清空ip锁定信息
			}catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(),null);
			}
		}else{
			lockedService.updateErrorCount(ipAdress,null);
			return jsonBuilder.returnFailureJson("验证码输入错误超3次已无效、请重新获取", null);
		}
		
		return jsonBuilder.returnSuccessJson("注册成功", null);
	}
	
	
}
