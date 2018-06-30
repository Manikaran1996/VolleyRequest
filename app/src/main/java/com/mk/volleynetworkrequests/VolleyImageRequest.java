package com.mk.volleynetworkrequests;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class VolleyImageRequest extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private int imageViewWidthInDp, imageViewHeightInDp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_request_activity);
        imageView = findViewById(R.id.imageView);
        imageViewHeightInDp = 320;
        imageViewWidthInDp = 320;
        Button downloadButton = findViewById(R.id.downloadBtn);
        downloadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendRequest();

    }

    private void sendRequest() {
        String url = Application.getInstance().getUrl();
        if(url != null)
            url += "/get_image";
        else
            return;

        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageViewWidthInDp, displayMetrics),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageViewHeightInDp, displayMetrics),
                ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolleyImageRequest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRequestQueue.getInstance(this.getApplicationContext()).addToRequestQueue(imageRequest);
    }
}
