package sinia.com.entertainer.bean;

import java.io.Serializable;

/**
 * 验证码
 * Created by byw on 2016/12/27.
 */
public class CodeBean implements Serializable{

    private int state;
    private int isSuccessful;
    private String codeId;
    private String validateCode;
    private String canTime;

    public int getIsSuccessful() {
        return isSuccessful;
    }

    public int getState() {
        return state;
    }

    public String getCanTime() {
        return canTime;
    }

    public String getCodeId() {
        return codeId;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setIsSuccessful(int isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public void setCanTime(String canTime) {
        this.canTime = canTime;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
