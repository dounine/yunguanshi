package com.yunguanshi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunguanshi.dao.BaseDao;
import com.yunguanshi.model.Message;
import com.yunguanshi.service.IMsgService;

/**
 * 消息接口实现.
 * @author huanghuanlai
 *
 */
@Service
public class MsgService implements IMsgService{
	
	@Resource
	BaseDao<Message> msgDao;
	
	public void add(Message msg) {
		msgDao.save(msg);
	}

}
