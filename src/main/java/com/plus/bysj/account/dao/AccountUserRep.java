package com.plus.bysj.account.dao;

import com.plus.bysj.account.entity.AccountCard;
import com.plus.bysj.account.entity.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.dao
 * @ClassName: AccountUserReq
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 20:17
 */
@Repository
public interface AccountUserRep extends JpaRepository<AccountUser,String> {
    AccountUser findByPhoneOrCardNum(String phone, String cardNum);
}
