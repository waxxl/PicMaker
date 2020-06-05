package com.yd.photoeditor.actions;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yd.photoeditor.R;
import com.yd.photoeditor.listener.ApplyFilterListener;
import com.yd.photoeditor.task.ApplyFilterTask;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;
import com.yd.photoeditor.view.OrientationImageView;

public class RotationAction extends BaseAction {
    private static final String ROTATION_ACTION_PREF_NAME = "rotationActionPref";
    private static final String SHOW_GUIDE_NAME = "showGuide";
    /* access modifiers changed from: private */
    public boolean mFirstApplied = false;
    private boolean mFirstAttached = false;
    private View mFlipHorView;
    private View mFlipVerView;
    /* access modifiers changed from: private */
    public OrientationImageView mOrientationImageView;
    private View mOrientationLayout;
    private boolean mRestoreOldState = false;
    private View mRotateLeftView;
    private View mRotateRightView;
    /* access modifiers changed from: private */
    public SharedPreferences mRotationActionPref;

    public String getActionName() {
        return "RotationAction";
    }

    public RotationAction(ImageProcessingActivity imageProcessingActivity) {
        super(imageProcessingActivity);
        this.mRotationActionPref = imageProcessingActivity.getSharedPreferences(ROTATION_ACTION_PREF_NAME, 0);
    }

    public void saveInstanceState(Bundle bundle) {
        super.saveInstanceState(bundle);
        bundle.putBoolean("com.yd.photoeditor.actions.RotationAction.mFirstAttached", this.mFirstAttached);
        this.mOrientationImageView.saveInstanceState(bundle);
    }

    public void restoreInstanceState(Bundle bundle) {
        super.restoreInstanceState(bundle);
        this.mFirstAttached = bundle.getBoolean("com.yd.photoeditor.actions.RotationAction.mFirstAttached", this.mFirstAttached);
        if (this.mFirstAttached) {
            this.mOrientationImageView.restoreInstanceState(bundle);
            this.mRestoreOldState = true;
            return;
        }
        this.mRestoreOldState = false;
    }

    public void reset() {
        this.mRestoreOldState = false;
        this.mFirstAttached = false;
    }

    public void apply(final boolean z) {
        if (isAttached()) {
            new ApplyFilterTask(this.mActivity, new ApplyFilterListener() {
                public void onFinishFiltering() {
                    reset();
                    if (!(mOrientationImageView.getAngle() % 180.0f == 0.0f || mActivity.getCropAction() == null)) {
                        mActivity.getCropAction().reset();
                    }
                    if (!mFirstApplied && Build.VERSION.SDK_INT > 19) {
                        Bitmap createBitmap = Bitmap.createBitmap(mActivity.getImageWidth(), mActivity.getImageHeight(), Bitmap.Config.ARGB_8888);
                        new Canvas(createBitmap).drawBitmap(mActivity.getImage(), 0.0f, 0.0f, new Paint());
                        mActivity.setImage(createBitmap, true);
                        mFirstApplied = true;
                    }
                    if (z) {
                        done();
                    }
                }

                public Bitmap applyFilter() {
                    return RotationAction.this.mOrientationImageView.applyTransform();
                }
            }).execute();
        }
    }

    public View inflateMenuView() {
        LayoutInflater from = LayoutInflater.from(this.mActivity);
        mRootActionView = from.inflate(R.layout.photo_editor_action_rotation, null);
        mRotateLeftView = this.mRootActionView.findViewById(R.id.leftRotate);
        mRotateLeftView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RotationAction.this.rotateLeft();
                RotationAction.this.onClicked();
            }
        });
        mRotateRightView = this.mRootActionView.findViewById(R.id.rightRotate);
        mRotateRightView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RotationAction.this.rotateRight();
                RotationAction.this.onClicked();
            }
        });
        this.mFlipHorView = this.mRootActionView.findViewById(R.id.flipHor);
        this.mFlipHorView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RotationAction.this.flipHor();
                RotationAction.this.onClicked();
            }
        });
        this.mFlipVerView = this.mRootActionView.findViewById(R.id.flipVer);
        this.mFlipVerView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RotationAction.this.flipVer();
                RotationAction.this.onClicked();
            }
        });
        this.mOrientationLayout = from.inflate(R.layout.photo_editor_orientation, null);
        this.mOrientationImageView = this.mOrientationLayout.findViewById(R.id.orientationView);
        return this.mRootActionView;
    }

    public void attach() {
        super.attach();
        this.mActivity.attachMaskView(this.mOrientationLayout);
        if (this.mRestoreOldState || this.mFirstAttached) {
            this.mOrientationImageView.setImage(this.mActivity.getImage());
            this.mRestoreOldState = false;
        } else {
            this.mOrientationImageView.init(this.mActivity.getImage(), this.mActivity.getPhotoViewWidth(), this.mActivity.getPhotoViewHeight());
        }
        this.mOrientationImageView.post(new Runnable() {
            public void run() {
                RotationAction.this.mActivity.getImageProcessingView().setVisibility(View.GONE);
            }
        });
        this.mFirstAttached = true;
    }

    public void onActivityResume() {
        super.onActivityResume();
        if (isAttached() && this.mFirstAttached) {
            this.mActivity.attachMaskView(this.mOrientationLayout);
            this.mOrientationImageView.invalidate();
            this.mOrientationImageView.post(new Runnable() {
                public void run() {
                    RotationAction.this.mActivity.getImageProcessingView().setVisibility(View.GONE);
                }
            });
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mActivity.getImageProcessingView().setVisibility(View.VISIBLE);
    }

    public void rotateLeft() {
        this.mOrientationImageView.rotate(-90.0f);
    }

    public void rotateRight() {
        this.mOrientationImageView.rotate(90.0f);
    }

    public void flipVer() {
        this.mOrientationImageView.flip(1);
    }

    public void flipHor() {
        this.mOrientationImageView.flip(2);
    }
}
