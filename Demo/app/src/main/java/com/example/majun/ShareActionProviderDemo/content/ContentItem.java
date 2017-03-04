package com.example.majun.ShareActionProviderDemo.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by majun on 17/3/4.
 */
public class ContentItem {
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_TEXT = 1;
    public final int mType;
    public final int mResourceID;
    public final String mFilePath;


    public ContentItem(int type, String path) {
        mType = type;
        mFilePath = path;
        mResourceID = 0;
    }

    public ContentItem(int type, int resourceID) {
        mType = type;
        mFilePath = null;
        mResourceID = resourceID;
    }

    public Uri getUri() {
        if (!TextUtils.isEmpty(mFilePath)) {
            return Uri.parse("content://" + AssetProvider.CONTENT_URL + "/" + mFilePath);
        }
        return null;
    }

    public Intent getShareIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        switch (mType) {
            case TYPE_IMAGE:
                intent.setType("image/jpg");
                intent.putExtra(Intent.EXTRA_STREAM, getUri());
                break;
            case TYPE_TEXT:
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, context.getString(mResourceID));
                break;
            default:
                break;
        }
        return intent;

    }
}
