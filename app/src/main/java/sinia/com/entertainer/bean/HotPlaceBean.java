package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2016/12/28.
 */
public class HotPlaceBean implements Serializable{
    private String userId;
    private String name;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
