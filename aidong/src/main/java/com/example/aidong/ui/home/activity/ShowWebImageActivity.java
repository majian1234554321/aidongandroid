package com.example.aidong.ui.home.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.ui.home.view.ZoomableImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ShowWebImageActivity extends Activity {
    /**
     *  在一个activity中销毁指定activity
     */

    public  static ShowWebImageActivity showWebImageActivity;
    private TextView imageTextView = null;
    private String imagePath = null;
    private ZoomableImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showWebImageActivity=this;
        setContentView(R.layout.show_webimage);
        this.imagePath = getIntent().getStringExtra("image");

      /*this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
      imageTextView.setText(this.imagePath);*/
        imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);

        try {
            imageView.setImageBitmap(((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(this.imagePath)).getBitmap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Drawable loadImageFromUrl(String url) throws IOException {

        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("zlb","zlb===");
    }
}
