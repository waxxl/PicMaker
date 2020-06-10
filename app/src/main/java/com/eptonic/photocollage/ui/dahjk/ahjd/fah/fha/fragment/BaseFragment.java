package com.eptonic.photocollage.ui.dahjk.ahjd.fah.fha.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eptonic.photocollage.R;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.GalleryActivity;
import com.eptonic.photocollage.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class BaseFragment extends Fragment {
    protected static final int BACKGROUND_ITEM = 0;
    protected static final int CAPTURE_IMAGE_REQUEST_CODE = 997;
    protected static final String CAPTURE_TITLE = "capture.jpg";
    public static final int MAX_NEEDED_PHOTOS = 20;
    protected static final int NORMAL_IMAGE_ITEM = 2;
    protected static final int PICK_BACKGROUND_REQUEST_CODE = 995;
    protected static final int PICK_IMAGE_REQUEST_CODE = 998;
    protected static final int PICK_MULTIPLE_IMAGE_REQUEST_CODE = 993;
    protected static final int PICK_STICKER_REQUEST_CODE = 996;
    protected static final int REQUEST_ADD_TEXT_ITEM = 1000;
    protected static final int REQUEST_EDIT_IMAGE = 994;
    protected static final int REQUEST_EDIT_TEXT_ITEM = 992;
    protected static final int REQUEST_PHOTO_EDITOR_CODE = 999;
    protected static final int STICKER_ITEM = 1;
    protected Activity mActivity;
    private String mTitle = null;

    public void resultAddTextItem(String str, int i, String str2) {
    }

    /* access modifiers changed from: protected */
    public void resultBackground(Uri uri) {
    }

    /* access modifiers changed from: protected */
    public void resultEditImage(Uri uri) {
    }

    /* access modifiers changed from: protected */
    public void resultEditTextItem(String str, int i, String str2) {
    }

    /* access modifiers changed from: protected */
    public void resultFromPhotoEditor(Uri uri) {
    }

    public void resultPickMultipleImages(Uri[] uriArr) {
    }

    /* access modifiers changed from: protected */
    public void resultSticker(Uri uri) {
    }

    /* access modifiers changed from: protected */
    public void resultStickers(Uri[] uriArr) {
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        setTitle();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public void onStart() {
        super.onStart();
        setTitle();
    }

    /* access modifiers changed from: protected */
    public void setTitle() {
        setTitle(R.string.gallery_albums);
    }

    public void setTitle(String str) {
        ActionBar supportActionBar;
        mTitle = str;
        if (already()) {
            this.mActivity.setTitle(str);
            Activity activity = this.mActivity;
            if ((activity instanceof AppCompatActivity) && (supportActionBar = ((AppCompatActivity) activity).getSupportActionBar()) != null) {
                supportActionBar.setTitle(mTitle);
            }
        }
    }

    public void setTitle(int res) {
        this.mTitle = getString(res);
        setTitle(this.mTitle);
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void pickImageFromGallery() {
        try {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType(FileUtils.MIME_TYPE_IMAGE);
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.setType(FileUtils.MIME_TYPE_IMAGE);
                startActivityForResult(intent2, PICK_IMAGE_REQUEST_CODE);
            } catch (Exception e2) {
                e2.printStackTrace();
                Toast.makeText(mActivity.getApplicationContext(), mActivity.getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void pickMultipleImageFromGallery() {
        Intent intent = new Intent(getActivity(), GalleryActivity.class);
        intent.putExtra(GalleryActivity.MAX_IMAGE_COUNT, 20);
        intent.putExtra(GalleryActivity.IS_MAX_IMAGE_COUNT, true);
        startActivityForResult(intent, PICK_MULTIPLE_IMAGE_REQUEST_CODE);
    }

    public void pickMultipleImageFromGalleryNew(Activity activity) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.putExtra(GalleryActivity.MAX_IMAGE_COUNT, 20);
        intent.putExtra(GalleryActivity.IS_MAX_IMAGE_COUNT, true);
        activity.startActivityForResult(intent, PICK_MULTIPLE_IMAGE_REQUEST_CODE);
    }

    public void getImageFromCamera() {
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", getImageUri());
            startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.mActivity.getApplicationContext(), this.mActivity.getString(R.string.app_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/DCIM", CAPTURE_TITLE));
    }

//    public void pickSticker() {
//        if (already()) {
//            Intent intent = new Intent(getActivity(), DownloadedPackageActivityHelper.class);
//            intent.putExtra(DownloadedPackageFragment.EXTRA_PACKAGE_TYPE, "sticker");
//            startActivityForResult(intent, PICK_STICKER_REQUEST_CODE);
//        }
//    }
//
//    public void addTextItem() {
//        if (already()) {
//            startActivityForResult(new Intent(getActivity(), AddTextItemActivityHelper.class), 1000);
//        }
//    }
//
//    public void pickBackground() {
//        if (already()) {
//            Intent intent = new Intent(getActivity(), DownloadedPackageActivityHelper.class);
//            intent.putExtra(DownloadedPackageFragment.EXTRA_PACKAGE_TYPE, "background");
//            startActivityForResult(intent, PICK_BACKGROUND_REQUEST_CODE);
//        }
//    }
//
//    public void requestEditingImage(Uri uri) {
//        if (already()) {
//            Intent intent = new Intent(getActivity(), ImageProcessingActivity.class);
//            intent.putExtra(ImageProcessingActivity.IMAGE_URI_KEY, uri);
//            startActivityForResult(intent, REQUEST_EDIT_IMAGE);
//        }
//    }
//
//    public void editTextItem(String str, String str2, int i) {
//        Intent intent = new Intent(this.mActivity, AddTextItemActivityHelper.class);
//        intent.putExtra(AddTextItemActivityHelper.EXTRA_TEXT_CONTENT, str);
//        intent.putExtra(AddTextItemActivityHelper.EXTRA_TEXT_FONT, str2);
//        intent.putExtra(AddTextItemActivityHelper.EXTRA_TEXT_COLOR, i);
//        startActivityForResult(intent, REQUEST_EDIT_TEXT_ITEM);
//    }
//
//    /* access modifiers changed from: protected */
//
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if (i2 == -1) {
//            int i3 = 0;
//            switch (requestCode) {
//                case REQUEST_EDIT_TEXT_ITEM /*992*/:
//                    resultEditTextItem(intent.getStringExtra(AddTextItemActivityHelper.EXTRA_TEXT_CONTENT), intent.getIntExtra(AddTextItemActivityHelper.EXTRA_TEXT_COLOR, -16777216), intent.getStringExtra(AddTextItemActivityHelper.EXTRA_TEXT_FONT));
//                    return;
//                case PICK_MULTIPLE_IMAGE_REQUEST_CODE /*993*/:
//                    ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("selectedImages");
//                    if (stringArrayListExtra != null && stringArrayListExtra.size() > 0) {
//                        int size = stringArrayListExtra.size();
//                        Uri[] uriArr = new Uri[size];
//                        while (i3 < size) {
//                            uriArr[i3] = Uri.fromFile(new File(stringArrayListExtra.get(i3)));
//                            i3++;
//                        }
//                        resultPickMultipleImages(uriArr);
//                        return;
//                    }
//                    return;
//                case REQUEST_EDIT_IMAGE /*994*/:
//                    resultEditImage(intent.getData());
//                    return;
//                case PICK_BACKGROUND_REQUEST_CODE /*995*/:
//                    ArrayList<String> stringArrayListExtra2 = intent.getStringArrayListExtra("selectedImages");
//                    if (stringArrayListExtra2 != null && stringArrayListExtra2.size() > 0) {
//                        resultBackground(Uri.fromFile(new File(stringArrayListExtra2.get(0))));
//                        return;
//                    }
//                    return;
//                case PICK_STICKER_REQUEST_CODE /*996*/:
//                    ArrayList<String> stringArrayListExtra3 = intent.getStringArrayListExtra("selectedImages");
//                    if (stringArrayListExtra3 != null && stringArrayListExtra3.size() > 0) {
//                        int size2 = stringArrayListExtra3.size();
//                        Uri[] uriArr2 = new Uri[size2];
//                        while (i3 < size2) {
//                            uriArr2[i3] = Uri.fromFile(new File(stringArrayListExtra3.get(i3)));
//                            i3++;
//                        }
//                        resultStickers(uriArr2);
//                        return;
//                    }
//                    return;
//                case CAPTURE_IMAGE_REQUEST_CODE /*997*/:
//                    Uri imageUri = getImageUri();
//                    if (imageUri != null) {
//                        startPhotoEditor(imageUri, true);
//                        return;
//                    }
//                    return;
//                case PICK_IMAGE_REQUEST_CODE /*998*/:
//                    if (intent != null && intent.getData() != null) {
//                        startPhotoEditor(intent.getData(), false);
//                        return;
//                    }
//                    return;
//                case REQUEST_PHOTO_EDITOR_CODE /*999*/:
//                    resultFromPhotoEditor(intent.getData());
//                    return;
//                case 1000:
//                    resultAddTextItem(intent.getStringExtra(AddTextItemActivityHelper.EXTRA_TEXT_CONTENT), intent.getIntExtra(AddTextItemActivityHelper.EXTRA_TEXT_COLOR, -16777216), intent.getStringExtra(AddTextItemActivityHelper.EXTRA_TEXT_FONT));
//                    return;
//                default:
//                    return;
//            }
//        }
//    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.setHasAlpha(true);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null));
    }

    public Context getTheContext() {
        return getActivity().getApplicationContext();
    }

    public boolean already() {
        return isAdded() && getActivity() != null;
    }
}