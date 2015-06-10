package com.yunguanshi.utils;

import java.lang.reflect.Field;

@SuppressWarnings("all")
public class FormUtil {
	
	public static Object copyNewField(Object object,Object entity){
		Field[] fields = ModelUtil.getClassFields(entity.getClass(), false);
		return null;
	}
	
	public static Object getPropertyValue(Object model,String propName){
		String getMethodName = "get"+propName.substring(0).toUpperCase()+propName.substring(1);
		//Object object = invo
		return null;
	}
}
