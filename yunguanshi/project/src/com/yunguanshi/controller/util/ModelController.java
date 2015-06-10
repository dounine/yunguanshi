package com.yunguanshi.controller.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.utils.EntityUtil;
import com.yunguanshi.utils.JsonBuilder;
import com.yunguanshi.utils.ModelUtil;

/**
 * 
 * @author huanghuanlai
 *
 */

@Controller
@RequestMapping("model")
@Scope("prototype")//非单例安全，因为有成员变量（功能带有增删除修改查）
public class ModelController {
	
	private static final Logger console = LoggerFactory.getLogger(ModelController.class);
	
	private JsonBuilder jsonBuilder = new JsonBuilder();

	@RequestMapping(value="{modelName}.html")
	@ResponseBody
	public String getModelJson(@PathVariable String modelName,String excludes){
		console.info("读取 [ "+modelName+" ] 模型");
		if(modelName.indexOf("_")!=-1){
			modelName = modelName.replaceAll("_", ".");
		}
		String strDate = "";
		if(ModelUtil.modelFieldsJson.get(modelName)==null){
			Class<?> class1=null;
			try {
				class1 = EntityUtil.getClassByName(modelName);
			} catch (ClassNotFoundException e) {
				return jsonBuilder.returnFailureJson(e.getMessage(), null);
			}
			Field[] fields = ModelUtil.getClassFields(class1, false);
			strDate = jsonBuilder.getModelFields(modelName, fields, excludes);
		}else{
			strDate = ModelUtil.modelFieldsJson.get(modelName);
		}
		return strDate;
	}
	
}
