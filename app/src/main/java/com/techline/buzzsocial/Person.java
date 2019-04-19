package com.techline.buzzsocial;

public class Person {
    private String full_name;
    private String user_name;
    private String pk;
    private String imgURL;

    public Person(String full_name, String user_name,  String pk, String imgURL) {
        this.user_name = user_name;
        this.full_name = full_name;
        this.pk = pk;
        this.imgURL = imgURL;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
