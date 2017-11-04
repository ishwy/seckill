package org.seckill.entity;

import java.util.Date;

/**
 * 实体类，对应的就是数据表里面的字段，然后实现getter和setter方法。
 */
public class Successkilled {

    private long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    // 多对一的复合属性,对应的seckill  mybatis
    private Seckill seckill;



    public long getSeckillId() {
        return seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public short getState() {
        return state;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public void setState(short state) {
        this.state = state;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "Successkilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
