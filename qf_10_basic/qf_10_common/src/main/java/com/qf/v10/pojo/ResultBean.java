package com.qf.v10.pojo;

import java.io.Serializable;

/**
 * @Author chenzetao
 * @Date 2020/1/7
 */
public class ResultBean implements Serializable {
    //状态码
    private Integer statuCode;//200,500
    //操作成功，返回的消息
    private String data;
    //操作失败，返回的消息
    private String msg;

    public static ResultBean success(String data){
        ResultBean resultBean = new ResultBean();
        resultBean.setStatuCode(200);
        resultBean.setData(data);
        return resultBean;
    }

    public static ResultBean error(String msg){
        ResultBean resultBean = new ResultBean();
        resultBean.setStatuCode(500);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public Integer getStatuCode() {
        return statuCode;
    }

    public void setStatuCode(Integer statuCode) {
        this.statuCode = statuCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
