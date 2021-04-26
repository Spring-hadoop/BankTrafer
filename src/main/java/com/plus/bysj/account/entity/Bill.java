package com.plus.bysj.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.entity
 * @ClassName: Bill
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 17:05
 */
@Data
@Entity
@Table(name = "tb_bill")
public class Bill {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String userId;
    private String type;//1支出2收入
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private double money;
    private String mark;
    private String transactionId;
    private String status;
    private String accountType;


}
