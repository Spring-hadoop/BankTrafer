package com.plus.bysj.account.service;

import com.plus.bysj.account.beans.req.*;
import com.plus.bysj.account.beans.resp.TransactionResp;
import com.plus.bysj.account.beans.resp.commom.BaseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service
 * @ClassName: TransactionService
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 13:30
 */
public interface TransactionService {
    BaseResult transactionQuery(QueryReq id, HttpServletRequest request);

    BaseResult transaction(TransactionReq req, HttpServletRequest request);

    BaseResult transactionTwo(TransactionResp req, HttpServletRequest request);

    BaseResult transactionInQuery(PageReq req, HttpServletRequest request);

    BaseResult transactionConfirm(TransactionConfirmReq req, HttpServletRequest request);

    BaseResult transactionCancel(TransactionCancelReq req, HttpServletRequest request);
}
