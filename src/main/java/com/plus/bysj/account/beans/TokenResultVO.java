/**
 * Created by llj on 2020/2/19 14:52
 */
package com.plus.bysj.account.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *@ClassName TokenResultVO
 *@Description
 *@Author llj
 *@Date 2020/2/19 14:52
 *@Version
 **/
@Data
@ApiModel(description = "获取token结果对象")
public class TokenResultVO {
    @ApiModelProperty(value = "TOKEN",name = "token",example = "")
    private String token;
    @ApiModelProperty(value = "token有效时间(秒)",name = "expiresIn",example = "")
    private Long expiresIn;
}
