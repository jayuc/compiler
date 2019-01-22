package com.github.jayuc.compiler.iter;

/**
 * 文件路径
 * @author jayu
 *
 */
public interface IFilePath {

	/**
	 * 文件路径，相对项目路径：例如： /compiler/src/main/java/com/github/jayu/compiler/iter/IFileInfo.java
	 * @return
	 */
	String getFilePath();

	/**
	 * 文件绝对路径，从根目录开始，
	 * 例如（linux）： /home/jayu/codeRepository/compiler/src/main/java/com/github/jayu/compiler/iter/IFileInfo.java
	 * 例如 (window)： E:/codeRepository/compiler/src/main/java/com/github/jayu/compiler/iter/IFileInfo.java
	 * @return
	 */
	String getFileLocation();
	
}
