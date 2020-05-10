package com.stephen.learning.advice;

import com.stephen.learning.annonation.DecryptBody;
import com.stephen.learning.config.EncryptBodyConfig;
import com.stephen.learning.exception.DecryptBodyFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author jack
 * @description 实现请求解密，只对post请求使用@RequestBody时生效
 * @date 2020/5/10 19:40
 */


@ControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    private EncryptBodyConfig config;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(config.getAnnotationClass())||
                methodParameter.hasParameterAnnotation(config.getAnnotationClass());
        //判断是否是类注解
        //methodParameter.getDeclaringClass().isAnnotationPresent(config.getAnnotationClass());

    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if(inputMessage.getBody()==null){
            return inputMessage;
        }
        //此处不能使用IOUtils方法,会使io流关闭
        try {
            //判断是否注解在方法上
            if(methodParameter.getMethod().isAnnotationPresent(DecryptBody.class)){
                DecryptBody decryptBody = methodParameter.getMethodAnnotation(DecryptBody.class);
                return new DecryptHttpInputMessage(inputMessage,decryptBody.value(),config);
            }
            //注解在类上
            else if(methodParameter.getParameter().isAnnotationPresent(DecryptBody.class)){
                DecryptBody decryptBody = methodParameter.getParameterAnnotation(DecryptBody.class);
                return new DecryptHttpInputMessage(inputMessage,decryptBody.value(),config);
            }
            /*
            else if(methodParameter.getDeclaringClass().isAnnotationPresent(DecryptBody.class)){
                DecryptBody decryptBody = methodParameter.getDeclaringClass().getAnnotation(DecryptBody.class);
                return new DecryptHttpInputMessage(inputMessage,decryptBody.value(),config);
            }
            */

        }catch (Exception e){
            throw new DecryptBodyFailException("The string is converted to a stream format exception." +
                    " Please check if the format such as encoding is correct." +
                    " (字符串转换成流格式异常，请检查编码等格式是否正确。)");
        }
        return super.beforeBodyRead(inputMessage, methodParameter, targetType, converterType);
    }
}
