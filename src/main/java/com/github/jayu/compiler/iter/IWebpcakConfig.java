package com.github.jayu.compiler.iter;

/**
 * webpack config interface
 * @author yujie
 *
 */
public interface IWebpcakConfig {

	/**
	 * webpack.js 安装位置
	 */
	String getWebpackPrefix();
	
	/**
	 * 需要编译项目根目录
	 */
	String getContext();
	
	/**
	 * 输出根目录
	 */
	String getOutPath();
	
	/**
	 * 需要编译的项目名
	 */
	String getProjectName();
	
	/**
	 * 真实的项目名，不包含父项目
	 */
	String getRecentProjectName();
	
	/**
	 * 输出文件在tomcat中的位置，不是真实的代码，是为了解决tomcat中的缓存问题
	 */
	String getWebFilePath();
	
	/**
	 * 编译模式  开发环境(development)/生产环境(production),默认开发环境
	 */
	String getModel();
	
	/**
	 * 生效的文件类型
	 */
	String getFileType();
	
	/**
	 * 生效的文件名
	 */
	String getFileName();
	
}
