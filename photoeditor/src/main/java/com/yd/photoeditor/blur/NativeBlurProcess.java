package com.yd.photoeditor.blur;

import android.graphics.Bitmap;
import java.util.concurrent.Callable;

class NativeBlurProcess implements BlurProcess {
    /* access modifiers changed from: private */
    public static native void functionToBlur(Bitmap bitmap, int i, int i2, int i3, int i4);

    NativeBlurProcess() {
    }

    public Bitmap blur(Bitmap r13, float r14) {
        return null;
    }

    private static class NativeTask implements Callable<Void> {
        private final Bitmap mBitmapOut;
        private final int mCoreIndex;
        private final int mRadius;
        private final int mRound;
        private final int mTotalCores;

        public NativeTask(Bitmap bitmap, int i, int i2, int i3, int i4) {
            this.mBitmapOut = bitmap;
            this.mRadius = i;
            this.mTotalCores = i2;
            this.mCoreIndex = i3;
            this.mRound = i4;
        }

        public Void call() throws Exception {
            NativeBlurProcess.functionToBlur(this.mBitmapOut, this.mRadius, this.mTotalCores, this.mCoreIndex, this.mRound);
            return null;
        }
    }
}
