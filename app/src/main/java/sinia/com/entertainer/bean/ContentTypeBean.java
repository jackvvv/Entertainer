package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2017/1/12.
 */
public class ContentTypeBean implements Serializable {
    private List<ContentDetailBean> items;
    private String type;//字母

    public List<ContentDetailBean> getItems() {
        return items;
    }

    public String getType() {
        return type;
    }

    public void setItems(List<ContentDetailBean> items) {
        this.items = items;
    }

    public void setType(String type) {
        this.type = type;
    }
}
