package com.yunguanshi.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunguanshi.model.Voice;
import com.yunguanshi.service.IVoiceService;
import com.yunguanshi.utils.JsonBuilder;

/**
 * 语音记事
 * @author zengguangliang
 *
 */
@Controller
@RequestMapping("voice")
public class VoiceController {
	
	private static final Logger console = LoggerFactory.getLogger(VoiceController.class);
	
	@Resource
	IVoiceService voiceService;
	
	JsonBuilder jsonBuilder = new JsonBuilder();
	
	/**
	 * 添加语音记事
	 * @param voice
	 * @return
	 */
	@RequestMapping(value="setVoice.html")
	@ResponseBody
	public String setVoice(Voice voice){
		console.info("语音保存成功");
		voiceService.add(voice);
		return "asdf";
	}
	
	/**
	 * 删除语音记事
	 * @param voice
	 * @return
	 */
	@RequestMapping(value="deleteVoice.html")
	@ResponseBody
	public void deleteVoice(Voice voice){
		voiceService.delete(voice.getId());
		console.info("删除语音成功");
	}
	
	/**
	 * 根据userid查询所有语音记事
	 * @param voice
	 */
	@RequestMapping(value="getVoice.html")
	@ResponseBody
	public String getVoice(Voice voice){
		List<Voice> voices = voiceService.findVoices(voice.getUser().getUserId());
		return jsonBuilder.toJson(voices);
	}
	
	/**
	 * 根据id来查询语音记事
	 * @param voice
	 */
	@RequestMapping(value="getByIdVoice.html")
	@ResponseBody
	public String getByIdVoice(Voice voice){
		Voice voices = voiceService.findById(voice.getId());
		return jsonBuilder.toJson(voices);
	}
}
