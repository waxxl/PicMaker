package com.yd.picmaker.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StickerSaveData {
    private int id;
    private String filename;
    @Generated(hash = 1592971551)
    public StickerSaveData(int id, String filename) {
        this.id = id;
        this.filename = filename;
    }
    @Generated(hash = 1324695574)
    public StickerSaveData() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
