package com.lch.hunter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;

@TableName("hunter_requires")
public class Requires {
    private int userid;
    @TableId(type = IdType.AUTO)
    private int requireid;
    private String title;
    private String description;
    private String reward;
    private String createtime;
    private String endtime;
    private String status;

    public Requires(int userid, int requireid, String title, String description, String reward, String createtime, String endtime, String status) {
        this.userid = userid;
        this.requireid = requireid;
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.createtime = createtime;
        this.endtime = endtime;
        this.status = status;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getRequireid() {
        return requireid;
    }

    public void setRequireid(int requireid) {
        this.requireid = requireid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("userid", userid)
                .append("requireid", requireid)
                .append("title", title)
                .append("description", description)
                .append("reward", reward)
                .append("createtime", createtime)
                .append("endtime", endtime)
                .append("status", status)
                .toString();
    }
}
