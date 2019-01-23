package com.github.jayuc.compiler.iter;

/**
 * webpack config interface
 * @author yujie
 *
 */
public interface IWebpackConfig extends IConfig {

	/**
	 * webpack.js 安装位置
	 */
	String getWebpackPrefix();

	/**
	 * 输出文件在tomcat中的位置，不是真实的代码，是为了解决tomcat中的缓存问题
	 */
	String getWebFilePath();
	
	/**
	 * 工作空间路径
	 */
	String getWorkPath();
	
}
