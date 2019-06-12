package rh.study.knowledge.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * service层自定义异常类
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {
    private Integer code;
    private String message;

    public ServiceException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ServiceException(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
