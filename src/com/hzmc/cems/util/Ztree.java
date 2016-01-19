package com.hzmc.cems.util;


import java.util.List;


public class Ztree {

	private String		id;					// ID
	private String		name;				// 名字
	private boolean		noR;				// ？？
	private boolean		open;				// 是否打开
	private List<Ztree>	chlds;				// 子节点
	private boolean		isFatPro	;	// 父项目

	/**
	 * 获取树返回一个字符串
	 * 
	 * @author 杨超
	 * @time 2015年7月31日 下午1:40:52
	 */
	public String getJson(List<Ztree> ztreeList) {
		StringBuffer TreeString = new StringBuffer();
		TreeString.append("[");
		for (Ztree ztree : ztreeList) {
			TreeString.append("{id:");
			TreeString.append(ztree.getId());
			TreeString.append(",name:\"");
			TreeString.append(ztree.getName());
			if (ztree.isFatPro()) {
				TreeString.append("\",isFatPro:\"1\",");
			}else{
				TreeString.append("\",isFatPro:\"0\",");
			}
			TreeString.append("noR:");
			TreeString.append(String.valueOf(ztree.isNoR()));
			TreeString.append(",open:");
			TreeString.append(String.valueOf(ztree.isOpen()));
			// 加入子节点
			if (ztree.getChlds() != null) {
				TreeString.append(",childs:[");
				String child = getChild(ztree.getChlds());
				TreeString.append(child);
				TreeString.append("]},");
			} else {
				TreeString.append("},");
			}
		}
		String tree = TreeString.toString();
		if (tree.endsWith(",")) {
			tree = tree.substring(0, tree.length() - 1);
		}
		tree += "]";
		return tree;
	}

	// 获取子节点的json字符串
	public String getChild(List<Ztree> child) {
		StringBuffer TreeString = new StringBuffer();
		int i = child.size();
		for (Ztree ztree : child) {
			TreeString.append("{id:");
			TreeString.append(ztree.getId());
			TreeString.append(",name:\"");
			TreeString.append(ztree.getName());
			TreeString.append("\",noR:");
			TreeString.append(String.valueOf(ztree.isNoR()));
			if (i == 1) {
				TreeString.append("}");
			} else {
				TreeString.append("},");
			}
			i--;
		}
		return TreeString.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNoR() {
		return noR;
	}

	public void setNoR(boolean noR) {
		this.noR = noR;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<Ztree> getChlds() {
		return chlds;
	}

	public void setChlds(List<Ztree> chlds) {
		this.chlds = chlds;
	}

	public boolean isFatPro() {
		return isFatPro;
	}

	public void setFatPro(boolean isFatPro) {
		this.isFatPro = isFatPro;
	}
}
