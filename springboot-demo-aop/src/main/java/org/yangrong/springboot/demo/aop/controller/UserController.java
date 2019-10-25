package org.yangrong.springboot.demo.aop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yangrong.springboot.demo.aop.service.UserService;

import javax.annotation.Resource;

/**
 * @Description: 用户controller
 * @Author: yangrong
 * @CreateDate: 2019/10/25 13:54
 * @Version: 2.0
 */
@RestController
public class UserController {
    @Resource
    UserService userService;

    @ResponseBody
    @RequestMapping("/findUserNameByTel")
    public String findUserNameByTel(@RequestParam("tel") String tel) {
        return userService.findUserName(tel);
    }
}
