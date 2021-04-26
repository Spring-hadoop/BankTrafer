package com.plus.bysj.account.controller;

import com.plus.bysj.account.beans.req.RegReq;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.entity.AccountCard;
import com.plus.bysj.account.service.AccountCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.controller
 * @ClassName: CardController
 * @Author: rh
 * @Description: 卡种控制器
 * @Date: 2021/4/22 21:54
 */
@RestController
@RequestMapping("/card")
@Api(tags = "卡种控制器")
public class CardController {
    @Autowired
    private AccountCardService accountCardService;

    @ApiOperation(value = "卡种查询",notes = "卡种查询")
    @PostMapping("/ignore/query")
    public BaseResult<AccountCard> queryCrad(){
        return accountCardService.queryCrad();
    }
}
