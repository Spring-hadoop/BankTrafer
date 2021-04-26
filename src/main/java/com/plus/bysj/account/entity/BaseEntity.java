package com.plus.bysj.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.entity
 * @ClassName: BaseEntity
 * @Author: rh
 * @Description:
 * @Date: 2021/4/22 23:00
 */
@Data
@MappedSuperclass//jpa 实体类继承
public class BaseEntity {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String createBy;
    private String updateBy;
}