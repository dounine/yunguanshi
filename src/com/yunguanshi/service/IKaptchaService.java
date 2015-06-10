package com.yunguanshi.service;

import java.util.List;

import com.yunguanshi.model.Kaptcha;

public interface IKaptchaService {

	public List<Kaptcha> findKaptchas(String email, String ipAdress);
	
	public List<Kaptcha> findKaptchas(String ipAdress);
	
	public List<Kaptcha> findKaptchasForEmail(String email);
	
	public void add(Kaptcha kaptcha);
	
	public void deleteAll(String email,String ipAdress);
	
	public void deleteById(Long id);
	
	public void updateErrorCount(String email, String ipAdress);
}
