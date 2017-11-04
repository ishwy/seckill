package org.seckill.dto;

import java.util.Date;

/**
 * 暴露秒杀地址DTO
 * 用于Service 数据返回的封装
 * Web -> Service 层的数据传递
 */
public class Exporse {

    //是否开启秒杀
    private boolean exposed;

    //md5加密
    private String md5;

    //秒杀id
    private long seckillId;

    //当前时间
    private long now;

    //当前时间
    private long start;

    //当前时间
    private long end;


    @Override
    public String toString() {
        return "Exporse{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    //用于判断秒杀成功,返回数据
    public Exporse(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    //用户判断是否存在秒杀数据
    public Exporse(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    //用于判断秒杀时间是否开启
    public Exporse(boolean exposed, long seckillId,long now, long start, long end) {

        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

}
