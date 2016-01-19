package com.hzmc.cems.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FTPUtils {
	public static FTPUtils ftpUtils;  
	public static String servername; //FTP服务器名称 
	public static String port; // 服务器端口  
	public static String username; // 用户登录名  
    public static String password; // 用户登录密码  
    public static String path;//ftp路径
    public static String address;//链接地址
    public static String uploadpath;//上传地址
    public InputStream is; // 文件下载输入流  
    /** 
     * 初始化FTP服务器连接属性 
     */  
    public  static void initConfig () {  
        // 构造Properties对象  
        Properties properties = new Properties();  
        // 定义配置文件输入流  
        InputStream is = null;  
        try { 
            // 获取配置文件输入流 
            is = new FileInputStream(new File(getWebRootAppPath()+"WEB-INF"+File.separator+"config"+File.separator+"ftp.properties"));  
            // 加载配置文件  
            properties.load(is);  
            // 读取配置文件  
            servername = (String) properties.get("servername"); // 设置服务名
            port = (String) properties.get("port"); // 设置端口  
            username = (String) properties.get("username"); // 设置用户名  
            password = (String) properties.get("password"); // 设置密码  
            path = (String) properties.get("path");
            uploadpath=(String) properties.get("uploadpath");
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
    } 
    
    /**
	 * 
	 * <p>方法: getWebRootAppPath</p>
	 * <p>描述: 取得webapp根目录</p>
	 * @return String 返回根目录的路径（结尾带'/'）
	 * @throws 抛出异常
	 * @since JDK 1.6
	 */
	public static String getWebRootAppPath(){
		String webRootAppPath = System.getProperty("vm_hos.root");
		if (webRootAppPath != null) {
			char lastch = webRootAppPath.charAt(webRootAppPath.length()-1);
			if (lastch != '/' && lastch != '\\') {
				webRootAppPath = webRootAppPath + File.separator;
			}
		}
		return webRootAppPath;
	}
	
    public static String chackString(String str){
    	if(str!=null){
    		str=str.replace("#", "%23");
    	}
    	
    	return str;
    }
    public static String getdownAddress(){
    	initConfig () ;
    	String url ="";
    	password=chackString(password);
    	if(path.equals("")){
    		url="ftp://"+username+":"+password+"@"+servername+":"+port;
    	}else{
    		url="ftp://"+username+":"+password+"@"+servername+":"+port+path;
    	}
    	return url;
    }
   public static String getUploadpath(){
	   initConfig();
	   return uploadpath;
   }
   
   public static String getPath(){
	   initConfig();
	   return path;
   }
    
}
