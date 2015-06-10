package com.yunguanshi.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * zip解压压缩工具
 * 
 * @author Kerbores
 * 
 */
@SuppressWarnings("all")
public class ZipUtils {
	private ZipFile zipFile;
	private ZipOutputStream zipOut; // 压缩Zip
	// private ZipEntry zipEntry;
	private int bufSize; // size of bytes
	private byte[] buf;
	private int readedBytes;

	public ZipUtils() {
		this(512);
	}

	public ZipUtils(int bufSize) {
		this.bufSize = bufSize;
		this.buf = new byte[this.bufSize];
	}

	/**
	 * 压缩文件
	 * 
	 * @param zipDirectory
	 *            待压缩的目录
	 * @param out
	 *            输出压缩文件
	 */
	public void doZip(String zipDirectory, File out) {// zipDirectoryPath:需要压缩的文件夹名
		File zipDir;
		zipDir = new File(zipDirectory);
		try {
			this.zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(out)));
			handleDir(zipDir, this.zipOut);
			this.zipOut.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// 由doZip调用,递归完成目录文件读取
	private void handleDir(File dir, ZipOutputStream zipOut) throws IOException {
		FileInputStream fileIn;
		File[] files;
		files = dir.listFiles();
		if (files.length == 0) {// 如果目录为空,则单独创建之.
			// ZipEntry的isDirectory()方法中,目录以"/"结尾.
			this.zipOut.putNextEntry(new ZipEntry(dir.toString() + "/"));
			this.zipOut.closeEntry();
		} else {// 如果目录不为空,则分别处理目录和文件.
			for (File fileName : files) {
				if (fileName.isDirectory()) {
					handleDir(fileName, this.zipOut);
				} else {
					fileIn = new FileInputStream(fileName);
					this.zipOut.putNextEntry(new ZipEntry(fileName.toString()));
					while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
						this.zipOut.write(this.buf, 0, this.readedBytes);
					}
					this.zipOut.closeEntry();
				}
			}
		}
	}

	/**
	 * 解压自拍文件
	 * 
	 * @param unZipfileName
	 *            待解压文件
	 * @param path
	 *            解压到目录
	 */
	public void unZip(String unZipfileName, String path) {// unZipfileName需要解压的zip文件名
		FileOutputStream fileOut;
		File file;
		InputStream inputStream;
		try {
			this.zipFile = new ZipFile(unZipfileName);
			for (Enumeration entries = this.zipFile.getEntries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				file = new File(path + entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					// 如果指定文件的目录不存在,则创建之.
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					inputStream = zipFile.getInputStream(entry);
					fileOut = new FileOutputStream(file);
					while ((this.readedBytes = inputStream.read(this.buf)) > 0) {
						fileOut.write(this.buf, 0, this.readedBytes);
					}
					fileOut.close();
					inputStream.close();
					// }

				}
			}
			this.zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// 设置缓冲区大小
	public void setBufSize(int bufSize) {
		this.bufSize = bufSize;
	}
}