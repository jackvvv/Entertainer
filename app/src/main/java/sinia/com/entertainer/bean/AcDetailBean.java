package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/6.
 */
public class AcDetailBean implements Serializable {
    private String marketId;//id
    private String actorTitle;//演员标题
    private String userId;//用户id
    private String userImage;//用户头像
    private String userName;//用户名
    private String profession;//职业
    private String marketTime;//场所发布时间
    private String idConfirm;//实名认证？（2.是1.否）
    private String vConfirm;//加V？（1.是0.否）
    private String imageUrl;//图片
    private String actPlace;//地址

    public String getActPlace() {
        return actPlace;
    }

    public void setActPlace(String actPlace) {
        this.actPlace = actPlace;
    }

    public String getActorTitle() {
        return actorTitle;
    }

    public String getIdConfirm() {
        return idConfirm;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getMarketTime() {
        return marketTime;
    }

    public String getProfession() {
        return profession;
    }

    public String getUserId() {
        return userId;
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

    public void setActorTitle(String actorTitle) {
        this.actorTitle = actorTitle;
    }

    public void setIdConfirm(String idConfirm) {
        this.idConfirm = idConfirm;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setMarketTime(String marketTime) {
        this.marketTime = marketTime;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
