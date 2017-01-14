package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/4.
 */
public class MusicDetBean implements Serializable {
    private int state;
    private int isSuccessful;
    private String musicDataId;//音乐id，
    private String name;//歌名，
    private String imageUrl;//图片，
    private String musicUrl;//音乐地址，
    private String author;//作者，
    private String likeNum;//-赞数，
    private List<MusicDetComBean> comItems;//评论
    private String zanStatus;//赞1.未点赞2.已点赞
    private String collectionStatus;// 1 没有收藏 2收藏过了

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public String getZanStatus() {
        return zanStatus;
    }

    public void setZanStatus(String zanStatus) {
        this.zanStatus = zanStatus;
    }

    public String getName() {
        return name;
    }

    public String getMusicDataId() {
        return musicDataId;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public List<MusicDetComBean> getComItems() {
        return comItems;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMusicDataId(String musicDataId) {
        this.musicDataId = musicDataId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComItems(List<MusicDetComBean> comItems) {
        this.comItems = comItems;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public void setState(int state) {
        this.state = state;
    }
}
