package com.rekoe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserUtils;
import org.nutz.http.Http;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.domain.CmsAcquisition;

public class HtmlParserImpl implements ParseHtmlTool {
	private final static Log log = Logs.get();
	/** 连接集合标志 */
	private static String LINK_LIST = "linkList";
	/** 标题集合标志 */
	private static String TITLE_LIST = "titleList";
	/** 单标签标志 */
	private static String SINGLE_TAG = "singleTag";
	/** 连接正则表达式 */
	private static String LINK_REGX = "<a.*href=\"(.*?)\".*>(.*?)</a>";
	/** 正则表达式对象 */
	private Pattern pt = Pattern.compile(LINK_REGX);
	/** 采集参数bean */
	private ParamBean paramBean;

	public HtmlParserImpl(CmsAcquisition acqu) {
		parseRequestParam(acqu);
	}

	/**
	 * 取得标题集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 标题集合
	 */
	public List<String> getTitleList(String orginHtml) {
		orginHtml = getHtmlByFilter(paramBean.getLinksetStartMap(), paramBean.getLinksetEndMap(), orginHtml);
		if (StringUtils.isNotEmpty(orginHtml)) {
			return getUrlOrTitleListByType(orginHtml, TITLE_LIST);
		}
		return null;
	}

	/**
	 * 取得连接集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 连接集合
	 */
	public List<String> getUrlList(String orginHtml) {
		orginHtml = getHtmlByFilter(paramBean.getLinksetStartMap(), paramBean.getLinksetEndMap(), orginHtml);
		if (StringUtils.isNotEmpty(orginHtml)) {
			return getUrlOrTitleListByType(orginHtml, LINK_LIST);
		}
		return null;
	}

	/**
	 * 取得指定区域的HTML内容
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 指定区域的HTML内容
	 * @throws ParserException
	 */
	public String getHtml(String orginHtml) {
		orginHtml = getHtmlByFilter(paramBean.getContentStartMap(), paramBean.getContentEndMap(), orginHtml);
		return htmlInit(orginHtml);
	}

	/**
	 * 解析采集参数,并封装到ParamBean
	 * 
	 * @param acqu
	 *            原始采集参数
	 * @return 采集参数封装bean
	 */
	private void parseRequestParam(CmsAcquisition acqu) {
		paramBean = new ParamBean();
		if (!StringUtils.isEmpty(acqu.getLinksetStart())) {
			paramBean.setLinksetStartMap(populateParamMap(acqu.getLinksetStart()));
		}
		if (!StringUtils.isEmpty(acqu.getLinksetEnd())) {
			paramBean.setLinksetEndMap(populateParamMap(acqu.getLinksetEnd()));
		}
		if (!StringUtils.isEmpty(acqu.getContentStart())) {
			paramBean.setContentStartMap(populateParamMap(acqu.getContentStart()));
		}
		if (!StringUtils.isEmpty(acqu.getContentEnd())) {
			paramBean.setContentEndMap(populateParamMap(acqu.getContentEnd()));
		}
	}

	/**
	 * 得到地址集
	 * 
	 * @param html
	 *            html内容
	 * @param type
	 *            1 ：取得连接集合，2：取得标题集合
	 * @return 连接或者标题集合
	 */
	private List<String> getUrlOrTitleListByType(String html, String type) {
		List<String> resultList = new ArrayList<String>();
		Matcher m = pt.matcher(html);
		String result = "";
		int pos = 1;
		if (TITLE_LIST.equals(type)) {
			pos = 2;
		}
		while (m.find()) {
			result = m.group(pos);
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * 取得指定区域的HTML内容
	 * 
	 * @param tagMap
	 *            标签MAP
	 * @param removeTagMap
	 *            要过滤的标签MAP
	 * @param orginHtml
	 *            原始HTML
	 * @return 指定区域的HTML内容
	 * @throws ParserException
	 */
	private String getHtmlByFilter(Map<String, String> tagMap, Map<String, String> removeTagMap, String orginHtml) {
		try {
			Parser parser = new Parser();
			parser.setInputHTML(orginHtml);
			// 第一步取得指定属性/标签内容
			String tempKey = null;
			String tempValue = null;
			String[] tempValueArr = null;
			StringBuilder sb = new StringBuilder();
			NodeFilter filter = null;
			for (Iterator<String> it = tagMap.keySet().iterator(); it.hasNext();) {
				tempKey = it.next();
				tempValue = tagMap.get(tempKey);
				if (tempValue.contains("|")) {
					tempValueArr = tempValue.split("\\|");
				} else {
					tempValueArr = new String[] { tempValue };
				}
				for (String value : tempValueArr) {
					filter = populateFilter(tempKey, value);
					appendHtmlByFilter(parser, filter, sb);
				}
			}
			// 第二步过滤指定属性/标签内容
			String contentHtml = sb.toString();
			for (Iterator<String> it = removeTagMap.keySet().iterator(); it.hasNext();) {
				tempKey = it.next();
				tempValue = removeTagMap.get(tempKey);
				if (tempValue.contains("|")) {
					tempValueArr = tempValue.split("\\|");
				} else {
					tempValueArr = new String[] { tempValue };
				}
				for (String value : tempValueArr) {
					filter = populateFilter(tempKey, value);
					contentHtml = removeHtmlByFilter(parser, filter, contentHtml);
				}
			}
			// 第三步过滤注释
			filter = new NodeClassFilter(RemarkNode.class);
			contentHtml = removeHtmlByFilter(parser, filter, contentHtml);
			return contentHtml;
		} catch (ParserException e) {
			log.error(e);
		}
		return "";
	}

	/**
	 * 解析并组装采集参数，支持标签属性/值形式和标签名称形式，可混合使用
	 *
	 * 约定采集参数格式如下
	 * 
	 *
	 * 1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN
	 * 
	 *
	 * 2，标签名称形式，如：div,p,span
	 * 
	 *
	 * 3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span
	 * 
	 * @param paramStr
	 *            参数字符串
	 */
	private Map<String, String> populateParamMap(String paramStr) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] paramStrArr = paramStr.split(",");
		String[] tempStrArr = null;
		StringBuilder sb = new StringBuilder();
		for (String temp : paramStrArr) {
			if (temp.contains("=")) {
				tempStrArr = temp.split("=");
				paramMap.put(tempStrArr[0], tempStrArr[1]);
			} else {
				if (StringUtils.isNotEmpty(temp)) {
					sb.append(temp).append("|");
				}
			}
		}
		if (StringUtils.isNotEmpty(sb.toString())) {
			paramMap.put(SINGLE_TAG, sb.substring(0, sb.length() - 1));
		}
		return paramMap;
	}

	/**
	 * 组装过滤器
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return 过滤器
	 */
	private NodeFilter populateFilter(String key, String value) {
		NodeFilter filter;
		if (SINGLE_TAG.equals(key)) {
			filter = new TagNameFilter(value);
		} else {
			filter = new HasAttributeFilter(key, value);
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
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		log.info("匹配节点数:" + nodes.size());
		for (int i = 0; i < nodes.size(); i++) {
			Node textnode = (Node) nodes.elementAt(i);
			sb.append(textnode.toHtml());
		}
	}

	/**
	 * 解析并组装采集参数，支持标签属性/值形式和标签名称形式，可混合使用
	 *
	 * 约定采集参数格式如下
	 * 
	 *
	 * 1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN
	 * 
	 *
	 * 2，标签名称形式，如：div,p,span
	 * 
	 *
	 * 3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span
	 * 
	 * @param paramMap
	 *            参数map
	 * @param str
	 *            参数字符串
	 */
	private void populateParamMap(Map<String, String> paramMap, String paramStr) {
		String[] paramStrArr = paramStr.split(",");
		String[] tempStrArr = null;
		StringBuilder sb = new StringBuilder();
		for (String temp : paramStrArr) {
			if (temp.contains("=")) {
				tempStrArr = temp.split("=");
				paramMap.put(tempStrArr[0], tempStrArr[1]);
			} else {
				if (StringUtils.isNotEmpty(temp)) {
					sb.append(temp).append("|");
				}
			}
		}
		if (StringUtils.isNotEmpty(sb.toString())) {
			paramMap.put(SINGLE_TAG, sb.substring(0, sb.length() - 1));
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
			log.error(e);
			return "";
		}
	}

	/**
	 * 测试取得连接地址和标题
	 * 
	 * @throws ParserException
	 */
	public void testFetchLinkAndTitle() throws ParserException {
		String html = openFile("F:\\4.htm", "UTF-8");
		String result = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("class", "m_list");
		Map<String, String> notMap = new HashMap<String, String>();
		// notMap.put("class", "atc_ic_f");
		result = getHtmlByFilter(map, notMap, html);
		System.out.println("=============================result============================");
		System.out.println(result);
		System.out.println("==========================================================");
		Pattern pt = Pattern.compile("<a.*href=\"(.*?)\".*>(.*?)</a>");

		Matcher m = pt.matcher(result);
		String link = null;
		String title = null;
		while (m.find()) {
			link = m.group(1);
			title = m.group(2);
			if (StringUtils.isNotEmpty(link)) {
				System.out.println("url : " + link);
				System.out.println("title : " + title);
			}
		}
	}

	/**
	 * 测试取得内容
	 * 
	 * @throws ParserException
	 */
	public void testFetchContent() throws ParserException {
		String html = openFile("F:\\6.shtml", "GB2312");
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "artibody");
		Map<String, String> notMap = new HashMap<String, String>();
		notMap.put(SINGLE_TAG, "style|script");
		notMap.put("type", "text/javascript");
		notMap.put("class", "icon_fx|blkComment otherContent_01");
		notMap.put("style", "text-align: right;padding-right:10px;|margin-top:6px;|font-size: 12px ! important;|font-size:12px");
		notMap.put("id", "fxwb|fxMSN|fxMSN|comment_t_show_top");
		getHtmlByFilter(map, notMap, html);
	}

	/**
	 * 测试解析参数
	 */
	public void testParseParam() {
		Map<String, String> map = new HashMap<String, String>();
		populateParamMap(map, "class=articleList|tips,p,div");
		String tempKey = null;
		String tempValue = null;
		String[] tempValueArr = null;
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			tempKey = it.next();
			tempValue = map.get(tempKey);
			if (tempValue.contains("|")) {
				tempValueArr = tempValue.split("\\|");
			} else {
				tempValueArr = new String[] { tempValue };
			}
			for (String value : tempValueArr) {
				System.out.println("tempKey:" + tempKey);
				System.out.println("value:" + value);
			}
		}
	}

	/**
	 * 测试过滤标签
	 * 
	 * @throws ParserException
	 */
	public void testRemarkFilter() throws ParserException {
		String html = openFile("F:\\6.shtml", "GB2312");
		System.out.println("=========================过滤注释前HTML==================================");
		System.out.println(html);
		NodeFilter filter = new NodeClassFilter(RemarkNode.class);
		html = removeHtmlByFilter(new Parser(), filter, html);
		System.out.println("=========================过滤注释后HTML==================================");
		System.out.println(html);
	}

	// getHtmlByFilter(paramBean.getContentStartMap(),
	// paramBean.getContentEndMap(), orginHtml);
	public static void main(String[] args) throws ParserException, URISyntaxException, IOException {
		CmsAcquisition acqu = new CmsAcquisition();
		acqu.setPlanUrl("http://www.qq.com/");
		acqu.setLinksetStart("class=newsContent|bosszone=beijingnews");
		acqu.setLinksetEnd("");
		acqu.setPageEncoding("GBK");
		acqu.setTitleStart("false");
		acqu.setTitleEnd("true");
		acqu.setContentStart("style=TEXT-INDENT: 2em");
		acqu.setContentEnd("");
		HtmlParserImpl parseHtmlTool = new HtmlParserImpl(acqu);
		String orginHtml = Http.get(acqu.getPlanUrl()).getContent(acqu.getPageEncoding());
		List<String> linkList = parseHtmlTool.getUrlList(orginHtml);
		// List<String> linkList = parseHtmlTool.getTitleList(orginHtml);
		// System.out.println(linkList);
		for (String url : linkList) {
			String orginHtmlTemp = Http.get(url).getContent(acqu.getPageEncoding());
			String text = parseHtmlTool.getHtml(orginHtmlTemp);
			System.out.println(parseHtmlTool.htmlInit(text));
		}

		// NodeFilter filter = new NodeClassFilter(TagNode.class);
		// System.out.println(parseHtmlTool.removeHtmlByFilter(new Parser(),
		// filter, text));
		// parseHtmlTool.testParseParam();
		// parseHtmlTool.testFetchLinkAndTitle();
		// parseHtmlTool.testFetchContent();
		// parseHtmlTool.testRemarkFilter();
	}

	public String htmlInit(String htmlStr) {
		NodeFilter scriptFilter = new NodeClassFilter(ScriptTag.class);
		NodeFilter styleFilter = new NodeClassFilter(StyleTag.class);
		NodeFilter spanFilter = new NodeClassFilter(Span.class);
		NodeFilter[] filter = { scriptFilter, styleFilter,spanFilter };
		OrFilter orFilter = new OrFilter(filter);
		try {
			htmlStr = ParserUtils.trimTags(htmlStr, orFilter, true, true);
		} catch (ParserException e) {
			log.error(e);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return htmlStr;
	}
}