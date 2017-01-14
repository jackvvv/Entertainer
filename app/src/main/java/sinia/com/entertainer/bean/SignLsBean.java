package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/7.
 */
public class SignLsBean implements Serializable{
    private int state;
    private int isSuccessful;
    private List<SignBean> items;

    public int getState() {
        return state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public List<SignBean> getItems() {
        return items;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setItems(List<SignBean> items) {
        this.items = items;
    }

    public void setState(int state) {
        this.state = state;
    }
}
