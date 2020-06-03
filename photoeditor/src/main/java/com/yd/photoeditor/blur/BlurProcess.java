package com.yd.photoeditor.blur;

import android.graphics.Bitmap;

interface BlurProcess {
    Bitmap blur(Bitmap bitmap, float f);
}
