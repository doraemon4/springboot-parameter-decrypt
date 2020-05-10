package com.stephen.learning.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author jack
 * @description
 * @date 2020/5/10 22:46
 */
@Data
@ToString
public class User {
    private String name;
    private String sex;
    private String email;
    private int age;
}
