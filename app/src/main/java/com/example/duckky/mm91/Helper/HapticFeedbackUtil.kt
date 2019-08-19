/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maxis.ereload2.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources.NotFoundException
import android.os.Vibrator
import android.provider.Settings.System
import android.util.Log

/**
 * Handles the haptic feedback: a light buzz happening when the user
 * presses a soft key (UI button or capacitive key).  The haptic
 * feedback is controlled by:
 * - a system resource for the pattern
 * The pattern used is tuned per device and stored in an internal
 * resource (config_virtualKeyVibePattern.)
 * - a system setting HAPTIC_FEEDBACK_ENABLED.
 * HAPTIC_FEEDBACK_ENABLED can be changed by the user using the
 * system Settings activity. It must be rechecked each time the
 * activity comes in the foreground (onResume).
 *
 * This class is not thread safe. It assumes it'll be called from the
 * UI thead.
 *
 * Typical usage:
 * --------------
 * static private final boolean HAPTIC_ENABLED = true;
 * private HapticFeedback mHaptic = new HapticFeedback();
 *
 * protected void onCreate(Bundle icicle) {
 * mHaptic.init((Context)this, HAPTIC_ENABLED);
 * }
 *
 * protected void onResume() {
 * // Refresh the system setting.
 * mHaptic.checkSystemSetting();
 * }
 *
 * public void foo() {
 * mHaptic.vibrate();
 * }
 *
 */
class HapticFeedbackUtil {
    private val mContext: Context? = null
    private var mHapticPattern: LongArray? = null
    private var mVibrator: Vibrator? = null
    private var mEnabled = false
    private var mContentResolver: ContentResolver? = null
    private var mSettingEnabled = false
    /**
     * Initialize this instance using the app and system
     * configs. Since these don't change, init is typically called
     * once in 'onCreate'.
     * checkSettings is not called during init.
     * @param context To look up the resources and system settings.
     * @param enabled If false, vibrate will be a no-op regardless of
     * the system settings.
     */
    fun init(context: Context, enabled: Boolean) {
        mEnabled = enabled
        if (enabled) {
            mVibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            mHapticPattern = longArrayOf(0, DURATION, 2 * DURATION, 3 * DURATION)
            mContentResolver = context.contentResolver
        }
    }

    /**
     * Reload the system settings to check if the user enabled the
     * haptic feedback.
     */
    fun checkSystemSetting() {
        if (!mEnabled) {
            return
        }
        mSettingEnabled = try {
            val `val` = System.getInt(mContentResolver, System.HAPTIC_FEEDBACK_ENABLED, 0)
            `val` != 0
        } catch (nfe: NotFoundException) {
            Log.e(LOG_TAG, "Could not retrieve system setting.", nfe)
            false
        }
    }

    /**
     * Generate the haptic feedback vibration. Only one thread can
     * request it. If the phone is already in a middle of an haptic
     * feedback sequence, the request is ignored.
     */
    fun vibrate() {
        if (!mEnabled || !mSettingEnabled) {
            return
        }
        // System-wide configuration may return different styles of haptic feedback pattern.
        // - an array with one value implies "one-shot vibration"
        // - an array with multiple values implies "pattern vibration"
        // We need to switch methods to call depending on the difference.
        // See also PhoneWindowManager#performHapticFeedbackLw() for another example.


        if (mHapticPattern != null && mHapticPattern!!.size == 1) {
            mVibrator!!.vibrate(mHapticPattern!![0])
        } else {
            mVibrator!!.vibrate(mHapticPattern, NO_REPEAT)
        }
    }

    companion object {
        /** If no pattern was found, vibrate for a small amount of time.  */
        private const val DURATION: Long = 10  // millisec.

        /** Play the haptic pattern only once.  */
        private const val NO_REPEAT = -1
        private const val LOG_TAG = "HapticFeedback"
    }
}