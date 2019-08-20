package com.equinox.util.exception;

import java.io.Serializable;

/**
 * @author yangjiwei
 * @Description:
 * @name: ParamCheckException
 * @date 2017/9/19 18:30
 */
public class ParamCheckException  extends BusinessException implements Serializable {
    private static final long serialVersionUID = 639684179287722089L;

    public ParamCheckException() {
    }

    public ParamCheckException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    public ParamCheckException(String errorCode, Throwable e, Object... args) {
        super(errorCode, e, args);
    }
}