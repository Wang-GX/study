package com.wgx.study.project.Jwt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@Slf4j
@ConfigurationProperties(prefix = "study.jwt")
public class JwtProperties implements InitializingBean {

    //通过读取配置文件中的指定前缀完成赋值
    private String priKeyPath;
    private String pubKeyPath;
    private Integer expire;

    //通过调用afterPropertiesSet方法完成赋值
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * 这个方法会在属性赋值完成之后执行，目的是通过私钥和公钥的存储路径加载私钥和公钥对象
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException(e);
        }
    }
}
