package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/12.
 */
public class ContentBean implements Serializable {
    private List<ContentTypeBean> followItems;
    private int state;
    private int isSuccessful;

    public int getState() {
        return state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public List<ContentTypeBean> getFollowItems() {
        return followItems;
    }

    public void setFollowItems(List<ContentTypeBean> followItems) {
        this.followItems = followItems;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setState(int state) {
        this.state = state;
    }
}
