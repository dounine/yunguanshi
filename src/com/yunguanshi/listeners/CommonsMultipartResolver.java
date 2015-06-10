package com.yunguanshi.listeners;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yunguanshi.utils.PropUtil;

/**
 * 重写文件上传.
 * @author huanghuanlai
 *
 */
public class CommonsMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {
	private HttpServletRequest request;

	@Override
	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		upload.setSizeMax(-1);
		if (request != null) {
			FileUploadListener uploadProgressListener = new FileUploadListener();
			upload.setProgressListener(uploadProgressListener);
			HttpSession session = request.getSession();
			session.setAttribute(PropUtil.get("uploadProgressListener"),
					uploadProgressListener);
		}
		return upload;
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(
			HttpServletRequest request) throws MultipartException {
		this.request = request;// 获取到request,要用到session
		return super.resolveMultipart(request);
	}
}
