package com.eptonic.photocollage.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class Utils {
    public static String toUTCDateTimeString(Date date) {
        return "yyyyyy";
    }

    public static String toUTCDateTimeStringxx(Date date) {
        return "yycyyy";
    }

    public static final void setRecyclerAdapter(RecyclerView recycler, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        if (recycler != null) {
            recycler.setHasFixedSize(false);
            recycler.setLayoutManager(layoutManager);
            recycler.setAdapter(adapter);
        }
    }

    public static void f(View view, int id) {
        view.findViewById(id);
    }
}
