package com.example.duckky.mm91.Animation;

import android.view.animation.Animation;

/**
 * Created by DuckKy on 2/3/2017.
 */

public interface Callback {
    interface OnAnimationStart{
        void onAnimationStart(Animation animation);
    }
    interface OnAnimationRepeat{
        void onAnimationRepeat(Animation animation);
    }
    interface OnAnimationEnd{
        void onAnimationEnd(Animation animation);
    }
}
