package com.eptonic.photocollage.ui.dahjk.ahjd.fah.fha.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.eptonic.photocollage.presenter.listener.OnImageSelectListener;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.ui.next.s.xx.xxx.yy.cc.activity.GalleryActivity;
import com.eptonic.photocollage.adapter.PhotosAdapter;

import java.util.ArrayList;

public class GalleryAlbumImageFragment extends BaseFragment implements View.OnClickListener {
    public static final String ALBUM_IMAGE_EXTRA = "image_extra";
    public static final String ALBUM_NAME_EXTRA = "name_extra";

    private GridView mPhotoGrid;
    private View mToolBar;
    private ImageView mBack;

    private ArrayList<String> mPhotos;
    private String mTitle;
    private OnImageSelectListener mListener;

    public void setArguments(Bundle bundle) {
        mPhotos = bundle.getStringArrayList(ALBUM_IMAGE_EXTRA);
        mTitle = bundle.getString(ALBUM_NAME_EXTRA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_gallery_detail, container, false);
        mPhotoGrid = view.findViewById(R.id.gallery_x_main);
        mToolBar = view.findViewById(R.id.bar_tool_x);

        if(mPhotos != null && mPhotos.size() > 0) {
            mPhotoGrid.setAdapter(new PhotosAdapter(getActivity(), mPhotos));
            mPhotoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mListener != null) {
                        mListener.onSelect(mPhotos.get(position));
                    }

                    if(already()) {
                        GalleryActivity GalleryAlbumActivity = (GalleryActivity) getActivity();
                        GalleryAlbumActivity.addSelectPhotos(mPhotos.get(position));
                    }
                }
            });
        }
        setTitle(TextUtils.isEmpty(mTitle) ? getString(R.string.gallery_albums) : mTitle);
        return view;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
