package org.seckill.exception;

/**
 * Created by IDEA
 * Date: 2017/10/24
 * Time: 10:49
 * Author: shwy
 * 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
