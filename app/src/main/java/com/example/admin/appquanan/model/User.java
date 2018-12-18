package com.example.admin.appquanan.model;

import java.io.Serializable;

/**
 * Created by Admin on 10/17/2017.
 */

public class User implements Serializable{
    private int idUser;
    private String nickName;
    private String username;
    private String password;
    private int roleId;

    public User() {
    }

    public User(int idUser, String nickName, String username, String password,int roleId) {
        this.idUser = idUser;
        this.nickName = nickName;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
