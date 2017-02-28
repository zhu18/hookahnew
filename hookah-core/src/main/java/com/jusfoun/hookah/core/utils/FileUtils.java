package com.jusfoun.hookah.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class FileUtils {
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);
	
	public static boolean checkDirectory(String path){
		File dir = new File(path);
		if(dir.isDirectory()){
			 if( dir.exists() ){
				 if(dir.canRead()){
					 return true;
				 }else{
					 log.error("can not read directory:"+path);
				 }
			 }else{
				 log.error("directory["+path+"] doesn't exist");
			 }
		}else{
			log.error("path ["+path+"] is not a directory" );
		}
		return false;
	}
	
	/**
	 * 遍历文件夹中文件
	 * 
	 * @param filepath
	 * @return 返回file［］数组
	 */
	public static File[] getFileList(String filePath){
		return recursionDirectory(filePath,null);
	} 
	private static File[] recursionDirectory(String filePath, List<File> fileLst) {
		if(fileLst==null){
			fileLst = new LinkedList<File>();
		}
		File root = new File(filePath);
		File[] files = root.listFiles();
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					/*
					 * 递归调用
					 */
					recursionDirectory(file.getCanonicalPath(), fileLst);
				} else {
					fileLst.add(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileLst.toArray(files);
	}
	/**
	 * 遍历文件夹中文件，获取文件名
	 * 
	 * @param filepath
	 * @return 返回file［］数组
	 */
	public static String[] getFileNameList(String filePath) {
		System.out.println(filePath);
		File[] files = recursionDirectory(filePath,null);
		String[] fileNameArray = new String[files.length];
		try{
			for(int i=0;i<files.length;i++){
				File f = files[i];
				fileNameArray[i]=f.getCanonicalPath();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileNameArray;
	}

	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName
	 *            <带有路径的>文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(String file, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(file);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data).append("\n");
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			if (st != null && st.length() > 1)
				st = st.substring(0, st.length() - 1);
		} catch (IOException es) {
			st = "";
			es.printStackTrace();
		}
		return st;
	}
	
	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName
	 *            <带有路径的>文件名
	 * @param encoding
	 *            文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public static String readTxt(File file, String encoding)
			throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(file);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data);
				}
			} catch (Exception e) {
				str.append(e.toString());
			}
			st = str.toString();
			if(st!=null){
				st = st.trim();
			}
		} catch (IOException es) {
			st = "";
			es.printStackTrace();
		}
		return st;
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public static String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			File myFilePath = new File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 * 
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径
	 */
	public static String createFolders(String folderPath, String paths) {
		String txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			StringTokenizer st = new StringTokenizer(paths, "|");
			for (int i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txts;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @return
	 */
	public static void writeFile(String filePathAndName, String content) {    
		log.info(filePathAndName);
		writeFile(filePathAndName,content,"UTF-8",true);
    }  
	
	/**
	 * 有编码方式的文件创建
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @param fileContent
	 *            文本文件内容
	 * @param encoding
	 *            编码方式 例如 GBK 或者 UTF-8
	 * @return
	 */
	public static void writeFile(String filePathAndName, String conent,String charset,boolean append) {     
        BufferedWriter out = null;     
        try {
        	String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myFilePath, append),charset));     
            out.write(conent);     
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
    }  

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public static boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				log.error(filePathAndName + "删除文件操作出错");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bea;
	}

	/**
	 * 删除文件
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	public static void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) { // 文件存在
				InputStream inStream = new FileInputStream(oldPathFile); // 读入源文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 建立一个可以追加的bufferedwriter
	 * 
	 * @param fileDir
	 * @param fileName
	 * @return
	 */
	public static BufferedWriter getWriter(String fileDir, String fileName) {
		try {
			File f1 = new File(fileDir);
			if (!f1.exists()) {
				f1.mkdirs();
			}
			f1 = new File(fileDir, fileName);
			if (!f1.exists()) {
				f1.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f1.getPath(),
					true));
			return bw;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到一个bufferedreader
	 * 
	 * @param fileDir
	 * @param fileName
	 * @param encoding
	 * @return
	 */
	public static BufferedReader getReader(String fileDir, String fileName,
			String encoding) {
		try {
			File file = new File(fileDir, fileName);
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);
			BufferedReader br = new BufferedReader(read);
			return br;

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static String getShortName(File f){
    	return (f != null) ? StringUtils.replace(f.getName(),"."+getExtension(f.getName()),"") : ""; 
    }
    
    public static String getShortName(String filename){
    	String seperatorRegex = "\\\\";
    	if(filename.contains("/")){
    		seperatorRegex="/";
    	}
    	String[] dirs = StringUtils.replace(filename,"."+getExtension(filename),"").split(seperatorRegex);
    	return dirs[dirs.length-1];
    }
	
    /**
	 * Return the extension portion of the file's name .
	 * 
	 * @see #getExtension
	 */ 
    public static String getExtension(File f) { 
        return (f != null) ? getExtension(f.getName()) : ""; 
    } 

    public static String getExtension(String filename) { 
        return getExtension(filename, ""); 
    } 
    
    public static String getExtension(String filename, String defExt) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int i = filename.lastIndexOf('.'); 

            if ((i >-1) && (i < (filename.length() - 1))) { 
                return filename.substring(i + 1); 
            } 
        } 
        return defExt; 
    } 

    public static String trimExtension(String filename) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int i = filename.lastIndexOf('.'); 
            if ((i >-1) && (i < (filename.length()))) { 
                return filename.substring(0, i); 
            } 
        } 
        return filename; 
    } 
}