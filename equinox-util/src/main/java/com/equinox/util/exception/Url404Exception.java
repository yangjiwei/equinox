package com.equinox.util.exception;

import java.io.Serializable;

/**
 * @author yangjiwei
 * @Description:
 * @name: Url404Exception
 * @date 2018/4/3 15:50
 */
public class Url404Exception extends Exception implements Serializable {
    private static final long serialVersionUID = -1534307542383990569L;

    public Url404Exception(String message) {
        super(message);
    }

    public Url404Exception() {
    }
}