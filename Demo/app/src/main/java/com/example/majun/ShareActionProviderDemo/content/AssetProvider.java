package com.example.majun.ShareActionProviderDemo.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by majun on 17/3/4.
 */
public class AssetProvider extends ContentProvider {

    //url内容的content
    public static String CONTENT_URL = "com.example.majun.ShareActionProviderDemo";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
        // The asset file name should be the last path segment
        final String assetName = uri.getLastPathSegment();

        // If the given asset name is empty, throw an exception
        if (TextUtils.isEmpty(assetName)) {
            throw new FileNotFoundException();
        }

        try {
            // Try and return a file descriptor for the given asset name
            AssetManager am = getContext().getAssets();
            return am.openFd(assetName);
        } catch (IOException e) {
            e.printStackTrace();
            return super.openAssetFile(uri, mode);
        }
    }

}
