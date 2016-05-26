package me.codpoe.onlyweather.httpUtil;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class ImgLoader {

    public static final String IMG_URL = "http://files.heweather.com/cond_icon/";

    public static void loadImg(Context context, String imgCode, ImageView view) {
        Glide.with(context)
                .load(IMG_URL + imgCode + ".png")
                .into(view);
    }
}
