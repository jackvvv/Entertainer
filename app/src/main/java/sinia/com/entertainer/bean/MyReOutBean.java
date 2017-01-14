package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2016/12/29.
 */
public class MyReOutBean implements Serializable {
    private int state;
    private int isSuccessful;
    private String marketId;//市场id，
    private String spaceName;//场所名称，
    private String allowTime;//截止时间，
    private String sex;//性别要求 1.男2.女3.不限
    private String reActor;//需求角色，
    private String actPlace;//工作地点，
    private String createTime;//发布时间，
    private String signNum;//报名人数
    private String agreeNum;//已接受人数
    private String status;//类型   1.待处理2.已过期3.已关闭

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
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

    public String getReActor() {
        return reActor;
    }

    public String getSex() {
        return sex;
    }

    public String getSignNum() {
        return signNum;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setReActor(String reActor) {
        this.reActor = reActor;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public void setState(int state) {
        this.state = state;
    }
}
