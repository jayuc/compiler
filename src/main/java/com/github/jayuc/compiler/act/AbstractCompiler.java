package com.github.jayuc.compiler.act;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.jayuc.compiler.er.CompileException;
import com.github.jayuc.compiler.iter.ICompiler;
import com.github.jayuc.compiler.iter.IFileChangeInfo;
import com.github.jayuc.compiler.pojo.CompilerStatus;

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
	
	/**
	 * 是否有剩余未被编译的任务，
	 * （当正常编译的时候又触发编译任务，这个时候不会编译，而是包编译任务保留下来,等编译器空闲再进行编译）
	 */
	private boolean remain = Boolean.FALSE;
	
	//remain锁
	private Object remainLock = new Object();
	
	//状态锁
	private Object statusLock = new Object();

	@Override
	public void compile(IFileChangeInfo fileChangeInfo) {
		if(shouldCompile()) {
			executor.submit(() -> {
				setStatus(CompilerStatus.BUSY);
				loopDoCompile(fileChangeInfo);
				setStatus(CompilerStatus.IDLE);
			});
		}
	}
	
	/**
	 * 接力执行编译任务
	 * @param fileChangeInfo
	 */
	private void loopDoCompile(IFileChangeInfo fileChangeInfo) {
		try {
			doCompile(fileChangeInfo);
		} catch (CompileException e) {
			setStatus(CompilerStatus.IDLE);
			//错误日志
			e.printStackTrace();
		}
		if(Boolean.TRUE == getRemain()) {
			setRemain(Boolean.FALSE);
			loopDoCompile(fileChangeInfo);
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
		}else {
			if(Boolean.FALSE == getRemain()) {
				setRemain(Boolean.TRUE);
			}
		}
		return false;
	}
	
	/**
	 * 获取是否有保留任务
	 * @return
	 */
	private boolean getRemain() {
		synchronized (remainLock) {
			return remain;
		}
	}
	
	/**
	 * 设置是否有保留任务
	 * @param remain
	 */
	private void setRemain(boolean remain) {
		synchronized (remainLock) {
			this.remain = remain;
		}
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
