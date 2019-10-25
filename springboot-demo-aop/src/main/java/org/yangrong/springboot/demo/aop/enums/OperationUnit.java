package org.yangrong.springboot.demo.aop.enums;

/**
 * @Description: 被操作对象
 * @Author: yangrong
 * @CreateDate: 2019/10/25 13:45
 * @Version: 2.0
 */
public enum OperationUnit {
    UNKNOWN("unknown"),
    USER("user"),
    EMPLOYEE("employee"),
    Redis("redis");
    private String unit;

    OperationUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
