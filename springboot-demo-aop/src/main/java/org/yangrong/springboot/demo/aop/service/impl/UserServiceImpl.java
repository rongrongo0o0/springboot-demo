package org.yangrong.springboot.demo.aop.service.impl;

import org.springframework.stereotype.Service;
import org.yangrong.springboot.demo.aop.annotation.OperationLogDetail;
import org.yangrong.springboot.demo.aop.enums.OperationType;
import org.yangrong.springboot.demo.aop.enums.OperationUnit;
import org.yangrong.springboot.demo.aop.service.UserService;

/**
 * @Description: userservice实现类
 * @Author: yangrong
 * @CreateDate: 2019/10/25 13:51
 * @Version: 2.0
 */
@Service
public class UserServiceImpl implements UserService {
    @OperationLogDetail(detail = "通过手机号[{{tel}}]获取用户名", level = 2, operationType = OperationType.SELECT,
            operationUnit = OperationUnit.USER)
    @Override
    public String findUserName(String tel) {
        System.out.println("tel:" + tel);
        return "yangrong";
    }
}
