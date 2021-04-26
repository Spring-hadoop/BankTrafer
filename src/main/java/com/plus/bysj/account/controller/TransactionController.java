package com.plus.bysj.account.controller;

import com.plus.bysj.account.beans.TokenResultVO;
import com.plus.bysj.account.beans.req.*;
import com.plus.bysj.account.beans.resp.TransactionResp;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.dao.TransactionRep;
import com.plus.bysj.account.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.controller
 * @ClassName: TransactionController
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 13:29
 */
@RestController
@RequestMapping("/transaction")
@Api(tags = "交易控制器")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "用户交易",notes = "用户交易")
    @PostMapping("/transaction")
    public BaseResult transaction(@Valid @RequestBody TransactionReq req,HttpServletRequest request){
        return transactionService.transaction(req,request);
    }

    @ApiOperation(value = "用户交易二次确认",notes = "用户交易")
    @PostMapping("/transactionTwo")
    public BaseResult transactionTwo(@Valid @RequestBody TransactionResp req, HttpServletRequest request){
        return transactionService.transactionTwo(req,request);
    }

    @ApiOperation(value = "交易明细",notes = "交易明细")
    @PostMapping("/transactionQuery")
    public BaseResult transactionQuery(@RequestBody QueryReq req, HttpServletRequest request){
        return transactionService.transactionQuery(req,request);
    }

    @ApiOperation(value = "获取收款交易",notes = "获取收款交易")
    @PostMapping("/transactionInQuery")
    public BaseResult transactionInQuery(@RequestBody PageReq req, HttpServletRequest request){
        return transactionService.transactionInQuery(req,request);
    }

    @ApiOperation(value = "确认收款交易",notes = "获取收款交易")
    @PostMapping("/transactionConfirm")
    public BaseResult transactionConfirm(@RequestBody TransactionConfirmReq req, HttpServletRequest request){
        return transactionService.transactionConfirm(req,request);
    }

    @ApiOperation(value = "取消交易",notes = "获取收款交易")
    @PostMapping("/transactionCancel")
    public BaseResult transactionCancel(@RequestBody TransactionCancelReq req, HttpServletRequest request){
        return transactionService.transactionCancel(req,request);
    }
}
