package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * 少电话，reactor
 * Created by byw on 2016/12/29.
 */
public class MyAdBean implements Serializable {
    private String marketId;//市场id，
    private String actorTitle;//演员标题
    private String sex;//性别要求，
    private String allowTime;//截止时间，
    private String marketTime;//发布时间，
    private String actPlace;//工作地点
    private String signNum;//报名人数
    private String agreeNum;//已接受人数
    private String reActor;//需求角色，
    private String phone;//发布人手机号
    private String status;//类型   1.待处理2.已过期3.已关闭

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReActor() {
        return reActor;
    }

    public void setReActor(String reActor) {
        this.reActor = reActor;
    }

    public String getActorTitle() {
        return actorTitle;
    }

    public String getActPlace() {
        return actPlace;
    }

    public String getAgreeNum() {
        return agreeNum;
    }

    public String getAllowTime() {
        return allowTime;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getMarketTime() {
        return marketTime;
    }

    public String getSex() {
        return sex;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setActorTitle(String actorTitle) {
        this.actorTitle = actorTitle;
    }

    public void setActPlace(String actPlace) {
        this.actPlace = actPlace;
    }

    public void setAgreeNum(String agreeNum) {
        this.agreeNum = agreeNum;
    }

    public void setAllowTime(String allowTime) {
        this.allowTime = allowTime;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setMarketTime(String marketTime) {
        this.marketTime = marketTime;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }
}

