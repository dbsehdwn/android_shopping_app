package com.dinosaurfactory.android;

import java.util.ArrayList;

public class order {
    public String getList() {
        return list;
    }

    public ArrayList getPick() {
        return pick;
    }

    public void setList(String list) {
        this.list = list;
    }

    public void setPick(ArrayList pick) {
        this.pick = pick;
    }

    private String list;
    private ArrayList pick;
    private String ordername;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price;

    public int getNo() {
        return Order_no;
    }

    public void setNo(int no) {
        this.Order_no = no;
    }

    private int Order_no;

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public void setOrderoption(String orderoption) {
        this.orderoption = orderoption;
    }

    public void setOrderstate(String oarderstate) {
        this.orderstate = oarderstate;
    }

    private String orderoption;

    public String getOrdername() {
        return ordername;
    }

    public String getOrderoption() {
        return orderoption;
    }

    public String getOrderstate() {
        return orderstate;
    }

    private String orderstate;

}
