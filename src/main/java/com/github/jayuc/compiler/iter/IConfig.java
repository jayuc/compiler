package com.github.jayuc.compiler.iter;

/**
 * 编译所需配置
 * @author yujie
 *
 */
public interface IConfig {

	/**
	 * 需要编译项目根目录
	 */
	String getContext();
	
	/**
	 * 输出根目录
	 */
	String getOutPath();
	
	/**
	 * 需要编译的项目名（包含父项目）
	 */
	String getProjectName();
	
	/**
	 * 真实的项目名，不包含父项目
	 */
	String getRecentProjectName();
	
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
