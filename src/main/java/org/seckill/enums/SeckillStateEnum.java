package org.seckill.enums;

/**
 * Created by IDEA
 * Date: 2017/10/24
 * Time: 16:56
 * Author: shwy
 * 使用枚举表述常量数据字段
 */
public enum SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改"),;

    //成员变量
    //状态
    private int state;
    //状态描述
    private String stateInfo;

    //构造方法
    private SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    //get方法
    public int getState() {
        return state;
    }
    public String getStateInfo() {
        return stateInfo;
    }



    //实现方法
    public static SeckillStateEnum stateOf(int index) {
        //values,内置的函数,返回值,如果跟我们传进来的一直,就返回
        for (SeckillStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
