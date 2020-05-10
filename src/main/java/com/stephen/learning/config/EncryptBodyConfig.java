package com.stephen.learning.config;

import com.stephen.learning.annonation.DecryptBody;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

/**
 * @author jack
 * @description  加解密的key配置
 * @date 2020/5/10 19:44
 */
@ConfigurationProperties(prefix = "encrypt.secret")
@Configuration
@Data
public class EncryptBodyConfig {
    private String aesKey;

    private String desKey;

    private String iv;

    private String encoding = "UTF-8";

    private Class<? extends Annotation> annotationClass = DecryptBody.class;
}
