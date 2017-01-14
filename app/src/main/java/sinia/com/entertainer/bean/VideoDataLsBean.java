package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/10.
 */
public class VideoDataLsBean implements Serializable {
    private int state;
    private int isSuccessful;
    private List<VideoDataDetBean> items;

    public int getState() {
        return state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public List<VideoDataDetBean> getItems() {
        return items;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setItems(List<VideoDataDetBean> items) {
        this.items = items;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
