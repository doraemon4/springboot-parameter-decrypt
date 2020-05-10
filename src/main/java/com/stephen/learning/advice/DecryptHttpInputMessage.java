package com.stephen.learning.advice;

import com.alibaba.fastjson.JSON;
import com.stephen.learning.config.EncryptBodyConfig;
import com.stephen.learning.constant.DecryptType;
import com.stephen.learning.exception.DecryptBodyFailException;
import com.stephen.learning.util.AESUtil;
import com.stephen.learning.util.DESUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jack
 * @description
 * @date 2020/5/10 20:18
 */
public class DecryptHttpInputMessage implements HttpInputMessage {
    private static final Logger log = LoggerFactory.getLogger(DecryptHttpInputMessage.class);
    private HttpHeaders headers;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, DecryptType decryptType, EncryptBodyConfig config) throws IOException {
        this.headers = inputMessage.getHeaders();
        String body;
        try {
            body = IOUtils.toString(inputMessage.getBody(),config.getEncoding());
        }catch (Exception e){
            throw new DecryptBodyFailException("Unable to get request body data," +
                    " please check if the sending data body or request method is in compliance with the specification." +
                    " (无法获取请求正文数据，请检查发送数据体或请求方法是否符合规范。)");
        }
        if(StringUtils.isBlank(body)){
            throw new DecryptBodyFailException("The request body is NULL or an empty string, so the decryption failed." +
                    " (请求正文为NULL或为空字符串，因此解密失败。)");
        }
        body = JSON.parseObject(body).getString("value");

        if(decryptType == DecryptType.AES){
            body = AESUtil.decryptCBC(body, config.getAesKey(), config.getIv());
        }else{
            body = DESUtil.decryptCBC(body, config.getDesKey(), config.getIv());
        }

        if (StringUtils.isEmpty(body)) {
            throw new DecryptBodyFailException("Decryption error, " +
                    "please check if the selected source data is encrypted correctly." +
                    " (解密错误，请检查选择的源数据的加密方式是否正确。)");
        } else {
                this.body = IOUtils.toInputStream(body, "UTF-8");
        }
    }

    @Override
    public InputStream getBody() throws IOException {
        return this.body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }
}
