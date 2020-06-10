package com.eptonic.photocollage.ui.wrapper.dah.interfaces;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.eptonic.photocollage.model.TemplateItem;

public interface TemplateInterface {
    void initView();

    void loadTemplateImages();

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    void onTemplateItemClick(TemplateItem templateItem);
}
