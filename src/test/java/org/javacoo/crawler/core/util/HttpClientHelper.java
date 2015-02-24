/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.crawler.core.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.CrawlScope;
import com.rekoe.crawler.core.data.uri.CrawlURI;

/**
 * 
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-10-19 下午2:15:32
 * @version 1.0
 */
public class HttpClientHelper {
	private static Log log = LogFactory.getLog(HttpClientHelper.class);

	public static CloseableHttpClient createHttpClient(final CrawlScope crawlScope) {
		HttpHost proxy = null;
		if (crawlScope.isUseProxy()) {
			if (StringUtils.isNotBlank(crawlScope.getProxyAddress()) && StringUtils.isNotBlank(crawlScope.getProxyPort()) && StringUtils.isNumeric(crawlScope.getProxyPort())) {
				proxy = new HttpHost(crawlScope.getProxyAddress(), Integer.valueOf(crawlScope.getProxyPort()), Constants.HTTP_CLIENT_REG_HTTP_KEY);
				log.info("=====================使用界面传入的代理========地址：" + crawlScope.getProxyAddress() + ":" + crawlScope.getProxyPort());
			} else if (!CollectionUtils.isEmpty(Constants.PROXY_SERVER_LIST)) {
				Map<String, Integer> proxyMap = Constants.PROXY_SERVER_LIST.get((int) (Math.random() * (Constants.PROXY_SERVER_LIST.size() - 1)) + 0);
				String key = null;
				for (Iterator<String> it = proxyMap.keySet().iterator(); it.hasNext();) {
					key = it.next();
					proxy = new HttpHost(key, proxyMap.get(key), Constants.HTTP_CLIENT_REG_HTTP_KEY);
					log.info("=====================随即使用网络蜘蛛参数配置的代理========地址：" + key + ":" + proxyMap.get(key));
				}
			}
		}
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

			@Override
			public HttpMessageParser<HttpResponse> create(SessionInputBuffer buffer, MessageConstraints constraints) {
				LineParser lineParser = new BasicLineParser() {

					@Override
					public Header parseHeader(final CharArrayBuffer buffer) {
						try {
							return super.parseHeader(buffer);
						} catch (ParseException ex) {
							return new BasicHeader(buffer.toString(), null);
						}
					}

				};
				return new DefaultHttpResponseParser(buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {

					@Override
					protected boolean reject(final CharArrayBuffer line, int count) {
						return false;
					}

				};
			}

		};
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(requestWriterFactory, responseParserFactory);
		SSLContext sslcontext = SSLContexts.createSystemDefault();
		X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(Constants.HTTP_CLIENT_REG_HTTP_KEY, PlainConnectionSocketFactory.INSTANCE).register(Constants.HTTP_CLIENT_REG_HTTPS_KEY, new SSLConnectionSocketFactory(sslcontext, hostnameVerifier)).build();

		DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

			@Override
			public InetAddress[] resolve(final String host) throws UnknownHostException {
				if (host.equalsIgnoreCase("myhost")) {
					return new InetAddress[] { InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }) };
				} else {
					return super.resolve(host);
				}
			}

		};

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(Constants.HTTP_SOCKET_TIMEOUT).build();
		connManager.setDefaultSocketConfig(socketConfig);
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();
		connManager.setDefaultConnectionConfig(connectionConfig);

		connManager.setMaxTotal(Constants.HTTP_CLIENT_MAX_CONN);
		connManager.setDefaultMaxPerRoute(Constants.HTTP_CLIENT_MAX_ROUTE);

		CookieStore cookieStore = new BasicCookieStore();
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		RequestConfig defaultRequestConfig = getDefaultRequestConfig();
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).setDefaultCookieStore(cookieStore).setDefaultCredentialsProvider(credentialsProvider).setProxy(proxy).setDefaultRequestConfig(defaultRequestConfig).build();
		return httpclient;
	}

	public static RequestConfig getDefaultRequestConfig() {
		RequestConfig defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).setExpectContinueEnabled(true).setStaleConnectionCheckEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST)).setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		return defaultRequestConfig;
	}

	public static HttpGet getHttpGet(CrawlURI uri) {
		Log log = LogFactory.getLog(HttpGet.class);
		String pathType = uri.getPathType();
		// 取得当前URL的路径
		String rawPath = uri.getRawPath();
		// 绝对路径
		if (Constants.PATH_TYPE_2.equals(pathType)) {
			// 如果是相对当前路径，则取得父路径的RawPath在加上当前RawPath
			if (null != uri.getParentURI()) {
				rawPath = getRawPath(uri.getParentURI().getRawPath()) + rawPath;
			}
		}
		log.info("==============当前访问路径========" + rawPath);
		HttpGet httpGet = new HttpGet(rawPath);
		RequestConfig requestConfig = RequestConfig.copy(HttpClientHelper.getDefaultRequestConfig()).setSocketTimeout(Constants.HTTP_SOCKET_TIMEOUT).setConnectTimeout(Constants.HTTP_CONN_TIMEOUT).setConnectionRequestTimeout(Constants.HTTP_CONN_TIMEOUT).build();
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625 Firefox/3.6.6 Greatwqs");
		httpGet.setHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		httpGet.setHeader("Keep-Alive", "300");
		httpGet.setHeader("Connection", "Keep-Alive");
		httpGet.setHeader("Cache-Control", "no-cache");
		return httpGet;
	}

	/**
	 * 取得URI对象的路径
	 * 
	 * @param uri
	 * @return
	 */
	private static String getRawPath(String rawPath) {
		if (StringUtils.isBlank(rawPath) || rawPath.lastIndexOf("/") == rawPath.indexOf("/")) {
			rawPath = "";
		} else {
			int endPos = rawPath.lastIndexOf("/");
			rawPath = rawPath.substring(0, endPos);
		}
		return rawPath;
	}

	public static HttpClientContext getHttpClientContext() {
		// Execution context can be customized locally.
		HttpClientContext context = HttpClientContext.create();
		// Contextual attributes set the local context level will take
		// precedence over those set at the client level.
		CookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		context.setCredentialsProvider(credentialsProvider);
		return context;
	}

	public static void main(String[] args) throws Exception {
		CookieStore cookieStore = null;
		HttpClientContext context = null;
		String testUrl = "http://127.0.0.1";
		System.out.println("----testCookieStore");
		testLogin(cookieStore);
		// 使用cookieStore方式
		CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet httpGet = new HttpGet(testUrl);
		System.out.println("request line:" + httpGet.getRequestLine());
		try {
			// 执行get请求
			HttpResponse httpResponse = client.execute(httpGet);
			System.out.println("cookie store:" + cookieStore.getCookies());
			printResponse(httpResponse);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		System.out.println("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			System.out.println("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			System.out.println("response length:" + responseString.length());
			System.out.println("response content:" + responseString.replace("\r\n", ""));
		}
	}

	public static void testLogin(CookieStore cookieStore) throws Exception {
		System.out.println("----testLogin");
		String testUrl = "http://127.0.0.1";
		String loginUrl = "http://127.0.0.1/login.jspx";

		// // 创建HttpClientBuilder
		// HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// // HttpClient
		// CloseableHttpClient client = httpClientBuilder.build();
		// 直接创建client
		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(loginUrl);
		Map parameterMap = new HashMap();
		parameterMap.put("username", "LY");
		parameterMap.put("password", "123456");
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
		httpPost.setEntity(postEntity);
		System.out.println("request line:" + httpPost.getRequestLine());
		try {
			// 执行post请求
			HttpResponse httpResponse = client.execute(httpPost);
			// String location =
			// httpResponse.getFirstHeader("Location").getValue();
			// if (location != null && location.startsWith("")) {
			// System.out.println("----loginError");
			// }
			printResponse(httpResponse);

			// 执行get请求
			System.out.println("----the same client");
			HttpGet httpGet = new HttpGet(testUrl);
			System.out.println("request line:" + httpGet.getRequestLine());
			HttpResponse httpResponse1 = client.execute(httpGet);
			printResponse(httpResponse1);

			// cookie store
			cookieStore = setCookieStore(httpResponse);
			// context
			// setContext();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<NameValuePair> getParam(Map parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue()));
		}
		return param;
	}

	public static BasicCookieStore setCookieStore(HttpResponse httpResponse) {
		System.out.println("----setCookieStore");
		BasicCookieStore cookieStore = new BasicCookieStore();
		// JSESSIONID
		String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
		String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
		System.out.println("JSESSIONID:" + JSESSIONID);
		// 新建一个Cookie
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
		cookie.setVersion(0);
		cookie.setDomain("127.0.0.1");
		cookie.setPath("/CwlProClient");
		// cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
		// cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
		// cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
		// cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
		cookieStore.addCookie(cookie);
		return cookieStore;
	}
}
