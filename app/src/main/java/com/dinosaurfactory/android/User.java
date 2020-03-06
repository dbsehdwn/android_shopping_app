package com.dinosaurfactory.android;

public class User {
    private int user_num;
    private String id;
    private String pw;
    private String name;

    public int getUser_num() {
        return user_num;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }
}
