package com.aloogn.wjdc.redis.service.exception;

public class RedisException extends Exception{
    public RedisException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RedisException(String message, Throwable cause, boolean enableSuppression,
                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public RedisException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public RedisException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public RedisException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
