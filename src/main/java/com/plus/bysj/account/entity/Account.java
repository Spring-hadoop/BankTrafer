package com.plus.bysj.account.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.entity
 * @ClassName: Account
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 8:39
 */
@Data
@Entity
@Table( name = "tb_account")
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String userId;

    private String cardNum;

    private String type;

    private Double balance;

    private Double frozenMoney;

    private String status;
}
