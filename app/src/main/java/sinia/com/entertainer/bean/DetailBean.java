package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/6.
 */
public class DetailBean implements Serializable {
    private int state;
    private int isSuccessful;
    private String actMarketId;//演艺市场id，
    //发布者
    private String userId;//用户id，
    private String upImage;//用户头像，
    private String upUserName;//用户名，
    private String idConfirm;//是否已实名认证（1.是0.否）
    private String vConfirm;//加v?(1.是2.否)
    //场地
    private String marketTime;//演艺市场发布时间，
    private String spaceInfo;//场地描述，
    private String allowTime;//截止时间，
    private String advantage;//场地优势，
    private String actPlace;//工作地点，
    private String sex;//性别要求，
    private String reActor;//需要角色，
    private String spaceName;//场所名称，
    private String vedioUrl;//宣传视频
    private List<DetailImgBean> imageItems;//图片
    private List<DetailSignBean> signItems;//参加人员
    private List<DetailComBean> commentItems;//评论
    private String signStatus;//是否报名 1.是2.否
    private String closeType;//是否关闭1.是0.否

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public int getState() {
        return state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public List<DetailComBean> getCommentItems() {
        return commentItems;
    }

    public List<DetailImgBean> getImageItems() {
        return imageItems;
    }

    public List<DetailSignBean> getSignItems() {
        return signItems;
    }

    public String getActMarketId() {
        return actMarketId;
    }

    public String getActPlace() {
        return actPlace;
    }

    public String getAdvantage() {
        return advantage;
    }

    public String getAllowTime() {
        return allowTime;
    }

    public String getIdConfirm() {
        return idConfirm;
    }

    public String getMarketTime() {
        return marketTime;
    }

    public String getReActor() {
        return reActor;
    }

    public String getSex() {
        return sex;
    }

    public String getSpaceInfo() {
        return spaceInfo;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public String getUpImage() {
        return upImage;
    }

    public String getUpUserName() {
        return upUserName;
    }

    public String getUserId() {
        return userId;
    }

    public String getvConfirm() {
        return vConfirm;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public void setActMarketId(String actMarketId) {
        this.actMarketId = actMarketId;
    }

    public void setActPlace(String actPlace) {
        this.actPlace = actPlace;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public void setAllowTime(String allowTime) {
        this.allowTime = allowTime;
    }

    public void setCommentItems(List<DetailComBean> commentItems) {
        this.commentItems = commentItems;
    }

    public void setIdConfirm(String idConfirm) {
        this.idConfirm = idConfirm;
    }

    public void setImageItems(List<DetailImgBean> imageItems) {
        this.imageItems = imageItems;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setMarketTime(String marketTime) {
        this.marketTime = marketTime;
    }

    public void setReActor(String reActor) {
        this.reActor = reActor;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSignItems(List<DetailSignBean> signItems) {
        this.signItems = signItems;
    }

    public void setSpaceInfo(String spaceInfo) {
        this.spaceInfo = spaceInfo;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUpImage(String upImage) {
        this.upImage = upImage;
    }

    public void setUpUserName(String upUserName) {
        this.upUserName = upUserName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setvConfirm(String vConfirm) {
        this.vConfirm = vConfirm;
    }
}
