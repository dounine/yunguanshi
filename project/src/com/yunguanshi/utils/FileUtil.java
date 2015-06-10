
package com.yunguanshi.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * description:
 * 
 * @author Franklin －－zhangyixuan
 * @date 2008-4-10 上午10:30:21
 */
@SuppressWarnings("all")
public class FileUtil {

	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	
	
	/**
	 * 按默认字符集读取文件到字符串中
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(String fileName) throws IOException {

		return FileUtil.readFileToString(fileName, Charset.defaultCharset()
				.name());

	}
	
	/**
	 * 追加到文件末尾
	 * @param filename
	 * @param texts
	 */
	public static void appendToFile(String filename,Object[] texts){
	   	 FileOutputStream stream;
	   	 OutputStreamWriter writer;
	   	 try{
	    	   stream = new FileOutputStream(filename, true);
	    	   writer = new OutputStreamWriter(stream);
	    	   for(Object text:texts){
	    		   writer.write ("\r\n");
	    		   writer.write(text.toString());
	     	   }
		       writer.close();
		       stream.close();
	    	}catch(Exception e){
	          e.getStackTrace ();
	    	}
	  	}
	/**
	 * 将文件中的内容读进某个字符串
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 * @throws IOException 
	 * @author Franklin 2008-7-22
	 */
	public static String readFileToString(String fileName, String charset) throws IOException{
		Charset cs = Charset.forName(charset);
		return readFileToString(fileName,cs);
	}
	
	/**
	 * 将文件中的内容读进某个字符串
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 * @throws IOException
	 * @author Franklin 2008-7-22
	 */
	public static String readFileToString(String fileName, Charset cs)
			throws IOException {

		if(fileName==null)
			throw new IOException("fileName must not be null! ::: ");
		File f = new File(fileName);
		StringBuffer buffer = new StringBuffer(); 
		if(f.exists()){
			FileChannel fc = new FileInputStream(f).getChannel();
			ByteBuffer buff = ByteBuffer.allocate(1024 * 3);
			
			while(fc.read(buff)!=-1){
				buff.flip();
				buffer.append(cs.decode(buff));
				buff.clear();
			}
			fc.close();
			return buffer.toString();
		}
		return null;

	}
	
	/**
	 * 将文本写入文件磁盘
	 * 
	 * @param content
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeStringToFile(String content, String fileName)
			throws IOException {
		ensureFile(fileName);

		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			fw.write(content);
		} finally {

			if (fw != null) {
				fw.close();
			}

		}

	}

	public static void appendStringToFile(String content, String fileName)
			throws IOException {
		ensureFile(fileName);

		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			fw.append(content);
		} finally {

			if (fw != null) {
				fw.close();
			}

		}

	}

	public static void ensureDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static File ensureFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}

		return file;

	}

	public static boolean copy(String src, String dest) {
		File outputFile = new File(dest);
		if (!outputFile.exists()) {
			outputFile.getParentFile().mkdirs();
		}
		FileChannel in = null;
		FileChannel out = null;
		try {
			if (!new File(src).exists()) {
				return false;
			}
			in = new FileInputStream(src).getChannel();
			out = new FileOutputStream(dest).getChannel();
			ByteBuffer bb = ByteBuffer.allocate(1024 * 1000);
			while (true) {
				bb.clear();
				int n = in.read(bb);
				if (n == -1) {
					break;
				}
				bb.flip();
				out.write(bb);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void delete(String path){
		File file = new File(path);
		if(file.exists()&&file.isFile()){
			file.delete();
			log.debug("删除"+path+"文件成功");
		}
	}
	
	/**
	 *@Describtion: 根据不同运行平台选择不同系统路径
	 *@return String
	 */
	public static String getSystemPath(){
		String paths = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""));
		if(!"windows".equals(getSystemProperties())){
			return paths.substring(5,paths.indexOf("WEB-INF"));
		}else{
			return paths.substring(6,paths.indexOf("WEB-INF"));
		}
	}
	
	public void delFiles(String path) {  
	       // 首先根据path路径创建一个File对象  
	       File file = new File(path);  
	  
	       // 判断所给的路径是否是一个目录是否存在，如果都成立再继续操作  
	       if (file.exists() && file.isDirectory()) {  
	  
	           // 如果目录下的没有文件则直接删除，否则继续进行  
	           // 后面的操作  
	           if (file.listFiles().length == 0) {  
	                  file.delete();  
	           } else {  
	                  // 如果只习惯到这一步，怎说明目录下还有其他文件或目录，  
	                  // 拿到这个目录下的所有文件对象进行遍历  
	                  File[] delFile = file.listFiles();  
	              int i = delFile.length;  
	              for (int j=0; j<i; i++) {  
	                   // 每遍历一个文件对象，判断其如果是目录，则  
	                   // 调用自身方法进行递归操作，不是目录则直接删除  
	                   if (delFile[j].isDirectory()) {  
	                    delFiles(delFile[j].getAbsolutePath());  
	               }  
	                delFile[j].delete();  
	            }  
	        }  
	    }  
	}  
	
	/**
	 *@Describtion: 获取当前系统格式
	 *@return String
	 */
	public static String getSystemProperties(){
		Properties pop = new Properties(System.getProperties());
		return pop.getProperty("sun.desktop");
	}
	
	public static void writeFile(String filePath, String data){
    FileOutputStream fos = null;
    OutputStreamWriter writer = null;
	     try {
	       fos = new FileOutputStream(new File(filePath));
	       writer = new OutputStreamWriter(fos, "UTF-8");
	       writer.write(data);
	     } catch (Exception ex) {
	    	 ex.printStackTrace();
	       try
	       {
	         if (writer != null) {
	           writer.close();
	         }
	         if (fos != null)
	           fos.close();
	       }
	       catch (Exception localException1)
	       {
	       }
	     }finally{
	       try{
	         if (writer != null) {
	           writer.close();
	         }
	         if (fos != null)
	           fos.close();
	       }
	       catch (Exception localException2)
	       {
	       }
	     }
	   }
	
	/**
	 * 
	 * @Description:写入文件流
	 * @param data
	 * @param filename
	 * @return void
	 */
	public static void writeFile(byte[] data,String filename){
		try{
			File file=new File(filename);
			file.getParentFile().mkdir();
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
			bos.write(data);
			bos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description:读取文件流
	 * @param file
	 * @return
	 * @return byte[]
	 */
	public static byte[] readFile(File file){
		byte[] bytes=null;
		try{
			//File tempFil=new File(request.getParameter("file"));
			long len=file.length();
			bytes=new byte[(int)len];
			BufferedInputStream bfis=new BufferedInputStream(new FileInputStream(file));
			int r=bfis.read(bytes);
			if(r!=len){
				throw new IOException("读取文件不正确");
			}
			bfis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bytes;
	}
	
	 public static String readFile(String filePath){
	     StringBuffer buffer = new StringBuffer();
	     try{
	       File file = new File(filePath);
	       FileInputStream fis = null;
	       BufferedReader breader = null;
	       try {
	         fis = new FileInputStream(file);
	         InputStreamReader isReader = new InputStreamReader(fis, "UTF-8");
	       	 breader = new BufferedReader(isReader);
	         String line;
	         while ((line = breader.readLine()) != null)
	         {
	           buffer.append(line);
	           buffer.append("\r\n");
	         }
	         breader.close();
	         isReader.close();
	         fis.close();
	       }
	       catch (FileNotFoundException e) {
	         e.printStackTrace();
	       } catch (IOException e) {
	         e.printStackTrace();
	       }
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	     return buffer.toString();
	   }
	 
	  public static void rewrite(File file, String data) {
	        BufferedWriter bw = null;
	        try {
	            bw = new BufferedWriter(new FileWriter(file));
	            bw.write(data);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {       
	            if(bw != null) {
	               try {
	                   bw.close();
	               } catch(IOException e) {
	                   e.printStackTrace();
	               }
	            }           
	        }
	    }
	   
	    public static List<String> readList(File file) {
	        BufferedReader br = null;
	        List<String> data = new ArrayList<String>();
	        try {
	            br = new BufferedReader(new FileReader(file));
	            for(String str = null; (str = br.readLine()) != null; ) {
	                data.add(str);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if(br != null) {
	               try {
	                   br.close();
	               } catch(IOException e) {
	                   e.printStackTrace();
	               }
	            }
	        }
	        return data;
	    }
	    
	    /**   
	     * zip压缩功能,压缩sourceFile(文件夹目录)下所有文件，包括子目录 
	     * @param  sourceFile,待压缩目录; toFolerName,压缩完毕生成的目录 
	     * @throws Exception   
	     */  
	    public static void fileToZip(String sourceFile, String toFolerName) throws Exception {  
	  
	        List fileList = getSubFiles(new File(sourceFile)); //得到待压缩的文件夹的所有内容    
	        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(  
	                toFolerName));  
	  
	        ZipEntry ze = null;  
	        byte[] buf = new byte[1024];  
	        int readLen = 0;  
	        for (int i = 0; i < fileList.size(); i++) { //遍历要压缩的所有子文件    
	            File file = (File) fileList.get(i);  
	            ze = new ZipEntry(getAbsFileName(sourceFile, file));  
	            ze.setSize(file.length());  
	            ze.setTime(file.lastModified());  
	            zos.putNextEntry(ze);  
	            InputStream is = new BufferedInputStream(new FileInputStream(file));  
	            while ((readLen = is.read(buf, 0, 1024)) != -1) {  
	                zos.write(buf, 0, readLen);  
	            }  
	            is.close();  
	        }  
	        zos.close();  
	    }  
	    
	    /**   
	     * 取得指定目录下的所有文件列表，包括子目录.   
	     * @param baseDir File 指定的目录   
	     * @return 包含java.io.File的List   
	     */  
	    private static List<File> getSubFiles(File baseDir) {  
	        List<File> ret = new ArrayList<File>();  
	        File[] tmp = baseDir.listFiles();  
	        for (int i = 0; i < tmp.length; i++) {  
	            if (tmp[i].isFile())  
	                ret.add(tmp[i]);  
	            if (tmp[i].isDirectory())  
	                ret.addAll(getSubFiles(tmp[i]));  
	        }  
	        return ret;  
	    }  
	    
	    /**   
	     * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.   
	     * @param baseDir java.lang.String 根目录   
	     * @param realFileName java.io.File 实际的文件名   
	     * @return 相对文件名   
	     */  
	    private static String getAbsFileName(String baseDir, File realFileName) {  
	        File real = realFileName;  
	        File base = new File(baseDir);  
	        String ret = real.getName();  
	        while (true) {  
	            real = real.getParentFile();  
	            if (real == null)  
	                break;  
	            if (real.equals(base))  
	                break;  
	            else  
	                ret = real.getName() + "/" + ret;  
	        }  
	        return ret;  
	    }
	    
	    /**
		    * inputFileName 输入一个文件夹
		    * zipFileName 输一个压缩文件路径
		    **/
		    public static void zip(String zipFileName,String inputFileName) throws Exception {
		        zip(zipFileName, new File(inputFileName));
		    }

		    private static void zip(String zipFileName, File inputFile) throws Exception {
		        ZipOutputStream out = null;
		        try{
		        	out = new ZipOutputStream(new FileOutputStream(zipFileName));
			        zip(out, inputFile, "");
		        }catch(Exception e){
		        	e.printStackTrace();
		        }finally{
		        	if(out != null)out.close();
		        }
		    }

		    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		        if (f.isDirectory()) {
		           File[] fl = f.listFiles();
		           out.putNextEntry(new ZipEntry(base + "/"));
		           base = base.length() == 0 ? "" : base + "/";
		           for (int i = 0; i < fl.length; i++) {
		           zip(out, fl[i], base + fl[i].getName());
		         }
		        }else {
		           out.putNextEntry(new ZipEntry(base));
		           FileInputStream in = new FileInputStream(f);
		           int b;
		           while ( (b = in.read()) != -1) {
		            out.write(b);
		         }
		         in.close();
		       }
		    }
		    
		    
}
