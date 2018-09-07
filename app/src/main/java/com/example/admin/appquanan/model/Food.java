package com.example.admin.appquanan.model;

import java.io.Serializable;

/**
 * Created by Admin on 10/17/2017.
 */

public class Food implements Serializable {
    private int id;
    private String nameFood;
    private String imageFood;
    private String type;//0:đồ uống //1:đồ ăn
    private int love;
    private int checkLike;
    private String district;
    private String address;
    private String price;
    private int totalLike;
    private int totalCmt;

    public Food() {
    }

    public int getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(int checkLike) {
        this.checkLike = checkLike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getTotalCmt() {
        return totalCmt;
    }

    public void setTotalCmt(int totalCmt) {
        this.totalCmt = totalCmt;
    }
}
