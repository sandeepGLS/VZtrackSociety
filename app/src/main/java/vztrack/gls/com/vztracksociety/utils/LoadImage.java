package vztrack.gls.com.vztracksociety.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import vztrack.gls.com.vztracksociety.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.DiscCacheUtil;
import com.nostra13.universalimageloader.core.assist.MemoryCacheUtil;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by pratima on 14/10/15.
 */
public class LoadImage {
    ImageLoader imageLoader;
    DisplayImageOptions options;
    public static int p_flag=0;

    public void loadImage(Context context,final int defaultImage, final String url, ImageView imageView,final View optionView){
        if(url == null){
            if (optionView!=null) {
                optionView.setBackgroundResource(defaultImage);
            }
            imageView.setImageResource(defaultImage);
            return;
        }

        options = new DisplayImageOptions.Builder()
                .showStubImage(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        try {
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            imageLoader.displayImage(url, imageView, options, new SimpleImageLoadingListener() {
                boolean cacheFound;
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    List<String> memCache = MemoryCacheUtil.findCacheKeysForImageUri(imageUri, ImageLoader.getInstance().getMemoryCache());
                    cacheFound = !memCache.isEmpty();
                    if (!cacheFound) {
                        File discCache = DiscCacheUtil.findInCache(imageUri, ImageLoader.getInstance().getDiscCache());
                        if (discCache != null) {
                            p_flag=1;
                            cacheFound = discCache.exists();
                        }
                        if(optionView !=null) {
                            if (url == null) {
                                optionView.setBackgroundResource(defaultImage);
                            } else {
                                new LoadBackground(url,"", optionView).execute();
                            }
                        }
                        else
                        {
                        }
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (cacheFound) {
                        p_flag=1;
                        ImageLoader.getInstance().displayImage(imageUri, (ImageView) view);
                        if(optionView != null){
                            Drawable drawable = new BitmapDrawable(loadedImage);
                            optionView.setBackgroundDrawable(drawable);
                        }
                        else
                        {
                        }
                    }
                }

            });
        } catch (Exception e) {
        }
    }

    private class LoadBackground extends AsyncTask<String, Void, Drawable> {

        private String imageUrl , imageName;
        View view;

        public LoadBackground(String url, String file_name,View view) {
            this.imageUrl = url;
            this.imageName = file_name;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... urls) {
            try {
                InputStream is = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(is, this.imageName);

                return d;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }
        private Object fetch(String address) throws MalformedURLException,IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onPostExecute(Drawable result) {
            view.setBackgroundDrawable(result);
        }
    }
}
