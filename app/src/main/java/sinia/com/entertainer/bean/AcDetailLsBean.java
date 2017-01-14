package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/6.
 */
public class AcDetailLsBean implements Serializable {
    private int state;
    private int isSuccessful;
    private List<AcDetailBean> items;

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public List<AcDetailBean> getItems() {
        return items;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setItems(List<AcDetailBean> items) {
        this.items = items;
    }

    public void setState(int state) {
        this.state = state;
    }
}
