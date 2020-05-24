package com.example.myselfview.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myself_view.R;
import com.example.myselfview.util.PictureUtils;
import com.whaty.base.asyncimage.MCCacheManager;

public class PicFullScreenShowActivity extends Activity implements
		ViewPager.OnPageChangeListener, View.OnClickListener {

	private ImagePagerAdapter pagerAdapter;
	private ViewPager viewPager;

	private ImageView[] imageArr;
	private LinearLayout layout;
	int currentIndex = 0;
	private List<String> imgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fullscreen_pic_pager);

		imgList = (List) this.getIntent().getSerializableExtra("imgPath");
		if (imgList == null) {
			imgList = new ArrayList<String>();
		}
		currentIndex = this.getIntent().getIntExtra("startIndex", 0);
		if (currentIndex < 0 || currentIndex >= imgList.size()) {
			currentIndex = 0;
		}
		initView();
	}

	private void initView() {
		this.viewPager = (ViewPager) ((ViewPager) findViewById(R.id.viewpager));

		pagerAdapter = new ImagePagerAdapter(this);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(currentIndex);
		viewPager.setOnPageChangeListener(this);
		this.layout = (LinearLayout) ((LinearLayout) findViewById(R.id.view_idx_layout));

		imageArr = new ImageView[imgList.size()];

		if (this.imgList.size() < 2) {
			this.layout.setVisibility(View.GONE);
		} else {
			for (int i = 0; i < this.imgList.size(); i++) {
				this.imageArr[i] = new ImageView(this);
				LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
						-2, -2);
				localLayoutParams.gravity = 16;
				int padding = getResources().getDimensionPixelOffset(
						R.dimen.startup_pg_padding);
				localLayoutParams.bottomMargin = getResources()
						.getDimensionPixelOffset(R.dimen.startup_pg_margin_bot);
				this.imageArr[i].setImageResource(R.drawable.startup_point_sec);
				this.imageArr[i].setLayoutParams(localLayoutParams);
				this.imageArr[i].setPadding(padding, padding, padding, padding);
				this.layout.addView(this.imageArr[i]);
				this.imageArr[i].setEnabled(true);
				this.imageArr[i].setOnClickListener(this);
				this.imageArr[i].setTag(Integer.valueOf(i));
			}
			this.imageArr[this.currentIndex].setEnabled(false);
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private LayoutInflater inflater;
		private Context mContext;

		ImagePagerAdapter(Context context) {
			this.mContext = context;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public int getCount() {
			return imgList.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(
					R.layout.pic_item_fullscreen_layout, view, false);
			PhotoView imageView = (PhotoView) imageLayout
					.findViewById(R.id.image);
			
			// 这里应该如何处理一下，显示图片的加载进度呢？
			String path = imgList.get(position);
			File file = new File(path);
			if (path.startsWith("http:") && !file.exists()) {
				imageView.setImageUrl(path, MCCacheManager.getInstance()
						.getImageLoader());
			} else {
				imageView.setImageBitmap(PictureUtils.getBitmapByPath(imgList
						.get(position)));
			}
			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int paramInt) {
		if (paramInt < 0 || imageArr == null)
			return;
		this.imageArr[paramInt].setEnabled(false);
		this.imageArr[this.currentIndex].setEnabled(true);
		this.currentIndex = paramInt;
	}

	@Override
	public void onClick(View view) {
		int i = ((Integer) view.getTag()).intValue();
		if (i >= 0 && i < this.imgList.size()) {
			this.viewPager.setCurrentItem(i);
		}
	}

}
