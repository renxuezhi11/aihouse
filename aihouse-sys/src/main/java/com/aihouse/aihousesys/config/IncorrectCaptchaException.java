package com.aihouse.aihousesys.config;

import org.apache.shiro.authc.AuthenticationException;

public class IncorrectCaptchaException extends AuthenticationException {
    private static final long serivalVersionUID = 1L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
