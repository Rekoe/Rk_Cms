package com.rekoe.module;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.UnavailableException;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Streams;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.view.RawView;
import org.nutz.mvc.view.ViewWrapper;

/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
public class PhotoModul {

	@At("/sparklr/photos/?")
	public View photo(String id) throws Exception {
		InputStream photo = Streams.fileIn("");
		if (photo == null) {
			throw new UnavailableException("The requested photo does not exist");
		}
		BufferedImage body;
		String contentType = "image/jpg";
		Iterator<ImageReader> imageReaders = ImageIO.getImageReadersByMIMEType(contentType.toString());
		if (imageReaders.hasNext()) {
			ImageReader imageReader = imageReaders.next();
			ImageReadParam irp = imageReader.getDefaultReadParam();
			imageReader.setInput(new MemoryCacheImageInputStream(photo), true);
			body = imageReader.read(0, irp);
		} else {
			throw Lang.makeThrow("Could not find javax.imageio.ImageReader for Content-Type [%s]", contentType);
		}
		return new ViewWrapper(new RawView(contentType), body);
	}
}
