package com.plus.bysj.account.utils;

import com.alibaba.fastjson.JSON;
import com.plus.bysj.account.beans.TokenInfo;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.utils
 * @ClassName: TokenUtil
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 9:32
 */
public class TokenUtil {
    /**
     * 获取token值
     * @param tokenInfo
     * @return
     */
    public static String getToken(TokenInfo tokenInfo){
        String tokenVOStr = JSON.toJSONString(tokenInfo);
        return AESUtil.aesEncrypt(tokenVOStr);
    }

}
