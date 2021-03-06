package com.plus.bysj.account.service.impl;

import com.plus.bysj.account.beans.QuickPrincipal;
import com.plus.bysj.account.beans.req.*;
import com.plus.bysj.account.beans.resp.TransactionResp;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.dao.*;
import com.plus.bysj.account.entity.*;
import com.plus.bysj.account.exce.ServiceException;
import com.plus.bysj.account.service.AccountService;
import com.plus.bysj.account.service.TransactionService;
import com.plus.bysj.account.utils.CommonUtil;
import com.plus.bysj.account.utils.MailUtils;
import com.plus.bysj.account.utils.RedisUtil;
import com.plus.bysj.account.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service.impl
 * @ClassName: TransactionServiceImpl
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 13:30
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRep transactionRep;
    @Autowired
    private AccountService accountService;
    @Autowired
    private QuestionRep questionRep;
    @Autowired
    private AccountCardRep accountCardRep;
    @Autowired
    private AccountUserRep accountUserRep;
    @Autowired
    private AccountRep accountRep;
    @Autowired
    private BillRep billRep;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResult transactionQuery(QueryReq req, HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        if(!StringUtils.isEmpty(req.getId())) {
            transaction.setId(req.getId());
        }
        List<Transaction> list = transactionRep.findAll(Example.of(transaction));
        return ResultUtil.success(list);
    }

    @Override
    @Transactional
    public BaseResult transaction(TransactionReq req, HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();

        AccountUser user = check(req.getTransactionCardNum());
        if(user==null){
            return ResultUtil.fail("????????????????????????");
        }
        Account account = accountService.getAccount(userId,req.getAccountType());
        if(account!=null){
            Double money = account.getBalance();
            if(money>req.getMoney()) {
                //??????
                //if ("2".equals(req.getTransactionType())) {
                    //????????????
                    Transaction transaction = new Transaction();
                    BeanUtils.copyProperties(req, transaction);
                    transaction.setCardNum(account.getCardNum());
                    transaction.setStatus("0");
                    Random rand = new Random();
                    String id = CommonUtil.dateFormat(new Date())+(rand.nextInt(9000)+ 1000);
                    transaction.setId(id);
                    transaction.setCreateTime(new Date());
                    transaction.setUpdateTime(transaction.getCreateTime());
                    transaction.setCreateBy(userId);
                    transaction.setUserId(userId);
                   //???????????????
                    Double serviceFee = getServiceFee(transaction.getCardNum(),userId,req.getMoney());
                    transaction.setServiceFee(serviceFee);
                    if(money<(req.getMoney()+serviceFee)){
                        return ResultUtil.fail("????????????");
                    }
                    transaction.setTime(CommonUtil.dateFormat(req.getTime()));
                    if("1".equals(req.getTimeType())){
                        transaction.setTime(new Date());
                    }
                    Question question = new Question();
                    question.setQuestion(req.getQuestion());
                    question.setTransactionId(id);
                    question.setAnswer(req.getAnswer());
                    question.setCreateTime(transaction.getCreateTime());
                    question.setUpdateTime(transaction.getCreateTime());
                    question.setCreateBy(userId);
                    transactionRep.save(transaction);
                    questionRep.save(question);
                    TransactionResp resp = new TransactionResp();
                    resp.setTransactionId(id);
                    resp.setTransactionCardNum(req.getTransactionCardNum());
                    resp.setMoney(req.getMoney()+serviceFee);
                    resp.setServiceFee(serviceFee);
                    resp.setName(user.getName());
                    //??????
                    return ResultUtil.success(resp);
               // }
            }else {
                return ResultUtil.fail("????????????");
            }
        }else {
            return ResultUtil.fail("?????????????????????");
        }
    }
   //??????
    @Override
    @Transactional
    public BaseResult transactionTwo(TransactionResp req, HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();
        Optional<Transaction> transactionOptional = transactionRep.findById(req.getTransactionId());
        if(transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            if(!transaction.getStatus().equals("0")){
                return ResultUtil.fail("?????????????????????");
            }
            //????????????
            String cardNum = transaction.getCardNum();
            String AccoungtType = transaction.getAccountType();
            Account account = getAccount(cardNum,AccoungtType);
            if(account==null){
                return ResultUtil.fail();
            }
            account.setBalance(account.getBalance()-transaction.getMoney()-transaction.getServiceFee());
            account.setFrozenMoney(account.getFrozenMoney()+transaction.getMoney()+transaction.getServiceFee());
            accountRep.save(account);
            //??????????????????
            transaction.setStatus("2");
            transaction.setMark("?????????");
            transactionRep.save(transaction);
            //???????????????
            Bill bill = new Bill();
            bill.setTransactionId(transaction.getId());
            bill.setStatus(transaction.getStatus());
            bill.setMark(transaction.getMark());
            bill.setCreateTime(new Date());
            bill.setUpdateTime(bill.getCreateTime());
            bill.setType("1");
            bill.setMoney(transaction.getMoney()+transaction.getServiceFee());
            bill.setUserId(userId);
            bill.setAccountType(transaction.getAccountType());
            billRep.save(bill);
            //????????????
            // ???????????????
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendMail(transaction.getCardNum(),transaction.getTransactionCardNum(),transaction.getMoney(),transaction.getId());
                }
            }).start();
            return ResultUtil.success();
        }else {
            return ResultUtil.fail("?????????????????????");
        }

    }

    @Override
    public BaseResult transactionInQuery(PageReq req, HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();
        Pageable pageable = PageRequest.of(req.getCurrentPage()-1,req.getPageSize(), Sort.by("createTime"));
        AccountUser user = get(userId);
        String cardNum = user.getCardNum();
//        Transaction transaction = new Transaction();
//        transaction.setTransactionCardNum(cardNum);
//        Page page = transactionRep.findAll(Example.of(transaction),pageable);
        Specification<Transaction> specification = (Specification<Transaction>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // ?????????userId???Bill????????????????????????userId?????????
            Predicate p1 = criteriaBuilder.equal(root.get("transactionCardNum"), cardNum);
            list.add(p1);

            Predicate p2 = criteriaBuilder.lessThanOrEqualTo(root.get("time").as(Date.class), new Date());
            list.add(p2);

            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page page =  transactionRep.findAll(specification, pageable);
        return ResultUtil.success(page);
    }

    //????????????
    @Override
    public BaseResult transactionConfirm(TransactionConfirmReq req, HttpServletRequest request) {
        QuickPrincipal quickPrincipal = (QuickPrincipal)request.getUserPrincipal();
        String userId = quickPrincipal.getUserid();
        String transactionId = req.getTransactionId();
        //???????????????
        Optional<Transaction> transactionOptional  = transactionRep.findById(transactionId);
        if(transactionOptional.isPresent()){
            Transaction transaction = new Transaction();
            //?????????????????????
            if("2".equals(transaction.getStatus())){
                return null;
            }else if("1".equals(transaction.getStatus())){
                return ResultUtil.fail("?????????????????????");
            }else {
                return ResultUtil.fail("????????????????????????");
            }
        }else {
            return ResultUtil.fail("??????????????????");
        }

    }

    //????????????
    @Override
    public BaseResult transactionCancel(TransactionCancelReq req, HttpServletRequest request) {
        //???????????????
        Optional<Transaction> transactionOptional  = transactionRep.findById(req.getTransactionId());
        if(transactionOptional.isPresent()){
            Transaction transaction = new Transaction();
            //?????????????????????
            if("2".equals(transaction.getStatus())){
                transaction.setStatus("1");
                transaction.setMark("??????");
                transactionRep.save(transaction);
                //?????????????????????????????????

                return ResultUtil.success("????????????");
            }else if("1".equals(transaction.getStatus())){
                return ResultUtil.fail("?????????????????????");
            }else {
                return ResultUtil.fail("????????????????????????");
            }
        }else {
            return ResultUtil.fail("??????????????????");
        }
    }


    private void sendMail(String cardNum,String transactionCardNum,Double money,String transactionId)  {
        AccountUser user = check(cardNum);
        AccountUser user1 = check(transactionCardNum);
        if(user!=null&&user1!=null){
            Question anw = questionRep.findByTransactionId(transactionId);
            String content = user.getName()+"("+user.getCardNum()+")????????????"+money+"??????,???????????????????????????????????????????????????"+anw.getAnswer();
            try {
                MailUtils.sendMail(user1.getEmail(),"????????????",content);
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new ServiceException(e.getMessage());
            }
        }
    }


    public Account getAccount(String cradNum, String accountType) {
        Account account = new Account();
        account.setCardNum(cradNum);
        account.setType(accountType);
        Optional<Account> list =  accountRep.findOne(Example.of(account));
        if(list.isPresent()){
            return list.get();
        }else {
            return null;
        }

    }

    private AccountUser get(String userId){
        return accountUserRep.findById(userId).get();
    }


    //?????????????????????????????????
    private AccountUser check(String transactionCardNum){
        AccountUser user = new AccountUser();
        user.setCardNum(transactionCardNum);
        Optional<AccountUser> accountUserOptional =accountUserRep.findOne(Example.of(user));
        if(accountUserOptional.isPresent()){
            return accountUserOptional.get();
        }else {
            return null;
        }
    }

    //?????????
    private Double getServiceFee(String cardNum, String userId, Double money){
        Transaction transaction = new Transaction();
        transaction.setCardNum(cardNum);
        Long num = transactionRep.count(Example.of(transaction));
        AccountCard accountCard = accountCardRep.findAccountCard(cardNum,userId);
        if(num<=Long.parseLong(accountCard.getFreeNum())){
            return 0.0;
        }else{
            String rate = accountCard.getRate();
            Double rat = 0.0;
            if(rate.contains("%")){
                rat = Double.parseDouble(rate.replace("%",""))*0.01;
            }else {
                rat = Double.parseDouble(rate);
            }
            return money*rat;
        }

    }
}
