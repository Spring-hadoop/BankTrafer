package com.plus.bysj.account.beans.resp;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.resp
 * @ClassName: TransactionResp
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 14:47
 */
@Data
public class TransactionResp {
    @NotBlank
    private String transactionId;
    private String transactionCardNum;
    private Double money;
    private Double serviceFee;
    private String name;
}
