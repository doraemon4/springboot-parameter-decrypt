package com.stephen.learning.controller;

import com.stephen.learning.annonation.DecryptBody;
import com.stephen.learning.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jack
 * @description
 * @date 2020/5/10 19:23
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    /**
     * 模拟方法注解
     * @param user
     * @return
     */
    @DecryptBody
    @PostMapping("/user")
    public String decryptRequest(@RequestBody User user){
        return user.toString();
    }


    /**
     * 模拟参数注解
     * @param user
     * @return
     */
    @PostMapping("/user2")
    public String decryptRequest2(@DecryptBody @RequestBody User user){
        return user.toString();
    }
}
