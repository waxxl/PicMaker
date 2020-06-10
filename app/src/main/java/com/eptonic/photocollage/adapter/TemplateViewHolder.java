package com.eptonic.photocollage.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.R;
import com.eptonic.photocollage.model.TemplateItem;
import com.eptonic.photocollage.util.PhotoUtils;

public class TemplateViewHolder extends RecyclerView.ViewHolder {
    private final ImageView mImageView;
    private final TextView mTextView;
    private int mViewType = 0;

    public interface OnTemplateItemClickListener {
        void onTemplateItemClick(TemplateItem templateItem);
    }

    TemplateViewHolder(View view, int type) {
        super(view);
        mViewType = type;
        mTextView = view.findViewById(R.id.text);
        mImageView = view.findViewById(R.id.imageView);
    }

    public void bindItem(final TemplateItem templateItem, final OnTemplateItemClickListener onTemplateItemClickListener, int pos) {
        if (mViewType != 1 || mTextView == null) {
            if (mImageView != null) {
                PhotoUtils.loadImageWithGlide(mImageView.getContext(), this.mImageView, templateItem.getPreview());
                mImageView.setOnClickListener(new View.OnClickListener() {
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
        mTextView.setText(templateItem.getHeader());
    }
}