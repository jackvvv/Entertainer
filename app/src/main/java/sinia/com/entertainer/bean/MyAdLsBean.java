package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2016/12/29.
 */
public class MyAdLsBean implements Serializable{
    private int state;
    private int isSuccessful;
    private List<MyAdBean> items;

    public int getState() {
        return state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public List<MyAdBean> getItems() {
        return items;
    }

    public void setItems(List<MyAdBean> items) {
        this.items = items;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setState(int state) {
        this.state = state;
    }
}
