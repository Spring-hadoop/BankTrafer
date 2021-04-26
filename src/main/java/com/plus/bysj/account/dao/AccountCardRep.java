package com.plus.bysj.account.dao;

import com.plus.bysj.account.entity.AccountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.dao
 * @ClassName: AccountCardRep
 * @Author: rh
 * @Description:
 * @Date: 2021/4/22 23:01
 */
@Repository
public interface AccountCardRep extends JpaRepository<AccountCard,Integer> {
    @Query(value = "select a.* from tb_account_card a,tb_account_user b " +
            "where a.card_type=b.card_type and b.card_num= ?1 and b.id = ?2",nativeQuery = true)
    AccountCard findAccountCard(String cardNum,String userId);
}
