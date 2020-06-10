package com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.yd.photoeditor.ui.activity.ImageProcessingActivity;
import com.yd.photoeditor.vv.DialogUtils;
import com.yd.photoeditor.vv.FileUtils;

import java.io.File;
import java.util.ArrayList;

public abstract class BasePhotoActivity extends MyBaseActivity implements DialogUtils.OnAddImageButtonClickListener {
    protected static final int CAPTURE_IMAGE = 0x2010;
    protected static final int PICK_BACKGROUND = 0x2011;
    protected static final int PICK_IMAGE = 0x2012;
    protected static final int PICK_MULTIPLE = 0x2013;
    protected static final int PICK_STICKER = 0x2014;
    protected static final int REQUEST_ADD_TEXT_ITEM = 0x2015;
    protected static final int REQUEST_EDIT_IMAGE = 0x2016;
    protected static final int REQUEST_EDIT_TEXT_ITEM = 0x2017;
    protected static final int REQUEST_PHOTO_EDITOR = 0x2018;
    private Dialog mAddImageDialog;
    private View mAddImageView;
    private Animation mAnimation;
    private Uri mCapturedImageUri;

    public void onBackgroundColorButtonClick() {
    }

    public void onBackgroundPhotoButtonClick() {
    }

    public void onTextButtonClick() {
    }

    public void resultAddTextItem(String str, int i, String str2) {
    }

    public void resultBackground(Uri uri) {
    }

    public void resultEditImage(Uri uri) {
    }

    public void resultEditTextItem(String str, int i, String str2) {
    }

    public void resultFromPhotoEditor(Uri uri) {
    }

    public void resultPickMultipleImages(Uri[] uriArr) {
    }


    public void resultSticker(Uri uri) {
    }

    public void resultStickers(Uri[] uriArr) {
    }

    public Dialog getBackgroundImageDialog() {
        return this.mAddImageDialog;
    }

    public void onCameraButtonClick() {
        getImageFromCamera();
        mAddImageDialog.dismiss();
    }

    public void onGalleryButtonClick() {
        pickImageFromGallery();
        mAddImageDialog.dismiss();
    }

    public void onStickerButtonClick() {
        pickSticker();
        mAddImageDialog.dismiss();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        if (bundle != null) {
//            mCapturedImageUri = bundle.getParcelable("mCapturedImageUri");
//        }
        mAddImageDialog = DialogUtils.createAddImageDialog(this, this, false);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("mCapturedImageUri", this.mCapturedImageUri);
    }

    public void onActivityResult(int requestCode, int i2, Intent intent) {
        super.onActivityResult(requestCode, i2, intent);
        if (i2 != RESULT_OK) {
            return;
        }
        if (requestCode != REQUEST_EDIT_TEXT_ITEM) {
            int i3 = 0;
            if (requestCode == PICK_MULTIPLE) {
                ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("selectedImages");
                if (stringArrayListExtra != null && stringArrayListExtra.size() > 0) {
                    int size = stringArrayListExtra.size();
                    Uri[] uriArr = new Uri[size];
                    while (i3 < size) {
                        uriArr[i3] = Uri.fromFile(new File(stringArrayListExtra.get(i3)));
                        i3++;
                    }
                    resultPickMultipleImages(uriArr);
                }
            } else if (requestCode == REQUEST_EDIT_IMAGE) {
                resultEditImage(intent.getData());
            } else if (requestCode == PICK_BACKGROUND) {
                ArrayList<String> stringArrayListExtra2 = intent.getStringArrayListExtra("selectedImages");
                if (stringArrayListExtra2 != null && stringArrayListExtra2.size() > 0) {
                    resultBackground(Uri.fromFile(new File(stringArrayListExtra2.get(0))));
                }
            } else if (requestCode == PICK_STICKER) {
                ArrayList<String> stringArrayListExtra3 = intent.getStringArrayListExtra("selectedImages");
                if (stringArrayListExtra3 != null && stringArrayListExtra3.size() > 0) {
                    int size2 = stringArrayListExtra3.size();
                    Uri[] uriArr2 = new Uri[size2];
                    while (i3 < size2) {
                        uriArr2[i3] = Uri.fromFile(new File(stringArrayListExtra3.get(i3)));
                        i3++;
                    }
                    resultStickers(uriArr2);
                }
            } else if (requestCode == CAPTURE_IMAGE) {
                Uri uri = this.mCapturedImageUri;
                if (uri != null) {
                    startPhotoEditor(uri, true);
                }
            } else if (requestCode != PICK_IMAGE) {
                if (requestCode == REQUEST_PHOTO_EDITOR) {
                    resultFromPhotoEditor(intent.getData());
                } else if (requestCode == 10000) {
                }
            } else if (intent != null && intent.getData() != null) {
                resultFromPhotoEditor(intent.getData());
            }
        }
    }

    public void requestPhoto() {
        mAddImageDialog.show();
    }

    private void startPhotoEditor(Uri uri, boolean z) {
        Intent intent = new Intent(this, ImageProcessingActivity.class);
        intent.putExtra(ImageProcessingActivity.IMAGE_URI_KEY, uri);
        if (z) {
            intent.putExtra(ImageProcessingActivity.ROTATION_KEY, 90);
        }
        startActivityForResult(intent, REQUEST_PHOTO_EDITOR);
    }

    public void pickImageFromGallery() {
        try {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType(FileUtils.MIME_TYPE_IMAGE);
            startActivityForResult(intent, PICK_IMAGE);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.setType(FileUtils.MIME_TYPE_IMAGE);
                startActivityForResult(intent2, PICK_IMAGE);
            } catch (Exception e2) {
                e2.printStackTrace();
                Toast.makeText(this, e2.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getImageFromCamera() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM", "image_" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        this.mCapturedImageUri = Uri.fromFile(file);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", this.mCapturedImageUri);
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    public void pickSticker() {
    }

    public void addTextItem() {
    }

    public void editTextItem(String str, String str2, int i) {
    }

    public void pickBackground() {

    }

    public void requestEditingImage(Uri uri) {
        Intent intent = new Intent(this, ImageProcessingActivity.class);
        intent.putExtra(ImageProcessingActivity.IMAGE_URI_KEY, uri);
        startActivityForResult(intent, REQUEST_EDIT_IMAGE);
    }
}