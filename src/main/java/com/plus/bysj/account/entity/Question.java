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
 * @ClassName: Question
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 13:20
 */
@Data
@Entity
@Table( name = "tb_question")
public class Question extends BaseEntity{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String transactionId;
    private String question;
    private String answer;
    private String errorNum;

}
