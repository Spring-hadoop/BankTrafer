package com.plus.bysj.account.service.impl;

import com.plus.bysj.account.dao.DictRep;
import com.plus.bysj.account.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service.impl
 * @ClassName: DictServiceImpl
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 14:23
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictRep dictRep;

    private String get(String markCode,String typeCode){
       return dictRep.findByMarkCodeAndTypeCode(markCode,typeCode);
    }
}
