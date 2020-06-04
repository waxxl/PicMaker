package com.yd.photoeditor.actions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.yd.photoeditor.listener.OnDoneActionsClickListener;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;
import com.yd.photoeditor.utils.PhotoUtils;
import com.yd.photoeditor.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;

public abstract class BaseAction implements OnDoneActionsClickListener {
    protected ImageProcessingActivity mActivity;
    private boolean mAttached = false;
    protected View mRootActionView;

    public abstract void apply(boolean z);

    public abstract String getActionName();

    public abstract View inflateMenuView();

    public void onActivityDestroy() {
    }

    public void onActivityPause() {
    }

    public void onActivityResume() {
    }

    public void onDetach() {
    }

    public void onInit() {
    }

    public BaseAction(ImageProcessingActivity imageProcessingActivity) {
        mActivity = imageProcessingActivity;
        onInit();
        mRootActionView = inflateMenuView();
    }

    public void attach() {
        mActivity.setDoneActionsClickListener(this);
        if (mRootActionView != null) {
            BaseAction currentAction = mActivity.getCurrentAction();
            if (currentAction != null) {
                currentAction.onDetach();
                currentAction.mAttached = false;
            }
            mActivity.setCurrentAction(this);
            mAttached = true;
        }
    }

    public boolean isAttached() {
        return mAttached;
    }

    public void done() {
        new AsyncTask<Void, Void, File>() {
            public void onPreExecute() {
                super.onPreExecute();
                mActivity.hideActions();
                mActivity.showProgress(true);
            }

            public File doInBackground(Void... voidArr) {
                if (mActivity.isEditingImage() && mActivity.getEditingImagePath() != null && mActivity.getEditingImagePath().length() > 0) {
                    String editingImagePath = mActivity.getEditingImagePath();
                    File file = new File(editingImagePath);
                    file.delete();
                    new File(editingImagePath.substring(0, editingImagePath.length() - 4).concat(PhotoUtils.EDITED_WHITE_IMAGE_SUFFIX)).delete();
                    new File(Utils.EDITED_IMAGE_THUMBNAIL_FOLDER, file.getName()).delete();
                }
                long currentTimeMillis = System.currentTimeMillis();
                File file2 = new File(Utils.EDITED_IMAGE_FOLDER.concat("/edited_image_") + currentTimeMillis + ".png");
                File parentFile = file2.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                try {
                    mActivity.getImage().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file2));
                    mActivity.getImage().recycle();
                    System.gc();
                    return file2;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void onPostExecute(File file) {
                super.onPostExecute(file);
                if (file != null) {
                    Intent intent = new Intent();
                    intent.setData(Uri.fromFile(file));
                    intent.putExtra(ImageProcessingActivity.EXTRA_RETURN_EDITED_IMAGE_PATH, file.getAbsolutePath());
                    mActivity.setResult(Activity.RESULT_OK, intent);
                    mActivity.finish();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void saveInstanceState(Bundle bundle) {
        bundle.putBoolean("actions".concat(getActionName()).concat("mAttached"), this.mAttached);
    }

    public void restoreInstanceState(Bundle bundle) {
        this.mAttached = bundle.getBoolean("actions".concat(getActionName()).concat("mAttached"), this.mAttached);
    }

    public void onDoneButtonClick() {
        apply(true);
    }

    public void onApplyButtonClick() {
        apply(false);
    }

    public void onClicked() {

    }
}
