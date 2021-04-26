package com.plus.bysj.account.service;

import com.plus.bysj.account.beans.req.BillQueryReq;
import com.plus.bysj.account.beans.resp.commom.BaseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service
 * @ClassName: BillService
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 18:46
 */
public interface BillService {
    BaseResult billQuery(BillQueryReq req, HttpServletRequest request);
}
