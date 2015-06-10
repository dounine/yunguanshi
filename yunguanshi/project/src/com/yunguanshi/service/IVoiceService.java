/**
 * 
 */
package com.yunguanshi.service;

import java.util.List;

import com.yunguanshi.model.Voice;

/**
 * 语音记事
 * @author zengguangliang
 *
 */
public interface IVoiceService {
	/**
	 * 添加新的语音记事
	 * @param voice
	 * @return
	 */
	public void add(Voice voice);
	
	/**
	 * 删除语音记事
	 * @param id
	 * @return
	 */
	public void delete(Long id);
	
	/**
	 * 按ID查询语音记事
	 * @param id
	 * @return
	 */
	public Voice findById(Long id);
	
	/**
	 * 查询所有的语音记事
	 * @return
	 */
	public List<Voice> findVoices(String id);
}
