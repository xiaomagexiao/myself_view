package com.example.myselfview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myself_view.R;

public class MCPullToRefreshView extends LinearLayout {

	public interface OnFooterRefreshListener {
		void onFooterRefresh(MCPullToRefreshView arg1);
	}

	public interface OnHeaderRefreshListener {
		void onHeaderRefresh(MCPullToRefreshView arg1);
	}

	public interface OnTouchViewListener {
		void onTouchView(MCPullToRefreshView arg1);
	}

	private static final int DEFAULT_REFRESH = -1;
	private static final int PULL_DOWN_STATE = 1;
	private static final int PULL_TO_REFRESH = 2;
	private static final int PULL_UP_STATE = 0;
	private static final int REFRESHING = 4;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final String TAG = "PullToRefreshView";
	private boolean allowFooterPull;
	private boolean allowHeaderPull;
	private boolean allowSwiped;
	// private AnimationDrawable animationDrawable;
	private boolean hasVerticalScrollbars;
	private boolean isSupportLoadMore;
	private AdapterView mAdapterView;
	private RotateAnimation mFlipAnimation;
	private LinearLayout mFooterLoadingView;
	private ProgressBar mFooterProgressBar;
	private int mFooterState;
	private TextView mFooterTextView;
	private View mFooterView;
	private int mFooterViewHeight;
	private ImageView mHeaderImageView;
	private ProgressBar mHeaderProgressBar;
	private TextView mHeaderTextView;
	private int mHeaderState;
	private View mHeaderView;
	private int mHeaderViewHeight;
	private LayoutInflater mInflater;
	private int mLastMotionY;
	private int mLastTotalItemCount;
	private boolean mLock;
	private OnFooterRefreshListener mOnFooterRefreshListener;
	private OnHeaderRefreshListener mOnHeaderRefreshListener;
	private AdapterView.OnItemClickListener mOnItemClickListener;
	private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
	private AbsListView.OnScrollListener mOnScollListener;
	private OnTouchViewListener mOnTouchViewListener;
	private int mPullState;
	private RotateAnimation mReverseFlipAnimation;
	private ScrollView mScrollView;
	private int mSwipeOffsetLeft;
	private int rowCount;
	private onScollListenter monScollListenter;

	public MCPullToRefreshView(Context context) {
		super(context);
		this.mLastTotalItemCount = 0;
		this.mHeaderState = -1;
		this.mFooterState = -1;
		this.mSwipeOffsetLeft = 0;
		this.rowCount = 1;
		this.allowHeaderPull = false;
		this.allowFooterPull = false;
		this.allowSwiped = false;
		this.isSupportLoadMore = false;
		this.hasVerticalScrollbars = false;
		this.mOnScollListener = new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(monScollListenter!=null)
				monScollListenter.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
				if (!MCPullToRefreshView.this.isRefresh() && (MCPullToRefreshView.this.isSupportLoadMore)
						&& firstVisibleItem + visibleItemCount == totalItemCount && MCPullToRefreshView.this.mFooterView != null
						&& MCPullToRefreshView.this.mFooterView.getVisibility() == 0) {
					View v0 = view.getChildAt(0);
					if (v0 != null) {
						int v1 = v0.getHeight() * totalItemCount;
						if (MCPullToRefreshView.this.mLastTotalItemCount != totalItemCount && v1 > view.getMeasuredHeight()) {
							MCPullToRefreshView.this.footerRefreshing(true);
							MCPullToRefreshView.this.mLastTotalItemCount = totalItemCount;
						}
					}
				}
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(monScollListenter!=null)
				monScollListenter.onScrollStateChanged(view, scrollState);
			}
		};
		this.init();
	}

	public static abstract interface onScollListenter {
		public abstract void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
		public abstract void onScrollStateChanged(AbsListView view, int scrollState);

	}
	//listview 滚动事件回调
    public void setOnScollListenter(onScollListenter onScollListenter){
    	this.monScollListenter =onScollListenter;
    }
	
	
	public MCPullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mLastTotalItemCount = 0;
		this.mHeaderState = -1;
		this.mFooterState = -1;
		this.mSwipeOffsetLeft = 0;
		this.rowCount = 1;
		this.allowHeaderPull = false;
		this.allowFooterPull = false;
		this.allowSwiped = false;
		this.isSupportLoadMore = false;
		this.hasVerticalScrollbars = false;
		this.mOnScollListener = new AbsListView.OnScrollListener() {
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(monScollListenter!=null)
				monScollListenter.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
				if (!MCPullToRefreshView.this.isRefresh() && (MCPullToRefreshView.this.isSupportLoadMore)
						&& firstVisibleItem + visibleItemCount == totalItemCount && MCPullToRefreshView.this.mFooterView != null
						&& MCPullToRefreshView.this.mFooterView.getVisibility() == 0) {
					View v0 = view.getChildAt(0);
					if (v0 != null) {
						int v1 = v0.getHeight() * totalItemCount;
						if (MCPullToRefreshView.this.mLastTotalItemCount != totalItemCount && v1 > view.getMeasuredHeight()) {
							MCPullToRefreshView.this.footerRefreshing(true);
							MCPullToRefreshView.this.mLastTotalItemCount = totalItemCount;
						}
					}
				}
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(monScollListenter!=null)
				monScollListenter.onScrollStateChanged(view, scrollState);
			}
		};
		// TypedArray v0 = context.obtainStyledAttributes(attrs,
		// com_mooc_uikit_refreshview_MCPullToRefreshView);
		// TypedArray v0 = context.obtainStyledAttributes(attrs,
		// R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView, 0, 0);
		TypedArray v0 = context.obtainStyledAttributes(attrs, R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView);
		this.rowCount = v0.getInteger(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_rowCount, 1);
		this.allowHeaderPull = v0.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_allowHeaderPull, true);
		this.allowFooterPull = v0.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_allowFooterPull, true);
		this.allowSwiped = v0.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_allowSwipe, false);
		this.mSwipeOffsetLeft = v0.getDimensionPixelSize(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_swipelistOffsetLeft, 0);
		this.isSupportLoadMore = v0.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_supportLoadMore, true);
		this.hasVerticalScrollbars = v0.getBoolean(R.styleable.com_mooc_uikit_refreshview_MCPullToRefreshView_hasVerticalScrollbars, false);
		this.init();
	}

	public void addAdapterHeaderView(View v) {
		if (this.rowCount == 1) {
			((ListView) this.mAdapterView).addHeaderView(v);
		}
	}

	  /**
	   * 返回当前顶部可见Items
	   * @return
	   */
	public int getFirstVisiblePosition() {
		return ((ListView) this.mAdapterView).getFirstVisiblePosition();
	}
	
	private void addFooterView() {
		this.mFooterView = this.mInflater.inflate(R.layout.refresh_footer, ((ViewGroup) this), false);
		this.mFooterLoadingView = (LinearLayout) this.mFooterView.findViewById(R.id.loading_data);
		this.mFooterTextView = (TextView) this.mFooterView.findViewById(R.id.pull_to_load_text);
		this.mFooterProgressBar = (ProgressBar) this.mFooterView.findViewById(R.id.pull_to_load_progress);
		this.measureView(this.mFooterView);
		this.mFooterViewHeight = this.mFooterView.getMeasuredHeight();
		this.addView(this.mFooterView, new LinearLayout.LayoutParams(-1, this.mFooterViewHeight));
	}

	private void addHeaderView() {
		// this.mHeaderView = this.mInflater.inflate(R.layout.refresh_header,
		// ((ViewGroup)this), false);
		this.mHeaderView = this.mInflater.inflate(R.layout.my_refresh_header, ((ViewGroup) this), false);
		this.mHeaderImageView = (ImageView) this.mHeaderView.findViewById(R.id.head_image);
		this.mHeaderProgressBar = (ProgressBar) this.mHeaderView.findViewById(R.id.pb_refresh);
		mHeaderTextView = (TextView) this.mHeaderView.findViewById(R.id.tv_refresh);
		this.measureView(this.mHeaderView);
		this.mHeaderViewHeight = this.mHeaderView.getMeasuredHeight();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, this.mHeaderViewHeight);
		layoutParams.topMargin = -this.mHeaderViewHeight;
		this.addView(this.mHeaderView, ((ViewGroup.LayoutParams) layoutParams));
	}

	private int changingHeaderViewTopMargin(int deltaY) {
		int result;
		ViewGroup.LayoutParams params = this.mHeaderView.getLayoutParams();
		float margin = (((float) ((LinearLayout.LayoutParams) params).topMargin)) + (((float) deltaY)) * 0.3f;
		if (deltaY <= 0 || this.mPullState != 0 || Math.abs(((LinearLayout.LayoutParams) params).topMargin) > this.mHeaderViewHeight) {
			if (deltaY < 0 && this.mPullState == 1) {
				Math.abs(((LinearLayout.LayoutParams) params).topMargin);
			}

			Log.i("PullToRefreshView", "newTopMargin:" + margin + "  mHeaderViewHeight:" + this.mHeaderViewHeight);
			((LinearLayout.LayoutParams) params).topMargin = ((int) margin);
			this.mHeaderView.setLayoutParams(params);
			this.invalidate();
			result = ((LinearLayout.LayoutParams) params).topMargin;
		} else {
			result = ((LinearLayout.LayoutParams) params).topMargin;
		}

		return result;
	}

	private void footerPrepareToRefresh(int deltaY) {
		int footState = 3;
		if (this.allowFooterPull) {
			int v0 = this.changingHeaderViewTopMargin(deltaY);
			if (Math.abs(v0) >= this.mHeaderViewHeight + this.mFooterViewHeight && this.mFooterState != footState) {
				this.mFooterTextView.setText("");
				this.mFooterState = 3;
			} else if (Math.abs(v0) < this.mHeaderViewHeight + this.mFooterViewHeight) {
				this.mFooterTextView.setText("");
				this.mFooterState = 2;
			}

			if (this.isSupportLoadMore) {
				return;
			}

			this.mFooterProgressBar.setVisibility(View.GONE);
		}
	}

	private void footerRefreshing(boolean showProgressBar) {
		if (this.mFooterLoadingView.getVisibility() == 0) {
			this.mFooterState = 4;
			this.setHeaderTopMargin(-(this.mHeaderViewHeight + this.mFooterViewHeight));
			if (showProgressBar) {
				this.mFooterProgressBar.setVisibility(View.VISIBLE);
				this.mFooterTextView.setVisibility(View.VISIBLE);
				this.mFooterTextView.setText(R.string.pull_to_refresh_loading_more);
				if (this.mOnFooterRefreshListener != null) {
					this.mOnFooterRefreshListener.onFooterRefresh(this);
				}
			} else {
				this.onFooterRefreshComplete();
			}
		} else {
			this.setHeaderTopMargin(-this.mHeaderViewHeight);
		}
	}

	private int getHeaderTopMargin() {
		// 这种临时的解决办法会引起一些其他问题，很难查找，所以尽量原来的逻辑一点都不要变。
		return ((LinearLayout.LayoutParams) (this.mHeaderView.getLayoutParams())).topMargin;
	}

	private void headerPrepareToRefresh(int deltaY) {
		int v3 = 3;
		if (this.allowHeaderPull) {
			int v0 = this.changingHeaderViewTopMargin(deltaY);
			if (v0 >= 0 && this.mHeaderState != v3) {
				this.mHeaderProgressBar.setVisibility(View.GONE);
				this.mHeaderImageView.setVisibility(View.VISIBLE);
				// this.setBackground(R.drawable.head_image_default);
				this.setBackground(R.drawable.refresh_up);
				this.mHeaderTextView.setText(getResources().getString(R.string.pull_to_refresh_release_label));
				this.mHeaderState = v3;
				return;
			}

			if (v0 >= 0) {
				return;
			}

			if (v0 <= -this.mHeaderViewHeight) {
				return;
			}
			this.mHeaderProgressBar.setVisibility(View.GONE);
			this.mHeaderImageView.setVisibility(View.VISIBLE);
			this.mHeaderTextView.setText(getResources().getString(R.string.pull_to_refresh_pull_label));
			// this.setBackground(R.drawable.head_image_default);
			this.setBackground(R.drawable.refresh_down);
			this.mHeaderState = 2;
		}
	}

	public void headerRefreshing() {
		this.mHeaderState = 4;
		this.setHeaderTopMargin(0);
		// this.mHeaderImageView.setVisibility(View.VISIBLE);
		this.mHeaderImageView.setVisibility(View.GONE);
		this.mHeaderProgressBar.setVisibility(View.VISIBLE);
		this.mHeaderTextView.setText(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		// 红色的加载进度条
		// this.setBackground(R.drawable.head_image_background);
		/*
		 * this.animationDrawable = (AnimationDrawable)
		 * this.mHeaderImageView.getBackground(); if(this.animationDrawable !=
		 * null && (this.animationDrawable.isRunning())) {
		 * this.animationDrawable.stop(); }
		 * 
		 * this.animationDrawable.start();
		 */
		if (this.mOnHeaderRefreshListener != null) {
			this.mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	private void init() {
		this.setOrientation(1);
		this.mFlipAnimation = new RotateAnimation(0f, -180f, 1, 0.5f, 1, 0.5f);
		this.mFlipAnimation.setInterpolator(new LinearInterpolator());
		this.mFlipAnimation.setDuration(250);
		this.mFlipAnimation.setFillAfter(true);
		this.mReverseFlipAnimation = new RotateAnimation(-180f, 0f, 1, 0.5f, 1, 0.5f);
		this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		this.mReverseFlipAnimation.setDuration(250);
		this.mReverseFlipAnimation.setFillAfter(true);
		this.mInflater = LayoutInflater.from(this.getContext());
		this.addHeaderView();
	}

	public void initAdapterView() {
		ViewGroup v10 = null;
		int v7 = -1;
		View v0 = LayoutInflater.from(this.getContext()).inflate(R.layout.refresh_framelayout, v10);
		if (this.rowCount == 1) {
			View v2 = LayoutInflater.from(this.getContext()).inflate(R.layout.listview, v10);
			if (this.hasVerticalScrollbars) {
				((ListView) v2).setVerticalScrollBarEnabled(true);
			} else {
				((ListView) v2).setVerticalScrollBarEnabled(false);
			}

			((ListView) v2).setOnItemClickListener(this.mOnItemClickListener);
			((ListView) v2).setOnItemLongClickListener(this.mOnItemLongClickListener);
			((ListView) v2).setOnScrollListener(this.mOnScollListener);
			((FrameLayout) v0).addView(v2, 0, new FrameLayout.LayoutParams(v7, v7));
		} else {
			View v1 = LayoutInflater.from(this.getContext()).inflate(R.layout.gridview, v10);
			((GridView) v1).setOnItemClickListener(this.mOnItemClickListener);
			((GridView) v1).setOnItemLongClickListener(this.mOnItemLongClickListener);
			FrameLayout.LayoutParams v3 = new FrameLayout.LayoutParams(v7, v7);
			((GridView) v1).setNumColumns(this.rowCount);
			((FrameLayout) v0).addView(v1, 0, ((ViewGroup.LayoutParams) v3));
		}

		this.addView(v0, 1, new LinearLayout.LayoutParams(v7, v7));
	}

	private void initContentAdapterView() {
		if (this.getChildCount() < 3) {
			throw new IllegalArgumentException("this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}

		View v2 = ((ViewGroup) this.getChildAt(1)).getChildAt(0);
		if ((v2 instanceof AdapterView)) {
			this.mAdapterView = ((AdapterView) v2);
		}

		if (this.mAdapterView == null && this.mScrollView == null) {
			throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
		}
	}

	public boolean isFooterRefresh() {
		boolean v0 = this.mFooterState != -1 ? true : false;
		return v0;
	}

	public boolean isHeaderRefresh() {
		boolean v0 = this.mHeaderState != -1 ? true : false;
		return v0;
	}

	private boolean isRefresh() {
		boolean v0 = (this.isHeaderRefresh()) || (this.isFooterRefresh()) ? true : false;
		return v0;
	}

	private boolean isRefreshViewScroll(int deltaY) {
		View v0;
		int v8 = 4;
		boolean v5 = false;
		if (this.mHeaderState != v8 && this.mFooterState != v8) {
			View v1 = this.getChildAt(1);
			if (this.mAdapterView != null && ((FrameLayout) v1).getChildAt(0).getVisibility() == 0) {
				if (deltaY > 0) {
					v0 = this.mAdapterView.getChildAt(0);
					if (v0 != null) {
						if (this.mAdapterView.getFirstVisiblePosition() == 0 && v0.getTop() == 0) {
							this.mPullState = 1;
							return true;
						}

						int v4 = v0.getTop();
						int v3 = this.mAdapterView.getPaddingTop();
						if (this.mAdapterView.getFirstVisiblePosition() != 0) {
							return v5;
						}

						if (Math.abs(v4 - v3) > 20) {
							return v5;
						}

						this.mPullState = 1;
						v5 = true;
					} else {
					}
				} else {
					if (deltaY >= 0) {
						return v5;
					}

					View v2 = this.mAdapterView.getChildAt(this.mAdapterView.getChildCount() - 1);
					if (v2 == null) {
						return v5;
					}

					if (v2.getBottom() > this.getHeight()) {
						return v5;
					}

					if (this.mAdapterView.getLastVisiblePosition() != this.mAdapterView.getCount() - 1) {
						return v5;
					}

					this.mPullState = 0;
					v5 = true;
				}

				return v5;
			}

			if (this.mScrollView != null && ((FrameLayout) v1).getChildAt(1).getVisibility() == 0) {
				v0 = this.mScrollView.getChildAt(0);
				if (deltaY > 0 && this.mScrollView.getScrollY() == 0) {
					this.mPullState = 1;
					return true;
				}

				if (deltaY >= 0) {
					return v5;
				}

				if (v0.getMeasuredHeight() > this.getHeight() + this.mScrollView.getScrollY()) {
					return v5;
				}

				this.mPullState = 0;
				return true;
			}

			if (this.getChildAt(1) == null) {
				return v5;
			}

			if (deltaY > 0) {
				this.mPullState = 1;
				return true;
			}

			this.mPullState = 0;
			v5 = true;
		}

		return v5;
	}

	private void lock() {
		this.mLock = true;
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams v3 = child.getLayoutParams();
		if (v3 == null) {
			v3 = new ViewGroup.LayoutParams(-1, -2);
		}

		int v1 = ViewGroup.getChildMeasureSpec(0, 0, v3.width);
		int v2 = v3.height;
		int v0 = v2 > 0 ? View.MeasureSpec.makeMeasureSpec(v2, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0);
		child.measure(v1, v0);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		this.initAdapterView();
		this.addFooterView();
		this.initContentAdapterView();
	}

	public void onFooterRefreshComplete() {
		this.setHeaderTopMargin(-this.mHeaderViewHeight);
		this.mFooterTextView.setText("");
		this.mFooterProgressBar.setVisibility(View.GONE);
		this.mFooterState = -1;
	}

	public void onHeaderRefreshComplete() {
		// 这里结束进度条
		this.setHeaderTopMargin(-this.mHeaderViewHeight);
		/*
		 * if(this.animationDrawable != null &&
		 * (this.animationDrawable.isRunning())) {
		 * this.animationDrawable.stop(); }
		 */

		this.setBackground(R.drawable.refresh_down);
		this.mHeaderImageView.setVisibility(View.GONE);
		this.mHeaderState = -1;
		if (this.mFooterLoadingView.getVisibility() != 0) {
			this.mFooterLoadingView.setVisibility(View.VISIBLE);
		}

		this.mFooterView.setVisibility(View.VISIBLE);
		this.mFooterProgressBar.setVisibility(View.VISIBLE);
		this.mFooterTextView.setVisibility(View.VISIBLE);
		this.mLastTotalItemCount = 0;
	}

	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		this.setLastUpdated(lastUpdated);
		this.onHeaderRefreshComplete();
	}

	public boolean onInterceptTouchEvent(MotionEvent e) {
		boolean bool = false;
		switch (e.getAction()) {
		case 0: {
			this.mLastMotionY = ((int) e.getRawY());
			if (this.mOnTouchViewListener == null) {
				return bool;
			}

			this.mOnTouchViewListener.onTouchView(this);
			break;
		}
		case 2: {
			int v0 = (((int) e.getRawY())) - this.mLastMotionY;
			Log.i("PullToRefreshView", "onInterceptTouchEvent deltaY:" + v0 + "  e.getRawY():" + e.getRawY() + " mLastMotionY:" + this.mLastMotionY);
			if (Math.abs(v0) < 10) {
				return bool;
			}

			if (this.isRefreshViewScroll(v0)) {
				Log.i("PullToRefreshView", "true");
				return true;
			}

			Log.i("PullToRefreshView", "false");
			break;
		}
		}

		return bool;
	}

	public boolean onTouchEvent(MotionEvent event) {
		boolean v3 = true;
		if (!this.mLock) {
			Log.i("PullToRefreshView", "onTouchEvent getAction:" + event.getAction());
			int v2 = ((int) event.getRawY());
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE: {
				int v0 = v2 - this.mLastMotionY;
				Log.i("PullToRefreshView", "onTouchEvent deltaY:" + v0);
				if (this.mPullState == 1) {
					Log.i("PullToRefreshView", " pull down!parent view move!");
					this.headerPrepareToRefresh(v0);
				} else if (this.mPullState == 0) {
					Log.i("PullToRefreshView", "pull up!parent view move!");
					this.footerPrepareToRefresh(v0);
				}

				this.mLastMotionY = v2;
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL: {
				int v1 = this.getHeaderTopMargin();
				if (this.mPullState == 1) {
					if (v1 >= 0) {
						this.headerRefreshing();
						return super.onTouchEvent(event);
					}

					this.setHeaderTopMargin(-this.mHeaderViewHeight);
					return super.onTouchEvent(event);
				}

				if (this.mPullState != 0) {
					return super.onTouchEvent(event);
				}

				if (this.mFooterState != 2 && this.mFooterState != 3) {
					return super.onTouchEvent(event);
				}

				if (Math.abs(v1) >= this.mHeaderViewHeight + this.mFooterViewHeight) {
					this.footerRefreshing(true);// 这里改成true就有下拉效果了，原来是false
					return super.onTouchEvent(event);
				}

				this.setHeaderTopMargin(-this.mHeaderViewHeight);
				break;
			}
			}
			v3 = super.onTouchEvent(event);
		}

		return v3;
	}

	public void setAdapterViewHorizontalSpacing(int horizontalSpacing) {
		if (this.rowCount > 1) {
			((GridView) this.mAdapterView).setHorizontalSpacing(horizontalSpacing);
		}
	}

	public void setAdapterViewPadding(int left, int top, int right, int bottom) {
		this.mAdapterView.setPadding(left, top, right, bottom);
	}

	public void setAdapterViewRecyclerListener(AbsListView.RecyclerListener mRecyclerListener) {
		if (this.rowCount == 1) {
			((AbsListView) this.mAdapterView).setRecyclerListener(mRecyclerListener);
		} else {
			((AbsListView) this.mAdapterView).setRecyclerListener(mRecyclerListener);
		}
	}

	public void setAdapterViewVerticalSpacing(int verticalSpacing) {
		if (this.rowCount > 1) {
			((GridView) this.mAdapterView).setVerticalSpacing(verticalSpacing);
		}
	}

	public void setAdapterViewWhenHasData() {
		View v1 = this.getChildAt(1);
		try {
			((FrameLayout) v1).getChildAt(1).setVisibility(View.GONE);
		} catch (Exception v2) {
		}

		try {
			View v0 = ((FrameLayout) v1).getChildAt(0);
			if (v0 == null) {
				this.initAdapterView();
				return;
			}

			v0.setVisibility(View.VISIBLE);
		} catch (Exception v2) {
		}
	}

	public void setAllowFooterPull(boolean allowFooterPull) {
		this.allowFooterPull = allowFooterPull;
	}

	public void setAllowHeaderPull(boolean allowHeaderPull) {
		this.allowHeaderPull = allowHeaderPull;
	}

	public void setAllowSwipe(boolean allowSwipe) {
		this.allowSwiped = allowSwipe;
	}

	@SuppressLint("NewApi")
	private void setBackground(int drawableId) {
		this.mHeaderImageView.setImageResource(drawableId);
		/*
		 * if(Build.VERSION.SDK_INT < 16) {
		 * this.mHeaderImageView.setBackgroundDrawable
		 * (this.getResources().getDrawable(drawableId)); } else {
		 * this.mHeaderImageView
		 * .setBackground(this.getResources().getDrawable(drawableId)); }
		 */
	}

	public void setDataAdapter(BaseAdapter adapter) {
		if (this.rowCount == 1) {
			this.mAdapterView.setAdapter(((ListAdapter) adapter));
		} else {
			this.mAdapterView.setAdapter(((ListAdapter) adapter));
		}
	}

	public void setGuidanceViewWhenNoData(View view) {
		FrameLayout layOut = (FrameLayout) this.getChildAt(1);
		try {
			layOut.getChildAt(0).setVisibility(View.GONE);
		} catch (Exception v3) {
			v3.printStackTrace();
		}

		try {
			if (layOut.getChildAt(1) == null) {
				layOut.addView(view, 1, new FrameLayout.LayoutParams(-1, -1));
				return;
			}

			layOut.getChildAt(1).setVisibility(View.VISIBLE);
		} catch (Exception v3) {
			v3.printStackTrace();
		}
	}

	private void setHeaderTopMargin(int topMargin) {
		ViewGroup.LayoutParams v0 = this.mHeaderView.getLayoutParams();
		((LinearLayout.LayoutParams) v0).topMargin = topMargin;
		this.mHeaderView.setLayoutParams(v0);
		this.invalidate();
	}

	public void setLastUpdated(CharSequence lastUpdated) {
	}

	public void setNoContentVisibility() {
		this.mFooterView.setVisibility(View.INVISIBLE);
	}

	public void setOnFooterRefreshListener(OnFooterRefreshListener footerRefreshListener) {
		this.mOnFooterRefreshListener = footerRefreshListener;
	}

	public void setOnHeaderRefreshListener(OnHeaderRefreshListener headerRefreshListener) {
		this.mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener arg3) {
		this.mOnItemClickListener = arg3;
		this.mAdapterView.setOnItemClickListener(this.mOnItemClickListener);
	}

	public void setOnItemLongLitener(AdapterView.OnItemLongClickListener arg3) {
		this.mOnItemLongClickListener = arg3;
		this.mAdapterView.setOnItemLongClickListener(this.mOnItemLongClickListener);
	}

	public void setOnTouchViewListener(OnTouchViewListener touchViewListener) {
		this.mOnTouchViewListener = touchViewListener;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setSelection(int position) {
		if (this.mAdapterView != null && ((this.mAdapterView instanceof ListView))) {
			this.mAdapterView.setSelection(position);
		}
	}

	public void setTranscriptMode(int mode) {
		if (this.mAdapterView != null && ((this.mAdapterView instanceof ListView))) {
			((AbsListView) this.mAdapterView).setTranscriptMode(mode);
		}
	}

}
