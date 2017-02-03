package com.example.duckky.mm91.Animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

/**
 * Created by DuckKy on 2/3/2017.
 */

public class AnimationCreator {
    Context context;
    int animID;
    Callback.OnAnimationStart onAnimationStart;
    Callback.OnAnimationRepeat onAnimationRepeat;
    Callback.OnAnimationEnd onAnimationEnd;

    AnimationCreator(Context context, int animID) {
        this.context = context;
        this.animID = animID;
    }

    public AnimationCreator addOnAnimationStart(Callback.OnAnimationStart onAnimationStart) {
        this.onAnimationStart = onAnimationStart;
        return this;
    }

    public AnimationCreator addOnAnimationRepeat(Callback.OnAnimationRepeat onAnimationRepeat) {
        this.onAnimationRepeat = onAnimationRepeat;
        return this;
    }

    public AnimationCreator addOnAnimationEnd(Callback.OnAnimationEnd onAnimationEnd) {
        this.onAnimationEnd = onAnimationEnd;
        return this;
    }

    public void startAnimationWith(View view) {
        if (view == null) {
            throw new NullPointerException("View must not be null");
        } else {
            Animation animation = AnimationUtils.loadAnimation(context, animID);
            if (onAnimationStart != null || onAnimationRepeat != null || onAnimationEnd != null) {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (onAnimationStart != null) {
                            onAnimationStart.onAnimationStart(animation);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (onAnimationEnd != null) {
                            onAnimationEnd.onAnimationEnd(animation);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        if (onAnimationRepeat != null) {
                            onAnimationRepeat.onAnimationRepeat(animation);
                        }
                    }
                });
            }
            view.startAnimation(animation);
        }
    }
}
