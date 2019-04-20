package com.lvyuntao.api;

import com.lvyuntao.Parmsexception;
import com.lvyuntao.model.base.BaseMessage;
import com.lvyuntao.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SF on 2019/3/24.
 */
@Api(tags="TEST")
@RequestMapping("/Login")
@RestController
public class LoginApi {
    @Autowired
    LoginService loginService;


    @ApiOperation(value = "测试接口", httpMethod = "POST", response = BaseMessage.class)
    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = {"application/json"})
    public BaseMessage test(@ApiParam(value = "测试参数", required = true) @RequestParam Integer test){
        return BaseMessage.success(test);
    }

    @ApiOperation(value = "测试接口", httpMethod = "POST", response = BaseMessage.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json"})
    public BaseMessage login(
            @ApiParam(value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(value = "用户id", required = true) @RequestParam String passWord){
        try {
            return BaseMessage.success(loginService.adminLogin(userId,passWord));
        } catch (Parmsexception e) {
            return BaseMessage.error(400,e.getMessage());
        }
    }
}
