package com.plus.bysj.account.beans.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.req
 * @ClassName: Transaction
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 13:28
 */
@Data
public class TransactionReq {
//    @ApiModelProperty("发起者账号")
//    private String cardNum;
    @NotBlank
    @ApiModelProperty("方式（1转账，2发票）")
    private String transactionType;
    @NotBlank
    @ApiModelProperty("发起者账户形式（1支票，2储蓄）")
    private String accountType;
    @DecimalMin(value = "0.01") // 最小值0.01元
    private Double money;
    @NotBlank
    @ApiModelProperty("收款者账户")
    private String transactionCardNum;
    @NotBlank
    @ApiModelProperty("收款者账户形式（1支票，2储蓄3信用卡）")
    private String transactionAccountType;
    @NotBlank
    @ApiModelProperty("问题")
    private String question;
    @NotBlank
    @ApiModelProperty("答案")
    private String answer;
    @NotBlank
    @ApiModelProperty("1即刻转账 2未来X个时间")
    private String timeType;//1即刻转账 2未来X个时间
    private String time;
}
