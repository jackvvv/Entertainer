package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/10.
 */
public class VideoDataDetBean implements Serializable {

    private String videoId;//视频id
    private String imageUrl;//视频截图
    private String title;//视频标题
    private String content;//介绍
    private String createTime;//发布时间
    private String number;//浏览数
    private String commentNum;//评论数

    public String getContent() {
        return content;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
