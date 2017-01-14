package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/6.
 */
public class DetailSignBean implements Serializable{
    private String signId;//报名id，
    private String  signUserId;//报名者用户id，
    private String signUserImage;//报名者头像，
    private String profession;//角色
    private String userName;//报名者名字

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSignUserId() {
        return signUserId;
    }

    public String getSignUserImage() {
        return signUserImage;
    }

    public void setSignUserId(String signUserId) {
        this.signUserId = signUserId;
    }

    public void setSignUserImage(String signUserImage) {
        this.signUserImage = signUserImage;
    }
}
