package com.plus.bysj.account.utils;

import com.plus.bysj.account.beans.resp.commom.BaseConstant;
import com.plus.bysj.account.beans.resp.commom.BaseResult;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.utils
 * @ClassName: ResultUtil
 * @Author: rh
 * @Description:
 * @Date: 2021/4/22 22:25
 */
public class ResultUtil {
    /**
     * 请求成功
     *
     * @return
     */
    public static BaseResult success() {
        return new BaseResult(BaseConstant.SUCCESS_CODE, BaseConstant.SUCCESS_MESSAGE, null);
    }

    /**
     * 请求成功
     *
     * @param object 返回数据
     * @return
     */
    public static BaseResult success(Object object) {
        return new BaseResult(BaseConstant.SUCCESS_CODE, BaseConstant.SUCCESS_MESSAGE, object);
    }

    /**
     * 请求成功
     *
     * @param message 请求成功描述
     * @param object  返回数据
     * @return
     */
    public static BaseResult success(String message, Object object) {
        return new BaseResult(BaseConstant.SUCCESS_CODE, message, object);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static BaseResult fail() {
        return new BaseResult(BaseConstant.ERROR_CODE, BaseConstant.ERROR_MESSAGE, null);
    }

    /**
     * 请求失败
     *
     * @param message 请求失败描述
     * @return
     */
    public static BaseResult fail(String message) {
        return new BaseResult(BaseConstant.ERROR_CODE, message, null);
    }

    /**
     * 请求失败
     *
     * @param message 请求失败描述
     * @param code    请求失败编码
     * @return
     */
    public static BaseResult fail(String code, String message) {
        return new BaseResult(code, message, null);
    }

}
