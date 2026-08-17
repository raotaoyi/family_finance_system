package com.family.finance.system.common.commonenum;

import lombok.Getter;

@Getter
public enum SystemUserEnum {
    SYSTEM_USER_BABA(1, "爸爸"),
    SYSTEM_USER_MAMA(2, "妈妈"),
    SYSTEM_USER_BABY(3, "宝宝");

    // 补充Getter供前端/业务取值
    private final String userName;

    private final Integer userCd;


    SystemUserEnum(Integer userCd, String userName) {
        this.userName = userName;
        this.userCd = userCd;
    }

    // 根据类型编码查询名称
    public static SystemUserEnum getSystemUserEnum(Integer userCd) {
        // 遍历全部枚举实例匹配编码
        for (SystemUserEnum item : SystemUserEnum.values()) {
            if (item.userCd.equals(userCd)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }

    // 根据类型编码查询名称
    public static SystemUserEnum getSystemUserEnum(String userName) {
        // 遍历全部枚举实例匹配编码
        for (SystemUserEnum item : SystemUserEnum.values()) {
            if (item.userName.equals(userName)) {
                return item;
            }
        }
        // 无匹配编码返回空，可按需改为抛异常/返回默认值
        return null;
    }
}
