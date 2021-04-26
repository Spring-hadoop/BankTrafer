package com.plus.bysj.account.beans.resp.commom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.resp.commom
 * @ClassName: StateResp
 * @Author: rh
 * @Description:
 * @Date: 2021/4/22 22:02
 */
@Data
@ApiModel(value="基础返回类",description="基础返回类" )
public class BaseResult<T> {
    @ApiModelProperty(value = "T 成功 F失败")
    private String code;
    @ApiModelProperty(value = "消息")
    private String msg;
    @ApiModelProperty(value = "数据")
    private T data;

    public BaseResult() {

    }

    public BaseResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
