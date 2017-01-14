package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2016/12/28.
 */
public class UserIdBean implements Serializable {
    private int state;
    private int isSuccessful;
    private String userId;

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public String getUserId() {
        return userId;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
