package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/7.
 */
public class SignBean implements Serializable {

    private String userId;//用户id，
    private String userImg;//报名者头像，
    private String userName;//报名者用户名，
    private String profession;//报名者职业，
    private String signId;//报名id，

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfession() {
        return profession;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
