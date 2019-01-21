package com.github.jayu.compiler.iter;

import com.github.jayu.compiler.pojo.FileChangeType;

/**
 * 文件属性
 * @author jayu
 *
 */
public interface IFileInfo {

	/**
	 * 文件操作类型
	 * @return
	 */
	FileChangeType getOpearType();
	
	/**
	 * 文件类型
	 * @return
	 */
	String getFileType();
	
	/**
	 * 文件名
	 * @return
	 */
	String getFileName();
	
}
