package site.aboy.album.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.aboy.album.config.OpenConfig;
import site.aboy.album.service.LoginService;
import site.aboy.album.service.ShareService;


@RestController
@Api(value = "登录相关操作", description = "登录相关的操作")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private ShareService shareService;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "支付宝登录回调地址")
    @GetMapping(path = "/alipay_login")
    public String AliLogin(@RequestParam("auth_code") String authCode, @RequestParam("state") String state) {
        logger.info("auth_code==========>" + authCode + ",state=================>" + state);
        return shareService.alipayUserInfoShareService(authCode, state);
    }

    @ApiOperation(value = "获取登录链接")
    @ResponseBody
    @GetMapping(path = "/getLoginUrl")
    public String getLoginUrl(){
        int sid = (int)(Math.random()*1000000000);
        JSONObject json = new JSONObject();
        json.put("url", OpenConfig.getReturnUrl());
        json.put("sid", sid);
        return json.toJSONString();
    }

    @ApiOperation(value = "管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "username", name = "登录用户名", type = "String", required = true),
            @ApiImplicitParam(value = "password", name = "登录口令", type = "String", required = true)
    })
    @PostMapping("/login")
    public String adminLogin(@RequestBody JSONObject json){
        return loginService.adminLoginAction(json.getString("username"), json.getString("password"));
    }
}
