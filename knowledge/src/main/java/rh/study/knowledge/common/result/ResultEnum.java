package rh.study.knowledge.common.result;

/**
 * 状态码枚举
 */
public enum ResultEnum {
    // 服务器成功返回请求的数据。
    SUCCESS(200,"成功"),
    // 服务器发生错误，请检查服务器。
    FAIL(500,"失败")
    ;
    private Integer status;
    private String message;

    ResultEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
