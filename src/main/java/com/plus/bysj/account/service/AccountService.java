package com.plus.bysj.account.service;

import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.entity.Account;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service
 * @ClassName: AccountService
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 10:29
 */
public interface AccountService {
    BaseResult accountQuery(HttpServletRequest request);

    Account getAccount(String userId, String accountType);
}
