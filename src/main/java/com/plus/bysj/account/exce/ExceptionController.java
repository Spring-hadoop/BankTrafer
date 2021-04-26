package com.plus.bysj.account.exce;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.plus.bysj.account.beans.resp.commom.BaseConstant;
import com.plus.bysj.account.beans.resp.commom.BaseResult;
import com.plus.bysj.account.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.exce
 * @ClassName: ExceptionController
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 20:23
 */
@RestController
@EnableConfigurationProperties({ServerProperties.class})
@RequestMapping("/error")
public class ExceptionController implements ErrorController {

    protected final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    /**
     * 初始化ExceptionController
     *
     * @param errorAttributes
     */
    @Autowired
    public ExceptionController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(produces = {"text/html", "application/x-ms-application"})
    public void errorHtml(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        Map map = getErrorAttributes(request);
        Throwable t = (Throwable) map.get("throwable");
        String msg = "请求错误";
        if (t != null) {
            logger.error("页面请求错误", t.getMessage());
            msg = msg + ":" + t.getMessage();
        }
        String status = String.valueOf(map.get("status"));
        //String redirect = error_base_dir + "/" + status + ".html";
        //response.sendRedirect(redirect);
        BaseResult stateResp = ResultUtil.fail(msg);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(JSONObject.toJSONString(stateResp, SerializerFeature.WriteMapNullValue));
    }

    @RequestMapping(produces = "application/json")
    @ResponseBody
    public ResponseEntity<BaseResult> errorJson(HttpServletRequest request, HttpServletResponse response) {
        String errCode = "ERROR-000000";
        String errInfo = "服务内部错误";
        Map map = getErrorAttributes(request);
        Throwable t = (Throwable) map.get("throwable");
        if (t != null) {
            map.put("详细异常信息", t);
        }
        logger.error(String.valueOf(map));
        if (t == null) {
            errCode = BaseConstant.ERROR_CODE+"-000001";
            errInfo = "未找到服务";
        } else if (t instanceof HttpRequestMethodNotSupportedException) {
            /**请求错误*/
            errCode = BaseConstant.ERROR_CODE+"-000002";
            errInfo = "请求错误:" + t.getMessage();
        } else if (t instanceof SQLException || t instanceof DataAccessException) {
            /**数据库异常*/
            errCode = BaseConstant.ERROR_CODE+"-000003";
            errInfo = "数据库异常";
        } else if (t instanceof ServiceException) {
            ServiceException se = (ServiceException) t;
            errCode = BaseConstant.ERROR_CODE+se.getErrCode();
            errInfo = se.getErrInfo();
        }else if(t instanceof BindException){
            BindException se =(BindException) t;
            errCode = BaseConstant.ERROR_CODE;
            errInfo = se.getBindingResult().getFieldError().getField()+se.getFieldError().getDefaultMessage();
        }
        BaseResult stateResp = ResultUtil.fail(errCode, errInfo);
        ResponseEntity re = new ResponseEntity(stateResp, HttpStatus.OK);
        return re;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes, ErrorAttributeOptions.defaults());
        Throwable t = this.errorAttributes.getError(requestAttributes);
        map.put("throwable", t);
        return map;
    }

    @Override
    public String getErrorPath() {
        return this.serverProperties.getError().getPath();
    }
}

