package com.yunguanshi.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apkinfo.api.GetApkInfo;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;

/**
 * @author 贵源 <br>
 *         create at 2014年5月10日
 */
public class FileUtils {
	/**
	 * 清除SVN
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean cleanSvn(File dir) {
		try {
			Files.cleanAllFolderInSubFolderes(dir, ".svn");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 统计文件或者目录下的java代码的行数
	 * 
	 * @param file
	 *            文件或者目录
	 * @return java代码行数
	 */
	public static long countJAVACodeLines(File file) {
		return countLines(file, ".java");
	}

	/**
	 * 统计文件或者目录下的指定类型文件的行数
	 * 
	 * @param file
	 *            文件或者目录
	 * @param suf
	 *            扩展名
	 * @return 行数
	 */
	public static long countLines(File file, String suf) {
		long target = 0;
		if (file.isFile() && file.getName().endsWith(suf)) {
			return countLine(file);
		} else if (file.isFile()) {
			return 0;
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				target += countJAVACodeLines(f);
			}
		}
		return target;
	}

	/**
	 * 计算文件行数
	 * 
	 * @param file
	 *            文件(非目录类型)
	 * @return 行数
	 */
	public static long countLine(File file) {
		long target = 0;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			if (reader != null) {
				while (reader.readLine() != null) {
					target++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return target;
	}

	/**
	 * 获取APK版本信息
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getApkVersionInfo(String filePath) {
		try {
			return GetApkInfo.getApkInfoByFilePath(filePath).getVersionName();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 基本实现 文件查找
	 * 
	 * @param dir
	 *            查找的开始位置
	 * @param name
	 *            查找的文件的名字
	 * @return 文件
	 */
	public static File findFile(File dir, String name) {
		File target = null;
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && Strings.equals(file.getName(), name)) {
					return file;
				} else if (file.isDirectory()) {
					target = findFile(file, name);
					if (target != null) {
						return target;
					}
				}
			}
		}
		return target;
	}

	/**
	 * 快速查找
	 * 
	 * @param dir
	 *            基目录
	 * @param name
	 *            带查找文件名
	 * @return 文件
	 */
	public static File fastFindFile(File dir, String name) {
		return fastFindFile(dir, name, 1);
	}

	public static File fastFindFile(String dir, String name) {
		return fastFindFile(new File(dir), name, 1);
	}

	/**
	 * 快速查找
	 * 
	 * @param dir
	 *            基目录
	 * @param name
	 *            带查找文件名
	 * @return 文件
	 */
	public static File fastFindFileLikeName(File dir, String name) {
		return fastFindFile(dir, name, 2);
	}

	/**
	 * 快速查找文件
	 * 
	 * @param dir
	 *            基目录
	 * @param name
	 *            文件名
	 * @param method
	 *            查找方法 1 全等查询 2模糊查找 3 忽略大小写全等 4忽略大小写模糊
	 * @return 文件
	 */
	public static File fastFindFile(File dir, String name, int method) {
		File target = null;
		File[] dirs = Files.dirs(dir);// 获取目录
		File[] files = Files.files(dir, name);// 获取文件
		// 优先扫描文件
		if (files != null) {
			for (File file : files) {
				if (method == 1 ? Strings.equals(file.getName(), name) : method == 2 ? file.getName().endsWith(name) : method == 3 ? Strings.equals(file.getName().toUpperCase(),
						name.toUpperCase()) : file.getName().toUpperCase().endsWith(name.toUpperCase())) {
					return file;
				}
			}
		}
		// 然后扫目录
		if (dirs != null) {
			for (File file : dirs) {
				target = findFile(file, name);
				if (target != null) {
					return target;
				}
			}
		}
		return target;
	}
}
