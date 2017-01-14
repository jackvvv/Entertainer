package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2016/12/27.
 */
public class PersonInfCBean implements Serializable {
    private String content;//内容
    private String vedioUrl;//视频
    private List<PersonInfCIBean> images;//图片

    public List<PersonInfCIBean> getImages() {
        return images;
    }

    public void setImages(List<PersonInfCIBean> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }


    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
