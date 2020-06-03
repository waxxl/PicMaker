package com.yd.picmaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.yd.picmaker.R;
import com.yd.picmaker.util.PhotoUtils;

import java.util.List;

public class PhotosAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater = null;
    private boolean mImageFitCenter = true;

    public PhotosAdapter(Context context, List<String> list) {
        super(context, R.layout.item_gallery_photo, list);
        this.mInflater = LayoutInflater.from(context);
    }


    public PhotosAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public PhotosAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public PhotosAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public PhotosAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public PhotosAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    public PhotosAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    public void setImageFitCenter(boolean fitCenter) {
        this.mImageFitCenter = fitCenter;
        notifyDataSetChanged();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewT;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            viewT = this.mInflater.inflate(R.layout.item_gallery_photo, viewGroup, false);
            viewHolder.imageView = (ImageView) viewT.findViewById(R.id.photo_gallery_detail);
            if (this.mImageFitCenter) {
                viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            viewT.setTag(viewHolder);
        } else {
            viewT = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        PhotoUtils.loadImageWithGlide(getContext(), viewHolder.imageView, (String) getItem(i));
        return viewT;
    }

    private class ViewHolder {
        ImageView imageView;

        private ViewHolder() {
        }
    }

}
