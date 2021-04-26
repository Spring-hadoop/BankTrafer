package com.plus.bysj.account.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.entity
 * @ClassName: AccountCard
 * @Author: rh
 * @Description:
 * @Date: 2021/4/22 22:34
 */
@Data
@Entity
@Table( name = "tb_account_card")
public class AccountCard extends BaseEntity{
    @Id
    private Integer id;

    private String cardType;

    private String cardName;

    @ApiModelProperty(value = "费用")
    private String cost;
    @ApiModelProperty(value = "免费转账次数")
    private String freeNum;

    private Integer seq;

    private String status;

    private String  timeType;

    private String  timeName;

    private String rate;

}
