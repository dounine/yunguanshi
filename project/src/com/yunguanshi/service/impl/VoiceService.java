package com.yunguanshi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.model.Voice;
import com.yunguanshi.service.IVoiceService;

@Service
public class VoiceService implements IVoiceService {
	
	@Resource
	BaseDao<Voice> voiceDao;
	
	
	@Override
	public void add(Voice voice) {
		voiceDao.save(voice);
	}

	@Override
	public void delete(Long id) {
		voiceDao.delete(Voice.class, id);
	}

	@Override
	public Voice findById(Long id) {
		return voiceDao.findById(Voice.class, id);
	}

	@Override
	public List<Voice> findVoices(String id) {
		String hql = "from voice where userid = ?";
		return voiceDao.find(hql, id);
	}

}
