package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2016/12/27.
 */
public class PersonInfBean implements Serializable {
    private int state;
    private int isSuccessful;
    private String imageUrl;//头像，
    private String userName;//艺名，
    private String profession;//角色，
    private String idConfirm;//实名认证？（1.是0.否）
    private String vConfirm;//加V？（1.是0.否）
    private String introduction;//签名，
    private String sex;//性别（1.男2.女）
    private String tall;//身高，
    private String weight;//体重，
    private String bwh;//三围，
    private String birth;//-生日，
    private String address;//所在地，
    private List<PersonInfCBean> cItems;//演艺圈
    private List<PersonInfMBean> mItems;//我的作品
    private String telephone;//手机号
    private String followStatus;//未关注 2已关注

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<PersonInfCBean> getcItems() {
        return cItems;
    }

    public List<PersonInfMBean> getmItems() {
        return mItems;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth() {
        return birth;
    }

    public String getBwh() {
        return bwh;
    }

    public String getIdConfirm() {
        return idConfirm;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProfession() {
        return profession;
    }

    public String getSex() {
        return sex;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setBwh(String bwh) {
        this.bwh = bwh;
    }

    public void setcItems(List<PersonInfCBean> cItems) {
        this.cItems = cItems;
    }

    public void setIdConfirm(String idConfirm) {
        this.idConfirm = idConfirm;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setmItems(List<PersonInfMBean> mItems) {
        this.mItems = mItems;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
}
