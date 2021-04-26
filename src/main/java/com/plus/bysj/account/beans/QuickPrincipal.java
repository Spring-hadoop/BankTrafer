package com.plus.bysj.account.beans;

import lombok.Data;

import java.security.Principal;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans
 * @ClassName: QuickPrincipal
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 10:34
 */
@Data
public class QuickPrincipal implements Principal {
    private TokenInfo userInfo;
    private String name;

    public QuickPrincipal(TokenInfo userInfo, String name) {
        this.userInfo = userInfo;
        this.name = name;
    }

    public String getUserid(){
        return userInfo.getUserId();
    }

}


