package com.plus.bysj.account.service.impl;

import com.plus.bysj.account.beans.req.BillQueryReq;
import com.plus.bysj.account.beans.QuickPrincipal;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.dao.BillRep;
import com.plus.bysj.account.entity.Bill;
import com.plus.bysj.account.service.BillService;
import com.plus.bysj.account.utils.CommonUtil;
import com.plus.bysj.account.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service.impl
 * @ClassName: BillServiceImpl
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 18:47
 */
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRep billRep;

    @Override
    public BaseResult billQuery(BillQueryReq req, HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();
        Pageable pageable = PageRequest.of(req.getCurrentPage()-1,req.getPageSize(), Sort.by("createTime"));
        Specification<Bill> specification = (Specification<Bill>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为Bill中的字段，第二个userId为参数
            Predicate p1 =  criteriaBuilder.equal(root.get("userId"),userId);
            list.add(p1);
            if (!StringUtils.isEmpty(req.getType())) {
                // 此处为查询serverName中含有key的数据
                Predicate p2 = criteriaBuilder.equal(root.get("type"),req.getType() );
                list.add(p2);
            }
            if (!StringUtils.isEmpty(req.getStart())) {

                // 此处为查询serverName中含有key的数据
                Predicate p2 = criteriaBuilder.between(root.get("createTime"), CommonUtil.dateFormat(req.getStart()),CommonUtil.dateFormat(req.getEnd()));
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page page =  billRep.findAll(specification, pageable);
        return ResultUtil.success(page);
    }
}
