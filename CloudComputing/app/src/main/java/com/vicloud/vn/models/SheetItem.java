package com.vicloud.vn.models;

import android.widget.ImageView;

/**
 * Created by huunc on 7/23/16.
 */
public class SheetItem {
    private int mDrawableRes;

    private String mTitle;

    public SheetItem(int drawable, String title) {
        mDrawableRes = drawable;
        mTitle = title;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }
}
