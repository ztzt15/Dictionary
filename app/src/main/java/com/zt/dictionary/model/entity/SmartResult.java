package com.zt.dictionary.model.entity;

import java.util.List;

/**
 * @user: zt
 * @describe:
 */

public class SmartResult {
    private int type;
    private List<String> entries;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getEntries() {
        return entries;
    }

    public void setEntries(List<String> entries) {
        this.entries = entries;
    }
}
