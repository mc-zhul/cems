package com.hzmc.cems.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

public class FtpUtils2 {

	/**
	 * 初始化FTP服务器连接属性
	 */
	public boolean ftpConnect(FTPClient ftp) {
		// 构造Properties对象
		Properties properties = new Properties();
		String servername = "";
		String port = "";
		String username = "";
		String password = "";
		String path = "";
		int reply;
		// 定义配置文件输入流
		InputStream is = null;
		try {
			// 获取配置文件输入流
			is = FTPUtils.class.getResourceAsStream("/ftp.properties");
			// 加载配置文件
			properties.load(is);
			// 读取配置文件
			servername = (String) properties.get("servername"); // 设置服务名
			port = (String) properties.get("port"); // 设置端口
			username = (String) properties.get("username"); // 设置用户名
			password = (String) properties.get("password"); // 设置密码
			path = (String) properties.get("path");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 判断输入流是否为空
			if (null != is) {
				try {
					// 关闭输入流
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 连接FTP服务器
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		try {
			ftp.connect(servername, Integer.valueOf(port));
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功

			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.out.println("连接服务器失败");
				return false;
			} else {

				ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录
				String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
				// 转到指定上传目录
				ftp.changeWorkingDirectory(path1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("登陆服务器成功");

		return true;
	}

	/**
	 * 
	 * @author 杨超
	 * @throws IOException
	 * @time 2015年7月28日 下午3:51:48
	 */
	public String uploadFile(MultipartFile file) throws IOException {
		boolean success = false;
		String downPath="";
		long size = file.getSize() / (1024 * 1024);
		System.out.println("文件大小" + size + "mb");// 打印文件大小
		InputStream input = null;
		input = file.getInputStream();
		FTPClient ftp = new FTPClient();
		String fileName = file.getOriginalFilename();

		try {
			success = ftpConnect(ftp);
			if (success == false) {
				return "false";
			}
			// 设置缓存
			ftp.setBufferSize(3072);
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			// System.out.println(fs.length);
			System.out.println(fileName);
			String filename1 = changeName(fileName, fs);
			filename1 = renameFile(filename1);
			String filename2 = new String(filename1.getBytes("GBK"), "ISO-8859-1");
			// 将上传文件存储到指定目录
			// ftp.appendFile(new String(filename.getBytes("GBK"),"ISO-8859-1"),
			// input);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
			Date a = new Date();
			long aa = a.getTime();// 获取时间
			String prefix = String.valueOf(new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(a));
			System.out.println(prefix + "正在上传....");
			ftp.storeFile(filename2, input);
			// 关闭输入流
			input.close();
			// 退出ftp
			ftp.logout();
			// 表示上传成功
			//success = true;
			Date b = new Date();
			long bb = b.getTime();// 获取时间
			String prefixB = String.valueOf(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(b));
			System.out.println(prefixB + "上传成功....");
			double s = (bb - aa) / 1000.00;
			System.out.println("上传时间：" + s + "S");
			DecimalFormat df = new DecimalFormat("######0.00");
			double v = size / s;
			System.out.println("速度为:" + df.format(v) + "MB/S");
			
			 downPath="\\"+filename1;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();

				} catch (IOException ioe) {
				}
			}
		}
		return downPath;
	}

	/**
	 * 
	 * 删除程序
	 * 
	 * 
	 * 
	 */

	public boolean deleteFile(String url, int port, String username,

	String password, String path, String filename) {

		// filename:要上传的文件
		// path :上传的路径
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {

			int reply;

			// 连接FTP服务器

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器

			ftp.connect(url, port);

			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件

			ftp.setControlEncoding("GBK");

			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);

			conf.setServerLanguageCode("zh");

			// 登录ftp

			ftp.login(username, password);

			// 看返回的值是不是230，如果是，表示登陆成功

			reply = ftp.getReplyCode();

			// 以2开头的返回值就会为真

			if (!FTPReply.isPositiveCompletion(reply)) {

				ftp.disconnect();

				System.out.println("连接服务器失败");

				return success;

			}

			System.out.println("登陆服务器成功");

			String filename2 = new String(filename.getBytes("GBK"),

			"ISO-8859-1");

			String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");

			// 转到指定上传目录

			ftp.changeWorkingDirectory(path1);

			ftp.deleteFile(filename2);

			ftp.logout();

			success = true;

		} catch (IOException e) {

			System.out.println(e);

		} finally {

			if (ftp.isConnected()) {

				try {

					ftp.disconnect();

				} catch (IOException ioe) {

				}

			}

		}

		return success;

	}

	/**
	 * 
	 * 下载程序
	 * 
	 * 
	 * 
	 */

	public static boolean downFile(String ip, int port, String username,

	String password, String remotePath, String fileName,

	OutputStream outputStream, HttpServletResponse response) {

		boolean success = false;

		FTPClient ftp = new FTPClient();

		try {

			int reply;

			ftp.connect(ip, port);

			// 下面三行代码必须要，而且不能改变编码格式

			ftp.setControlEncoding("GBK");

			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);

			conf.setServerLanguageCode("zh");

			// 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器

			ftp.login(username, password);// 登录

			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {

				ftp.disconnect();

				return success;

			}

			System.out.println("登陆成功。。。。");

			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录

			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表

			// System.out.println(fs.length);//打印列表长度

			for (int i = 0; i < fs.length; i++) {

				FTPFile ff = fs[i];

				if (ff.getName().equals(fileName)) {

					String filename = fileName;

					// 这个就就是弹出下载对话框的关键代码

					response.setHeader("Content-disposition",

					"attachment;filename="

					+ URLEncoder.encode(filename, "utf-8"));

					// 将文件保存到输出流outputStream中

					ftp.retrieveFile(new String(ff.getName().getBytes("GBK"),

					"ISO-8859-1"), outputStream);

					outputStream.flush();

					outputStream.close();

				}

			}

			ftp.logout();

			success = true;

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			if (ftp.isConnected()) {

				try {

					ftp.disconnect();

				} catch (IOException ioe) {

				}

			}

		}

		return success;

	}

	// 判断是否有重名方法

	public static boolean isDirExist(String fileName, FTPFile[] fs) {

		for (int i = 0; i < fs.length; i++) {

			FTPFile ff = fs[i];

			if (ff.getName().equals(fileName)) {

				return true; // 如果存在返回 正确信号

			}

		}

		return false; // 如果不存在返回错误信号

	}

	// 根据重名判断的结果 生成新的文件的名称

	public static String changeName(String filename, FTPFile[] fs) {

		int n = 0;

		// 创建一个可变的字符串对象 即StringBuffer对象，把filename值付给该对象

		StringBuffer filename1 = new StringBuffer("");

		filename1 = filename1.append(filename);

		System.out.println(filename1);

		while (isDirExist(filename1.toString(), fs)) {

			n++;

			String a = "[" + n + "]";

			System.out.println("字符串a的值是：" + a);

			int b = filename1.lastIndexOf(".");// 最后一出现小数点的位置

			int c = filename1.lastIndexOf("[");// 最后一次"["出现的位置

			if (c < 0) {

				c = b;

			}

			StringBuffer name = new StringBuffer(filename1.substring(0, c));// 文件的名字

			StringBuffer suffix = new StringBuffer(filename1.substring(b + 1));// 后缀的名称

			filename1 = name.append(a).append(".").append(suffix);

		}

		return filename1.toString();

	}

	/**
	 * 上传时间
	 * 
	 * @return
	 */
	public static String uploadTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		String uploadTime = sdf.format(System.currentTimeMillis());
		return uploadTime;
	}

	public static String renameFile(String fileName) {
		String prefix = String.valueOf(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
		// 得到文件的扩展名(无扩展名时将得到全名)
		// String t_ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		String file_name = prefix + "_" + fileName;
		return file_name;
	}

}
