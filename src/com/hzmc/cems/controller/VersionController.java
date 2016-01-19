package com.hzmc.cems.controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hzmc.cems.model.Branch;
import com.hzmc.cems.model.Project;
import com.hzmc.cems.model.Relation;
import com.hzmc.cems.model.User;
import com.hzmc.cems.model.Version;
import com.hzmc.cems.service.VersionService;
import com.hzmc.cems.util.FTPUtils;
import com.hzmc.cems.util.Page;


@Controller
public class VersionController {

	@Autowired
	private VersionService	versionService;

	/**
	 * 版本分页 查询
	 * 
	 * @author 杨超
	 * @time 2015年7月21日 上午9:55:44
	 */
	@RequestMapping("version/getVersionPage.do")
	public ModelAndView getVersion(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
//		String braId = request.getParameter("braId");
//		String verNum = request.getParameter("verNum");
//		String name = request.getParameter("name");
//		String verName = request.getParameter("verName");
//		List<Branch> branchList = new ArrayList<Branch>();
//		List<Version> versionList = new ArrayList<Version>();
////		Project project = projectService.getProjectByBraId(Integer.valueOf(braId));
////		// 初始化增加 子版本 项目 下拉 列表 的 值
////		List<Project> projectList = projectService.getChildProjectListAndBranch();
//		// 判断是否为空
//		if (!projectList.isEmpty()) {
//			branchList = projectList.get(0).getBranchList();
//			if (!branchList.isEmpty()) {
//				// 初始化增加 子版本 版本 下拉 列表 的 值
//				versionList = versionService.getVersionByBraId(branchList.get(0).getBraId());
//			}
//		}
//		// 分页
//		Page page = new Page();
//		String currentPage = request.getParameter("currentPage");
//		// 设置分页查询条件
//		page.setCurrentPage(currentPage);
//		page.addSearchParameter("braId", braId);
//		page.addSearchParameter("verNum", verNum);
//		page.addSearchParameter("name", name);
//		page.addSearchParameter("verName", verName);
//		page = versionService.getVersionPage(page);
//		mv.addObject("project", project);
//		mv.addObject("page", page);
//		mv.addObject("braId", braId);
//		mv.addObject("projectList", projectList);
//		mv.addObject("branchList", branchList);
//		mv.addObject("versionList", versionList);
//		mv.setViewName("/version/versionList");
		return mv;
	}

	/**
	 * 增加版本
	 * 
	 * @author 杨超
	 * @time 2015年7月22日 上午10:00:38
	 */
	@RequestMapping("version/saveVersion.do")
	public ModelAndView saveVersion(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,
			Version version, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("loginUser");
		version.setStatus("0");
		version.setUserId(user.getUserId());
		versionService.saveVersion(version);
		mv.addObject("braId", version.getBraId());
		mv.setViewName("redirect:/version/getVersionPage.do");
		return mv;
	}


	/**
	 * 删除版本
	 * 
	 * @author 杨超
	 * @time 2015年7月22日 上午10:00:55
	 */
	@RequestMapping("version/deleteVersion.do")
	public @ResponseBody String deleteVersion(HttpServletRequest request, HttpServletResponse response,
			ModelAndView mv) {
		String id = request.getParameter("id");
		// 去除字符串第一个逗号
		id = id.substring(1, id.length());
		// 通过“,”来分组存在数组里
		String idd[] = id.split(",");
		String str = versionService.deleteVersion(idd);
		return str;
	}


	/**
	 * 根据分支查询版本列表
	 * 
	 * @author 杨超
	 * @time 2015年7月22日 下午5:32:34
	 */
	@RequestMapping("version/getVersion.do")
	public @ResponseBody List<Version> getVersion(HttpServletRequest request) {
		String braId = request.getParameter("braId");
		List<Version> versionList = versionService.getVersionByBraId(Integer.valueOf(braId));
		return versionList;
	}


	/**
	 * 检查版本号
	 * 
	 * @author 杨超
	 * @time 2015年7月29日 下午4:15:42
	 */
	@RequestMapping("version/checkVerNum.do")
	public @ResponseBody boolean checkVerNum(HttpServletRequest request) {
		String braId = request.getParameter("braId");
		String verNum = request.getParameter("verNum");
		// 分离字符串里的num 存储到数组里
		String verNum1[] = verNum.split("\\.");
		boolean isNum = versionService.checkVerNum(Integer.valueOf(braId), verNum1);
		return isNum;
	}

	/**
	 * 上传 版本
	 * 
	 * @author 杨超
	 * @throws JSONException
	 * @throws IOException
	 * @time 2015年7月29日 上午10:36:42
	 */
	@RequestMapping(value = "version/file.do")
	public void file(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile file, HttpSession httpSession)
					throws JSONException, IOException {
		// 获取路径
		String path = FTPUtils.getUploadpath();
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 在文件名前面加时间保证不是名字不重复
		//fileName = renameFile(fileName, verNum);
		long size = file.getSize();
		// 上传
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置下载路径
		path = FTPUtils.getdownAddress();
		// String pathAndname = path + "/" + fileName;
		// 传出JSON
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		json.put("path", fileName);
		json.put("size", size);
		out.print(json);
	}

	/**
	 * 文件名修改
	 * 
	 * @author 杨超
	 * @time 2015年7月29日 下午4:11:20
	 */
	public String renameFile(String fileName, Project project, Version version) {
		fileName = fileName.substring(fileName.lastIndexOf("."));
		Branch branch = new Branch();
		if (project.getBranchList().size() > 0) {
			branch = project.getBranchList().get(0);
		}
		fileName = project.getProName() + "_V" + version.getVerNum() + "for" + branch.getBraName() + fileName;
		return fileName;
	}

	public String renameFile(Project project, Version version) {
		Branch branch = new Branch();
		if (project.getBranchList().size() > 0) {
			branch = project.getBranchList().get(0);
		}
		String file_name = project.getProName() + "_V" + version.getVerNum() + "for" + branch.getBraName() + ".zip";
		return file_name;
	}

	/**
	 * 下载版本
	 * 
	 * @author 杨超
	 * @time 2015年7月30日 上午9:05:26
	 */
	@RequestMapping("version/downVersion.do")
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
		String path = FTPUtils.getWebRootAppPath() + "WEB-INF"+File.separator+"版本管理系统用户手册.doc";
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("版本管理系统用户手册".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}


	/**
	 * 验证浏览器编码返回不同的下载路径文件名 对火狐以为的 进行特殊处理
	 * 
	 * @author 杨超
	 * @time 2015年8月7日 上午11:31:45
	 */
	@RequestMapping("version/getdownUrl.do")
	public @ResponseBody String geturl(HttpServletRequest request) throws UnsupportedEncodingException {
		String name = request.getParameter("name");
		String browser = request.getParameter("browser");
		String osname=System.getProperty("os.name");
		if(osname.indexOf("Windows") != -1){
			if (browser.indexOf("Firefox") == -1) {
				name = java.net.URLEncoder.encode(name, "GB2312");
			}
		}
		String path = FTPUtils.getdownAddress() + "/" + name;
		versionService.setDownLoadLog(name);
		return path;
	}

	/**
	 * 显示版本详情
	 * 
	 * @author 李树桓
	 * @time 2015-7-23下午2:45:45
	 */
	@RequestMapping("/getreturn.do")
	public ModelAndView returnable(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		String id = request.getParameter("id");
		mv.setViewName("redirect:/version/getVersionPage.do");
		mv.addObject("braId", id);
		return mv;
	}

	@RequestMapping("/getVersionDetail.do")
	public ModelAndView getVersionDetail(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
//		int id = Integer.valueOf(request.getParameter("id"));
//		Relation relation = new Relation();
//		List<Relation> relationList = new ArrayList<Relation>();
//		relationList = relationService.getChildVersionList(Integer.valueOf(id));
//		relation = relationService.getVersionById(id);
//		mv.addObject("relationList", relationList);
//		mv.addObject("relation", relation);
//		mv.setViewName("/version/version_info");
		return mv;
	}

}
