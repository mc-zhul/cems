package com.hzmc.cems.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzmc.cems.mapper.VersionMapper;
import com.hzmc.cems.model.OpLog;
import com.hzmc.cems.model.Project;
import com.hzmc.cems.model.Relation;
import com.hzmc.cems.model.SessionUser;
import com.hzmc.cems.model.Version;
import com.hzmc.cems.service.VersionService;
import com.hzmc.cems.util.FTPUtils;
import com.hzmc.cems.util.Page;
import com.hzmc.cems.util.ZIPFileUtil;


@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionMapper	versionMapper;

	/**
	 * 获取所有版本分页
	 * 
	 * @author 杨超
	 * @time 2015年7月20日 下午6:43:17
	 */
	@Override
	public Page getVersionPage(Page page) {
		page.setTotalCount(getTotalCount(page));
		List versionList = null;
		versionList = versionMapper.getVersionByPage(page);
		page.setItems(versionList);
		List<Version> versionList1 = versionList;
		return page;
	}

	private int getTotalCount(Page page) {
		return versionMapper.getVersionCount(page);
	}

	/**
	 * 保存版本信息
	 * 
	 * @author 杨超
	 * @time 2015年7月21日 上午10:50:01
	 */
	@Override
	public void saveVersion(Version version) {
//		versionMapper.saveVersion(version);
//		Project project = projectMappser.getProjectByBraId(version.getBraId());
//		OpLog log = new OpLog(); // 增加操作日志
//		String name=SessionUser.getSessionUser().getName();
//		log.setLogDate(new Date());
//		log.setName(name);
//		log.setOper("增加");
//		log.setFirModule("版本管理");
//		log.setSecModule("版本管理");
//		log.setDesc("用户：" + name + "  进行增加版本操作：项目名称：" + project.getProName() + ", 分支名称："
//				+ project.getBranchList().get(0).getBraName() + ", 版本名称：" + version.getVerName() + " , 版本号："
//				+ version.getVerNum());
	}

	/**
	 * 删除版本 改变版本状态为1
	 * 
	 * @author 杨超
	 * @time 2015年7月21日 下午3:00:50
	 */
	@Override
	@Transactional
	public String deleteVersion(String idd[]) {
		String str = "";
		return str;
	}

	/**
	 * 根据分支id获取所有版本
	 * 
	 * @author 杨超
	 * @time 2015年7月23日 下午2:16:45
	 */
	@Override
	public List<Version> getVersionByBraId(int braId) {
		return versionMapper.getVersionByBraId(braId);
	}

	/**
	 * 存储关系
	 * 
	 * @author 杨超
	 * @time 2015年7月23日 下午2:16:49
	 */
	@Transactional
	@Override
	public void saveVersionRelation(int fatId, String idd[]) {
		for (int i = 0; i < idd.length; i++) {
			Relation relation = new Relation();
			relation.setFatherId(fatId);
			relation.setChildId(Integer.valueOf(idd[i]));
			versionMapper.saveVerRelation(relation);
		}
	}

	/**
	 * 验证版本号比以前版本号大
	 * 
	 * @author 杨超
	 * @time 2015年7月23日 下午5:10:08
	 */
	@Override
	public boolean checkVerNum(int braId, String[] verNum) {
		Version version = versionMapper.getNewestVersion(braId);
		if (version == null) {
			return true;
		} else {
			String num = version.getVerNum();
			String OldNum[] = num.split("\\.");
			for (int i = 0; i < 4; i++) {
				int oldnum = Integer.valueOf(OldNum[i]);
				int newnum = Integer.valueOf(verNum[i]);
				if (oldnum > newnum) {
					return false;
				}
				if (oldnum < newnum) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 通过版本ID获取版本
	 * 
	 * @author 杨超
	 * @time 2015年7月31日 上午9:45:00
	 */
	@Override
	public Version getVersionById(int verId) {
		Version version = null;
		version = versionMapper.getVersionById(verId);
		return version;
	}

	/**
	 * 打包版本
	 * 
	 * @author 杨超
	 * @time 2015年7月31日 上午9:44:43
	 */
	@Override
	public String zipVersion(String[] idd, Version versionFat) {
		String pathZip = FTPUtils.getUploadpath() + File.separator + versionFat.getVerAddress();
		String fileName = versionFat.getVerAddress().substring(0, versionFat.getVerAddress().lastIndexOf("."));
		List<File> fileList = new ArrayList<File>();
		ZIPFileUtil fileUtil = new ZIPFileUtil();
		String path;
		String str = "";
		int i = 1;
		for (String verId : idd) {
			Version version = null;
			version = versionMapper.getVersionById(Integer.valueOf(verId));
			// 设置备注
			String cText = i + ".  " + version.getVerAddress() + "  的备注：   \r\n " + version.getComments() + "\r\n";
			str += cText;
			// 获取配置的路径
			path = FTPUtils.getUploadpath() + File.separator + version.getVerAddress();
			fileList.add(new File(path));
			i++;
		}
		String txtFilePath = FTPUtils.getUploadpath() + File.separator + "readme(" + fileName + ").txt";
		String fatConText = "总项目：   " + fileName + " 的备注：   \r\n " + versionFat.getComments() + "\r\n";
		str += fatConText;
		// 创建txt
		creatTxtFile(str, txtFilePath);
		fileList.add(new File(txtFilePath));
		long size = fileUtil.zip(fileList, new File(pathZip));
		if (size > 0) {
			deleteFile(txtFilePath);
		}
		return String.valueOf(size);
	}

	/**
	 * 下载日志
	 * 
	 * @author 杨超
	 * @time 2015年10月29日 下午4:48:30
	 */
	@Override
	public void setDownLoadLog(String flieName) {
//		OpLog log = new OpLog(); // 增加操作日志
//		String name=SessionUser.getSessionUser().getName();
//		log.setLogDate(new Date());
//		log.setName(name);
//		log.setOper("下载");
//		log.setFirModule("版本管理");
//		log.setSecModule("版本管理");
//		log.setDesc("用户：" + name + "  下载版本：" + flieName);
//		logMapper.saveLog(log);
	}

	/**
	 * 生成Text文档
	 * 
	 * @author 杨超
	 * @time 2015年10月29日 下午1:39:58
	 */
	public void creatTxtFile(String context, String path) {
		// File fileDir = new File(path);
		// if (!fileDir.exists()) {
		// fileDir.mkdirs();
		// }
		File filename = new File(path);
		if (!filename.exists()) {
			try {
				filename.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RandomAccessFile mm = null;
		try {
			mm = new RandomAccessFile(filename, "rw");
			try {
				context = new String(context.getBytes("GBK"), "ISO8859_1");
				mm.writeBytes(context);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除txt
	 */
	public boolean deleteDirectory(String sPath) {
		boolean flag = true;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	public String modifyFileName(String sPath) {
		String prefix = String.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		String localpath = FTPUtils.getUploadpath();
		String newFileName = "Del_" + prefix + "_" + sPath;
		String newPath = localpath + File.separator + newFileName;
		sPath = localpath + File.separator + sPath;
		File file = new File(sPath);
		File newfile = new File(newPath);
		// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
		file.renameTo(newfile);
		return newFileName;
	}

	public static void main(String[] args) {
		VersionServiceImpl impl = new VersionServiceImpl();
		impl.modifyFileName("本机打包_V2.1.1.2forOracle.zip");
	}
}
