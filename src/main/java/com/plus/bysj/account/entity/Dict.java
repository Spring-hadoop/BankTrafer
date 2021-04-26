package com.plus.bysj.account.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.entity
 * @ClassName: Dict
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 8:52
 */
@Data
@Entity
@Table( name = "tb_dict")
public class Dict {
    @Id
    private String id;

    private String markCode;

    private String markName;

    private String typeCode;

    private String typeName;

    private String status;

    private Integer seq;

}
