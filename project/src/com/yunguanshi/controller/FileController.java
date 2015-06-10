package com.yunguanshi.controller;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yunguanshi.listeners.FileUploadListener;
import com.yunguanshi.utils.JsonBuilder;

/**
 * 文件处理类 
 * @author huanghuanlai
 *
 */
@Controller
@RequestMapping("file")
public class FileController {
	
	private JsonBuilder jsonBuilder = new JsonBuilder();
	
	private static final Logger console = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value = "/upload.html", method = RequestMethod.POST)
	public String upload(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request) {
		console.info("文件开始上传了");
		if (!file.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/");
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			File file2 = new File(path, new Date().getTime() + fileType);
			try {
				file.getFileItem().write(file2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "file/ok_fileupload";
		}
		return "file/error_fileupload";
	}

	@RequestMapping(value = "/upload.html", method = RequestMethod.GET)
	public String upload() {
		console.info("进入上传文件页面了");
		return "file/upload";
	}

	@RequestMapping(value = "/progress.html", method = RequestMethod.POST)
	@ResponseBody
	public String progress(HttpSession session) {
		console.info("进入读取等待条了");
		FileUploadListener uploadProgressListener = (FileUploadListener) session
				.getAttribute("uploadProgressListener");
		int ret = uploadProgressListener.getPercentDone();
		console.info("new ->:" + String.valueOf(ret));
		return jsonBuilder.toString(ret);
	}
	
	@RequestMapping(value="/import.html",method=RequestMethod.GET)
	public String importExcelGet(){
		return "file/upload";
	}
}
