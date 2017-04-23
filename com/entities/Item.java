package com.entities;

/**
 * Created by jezamartu on 4/18/2017.
 */
public class Item {
    private int user_ID;
    private String date;
    private String item_name;
    private float item_price;

    public Item(){
        user_ID = 0;
        date = "" ;
        item_name = "";
        item_price = 0;
    }

    public Item(int user_id, String date, String item_name, float item_price){
        this.user_ID = user_id;
        this.date = date;
        this.item_name = item_name;
        this.item_price = item_price;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public float getItem_price() {
        return item_price;
    }

    public void setItem_price(float item_price) {
        this.item_price = item_price;
    }
}
