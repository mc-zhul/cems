package com.hzmc.cems.service;


import java.util.List;

import com.hzmc.cems.model.Version;
import com.hzmc.cems.util.Page;


public interface VersionService {

	// 获取分页
	public Page getVersionPage(Page page);

	// 保存版本
	public void saveVersion(Version version);

	// 删除版本
	public String deleteVersion(String idd[]);

	// 获取所有版本
	public List<Version> getVersionByBraId(int braId);

	// 保存版本关系
	public void saveVersionRelation(int fatId, String idd[]);

	// 检查版本号
	public boolean checkVerNum(int braId, String verNum[]);

	// 获取版本通过ID
	public Version getVersionById(int verId);

	// 打包版本
	public String zipVersion(String idd[],Version version);
	
	//记录下载日志
	public void setDownLoadLog(String flieName);
	
	

	
}
