package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2016/11/29.
 */
public class LoginBean implements Serializable {

    private int state;
    private int isSuccessful;
    private String userId;//用户ID
    private String imageUrl;//头像，
    private String userName;//艺名，
    private String profession;//角色，
    private String idConfirm;//实名认证？（1.是0.否）
    private String vConfirm;//加V？（1.是0.否）
    private String sign;//签名，
    private String phone;//电话
    private String sex;//性别（1.男2.女）
    private String tall;//身高，
    private String weight;//体重，
    private String bwh;//三围，
    private String birth;//生日，
    private String address;//所在地，

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIdConfirm() {
        return idConfirm;
    }

    public String getBirth() {
        return birth;
    }

    public String getBwh() {
        return bwh;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfession() {
        return profession;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public String getSign() {
        return sign;
    }

    public String getTall() {
        return tall;
    }

    public String getUserName() {
        return userName;
    }

    public String getvConfirm() {
        return vConfirm;
    }

    public String getWeight() {
        return weight;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setBwh(String bwh) {
        this.bwh = bwh;
    }

    public void setIdConfirm(String idConfirm) {
        this.idConfirm = idConfirm;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setTall(String tall) {
        this.tall = tall;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setvConfirm(String vConfirm) {
        this.vConfirm = vConfirm;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getState() {
        return state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
