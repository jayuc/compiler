package com.github.jayuc.compiler.pojo;

import java.io.File;

import com.github.jayuc.compiler.iter.IFileChangeInfo;
import com.github.jayuc.compiler.iter.IWebpackConfig;

//import org.apache.log4j.Logger;

/**
 * 构建文档组织结构，寻找合适的编译目标文件
 * @author yujie
 *
 */
public class DocumentOrganization {
	
	//private final static Logger log = LogUtil.getLog(DocumentOrganization.class);
	
	/**
	 * webpack 配置项
	 */
	private IWebpackConfig webpackConfig = null;
	
	/**
	 * 项目路径
	 */
	private String projectPath = null;

	/**
	 * 输入文件路径
	 */
	private String entryPath = null;

	/**
	 * 输出文件路径
	 */
	private String outPath = null;
	
	/**
	 * 输出文件在tomcat中的位置，不是真实的代码，是为了解决tomcat中的缓存问题
	 */
	private String webOutPath = null;
	
	/**
	 * 输出文件名
	 */
	private String outName = null;

	/**
	 * 根据文件变化对象生成文件机构对象
	 * @param fileChangeInfo
	 */
	public DocumentOrganization(IFileChangeInfo fileChangeInfo, IWebpackConfig webpackConfig) {
		if(null != webpackConfig) {
			projectPath = fileChangeInfo.getProjectLocation();
			this.webpackConfig = webpackConfig;
			String context = projectPath + webpackConfig.getContext();
			//log.debug("context:" + context);
			String location = fileChangeInfo.getFileLocation();
			//log.debug("location:" + location);
			String fileName = webpackConfig.getFileName();
			//log.debug("fileName:" + fileName);
			if(location.endsWith(fileName)) {
				setValues(location);
			}else {
				String temp = location.replace(context, "");
				String fix = temp.substring(0, temp.lastIndexOf("/"));
				String paths = findPath(context, fix);
				setValues(paths);
			}
		}
	}
	
	/**
	 * 生成webpack 命令
	 * @return
	 */
	public String createWebpackCommand() {
		if(null != entryPath 
				&& null != outName 
				&& null != projectPath
				&& null != outPath) {
			return "node " + webpackConfig.getWebpackPrefix() + "node_modules/webpack/bin/webpack.js --context " + projectPath + webpackConfig.getContext() 
					+ " " + entryPath + webpackConfig.getFileName() + " -o " + projectPath + outPath + outName + " --mode " + webpackConfig.getModel();
		}
		return null;
	}
	
	/**
	 * 获取输出文件
	 * @return
	 */
	public String getOutFile() {
		if(null != outPath 
				&& null != outName) {
			return webpackConfig.getProjectName() + outPath + outName;
		}
		return null;
	}
	
	/**
	 * 获取输出文件的绝对路径
	 * @return
	 */
	public String getAbsoluteOutFile() {
		if(null != projectPath 
				&& null != outPath 
				&& null != outName) {
			return projectPath + outPath + outName;
		}
		return null;
	}
	
	/**
	 * 输出文件在web中的绝对位置
	 * @return
	 */
	public String getWebAbsoluteOutFile() {
		if(null != webOutPath 
				&& null != outName) {
			return webpackConfig.getWorkPath() + ".metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/" + 
					webpackConfig.getRecentProjectName() + webOutPath + outName;
		}
		return null;
	}
	
	private String findPath(String context, String sep) {
		//log.debug("context:" + context);
		//log.debug("sep:" + sep);
		String path = context + sep + "/" + webpackConfig.getFileName();
		File file = new File(path);
		if(file.exists()) {
			return path;
		}
		//log.debug(path + "不存在");
		if("".equals(sep)) {
			return null;
		}else {
			return findPath(context, sep.substring(0, sep.lastIndexOf("/")));
		}
	}
	
	private void setValues(String path) {
		//log.debug("path:" + path);
		if(null != path) {
			String temp = path.replace(projectPath + webpackConfig.getContext(), "");
			String fix = temp.replace(webpackConfig.getFileName(), "");
			entryPath = "." + fix;
			if("/".equals(fix)) {
				outPath = webpackConfig.getOutPath() + "/";
				webOutPath = webpackConfig.getWebFilePath() + "/";
				outName = webpackConfig.getFileName();
			}else {
				fix = fix.substring(0, fix.lastIndexOf("/"));
				String ftemp = fix.substring(0, fix.lastIndexOf("/") + 1);
				outPath = webpackConfig.getOutPath() + ftemp;
				webOutPath = webpackConfig.getWebFilePath() + ftemp;
				outName = fix.substring(fix.lastIndexOf("/") + 1, fix.length()) + ".js";
			}
			//log.debug("entryPath:" + entryPath);
			//log.debug("outPath:" + outPath);
			//log.debug("outName:" + outName);
			//log.debug("projectPath:" + projectPath);
		}
	}
	
}
