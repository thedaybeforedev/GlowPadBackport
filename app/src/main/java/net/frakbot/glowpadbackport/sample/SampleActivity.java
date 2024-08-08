package net.frakbot.glowpadbackport.sample;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import net.frakbot.glowpadbackport.GlowPadView;
import net.frakbot.glowpadbackport.R;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button toggleBtn = (Button) findViewById(R.id.btn_toggle_padmult);
        final GlowPadView glowPad = (GlowPadView) findViewById(R.id.incomingCallWidget);


        bindGlowPadButtons(glowPad);



        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currMult = glowPad.getPointsMultiplier();
                final int[] mults = {1, 2, 3, 4, 6, 8, 10, 12, 16, 20, 32};

                int multIndex = 0;
                for (int i = 0; i < mults.length; i++) {
                    if (mults[i] == currMult) {
                        multIndex = i;
                    }
                }

                multIndex++;
                if (multIndex >= mults.length) {
                    multIndex = 0;
                }

                glowPad.setPointsMultiplier(mults[multIndex]);

                Toast.makeText(SampleActivity.this, "Multi: " + mults[multIndex], Toast.LENGTH_SHORT).show();

                glowPad.ping();
            }
        });

        glowPad.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
            @Override
            public void onGrabbed(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onReleased(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onTrigger(View v, int target) {
                Toast.makeText(SampleActivity.this, "Target triggered! ID=" + target, Toast.LENGTH_SHORT).show();
                glowPad.reset(true);
            }

            @Override
            public void onGrabbedStateChange(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onFinishFinalAnimation() {
                // Do nothing
            }
        });
    }

    private void bindGlowPadButtons(GlowPadView glowPadView){

        ArrayList<Drawable> targetDrawables = new ArrayList<>();

//        for (int  i=0; i<100; i++){
//            {
//                Bitmap pressBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_activated.png");
//                Bitmap checkBmp = getBitmapFromAsset(this, "ic_lockscreen_decline_activated.png");
//                Bitmap normalBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_normal.png");
//                Drawable pressDraw = new BitmapDrawable(getResources(), pressBmp);
//                Drawable checkDraw = new BitmapDrawable(getResources(), checkBmp);
//                Drawable normalDraw = new BitmapDrawable(getResources(), normalBmp);
//                StateListDrawable drawable = new StateListDrawable(); drawable.addState(new int[]{android.R.attr.state_focused}, pressDraw);
//                drawable.addState(new int[]{android.R.attr.state_active}, checkDraw);
//                drawable.addState(new int[]{}, normalDraw);
//
//            }
//        }
        {
            Bitmap pressBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_activated.png");
            Bitmap checkBmp = getBitmapFromAsset(this, "ic_lockscreen_decline_activated.png");
            Bitmap normalBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_normal.png");
            Drawable pressDraw = new BitmapDrawable(getResources(), pressBmp);
            Drawable checkDraw = new BitmapDrawable(getResources(), checkBmp);
            Drawable normalDraw = new BitmapDrawable(getResources(), normalBmp);
            StateListDrawable drawable = new StateListDrawable(); drawable.addState(new int[]{android.R.attr.state_focused}, pressDraw);
            drawable.addState(new int[]{android.R.attr.state_active}, checkDraw);
            drawable.addState(new int[]{}, normalDraw);
            targetDrawables.add(drawable);
        }
        {
            Bitmap pressBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_lockscreen_glowdot);
            Bitmap checkBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_lockscreen_glowdot);
            Bitmap normalBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_lockscreen_glowdot);
            Drawable pressDraw = new BitmapDrawable(getResources(), pressBmp);
            Drawable checkDraw = new BitmapDrawable(getResources(), checkBmp);
            Drawable normalDraw = new BitmapDrawable(getResources(), normalBmp);
            StateListDrawable drawable = new StateListDrawable(); drawable.addState(new int[]{android.R.attr.state_focused}, pressDraw);
            drawable.addState(new int[]{android.R.attr.state_active}, checkDraw);
            drawable.addState(new int[]{}, normalDraw);
            targetDrawables.add(drawable);
        }
        {
            Bitmap pressBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_activated.png");
            Bitmap checkBmp = getBitmapFromAsset(this, "ic_lockscreen_decline_activated.png");
            Bitmap normalBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_normal.png");
            Drawable pressDraw = new BitmapDrawable(getResources(), pressBmp);
            Drawable checkDraw = new BitmapDrawable(getResources(), checkBmp);
            Drawable normalDraw = new BitmapDrawable(getResources(), normalBmp);
            StateListDrawable drawable = new StateListDrawable(); drawable.addState(new int[]{android.R.attr.state_focused}, pressDraw);
            drawable.addState(new int[]{android.R.attr.state_active}, checkDraw);
            drawable.addState(new int[]{}, normalDraw);
            targetDrawables.add(drawable);
        }
        {
            Bitmap pressBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_activated.png");
            Bitmap checkBmp = getBitmapFromAsset(this, "ic_lockscreen_decline_activated.png");
            Bitmap normalBmp = getBitmapFromAsset(this, "ic_lockscreen_answer_normal.png");
            Drawable pressDraw = new BitmapDrawable(getResources(), pressBmp);
            Drawable checkDraw = new BitmapDrawable(getResources(), checkBmp);
            Drawable normalDraw = new BitmapDrawable(getResources(), normalBmp);
            StateListDrawable drawable = new StateListDrawable(); drawable.addState(new int[]{android.R.attr.state_focused}, pressDraw);
            drawable.addState(new int[]{android.R.attr.state_active}, checkDraw);
            drawable.addState(new int[]{}, normalDraw);

            targetDrawables.add(drawable);
        }

            Bitmap pressBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_in_call_touch_handle_normal);
            Bitmap checkBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_lockscreen_answer_activated);
            Bitmap normalBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_in_call_touch_handle_normal);
            Drawable pressDraw = new BitmapDrawable(getResources(), pressBmp);
            Drawable checkDraw = new BitmapDrawable(getResources(), checkBmp);
            Drawable normalDraw = new BitmapDrawable(getResources(), normalBmp);
            StateListDrawable handleDrawable = new StateListDrawable(); handleDrawable.addState(new int[]{android.R.attr.state_focused}, pressDraw);
            handleDrawable.addState(new int[]{android.R.attr.state_active}, checkDraw);
            handleDrawable.addState(new int[]{}, normalDraw);


        Bitmap pointBitmap = getBitmapFromAsset(this, "bullet_disk.png");
        Drawable pointDrawable = new BitmapDrawable(getResources(), pointBitmap);


        glowPadView.setTargetResources(handleDrawable, targetDrawables);

        glowPadView.getHandleDrawable().setTintColor(Color.RED);

//        glowPadView.setPointCloudDrawable(pointDrawable);

    }


    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }
}
