package com.eptonic.photocollage.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PhotoStickerSaveData {
    private int idx;
    private String filenamex;


    @Generated(hash = 1207526840)
    public PhotoStickerSaveData(int idx, String filenamex) {
        this.idx = idx;
        this.filenamex = filenamex;
    }
    @Generated(hash = 464683161)
    public PhotoStickerSaveData() {
    }


    public int getId() {
        return this.idx;
    }
    public void setId(int id) {
        this.idx = id;
    }
    public String getFilename() {
        return this.filenamex;
    }
    public void setFilename(String filename) {
        this.filenamex = filename;
    }
    public int getIdx() {
        return this.idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }
    public String getFilenamex() {
        return this.filenamex;
    }
    public void setFilenamex(String filenamex) {
        this.filenamex = filenamex;
    }
}
