package com.wgx.study.project.Jwt;

import com.wgx.study.project.common.handler.CommonException;
import com.wgx.study.project.common.handler.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class LoginService {

    @Autowired
    private JwtProperties jwtProperties;

    private static String rightUsername = "wgx";
    private static String rightPassword = "123456";

    /**
     * 用户登录，成功返回jwt，失败返回错误提示
     *
     * @param username
     * @param password
     * @return
     */
    public Response login(String username, String password, HttpServletResponse response) {

        //模拟查询数据库正确的账号和密码
        if (rightUsername.equals(username) && rightPassword.equals(password)) {
            //生成JWT
            String jwt = JwtUtils.generateTokenExpireInMinutes(new UserInfo(1L, username, password), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            //将生成的JWT保存到Cookie中
            Cookie cookie = new Cookie("token", jwt);
            cookie.setMaxAge(jwtProperties.getExpire() * 60);//得到的是分钟,setMaxAge的单位是秒
            cookie.setPath("/");//设置cookie的有效路径为根路径，这样访问该路径的子路径下就都会携带cookie了(cookie是与域名绑定的)
            cookie.setHttpOnly(true);//JS不可操作，隐藏
            //TODO 如果不设置域名默认是当前服务的地址
            //cookie.setDomain(jwtProperties.getUser().getCookieDomain());
            response.addCookie(cookie);
            return new Response().setCode(HttpStatus.OK.toString()).setMessage("登录成功");
        } else {
            throw new CommonException("401","登录失败");
        }

    }
}
