/**
 *  Copyright (c)  2011-2020 Panguso, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of Panguso, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with Panguso.
 */
package com.equinox.util.exception;

import java.io.Serializable;

/**
 * 由于编程或环境错误导致的异常。
 * <p>
 * 该异常继承自RuntimeException，因此不需要在方法的Throws中声明， 该异常由全局异常处理程序统一处理，程序中不需要对该异常进行处理。
 * <p/>
 * 该异常主要应于下面场合：在业务层或数据层在调用第三方类库时，将 第三方类库抛出的由环境异常或编程错误导致的Exception转化成改异常，
 * 以便能够统一处理该类异常。
 * </p>
 *
 * @author liufengyu
 */
public class ProgramException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1913263321312485172L;

    /**
     * 默认构造函数，反序列化时使用
     */
    public ProgramException() {

    }

    /**
     * @param cause 原因异常
     */
    public ProgramException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * @param message 错误消息
     */
    public ProgramException(String message) {
        super(message);
    }

    /**
     * @param message 错误消息
     * @param cause   原因异常
     */
    public ProgramException(String message, Throwable cause) {
        super(message, cause);
    }

}
