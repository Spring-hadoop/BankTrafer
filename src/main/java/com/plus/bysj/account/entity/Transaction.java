package com.plus.bysj.account.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.entity
 * @ClassName: Transaction
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 12:52
 */
@Data
@Entity
@Table( name = "tb_transaction")
public class Transaction extends  BaseEntity{
    @Id
    private String id;
    private String userId;
    private String cardNum;
    private String transactionType;
    private String accountType;
    private Double money;
    private String transactionCardNum;
    private String transactionAccountType;
    private Double serviceFee;
    private String status;
    private String mark;
    private String timeType;//1即刻转账 2未来X个时间
    private Date time;

}
