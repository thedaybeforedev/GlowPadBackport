/*
 * Copyright (C) 2011 The Android Open Source Project
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

package net.frakbot.glowpadbackport;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;

import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.reflect.Method;
import java.util.Arrays;

import static net.frakbot.glowpadbackport.util.Const.IS_Q;

public class TargetDrawable {
    private static final String TAG = "TargetDrawable";
    private static final boolean DEBUG = false;

    public static final int[] STATE_ACTIVE =
            {android.R.attr.state_enabled, android.R.attr.state_active};
    public static final int[] STATE_INACTIVE =
            {android.R.attr.state_enabled, -android.R.attr.state_active};
    public static final int[] STATE_FOCUSED =
            {android.R.attr.state_enabled, -android.R.attr.state_active,
                    android.R.attr.state_focused};

    // Reflection을 위한 메소드들
    private static Method getStateDrawableMethod;
    private static Method getStateCountMethod;

    static {
        try {
            getStateDrawableMethod = StateListDrawable.class.getDeclaredMethod("getStateDrawable", int.class);
            getStateDrawableMethod.setAccessible(true);
            getStateCountMethod = StateListDrawable.class.getDeclaredMethod("getStateCount");
            getStateCountMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Reflection setup failed", e);
        }
    }

    private float mTranslationX = 0.0f;
    private float mTranslationY = 0.0f;
    private float mPositionX = 0.0f;
    private float mPositionY = 0.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mAlpha = 1.0f;
    private Drawable mDrawable;
    private boolean mEnabled = true;
    private final int mResourceId;

    public TargetDrawable(Resources res, int resId) {
        mResourceId = resId;
        setDrawable(res, resId);
    }

    public TargetDrawable(Drawable drawable) {
        mResourceId = drawable.hashCode();
        setDrawable(drawable);
    }

    public void setDrawable(Resources res, int resId) {
        Drawable drawable = resId == 0 ? null : res.getDrawable(resId);
        mDrawable = drawable != null ? drawable.mutate() : null;
        resizeDrawables();
        setState(STATE_INACTIVE);
    }

    public void setDrawable(Drawable drawableResource) {
        Drawable drawable = drawableResource;
        mDrawable = drawable != null ? drawable.mutate() : null;
        resizeDrawables();
        setState(STATE_INACTIVE);
    }

    public TargetDrawable(TargetDrawable other) {
        mResourceId = other.mResourceId;
        mDrawable = other.mDrawable != null ? other.mDrawable.mutate() : null;
        resizeDrawables();
        setState(STATE_INACTIVE);
    }

    public void setState(int[] state) {
        if (mDrawable instanceof StateListDrawable) {
            StateListDrawable d = (StateListDrawable) mDrawable;
            d.setState(state);
        }
    }

    public boolean hasState(int[] state) {
        if (mDrawable instanceof StateListDrawable) {
            StateListDrawable d = (StateListDrawable) mDrawable;
            int[][] states = getStateArray(d);
            for (int[] existingState : states) {
                if (Arrays.equals(existingState, state)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[][] getStateArray(StateListDrawable d) {
        int stateCount = getStateCount(d);
        int[][] states = new int[stateCount][];
        for (int i = 0; i < stateCount; i++) {
            states[i] = getStateDrawable(d, i).getState();
        }
        return states;
    }

    private int getStateCount(StateListDrawable d) {
        try {
            return (int) getStateCountMethod.invoke(d);
        } catch (Exception e) {
            Log.e(TAG, "Couldn't access StateListDrawable#getStateCount method", e);
            return 0;
        }
    }

    private Drawable getStateDrawable(StateListDrawable d, int index) {
        try {
            return (Drawable) getStateDrawableMethod.invoke(d, index);
        } catch (Exception e) {
            Log.e(TAG, "Couldn't access StateListDrawable#getStateDrawable method", e);
            return null;
        }
    }

    public void setTintColor(int tintColor) {
        DrawableCompat.setTint(mDrawable, tintColor);
    }

    public boolean isActive() {
        return hasState(STATE_FOCUSED);
    }

    public boolean isEnabled() {
        return mDrawable != null && mEnabled;
    }

    private void resizeDrawables() {
        if (mDrawable instanceof StateListDrawable) {
            StateListDrawable d = (StateListDrawable) mDrawable;
            int maxWidth = 0;
            int maxHeight = 0;
            int stateCount = getStateCount(d);
            for (int i = 0; i < stateCount; i++) {
                Drawable childDrawable = getStateDrawable(d, i);
                if (childDrawable != null) {
                    maxWidth = Math.max(maxWidth, childDrawable.getIntrinsicWidth());
                    maxHeight = Math.max(maxHeight, childDrawable.getIntrinsicHeight());
                }
            }
            d.setBounds(0, 0, maxWidth, maxHeight);
            for (int i = 0; i < stateCount; i++) {
                Drawable childDrawable = getStateDrawable(d, i);
                if (childDrawable != null) {
                    childDrawable.setBounds(0, 0, maxWidth, maxHeight);
                }
            }
        } else if (mDrawable != null) {
            mDrawable.setBounds(0, 0,
                    mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }
    }

    public void setX(float x) {
        mTranslationX = x;
    }

    public void setY(float y) {
        mTranslationY = y;
    }

    public void setScaleX(float x) {
        mScaleX = x;
    }

    public void setScaleY(float y) {
        mScaleY = y;
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public float getX() {
        return mTranslationX;
    }

    public float getY() {
        return mTranslationY;
    }

    public float getScaleX() {
        return mScaleX;
    }

    public float getScaleY() {
        return mScaleY;
    }

    public float getAlpha() {
        return mAlpha;
    }

    public void setPositionX(float x) {
        mPositionX = x;
    }

    public void setPositionY(float y) {
        mPositionY = y;
    }

    public float getPositionX() {
        return mPositionX;
    }

    public float getPositionY() {
        return mPositionY;
    }

    public int getWidth() {
        return mDrawable != null ? mDrawable.getIntrinsicWidth() : 0;
    }

    public int getHeight() {
        return mDrawable != null ? mDrawable.getIntrinsicHeight() : 0;
    }

    public void draw(Canvas canvas) {
        if (mDrawable == null || !mEnabled) {
            return;
        }
        canvas.save();
        canvas.scale(mScaleX, mScaleY, mPositionX, mPositionY);
        canvas.translate(mTranslationX + mPositionX, mTranslationY + mPositionY);
        canvas.translate(-0.5f * getWidth(), -0.5f * getHeight());
        mDrawable.setAlpha(Math.round(mAlpha * 255f));
        mDrawable.draw(canvas);
        canvas.restore();
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public int getResourceId() {
        return mResourceId;
    }
}