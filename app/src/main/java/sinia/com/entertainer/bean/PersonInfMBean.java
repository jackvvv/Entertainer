package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2016/12/27.
 */
public class PersonInfMBean implements Serializable{
    private String imageUrl;//视频截图
    private String videoUrl;//视频连接

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
