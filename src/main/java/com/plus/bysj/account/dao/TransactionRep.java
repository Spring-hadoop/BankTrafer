package com.plus.bysj.account.dao;

import com.plus.bysj.account.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.dao
 * @ClassName: TransactionRep
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 12:56
 */
@Repository
public interface TransactionRep extends JpaRepository<Transaction,String> {
    Page findAll(Specification<Transaction> specification, Pageable pageable);
}
