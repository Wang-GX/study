package com.wgx.study.project.Jwt;

import com.wgx.study.project.common.handler.CommonException;
import com.wgx.study.project.common.handler.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class LoginService {

    @Autowired
    private JwtProperties jwtProperties;

    private static Long userId = 1L;
    private static String userRole = "guest";
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
            String jwt = JwtUtils.generateTokenExpireInMinutes(new UserInfo().setId(userId).setUsername(rightUsername).setRole(userRole), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
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
            throw new CommonException("401", "登录失败");
        }

    }

    /**
     * 查看用户信息，成功返回用户信息，失败返回错误提示
     *
     * @param request
     * @return
     */
    public Response select(HttpServletRequest request, HttpServletResponse response) {

        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        //解析token
        if (jwt != null) {
            Payload<UserInfo> info = JwtUtils.getInfoFromToken(jwt, jwtProperties.getPublicKey(), UserInfo.class);
            log.info("info.getId() = " + info.getId());
            log.info("info.getExpiration() = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info.getExpiration()));
            log.info("info.getInfo() = " + info.getInfo());
            //获取载荷中的载荷数据
            Long id = info.getInfo().getId();
            String username = info.getInfo().getUsername();
            String userRole = info.getInfo().getRole();
            //如果jwt即将过期则重新生成(jwt一旦生成就无法更改，因此我们无法修改token中的有效期，只能重新生成)
            if (System.currentTimeMillis()+ 2 * 60 * 1000L < info.getExpiration().getTime()){
                //重新生成JWT
                jwt = JwtUtils.generateTokenExpireInMinutes(new UserInfo().setId(id).setUsername(username).setRole(userRole), jwtProperties.getPrivateKey(), jwtProperties.getExpire());
                //将生成的JWT保存到Cookie中
                Cookie cookie = new Cookie("token", jwt);
                cookie.setMaxAge(jwtProperties.getExpire() * 60);//得到的是分钟,setMaxAge的单位是秒
                cookie.setPath("/");//设置cookie的有效路径为根路径，这样访问该路径的子路径下就都会携带cookie了(cookie是与域名绑定的)
                cookie.setHttpOnly(true);//JS不可操作，隐藏
                //TODO 如果不设置域名默认是当前服务的地址
                //cookie.setDomain(jwtProperties.getUser().getCookieDomain());
                response.addCookie(cookie);
            }
            return new Response().setCode(HttpStatus.OK.toString()).setMessage("获取用户信息成功").setData(new UserInfo().setId(userId).setUsername(username).setRole(userRole));
        }

        throw new CommonException("401", "获取用户信息失败");
    }
}
