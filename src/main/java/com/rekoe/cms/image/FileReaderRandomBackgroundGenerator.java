package com.rekoe.cms.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.resource.NutResource;
import org.nutz.resource.Scans;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;

/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
public class FileReaderRandomBackgroundGenerator implements BackgroundGenerator {

	private final static Log log = Logs.get();
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	protected static Map<String, File> cachedDirectories = new HashMap<String, File>();
	private int height = 100;
	private int width = 200;
	private Random myRandom = new SecureRandom();

	public FileReaderRandomBackgroundGenerator(Integer width, Integer height, String rootPath) {
		this.width = width;
		this.height = height;
		List<NutResource> list = Scans.me().scan(rootPath, ".+\\.jpg");
		for (NutResource resource : list) {
			BufferedImage out = null;
			try {
				out = getImage(resource.getInputStream());
			} catch (IOException e) {
				log.error(e);
			}
			if (out != null) {
				this.images.add(this.images.size(), out);
			}
			if (this.images.size() != 0)
				for (int i = 0; i < this.images.size(); ++i) {
					BufferedImage bufferedImage = (BufferedImage) this.images.get(i);
					this.images.set(i, tile(bufferedImage));
				}
			else {
				throw new CaptchaException("Root path directory is valid but does not contains any image (jpg) files");
			}
		}
	}

	protected File findDirectory(String rootPath) {
		if (cachedDirectories.containsKey(rootPath)) {
			return cachedDirectories.get(rootPath);
		}
		File dir = new File(rootPath);
		StringBuffer triedPath = new StringBuffer();
		appendFilePath(triedPath, dir);
		if ((!(dir.canRead())) || (!(dir.isDirectory()))) {
			dir = new File(".", rootPath);
			appendFilePath(triedPath, dir);
			if ((!(dir.canRead())) || (!(dir.isDirectory()))) {
				dir = new File("/", rootPath);
				appendFilePath(triedPath, dir);
				if ((!(dir.canRead())) || (!(dir.isDirectory()))) {
					URL url = FileReaderRandomBackgroundGenerator.class.getClassLoader().getResource(rootPath);
					if (url != null) {
						dir = new File(url.getFile());
						appendFilePath(triedPath, dir);
					} else {
						url = ClassLoader.getSystemClassLoader().getResource(rootPath);
						if (url != null) {
							dir = new File(url.getFile());
							appendFilePath(triedPath, dir);
						}
					}
				}
			}
		}
		if ((!(dir.canRead())) || (!(dir.isDirectory()))) {
			StringTokenizer token = getClasspathFromSystemProperty();
			while (token.hasMoreElements()) {
				String path = token.nextToken();
				if (!(path.endsWith(".jar"))) {
					dir = new File(path, rootPath);
					appendFilePath(triedPath, dir);
					if ((dir.canRead()) && (dir.isDirectory())) {
						break;
					}
				}
			}
		}
		if ((!(dir.canRead())) || (!(dir.isDirectory()))) {
			throw new CaptchaException("All tried paths :'" + triedPath.toString() + "' is not" + " a directory or cannot be read");
		}
		cachedDirectories.put(rootPath, dir);
		return dir;
	}

	private StringTokenizer getClasspathFromSystemProperty() {
		String classpath = System.getProperty("java.class.path");
		StringTokenizer token = new StringTokenizer(classpath, File.pathSeparator);
		return token;
	}

	private void appendFilePath(StringBuffer triedPath, File dir) {
		triedPath.append(dir.getAbsolutePath());
		triedPath.append("\n");
	}

	private BufferedImage tile(BufferedImage tileImage) {
		BufferedImage image = new BufferedImage(getImageWidth(), getImageHeight(), tileImage.getType());
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		int NumberX = getImageWidth() / tileImage.getWidth();
		int NumberY = getImageHeight() / tileImage.getHeight();
		for (int k = 0; k <= NumberY; ++k) {
			for (int l = 0; l <= NumberX; ++l) {
				g2.drawImage(tileImage, l * tileImage.getWidth(), k * tileImage.getHeight(), Math.min(tileImage.getWidth(), getImageWidth()), Math.min(tileImage.getHeight(), getImageHeight()), null);
			}
		}
		g2.dispose();
		return image;
	}

	private static BufferedImage getImage(InputStream in) {
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			throw new CaptchaException("Unknown error during file reading ", e);
		}
	}

	public BufferedImage getBackground() {
		return this.images.get(this.myRandom.nextInt(this.images.size()));
	}

	@Override
	public int getImageHeight() {
		return this.height;
	}

	@Override
	public int getImageWidth() {
		return this.width;
	}

}
