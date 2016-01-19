package com.hzmc.cems.util;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;


public class ZIPFileUtil {

	private static final Project DEFAULT_PROJECT = new Project();

	/**
	 * 压缩
	 * 
	 * @author 杨超
	 * @time 2015年7月30日 上午11:21:23
	 */
	public static long zip(List<File> sources, File zipFile) {
		int PathNum=100;
		Zip zip = new Zip();
		zip.setProject(DEFAULT_PROJECT);
		zip.setDestFile(zipFile);
		FileSet fs = new FileSet();
		fs.setProject(DEFAULT_PROJECT);
		for (File file : sources) {
			String path=file.getPath();
			String pathDir[]=path.split("\\\\");
			if(pathDir.length>PathNum){
				String path1=file.getPath().substring(0, file.getPath().lastIndexOf("\\")+1);
				fs.setFile(new File(path1));
			}else{
				PathNum=pathDir.length;
				fs.setFile(file);
			}
			
		}
		zip.addFileset(fs);
		zip.execute();
		return zipFile.length();
	}

	/**
	 * 测试
	 * 
	 * @author 杨超
	 * @time 2015年7月30日 上午11:20:42
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		List<File> flie = new ArrayList<File>();
		flie.add(new File("D:\\1.txt"));
		flie.add(new File("D:\\2.txt"));
		flie.add(new File("D:\\3\\2.txt"));
		File target = new File("D:\\aa.zip");
		System.out.println(new Date());
		zip(flie, target);
		System.out.println(new Date());
	}
}
