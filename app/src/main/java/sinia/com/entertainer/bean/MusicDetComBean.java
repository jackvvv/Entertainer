package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/4.
 */
public class MusicDetComBean implements Serializable {
    private String comId;//评论id，
    private String userId;//评论者id，
    private String comment;//评论内容，
    private String comTime;//评论时间，
    private String comName;//评论者姓名，
    private String comImage;//评论者头像，
    private String reName;//回复人
    private String reComImage;//回复人头像
    private String type;//是否有回复 1有 2无

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReComImage() {
        return reComImage;
    }

    public String getReName() {
        return reName;
    }

    public void setReComImage(String reComImage) {
        this.reComImage = reComImage;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public String getComId() {
        return comId;
    }

    public String getComImage() {
        return comImage;
    }

    public String getComment() {
        return comment;
    }

    public String getComName() {
        return comName;
    }

    public String getComTime() {
        return comTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public void setComImage(String comImage) {
        this.comImage = comImage;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }
}
