package com.plus.bysj.account.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans
 * @ClassName: QuickRequestWapper
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 10:35
 */
public class QuickRequestWapper extends HttpServletRequestWrapper {
    private QuickPrincipal principal;

    public QuickRequestWapper(HttpServletRequest request, TokenInfo userInfo, String name) {
        super(request);
        this.principal = new QuickPrincipal(userInfo,name);
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }
}
