package com.yd.picmaker.manager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.Listener.AddTextListener;
import com.yd.picmaker.Listener.EditEventListener;
import com.yd.picmaker.Listener.FreeEditInterface;
import com.yd.picmaker.Listener.OnItemClickListener;
import com.yd.picmaker.PCApplication;
import com.yd.picmaker.R;
import com.yd.picmaker.activity.FreePhotoEditActivity;
import com.yd.picmaker.activity.GalleryAlbumActivity;
import com.yd.picmaker.activity.SaveOneActivity;
import com.yd.picmaker.adapter.FreeTabEditAdapter;
import com.yd.picmaker.adapter.SelectStickerAdapter;
import com.yd.picmaker.dialog.AddTextDialog;
import com.yd.picmaker.model.StickerSaveData;
import com.yd.picmaker.sticker.DrawableSticker;
import com.yd.picmaker.sticker.Sticker;
import com.yd.picmaker.sticker.StickerLayout;
import com.yd.picmaker.sticker.StickerView;
import com.yd.picmaker.util.Constants;
import com.yd.picmaker.util.FileUtils;
import com.yd.picmaker.util.PhotoUtils;
import com.yd.photoeditor.ui.activity.ImageProcessingActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FreePhotoUiManager implements StickerView.OnStickerOperationListener, View.OnClickListener, EditEventListener {
    private static final String TAG = "PhotoUiManager";
    private static final int PICK_MULTIPLE_IMAGE_REQUEST_CODE = 0x01;
    private static final int EDIT_STICKER_CODE = 0x02;
    private static final int BACKGROUND_TAB = 0;
    private static final int FILTERS_TAB = 1;
    private static final int PICTURES_TAB = 2;
    private static final int TEXT_TAB = 3;
    private static final int STICKER_TAB = 4;
    private FreePhotoEditActivity mFreeEditPhotoActivity;
    private FrameLayout mSelectFrameLayout;
    private View mTabLayout, mFilterRL;
    private RecyclerView mBacksRecycler, mFilterRecycler, mStickerRecycler;
    private StickerLayout mStickerLayout;
    private SeekBar mFilterSeekBar;
    private ImageView mFilterLayout;
    private FreeTabEditAdapter mBackgroundEditAdapter;
    private FreeTabEditAdapter mFilterAdapter;
    private SelectStickerAdapter mStickerAdapter;
    private AddTextDialog addTextDialog;
    private ImageView mCloseRecyclerIV;
    private FreeEditInterface mListener;
    private int mCurrentTAB;

    public FreePhotoUiManager(FreePhotoEditActivity activity, FreeEditInterface listener) {
        mFreeEditPhotoActivity = activity;
        mListener = listener;
    }

    public void initView() {
        mTabLayout = mFreeEditPhotoActivity.findViewById(R.id.tab_mode);
        mFilterLayout = mFreeEditPhotoActivity.findViewById(R.id.filter_layout);
        mFilterSeekBar = mFreeEditPhotoActivity.findViewById(R.id.filter_seek_bar);
        mSelectFrameLayout = mFreeEditPhotoActivity.findViewById(R.id.select_recycler_frame);
        mStickerLayout = mFreeEditPhotoActivity.findViewById(R.id.photo_free_edit_frame);
        mBacksRecycler = mFreeEditPhotoActivity.findViewById(R.id.background_recycler);
        mFilterRecycler = mFreeEditPhotoActivity.findViewById(R.id.filter_recycler);
        mFilterRL = mFreeEditPhotoActivity.findViewById(R.id.filter_rl);
        mStickerRecycler = mFreeEditPhotoActivity.findViewById(R.id.sticker_recycler);
        mStickerLayout.setOnStickerOperationListener(this);
        mStickerLayout.setEditListener(this);
        if (null == mBackgroundEditAdapter) {
            mBackgroundEditAdapter = new FreeTabEditAdapter(Constants.BACKGROUNDS);
        }
        mBacksRecycler.setHasFixedSize(false);
        mBacksRecycler.setLayoutManager(new LinearLayoutManager(mFreeEditPhotoActivity, RecyclerView.HORIZONTAL, false));
        mBacksRecycler.setAdapter(mBackgroundEditAdapter);
        mBackgroundEditAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mStickerLayout.setBackgroundResource(Constants.BACKGROUNDS[pos]);
            }
        });

        if (null == mFilterAdapter) {
            mFilterAdapter = new FreeTabEditAdapter(Constants.FILTERS);
        }
        mFilterRecycler.setHasFixedSize(false);
        mFilterRecycler.setLayoutManager(new LinearLayoutManager(mFreeEditPhotoActivity, RecyclerView.HORIZONTAL, false));
        mFilterAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mFilterLayout.setImageResource(Constants.FILTERS[position]);
                mFilterLayout.setImageAlpha(120);
            }
        });
        mFilterRecycler.setAdapter(mFilterAdapter);
        mStickerRecycler.setHasFixedSize(false);
        mStickerRecycler.setLayoutManager(new LinearLayoutManager(mFreeEditPhotoActivity, RecyclerView.HORIZONTAL, false));
        if (null == mStickerAdapter) {
            mStickerAdapter = new SelectStickerAdapter();
        }
        mStickerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mStickerLayout.addImageSticker(Constants.STICKERS[pos]);
            }
        });
        mStickerRecycler.setAdapter(mStickerAdapter);
        mFilterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onProgressChange(seekBar, progress, fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setCurrentTab(int currentTab) {
        mCurrentTAB = currentTab;
    }

    public void initTab() {
        if (mTabLayout == null) {
            Log.e(TAG, "initTab mTabLayout == null");
            return;
        }
        TextView backgrounds = mTabLayout.findViewById(R.id.tab_background);
        TextView filters = mTabLayout.findViewById(R.id.tab_filters);
        TextView pictures = mTabLayout.findViewById(R.id.tab_pic);
        TextView texts = mTabLayout.findViewById(R.id.tab_texts);
        TextView stickers = mTabLayout.findViewById(R.id.tab_stickers);
        ImageView func = mFreeEditPhotoActivity.getFunc();
        backgrounds.setOnClickListener(this);
        filters.setOnClickListener(this);
        pictures.setOnClickListener(this::onClick);
        texts.setOnClickListener(this::onClick);
        stickers.setOnClickListener(this::onClick);
        func.setOnClickListener(this::onClick);
    }

    private void selectTabPosition(int position) {
        switch (position) {
            case 0:
                mCurrentTAB = 0;

                mBacksRecycler.setVisibility(View.VISIBLE);
                mFilterRL.setVisibility(View.GONE);
                mFilterRecycler.setVisibility(View.GONE);
                mStickerRecycler.setVisibility(View.GONE);
                mSelectFrameLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                mCurrentTAB = 1;
                mFilterRL.setVisibility(View.VISIBLE);
                mFilterRecycler.setVisibility(View.VISIBLE);
                mBacksRecycler.setVisibility(View.GONE);
                mStickerRecycler.setVisibility(View.GONE);
                mSelectFrameLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                mCurrentTAB = 2;
                pickMultipleImageFromGallery();
                mSelectFrameLayout.setVisibility(View.GONE);
                break;
            case 3:
                mCurrentTAB = 3;
                if (addTextDialog == null) {
                    AddTextDialog.Builder builder = new AddTextDialog.Builder(mFreeEditPhotoActivity);
                    builder.setListener(new AddTextListener() {
                        @Override
                        public void onOkClick(String s) {
                            if (addTextDialog != null && addTextDialog.isShowing()) {
                                if (!TextUtils.isEmpty(s)) {
                                    mStickerLayout.addTextSticker(s);
                                }
                                addTextDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelClick() {
                            if (addTextDialog != null && addTextDialog.isShowing()) {
                                addTextDialog.dismiss();
                            }
                        }
                    });
                    addTextDialog = builder.create();
                    addTextDialog.setCancelable(false);
                }
                if (!addTextDialog.isShowing()) {
                    addTextDialog.show();
                }
                break;
            case 4:
                mStickerRecycler.setVisibility(View.VISIBLE);
                mFilterRecycler.setVisibility(View.GONE);
                mFilterRL.setVisibility(View.GONE);
                mBacksRecycler.setVisibility(View.GONE);
                mSelectFrameLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void pickMultipleImageFromGallery() {
        Intent intent = new Intent(mFreeEditPhotoActivity, GalleryAlbumActivity.class);
        intent.putExtra("maxImageCount", 20);
        intent.putExtra("isMaxImageCount", true);
        mFreeEditPhotoActivity.startActivityForResult(intent, PICK_MULTIPLE_IMAGE_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_MULTIPLE_IMAGE_REQUEST_CODE && data != null) {
                ArrayList<String> datas = data.getStringArrayListExtra(GalleryAlbumActivity.SELECT_ITEMS);

                if (datas != null) {
                    Uri[] uris = new Uri[datas.size()];
                    for (int i = 0; i < datas.size(); i++) {
                        Uri uri = Uri.fromFile(new File(datas.get(i)));
                        uris[i] = uri;
                    }
                    getMultipleImagesAsync(uris, mFreeEditPhotoActivity);
                }
            } else if(requestCode == EDIT_STICKER_CODE) {
                getMultipleImagesAsync(new Uri[]{data.getData()}, mFreeEditPhotoActivity);
            }
        }
    }

    public void onProgressChange(SeekBar seekBar, int alpha, boolean fromUser) {
        int id = seekBar.getId();
        if (id == R.id.filter_seek_bar) {
            mFilterLayout.setImageAlpha(alpha);
        }
    }


    @Override
    public void onStickerAdded(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerClicked(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerDeleted(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerDragFinished(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerTouchedDown(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerZoomFinished(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerFlipped(@NonNull Sticker sticker) {

    }

    @Override
    public void onStickerDoubleTapped(@NonNull Sticker sticker) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_background:
                selectTabPosition(0);
                mListener.onTabSelect(0);
                break;
            case R.id.tab_filters:
                selectTabPosition(1);
                mListener.onTabSelect(1);
                break;
            case R.id.tab_pic:
                selectTabPosition(2);
                mListener.onTabSelect(2);
                break;
            case R.id.tab_texts:
                selectTabPosition(3);
                mListener.onTabSelect(3);
                break;
            case R.id.tab_stickers:
                selectTabPosition(4);
                mListener.onTabSelect(4);
                break;
            case R.id.func:
                mStickerLayout.invalidate();
                mStickerLayout.setDrawingCacheEnabled(true);
                mStickerLayout.buildDrawingCache();

                try {
                    Bitmap bitmap = mStickerLayout.getDrawingCache();
                    if(bitmap == null) {
                        Log.e(TAG, "saveImage failed bitmap == null");
                        return;
                    }
                    saveImage(bitmap, mFreeEditPhotoActivity);
                } catch (Exception e) {
                    Log.e(TAG, "saveImage failed");
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void getMultipleImagesAsync(final Uri[] uriArr, final Context context) {
        new AsyncTask<Void, Void, Bitmap[]>() {
            private ProgressDialog dialog;

            public void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.requestWindowFeature(1);
                dialog.setMessage("Importing Images...");
                dialog.show();
            }

            public Bitmap[] doInBackground(Void... voidArr) {
                Bitmap[] bitmapArr = new Bitmap[uriArr.length];
                int i = 0;
                while (true) {
                    if (i >= uriArr.length) {
                        return bitmapArr;
                    }
                    bitmapArr[i] = PhotoUtils.getResizedBitmap(uriArr[i], context);
                    i++;
                }
            }

            public void onPostExecute(Bitmap[] bitmapArr) {
                for (Bitmap addImageSticker : bitmapArr) {
                    mStickerLayout.addImageSticker(addImageSticker);
                }
                dialog.dismiss();
            }
        }.execute(new Void[0]);
    }

    private void saveTempImage(final Bitmap bitmap, final Activity activity) throws IOException {
        new AsyncTask<Void, Void, File>() {
            private ProgressDialog dialog;

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                this.dialog = new ProgressDialog(activity);
                this.dialog.requestWindowFeature(1);
                this.dialog.setMessage("Getting Editor Ready...");
                this.dialog.show();
            }

            /* access modifiers changed from: protected */
            public File doInBackground(Void... voidArr) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PTempFiles");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(file, System.currentTimeMillis() + ".png");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException unused) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file2;
            }

            public void onPostExecute(File file) {
                this.dialog.dismiss();
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(activity, ImageProcessingActivity.class);
                intent.putExtra(ImageProcessingActivity.IMAGE_URI_KEY, uri);
                activity.startActivityForResult(intent, EDIT_STICKER_CODE);
            }
        }.execute(new Void[0]);
    }

    private int i = 1;

    public void saveImage(final Bitmap bitmap, final Context context) throws IOException {
        new AsyncTask<Void, Void, File>() {
            private ProgressDialog dialog;

            public void onPreExecute() {
                dialog = new ProgressDialog(context);
                dialog.requestWindowFeature(1);
                dialog.setMessage("Saving...");
                dialog.show();
            }

            public File doInBackground(Void... voidArr) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "StickerFiles");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2;
                do{
                    String radomFileName = FileUtils.createFileName();
                    file2 = new File(file, radomFileName);
                } while (file2.exists());
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    PCApplication.getDaoSession().insert(new StickerSaveData(i++, file2.getAbsolutePath()));
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file2;
            }

            public void onPostExecute(File file) {
                dialog.dismiss();
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(context, SaveOneActivity.class);
                intent.putExtra(SaveOneActivity.IMAGE_URI_KEY, uri);
                context.startActivity(intent);
            }
        }.execute(new Void[0]);
    }

    @Override
    public void onImageEdit() {
        try {
            if(((BitmapDrawable) mStickerLayout.getCurrentSticker().getDrawable()).getBitmap() != null) {
                saveTempImage(((BitmapDrawable) mStickerLayout.getCurrentSticker().getDrawable()).getBitmap(), mFreeEditPhotoActivity);
            } else {
                Toast.makeText(mFreeEditPhotoActivity, R.string.edit_tint,Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
