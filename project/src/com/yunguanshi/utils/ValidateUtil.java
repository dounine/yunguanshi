package com.yunguanshi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	/**
	 * 邮箱验证
	 * @param email
	 * @return
	 */
	public boolean isEmail(String email){  
	    if (null==email || "".equals(email)) return false;    
	    Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
	    Matcher m = p.matcher(email);  
	    boolean pa = m.matches();
	    return pa;  
   } 
}
