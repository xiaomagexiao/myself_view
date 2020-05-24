package com.example.myselfview.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;

import com.example.myself_view.R;
import com.example.myselfview.adapter.MCNotePagerAdapter;
import com.example.myselfview.view.CustomScrollView;

public class ScrollListPagerActivity extends FragmentActivity {
	//private MCNotePagerAdapter adapter;
	private CustomScrollView mScrollView;
	private ViewPager mViewPager;
	private LinearLayout view_pager_container;
	private MCNotePagerAdapter adapter;
	private int current;

	protected void onCreate(android.os.Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.control_detail);
		initView();
	}

	private void initView() {
		this.mScrollView = (CustomScrollView) this.findViewById(R.id.mScrollView);
		this.mViewPager = (ViewPager) this.findViewById(R.id.foruminfo_vp_repyly);

		this.view_pager_container = (LinearLayout) this.findViewById(R.id.view_pager_container);
		this.mScrollView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				view_pager_container.setLayoutParams(new LinearLayout.LayoutParams(-1, mScrollView.getMeasuredHeight()));
				mScrollView.getViewTreeObserver().removeOnPreDrawListener(this);
				return true;
			}
		});

		adapter = new MCNotePagerAdapter(this.getSupportFragmentManager(), "402814a14a2981de014a2cb874aa1cf2");
		mViewPager.setAdapter(adapter);

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				update(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	};

	private void update(int index) {
		if (current == index)
			return;
		if (mViewPager.getCurrentItem() != index)
			mViewPager.setCurrentItem(index);
		current = index;
	}
}
