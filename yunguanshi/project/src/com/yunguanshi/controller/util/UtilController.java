package com.yunguanshi.controller.util;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;
import com.yunguanshi.email.MailSenderInfo;
import com.yunguanshi.email.SimpleMailSender;
import com.yunguanshi.exception.ValidateFailureException;
import com.yunguanshi.model.Kaptcha;
import com.yunguanshi.model.Locked;
import com.yunguanshi.service.IKaptchaService;
import com.yunguanshi.service.ILockedService;
import com.yunguanshi.utils.DateUtil;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.StringUtil;
import com.yunguanshi.utils.ValidateUtil;

/**
 * 工具
 * @author huanghaunlai
 *
 */
@Controller
@RequestMapping("util")
@Scope("prototype")//非单例安全，因为有成员变量（功能带有增删除修改查）
public class UtilController {
	
	private static final char[] codeSequence = { 'A','B','C','D','E','F','H','I','J','K','L','M','N','O',
		'P','Q','R','S','T','U','V','W','X','Y','X','a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 
        'x', 'y', 'z', '0', '2', '3', '4', '5', '6', '7', '8', '9' }; 
	private JsonBuilder jsonBuilder = new JsonBuilder();
	private Random random = new Random();
	@Resource
	private MailSenderInfo mailSenderInfo;
	@Resource
	private IKaptchaService kaptchaService;
	@Resource
	private ILockedService lockedService;
	private SimpleMailSender sms = new SimpleMailSender();
	private ValidateUtil validateUtil=new ValidateUtil();
	
	/**
	 * 汉语转拼音工具
	 * @param chinese
	 * @return
	 */
	@RequestMapping(value="convert.html")
	@ResponseBody
	public String toPinYin(String chinese){
		if(!StringUtil.isNotEmpty(chinese)) return jsonBuilder.returnFailureJson("请传递要转换的文字",null);
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//设置输出为小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置为无音标
		StringBuffer sb = new StringBuffer();
		try {
			for(char c : chinese.toCharArray()){
				String[] pinyin = null;
				if(StringUtil.isHanYu(c+"")){
					 pinyin = PinyinHelper.toHanyuPinyinStringArray(c,format);
					 for(String py : pinyin){
						 sb.append(py);
					 }
				}else {
					sb.append(c);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return jsonBuilder.returnSuccessJson("转换异常",null);
		}
		return jsonBuilder.returnSuccessJson(sb.toString(), null);
	}
	
	/**
	 * 发送邮箱
	 * @return
	 */
	@RequestMapping(value="sendmail.html")
	@ResponseBody
	public String sendmail(String email,HttpServletRequest request){
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
		if(!validateUtil.isEmail(email)){
			return jsonBuilder.returnFailureJson("邮箱格式不正确", null);
		}else{
			try {
				ipIsLocked();
				List<Kaptcha> kaptchas = kaptchaService.findKaptchas(email,ipAdress);
				if(kaptchas.size()<3){
					List<Kaptcha> kaptchaips = kaptchaService.findKaptchas(ipAdress);
					if(kaptchaips.size()>10){//ip地址注册过于频繁
						Locked locked = new Locked();
						locked.setBeginLockTime(DateUtil.getCurLongDateTime());
						locked.setEndLockTime(DateUtil.incrementMin(new Date(),locked.getLongTime()));
						locked.setIpAdress(ipAdress);
						lockedService.add(locked);
						return jsonBuilder.returnFailureJson("您的注册操作过于频繁,系统将锁定您 "+locked.getLongTime()+" 分钟。", null);
					}else{
						List<Kaptcha> kaptchaemails = kaptchaService.findKaptchasForEmail(email);
						if(kaptchaemails.size()>10){//邮箱号注册过于频繁
							Locked locked = new Locked();
							locked.setBeginLockTime(DateUtil.getCurLongDateTime());
							locked.setEndLockTime(DateUtil.incrementMin(new Date(),locked.getLongTime()));
							locked.setIpAdress(ipAdress);
							lockedService.add(locked);
							return jsonBuilder.returnFailureJson("您的邮箱操作过于频繁,系统将锁定您 "+locked.getLongTime()+" 分钟。", null);
						}
						StringBuffer sb =new StringBuffer();
						for(int i =0;i<8;i++){
							String code = String.valueOf(codeSequence[random.nextInt(59)]);
							sb.append(code);
						}
						mailSenderInfo.setToAddress(email);
						mailSenderInfo.setContent(getMailContext(sb.toString()));
						mailSenderInfo.setSubject("[ 云管事 ] 注册验证码");
						sms.sendHtmlMail(mailSenderInfo);
						Kaptcha kaptcha = new Kaptcha();
						kaptcha.setCreateTime(DateUtil.getCurLongDateTime());
						kaptcha.setEmail(email);
						kaptcha.setIpAdress(ipAdress);
						kaptcha.setKaptcha(sb.toString());
						kaptchaService.add(kaptcha);
					}
				}else{
					int i =0;
					for(Kaptcha kaptcha : kaptchas){
						int timelong = DateUtil.minsBetween(DateUtil.formatDateTime(kaptcha.getCreateTime()),new Date());
						if(timelong>=60){//查询有大于指定分钟的的注册验证码就删除
							kaptchaService.deleteById(kaptcha.getId());
						}else{
							i++;
						}
					}
					if(i>=3){//60分钟内的注册验证码>=3条,判断用户是恶意注册
						Locked locked = new Locked();
						locked.setBeginLockTime(DateUtil.getCurLongDateTime());
						locked.setEndLockTime(DateUtil.incrementMin(new Date(),locked.getLongTime()));
						locked.setIpAdress(ipAdress);
						lockedService.add(locked);
						return jsonBuilder.returnFailureJson("你的注册操作过于频繁,系统将锁定您 "+locked.getLongTime()+" 分钟。", null);
					}
				}
			} catch (ValidateFailureException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
				
		}
		return jsonBuilder.returnSuccessJson("发送成功", null);
	}
	
	public void ipIsLocked() throws ValidateFailureException{
		String ipHose = SecurityUtils.getSubject().getSession().getHost();
		List<Locked> lockeds = lockedService.findLockeds(ipHose);
		if(lockeds!=null&&lockeds.size()>0){
			Locked locked = lockeds.get(0);
			if(locked.isAccess()){
				lockedService.updateAccess(ipHose);
				throw new ValidateFailureException("由于您的恶意操作,系统已经将您锁定 "+lockeds.get(0).getLongTime()+" 分钟。");
			}else{
				int timelong = DateUtil.minsBetween(new Date(),DateUtil.formatDateTime(locked.getEndLockTime()));
				if(timelong<=0){
					lockedService.deleteAll(ipHose);
				}else{
					throw new ValidateFailureException("您离系统解锁还剩 "+timelong+" 分钟。");
				}
			}
		}
	}
	
	public String getMailContext(String kaptcha){
		StringBuffer sb = new StringBuffer();
		sb.append("<style class=\"fox_global_style\"> ");
		sb.append("div.fox_html_content { line-height: 1.5;} ");
		sb.append("blockquote { margin-Top: 0px; margin-Bottom: 0px; margin-Left: 0.5em } ");
		sb.append("ol, ul { margin-Top: 0px; margin-Bottom: 0px; list-style-position: inside; }");
		sb.append("p { margin-Top: 0px; margin-Bottom: 0px } ");
		sb.append("</style> ");
		sb.append("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#DBDDDD\" align=\"center\">"); 
		sb.append("<tbody>");
		sb.append("<tr> ");
			sb.append("<td> ");
				sb.append("<table width=\"600\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">"); 
					sb.append("<tbody>"); 
						sb.append("<tr>"); 
							sb.append("<td width=\"24\" bgcolor=\"#ffffff\">&nbsp;</td>"); 
								sb.append("<td width=\"552\" bgcolor=\"#ffffff\">"); 
								sb.append("<table width=\"552\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 10px;\">"); 
									sb.append("<tbody>"); 
										sb.append("<tr>"); 
											sb.append("<td width=\"352\" bgcolor=\"#ffffff\">"); 
												sb.append("<img width=\"198\" src=\"http://yunguanshi.qiniudn.com/images/ygs/emaillogo.png\">"); 
													sb.append("</td>"); 
												sb.append("<td width=\"200\" valign=\"top\" bgcolor=\"#ffffff\">");
												sb.append("<p style=\"color:#999; font:normal 12px/22px Arial, Helvetica, sans-serif; margin:0; padding:0;\"></p>");
													sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td colspan=\"2\" width=\"552\" bgcolor=\"#ffffff\">"); 
												sb.append("<div style=\"height:1px;border-bottom:1px dashed #C5C5C5; font:normal 12px/22px Arial, Helvetica, sans-serif; margin:12px 0; padding:0;\"></div>"); 
													sb.append("</td>"); 
												sb.append("</tr>");
											sb.append("</tbody>"); 
										sb.append("</table>"); 
									sb.append("<table width=\"552\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\">"); 
									sb.append("<tbody>"); 
										sb.append("<tr>"); 
											sb.append("<td width=\"552\" bgcolor=\"#ffffff\"> <h3 style=\"font:normal 700 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0;\">尊敬的用户，您好!</h3>"); 
												sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td height=\"12\" bgcolor=\"#ffffff\">&nbsp;</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td width=\"552\" bgcolor=\"#ffffff\">"); 
												sb.append("<p style=\"font:normal 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0; text-indent:2em;\">以下是你需要的验证码、请复制到指定的地方完成注册操作。</p>"); 
												sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td height=\"12\" bgcolor=\"#ffffff\">&nbsp;</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td width=\"552\" bgcolor=\"#ffffff\"> <p style=\"text-indent:28px; font:normal 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0; text-indent:2em;\"><a href=\"javascript:void(0)\" target=\"_blank\">"+kaptcha+"</a><span style=\"color:#336699; font:normal 700 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0;\"></span> </p>"); 
													sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td width=\"552\" bgcolor=\"#ffffff\"> <p style=\"font:normal 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0; display:none;text-indent:2em; color:#999;\">(如链接无法点击，请复制链接到浏览器地址栏打开页面)</p>"); 
												sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td height=\"12\" bgcolor=\"#ffffff\">&nbsp;</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>");
											sb.append("<br/>");
											sb.append("<td width=\"552\" align=\"right\" bgcolor=\"#ffffff\"> <p style=\" font:normal 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0;\">云管事  - 您的云生活</p> </td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td width=\"552\" align=\"right\" bgcolor=\"#ffffff\">"); 
												sb.append("<p style=\" font:normal 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0;\"><span style=\"border-bottom:1px dashed #ccc;\" t=\"5\" times=\"\">"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"</span></p>"); 
													sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("</tbody>"); 
										sb.append("</table>"); 
									sb.append("<table width=\"552\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#ffffff\" align=\"center\">");
									sb.append("<tbody>");
										sb.append("<tr>"); 
											sb.append("<td colspan=\"2\" width=\"552\" bgcolor=\"#ffffff\">"); 
												sb.append("<div style=\"height:1px;border-bottom:1px dashed #C5C5C5; font:normal 12px/22px Arial, Helvetica, sans-serif; margin:12px 0; padding:0;\"></div>"); 
													sb.append("</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td width=\"552\" bgcolor=\"#ffffff\"> <p style=\"color:#999; font:normal 14px/24px Arial, Helvetica, sans-serif; margin:0; padding:0; font-size:12px; text-indent:2em;\">这是一封自动生成的邮件,请勿直接回复本邮件。激活有效期为1个小时,请在有效期内激活您的邮箱！</p> </td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td height=\"12\" bgcolor=\"#ffffff\">&nbsp;</td>"); 
												sb.append("</tr>"); 
											sb.append("<tr>"); 
											sb.append("<td width=\"552\" align=\"center\" bgcolor=\"#ffffff\"> <img width=\"100\" height=\"38\" src=\"http://yunguanshi.qiniudn.com/images/ygs/emaillogo.png\">"); 
												sb.append("</td>");
												sb.append("</tr>"); 
											sb.append("</tbody>"); 
										sb.append("</table>"); 
									sb.append("</td>"); 
								sb.append("<td width=\"24\" bgcolor=\"#ffffff\">&nbsp;</td>"); 
								sb.append("</tr>"); 
							sb.append("</tbody>"); 
						sb.append("</table>"); 
					sb.append("</td>"); 
				sb.append("</tr>"); 
			sb.append("</tbody>");
		sb.append("</table>");
		
		return sb.toString();
	}
}
