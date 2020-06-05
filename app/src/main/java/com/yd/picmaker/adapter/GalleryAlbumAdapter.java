package com.yd.picmaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.Listener.OnItemClickListener;
import com.yd.picmaker.R;
import com.yd.picmaker.model.GalleryAlbum;
import com.yd.picmaker.util.PhotoUtils;

import java.util.ArrayList;

public class GalleryAlbumAdapter extends RecyclerView.Adapter<GalleryAlbumAdapter.ViewHolder> {
    private OnItemClickListener mItemListener;
    private final Context mContext;
    private final ArrayList<GalleryAlbum> mAlbums;
    private final int mType = 0;

    public GalleryAlbumAdapter(Activity activity, ArrayList<GalleryAlbum> mAlbums) {
        mContext = activity;
        this.mAlbums = mAlbums;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.gallery_albums_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GalleryAlbum galleryAlbum;
        try {
            galleryAlbum = mAlbums.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            galleryAlbum = null;
        }

        if(galleryAlbum != null) {

        }
        if (galleryAlbum.getImageList().size() > 0) {
            PhotoUtils.loadImageWithGlide(holder.imageView.getContext(), holder.imageView, galleryAlbum.getImageList().get(0));
        } else {
            holder.imageView.setImageBitmap(null);
        }

        holder.title.setText(mAlbums.get(position).getAlbumName());
        holder.count.setText(mAlbums.get(position).getImageList().size() + "");
        holder.allLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mItemListener) {
                    mItemListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mAlbums) {
            return 0;
        }
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout allLayout;
        public ImageView imageView;
        private final TextView title;
        private final TextView count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allLayout = itemView.findViewById(R.id.gallery_item);
            imageView = itemView.findViewById(R.id.gallery_item_icon);
            title = itemView.findViewById(R.id.gallery_item_title);
            count = itemView.findViewById(R.id.gallery_item_count);
        }
    }
}
