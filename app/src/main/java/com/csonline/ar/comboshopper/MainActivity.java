package com.csonline.ar.comboshopper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
CardView share;
RelativeLayout relativeLayouts;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scard);
        share=(CardView)findViewById(R.id.share);
        relativeLayouts=(RelativeLayout)findViewById(R.id.relativeLayoutss);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share.setVisibility(View.GONE);
               relativeLayouts.setDrawingCacheEnabled(true);

               // relativeLayouts.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.UNSPECIFIED),
                       // View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.UNSPECIFIED));
               // relativeLayouts.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

               relativeLayouts.buildDrawingCache(true);

                Bitmap mBitmap = Bitmap.createBitmap(relativeLayouts.getDrawingCache());
                //Drawable mDrawable = relativeLayouts.getDrawableState();
               // Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                relativeLayouts.setDrawingCacheEnabled(false);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                        mBitmap, "Image Description", null);

                uri = Uri.parse(path);
                Downloaded downloaded = new Downloaded();

                downloaded.execute();
                share.setVisibility(View.VISIBLE);
            }
        });

    }

    private class Downloaded extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... uris) {

            Uri bmpUri = uri;
            if (bmpUri != null) {
                // Construct a ShareIntent with link to image

                Intent shareIntent = new Intent();


                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "" +
                        "DownloadApp:(https://play.google.com/store/apps/details?id=com.ar.motivateme.motivateme)");
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/*");

                // Launch sharing dialog for image
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            } else {
                // ...sharing failed, handle error
            }
            return null;
        }

    }



}
