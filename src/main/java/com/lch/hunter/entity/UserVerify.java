package com.lch.hunter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lch.hunter.handler.AESEncryptHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@TableName(value = "hunter_user_verify", autoResultMap = true)
public class UserVerify {
    @TableId(type= IdType.AUTO)
    private int verifyid;
    private String mail;
    @TableField(typeHandler = AESEncryptHandler.class)
    private String code;

    public UserVerify() {
    }

    public UserVerify(int verifyid, String mail, String code) {
        this.verifyid = verifyid;
        this.mail = mail;
        this.code = code;
    }


    public int getVerifyid() {
        return verifyid;
    }

    public void setVerifyid(int verifyid) {
        this.verifyid = verifyid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("verifyid", verifyid)
                .append("mail", mail)
                .append("code", code)
                .toString();
    }
}
