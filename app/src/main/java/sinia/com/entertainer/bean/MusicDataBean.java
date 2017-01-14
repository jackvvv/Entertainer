package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/3.
 */
public class MusicDataBean implements Serializable {
    private int state;
    private int isSuccessful;
    private List<MusicDataDetBean> items;
    private List<HotTopBean> imageItems;

    public List<HotTopBean> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<HotTopBean> imageItems) {
        this.imageItems = imageItems;
    }

    public List<MusicDataDetBean> getItems() {
        return items;
    }


    public void setItems(List<MusicDataDetBean> items) {
        this.items = items;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setState(int state) {
        this.state = state;
    }
}
