package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/10.
 */
public class VideoDetBean implements Serializable {
    private int state;
    private int isSuccessful;
    private String videoUrl;//视频url
    private String number;//浏览数
    private String createTime;//创建时时间
    private String userName;//用户名
    private String userImage;//用户头像
    private String idConfirm;//实名认证1未验证 2.已验证
    private String vConfirm;//加v验证1是0不是
    private String zanNum;//1未点赞 2已点赞
    private String collectionNum;//1未收藏 2已收藏
    private List<MusicDetComBean> items;//评论

    public int getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public List<MusicDetComBean> getItems() {
        return items;
    }

    public String getCollectionNum() {
        return collectionNum;
    }

    public String getIdConfirm() {
        return idConfirm;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getvConfirm() {
        return vConfirm;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getZanNum() {
        return zanNum;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCollectionNum(String collectionNum) {
        this.collectionNum = collectionNum;
    }

    public void setIdConfirm(String idConfirm) {
        this.idConfirm = idConfirm;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setItems(List<MusicDetComBean> items) {
        this.items = items;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setvConfirm(String vConfirm) {
        this.vConfirm = vConfirm;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setZanNum(String zanNum) {
        this.zanNum = zanNum;
    }
}

