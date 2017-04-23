package com.dao;

import com.entities.Budget;

import java.util.List;

/**
 * Created by jezamartu on 4/10/2017.
 */
public interface BudgetDao {
    void createBudgetTable();//called once only ever, same with person table and items table
    void insertBudget(Budget budget);//adds new day with new set of expenses
    boolean ifMet(int id, String day);//returns true if total expenses is less than budget-expenses, false if not
    Budget selectByIdDate(int id, String day);//gets day specified, called when user selects day they want to view progress of
    int selectAllMeet(int id);//returns number of days the user met target percentage
    void deleteById(int id);//deletes all budget logs done by that user
    //user can't delete day, regulated by mods; user can't
}
