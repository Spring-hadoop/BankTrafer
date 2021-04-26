package com.plus.bysj.account.service.impl;

import com.plus.bysj.account.beans.QuickPrincipal;
import com.plus.bysj.account.beans.resp.AccountQueryResp;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.dao.AccountCardRep;
import com.plus.bysj.account.dao.AccountRep;
import com.plus.bysj.account.entity.Account;
import com.plus.bysj.account.service.AccountService;
import com.plus.bysj.account.utils.ColaBeanUtils;
import com.plus.bysj.account.utils.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service.impl
 * @ClassName: AccountServiceImpl
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 10:29
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRep accountRep;

    @Override
    public BaseResult accountQuery(HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();
        Account account = new Account();
        account.setUserId(userId);
        List<Account> list =  accountRep.findAll(Example.of(account));
        List<AccountQueryResp>  accountQueryResps = ColaBeanUtils.copyListProperties(list,AccountQueryResp::new);
        return ResultUtil.success(accountQueryResps);
    }

    @Override
    public Account getAccount(String userId, String accountType) {
        Account account = new Account();
        account.setUserId(userId);
        account.setType(accountType);
        Optional<Account> list =  accountRep.findOne(Example.of(account));
        if(list.isPresent()){
            return list.get();
        }else {
            return null;
        }

    }

}
