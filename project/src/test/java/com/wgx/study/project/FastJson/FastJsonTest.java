package com.wgx.study.project.FastJson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wgx.study.project.common.pojo.User;

public class FastJsonTest {
    public static void main(String[] args) {
        User user = new User();
        user.setUserName("张三");
        user.setUserSex("1");
        user.setUserAge("18");

        //标准预期结果
        String str = JSONObject.toJSONString(user, SerializerFeature.WriteMapNullValue);
        System.out.println(str);

    }
}
