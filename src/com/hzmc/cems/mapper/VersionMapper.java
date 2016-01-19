package com.hzmc.cems.mapper;


import java.util.List;

import com.hzmc.cems.model.Relation;
import com.hzmc.cems.model.Version;
import com.hzmc.cems.util.Page;


public interface VersionMapper {

	// 获取版本分页
	List<Version> getVersionByPage(Page page);

	// 获取分页的总数
	int getVersionCount(Page page);

	// 保存版本
	void saveVersion(Version version);

	// 删除版本
	void deleteVersion(Version version);

	// 获取版本LIST通过分支ID
	List<Version> getVersionByBraId(int braId);

	// 保存版本之间的管理。父版本和子版本
	void saveVerRelation(Relation relation);

	// 获取版本信息
	Version getNewestVersion(int braId);

	// 根据版本ID获取版本
	Version getVersionById(int verId);
	
	//取出所有状态为1的版本
	List<Version> getVersionDelete();
	
	//物理删除记录
	void delete(int verId);
	
	//删除关系记录
	void deleteRelation(int verId);
}
