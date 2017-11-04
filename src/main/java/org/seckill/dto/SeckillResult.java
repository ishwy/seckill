package org.seckill.dto;

/**
 * Created by IDEA
 * Date: 2017/10/27
 * Time: 16:53
 * Author: shwy
 * 封装ajax请求json数据格式,泛型
 */
public class SeckillResult<T> {

    private boolean success;

    private T data;

    private String error;

    //如果成功肯定有状态和数据
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    //如果失败就有状态和错误信息
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
