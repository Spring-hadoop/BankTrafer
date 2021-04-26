package com.plus.bysj.account.beans.resp;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.resp
 * @ClassName: LoginResp
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 9:24
 */
public class LoginResp {
    @ApiModelProperty(value = "TOKEN",name = "token",example = "")
    private String token;
    @ApiModelProperty(value = "token生效时间(秒)",name = "expiresIn",example = "")
    private Long expiresIn;
}
