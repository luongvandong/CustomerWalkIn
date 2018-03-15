package katana.customerwalkin.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * ka
 * 05/07/2017
 */

public class ImageLoaderUtils {

    public static void show(Context context, String uri, ImageView iv) {
        Glide.with(context)
                .load(uri)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    public static void showCenterCrop(Context context, String uri, ImageView iv) {
        Glide.with(context)
                .load(uri)
                .crossFade()
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    public static void show(Context context, int uri, ImageView iv) {
        Glide.with(context)
                .load(uri)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }
}
