package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2016/12/29.
 */
public class MyReOutLsBean implements Serializable{
    private int state;
    private int isSuccessful;
    private List<MyReOutBean> items;

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public List<MyReOutBean> getItems() {
        return items;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setItems(List<MyReOutBean> items) {
        this.items = items;
    }
}
