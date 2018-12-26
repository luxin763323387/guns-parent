package com.stylefeng.guns.rest.modular.vo;

/**
 * @author Steven Lu
 * @date 2018/12/18 -13:12
 */
public class ResponseVO<M> {

    //返回状态[0 成功， 1 业务失败， 999 表示系统异常]
    private int status;
    //返回错误消息
    private String msg;
    //返回实体
    private M date;

    //单例模式
    private ResponseVO(){};

    //业务成功
    public static<M> ResponseVO success(M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setDate(m);

        return responseVO;
    }

    public static<M> ResponseVO success(String msg ){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
       responseVO.setMsg(msg);
        return responseVO;
    }

    //业务失败
    public static ResponseVO serviceFaile(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(1);
        responseVO.setMsg(msg);

        return responseVO;
    }


    //系统异常
    public static ResponseVO appFaile(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public M getDate() {
        return date;
    }

    public void setDate(M date) {
        this.date = date;
    }
}
