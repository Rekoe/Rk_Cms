package com.rekoe.socialauth;

import java.io.Serializable;


public class OAuthConfig implements Serializable{

	private static final long serialVersionUID = 1921829542203218159L;

	private final String _consumerKey;
	private final String _consumerSecret;
	private final String _consumerNameSpace;
	private final String _consumerPay;
	private final String _consumerUrl;
	private final String _consumer_login_err;
	private final String _signatureMethod;
	private String id;
	private Class<?> providerImplClass;

	/**
	 * 
	 * @param consumerKey
	 *            Application consumer key
	 * @param consumerSecret
	 *            Application consumer secret
	 * @param signatureMethod
	 *            Signature Method type
	 * @param transportName
	 *            Transport name
	 */
	public OAuthConfig(final String consumerKey, final String consumerSecret,
			final String signatureMethod, final String transportName,final String consumerNameSpace,final String payKey,final String url,final String login_err) {
		_consumerKey = consumerKey;
		_consumerSecret = consumerSecret;
		_consumerNameSpace = consumerNameSpace;
		_consumerPay = payKey;
		_consumerUrl = url;
		_consumer_login_err = login_err;
		if (signatureMethod == null || signatureMethod.length() == 0) {
			_signatureMethod = Constants.HMACSHA1_SIGNATURE;
		} else {
			_signatureMethod = signatureMethod;
		}
	}

	public OAuthConfig(final String consumerKey, final String consumerSecret,final String consumerNameSpace,final String payKey,final String url,final String login_err) {
		_consumerKey = consumerKey;
		_consumerSecret = consumerSecret;
		_consumerNameSpace = consumerNameSpace;
		_signatureMethod = Constants.HMACSHA1_SIGNATURE;
		_consumerPay = payKey;
		_consumerUrl = url;
		_consumer_login_err = login_err;
	}

	/**
	 * Retrieves the consumer key
	 * 
	 * @return the consumer key
	 */
	public String get_consumerKey() {
		return _consumerKey;
	}

	/**
	 * Retrieves the consumer secret
	 * 
	 * @return the consumer secret
	 */
	public String get_consumerSecret() {
		return _consumerSecret;
	}

	/**
	 * Retrieves the signature method
	 * 
	 * @return the signature method
	 */
	public String get_signatureMethod() {
		return _signatureMethod;
	}

	/**
	 * Retrieves the provider id
	 * 
	 * @return the provider id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Updates the provider id
	 * 
	 * @param id
	 *            the provider id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Retrieves the provider implementation class
	 * 
	 * @return the provider implementation class
	 */
	public Class<?> getProviderImplClass() {
		return providerImplClass;
	}

	/**
	 * Updates the provider implementation class
	 * 
	 * @param providerImplClass
	 *            the provider implementation class
	 */
	public void setProviderImplClass(final Class<?> providerImplClass) {
		this.providerImplClass = providerImplClass;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		result.append(" consumerKey: " + _consumerKey + NEW_LINE);
		result.append(" consumerSecret: " + _consumerSecret + NEW_LINE);
		result.append(" signatureMethod: " + _signatureMethod + NEW_LINE);
		result.append(" id: " + id + NEW_LINE);
		result.append(" providerImplClass: " + providerImplClass + NEW_LINE);
		result.append("}");
		return result.toString();
	}

	public String get_consumerPay() {
		return _consumerPay;
	}

	public String get_consumerNameSpace() {
		return _consumerNameSpace;
	}

	public String get_consumerUrl() {
		return _consumerUrl;
	}

	public String get_consumer_login_err() {
		return _consumer_login_err;
	}
	
}