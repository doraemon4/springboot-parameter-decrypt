package com.stephen.learning.exception;

/**
 * @author jack
 * @description 异常信息
 * @date 2020/5/10 20:05
 */
public class DecryptBodyFailException extends RuntimeException{
    public DecryptBodyFailException() {
        super("Decrypting data failed. (解密数据失败)");
    }

    public DecryptBodyFailException(String message) {
        super(message);
    }
}
