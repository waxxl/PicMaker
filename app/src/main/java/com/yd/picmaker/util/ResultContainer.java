package com.yd.picmaker.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.yd.picmaker.frame.FrameEntity;
import com.yd.picmaker.mutitouchh.MultiTouchEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ResultContainer {
    public static final String FRAME_BACKGROUND_IMAGE_KEY = "frameBackgroundKey";
    public static final String FRAME_IMAGES_KEY = "frameImageKey";
    public static final String FRAME_STICKER_IMAGES_KEY = "frameStickerKey";
    public static final String IMAGES_KEY = "imagesKey";
    public static final String PHOTO_BACKGROUND_IMAGE_KEY = "photoBgKey";
    private static ResultContainer instance;
    private final HashMap<String, Bitmap> mDecodedImageMap = new HashMap<>();
    private Uri mFrameBackgroundImage = null;
    private ArrayList<FrameEntity> mFrameImages = new ArrayList<>();
    private ArrayList<MultiTouchEntity> mFrameStickerImages = new ArrayList<>();
    private ArrayList<MultiTouchEntity> mImages = new ArrayList<>();
    private Uri mPhotoBackgroundImage = null;

    public static ResultContainer getInstance() {
        if (instance == null) {
            instance = new ResultContainer();
        }
        return instance;
    }

    private ResultContainer() {
    }

    public void removeImageEntity(MultiTouchEntity multiTouchEntity) {
        this.mImages.remove(multiTouchEntity);
    }

    public void putImageEntities(ArrayList<MultiTouchEntity> arrayList) {
        this.mImages.clear();
        Iterator<MultiTouchEntity> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mImages.add(it.next());
        }
    }

    public void putImage(String str, Bitmap bitmap) {
        this.mDecodedImageMap.put(str, bitmap);
    }

    public Bitmap getImage(String str) {
        return this.mDecodedImageMap.get(str);
    }

    public ArrayList<MultiTouchEntity> copyImageEntities() {
        ArrayList<MultiTouchEntity> arrayList = new ArrayList<>();
        Iterator<MultiTouchEntity> it = this.mImages.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public ArrayList<MultiTouchEntity> getImageEntities() {
        return this.mImages;
    }

    public Uri getPhotoBackgroundImage() {
        return this.mPhotoBackgroundImage;
    }

    public void setPhotoBackgroundImage(Uri uri) {
        this.mPhotoBackgroundImage = uri;
    }

    public void setFrameBackgroundImage(Uri uri) {
        this.mFrameBackgroundImage = uri;
    }

    public Uri getFrameBackgroundImage() {
        return this.mFrameBackgroundImage;
    }

    public void putFrameImage(FrameEntity frameEntity) {
        this.mFrameImages.add(frameEntity);
    }

    public ArrayList<FrameEntity> copyFrameImages() {
        ArrayList<FrameEntity> arrayList = new ArrayList<>();
        Iterator<FrameEntity> it = this.mFrameImages.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public void putFrameSticker(MultiTouchEntity multiTouchEntity) {
        this.mFrameStickerImages.add(multiTouchEntity);
    }

    public void putFrameStickerImages(ArrayList<MultiTouchEntity> arrayList) {
        this.mFrameStickerImages.clear();
        Iterator<MultiTouchEntity> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mFrameStickerImages.add(it.next());
        }
    }

    public ArrayList<MultiTouchEntity> copyFrameStickerImages() {
        ArrayList<MultiTouchEntity> arrayList = new ArrayList<>();
        Iterator<MultiTouchEntity> it = this.mFrameStickerImages.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public void removeFrameSticker(MultiTouchEntity multiTouchEntity) {
        this.mFrameStickerImages.remove(multiTouchEntity);
    }

    public void clearFrameImages() {
        this.mFrameImages.clear();
    }

    public void clearAll() {
        this.mImages.clear();
        this.mPhotoBackgroundImage = null;
        this.mFrameStickerImages.clear();
        this.mFrameImages.clear();
        this.mFrameBackgroundImage = null;
        this.mDecodedImageMap.clear();
    }

    public void clearAllImageInFrameCreator() {
        this.mFrameStickerImages.clear();
        this.mFrameImages.clear();
        this.mFrameBackgroundImage = null;
    }

    public void saveToBundle(Bundle bundle) {
        bundle.putParcelableArrayList(IMAGES_KEY, this.mImages);
        bundle.putParcelable(PHOTO_BACKGROUND_IMAGE_KEY, this.mPhotoBackgroundImage);
        bundle.putParcelableArrayList(FRAME_STICKER_IMAGES_KEY, this.mFrameStickerImages);
        bundle.putParcelable(FRAME_BACKGROUND_IMAGE_KEY, this.mFrameBackgroundImage);
        bundle.putParcelableArrayList(FRAME_IMAGES_KEY, this.mFrameImages);
    }

    public void restoreFromBundle(Bundle bundle) {
        this.mImages = bundle.getParcelableArrayList(IMAGES_KEY);
        if (this.mImages == null) {
            this.mImages = new ArrayList<>();
        }
        this.mPhotoBackgroundImage = bundle.getParcelable(PHOTO_BACKGROUND_IMAGE_KEY);
        this.mFrameStickerImages = bundle.getParcelableArrayList(FRAME_STICKER_IMAGES_KEY);
        if (this.mFrameStickerImages == null) {
            this.mFrameStickerImages = new ArrayList<>();
        }
        this.mFrameBackgroundImage = bundle.getParcelable(FRAME_BACKGROUND_IMAGE_KEY);
        this.mFrameImages = bundle.getParcelableArrayList(FRAME_IMAGES_KEY);
        if (this.mFrameImages == null) {
            this.mFrameImages = new ArrayList<>();
        }
    }
}