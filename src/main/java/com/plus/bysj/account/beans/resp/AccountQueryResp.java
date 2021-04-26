package com.plus.bysj.account.beans.resp;

import lombok.Data;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans.resp
 * @ClassName: AccountQueryResp
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 10:57
 */
@Data
public class AccountQueryResp {
    private String type;

    private Double balance;

    private Double frozenMoney;
}
