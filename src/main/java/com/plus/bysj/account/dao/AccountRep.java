package com.plus.bysj.account.dao;

import com.plus.bysj.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.dao
 * @ClassName: AccountReq
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 8:42
 */
@Repository
public interface AccountRep extends JpaRepository<Account,String> {
}
