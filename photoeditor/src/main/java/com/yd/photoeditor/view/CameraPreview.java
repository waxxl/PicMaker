package com.yd.photoeditor.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;
import java.util.List;

public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {
    private final String TAG = CameraPreview.class.getSimpleName();
    Camera mCamera;
    SurfaceHolder mHolder;
    Camera.Size mPreviewSize;
    List<Camera.Size> mSupportedPreviewSizes;
    SurfaceView mSurfaceView;

    public CameraPreview(Context context, SurfaceView surfaceView) {
        super(context);
        this.mSurfaceView = surfaceView;
        this.mHolder = this.mSurfaceView.getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
        Camera camera2 = this.mCamera;
        if (camera2 != null) {
            try {
                camera2.setPreviewDisplay(this.mHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mSupportedPreviewSizes = this.mCamera.getParameters().getSupportedPreviewSizes();
            requestLayout();
            Camera.Parameters parameters = this.mCamera.getParameters();
            if (parameters.getSupportedFocusModes().contains("auto")) {
                parameters.setFocusMode("auto");
                this.mCamera.setParameters(parameters);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int resolveSize = resolveSize(getSuggestedMinimumWidth(), i);
        int resolveSize2 = resolveSize(getSuggestedMinimumHeight(), i2);
        setMeasuredDimension(resolveSize, resolveSize2);
        List<Camera.Size> list = this.mSupportedPreviewSizes;
        if (list != null) {
            this.mPreviewSize = getOptimalPreviewSize(list, resolveSize, resolveSize2);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (z && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int i7 = i3 - i;
            int i8 = i4 - i2;
            Camera.Size size = this.mPreviewSize;
            if (size != null) {
                i6 = size.width;
                i5 = this.mPreviewSize.height;
            } else {
                i6 = i7;
                i5 = i8;
            }
            int i9 = i7 * i5;
            int i10 = i8 * i6;
            if (i9 > i10) {
                int i11 = i10 / i5;
                childAt.layout((i7 - i11) / 2, 0, (i7 + i11) / 2, i8);
                return;
            }
            int i12 = i9 / i6;
            childAt.layout(0, (i8 - i12) / 2, i7, (i8 + i12) / 2);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            if (this.mCamera != null) {
                this.mCamera.setPreviewDisplay(surfaceHolder);
            }
        } catch (IOException e) {
            Log.e(this.TAG, "IOException caused by setPreviewDisplay()", e);
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Camera camera = this.mCamera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(this.mPreviewSize.width, this.mPreviewSize.height);
            requestLayout();
            this.mCamera.setParameters(parameters);
            this.mCamera.startPreview();
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.stopPreview();
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> list, int i, int i2) {
        int i3 = i2;
        double d = (double) i;
        double d2 = (double) i3;
        Double.isNaN(d);
        Double.isNaN(d2);
        double d3 = d / d2;
        Camera.Size size = null;
        if (list == null) {
            return null;
        }
        double d4 = Double.MAX_VALUE;
        double d5 = Double.MAX_VALUE;
        for (Camera.Size next : list) {
            double d6 = (double) next.width;
            double d7 = (double) next.height;
            Double.isNaN(d6);
            Double.isNaN(d7);
            if (Math.abs((d6 / d7) - d3) <= 0.1d && ((double) Math.abs(next.height - i3)) < d5) {
                d5 = (double) Math.abs(next.height - i3);
                size = next;
            }
        }
        if (size == null) {
            for (Camera.Size next2 : list) {
                if (((double) Math.abs(next2.height - i3)) < d4) {
                    size = next2;
                    d4 = (double) Math.abs(next2.height - i3);
                }
            }
        }
        return size;
    }
}
