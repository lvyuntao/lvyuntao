package com.lvyuntao.model.base;

import lombok.Data;

/**
 * Created by SF on 2019/3/24.
 */
@Data
public class BaseMessage {
    //状态
    private Integer state;
    //信息
    private String message;
    //主数据
    private Object data;
    //可能需要的附加数据
    private Object extra;
    //错误堆栈详情
    private Object messageDetail;

    /**
     * 正常返回
     * @param data
     * @return
     */
    public static BaseMessage success(Object data){
        return new BaseMessage(0,"成功",data,null,null);
    }
    public static BaseMessage success(Object data,Object extra){
        return new BaseMessage(0,"成功",data,extra,null);
    }

    /**
     * 错误返回
     * @param state
     * @param message
     * @return
     */
    public static BaseMessage error(int state,String message) {
        return new BaseMessage(state,"message",null,null,null);
    }

    /**
     * 错误返回并返回错误的堆栈异常信息
     * @param state
     * @param message
     * @param messageDetail
     * @return
     */
    public static BaseMessage error(int state,String message,Object messageDetail) {
        return new BaseMessage(state,message,null,null,null);
    }



    public BaseMessage(Integer state, String message, Object data, Object extra, Object messageDetail) {
        this.state = state;
        this.message = message;
        this.data = data;
        this.extra = extra;
        this.messageDetail = messageDetail;
    }
}
