package com.lch.hunter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@TableName("hunter_img")
public class Img {
    @TableId(type = IdType.AUTO)
    private int imgid;
    private int requireid;
    private String imgpath;

    public Img(int imgid, int requireid, String imgpath) {
        this.imgid = imgid;
        this.requireid = requireid;
        this.imgpath = imgpath;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getRequireid() {
        return requireid;
    }

    public void setRequireid(int requireid) {
        this.requireid = requireid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("imgid", imgid)
                .append("requireid", requireid)
                .append("imgpath", imgpath)
                .toString();
    }
}
