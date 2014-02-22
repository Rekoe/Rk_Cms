package com.rekoe.module.admin;

import java.io.IOException;
import java.util.Date;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.rekoe.service.FileService;

@IocBean
@At("/admin/file")
public class FileAct {

	@Inject
	private FileService fileService;

	@At
	@Ok("raw")
	public Object browser(@Param("fileType") String fileType, @Param("orderType") String orderType, @Param("path") String path) {
		return Files.read("file.json");
	}

	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "ioc:myPicUpload" })
	@Ok("raw")
	public Object upload(@Param("fileType") FileInfo.FileType fileType, @Param("file") TempFile tempFile, AdaptorErrorContext errCtx) throws IOException {
		if (Lang.isEmpty(tempFile)) {
			if (Lang.isEmpty(errCtx)) {
				// req.setAttribute("err","文件不可以为空");
			} else {
				// req.setAttribute("err",errCtx.getAdaptorErr().getMessage());
			}
			return null;
		}
		return fileService.upload(fileType, tempFile, true);
	}

	public static class FileInfo {
		private String name;
		private String url;
		private Boolean isDirectory;
		private Long size;
		private Date lastModified;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Boolean getIsDirectory() {
			return isDirectory;
		}

		public void setIsDirectory(Boolean isDirectory) {
			this.isDirectory = isDirectory;
		}

		public Long getSize() {
			return size;
		}

		public void setSize(Long size) {
			this.size = size;
		}

		public Date getLastModified() {
			return lastModified;
		}

		public void setLastModified(Date lastModified) {
			this.lastModified = lastModified;
		}

		public enum FileType {
			image, flash, media, file;
		}
	}
}
