package com.equinox.util.exception;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 业务逻辑异常 。
 * <p>
 * 业务层的每个方法都可能抛出该异常，该异常一般是在Action层的入口方法中捕获处理， 业务层没有必须的理由不要处理该异常。
 * </p>
 * <p/>
 * 注意：BusinessException类及子类使用时，最好使用BusinessException(String errorCode, Object
 * ... args)构造函数，
 * errorCode要在message_*.xml文件中定义。没有充分的理由，请不要使用BusinessException(String message,
 * Throwable cause) 构造函数。
 *
 * @author liufengyu
 */
public class BusinessException extends Exception implements Serializable {
    private String errorCode = null;
    private Object[] args = null;
    private String message = null;

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * 返回详细的错误消息。
     */
    public String getMessage() {
        return this.message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 默认构造函数，反序列化时使用
     */
    public BusinessException() {
    }

    public BusinessException(ErrorCode errorCode, Object... args){
        this(errorCode, (Throwable)null, args);
    }

    public BusinessException(ErrorCode errorCode, Throwable cause, Object... args){
        super(cause);
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.args = args;
        String arg = "";
        if(this.args != null) {
            for(int i = 0; i < this.args.length; ++i) {
                if(this.args[i] != null) {
                    this.args[i] = this.args[i].toString();
                }

                arg = MessageFormat.format(",{0}", this.args);
            }
        }

        this.message = message + arg;
    }

    public BusinessException(String errorCode, String message, Throwable cause, Object... args){
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
        this.args = args;
        if(this.args != null) {
            for(int i = 0; i < this.args.length; ++i) {
                if(this.args[i] != null) {
                    this.args[i] = this.args[i].toString();
                }
            }
        }

        this.message = message;
    }


    /**
     * 构造函数
     *
     * @param errorCode - 错误消息码
     * @param args      - 错误消息参数
     */
    public BusinessException(String errorCode, Object... args) {
        this(errorCode, null, args);
    }

    /**
     * 构造函数
     *
     * @param errorCode - 错误消息码
     * @param args      - 错误消息参数
     */
    public BusinessException(String errorCode, Throwable cause, Object... args) {
        super(cause);
        this.errorCode = errorCode;
        this.args = args;

        if (this.args != null) {
            for (int i = 0; i < this.args.length; i++) {
                if (this.args[i] != null) {
                    this.args[i] = this.args[i].toString();
                }
            }
        }

        this.message = errorCode;
    }
}
