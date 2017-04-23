package javaapp;


/**
 * Created by jezamartu on 4/19/2017.
 */
public class UserLogInId {
    private static UserLogInId ourInstance = null;

    public int currentUserId=0;

    private UserLogInId() {
    }

    public static UserLogInId getInstance() {
        if(ourInstance == null) {
            ourInstance = new UserLogInId();
        }
        return ourInstance;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void changeId(int id){
        currentUserId = id;
    }

}

