package com.eptonic.photocollage.ui.view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.eptonic.photocollage.R;
import com.eptonic.photocollage.model.ImageTemplate;
import com.eptonic.photocollage.ui.quickAction.QuickAction;
import com.eptonic.photocollage.ui.quickAction.QuickActionItem;
import com.eptonic.photocollage.ui.view.template.ItemImageView;
import com.eptonic.photocollage.ui.view.template.PhotoItem;
import com.eptonic.photocollage.util.ImageUtils;
import com.yd.photoeditor.vv.ImageDecoder;
import com.yd.photoeditor.vv.PhotoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PhotoLayout extends RelativeLayout implements ItemImageView.OnImageClickListener {
    private static final int ID_CANCEL = 4;
    private static final int ID_CHANGE = 2;
    private static final int ID_DELETE = 3;
    private static final int ID_EDIT = 1;
    private static final String TAG = PhotoLayout.class.getSimpleName();
    private Bitmap mBackgroundImage;
    public TransitionImageView mBackgroundImageView;
    public QuickAction mBackgroundQuickAction;
    private int mImageHeight;
    private int mImageWidth;
    private float mInternalScaleRatio = 1.0f;
    private List<ItemImageView> mItemImageViews;
    View.OnDragListener mOnDragListener = new View.OnDragListener() {
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            if (action == 3) {
                ItemImageView itemImageView = (ItemImageView) view;
                ItemImageView itemImageView2 = (ItemImageView) dragEvent.getLocalState();
                String str = "";
                String str2 = itemImageView.getPhotoItem() != null ? itemImageView.getPhotoItem().imagePath : str;
                String str3 = itemImageView2.getPhotoItem() != null ? itemImageView2.getPhotoItem().imagePath : str;
                if (str2 == null) {
                    str2 = str;
                }
                if (str3 != null) {
                    str = str3;
                }
                if (str2.equals(str)) {
                    return true;
                }
                itemImageView.swapImage(itemImageView2);
                return true;
            } else if (action == 5) {
                return true;
            } else if (action != 6) {
                return true;
            } else {
                return true;
            }
        }
    };
    public float mOutputScaleRatio = 1.0f;
    private List<PhotoItem> mPhotoItems;
    public ProgressBar mProgressBar;
    public QuickAction mQuickAction;
    public OnQuickActionClickListener mQuickActionClickListener;
    private Bitmap mTemplateImage;
    public int mViewHeight;
    public int mViewWidth;

    public interface OnQuickActionClickListener {
        void onChangeActionClick(ItemImageView itemImageView);

        void onChangeBackgroundActionClick(TransitionImageView transitionImageView);

        void onEditActionClick(ItemImageView itemImageView);
    }

    public static List<PhotoItem> parseImageTemplate(ImageTemplate imageTemplate) {
        ArrayList arrayList = new ArrayList();
        try {
            String[] split = imageTemplate.getChild().split(";");
            if (split != null) {
                for (String split2 : split) {
                    String[] split3 = split2.split(",");
                    if (split3 != null) {
                        PhotoItem photoItem = new PhotoItem();
                        photoItem.index = Integer.parseInt(split3[0]);
                        photoItem.x = (float) Integer.parseInt(split3[1]);
                        photoItem.y = (float) Integer.parseInt(split3[2]);
                        photoItem.maskPath = split3[3];
                        arrayList.add(photoItem);
                    }
                }
                Collections.sort(arrayList, new Comparator<PhotoItem>() {
                    public int compare(PhotoItem photoItem, PhotoItem photoItem2) {
                        return photoItem2.index - photoItem.index;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public PhotoLayout(Context context, ImageTemplate imageTemplate) {
        super(context);
        init(parseImageTemplate(imageTemplate), PhotoUtils.decodePNGImage(context, imageTemplate.getTemplate()));
    }

    public PhotoLayout(Context context, List<PhotoItem> list, Bitmap bitmap) {
        super(context);
        init(list, bitmap);
    }

    private void init(List<PhotoItem> list, Bitmap bitmap) {
        this.mPhotoItems = list;
        this.mTemplateImage = bitmap;
        this.mImageWidth = this.mTemplateImage.getWidth();
        this.mImageHeight = this.mTemplateImage.getHeight();
        this.mItemImageViews = new ArrayList();
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        createQuickAction();
    }

    public void setQuickActionClickListener(OnQuickActionClickListener onQuickActionClickListener) {
        this.mQuickActionClickListener = onQuickActionClickListener;
    }

    private void createQuickAction() {
        QuickActionItem quickActionItem = new QuickActionItem(1, getContext().getString(R.string.edit), getResources().getDrawable(R.drawable.edit));

        QuickActionItem quickActionItem3 = new QuickActionItem(3, getContext().getString(R.string.delete), getResources().getDrawable(R.drawable.delete));
        //QuickActionItem quickActionItem4 = new QuickActionItem(4, getContext().getString(R.string.cancel), getResources().getDrawable(R.drawable.colse));
        this.mQuickAction = new QuickAction(getContext(), 0);

        this.mQuickAction.addActionItem(quickActionItem);
        this.mQuickAction.addActionItem(quickActionItem3);
        //this.mQuickAction.addActionItem(quickActionItem4);
        this.mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            public void onItemClick(QuickAction quickAction, int i, int i2) {
                PhotoLayout.this.mQuickAction.getActionItem(i);
                PhotoLayout.this.mQuickAction.dismiss();
                if (i2 == 3) {
                    ((ItemImageView) PhotoLayout.this.mQuickAction.getAnchorView()).clearMainImage();
                } else if (i2 == 1) {
                    if (PhotoLayout.this.mQuickActionClickListener != null) {
                        PhotoLayout.this.mQuickActionClickListener.onEditActionClick((ItemImageView) PhotoLayout.this.mQuickAction.getAnchorView());
                    }
                } else if (i2 == 2 && PhotoLayout.this.mQuickActionClickListener != null) {
                    PhotoLayout.this.mQuickActionClickListener.onChangeActionClick((ItemImageView) PhotoLayout.this.mQuickAction.getAnchorView());
                }
            }
        });
        this.mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            public void onDismiss() {
            }
        });
        //QuickActionItem quickActionItem5 = new QuickActionItem(2, getContext().getString(R.string.change), getResources().getDrawable(R.drawable.menu_change));
        QuickActionItem quickActionItem6 = new QuickActionItem(3, getContext().getString(R.string.delete), getResources().getDrawable(R.drawable.delete));
        QuickActionItem quickActionItem7 = new QuickActionItem(4, getContext().getString(R.string.cancel), getResources().getDrawable(R.drawable.colse));
        this.mBackgroundQuickAction = new QuickAction(getContext(), 0);
        //this.mBackgroundQuickAction.addActionItem(quickActionItem5);
        this.mBackgroundQuickAction.addActionItem(quickActionItem6);
        this.mBackgroundQuickAction.addActionItem(quickActionItem7);
        this.mBackgroundQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            public void onItemClick(QuickAction quickAction, int i, int i2) {
                PhotoLayout.this.mBackgroundQuickAction.getActionItem(i);
                PhotoLayout.this.mBackgroundQuickAction.dismiss();
                if (i2 == 3) {
                    ((TransitionImageView) PhotoLayout.this.mBackgroundQuickAction.getAnchorView()).recycleImages();
                } else if (i2 == 2 && PhotoLayout.this.mQuickActionClickListener != null) {
                    PhotoLayout.this.mQuickActionClickListener.onChangeBackgroundActionClick((TransitionImageView) PhotoLayout.this.mBackgroundQuickAction.getAnchorView());
                }
            }
        });
    }

    public Bitmap getTemplateImage() {
        return this.mTemplateImage;
    }

    public TransitionImageView getBackgroundImageView() {
        return this.mBackgroundImageView;
    }

    public void setBackgroundImage(Bitmap bitmap) {
        this.mBackgroundImage = bitmap;
    }

    public Bitmap getBackgroundImage() {
        return this.mBackgroundImageView.getImage();
    }

    private void asyncCreateBackgroundImage(final String str) {
        new AsyncTask<Void, Void, Bitmap>() {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                PhotoLayout.this.mProgressBar.setVisibility(VISIBLE);
            }

            /* access modifiers changed from: protected */
            public Bitmap doInBackground(Void... voidArr) {
                try {
                    Bitmap decodeFileToBitmap = ImageDecoder.decodeFileToBitmap(str);
                    if (decodeFileToBitmap == null) {
                        return null;
                    }
                    Bitmap fastblur = PhotoUtils.fastblur(decodeFileToBitmap, 10);
                    if (decodeFileToBitmap != fastblur) {
                        decodeFileToBitmap.recycle();
                        System.gc();
                    }
                    return fastblur;
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    return null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                PhotoLayout.this.mProgressBar.setVisibility(GONE);
                if (bitmap != null) {
                    PhotoLayout.this.mBackgroundImageView.init(bitmap, PhotoLayout.this.mViewWidth, PhotoLayout.this.mViewHeight, PhotoLayout.this.mOutputScaleRatio);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void build(int i, int i2, float f) {
        if (i >= 1 && i2 >= 1) {
            this.mViewWidth = i;
            this.mViewHeight = i2;
            this.mOutputScaleRatio = f;
            this.mItemImageViews.clear();
            this.mInternalScaleRatio = 1.0f / PhotoUtils.calculateScaleRatio(this.mImageWidth, this.mImageHeight, i, i2);
            for (PhotoItem addPhotoItemView : mPhotoItems) {
                this.mItemImageViews.add(addPhotoItemView(addPhotoItemView, this.mInternalScaleRatio, this.mOutputScaleRatio));
            }
            ImageView imageView = new ImageView(getContext());
            if (Build.VERSION.SDK_INT >= 16) {
                imageView.setBackground(new BitmapDrawable(getResources(), this.mTemplateImage));
            } else {
                imageView.setBackgroundDrawable(new BitmapDrawable(getResources(), this.mTemplateImage));
            }
            addView(imageView, new RelativeLayout.LayoutParams(-1, -1));
            this.mProgressBar = new ProgressBar(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(13);
            this.mProgressBar.setVisibility(GONE);
            addView(this.mProgressBar, layoutParams);
            this.mBackgroundImageView = new TransitionImageView(getContext());
            addView(this.mBackgroundImageView, 0, new RelativeLayout.LayoutParams(-1, -1));
            this.mBackgroundImageView.setOnImageClickListener(new TransitionImageView.OnImageClickListener() {
                public void onLongClickImage(TransitionImageView transitionImageView) {
                }

                public void onDoubleClickImage(TransitionImageView transitionImageView) {
                    if ((transitionImageView.getImage() == null || transitionImageView.getImage().isRecycled()) && PhotoLayout.this.mQuickActionClickListener != null) {
                        PhotoLayout.this.mQuickActionClickListener.onChangeBackgroundActionClick(transitionImageView);
                        return;
                    }
                    double width = transitionImageView.getWidth();
                    Double.isNaN(width);
                    double height = transitionImageView.getHeight();
                    Double.isNaN(height);
                    mBackgroundQuickAction.show(transitionImageView, (int) (width / 2.0d), (int) (height / 2.0d));
                    PhotoLayout.this.mBackgroundQuickAction.setAnimStyle(4);
                }
            });
            Bitmap bitmap = this.mBackgroundImage;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mBackgroundImageView.init(this.mBackgroundImage, this.mViewWidth, this.mViewHeight, this.mOutputScaleRatio);
            } else if (this.mPhotoItems.size() > 0 && this.mPhotoItems.get(0).imagePath != null && this.mPhotoItems.get(0).imagePath.length() > 0) {
                asyncCreateBackgroundImage(this.mPhotoItems.get(0).imagePath);
            }
        }
    }

    private ItemImageView addPhotoItemView(PhotoItem photoItem, float f, float f2) {
        if (photoItem == null || photoItem.maskPath == null) {
            return null;
        }
        ItemImageView itemImageView = new ItemImageView(getContext(), photoItem);
        float width = ((float) itemImageView.getMaskImage().getWidth()) * f;
        float height = ((float) itemImageView.getMaskImage().getHeight()) * f;
        itemImageView.init(width, height, f2);
        itemImageView.setOnImageClickListener(this);
        if (this.mPhotoItems.size() > 1) {
            itemImageView.setOnDragListener(this.mOnDragListener);
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) width, (int) height);
        layoutParams.leftMargin = (int) (photoItem.x * f);
        layoutParams.topMargin = (int) (f * photoItem.y);
        itemImageView.setOriginalLayoutParams(layoutParams);
        addView(itemImageView, layoutParams);
        return itemImageView;
    }

    public Bitmap createImage() {
        Iterator<ItemImageView> it;
        float f = this.mOutputScaleRatio;
        Bitmap createBitmap = Bitmap.createBitmap((int) (((float) this.mViewWidth) * f), (int) (f * ((float) this.mViewHeight)), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (this.mBackgroundImageView.getImage() != null && !this.mBackgroundImageView.getImage().isRecycled()) {
            canvas.drawBitmap(this.mBackgroundImageView.getImage(), this.mBackgroundImageView.getScaleMatrix(), paint);
        }
        canvas.saveLayer(0.0f, 0.0f, (float) createBitmap.getWidth(), (float) createBitmap.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
        Iterator<ItemImageView> it2 = this.mItemImageViews.iterator();
        while (it2.hasNext()) {
            ItemImageView next = it2.next();
            if (next.getImage() == null || next.getImage().isRecycled()) {
                it = it2;
            } else {
                int left = (int) (((float) next.getLeft()) * this.mOutputScaleRatio);
                int top = (int) (((float) next.getTop()) * this.mOutputScaleRatio);
                int width = (int) (((float) next.getWidth()) * this.mOutputScaleRatio);
                int height = (int) (((float) next.getHeight()) * this.mOutputScaleRatio);
                float f2 = (float) left;
                float f3 = (float) top;
                it = it2;
                float f4 = f3;
                canvas.saveLayer(f2, f3, (float) (left + width), (float) (top + height), paint, Canvas.ALL_SAVE_FLAG);
                canvas.save();
                canvas.translate(f2, f4);
                canvas.clipRect(0, 0, width, height);
                canvas.drawBitmap(next.getImage(), next.getScaleMatrix(), paint);
                canvas.restore();
                canvas.save();
                canvas.translate(f2, f4);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                canvas.drawBitmap(next.getMaskImage(), next.getScaleMaskMatrix(), paint);
                paint.setXfermode(null);
                canvas.restore();
                canvas.restore();
            }
            it2 = it;
        }
        Bitmap bitmap = this.mTemplateImage;
        if (bitmap != null) {
            float f5 = this.mOutputScaleRatio;
            canvas.drawBitmap(bitmap, ImageUtils.createMatrixToDrawImageInCenterView(((float) this.mViewWidth) * f5, f5 * ((float) this.mViewHeight), (float) bitmap.getWidth(), (float) this.mTemplateImage.getHeight()), paint);
        }
        canvas.restore();
        return createBitmap;
    }

    public void recycleImages(boolean z) {
        String str = TAG;
        if (z) {
            this.mBackgroundImageView.recycleImages();
        }
        for (ItemImageView recycleImages : this.mItemImageViews) {
            recycleImages.recycleImages(z);
        }
        Bitmap bitmap = this.mTemplateImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mTemplateImage.recycle();
            this.mTemplateImage = null;
        }
        System.gc();
    }

    public void onLongClickImage(ItemImageView itemImageView) {
        if (this.mPhotoItems.size() > 1) {
            itemImageView.setTag("x=" + itemImageView.getPhotoItem().x + ",y=" + itemImageView.getPhotoItem().y + ",path=" + itemImageView.getPhotoItem().imagePath);
            String[] strArr = {"text/plain"};
            itemImageView.startDrag(new ClipData(itemImageView.getTag().toString(), strArr, new ClipData.Item((CharSequence) itemImageView.getTag())), new View.DragShadowBuilder(itemImageView), itemImageView, 0);
        }
    }

    public void onDoubleClickImage(ItemImageView itemImageView) {
        OnQuickActionClickListener onQuickActionClickListener;
        if ((itemImageView.getImage() == null || itemImageView.getImage().isRecycled()) && (onQuickActionClickListener = this.mQuickActionClickListener) != null) {
            onQuickActionClickListener.onChangeActionClick(itemImageView);
            return;
        }
        this.mQuickAction.show(itemImageView);
        this.mQuickAction.setAnimStyle(4);
    }
}