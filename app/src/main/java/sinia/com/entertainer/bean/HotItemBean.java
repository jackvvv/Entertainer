package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * 首页热点广场
 * Created by byw on 2016/12/28.
 */
public class HotItemBean implements Serializable {
    private String circleId;//晒艺圈id，
    private String actMarketId;//演艺市场id,
    private String musicDataId;//音乐id,
    private String title;//标题，
    private String imageUrl;//图片，
    private String createTime;//创建时间
    private String type;//1:晒艺圈信息详情2:找场所找演员详情3音乐播放详情4:视频播放详情
    private String musicType;//=4有伴奏
    private String videoId;//视频id

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getActMarketId() {
        return actMarketId;
    }

    public String getCircleId() {
        return circleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getMusicDataId() {
        return musicDataId;
    }

    public String getTitle() {
        return title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setActMarketId(String actMarketId) {
        this.actMarketId = actMarketId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setMusicDataId(String musicDataId) {
        this.musicDataId = musicDataId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
