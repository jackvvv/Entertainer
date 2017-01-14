package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * 首页轮播图
 * Created by byw on 2016/12/28.
 */
public class HotTopBean implements Serializable {
    private String imageUrl;//图片地址
    private String imageId;//id
    private String realPath;//链接

    public String getImageId() {
        return imageId;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
