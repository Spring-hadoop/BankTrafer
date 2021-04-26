package com.plus.bysj.account.controller;

import com.plus.bysj.account.beans.req.BillQueryReq;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.service.BillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.controller
 * @ClassName: BillController
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 18:46
 */
@RestController
@RequestMapping("/bill")
@Api(tags = "账单控制器")
public class BillController {
    @Autowired
    private BillService billService;

    @ApiOperation(value = "账单查询",notes = "账单查询")
    @PostMapping("/billQuery")
    public BaseResult billQuery(@RequestBody BillQueryReq req, HttpServletRequest request){
        return billService.billQuery(req,request);
    }
}
