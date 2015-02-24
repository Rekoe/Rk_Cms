package com.rekoe.crawler.core.util.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.nutz.lang.random.R;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlResURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;
import com.rekoe.crawler.core.util.URIHelper;
import com.rekoe.crawler.core.util.parser.tag.EmbedTag;

/**
 * HTML解析工具,HtmlParser实现类
 */
public class HtmlParserImpl implements HtmlParser {
	private final static Log log = Logs.get();
	/** 唯一标示生成接口,默认实现 */
	private PrototypicalNodeFactory factory;
	private URIHelper uriHelper;

	/**
	 * HTML解析类构造方法
	 * 
	 * @param filterList
	 *            过滤器集合
	 */
	public HtmlParserImpl(URIHelper uriHelper) {
		factory = new PrototypicalNodeFactory();
		factory.registerTag(new EmbedTag());
		this.uriHelper = uriHelper;
	}

	/**
	 * 取得指定区域的连接集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @param deleteAreaTagMap
	 *            待提取区域中要删除的标签属性Map
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 连接集合
	 */
	public List<CrawlLinkURI> getUrlList(String orginHtml, Map<String, String> fetchAreaTagMap, Map<String, String> deleteAreaTagMap, CrawlURI parentCrawlURI) {
		orginHtml = getHtmlByFilter(fetchAreaTagMap, deleteAreaTagMap, orginHtml);
		return getUrlListFromOrginHtml(orginHtml, parentCrawlURI);
	}

	/**
	 * 取得指定区域内的所有连接,并组装为list
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 连接集合
	 */
	private List<CrawlLinkURI> getUrlListFromOrginHtml(String orginHtml, CrawlURI parentCrawlURI) {
		List<CrawlLinkURI> resultList = new CopyOnWriteArrayList<CrawlLinkURI>();
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML(orginHtml);
				NodeFilter filter = new NodeClassFilter(LinkTag.class);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				for (int i = 0; i < nodes.size(); i++) {
					LinkTag linkTag = (LinkTag) nodes.elementAt(i);
					if (StringUtils.isNotBlank(linkTag.getLink()) && !"#".equals(StringUtils.trim(linkTag.getLink()))) {
						String title = StringUtils.trim(linkTag.getLinkText());
						String altTitle = linkTag.getAttribute("title");
						title = StringUtils.defaultIfBlank(title, StringUtils.trim(altTitle));
						populateList(resultList, StringUtils.trim(linkTag.getLink()), title, parentCrawlURI);
					}
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}

	/**
	 * 取得指定区域的HTML内容
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @param deleteAreaTagMap
	 *            待提取区域中要删除的标签属性Map
	 * @return 指定区域的HTML内容
	 * @throws ParserException
	 */
	public String getHtml(String orginHtml, Map<String, String> fetchAreaTagMap, Map<String, String> deleteAreaTagMap) {
		if (StringUtils.isNotBlank(orginHtml)) {
			orginHtml = getHtmlByFilter(fetchAreaTagMap, deleteAreaTagMap, orginHtml);
		}
		return orginHtml;
	}

	/**
	 * 取得指定区域中指定属性标签内的HTML内容
	 * 
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @return 指定区域的HTML内容
	 */
	public String fetchAreaHtml(String orginHtml, Map<String, String> fetchAreaTagMap) {
		log.info("========取得指定区域的HTML内容=========");
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				orginHtml = filterRequire(fetchAreaTagMap, parser, orginHtml);
				log.info(orginHtml);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return orginHtml;
	}

	/**
	 * 过滤指定区域的HTML内容
	 * 
	 * @param deleteAreaTagMap
	 *            待提取区域中要删除的标签属性Map
	 * @return 过滤后的HTML内容
	 */
	public String deleteAreaHtml(String orginHtml, Map<String, String> deleteAreaTagMap) {
		log.info("========过滤指定区域的HTML内容=========");
		return filterHtml(deleteAreaTagMap, orginHtml);
	}

	/**
	 * 取得无格式的HTML内容
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @return 无格式的HTML内容
	 */
	public String getPlainText(String orginHtml) {
		log.info("========取得无格式的HTML内容=========");
		String resultStr = "";
		try {
			Parser parser = new Parser();
			parser.setInputHTML(orginHtml);
			StringBuilder sb = new StringBuilder();
			while (parser.elements().hasMoreNodes()) {
				sb.append(StringUtils.trim(parser.elements().nextNode().toPlainTextString()));
			}
			resultStr = sb.toString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return resultStr.replaceAll("\\s", "");
	}

	/**
	 * 取得HTML内容中的资源
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return String
	 */
	public String getHtmlResource(String orginHtml, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) {
		log.info("========取得HTML内容中的资源=========");
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML(orginHtml);
				orginHtml = extractImageTag(parser, orginHtml, resCrawlURIList, parentCrawlURI);
				extractLinkRes(parser, resCrawlURIList, parentCrawlURI);
				orginHtml = extractObject(parser, orginHtml, resCrawlURIList, parentCrawlURI);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return orginHtml;

	}

	/**
	 * 取得指定区域内的内容并放如集合中
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @return 指定区域内的内容集合
	 */
	public List<String> getHtmlList(String orginHtml, Map<String, String> fetchAreaTagMap) {
		List<String> htmlList = new ArrayList<String>();
		log.info("========取得指定区域的HTML内容=========");
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML(orginHtml);
				// 第一步取得指定属性/标签内容
				List<NodeFilter> nodeFilterList = populateFilter(parser, fetchAreaTagMap);
				for (NodeFilter filter : nodeFilterList) {
					addHtmlToListByFilter(parser, filter, htmlList);
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return htmlList;
	}

	private String filterHtml(Map<String, String> filterMap, String orginHtml) {
		log.info("========过滤指定区域的HTML内容=========");
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				orginHtml = filterThrowAway(filterMap, parser, orginHtml);
				orginHtml = filterOtherTag(parser, orginHtml);
				log.info(orginHtml);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return orginHtml;
	}

	/**
	 * 提取内容中图片标签内容
	 * 
	 * @param parser
	 *            Parser对象
	 * @param orginHtml
	 *            原始HTML
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @throws ParserException
	 */
	private String extractImageTag(Parser parser, String orginHtml, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) throws ParserException {
		log.info("========提取内容中图片标签内容=========");
		List<CrawlResURI> resultCrawlResURIList = new CopyOnWriteArrayList<CrawlResURI>();
		NodeFilter filter = new NodeClassFilter(ImageTag.class);
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		ImageTag imageTag = null;
		String orginImgUrl = null;
		String orginImgTagHtml = null;
		String newImgTagHtml = null;
		for (int i = 0; i < nodes.size(); i++) {
			imageTag = (ImageTag) nodes.elementAt(i);
			orginImgUrl = imageTag.getImageURL();
			if (StringUtils.isNotBlank(orginImgUrl)) {
				if (!orginImgUrl.contains("http://")) {
					orginImgTagHtml = imageTag.toHtml();
					CrawlResURI crawlResURI = uriHelper.populateResURI(parentCrawlURI, orginImgUrl, "", Constants.RES_IMAGES_MAP_KEY);
					resultCrawlResURIList.add(crawlResURI);
					orginImgUrl = crawlResURI.getUrl();
					// 设置新URL
					imageTag.setImageURL(orginImgUrl);
					newImgTagHtml = imageTag.toHtml();
					orginHtml = StringUtils.replace(orginHtml, orginImgTagHtml, newImgTagHtml);
				} else {
					resultCrawlResURIList.add(uriHelper.populateResURI(parentCrawlURI, orginImgUrl, "", Constants.RES_IMAGES_MAP_KEY));
				}
			}

		}
		resCrawlURIList.addAll(resultCrawlResURIList);
		parser.reset();
		return orginHtml;
	}

	/**
	 * 提取内容连接中的资源
	 * 
	 * @param parser
	 *            Parser对象
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @throws ParserException
	 */
	@SuppressWarnings("serial")
	private void extractLinkRes(Parser parser, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) throws ParserException {
		log.info("========提取内容连接中的资源=========");
		List<CrawlResURI> resultCrawlResURIList = new CopyOnWriteArrayList<CrawlResURI>();
		NodeFilter filter = new NodeClassFilter(LinkTag.class) {
			public boolean accept(Node node) {
				if (node instanceof LinkTag) {
					String url = ((LinkTag) node).getLink();
					String urlTypeName = FilenameUtils.getExtension(url.toLowerCase());
					if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(urlTypeName) && Constants.EXTRACT_RES_TYPE.contains(urlTypeName)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		};
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		LinkTag linkTag = null;
		for (int i = 0; i < nodes.size(); i++) {
			linkTag = (LinkTag) nodes.elementAt(i);
			resultCrawlResURIList.add(uriHelper.populateResURI(parentCrawlURI, linkTag.getLink(), "", Constants.RES_ATTAC_MAP_KEY));
		}
		resCrawlURIList.addAll(resultCrawlResURIList);
		parser.reset();
	}

	/**
	 * 提取内容中对象资源，如，媒体资源
	 * 
	 * @param parser
	 *            Parser对象
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @throws ParserException
	 */
	@SuppressWarnings("serial")
	private String extractObject(Parser parser, String orginHtml, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) throws ParserException {
		log.info("========提取内容中对象资源=========");
		parser.setNodeFactory(factory);
		List<CrawlResURI> resultCrawlResURIList = new CopyOnWriteArrayList<CrawlResURI>();
		NodeFilter filter = new NodeClassFilter(EmbedTag.class) {
			public boolean accept(Node node) {
				if (node instanceof EmbedTag) {
					String url = ((EmbedTag) node).getSrc();
					String urlTypeName = FilenameUtils.getExtension(url.toLowerCase());
					if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(urlTypeName) && Constants.EXTRACT_MEDIA_RES_TYPE.contains(urlTypeName)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		};
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		EmbedTag embedTag = null;
		for (int i = 0; i < nodes.size(); i++) {
			embedTag = (EmbedTag) nodes.elementAt(i);
			resultCrawlResURIList.add(uriHelper.populateResURI(parentCrawlURI, embedTag.getSrc(), "", Constants.RES_MEDIA_MAP_KEY));
			// 去掉原始OBJ对象
			orginHtml = StringUtils.replace(orginHtml, embedTag.toHtml(), "");
		}
		resCrawlURIList.addAll(resultCrawlResURIList);
		parser.reset();
		return orginHtml;
	}

	/**
	 * 替换HTML内容中资源链接地址为指定地址，并返回包含了替换后HTML，原始资源链接与替换后资源链接对照的MAP对象
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @param savePath
	 *            资源保存路径
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 */
	public String replaceHtmlResource(String orginHtml, String savePath, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI) {
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML(orginHtml);
				orginHtml = replaceImages(orginHtml, savePath, resCrawlURIList, parentCrawlURI, parser);
				orginHtml = replaceLinkRes(orginHtml, savePath, resCrawlURIList, parentCrawlURI, parser);
				orginHtml = replaceObject(orginHtml, savePath, resCrawlURIList, parentCrawlURI, parser);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return orginHtml;
	}

	/**
	 * 替换图片
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param savePath
	 *            文件保存路径
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @param parser
	 *            Parser对象
	 * @return
	 * @throws ParserException
	 */
	private String replaceImages(String orginHtml, String savePath, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI, Parser parser) throws ParserException {
		log.info("========替换图片=========");
		// 图片地址MAP：KEY为新图片地址，value为源图片地址
		List<CrawlResURI> resultCrawlResURIList = new CopyOnWriteArrayList<CrawlResURI>();
		NodeFilter filter = new NodeClassFilter(ImageTag.class);
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		// 原始图片标签HTML
		String orginImgTagHtml = null;
		// 原始图片标签URL
		String orginImgTagUrl = null;
		// 新图片标签HTML
		String newImgTagHtml = null;
		// 新图片标签URL
		String newImgTagUrl = null;
		ImageTag imageTag = null;
		for (int i = 0; i < nodes.size(); i++) {
			imageTag = (ImageTag) nodes.elementAt(i);
			orginImgTagHtml = imageTag.toHtml();
			orginImgTagUrl = imageTag.getImageURL();
			newImgTagUrl = populateSavePath(orginImgTagUrl, savePath, "");
			// 设置新URL
			imageTag.setImageURL(newImgTagUrl);
			newImgTagHtml = imageTag.toHtml();
			resultCrawlResURIList.add(uriHelper.populateResURI(parentCrawlURI, orginImgTagUrl, newImgTagUrl, Constants.RES_IMAGES_MAP_KEY));
			orginHtml = StringUtils.replace(orginHtml, orginImgTagHtml, newImgTagHtml);
		}
		resCrawlURIList.addAll(resultCrawlResURIList);
		parser.reset();
		return orginHtml;
	}

	/**
	 * 替换内容中连接资源
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param savePath
	 *            文件保存路径
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @param parser
	 *            Parser对象
	 * @return
	 * @throws ParserException
	 */
	@SuppressWarnings("serial")
	private String replaceLinkRes(String orginHtml, String savePath, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI, Parser parser) throws ParserException {
		log.info("========替换内容中连接资源=========");
		// 资源地址MAP：KEY为新资源地址，value为源资源地址
		List<CrawlResURI> resultCrawlResURIList = new CopyOnWriteArrayList<CrawlResURI>();
		NodeFilter filter = new NodeClassFilter(LinkTag.class) {
			public boolean accept(Node node) {
				if (node instanceof LinkTag) {
					String url = ((LinkTag) node).getLink();
					String urlTypeName = FilenameUtils.getExtension(url.toLowerCase());
					if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(urlTypeName) && Constants.EXTRACT_MEDIA_RES_TYPE.contains(urlTypeName)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		};
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		// 原始资源标签HTML
		String orginTagHtml = null;
		// 原始资源标签URL
		String orginTagUrl = null;
		// 新资源标签HTML
		String newTagHtml = null;
		// 新资源标签URL
		String newTagUrl = null;
		LinkTag linkTag = null;
		for (int i = 0; i < nodes.size(); i++) {
			linkTag = (LinkTag) nodes.elementAt(i);
			orginTagHtml = linkTag.toHtml();
			orginTagUrl = linkTag.getLink();
			newTagUrl = populateSavePath(orginTagUrl, savePath, Constants.EXTRACT_RES_TYPE);
			// 设置新URL
			linkTag.setLink(newTagUrl);
			newTagHtml = linkTag.toHtml();
			if (StringUtils.isNotBlank(newTagUrl)) {
				resultCrawlResURIList.add(uriHelper.populateResURI(parentCrawlURI, orginTagUrl, newTagUrl, Constants.RES_ATTAC_MAP_KEY));
			}
			orginHtml = StringUtils.replace(orginHtml, orginTagHtml, newTagHtml);

		}
		resCrawlURIList.addAll(resultCrawlResURIList);
		parser.reset();
		return orginHtml;
	}

	/**
	 * 替换内容中对象资源
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param savePath
	 *            文件保存路径
	 * @param resCrawlURIList
	 *            资源URI对象集合
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @param parser
	 *            Parser对象
	 * @return
	 * @throws ParserException
	 */
	@SuppressWarnings({ "serial", "unused" })
	private String replaceObject(String orginHtml, String savePath, List<CrawlResURI> resCrawlURIList, CrawlURI parentCrawlURI, Parser parser) throws ParserException {
		log.info("========替换内容中对象资源=========");
		parser.setNodeFactory(factory);
		// 资源地址MAP：KEY为新资源地址，value为源资源地址
		List<CrawlResURI> resultCrawlResURIList = new CopyOnWriteArrayList<CrawlResURI>();
		NodeFilter filter = new NodeClassFilter(EmbedTag.class) {
			public boolean accept(Node node) {
				if (node instanceof EmbedTag) {
					String url = ((EmbedTag) node).getSrc();
					String urlTypeName = FilenameUtils.getExtension(url.toLowerCase());
					if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(urlTypeName) && Constants.EXTRACT_MEDIA_RES_TYPE.contains(urlTypeName)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		};
		NodeList nodes = parser.extractAllNodesThatMatch(filter);

		// 原始资源标签HTML
		String orginTagHtml = null;
		// 原始资源标签URL
		String orginTagUrl = null;
		// 新资源标签HTML
		String newTagHtml = null;
		// 新资源标签URL
		String newTagUrl = null;
		EmbedTag embedTag = null;
		for (int i = 0; i < nodes.size(); i++) {
			embedTag = (EmbedTag) nodes.elementAt(i);
			orginTagHtml = embedTag.toHtml();
			orginTagUrl = embedTag.getSrc();
			// newTagUrl =
			// populateSavePath(orginTagUrl,savePath,Constants.EXTRACT_MEDIA_RES_TYPE);
			newTagUrl = orginTagUrl;// 暂时不替换视频对象
			// 设置新URL
			embedTag.setSrc(newTagUrl);
			newTagHtml = embedTag.toHtml();
			if (StringUtils.isNotBlank(newTagUrl)) {
				resultCrawlResURIList.add(uriHelper.populateResURI(parentCrawlURI, orginTagUrl, newTagUrl, Constants.RES_MEDIA_MAP_KEY));
			}
			// 去掉原来OBJ对象
			orginHtml = StringUtils.replace(orginHtml, orginTagHtml, "");
			// orginHtml = StringUtils.replace(orginHtml, orginTagHtml,
			// newTagHtml);
		}
		resCrawlURIList.addAll(resultCrawlResURIList);
		parser.reset();
		return orginHtml;
	}

	/**
	 * 组装新地址
	 * 
	 * @param originResTagUrl
	 *            原始资源地址
	 * @param savePath
	 *            保存路径
	 * @param allowResTypeStr
	 *            运行采集回来的资源类型str
	 * @return
	 */
	private String populateSavePath(String originResTagUrl, String savePath, String allowResTypeStr) {
		StringBuilder resultStr = new StringBuilder();
		String resTypeName = FilenameUtils.getExtension(originResTagUrl);
		if (StringUtils.isNotBlank(originResTagUrl) && StringUtils.isNotBlank(savePath) && StringUtils.isNotBlank(resTypeName) && (StringUtils.isBlank(allowResTypeStr) || allowResTypeStr.contains(resTypeName.toLowerCase()))) {
			if (Boolean.valueOf(Constants.REPLACE_NAME)) {
				resultStr.append(savePath).append(R.UU16()).append(Constants.IMAGE_SPLIT).append(FilenameUtils.getExtension(originResTagUrl));
			} else {
				resultStr.append(savePath).append(FilenameUtils.getName(originResTagUrl));
			}

		}
		return resultStr.toString();
	}

	/**
	 * 替换内容中的超链接
	 * 
	 * @param orginHtml
	 *            原始HTML内容
	 * @return 去掉超链接后的HTML
	 */
	public String replaceHtmlLink(String orginHtml) {
		log.info("========替换内容中的超链接=========");
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				parser.setInputHTML(orginHtml);
				NodeFilter filter = new NodeClassFilter(LinkTag.class);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				String tempStr = "";
				for (int i = 0; i < nodes.size(); i++) {
					LinkTag linkTag = (LinkTag) nodes.elementAt(i);
					tempStr = linkTag.getLinkText();
					if (linkTag.getChildCount() > 0) {
						tempStr = linkTag.getChildrenHTML();
					}
					orginHtml = StringUtils.replace(orginHtml, linkTag.toHtml(), tempStr);
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return orginHtml;
	}

	/**
	 * 取得连接标题Map，如果有标题图片则放到资源MAP中
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param savePath
	 *            标题图片保存路径
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @param deleteAreaTagMap
	 *            待提取区域中要删除的标签属性Map
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 连接集合
	 */
	public List<CrawlLinkURI> getCrawlURIList(String orginHtml, String savePath, Map<String, String> fetchAreaTagMap, Map<String, String> deleteAreaTagMap, CrawlURI parentCrawlURI) {
		return getUrlAndTitleAndImagesMap(orginHtml, savePath, fetchAreaTagMap, deleteAreaTagMap, parentCrawlURI);
	}

	/**
	 * 得到连接标题MAP
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param savePath
	 *            标题图片保存路径
	 * @param parentCrawlURI
	 *            父crawlURI
	 * @return 连接或者标题集合
	 */
	private List<CrawlLinkURI> getUrlAndTitleAndImagesMap(String html, String savePath, Map<String, String> fetchAreaTagMap, Map<String, String> deleteAreaTagMap, CrawlURI parentCrawlURI) {
		log.info("========开始得到连接标题MAP=========savePath:" + savePath);
		log.info("========连接标题MAP=========getLinksetStartMap：" + fetchAreaTagMap + ",getLinksetEndMap:" + deleteAreaTagMap);
		List<CrawlLinkURI> crawlList = new CopyOnWriteArrayList<CrawlLinkURI>();
		try {
			html = getHtmlByFilter(fetchAreaTagMap, deleteAreaTagMap, html);
			Parser parser = new Parser();
			parser.setInputHTML(html);

			CrawlLinkURI crawlURI = null;
			NodeFilter filter = new NodeClassFilter(LinkTag.class);
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			String newImagePath = "";
			String orignImagePath = "";
			String title = "";
			for (int i = 0; i < nodes.size(); i++) {
				LinkTag linkTag = (LinkTag) nodes.elementAt(i);

				// orignImagePath = "";
				// newImagePath = "";
				title = linkTag.getLinkText();
				String altTitle = linkTag.getAttribute("title");
				title = StringUtils.defaultIfBlank(title, altTitle);
				// 取得连接中的子标签
				NodeList childrenList = linkTag.getChildren();
				for (int j = 0; j < childrenList.size(); j++) {
					Node node = childrenList.elementAt(j);
					// 如果有图片
					if (null != node && node instanceof ImageTag) {
						ImageTag imageTag = (ImageTag) node;
						orignImagePath = imageTag.getImageURL();
						newImagePath = populateSavePath(orignImagePath, savePath, "");
						title = linkTag.getAttribute("title");
						break;
					}
				}
				if (StringUtils.isNotBlank(linkTag.getLink()) && StringUtils.isNotBlank(title)) {
					crawlURI = uriHelper.populateCrawlURI(parentCrawlURI, linkTag.getLink(), title);
					if (StringUtils.isNotBlank(orignImagePath)) {
						CrawlResURI resURI = uriHelper.populateResURI(parentCrawlURI, orignImagePath, newImagePath, Constants.RES_IMAGES_MAP_KEY);
						crawlURI.setResURI(resURI);
					}
					crawlList.add(crawlURI);
				}
			}
			return crawlList;
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 组装CrawlLinkURI集合
	 * 
	 * @param list
	 *            连接集合
	 * @param urlStr
	 *            连接
	 * @param title
	 *            标题
	 * @param parentCrawlURI
	 *            父CrawlURI对象
	 */
	private void populateList(List<CrawlLinkURI> list, String urlStr, String title, CrawlURI parentCrawlURI) {
		urlStr = StringUtils.trim(urlStr);
		if (StringUtils.isNotBlank(urlStr)) {
			CrawlLinkURI url = uriHelper.populateCrawlURI(parentCrawlURI, urlStr, title);
			if (!list.contains(url)) {
				list.add(url);
			}
		}
	}

	/**
	 * 取得指定区域的HTML内容
	 * 
	 * @param fetchAreaTagMap
	 *            待提取区域标签属性Map
	 * @param deleteAreaTagMap
	 *            待提取区域中要删除的标签属性Map
	 * @param orginHtml
	 *            原始HTML
	 * @return 指定区域的HTML内容
	 * @throws ParserException
	 */
	private String getHtmlByFilter(Map<String, String> fetchAreaTagMap, Map<String, String> deleteAreaTagMap, String orginHtml) {
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				Parser parser = new Parser();
				// 第一步取得指定属性/标签内容
				orginHtml = filterRequire(fetchAreaTagMap, parser, orginHtml);
				// 第二步过滤指定属性/标签内容
				orginHtml = filterThrowAway(deleteAreaTagMap, parser, orginHtml);
				// 第三步过滤其他标签
				orginHtml = filterOtherTag(parser, orginHtml);
				log.info("=================================采集结果=======================================");
				log.info(orginHtml);
				return orginHtml;
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 采集指定属性/标签内容 如果没有找到，则在注释中查找
	 * 
	 * @param tagMap
	 *            指定属性/标签
	 * @param orginHtml
	 *            原始HTML
	 * @param parser
	 * @return
	 * @throws ParserException
	 */
	private String filterRequire(Map<String, String> tagMap, Parser parser, String orginHtml) throws ParserException {
		String resultHtml = getNaturalHtml(tagMap, parser, orginHtml);
		// 如果没有找到，则在注释内容中找
		if (StringUtils.isEmpty(resultHtml)) {
			List<String> remarkList = new ArrayList<String>();
			findAllRemark(parser, remarkList, orginHtml);
			for (String remark : remarkList) {
				resultHtml = getNaturalHtml(tagMap, parser, remark);
				if (StringUtils.isNotBlank(resultHtml))
					break;
			}
		}
		return resultHtml;
	}

	/**
	 * 递归取得所有注释内容
	 * 
	 * @param parser
	 * @param remarkList
	 * @param orginHtml
	 */
	private void findAllRemark(Parser parser, List<String> remarkList, String orginHtml) {
		String resultHtml = getRemarkHtml(parser, orginHtml);
		if (StringUtils.isNotBlank(resultHtml)) {
			remarkList.add(resultHtml);
			findAllRemark(parser, remarkList, resultHtml);
		}
	}

	/**
	 * 在正常的HTML内容中采集指定属性/标签内容
	 * 
	 * @param tagMap
	 *            指定属性/标签
	 * @param orginHtml
	 *            原始HTML
	 * @param parser
	 * @return
	 * @throws ParserException
	 */
	private String getNaturalHtml(Map<String, String> tagMap, Parser parser, String orginHtml) throws ParserException {
		if (null != tagMap && !tagMap.isEmpty()) {
			log.info("========原始HTML=========");
			log.info(orginHtml);
			log.info("========开始采集指定属性/标签内容=========");
			log.info("指定属性/标签=========" + tagMap);
			parser.setInputHTML(orginHtml);
			StringBuilder sb = new StringBuilder();
			// 第一步取得指定属性/标签内容
			List<NodeFilter> nodeFilterList = populateFilter(parser, tagMap);
			for (NodeFilter filter : nodeFilterList) {
				appendHtmlByFilter(parser, filter, sb);
			}
			log.info("========采集指定属性/标签内容结果=========");
			log.info(sb.toString());
			return sb.toString();
		}
		return orginHtml;
	}

	/**
	 * 取得注释内容
	 * 
	 * @param parser
	 *            解析器
	 * @param orginHtml
	 *            原始HTML
	 * @return 注释内容
	 */
	private String getRemarkHtml(Parser parser, String orginHtml) {
		log.info("========取得页面注释内容=========");
		NodeFilter filter = new NodeClassFilter(RemarkNode.class);
		StringBuilder sb = new StringBuilder();
		try {
			parser.setInputHTML(orginHtml);
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < nodes.size(); i++) {
				Node textnode = (Node) nodes.elementAt(i);
				sb.append(textnode.getText());
			}
			return sb.toString();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得指定属性/标签无格式内容集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @param tagMap
	 *            指定属性/标签
	 * @return 无格式内容集合
	 * @throws ParserException
	 */
	public List<String> getPlainTextList(String orginHtml, Map<String, String> fetchAreaTagMap) {
		Parser parser = new Parser();
		List<String> list = new CopyOnWriteArrayList<String>();
		if (StringUtils.isNotBlank(orginHtml)) {
			try {
				parser.setInputHTML(orginHtml);
				// 取得指定属性/标签内容
				List<NodeFilter> nodeFilterList = populateFilter(parser, fetchAreaTagMap);
				for (NodeFilter filter : nodeFilterList) {
					addPlainTextToList(parser, filter, list);
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 过滤其他标签
	 * 
	 * @param parser
	 *            解析器
	 * @param contentHtml
	 *            原始内容
	 * @return 过滤后内容
	 * @throws ParserException
	 */
	private String filterOtherTag(Parser parser, String contentHtml) throws ParserException {
		log.info("========开始过滤其他标签=========");
		// 过滤注释
		NodeFilter filter = new NodeClassFilter(RemarkNode.class);
		contentHtml = removeHtmlByFilter(parser, filter, contentHtml);
		return contentHtml;
	}

	/**
	 * 过滤指定标签
	 * 
	 * @param removeTagMap
	 *            指定标签属性MAP
	 * @param parser
	 *            解析器
	 * @param origin
	 * @return
	 * @throws ParserException
	 */
	private String filterThrowAway(Map<String, String> removeTagMap, Parser parser, String orginHtml) throws ParserException {
		log.info("========开始过滤指定标签=========");
		if (null != removeTagMap && !removeTagMap.isEmpty()) {
			List<NodeFilter> nodeFilterList;
			nodeFilterList = populateFilter(parser, removeTagMap);
			for (NodeFilter filter : nodeFilterList) {
				orginHtml = removeHtmlByFilter(parser, filter, orginHtml);
			}
		}
		return orginHtml;
	}

	/**
	 * 根据参数MAP组装NodeFilter集合
	 * 
	 * @param parser
	 *            Parser对象
	 * @param map
	 *            参数MAP
	 * @return NodeFilter集合
	 * @throws ParserException
	 */
	private List<NodeFilter> populateFilter(Parser parser, Map<String, String> map) throws ParserException {
		List<NodeFilter> nodeFilterList = new CopyOnWriteArrayList<NodeFilter>();
		String tempKey = null;
		String tempValue = null;
		String[] tempValueArr = null;
		NodeFilter filter = null;
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			tempKey = it.next();
			tempValue = map.get(tempKey);
			if (tempValue.contains("|")) {
				tempValueArr = tempValue.split("\\|");
			} else {
				tempValueArr = new String[] { tempValue };
			}
			for (String value : tempValueArr) {
				filter = populateFilter(tempKey, value);
				nodeFilterList.add(filter);
			}
		}
		return nodeFilterList;
	}

	/**
	 * 组装过滤器
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值,支持正则表达式
	 * @return 过滤器
	 */
	@SuppressWarnings("serial")
	private NodeFilter populateFilter(final String key, final String value) {
		NodeFilter filter;
		if (Constants.SINGLE_TAG.equals(key)) {
			filter = new TagNameFilter(value);
		} else {
			filter = new HasAttributeFilter() {
				public boolean accept(Node node) {
					if (node instanceof CompositeTag) {
						CompositeTag tag = (CompositeTag) node;
						// 如果匹配 则返回TRUE
						if (StringUtils.isNotBlank(tag.getAttribute(key)) && tag.getAttribute(key).matches(value)) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				}
			};
		}
		return filter;
	}

	/**
	 * 过滤指定属性标签HTML
	 * 
	 * @param parser
	 *            解析器
	 * @param filter
	 *            属性过滤器
	 * @param orginHtml
	 *            原始HTML
	 * @return 过滤后HTML
	 * @throws ParserException
	 */
	private String removeHtmlByFilter(Parser parser, NodeFilter filter, String orginHtml) throws ParserException {
		parser.setInputHTML(orginHtml);
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		for (int i = 0; i < nodes.size(); i++) {
			Node textnode = (Node) nodes.elementAt(i);
			orginHtml = StringUtils.remove(orginHtml, textnode.toHtml());
		}
		return orginHtml;
	}

	/**
	 * 查找指定属性，指定属性值正则表达式匹配的所有CompositeTa
	 * 
	 * @param node
	 *            节点
	 * @param tagList
	 *            CompositeTa集合
	 * @param attributeName
	 *            属性
	 * @param attributeValueMatches
	 *            属性值正则表达式
	 */
	@SuppressWarnings("unused")
	private void findAllCompositeTagByMatches(Node node, List<CompositeTag> tagList, String attributeName, String attributeValueMatches) {
		if (node instanceof CompositeTag) {
			CompositeTag tag = (CompositeTag) node;
			// 查找指定属性，指定属性值正则表达式的节点
			if (StringUtils.isNotBlank(tag.getAttribute(attributeName)) && tag.getAttribute(attributeName).matches(attributeValueMatches)) {
				tagList.add(tag);
			}
		}
		NodeList nodeList = node.getChildren();
		for (int i = 0; i < nodeList.size(); i++) {
			Node nextNode = (Node) nodeList.elementAt(i);
			if (null != nextNode.getChildren()) {
				findAllCompositeTagByMatches(nextNode, tagList, attributeName, attributeValueMatches);
			}
		}
	}

	/**
	 * 取得所有指定属性/标签的HTML
	 * 
	 * @param parser
	 *            解析器
	 * @param filter
	 *            过滤器
	 * @param sb
	 * @throws ParserException
	 */
	private void appendHtmlByFilter(Parser parser, NodeFilter filter, StringBuilder sb) throws ParserException {
		log.info("========开始取得所有指定属性/标签的HTML=========");
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		for (int i = 0; i < nodes.size(); i++) {
			Node textnode = (Node) nodes.elementAt(i);
			sb.append(textnode.toHtml());
			log.info("=========================HTML：=========" + textnode.toHtml());
		}
	}

	/**
	 * 取得所有指定属性/标签的HTML,并放在LIST中
	 * 
	 * @param parser
	 *            解析器
	 * @param filter
	 *            过滤器
	 * @param list
	 * @throws ParserException
	 */
	private void addHtmlToListByFilter(Parser parser, NodeFilter filter, List<String> list) throws ParserException {
		log.info("========开始取得所有指定属性/标签的HTML,并放在LIST中=========");
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		for (int i = 0; i < nodes.size(); i++) {
			Node textnode = (Node) nodes.elementAt(i);
			list.add(textnode.toHtml());
			log.info("=========================HTML：=========" + textnode.toHtml());
		}
	}

	/**
	 * 取得所有指定属性/标签的HTML
	 * 
	 * @param parser
	 *            解析器
	 * @param filter
	 *            过滤器
	 * @param sb
	 * @throws ParserException
	 */
	private void addPlainTextToList(Parser parser, NodeFilter filter, List<String> list) throws ParserException {
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		for (int i = 0; i < nodes.size(); i++) {
			Node textnode = (Node) nodes.elementAt(i);
			if (StringUtils.isNotBlank(textnode.toPlainTextString())) {
				list.add(textnode.toPlainTextString().replaceAll("\\s", ""));
			}
		}
	}

	/**
	 * 解析并组装采集参数，支持标签属性/值形式和标签名称形式，可混合使用 <li>约定采集参数格式如下</li> <li>
	 * 1，标签属性/值形式，如：class=articleList.*|tips,id=fxwb|fxMSN|fxMSN 注意：属性值 支持正则表达式</li>
	 * <li>2，标签名称形式，如：div,p,span</li> <li>
	 * 3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span</li>
	 * 
	 * @param paramMap
	 *            参数map
	 * @param str
	 *            参数字符串
	 */
	@SuppressWarnings("unused")
	private void populateParamMap(Map<String, String> paramMap, String paramStr) {
		String[] paramStrArr = paramStr.split(",");
		String[] tempStrArr = null;
		StringBuilder sb = new StringBuilder();
		for (String temp : paramStrArr) {
			if (temp.contains("=")) {
				tempStrArr = temp.split("=");
				paramMap.put(tempStrArr[0], tempStrArr[1]);
			} else {
				if (StringUtils.isNotBlank(temp)) {
					sb.append(temp).append("|");
				}
			}
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			paramMap.put(Constants.SINGLE_TAG, sb.substring(0, sb.length() - 1));
		}
	}

	/**
	 * 测试方法-打开文件并返回内容
	 * 
	 * @param szFileName
	 *            文件绝对地址
	 * @param charset
	 *            字符集
	 * @return 内容
	 */
	public static String openFile(String szFileName, String charset) {
		try {
			BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream(new File(szFileName)), charset));
			StringBuilder szContent = new StringBuilder();
			String szTemp;

			while ((szTemp = bis.readLine()) != null) {
				szContent.append(szTemp).append("\n");
			}
			bis.close();
			return szContent.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) throws ParserException, URISyntaxException, IOException {

	}

}
