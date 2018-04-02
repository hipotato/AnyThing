package org.potato.AnyThing.phoenix.config.handler;

import org.potato.AnyThing.phoenix.dto.resp.BaseResp;
import org.potato.AnyThing.phoenix.exception.IException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by potato on 2018/4/2.
 */
@RestControllerAdvice
public class IExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(IExceptionHandler.class);

    /**
     * 通用Exception异常
     * @return
     * @author
     */
    @ExceptionHandler(value = Exception.class)
    public BaseResp exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        logger.error("系统级异常", ex);
        autoRecordRequest(request);
        return BaseResp.error();
    }

    /**
     * 自定义异常
     * @return
     * @author potato
     */
    @ExceptionHandler(value = IException.class)
    public BaseResp exceptionHandler(HttpServletRequest request, HttpServletResponse response, IException ex) {
        logger.warn("业务级异常", ex);
        autoRecordRequest(request);
        return BaseResp.error(ex.getCode(), ex.getErrorMsg());
    }

    /**
     * 验证异常
     * @return
     * @author
     */
    @ExceptionHandler(value = BindException.class)
    public BaseResp exceptionHandler(HttpServletRequest request, HttpServletResponse response, BindException ex) {
        logger.warn("数据绑定异常", ex);

        StringBuffer sb = new StringBuffer();
        List<FieldError> errors = ex.getFieldErrors();
        int count = errors.size();
        for (int i = 0; i < errors.size(); i++) {
            FieldError error = errors.get(i);
            sb.append(error.getDefaultMessage());   // 异常信息
            if (i < count - 1) {
                sb.append("#");
            }
        }
        autoRecordRequest(request);

        // 对于CheckPayKey单独处理
        if (count == 0){
            List<ObjectError> objectErrors = ex.getAllErrors();
            for (ObjectError objectError: objectErrors){
                sb.append(objectError.getDefaultMessage());
            }
        }
        return BaseResp.error(510, sb.toString());
    }

    /**
     * 自动记录请求信息
     * @param request
     */
    private void autoRecordRequest(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getRequestURL().toString());
        Enumeration<String> names = request.getParameterNames();
        boolean flag = true;
        while (names.hasMoreElements()) {
            sb.append(flag ? "?" : "&");
            flag = false;
            String name = names.nextElement();
            sb.append(name);
            sb.append("=");
            sb.append(request.getParameter(name));
        }
        logger.error(sb.toString());
    }
}
