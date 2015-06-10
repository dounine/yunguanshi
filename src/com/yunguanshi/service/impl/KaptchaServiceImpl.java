package com.yunguanshi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.model.Kaptcha;
import com.yunguanshi.service.IKaptchaService;

@Service
public class KaptchaServiceImpl implements IKaptchaService{
	
	@Resource
	BaseDao<Kaptcha> baseDao;

	@Override
	public List<Kaptcha> findKaptchas(String email, String ipAdress) {
		List<Object> params = new ArrayList<Object>(2);
		params.add(email);
		params.add(ipAdress);
		return baseDao.findByhql("from Kaptcha k where k.email=? and k.ipAdress=? order by k.id desc,k.createTime desc,k.errorCount", params);
	}

	@Override
	public void add(Kaptcha kaptcha) {
		baseDao.save(kaptcha);
	}

	@Override
	public void deleteAll(String email, String ipAdress) {
		List<String> mList = new ArrayList<String>(2);
		mList.add(email);
		mList.add(ipAdress);
		baseDao.deleteByhql("delete Kaptcha k where k.email=? and k.ipAdress=?",mList);
	}

	@Override
	public void deleteById(Long id) {
		Kaptcha kaptcha = new Kaptcha();
		kaptcha.setId(id);
		baseDao.delete(Kaptcha.class, id);
	}
	
	public void updateErrorCount(String email, String ipAdress){
		List<Kaptcha> kaptchas = findKaptchas(email, ipAdress);
		if(kaptchas!=null&&kaptchas.size()>0){
			kaptchas.get(0).setErrorCount(kaptchas.get(0).getErrorCount()+1);
			baseDao.update(kaptchas.get(0));
		}
	}

	@Override
	public List<Kaptcha> findKaptchas(String ipAdress) {
		List<Object> params = new ArrayList<Object>(1);
		params.add(ipAdress);
		return baseDao.findByhql("from Kaptcha k where k.ipAdress=? order by k.id desc,k.createTime desc", params);
	}
	
	@Override
	public List<Kaptcha> findKaptchasForEmail(String email) {
		List<Object> params = new ArrayList<Object>(1);
		params.add(email);
		return baseDao.findByhql("from Kaptcha k where k.email=? order by k.id desc,k.createTime desc", params);
	}

}
