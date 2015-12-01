package com.zt.dictionary.model.db;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * @user: zt
 * @describe:
 */

public class Word extends DataSupport {
    @Column(nullable = false)
    private String word;

    private String mainTranslate;

    private String detailTranslate;

    private Date saveTime;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public String getMainTranslate() {
        return mainTranslate;
    }

    public void setMainTranslate(String mainTranslate) {
        this.mainTranslate = mainTranslate;
    }

    public String getDetailTranslate() {
        return detailTranslate;
    }

    public void setDetailTranslate(String detailTranslate) {
        this.detailTranslate = detailTranslate;
    }
}
