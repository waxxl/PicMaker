package com.yd.picmaker.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.R;
import com.yd.picmaker.model.TemplateItem;
import com.yd.picmaker.util.PhotoUtils;

import java.util.ArrayList;


public class HorizontalPreviewTemplateAdapter extends RecyclerView.Adapter<HorizontalPreviewTemplateAdapter.PreviewTemplateViewHolder> {
    public OnPreviewTemplateClickListener mListener;
    public ArrayList<TemplateItem> mTemplateItems;

    public interface OnPreviewTemplateClickListener {
        void onPreviewTemplateClick(TemplateItem templateItem);
    }

    public static class PreviewTemplateViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public View mSelectedView;

        PreviewTemplateViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.imageView);
            this.mSelectedView = view.findViewById(R.id.selectedView);
        }
    }

    public HorizontalPreviewTemplateAdapter(ArrayList<TemplateItem> arrayList, OnPreviewTemplateClickListener onPreviewTemplateClickListener) {
        this.mTemplateItems = arrayList;
        this.mListener = onPreviewTemplateClickListener;
    }

    public PreviewTemplateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PreviewTemplateViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_preview_template_hor, viewGroup, false));
    }

    public void onBindViewHolder(PreviewTemplateViewHolder previewTemplateViewHolder, final int i) {
        PhotoUtils.loadImageWithGlide(previewTemplateViewHolder.mImageView.getContext(), previewTemplateViewHolder.mImageView, this.mTemplateItems.get(i).getPreview());
        if (this.mTemplateItems.get(i).isSelected()) {
            previewTemplateViewHolder.mSelectedView.setVisibility(View.VISIBLE);
        } else {
            previewTemplateViewHolder.mSelectedView.setVisibility(View.GONE);
        }
        previewTemplateViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onPreviewTemplateClick((TemplateItem) mTemplateItems.get(i));
                }
            }
        });
    }

    public int getItemCount() {
        return this.mTemplateItems.size();
    }
}