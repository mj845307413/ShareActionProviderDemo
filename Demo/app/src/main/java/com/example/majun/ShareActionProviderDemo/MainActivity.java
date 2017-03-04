package com.example.majun.ShareActionProviderDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.majun.ShareActionProviderDemo.content.ContentItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private final List<ContentItem> mContentItems = getList();

    private ShareActionProvider mShareActionProvider;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setShare(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        LayoutInflater mLayoutInflater;

        @Override
        public int getCount() {
            return mContentItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ContentItem contentItem = mContentItems.get(position);
            if (mLayoutInflater == null) {
                mLayoutInflater = LayoutInflater.from(MainActivity.this);
            }
            switch (contentItem.mType) {
                case ContentItem.TYPE_TEXT:
                    TextView textView = (TextView) mLayoutInflater.inflate(R.layout.text_layout, container, false);
                    textView.setText(getResources().getText(contentItem.mResourceID));
                    container.addView(textView);
                    return textView;
                case ContentItem.TYPE_IMAGE:
                    ImageView imageview = (ImageView) mLayoutInflater.inflate(R.layout.image_layout, container, false);
                    if (contentItem.getUri() != null) {
                        imageview.setImageURI(contentItem.getUri());
                    }
                    container.addView(imageview);
                    return imageview;
                default:
                    return null;
            }
        }
    };

    static List<ContentItem> getList() {
        List<ContentItem> contentItems = new ArrayList<>();
        contentItems.add(new ContentItem(ContentItem.TYPE_TEXT, R.string.showText));
        contentItems.add(new ContentItem(ContentItem.TYPE_IMAGE, "photo_1.jpg"));
        contentItems.add(new ContentItem(ContentItem.TYPE_IMAGE, "photo_2.jpg"));
        contentItems.add(new ContentItem(ContentItem.TYPE_IMAGE, "photo_3.jpg"));
        return contentItems;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item1);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShare(mViewPager.getCurrentItem());
        return super.onCreateOptionsMenu(menu);
    }

    public void setShare(int position) {
        if (mShareActionProvider != null) {
            ContentItem contentItem = mContentItems.get(position);
            Intent intent = contentItem.getShareIntent(MainActivity.this);
            mShareActionProvider.setShareIntent(intent);
        }
    }
}
