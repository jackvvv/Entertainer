package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/3.
 */
public class MusicDataDetBean implements Serializable {

    private String musicDataId;//音乐id，
    private String name;//名字，
    private String author;//作者
    private String sourceDet;////出处
    private String songType;////1.翻唱2.原创
    private String zanStatus;//1.未点赞2.已点赞
    private String hotType;//1热2.不是
    private String newType;//1最新2.不是
    private String choosed = "1";//1未选择，2选择
    private String collectionStatus;//1.未收藏2.已收藏

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public String getChoosed() {
        return choosed;
    }

    public void setChoosed(String choosed) {
        this.choosed = choosed;
    }

    public String getHotType() {
        return hotType;
    }

    public String getNewType() {
        return newType;
    }

    public void setHotType(String hotType) {
        this.hotType = hotType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getAuthor() {
        return author;
    }

    public String getMusicDataId() {
        return musicDataId;
    }

    public String getName() {
        return name;
    }

    public String getSongType() {
        return songType;
    }

    public String getSourceDet() {
        return sourceDet;
    }

    public String getZanStatus() {
        return zanStatus;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMusicDataId(String musicDataId) {
        this.musicDataId = musicDataId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongType(String songType) {
        this.songType = songType;
    }

    public void setSourceDet(String sourceDet) {
        this.sourceDet = sourceDet;
    }

    public void setZanStatus(String zanStatus) {
        this.zanStatus = zanStatus;
    }
}
