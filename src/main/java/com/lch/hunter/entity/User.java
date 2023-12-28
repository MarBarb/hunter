package com.lch.hunter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lch.hunter.handler.AESEncryptHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
@TableName(value = "hunter_user", autoResultMap = true)
public class User {
    private String username;

    @TableId(type= IdType.AUTO)
    private int userid;

    @TableField(typeHandler = AESEncryptHandler.class)
    private String password; // 使用AES128加密存储

    private String userdepartment;

    private String usersemester;

    public User(){
    }

    public User(String username, int userid, String password, String userdepartment, String usersemester) {
        this.username = username;
        this.userid = userid;
        this.password = password;
        this.userdepartment = userdepartment;
        this.usersemester = usersemester;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserdepartment() {
        return userdepartment;
    }

    public void setUserdepartment(String userdepratment) {
        this.userdepartment = userdepratment;
    }

    public String getUsersemester() {
        return usersemester;
    }

    public void setUsersemester(String usersemester) {
        this.usersemester = usersemester;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("username", username)
                .append("userid", userid)
                .append("password", password)
                .append("userdepartment", userdepartment)
                .append("usersemester", usersemester)
                .toString();
    }
}
