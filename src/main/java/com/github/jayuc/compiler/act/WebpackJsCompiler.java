package com.github.jayuc.compiler.act;

import java.io.File;
import java.io.IOException;

import com.github.jayuc.compiler.er.CompileException;
import com.github.jayuc.compiler.iter.IFileChangeInfo;
import com.github.jayuc.compiler.iter.IWebpackConfig;
import com.github.jayuc.compiler.pojo.DocumentOrganization;

/**
 * webpack 编译 js文件
 * @author yujie
 *
 */
public class WebpackJsCompiler extends AbstractCompiler {
	
	/**
	 * 编译器的配置
	 */
	private IWebpackConfig config = null;
	
	/**
	 * 初始化编译器
	 * @param config
	 */
	public WebpackJsCompiler(IWebpackConfig config) {
		super();
		this.config = config;
	}

	/**
	 * 初始化编译器
	 * @param webpackConfig
	 */
	public void init(IWebpackConfig webpackConfig) {
		this.config = webpackConfig;
	}

	@Override
	protected void doCompile(IFileChangeInfo fileChangeInfo) throws CompileException {
		if(null != config) {
			executeCompile(fileChangeInfo, config);
		}
	}
	
	/**
	 * 执行编译
	 * @param fileChangeInfo
	 * @param webpackConfig
	 * @throws CompileException
	 */
	private void executeCompile(IFileChangeInfo fileChangeInfo, IWebpackConfig webpackConfig) 
			throws CompileException {
		
		DocumentOrganization document = new DocumentOrganization(fileChangeInfo, webpackConfig);
		String command = document.createWebpackCommand();
		
		String result = "编译失败！";
		
		if(null != command) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(command);
				process.waitFor();
			} catch (IOException | InterruptedException e) {
				throw new CompileException("webpack编译命令无效， 编译失败");  //编译完成(退出此次编译任务)
				//log.error("编译时抛出异常,自动结束本次编译任务");
			} finally {
				if(process.exitValue() == 0) {
					result = "编译成功！";
				}
				process.destroy();
				process = null;
				//log.debug(result);
			}
			
			//log.debug("开始执行收尾任务");
			//处理收尾工作  解决tomcat缓存问题
			afterExecute(document);
		}else {
			throw new CompileException("webpack编译命令为 null，编译失败");  //编译完成(退出此次编译任务)
			//log.debug("编译命令为null, " + result);
		}
		
	}
	
	/**
	 * 执行后序收尾任务
	 */
	private void afterExecute(DocumentOrganization doc) throws CompileException {
		String srcPath = doc.getWebAbsoluteOutFile();
		String sourcePath = doc.getAbsoluteOutFile();
		if(null != srcPath && null != sourcePath) {
			File source = new File(sourcePath);
			File src = new File(srcPath);
			if(source.exists()) {
				//复制文件到tomcat中 解决tomcat缓存问题
				try {
					//FileUtil.copyFileUsingFileStreams(source, src);
					throw new IOException();
				} catch (IOException e) {
					throw new CompileException("编译已完成，执行收尾任务失败");  //编译完成(退出此次编译任务)
					//log.info("sourcePath:" + doc.getAbsoluteOutFile());
					//log.info("srcPath:"+ srcPath);
					//log.error("复制文件到tomcat中失败了,自动结束此次任务");
				}
			}
		}else {
			//log.debug("srcPath:" + srcPath);
			//log.debug("sourcePath:" + sourcePath);
		}
	}

}
