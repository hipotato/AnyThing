package org.potato.AnyThing.phoenix.exception;

/**
 * Created by potato on 2018/4/2.
 */
public class IException extends RuntimeException {
    private int code = 500;   // 异常Code
    private String errorMsg = "通用异常！";  // 异常信息

    /**
     * 构造函数
     * @param code 异常编码
     */
    public IException(int code) {
        super();
        this.code = code;
    }

    /**
     * 构造函数
     * @param errorMsg 异常信息
     */
    public IException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    /**
     * 构造函数
     * @param code 异常编码
     * @param errorMsg 异常信息
     */
    public IException(int code, String errorMsg){
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * @return property value of code
     * @author potato
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * @param code value to be assigned to property code
     * @author potato
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>errorMsg</tt>.
     * @return property value of errorMsg
     * @author potato
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Setter method for property <tt>errorMsg</tt>.
     * @param errorMsg value to be assigned to property errorMsg
     * @author potato
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
