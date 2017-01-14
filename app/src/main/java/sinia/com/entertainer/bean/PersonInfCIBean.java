package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * 演艺圈图片
 * Created by byw on 2016/12/29.
 */
public class PersonInfCIBean implements Serializable{
    private String cimage;//图片

    public String getCimage() {
        return cimage;
    }

    public void setCimage(String cimage) {
        this.cimage = cimage;
    }
}
