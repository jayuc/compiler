package com.github.jayu.compiler.iter;

import com.github.jayu.compiler.pojo.CompilerStatus;

/**
 * 定义一个编译器接口
 * @author jayu
 *
 */
public interface ICompiler {

	/**
	 * 执行编译动作
	 */
	void compile(IFileChangeInfo fileChangeInfo);
	
	/**
	 * 获取编译器状态
	 * @return
	 */
	CompilerStatus getStatus();
	
}
