package com.example.admin.appquanan.model;

import java.io.Serializable;

/**
 * Created by Admin on 10/17/2017.
 */

public class Comment implements Serializable{
    private int idComment;
    private int userId;
    private int foodId;
    private String content;
    private String time;


    public Comment(int idComment, int userId, int foodId, String content,String time) {
        this.idComment = idComment;
        this.userId = userId;
        this.foodId = foodId;
        this.content = content;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Comment() {
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
