package com.rekoe.module;

import org.apache.commons.codec.binary.Base64;
import org.nutz.json.Json;

public class FacebookSignedRequest {
	
	private String algorithm;
	private Long expires;
	private Long issued_at;
	private String oauth_token;
	private Long user_id;
	private FacebookSignedRequestUser user;
	
	
	
	public static <T extends FacebookSignedRequest> T getFacebookSignedRequest(String signedRequest,Class<T> clazz) 
			throws Exception {
		String payload = signedRequest.split("[.]", 2)[1];
		payload = payload.replace("-", "+").replace("_", "/").trim();
		String jsonString = new String(Base64.decodeBase64(payload.getBytes()));
		return Json.fromJson(clazz, jsonString);
		
	}
	
	public static void main(String[] args) {
		
		String code = "ueLUKJlJZF7FIwqRHSi1fW6BvZKIhACIk7UpWUTtWqE.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImV4cGlyZXMiOjEzODE3NDg0MDAsImlzc3VlZF9hdCI6MTM4MTc0MjM0OCwib2F1dGhfdG9rZW4iOiJDQUFLVjBpRGZFRWdCQUZMREI1N0g3WkFhNlByYmpoSlpCaVRLWkEyaFpDMGVIVGpuQ2huMzRrN2Jkc1hrVWxsZ3BobFJtUWtoMTFzMjlPOUxReDNqd1hvbFBJYnZteTAxZVB2Z2xCWkNIM2haQkk5RWVmWkFaQjdYRGtaQWdLRVVidEZaQ2J6ZlVFMVFLTmpBTFdLc2xaQmV3bENrTHFRd0wxeGpRWUV1SElKcjB0R1M0cXREeVFyd0tNT0RmRUpsMHB4NzRMT1BwQkNsMnduakFaRFpEIiwidXNlciI6eyJjb3VudHJ5IjoidXMiLCJsb2NhbGUiOiJ6aF9UVyIsImFnZSI6eyJtaW4iOjIxfX0sInVzZXJfaWQiOiIxMDAwMDQ3Nzc5NTg1MjUifQ";
		//String code = "PFwUoyYGHCWywdxmyjYZQl-_OsKM0R_DDOeuqeS5LgE.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImlzc3VlZF9hdCI6MTM3NTk1MzMwMiwidXNlciI6eyJjb3VudHJ5IjoidHciLCJsb2NhbGUiOiJlbl9VUyIsImFnZSI6eyJtaW4iOjIxfX19";
		FacebookSignedRequest facebookSR;
		try {
			facebookSR = FacebookSignedRequest.getFacebookSignedRequest(code,FacebookSignedRequest.class);
			String accessToken = facebookSR.getOauth_token();
			System.out.println(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getAlgorithm() {
		return algorithm;
	}
	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public Long getExpires() {
		return expires;
	}
	
	public void setExpires(Long expires) {
		this.expires = expires;
	}
	
	public Long getIssued_at() {
		return issued_at;
	}
	
	public void setIssued_at(Long issued_at) {
		this.issued_at = issued_at;
	}
	
	public String getOauth_token() {
		return oauth_token;
	}
	
	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public FacebookSignedRequestUser getUser() {
		return user;
	}

	public void setUser(FacebookSignedRequestUser user) {
		this.user = user;
	}
	
	public static class FacebookSignedRequestUser {

		private String country;
		private String locale;
		private FacebookSignedRequestUserAge age;
		
		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getLocale() {
			return locale;
		}

		public void setLocale(String locale) {
			this.locale = locale;
		}

		public FacebookSignedRequestUserAge getAge() {
			return age;
		}

		public void setAge(FacebookSignedRequestUserAge age) {
			this.age = age;
		}

		public static class FacebookSignedRequestUserAge{
			private int min;
			private int max;

			public int getMin() {
				return min;
			}

			public void setMin(int min) {
				this.min = min;
			}

			public int getMax() {
				return max;
			}

			public void setMax(int max) {
				this.max = max;
			}
		}
	}
}
