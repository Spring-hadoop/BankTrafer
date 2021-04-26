package com.plus.bysj.account.controller;

import com.plus.bysj.account.beans.TokenResultVO;
import com.plus.bysj.account.beans.req.ConfirmReq;
import com.plus.bysj.account.beans.req.LoginReq;
import com.plus.bysj.account.beans.req.RegReq;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.service.AccountUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.controller
 * @ClassName: UserController
 * @Author: rh
 * @Description: 用户控制器
 * @Date: 2021/4/22 20:40
 */
@RestController
@RequestMapping("/userAccount")
@Api(tags = "用户控制器")
public class AccountUserController {
    @Autowired
    private AccountUserService userAccountService;

    @ApiOperation(value = "用户开户",notes = "用户开户")
    @PostMapping("/ignore/userAccountReg")
    public BaseResult userAccountReg(@Valid @RequestBody RegReq req,HttpServletRequest request){
        return userAccountService.userAccountReg(req,request);
    }

    @ApiOperation(value = "用户审核",notes = "用户审核")
    @PostMapping("/ignore/userAccountExamine")
    public BaseResult userAccountExamine(@Valid @NotBlank @RequestBody String cardNum, String addr,HttpServletRequest request){
        return userAccountService.userAccountExamine(cardNum,addr,request);
    }

    @ApiOperation(value = "用户确认",notes = "用户确认")
    @GetMapping("/ignore/userAccountConfirm")
    public BaseResult userAccountConfirm(@Valid ConfirmReq req){
        return userAccountService.userAccountConfirm(req);
    }

    @ApiOperation(value = "用户登录",notes = "用户登录")
    @PostMapping("/ignore/login")
    public BaseResult<TokenResultVO> login(@Valid @RequestBody LoginReq req){
        return userAccountService.login(req);
    }

}
