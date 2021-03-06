package com.yd.photoeditor.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.yd.photoeditor.listener.ApplyFilterListener;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

public class ApplyFilterTask extends AsyncTask<Void, Void, Bitmap> {
    private final ImageProcessingActivity mActivity;
    private final ApplyFilterListener mListener;

    public ApplyFilterTask(ImageProcessingActivity imageProcessingActivity, ApplyFilterListener applyFilterListener) {
        this.mActivity = imageProcessingActivity;
        this.mListener = applyFilterListener;
    }

    public void onPreExecute() {
        super.onPreExecute();
        this.mActivity.hideActions();
        this.mActivity.showProgress(true);
    }

    public Bitmap doInBackground(Void... voidArr) {
        return mListener.applyFilter();
    }

    public void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            mActivity.setImage(bitmap, true);
        }
        mActivity.selectAction(0);
        mActivity.showProgress(false);
        mActivity.showAllMenus();
        mListener.onFinishFiltering();
    }
}
