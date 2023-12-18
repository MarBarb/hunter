package com.lch.hunter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("hunter_user")
public class User {
    private String username;
    @TableId(type= IdType.AUTO)
    private String userid;
    private String password;
    private String userdepartment;
    private String usersemester;

    public User(String username, String userid, String password, String userdepartment, String usersemester) {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
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
        return "User{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", userdepartment='" + userdepartment + '\'' +
                ", usersemester='" + usersemester + '\'' +
                '}';
    }
}
