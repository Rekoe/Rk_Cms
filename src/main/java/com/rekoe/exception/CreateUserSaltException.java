package com.rekoe.exception;

import org.apache.shiro.authc.AuthenticationException;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
public class CreateUserSaltException extends AuthenticationException {

	private static final long serialVersionUID = 3315875923669742156L;

	public CreateUserSaltException() {
		super();
	}

	public CreateUserSaltException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreateUserSaltException(String message) {
		super(message);
	}

	public CreateUserSaltException(Throwable cause) {
		super(cause);
	}
}
