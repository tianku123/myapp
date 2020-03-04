package rh.study.knowledge.common.result;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        System.out.println( getErrorMessage(e));
        return Result.failure(500, getErrorMessage(e));
    }

    public static String getErrorMessage(Exception e) {
        if (null == e) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 拦截Exception类的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result exceptionHandler(ServiceException e) {
        return Result.failure(e.getCode(), e.getMessage());
    }

}
