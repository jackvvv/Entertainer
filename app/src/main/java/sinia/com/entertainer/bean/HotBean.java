package sinia.com.entertainer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byw on 2016/12/28.
 */
public class HotBean implements Serializable {

    private int state;
    private int isSuccessful;
    private List<HotActorBean> actorItems;//演员
    private List<HotPlaceBean> placeItems;//剧院
    private List<HotTopBean> topItems;//首页轮播
    private List<HotItemBean> items;//热点广场

    public List<HotActorBean> getActorItems() {
        return actorItems;
    }

    public List<HotItemBean> getItems() {
        return items;
    }

    public List<HotPlaceBean> getPlaceItems() {
        return placeItems;
    }

    public List<HotTopBean> getTopItems() {
        return topItems;
    }

    public void setActorItems(List<HotActorBean> actorItems) {
        this.actorItems = actorItems;
    }

    public void setItems(List<HotItemBean> items) {
        this.items = items;
    }

    public void setPlaceItems(List<HotPlaceBean> placeItems) {
        this.placeItems = placeItems;
    }

    public void setTopItems(List<HotTopBean> topItems) {
        this.topItems = topItems;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }
}
