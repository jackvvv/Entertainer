package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/12.
 */
public class ContentDetailBean implements Serializable {

    private String userId;//用户id，
    private String name;//名字，
    private String imageUrl;//头像，
    private String profession;//-角色
    private String vConfirm;//vip认证
    private String telephone;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getvConfirm() {
        return vConfirm;
    }

    public void setvConfirm(String vConfirm) {
        this.vConfirm = vConfirm;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public String getUserId() {
        return userId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
