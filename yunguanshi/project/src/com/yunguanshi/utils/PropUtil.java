package com.yunguanshi.utils;

import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 配置文件读取工具类.
 * @author huanghuanlai
 *
 */
public class PropUtil {

	public static Properties properties = null;
	public static String get(String key){
		try {
			if(properties==null){
				properties = PropertiesLoaderUtils.loadAllProperties("config.properties");
			}
			return properties.getProperty("sys.commons."+key,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
