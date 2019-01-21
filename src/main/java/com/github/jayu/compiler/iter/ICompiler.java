package com.github.jayu.compiler.iter;

/**
 * 定义一个编译器接口
 * @author jayu
 *
 */
public interface ICompiler {

	/**
	 * 执行编译动作
	 * 基本思路：编译需要有编译入口文件，和编译目标文件
	 */
	void compile(IFileChangeInfo fileChangeInfo);
	
}
