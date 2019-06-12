package rh.study.knowledge.common.result;

/**
 * 状态码枚举
 */
public enum ResultEnum {
    SUCCESS(0,"成功")
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
