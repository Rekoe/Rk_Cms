package com.rekoe.crawler.core.util.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.nutz.lang.Files;
import org.nutz.lang.Streams;
/**
 * 文件操作工具类接口实现类
 * @author javacoo
 * @since 2011-11-15
 */
public class DefaultFileHelper implements FileHelper{
	/**
	 * 保存文件
	 * @param in InputStream
	 * @param savePath 文件全路径
	 */
	public void saveFile(InputStream in, String savePath) {
		mkdir(savePath);
		File file = new File(savePath);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			Streams.write(outputStream, in);
		    outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void mkdir(String savePath){
		Files.makeDir(new File(savePath));
	}
}
