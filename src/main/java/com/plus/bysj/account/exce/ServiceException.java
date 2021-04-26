package com.plus.bysj.account.exce;

import lombok.Data;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.exce
 * @ClassName: ServiceException
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 20:25
 */
@Data
public class ServiceException extends  RuntimeException{
    private String ErrCode;
    private String ErrInfo;

    public ServiceException() {
    }

    public ServiceException(String errCode, String errInfo) {
        super(errInfo);
        ErrCode = errCode;
        ErrInfo = errInfo;
    }
    public ServiceException(String errInfo) {
        super(errInfo);
        ErrCode = "";
        ErrInfo = errInfo;
    }

}
