package com.plus.bysj.account.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans
 * @ClassName: TokenInfo
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 9:28
 */
@Data
public class TokenInfo {
    private String userId;
    private Long time;
}
