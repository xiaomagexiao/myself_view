package com.whaty.base.asyncimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class MCImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageContainer mImageContainer;
    private int mImageHeight;
    private ImageLoader mImageLoader;
    private int mImageWidth;
    private String mUrl;

    public MCImageView(Context context) {
        this(context, null);
    }

    public MCImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MCImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }

    private void loadImageIfNecessary(final boolean isInLayoutPass) {
        int v1 = -2;
        try {
            int v9 = this.getWidth();
            int v6 = this.getHeight();
            int v7 = this.getLayoutParams() == null || this.getLayoutParams().height != v1 || this.getLayoutParams().width != v1 ? 0 : 1;
            if(v9 != 0 || v6 != 0 || v7 != 0) {
                if(TextUtils.isEmpty(this.mUrl)) {
                    if(this.mImageContainer != null) {
                        this.mImageContainer.cancelRequest();
                        this.mImageContainer = null;
                    }

                    this.setDefaultImageOrNull();
                    return;
                }

                if(this.mImageContainer != null && this.mImageContainer.getRequestUrl() != null) {
                    if(!this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                        this.mImageContainer.cancelRequest();
                        this.setDefaultImageOrNull();
                    }
                    else {
                        return;
                    }
                }

                this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageListener() {
                    public void onErrorResponse(VolleyError error) {
                        if(MCImageView.this.mErrorImageId != 0) {
                            MCImageView.this.setImageResource(MCImageView.this.mErrorImageId);
                        }
                    }

                    public void onResponse(final ImageContainer response, boolean isImmediate) {
                        if((isImmediate) && (isInLayoutPass)) {
                            MCImageView.this.post(new Runnable() {
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                        }
                        else if(response.getBitmap() != null) {
                            MCImageView.this.setImageBitmap(response.getBitmap());
                        }
                        else if(MCImageView.this.mDefaultImageId != 0) {
                            MCImageView.this.setImageResource(MCImageView.this.mDefaultImageId);
                        }
                    }
                }, this.mImageWidth, this.mImageHeight);
            }

            return;
        }
        catch(Exception v0) {
        	v0.printStackTrace();
            return;
        }
    }

    protected void onDetachedFromWindow() {
        Bitmap v1 = null;
        if(this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            this.setImageBitmap(v1);
            this.mImageContainer = null;
        }

        super.onDetachedFromWindow();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.loadImageIfNecessary(true);
    }

    private void setDefaultImageOrNull() {
        if(this.mDefaultImageId != 0) {
            this.setImageResource(this.mDefaultImageId);
        }
    }

    public void setDefaultImageResId(int defaultImage) {
        this.mDefaultImageId = defaultImage;
    }

    public void setErrorImageResId(int errorImage) {
        this.mErrorImageId = errorImage;
    }

    public void setImageBitmap(Bitmap bm) {

        super.setImageBitmap(bm);
    }

    public void setImageUrl(String url, ImageLoader imageLoader, int width, int height, boolean isNeedScaled) {
        this.mUrl = url;
        this.mImageLoader = imageLoader;
        this.mImageWidth = width;
        this.mImageHeight = height;
        this.loadImageIfNecessary(false);
    }

    public void setImageUrl(String url, ImageLoader imageLoader) {
        this.setImageUrl(url, imageLoader, 0, 0, false);
    }
}

