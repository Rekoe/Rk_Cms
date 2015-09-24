package com.rekoe.module.admin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.random.R;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

@IocBean
@At("/admin/kindeditor")
public class AdminKindeditor {

	private static final DateFormat DF_DATE = new SimpleDateFormat("yyyyMMdd");

	@Inject("java:$dbconf.get('topic.image.dir')")
	protected String imageDir;

	private final static String NEWS_FILE_PATH = "/upload/news/";

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:upload" })
	@Ok("json")
	@RequiresUser
	public Map<String, Object> upload(@Param("imgFile") TempFile tempFile, @Param("dir") String dir, ServletContext context, AdaptorErrorContext errCtx) throws IOException {
		Map<String, Object> execute = new HashMap<String, Object>();
		if (Lang.isEmpty(tempFile)) {
			if (Lang.isEmpty(errCtx)) {
				execute.put("error", 1);
				execute.put("message", "请选择上传文件.");
				return execute;
			} else {
				execute.put("error", 1);
				execute.put("message", "不支持的文件类型");
				return execute;
			}
		}
		String picPath = imageDir;
		String tim = Times.format(DF_DATE, Times.now());
		Files.createDirIfNoExists(picPath);
		String viewPath = NEWS_FILE_PATH + tim + "/" + R.UU32() + "." + Files.getSuffixName(tempFile.getFile());
		String imgPath = picPath + viewPath;
		File target = new File(imgPath);
		Files.move(tempFile.getFile(), target);
		execute.put("error", 0);
		execute.put("url", viewPath);
		return execute;
	}

	@At
	@Ok("json")
	@RequiresUser
	public Map<String, Object> manager(@Param("dir") String dir, @Param("path") String path, @Param("order") String order, ServletContext context) {
		Map<String, Object> execute = new HashMap<String, Object>();
		String picPath = imageDir;
		String viewPath = picPath + NEWS_FILE_PATH;
		Files.createDirIfNoExists(viewPath);
		String curDir = path;
		String moveupDir = "";
		// 检查当前目录是否为根目录
		if (!"".equals(path)) {
			String str = curDir.substring(0, curDir.length() - 1);
			moveupDir = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		// 检查..命令
		if (path.indexOf("..") >= 0) {
			execute.put("error", 1);
			execute.put("message", "不允许使用..命令返回上一层.");
			return execute;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			execute.put("error", 1);
			execute.put("message", "文件路径不合法.");
			return execute;
		}
		// 检查当前目录
		File curPathFile = new File(viewPath + path);
		if (!curPathFile.isDirectory()) {
			execute.put("error", 1);
			execute.put("message", "当前目录不存在.");
			return execute;
		}
		// 遍历目录取的文件信息
		List<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();
		if (curPathFile.listFiles() != null) {
			for (File file : curPathFile.listFiles()) {
				HashMap<String, Object> hash = new HashMap<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", true);
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		// 文件排序方式
		String tempOrder = order != null ? order.toLowerCase() : "name";
		if ("size".equals(tempOrder)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(tempOrder)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		// 输出遍历后的文件信息数据
		execute.put("moveup_dir_path", moveupDir);
		execute.put("current_dir_path", path);
		execute.put("current_url", NEWS_FILE_PATH + path);
		execute.put("total_count", fileList.size());
		execute.put("file_list", fileList);
		return execute;
	}

	/**
	 * 根据文件名称排序
	 */
	private class NameComparator implements Comparator<HashMap<String, Object>> {
		@Override
		public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {
			HashMap<String, Object> hashA = a;
			HashMap<String, Object> hashB = b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
			}
		}
	}

	/**
	 * 根据文件大小排序
	 */
	private class SizeComparator implements Comparator<HashMap<String, Object>> {
		@Override
		public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {
			HashMap<String, Object> hashA = a;
			HashMap<String, Object> hashB = b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
					return 1;
				} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * 根据文件类型排序
	 */
	private class TypeComparator implements Comparator<HashMap<String, Object>> {
		@Override
		public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {
			HashMap<String, Object> hashA = a;
			HashMap<String, Object> hashB = b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
			}
		}
	}
}
