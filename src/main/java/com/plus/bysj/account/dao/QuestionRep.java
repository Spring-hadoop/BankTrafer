package com.plus.bysj.account.dao;

import com.plus.bysj.account.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.dao
 * @ClassName: QuestionRep
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 15:01
 */
public interface QuestionRep extends JpaRepository<Question,String> {
    Question findByTransactionId(String transactionId);
}
