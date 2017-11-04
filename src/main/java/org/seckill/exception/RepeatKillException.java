package org.seckill.exception;

/**
 * Created by IDEA
 * Date: 2017/10/24
 * Time: 10:46
 * Author: shwy
 * 重复秒杀异常处理(运行期日常)
 * spring 申明事物只接受运行期日常,才会事物回滚
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
