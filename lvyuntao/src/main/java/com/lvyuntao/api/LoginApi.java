package com.lvyuntao.api;

import com.lvyuntao.model.base.BaseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "获取发布版本", httpMethod = "POST", response = BaseMessage.class)
    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = {"application/json"})
    public BaseMessage test(@ApiParam(value = "测试参数", required = true) @RequestParam Integer test){
        return BaseMessage.success(test);
    }
}
