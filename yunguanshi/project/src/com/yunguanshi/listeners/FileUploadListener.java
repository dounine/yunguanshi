package com.yunguanshi.listeners;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 文件上传侦听类.
 * @author huanghuanlai
 *
 */
@SuppressWarnings("all")
public class FileUploadListener implements ProgressListener {

	private long num100Ks = 0;
	private long theBytesRead = 0;
	private long theContentLength = -1;
	private int whichItem = 0;
	private int percentDone = 0;
	private boolean contentLengthKnown = false;

	public void update(long bytesRead, long contentLength, int items) {

		if (contentLength > -1) {
			contentLengthKnown = true;
		}
		theBytesRead = bytesRead;
		theContentLength = contentLength;
		whichItem = items;

		long nowNum100Ks = bytesRead / 1000;
		if (nowNum100Ks > num100Ks) {
			num100Ks = nowNum100Ks;
			if (contentLengthKnown) {
				percentDone = (int) Math.round(100.00 * bytesRead
						/ contentLength);
			}

		}
	}

	public int getPercentDone() {
		return percentDone;
	}

}
