package com.yunguanshi.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * shiro令牌重写类.
 * @author huanghuanlai
 *
 */
@SuppressWarnings("all")
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}
	
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	private String captcha;
}
