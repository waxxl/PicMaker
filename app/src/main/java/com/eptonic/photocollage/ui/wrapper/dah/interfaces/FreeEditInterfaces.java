package com.eptonic.photocollage.ui.wrapper.dah.interfaces;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface FreeEditInterfaces {
    void initView2();

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
