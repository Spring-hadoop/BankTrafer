package com.plus.bysj.account.dao;

import com.plus.bysj.account.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.dao
 * @ClassName: DictReq
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 8:55
 */
@Repository
public interface DictRep extends JpaRepository<Dict,String> {
    @Query(value = "SELECT a.typeName from Dict a where a.markCode=?1 and a.typeCode=?2")
    String findByMarkCodeAndTypeCode(String markCode, String typeCode);
}
