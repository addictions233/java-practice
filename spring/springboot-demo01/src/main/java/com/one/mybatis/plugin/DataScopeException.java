package com.one.mybatis.plugin;

import org.springframework.dao.UncategorizedDataAccessException;

public class DataScopeException extends UncategorizedDataAccessException {

    private static final long serialVersionUID = 5449813393495389873L;

    public DataScopeException(String msg) {
        super(msg, null);
    }

    public DataScopeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
