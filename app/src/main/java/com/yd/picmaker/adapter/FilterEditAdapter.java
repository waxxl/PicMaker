package com.yd.picmaker.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yd.picmaker.Listener.OnItemClickListener;
import com.yd.picmaker.R;

import static com.yd.picmaker.util.Constants.FILTERS;

public class FilterEditAdapter extends RecyclerView.Adapter<FilterEditAdapter.ViewHolder> {
    private OnItemClickListener mItemListener;
    private int mType = 0;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.background_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(FILTERS[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
        return FILTERS.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
