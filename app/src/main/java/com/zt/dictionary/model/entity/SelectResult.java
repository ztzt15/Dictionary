package com.zt.dictionary.model.entity;

import java.util.List;

/**
 * @user: zt
 * @describe:
 */

public class SelectResult {
    private String type;
    private int errorCode;
    private int elapsedTime;
    private List<List<TranslateResult>> translateResult;//这个自己单独解析
    private SmartResult smartResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SmartResult getSmartResult() {
        return smartResult;
    }

    public void setSmartResult(SmartResult smartResult) {
        this.smartResult = smartResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<List<TranslateResult>> getTranslateResult() {
        return translateResult;
    }

    public void setTranslateResult(List<List<TranslateResult>> translateResult) {
        this.translateResult = translateResult;
    }
}
