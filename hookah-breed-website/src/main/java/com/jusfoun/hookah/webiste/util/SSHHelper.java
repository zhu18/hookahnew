package com.jusfoun.hookah.webiste.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * SSH工具类
 * 
 * @author 赵聪慧 2013-4-7
 */
public class SSHHelper {
	
	private static Logger logger = LoggerFactory.getLogger(SSHHelper.class);
	// ssh服务器的登入IP
	private static final String ip = PropertiesManager.getInstance().getProperty("upload.ip");
	// ssh服务器的登入端口
	private static final int port = Integer.valueOf(PropertiesManager.getInstance().getProperty("upload.port"));
	// ssh服务器的登入用户名
	private static final String user = PropertiesManager.getInstance().getProperty("upload.user");
	// ssh服务器的登入密码
	private static final String password = PropertiesManager.getInstance().getProperty("upload.password");
	// ssh服务器的绝对路径
	private static final String rootPath = PropertiesManager.getInstance().getProperty("upload.rootpath");

	/**
	 * 获取ssh session对象
	 * 
	 * @throws JSchException
	 */
	private Session getSession() throws JSchException {
		JSch jsch = new JSch();
		Session session = null;
		session = jsch.getSession(user, ip, port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		session.connect();
		return session;
	}

	public void file_upload(String src, String dst) throws IOException {
		Session session = null;
		FileInputStream inputSrr = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
//			inputSrr = new FileInputStream(filename);
//			sftpChannel.put(inputSrr, "/home/devel/aa.txt");
			//檢查目標是否存在
			exec("mkdir -p " +rootPath+dst.substring(0, dst.lastIndexOf("/")));
			sftpChannel.put(src,rootPath+dst);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			if (inputSrr != null) {
				inputSrr.close();
			}
			if (sftpChannel != null) {
				sftpChannel.exit();
			}
			if (session != null) {
				session.disconnect();
			}

		}
	}
	
	
	
	public void file_upload(InputStream src, String dst) throws IOException {
		Session session = null;
		FileInputStream inputSrr = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;
//			inputSrr = new FileInputStream(filename);
//			sftpChannel.put(inputSrr, "/home/devel/aa.txt");
			//檢查目標是否存在
			exec("mkdir -p " +rootPath+dst.substring(0, dst.lastIndexOf("/")));
			logger.info("fileupload dstpath={}",rootPath+dst);
			sftpChannel.put(src, rootPath+dst);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			if (inputSrr != null) {
				inputSrr.close();
			}
			if (sftpChannel != null) {
				sftpChannel.exit();
			}
			if (session != null) {
				session.disconnect();
			}

		}
	}

	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 *
	 * @param command
	 *            命令
	 * @return
	 * @throws IOException
	 */
	public String exec(String command) throws IOException {
		logger.info("exec command :{}",command);
		String result = "";
		Session session = null;
		ChannelExec openChannel = null;
		InputStream in = null;
		BufferedReader reader = null;
		try {
			session = getSession();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			int exitStatus = openChannel.getExitStatus();
			System.out.println(exitStatus);
			openChannel.connect();
			in = openChannel.getInputStream();
			reader = new BufferedReader(
					new InputStreamReader(in));
			String buf = null;
			while ((buf = reader.readLine()) != null) {
				result += new String(buf.getBytes("gbk"), "UTF-8")
						+ "    <br>\r\n";
			}
		} catch (Exception e) {
			result += e.getMessage();
		} finally {
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader!=null){
				reader.close();
			}
			if (openChannel != null && !openChannel.isClosed()) {
				openChannel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		return result;
	}

//	public static void main(String args[]) throws IOException {
////		String exec = exec("192.168.1.254", "root", "123456", 22,
////				"sleep 20;ls;");
////		System.out.println(exec);
//		SSHHelper sh = new SSHHelper();
////		sh.file_upload("/home/devel/work.log");
//		File file = new File("D://qing.txt");
//		InputStream is = new FileInputStream(file);
//		sh.file_upload(is,"/aa/bb/dd/cc/bbb");
//	}
}