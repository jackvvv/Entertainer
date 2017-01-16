package sinia.com.entertainer.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import sinia.com.entertainer.bean.PersonInfBean;

/**
 * Created by 忧郁的眼神 on 2017/1/15 0015.
 */

public class UserUtils {
    public static AsyncHttpClient client = new AsyncHttpClient();

    public interface OnUserInfoListener {
        public void setUserInfo(PersonInfBean userInfo);
    }

    public static void getUserInfo(final String idkey, final OnUserInfoListener listener) {
        RequestParams params = new RequestParams();
//        params.put("userId", userId);
        params.put("type", "1");
        params.put("beUserId", "-1");
        client.setTimeout(40 * 1000);
        client.post(Constants.BASE_URL + "personInfo", params, new

                AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, String s) {
                        super.onSuccess(i, s);
                        Gson gson = new Gson();
                        if (s.contains("isSuccessful")
                                && s.contains("state")) {
                            Log.e("tag", s.toString());
                            PersonInfBean bean = gson.fromJson(s, PersonInfBean.class);
                            int state = bean.getState();
                            int isSuccessful = bean.getIsSuccessful();
                            if (state == 0 && isSuccessful == 0) {
                                if (null != bean) {
                                    listener.setUserInfo(bean);
                                }
                            }
                        }
                    }
                }

        );
    }

}
