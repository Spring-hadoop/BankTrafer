package com.plus.bysj.account.service.impl;

import com.plus.bysj.account.beans.TokenInfo;
import com.plus.bysj.account.beans.TokenResultVO;
import com.plus.bysj.account.beans.req.ConfirmReq;
import com.plus.bysj.account.beans.req.LoginReq;
import com.plus.bysj.account.beans.req.RegReq;
import com.plus.bysj.account.beans.resp.RegResp;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.dao.AccountCardRep;
import com.plus.bysj.account.dao.AccountRep;
import com.plus.bysj.account.dao.AccountUserRep;
import com.plus.bysj.account.dao.DictRep;
import com.plus.bysj.account.entity.Account;
import com.plus.bysj.account.entity.AccountCard;
import com.plus.bysj.account.entity.AccountUser;
import com.plus.bysj.account.entity.Dict;
import com.plus.bysj.account.quartz.QuartzManager;
import com.plus.bysj.account.quartz.UserAccountExamineJob;
import com.plus.bysj.account.service.AccountUserService;
import com.plus.bysj.account.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.service.impl
 * @ClassName: UserAccountServiceImpl
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 19:57
 */
@Service
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    private AccountUserRep accountUserRep;
    @Autowired
    private AccountCardRep accountCardRep;
    @Autowired
    private AccountRep accountRep;
    @Autowired
    private DictRep dictRep;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private QuartzManager quartzManager;

    @Value("${common.token-exp}")
    private Long tokenExp;

    private static final String ACCOUNT_TYPE = "account_type";

    @Override
    @Transactional
    public BaseResult userAccountReg(RegReq req,HttpServletRequest request) {
        if(checkEmail(req.getEmail())){
            return ResultUtil.fail("邮箱已使用");
        }
        if(checkPhone(req.getPhone())){
            return ResultUtil.fail("手机号已使用");
        }
        if(checkCardType(req.getCardType())){
            return ResultUtil.fail("开卡类型不正确");
        }
        AccountUser user = new AccountUser();
        BeanUtils.copyProperties(req,user);
        user.setStatus("1");
        user.setCardNum(createCardNum(req.getPhone()));
        user.setCreateTime(new Date());
        user.setUpdateTime(user.getCreateTime());
        accountUserRep.save(user);
        RegResp resp = new RegResp();
        resp.setCardNum(user.getCardNum());
        Map map = new HashMap();
        map.put("cardNum",user.getCardNum());
        map.put("addr",IpUtil.getIp(request));
        quartzManager.addJob(user.getCardNum(),user.getCardNum(), UserAccountExamineJob.class,5*1000L,map);
        return ResultUtil.success(resp);
    }

    //系统审核
    @Override
    public BaseResult userAccountExamine(String cardNum,String addr, HttpServletRequest request) {
        AccountUser user = new AccountUser();
        user.setCardNum(cardNum);
        user.setStatus("1");
        Optional<AccountUser> accountUser = accountUserRep.findOne(Example.of(user));
        if(accountUser.isPresent()){
            user = accountUser.get();
            String sign = CommonUtil.Md5(user.getId()+user.getCardNum().substring(10));
            if(request!=null){
                addr = IpUtil.getIp(request);
            }
            if(StringUtils.isEmpty(addr)){
                addr = IpUtil.getIp(request);
            }
            String url = "http://"+ addr +"/account/userAccount" +
                    "/ignore/userAccountConfirm?id="+user.getId()+"&sign="+sign;
            try {
                MailUtils.sendMail(user.getEmail(),"卡号激活确认",
                        "你的卡号为"+user.getCardNum()+",确认激活请点击<a href='"+url+"'>"+url+"</a>");
                return ResultUtil.success();
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResultUtil.fail("发送失败");
            }
        }else {
            return ResultUtil.fail("不存在或已被激活");
        }

    }

    @Override
    @Transactional
    public BaseResult userAccountConfirm(ConfirmReq req) {
        Optional<AccountUser> userOptional  = accountUserRep.findById(req.getId());
        if(userOptional.isPresent()){
            AccountUser user = userOptional.get();
            if("2".equals(user.getStatus())){
                return ResultUtil.fail("已激活确认");
            }
            String sign = CommonUtil.Md5(user.getId()+user.getCardNum().substring(10));
            if(req.getSign().equals(sign)) {
                //修改状态
                user.setStatus("2");
                user.setUpdateTime(new Date());
                accountUserRep.save(user);
                createAccountType(user);
                return ResultUtil.success("激活确认成功");
            }else {
                return ResultUtil.fail("sign值不正确");
            }
        }else {
            return ResultUtil.fail("激活确认失败");
        }

    }

    @Override
    public BaseResult login(LoginReq req) {
        AccountUser user = accountUserRep.findByPhoneOrCardNum(req.getPhone(),req.getCardNum());
        if(user == null){
            return ResultUtil.fail("卡号或密码错误");
        }else {
            if(!"2".equals(user.getStatus())){
                return ResultUtil.fail("未激活");
            }
            if(user.getPwd().equals(req.getPwd())){
                TokenInfo info = new TokenInfo();
                info.setUserId(user.getId());
                info.setTime(System.currentTimeMillis());
                TokenResultVO tokenResultVO = new TokenResultVO();
                String token = TokenUtil.getToken(info);
                tokenResultVO.setToken(token);
                tokenResultVO.setExpiresIn(tokenExp);
                redisUtil.set(user.getId(),token,tokenExp);
                return ResultUtil.success(tokenResultVO);
            }else {
                return ResultUtil.fail("卡号或密码错误");
            }
        }

    }

    private boolean createAccountType(AccountUser user){
        //
        Dict dict = new Dict();
        dict.setMarkCode(ACCOUNT_TYPE);
        dict.setStatus("0");
        List<Dict> dicts = dictRep.findAll(Example.of(dict));

        if(dicts!=null&&dicts.size()>0) {
            for (Dict dict1: dicts) {
                //设置账户余额
                Account account = new Account();
                //初始金额
                account.setBalance(1000.00);
                account.setCardNum(user.getCardNum());
                account.setUserId(user.getId());
                account.setStatus("0");
                account.setFrozenMoney(0.0);
                account.setCreateTime(new Date());
                account.setUpdateTime(account.getCreateTime());
                account.setType(dict1.getTypeCode());
                if("2".equals(dict1.getTypeCode())){
                    //判断是否是前一万名
                    if (check()) {
                        account.setBalance(account.getBalance()+1000.00);
                    }
                }
                accountRep.save(account);
            }

        }
        return true;
    }
    //判断是否是前一万名
    private Boolean check(){
        AccountUser user = new AccountUser();
        user.setStatus("2");
        return  accountUserRep.count(Example.of(user))<=10000?true:false;
    }

    //生成卡号 19位
    private String createCardNum(String phone){
        String time = CommonUtil.dateFormat(new Date());
        Random rand = new Random();
        String cardNum = ""+(rand.nextInt(5)+5)+time+(rand.nextInt(900)+ 100)+phone.substring(9);
        return cardNum;
    }
    //生成卡号 19位
    private String create(String email,String phone){
        String cardNum = AESUtil.aesEncrypt(email+phone);
        return null;
    }
    //判断
    private Boolean checkCardType(String cardType){
        AccountCard card = new AccountCard();
        card.setCardType(cardType);
        return accountCardRep.count(Example.of(card))>0?false:true;
    }
    //判断
    private Boolean checkEmail(String email){
        AccountUser user = new AccountUser();
        user.setEmail(email);
        return accountUserRep.count(Example.of(user))>0?true:false;
    }
    //判断手机号是否使用
    private Boolean checkPhone(String phone){
        AccountUser user = new AccountUser();
        user.setEmail(phone);
        return accountUserRep.count(Example.of(user))>0?true:false;
    }

    @Override
    public AccountUser getAccountUser(String id){
        Optional<AccountUser> accountUser= accountUserRep.findById(id);
        if(accountUser.isPresent()){
            return accountUser.get();
        }else {
            return null;
        }
    }
}
