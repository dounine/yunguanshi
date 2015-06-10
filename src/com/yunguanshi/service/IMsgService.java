package com.yunguanshi.service;

import com.yunguanshi.model.Message;

/**
 * 消息服务接口.
 * @author huanghuanlai
 *
 */
public interface IMsgService {
	
	/**
	 * 添加一个消息 
	 * @param msg
	 */
	public void add(Message msg);
	
}

