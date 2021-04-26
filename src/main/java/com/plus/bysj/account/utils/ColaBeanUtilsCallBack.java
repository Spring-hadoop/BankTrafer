package com.plus.bysj.account.utils;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.utils
 * @ClassName: ColaBeanUtilsCallBack
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 11:10
 */
@FunctionalInterface
public interface ColaBeanUtilsCallBack<S, T> {

    void callBack(S t, T s);
}
