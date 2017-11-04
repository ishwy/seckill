package org.seckill.exception;

/**
 * Created by IDEA
 * Date: 2017/10/24
 * Time: 10:50
 * Author: shwy
 * 秒杀业务异常
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
