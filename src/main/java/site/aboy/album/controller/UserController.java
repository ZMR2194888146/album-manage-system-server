package site.aboy.album.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.aboy.album.service.UserService;

@RestController
@Api(value = "用户相关接口", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有的用户列表")
    @GetMapping(path = "/users")
    public String getAllUser() {
        return userService.getAllUserList();
    }

    @ApiOperation(value = "获取所有已经激活的用户列表")
    @GetMapping(path = "/users/active")
    public String getAllActiveUser() {
        return userService.getAllActiveUser();
    }

    @ApiOperation(value = "绑定用户信息或者称为用户信息认证")
    @PostMapping(value = "/bind")
    public String bindUserInfo(@RequestBody JSONObject json) {
        return userService.bindUserInfo(json);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping(path = "/user/{id}")
    public String modifyUserMotto(@PathVariable("id")Long uid,@RequestBody JSONObject json){
        return userService.setUserMotto(uid, json.getString("motto"));
    }
}
