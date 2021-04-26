package com.plus.bysj.account.beans.req;

import com.plus.bysj.account.beans.req.PageReq;
import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.beans
 * @ClassName: BillQueryReq
 * @Author: rh
 * @Description:
 * @Date: 2021/4/24 18:49
 */
@Data
public class BillQueryReq extends PageReq {
    private String type;
    private String start;
    private String end;

}
