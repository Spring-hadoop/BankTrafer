package com.plus.bysj.account.controller;

import com.plus.bysj.account.beans.req.RegReq;
import com.plus.bysj.account.beans.resp.AccountQueryResp;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.service.AccountService;
import com.plus.bysj.account.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.controller
 * @ClassName: AccountController
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 10:03
 */
@RestController
@RequestMapping("/account")
@Api(tags = "账户控制器")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "账户余额查询",notes = "账户余额查询")
    @PostMapping("/accountQuery")
    public BaseResult<List<AccountQueryResp>> accountQuery(HttpServletRequest request){
        return accountService.accountQuery(request);
    }


}
