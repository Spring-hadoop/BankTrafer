package com.plus.bysj.account.service;

import com.plus.bysj.account.beans.req.ConfirmReq;
import com.plus.bysj.account.beans.req.LoginReq;
import com.plus.bysj.account.beans.req.RegReq;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.entity.AccountUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service
 * @ClassName: UserAccountService
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 19:57
 */
public interface AccountUserService {
    BaseResult userAccountReg(RegReq req,HttpServletRequest request);

    BaseResult userAccountExamine(String cardNum,String addr, HttpServletRequest request);

    BaseResult userAccountConfirm(ConfirmReq req);

    BaseResult login(LoginReq req);

    AccountUser getAccountUser(String id);
}
