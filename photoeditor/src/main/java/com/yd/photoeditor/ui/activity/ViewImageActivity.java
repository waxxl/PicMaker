//package com.yd.photoeditor.ui.activity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import com.yd.photoeditor.R;
//import com.yd.photoeditor.utils.DialogUtils;
//import com.yd.photoeditor.utils.ImageDecoder;
//import java.io.File;
//
//public class ViewImageActivity extends BaseAdActivity {
//    public static final String IMAGE_FILE_KEY = "imageFile";
//    private View mActionLayout;
//    private View mDeleteView;
//    private View mEditView;
//    /* access modifiers changed from: private */
//    public String mImagePath;
//    private ImageView mImageView;
//    private View mShareView;
//
//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        setContentView(R.layout.photo_editor_activity_view_image);
//        this.mImagePath = getIntent().getStringExtra(IMAGE_FILE_KEY);
//        this.mActionLayout = findViewById(R.id.actionLayout);
//        this.mShareView = findViewById(R.id.shareView);
//        this.mShareView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (ViewImageActivity.this.mImagePath != null && ViewImageActivity.this.mImagePath.length() > 0) {
//                    Uri parse = Uri.parse(ViewImageActivity.this.mImagePath);
//                    Intent intent = new Intent();
//                    intent.setAction("android.intent.action.SEND");
//                    intent.setType("image/png");
//                    intent.addFlags(1);
//                    intent.putExtra("android.intent.extra.STREAM", parse);
//                    ViewImageActivity.this.startActivity(intent);
//                }
//            }
//        });
//        this.mEditView = findViewById(R.id.editView);
//        this.mEditView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(ViewImageActivity.this, ImageProcessingActivity.class);
//                intent.putExtra(ImageProcessingActivity.IMAGE_URI_KEY, Uri.fromFile(new File(ViewImageActivity.this.mImagePath)));
//                intent.putExtra(ImageProcessingActivity.IS_EDITING_IMAGE_KEY, true);
//                ViewImageActivity.this.startActivity(intent);
//                ViewImageActivity.this.finish();
//            }
//        });
//        this.mDeleteView = findViewById(R.id.deleteView);
//        this.mDeleteView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                DialogUtils.showCoolConfirmDialog(ViewImageActivity.this, R.string.photo_editor_app_name, R.string.photo_editor_confirm_delete_image, new DialogUtils.ConfirmDialogOnClickListener() {
//                    public void onCancelButtonOnClick() {
//                    }
//
//                    public void onOKButtonOnClick() {
//                        new File(ViewImageActivity.this.mImagePath).delete();
//                        ViewImageActivity.this.finish();
//                    }
//                });
//            }
//        });
//        this.mImageView = (ImageView) findViewById(R.id.imageView);
//        String str = this.mImagePath;
//        if (str != null) {
//            this.mImageView.setImageBitmap(ImageDecoder.decodeFileToBitmap(str));
//        }
//        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                ViewImageActivity.this.finish();
//            }
//        });
//    }
//
//    public void onBackPressed() {
//        finish();
//    }
//}
