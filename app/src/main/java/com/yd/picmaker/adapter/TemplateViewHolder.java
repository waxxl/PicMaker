package com.yd.picmaker.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.R;
import com.yd.picmaker.model.TemplateItem;
import com.yd.picmaker.util.PhotoUtils;

public class TemplateViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTextView;
    private int mViewType = 0;

    public interface OnTemplateItemClickListener {
        void onTemplateItemClick(TemplateItem templateItem);
    }

    TemplateViewHolder(View view, int i) {
        super(view);
        this.mViewType = i;
        this.mTextView = (TextView) view.findViewById(R.id.text);
        this.mImageView = (ImageView) view.findViewById(R.id.imageView);
    }

    public void bindItem(final TemplateItem templateItem, final OnTemplateItemClickListener onTemplateItemClickListener) {
        TextView textView;
        if (this.mViewType != 1 || (textView = this.mTextView) == null) {
            ImageView imageView = this.mImageView;
            if (imageView != null) {
                PhotoUtils.loadImageWithGlide(imageView.getContext(), this.mImageView, templateItem.getPreview());
                this.mImageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (onTemplateItemClickListener != null) {
                            onTemplateItemClickListener.onTemplateItemClick(templateItem);
                        }
                    }
                });
                return;
            }
            return;
        }
        textView.setText(templateItem.getHeader());
    }
}