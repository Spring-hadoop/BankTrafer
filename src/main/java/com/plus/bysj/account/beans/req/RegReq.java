package com.plus.bysj.account.beans.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans
 * @ClassName: RegReq
 * @Author: rh
 * @Description: 开户注册
 * @Date: 2021/4/22 21:56
 */
@Data
public class RegReq {
    @NotBlank
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱")
    private String email;
    @NotBlank
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "出生日期")
    private String birthDay;
    @NotBlank
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;
    @NotBlank
    @ApiModelProperty(value = "密码")
    private String pwd;
    @NotBlank
    @ApiModelProperty(value = "卡种")
    private String cardType;
}
