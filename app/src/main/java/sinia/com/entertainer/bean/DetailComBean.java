package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * Created by byw on 2017/1/6.
 */
public class DetailComBean implements Serializable{
   private String comImage;//评论者头像，
    private String comName;//评论者用户名，
    private String comLevel;//评论者等级，
    private String  content;//评论内容，
    private String  comTime;//评论时间

    public String getContent() {
        return content;
    }

    public String getComImage() {
        return comImage;
    }

    public String getComLevel() {
        return comLevel;
    }

    public String getComName() {
        return comName;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComImage(String comImage) {
        this.comImage = comImage;
    }

    public void setComLevel(String comLevel) {
        this.comLevel = comLevel;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
