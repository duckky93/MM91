package com.example.duckky.mm91.Animation;

import android.content.Context;

/**
 * Created by DuckKy on 2/3/2017.
 */

public class AnimUtils {

    static AnimUtils instance;

    Context context;

    AnimUtils(Context context) {
        this.context = context;
    }

    public AnimationCreator load(int animID) {
        return new AnimationCreator(context, animID);
    }

    public static AnimUtils with(Context context) {
        if (instance == null) {
            synchronized (AnimUtils.class) {
                instance = new AnimUtils(context.getApplicationContext());
            }
        }
        return instance;
    }
}
