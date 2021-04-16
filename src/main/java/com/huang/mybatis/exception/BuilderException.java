package com.huang.mybatis.exception;

/**
 * @author: hsz
 * @date: 2021/4/14 15:44
 * @description:
 */

public class BuilderException extends RuntimeException {

    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}
