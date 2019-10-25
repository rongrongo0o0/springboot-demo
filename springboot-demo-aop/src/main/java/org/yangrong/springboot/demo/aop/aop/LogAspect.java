package org.yangrong.springboot.demo.aop.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.yangrong.springboot.demo.aop.annotation.OperationLogDetail;
import org.yangrong.springboot.demo.aop.model.OperationLog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: 日志切面
 * @Author: yangrong
 * @CreateDate: 2019/10/25 15:34
 * @Version: 2.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.wwj.springboot.service.impl.*.*(..))")'
     */
    @Pointcut("@annotation(org.yangrong.springboot.demo.aop.annotation.OperationLogDetail)")
    public void operationLog() {

    }

    /**
     * 环绕增强，相当于MethodInterceptor
     *
     * @param joinPoint
     * @return
     */
    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res = joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                addOperationLog(joinPoint, res, time);
            } catch (Exception e) {
                log.error("LogAspect 操作失败：", e);
            }
        }
    }

    private void addOperationLog(JoinPoint joinPoint, Object res, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        operationLog.setRunTime(time);
        operationLog.setReturnValue(JSON.toJSONString(res));
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
        operationLog.setCreateTime(new Date());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        operationLog.setUserId("#{currentUserId}");
        operationLog.setUserName("#{currentUserName}");
        OperationLogDetail annotation = signature.getMethod().getAnnotation(OperationLogDetail.class);
        if (annotation != null) {
            operationLog.setLevel(annotation.level());
            operationLog.setDescribe(getDetail(((MethodSignature) joinPoint.getSignature()).getParameterNames(), joinPoint.getArgs(), annotation));
            operationLog.setOperationType(annotation.operationType().getType());
            operationLog.setOperationUnit(annotation.operationUnit().getUnit());
        }
        //TODO 这里保存日志
        System.out.println("记录日志:" + operationLog.toString());
//        operationLogService.insert(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argNames   方法参数名称数组
     * @param args       方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation) {

        Map<Object, Object> map = new HashMap<>(4);
        for (int i = 0; i < argNames.length; i++) {
            map.put(argNames[i], args[i]);
        }

        String detail = annotation.detail();
        try {
            detail = "'" + "#{currentUserName}" + "'=》" + annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
