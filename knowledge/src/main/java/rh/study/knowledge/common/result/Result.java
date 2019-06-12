package rh.study.knowledge.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据体
 */
@Data
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultEnum resultEnum, Object data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultEnum.SUCCESS, null);
    }

    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS, data);
    }

    public static Result failure(ResultEnum resultEnum) {
        return new Result(resultEnum, null);
    }

    public static Result failure(ResultEnum resultEnum, Object data) {
        return new Result(resultEnum, data);
    }

    public static Result failure(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static Result failure(Integer code, String message, Object data) {
        return new Result(code, message, data);
    }
}
