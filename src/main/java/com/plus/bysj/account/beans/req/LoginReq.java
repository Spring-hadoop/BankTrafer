package com.plus.bysj.account.beans.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.req
 * @ClassName: LoginReq
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 9:16
 */
@Data
public class LoginReq {
    private String phone;
    @NotBlank
    private String cardNum;
    @NotBlank
    private String pwd;
}
