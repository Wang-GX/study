package com.wgx.study.project.Jwt;

import com.wgx.study.project.common.handler.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(value = "登录",tags = "登录")
@RestController
@RequestMapping("/jwt")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        return loginService.login(username, password, response);
    }
}
