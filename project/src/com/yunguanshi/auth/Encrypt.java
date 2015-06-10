package com.yunguanshi.auth;

import java.util.Properties;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import com.yunguanshi.controller.rbac.DepartmentController;
import com.yunguanshi.utils.PropUtil;

@SuppressWarnings("all")
public class Encrypt implements FactoryBean<Object>{
	
	private static final Logger console = LoggerFactory.getLogger(Encrypt.class);
	
	private String user = "user";
	private String password = "password";
	private static String setPassword="http://yunguanshi.com";
	private Properties properties;  
     
    public Object getObject() throws Exception {  
        return getProperties();  
    }  
  
    public Class getObjectType() {  
        return java.util.Properties.class;  
    }  
  
    public boolean isSingleton() {  
        return true;  
    }  
  
    public Properties getProperties() {  
        return properties;  
    }  
  
    public void setProperties(Properties inProperties) {  
        this.properties = inProperties; 
        String originalUsername = properties.getProperty(user);  
        String originalPassword = properties.getProperty(password);  
        if (originalUsername != null){  
            String newUsername = originalUsername;
            if(PropUtil.get("encrypt").trim().equals("yes")){
            	newUsername = deEncryptUsername(originalUsername);  
            }
            properties.put(user, newUsername);  
        }
        if (originalPassword != null){  
            String newPassword = originalPassword;
            if(PropUtil.get("encrypt").trim().equals("yes")){
            	newPassword = deEncryptPassword(originalPassword);  
            }
            properties.put(password, newPassword);  
        }

    }  
      
    private String deEncryptUsername(String originalUsername){  
        return deEncryptString(originalUsername);  
    }  
      
    private String deEncryptPassword(String originalPassword){  
        return deEncryptString(originalPassword);  
    }  
    
    private String deEncryptString(String originalString){  
    	BasicTextEncryptor textEncryptor = new BasicTextEncryptor(); 
    	textEncryptor.setPassword(getSetPassword());
    	String newPassword = textEncryptor.decrypt(originalString);
    	return newPassword;
    } 
    
    public String getSetPassword() {
		return setPassword;
	}

	public void setSetPassword(String setPassword) {
		this.setPassword = setPassword;
	}

	public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor(); 
        textEncryptor.setPassword(setPassword);
        String newPassword = textEncryptor.encrypt("root");
        System.out.println(newPassword);
    }
	    
}
