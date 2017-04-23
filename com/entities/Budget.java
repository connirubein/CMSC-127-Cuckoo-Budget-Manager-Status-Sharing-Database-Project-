package com.entities;

/**
 * Created by jezamartu on 4/17/2017.
 */
public class Budget {
    private int user_ID;//primary key among 3 tables
    private float daily_budget;//budget for that day
    private String date;//date of day
    private float target;//target percentage
    private boolean meet;//if target was met
    private float expense;//accumulating expense of the day)

    public Budget(){
        user_ID = 0;
        daily_budget = 0;
        date = "";
        target = 0;
        meet = true;
        expense = 0;
    }

    public Budget(int user_ID, float daily_budget, String date, float target){//user id kay kuhaon from somewhere
        this.user_ID = user_ID;
        this.daily_budget = daily_budget;
        this.date = date;
        this.target = (float)((((double)target/(double)(100.00))*(double)daily_budget));
        this.meet = true;
        this.expense = 0;
    }//constructor called if new budget row is created

    public Budget(int user_ID, float daily_budget, String date, int target, boolean meet, float expense){//temporary, remove later
        this.user_ID = user_ID;
        this.daily_budget = daily_budget;
        this.date = date;
        this.target = (float)((((double)target/(double)(100.00))*(double)daily_budget));;
        this.meet = meet;
        this.expense = expense;
    }//only for testing

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public float getDaily_budget() {
        return daily_budget;
    }

    public void setDaily_budget(float daily_budget) {
        this.daily_budget = daily_budget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public boolean isMeet() {
        return meet;
    }

    public void setMeet(boolean meet) {
        this.meet = meet;
    }

    public float getExpense() {
        return expense;
    }

    public void setExpense(float expense) {
        this.expense = expense;
    }

}
