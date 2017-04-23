package com.dao;

import com.entities.Item;

/**
 * Created by jezamartu on 4/18/2017.
 */
public interface ItemDao {
    void createItemTable();//called once only ever, same with person table and budget table
    float getTotalExpensesForTheDay(int user_id, String date);//returns value of total expenses for the date and of user
    boolean addItem(Item item);//adds new item with already initialized variables and values
    void deleteById(int id);//delete all items by that id
}

