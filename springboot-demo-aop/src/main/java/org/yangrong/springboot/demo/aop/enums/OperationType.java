package org.yangrong.springboot.demo.aop.enums;

/**
 * @Description: 日志操作类型
 * @Author: yangrong
 * @CreateDate: 2019/10/25 13:41
 * @Version: 2.0
 */
public enum OperationType {
    UNKNOWN("unknown"),
    DELETE("delete"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert");

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    OperationType(String type) {
        this.type = type;
    }
}
