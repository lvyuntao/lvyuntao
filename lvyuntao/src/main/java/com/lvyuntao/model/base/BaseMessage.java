package com.lvyuntao.model.base;

import com.lvyuntao.model.table.Admin;
import lombok.Data;

/**
 * Created by SF on 2019/3/24.
 */
@Data
public class BaseMessage<T> {
    //状态
    private Integer state;
    //信息
    private String message;
    //主数据
    private T data;
    //可能需要的附加数据
    private Object extra;
    //错误堆栈详情
    private Object messageDetail;

    /**
     * 正常返回
     * @param data
     * @return
     */
    public static<T> BaseMessage<T> success(T data){
        return new BaseMessage(0,"成功",data,null,null);
    }
    public static<T> BaseMessage<T> success(T data,Object extra){
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



    public BaseMessage(Integer state, String message, T data, Object extra, Object messageDetail) {
        this.state = state;
        this.message = message;
        this.data = data;
        this.extra = extra;
        this.messageDetail = messageDetail;
    }
    public BaseMessage<Admin> test(){
        return BaseMessage.error(1,"");
    }

}
