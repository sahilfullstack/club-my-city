package com.dwarkainfoapp.category;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter {

    private Activity context;

    private static ImageView imageView;

    private ArrayList<String> plotsImages;

    private static ViewHolder holder;
    public ImageLoader imageLoader;
    public GalleryImageAdapter(Activity context, ArrayList<String> plotsImages) {

        this.context = context;
        this.plotsImages = plotsImages;
        imageLoader = new ImageLoader(context.getApplicationContext());

    }

    @Override
    public int getCount() {
        return plotsImages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            holder = new ViewHolder();

            imageView = new ImageView(this.context);

            imageView.setPadding(3, 3, 3, 3);

            convertView = imageView;

            holder.imageView = imageView;

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.DisplayImage("http://www.smartcityinfo.in/adminbly/images/adds/" + plotsImages.get(position), holder.imageView);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView.setLayoutParams(new Gallery.LayoutParams(150, 90));

        return imageView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

}

