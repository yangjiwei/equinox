package com.equinox.util.exception;

import java.io.Serializable;

/**
 * @author liufengyu
 * @date 2014年4月17日
 */
public class InputCheckException extends ProgramException implements Serializable {
    private static final long serialVersionUID = 639684179287722085L;

    /**
     * 默认构造函数，反序列化时使用
     */
    public InputCheckException() {
    }

    /**
     * @param message
     */
    public InputCheckException(String message) {
        super(message);
    }

    /**
     * @param message
     */
    public InputCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
