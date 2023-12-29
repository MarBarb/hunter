package com.lch.hunter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@TableName(value = "hunter_user_profile_img")
public class UserProfileImg {
    @TableId(type = IdType.AUTO)
    private int userimgid;
    private int userid;
    private String userimgpath;

    private String userimgrealpath;


    public UserProfileImg() {
    }

    public UserProfileImg(int userimgid, int userid, String userimgpath, String userimgrealpath) {
        this.userimgid = userimgid;
        this.userid = userid;
        this.userimgpath = userimgpath;
        this.userimgrealpath = userimgrealpath;
    }

    public int getUserimgid() {
        return userimgid;
    }

    public void setUserimgid(int userimgid) {
        this.userimgid = userimgid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUserimgpath() {
        return userimgpath;
    }

    public void setUserimgpath(String userimgpath) {
        this.userimgpath = userimgpath;
    }

    public String getUserimgrealpath() {
        return userimgrealpath;
    }

    public void setUserimgrealpath(String userimgrealpath) {
        this.userimgrealpath = userimgrealpath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("userimgid", userimgid)
                .append("userid", userid)
                .append("userimgpath", userimgpath)
                .append("userimgrealpath", userimgrealpath)
                .toString();
    }
}
