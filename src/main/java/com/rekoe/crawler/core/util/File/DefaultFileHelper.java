package com.rekoe.crawler.core.util.File;

import java.io.File;
import java.io.InputStream;

import org.nutz.lang.Files;

/**
 * 文件操作工具类接口实现类
 */
public class DefaultFileHelper implements FileHelper {
	/**
	 * 保存文件
	 */
	public void saveFile(InputStream in, String savePath) {
		mkdir(savePath);
		File file = new File(savePath);
		Files.write(file, in);
	}

	private void mkdir(String savePath) {
		Files.makeDir(new File(savePath));
	}
}
