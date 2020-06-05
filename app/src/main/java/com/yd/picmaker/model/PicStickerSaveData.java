package com.yd.picmaker.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PicStickerSaveData {
    private int id;
    private String filename;

    @Generated(hash = 1011945136)
    public PicStickerSaveData(int id, String filename) {
        this.id = id;
        this.filename = filename;
    }
    @Generated(hash = 1764234947)
    public PicStickerSaveData() {
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
