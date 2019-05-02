package com.lvyuntao.api;

import com.lvyuntao.model.base.BaseMessage;
import com.lvyuntao.model.base.BasePage;
import com.lvyuntao.model.table.Audit;
import com.lvyuntao.service.audit.AuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Service;

/**
 * Created by SF on 2019/4/21.
 */
@Api(tags="审核接口")
@RequestMapping("/Audit")
@RestController
public class AuditApi {
    @Autowired
    AuditService auditService;
    @PostMapping(value = "/getAuditInfoList",produces = {"application/json"})
    public BaseMessage<BasePage<Audit>> getAuditInfoList(@RequestParam Integer pageSize,@RequestParam Integer pageNo,@ApiParam(required = false) @RequestParam(required = false) String keyWord){
        return BaseMessage.success(auditService.getPage(pageSize,pageNo,keyWord));
    }
}
