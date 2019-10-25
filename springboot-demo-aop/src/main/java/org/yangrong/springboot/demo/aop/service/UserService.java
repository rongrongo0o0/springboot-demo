package org.yangrong.springboot.demo.aop.service;
/**
 * @Description:    用户service
 * @Author:         yangrong
 * @CreateDate:     2019/10/25 13:49
 * @Version:        2.0
*/
public interface UserService {
    /**
     * 获取用户信息
     * @param tel
     * @return
     */
    String findUserName(String tel);
}
