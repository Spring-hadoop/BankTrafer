package com.plus.bysj.account.beans.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.req
 * @ClassName: PageReq
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 22:23
 */
@Data
public class PageReq {
    @ApiModelProperty(example = "1")
    private Integer currentPage;
    @ApiModelProperty(example = "10")
    private Integer pageSize;
}
