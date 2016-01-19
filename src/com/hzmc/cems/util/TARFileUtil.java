package com.hzmc.cems.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Tar;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarOutputStream;


public class TARFileUtil {

	private static final Project DEFAULT_PROJECT = new Project();

	/**
	 * 压缩
	 * 
	 * @author 杨超
	 * @time 2015年7月30日 上午11:21:23
	 */
	public static long zip(List<File> sources, File zipFile) {
		Tar zip = new Tar();
		zip.setProject(DEFAULT_PROJECT);
		zip.setDestFile(zipFile);
		FileSet fs = new FileSet();
		fs.setProject(DEFAULT_PROJECT);
		for (File file : sources) {
			fs.setFile(file);
		}
		zip.add(fs);
		zip.execute();
		return zipFile.length();
	}

	/**
	 * 压缩文件成Gzip格式，Linux上可使用 压缩文件夹生成后缀名为".gz"的文件并下载
	 * 
	 * @param folderPath,要压缩的文件夹的路径
	 * @param zipFilePath,压缩后文件的路径
	 * @param zipFileName,压缩后文件的名称
	 * @throws BizException
	 */
	public static void CompressedFiles_Gzip(String folderPath, String targzipFilePath, String targzipFileName) {
		File srcPath = new File(folderPath);
		int length = srcPath.listFiles().length;
		byte[] buf = new byte[1024]; // 设定读入缓冲区尺寸
		File[] files = srcPath.listFiles();
		try {
			// 建立压缩文件输出流
			FileOutputStream fout = new FileOutputStream(targzipFilePath);
			// 建立tar压缩输出流
			TarOutputStream tout = new TarOutputStream(fout);
			for (int i = 0; i < length; i++) {
				String filename = srcPath.getPath() + File.separator + files[i].getName();
				// 打开需压缩文件作为文件输入流
				FileInputStream fin = new FileInputStream(filename); // filename是文件全路径
				TarEntry tarEn = new TarEntry(files[i]); // 此处必须使用new
															// TarEntry(File
															// file);
				tarEn.setName(files[i].getName()); // 此处需重置名称，默认是带全路径的，否则打包后会带全路径
				tout.putNextEntry(tarEn);
				int num;
				while ((num = fin.read(buf)) != -1) {
					tout.write(buf, 0, num);
				}
				tout.closeEntry();
				fin.close();
			}
			tout.close();
			fout.close();
			// 建立压缩文件输出流
			FileOutputStream gzFile = new FileOutputStream(targzipFilePath + ".gz");
			// 建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(gzFile);
			// 打开需压缩文件作为文件输入流
			FileInputStream tarin = new FileInputStream(targzipFilePath); // targzipFilePath是文件全路径
			int len;
			while ((len = tarin.read(buf)) != -1) {
				gzout.write(buf, 0, len);
			}
			gzout.close();
			gzFile.close();
			tarin.close();
		} catch (FileNotFoundException e) {
 			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * 测试
	 * 
	 * @author 杨超
	 * @time 2015年7月30日 上午11:20:42
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
//		List<File> flie = new ArrayList<File>();
//		flie.add(new File("D:测试1.exe"));
//		flie.add(new File("D:测试2.exe"));
//		File target = new File("D:\\测试111.tar");
//		System.out.println(new Date());
//		zip(flie, target);
//		System.out.println(new Date());
		
		
		CompressedFiles_Gzip("D:testTar","D:testTar","123");
		
		
		
		
		
		
		
		
		
		
	}
}
