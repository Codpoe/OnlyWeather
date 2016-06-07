package me.codpoe.onlyweather.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Codpoe on 2016/6/2.
 */
public class ConstellationBean {

    /**
     * date : 20160602
     * name : 射手座
     * QFriend : 射手座
     * all : 40%
     * color : 白色
     * datetime : 2016年06月02日
     * health : 75%
     * love : 20%
     * money : 40%
     * number : 5
     * summary : 你在人际，情感和家庭方面容易产生一些混乱的感受，比如不知如何增进彼此的感情，或者打理好自己的私生活和居家状态。但好在你的个人发展还算不错，不如聚焦于自己该完成的事情吧。
     * work : 20%
     * resultcode : 200
     * error_code : 0
     */

    @SerializedName("date")
    private int date;
    @SerializedName("name")
    private String name;
    @SerializedName("QFriend")
    private String QFriend;
    @SerializedName("all")
    private String all;
    @SerializedName("color")
    private String color;
    @SerializedName("datetime")
    private String datetime;
    @SerializedName("health")
    private String health;
    @SerializedName("love")
    private String love;
    @SerializedName("money")
    private String money;
    @SerializedName("number")
    private int number;
    @SerializedName("summary")
    private String summary;
    @SerializedName("work")
    private String work;
    @SerializedName("resultcode")
    private String resultcode;
    @SerializedName("error_code")
    private int errorCode;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
