package org.potato.AnyThing.phoenix.exception;


public enum Exceptions {
    // ------------------------------ 基础 ------------------------------
    SMS_SEND_TOO_FAST(500000001, "该设备短信发送过于频繁！"),
    TOO_MANY_TILES(500000002, "瓦片数量过多，请缩小范围或降低层级"),
    GOOGLE_NO_IMAGE(500000003, "下载瓦片失败，谷歌服务器上未找到当前地区的高清影像数据"),
    GOOGLE_TIME_OUT(500000004, "下载瓦片失败，请求谷歌服务器超时"),
    IMAGE_MAP_ERROR(500000005, "瓦片下载或拼接错误"),
    MARKS_EMPTY(500000006,"marks不能为空！")

    ;


    private final int code; // error code
    private final String msg;   // error msg

    /**
     * 构造函数
     * @param code
     * @param msg
     */
    Exceptions(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 异常
     * @return
     */
    public IException exception() {
        return new IException(this.code, this.msg);
    }

    /**
     * Getter method for property <tt>code</tt>.
     * @return property value of code
     * @author hewei
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     * @return property value of msg
     * @author hewei
     */
    public String getMsg() {
        return msg;
    }
}
