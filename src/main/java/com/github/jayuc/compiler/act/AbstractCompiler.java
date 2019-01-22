package com.github.jayuc.compiler.act;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.jayu.compiler.er.CompileException;
import com.github.jayu.compiler.iter.ICompiler;
import com.github.jayu.compiler.iter.IFileChangeInfo;
import com.github.jayu.compiler.pojo.CompilerStatus;

/**
 * 抽象编译器
 * @author yujie
 *
 */
public abstract class AbstractCompiler implements ICompiler {
	
	//executor service
	private final ExecutorService executor = Executors.newFixedThreadPool(1);
	
	/**
	 * 编译器状态，默认：空闲
	 */
	private CompilerStatus status = CompilerStatus.IDLE;
	
	//状态锁
	private Object statusLock = new Object();

	@Override
	public void compile(IFileChangeInfo fileChangeInfo) {
		if(shouldCompile()) {
			executor.submit(() -> {
				setStatus(CompilerStatus.BUSY);
				try {
					doCompile(fileChangeInfo);
				} catch (CompileException e) {
					setStatus(CompilerStatus.IDLE);
					//错误日志
					//e.printStackTrace();
				}
				setStatus(CompilerStatus.IDLE);
			});
		}
	}

	/**
	 * 执行编译
	 */
	protected abstract void doCompile(IFileChangeInfo fileChangeInfo) 
			throws CompileException;
	
	/**
	 * 判断是否应该被编译
	 * @return
	 */
	private boolean shouldCompile() {
		if(CompilerStatus.IDLE == getStatus()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取编译器状态
	 * @return
	 */
	private CompilerStatus getStatus() {
		synchronized (statusLock) {
			return status;
		}
	}
	
	/**
	 * 设置编译器状态
	 * @param status
	 */
	private void setStatus(CompilerStatus status) {
		synchronized (statusLock) {
			this.status = status;
		}
	}

}
