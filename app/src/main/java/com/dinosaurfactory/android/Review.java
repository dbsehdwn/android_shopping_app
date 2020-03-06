package com.dinosaurfactory.android;

public class Review {
    private String id;
    private String date;
    private String text;
    private String photo;

    public void setId(String id) {
        this.id = id;
    }
    public void setPhoto(String photo){this.photo = photo;}
    public void setDate(String date) {
        this.date = date;
    }
    public void setText(String text){
        this.text=text;
    }

    public String getId(){return id;}
    public String getDate(){ return date;}
    public String getText(){return text;}
    public String getPhoto(){return photo;}

}
