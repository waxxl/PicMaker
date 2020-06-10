package com.eptonic.photocollage.ui.dahjk.ahjd.fah.fha.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eptonic.photocollage.presenter.listener.OnItemClickListener;
import com.eptonic.photocollage.R;
import com.eptonic.photocollage.adapter.GalleryAlbumAdapter;
import com.eptonic.photocollage.model.GalleryAlbum;
import com.eptonic.photocollage.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class GalleryAlbumFragment extends Fragment {
    public GalleryAlbumAdapter mAdapter;
    public ArrayList<GalleryAlbum> mAlbums;
    public RecyclerView mPicRecycler;
    public Parcelable mListViewState;
    public View mProgressBar;
    private Activity mActivity;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_gallery_album, viewGroup, false);
        mPicRecycler = inflate.findViewById(R.id.recycler_pic);
        mProgressBar = inflate.findViewById(R.id.progress_bar);
        new LoadAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mActivity = getActivity();
        mActivity.setTitle(R.string.gallery_albums);
        return inflate;
    }

    public class LoadAsyncTask extends AsyncTask<Void, Void, ArrayList<GalleryAlbum>> {
        LoadAsyncTask() {}

        public void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        public ArrayList<GalleryAlbum> doInBackground(Void... voidArr) {
            return loadPhotoAlbums();
        }

        public void onPostExecute(ArrayList<GalleryAlbum> arrayList) {
            super.onPostExecute(arrayList);
            if (already()) {
                mProgressBar.setVisibility(View.GONE);
                mAlbums = arrayList;
                mAdapter = new GalleryAlbumAdapter(getActivity(), mAlbums);
                createWrapper();
                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        goToDetailPictures(mAlbums.get(position));
                    }
                });
            }
        }
    }

    public void createWrapper() {
        GalleryAlbumAdapter galleryAlbumAdapter = mAdapter;
        if (galleryAlbumAdapter != null) {
            mPicRecycler.setHasFixedSize(false);
            mPicRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mPicRecycler.setAdapter(galleryAlbumAdapter);
        }
    }

    public ArrayList<GalleryAlbum> loadPhotoAlbums() {
        HashMap hashMap = new HashMap();
        String[] strArr = {"_id", "_data", "bucket_id", "bucket_display_name", "datetaken"};
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, strArr, "", null, "");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String string = cursor.getString(cursor.getColumnIndex("bucket_display_name"));
                    long dateTaken = cursor.getLong(cursor.getColumnIndex("datetaken"));
                    String string2 = cursor.getString(cursor.getColumnIndex("_data"));
                    long bucket_id = cursor.getLong(cursor.getColumnIndex("bucket_id"));
                    GalleryAlbum galleryAlbum = (GalleryAlbum) hashMap.get(Long.valueOf(bucket_id));
                    if (galleryAlbum == null) {
                        GalleryAlbum galleryAlbum2 = new GalleryAlbum(bucket_id, string);
                        galleryAlbum2.setTakenDate(Utils.toUTCDateTimeString(new Date(dateTaken)));
                        galleryAlbum2.getImageList().add(string2);
                        hashMap.put(Long.valueOf(bucket_id), galleryAlbum2);
                    } else {
                        galleryAlbum.getImageList().add(string2);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<GalleryAlbum> galleryAlbums = new ArrayList<>();
        Iterator<GalleryAlbum> iterator = hashMap.values().iterator();
        while (iterator.hasNext()) {
            GalleryAlbum album = iterator.next();
            galleryAlbums.add(album);
        }
        return galleryAlbums;
    }

    public void loadAlbumNames() {
        Cursor query = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"bucket_id", "bucket_display_name", "datetaken", "_data"}, "1) GROUP BY 1,(2", null, "date_modified DESC");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < query.getCount(); i++) {
            query.moveToPosition(i);
            arrayList2.add(query.getString(query.getColumnIndex("bucket_display_name")));
            arrayList.add(query.getString(query.getColumnIndex("_data")));
        }
        query.close();
    }

    private void goToDetailPictures(GalleryAlbum galleryAlbum) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(GalleryAlbumImageFragment.ALBUM_IMAGE_EXTRA, (ArrayList) galleryAlbum.getImageList());
        bundle.putString(GalleryAlbumImageFragment.ALBUM_NAME_EXTRA, galleryAlbum.getAlbumName());
        GalleryAlbumImageFragment galleryAlbumImageFragment = new GalleryAlbumImageFragment();
        galleryAlbumImageFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getActivity().getFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.frame_container, galleryAlbumImageFragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public boolean already() {
        return isAdded() && getActivity() != null;
    }
}
