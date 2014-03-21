package com.rekoe.module.admin;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.rekoe.common.Message;
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
	@Ok("json")
	public Message upload(@Param("fileType") FileInfo.FileType fileType, @Param("file") TempFile tempFile, HttpServletRequest req, AdaptorErrorContext errCtx) throws IOException {
		if (Lang.isEmpty(tempFile)) {
			if (Lang.isEmpty(errCtx)) {
				return Message.warn("admin.error.message", req);
			} else {
				return Message.warn(errCtx.getAdaptorErr().getMessage(), req);
			}
		}
		fileService.upload(fileType, tempFile, true);
		return Message.success("Message.Type.success", req);
	}

	@At
	@Ok("void")
	@Fail("json")
	public void down(HttpServletResponse resp) throws IOException {
		File f = Files.findFile("file:path");
		String filename = URLEncoder.encode(f.getName(), Encoding.UTF8);
		resp.setHeader("Content-Length", "" + f.length());
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		Streams.writeAndClose(resp.getOutputStream(), Streams.fileIn(f));
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
