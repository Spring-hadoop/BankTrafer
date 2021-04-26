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
 * @ClassName: AccountUser
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 20:02
 */
@Data
@Entity
@Table(name = "tb_account_user")
public class AccountUser extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String email;
    private String name;
    private String phone;
    private String pwd;
    private String birthDay;
    private String cardType;
    private String cardNum;
    private String status;
}
