package com.wgx.study.project.枚举;

public enum  MyEnum {

    CONSTANT1("1000","常量1"),
    CONSTANT2("1000","常量2"),
    CONSTANT3("1000","常量3");

    MyEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
