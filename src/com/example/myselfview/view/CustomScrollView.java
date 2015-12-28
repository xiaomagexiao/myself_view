// Decompiled by JEB v1.5.201404100

package com.example.myselfview.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;


public class CustomScrollView extends ScrollView {
    private static final int CLOSE_ENOUGH = 2;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
	private static final String TAG = "CustomScrollView";
    private int mActivePointerId;
    private int mCloseEnough;
    private int mDefaultGutterSize;
    private int mFlingDistance;
    private int mGutterSize;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mSeenPositionMax;
    private int mSeenPositionMin;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public CustomScrollView(Context arg2) {
        super(arg2);
        this.mActivePointerId = -1;
        this.init();
    }

    public CustomScrollView(Context arg2, AttributeSet arg3) {
        super(arg2, arg3);
        this.mActivePointerId = -1;
        this.init();
    }

    protected boolean canScroll(View arg11, boolean arg12, int arg13, int arg14, int arg15) {
        boolean v0;
        if(arg13 < 0) {
            this.getChildAt(0);
            if(this.getScrollY() != this.getChildAt(0).getHeight() - this.getHeight()) {
                v0 = false;
            }
            else {
            	if((arg11 instanceof ViewGroup)) {
                    View v6 = arg11;
                    int v8 = arg11.getScrollX();
                    int v9 = arg11.getScrollY();
                    int v7;
                    for(v7 = ((ViewGroup)v6).getChildCount() - 1; v7 >= 0; --v7) {
                        View v1 = ((ViewGroup)v6).getChildAt(v7);
                        if(arg14 + v8 >= v1.getLeft() && arg14 + v8 < v1.getRight() && arg15 + v9 >= v1.getTop() && arg15 + v9 < v1.getBottom() && (this.canScroll(v1, true, arg13, arg14 + v8 - v1.getLeft(), arg15 + v9 - v1.getTop()))) {
                            return true;
                        }
                    }
                }

                if((arg12) && (ViewCompat.canScrollVertically(arg11, -arg13))) {
                    return true;
                }

                v0 = false;
            }
        }
        else if(arg13 <= 0) {
            if((arg11 instanceof ViewGroup)) {
                View v6 = arg11;
                int v8 = arg11.getScrollX();
                int v9 = arg11.getScrollY();
                int v7;
                for(v7 = ((ViewGroup)v6).getChildCount() - 1; v7 >= 0; --v7) {
                    View v1 = ((ViewGroup)v6).getChildAt(v7);
                    if(arg14 + v8 >= v1.getLeft() && arg14 + v8 < v1.getRight() && arg15 + v9 >= v1.getTop() && arg15 + v9 < v1.getBottom() && (this.canScroll(v1, true, arg13, arg14 + v8 - v1.getLeft(), arg15 + v9 - v1.getTop()))) {
                        return true;
                    }
                }
            }

            if((arg12) && (ViewCompat.canScrollVertically(arg11, -arg13))) {
                return true;
            }

            v0 = false;
        }
        else if((arg11 instanceof MCPullToRefreshView)) {
            View v0_1 = arg11;
            if(this.getScrollY() == this.getChildAt(0).getHeight() - this.getHeight()) {
            	if((arg11 instanceof ViewGroup)) {
                    View v6 = arg11;
                    int v8 = arg11.getScrollX();
                    int v9 = arg11.getScrollY();
                    int v7;
                    for(v7 = ((ViewGroup)v6).getChildCount() - 1; v7 >= 0; --v7) {
                        View v1 = ((ViewGroup)v6).getChildAt(v7);
                        if(arg14 + v8 >= v1.getLeft() && arg14 + v8 < v1.getRight() && arg15 + v9 >= v1.getTop() && arg15 + v9 < v1.getBottom() && (this.canScroll(v1, true, arg13, arg14 + v8 - v1.getLeft(), arg15 + v9 - v1.getTop()))) {
                            return true;
                        }
                    }
                }

                if((arg12) && (ViewCompat.canScrollVertically(arg11, -arg13))) {
                    return true;
                }

                v0 = false;
            }
            else if(((MCPullToRefreshView)v0_1).getFirstVisiblePosition() != 0) {
                v0 = false;
            }
            else {
            	if((arg11 instanceof ViewGroup)) {
                    View v6 = arg11;
                    int v8 = arg11.getScrollX();
                    int v9 = arg11.getScrollY();
                    int v7;
                    for(v7 = ((ViewGroup)v6).getChildCount() - 1; v7 >= 0; --v7) {
                        View v1 = ((ViewGroup)v6).getChildAt(v7);
                        if(arg14 + v8 >= v1.getLeft() && arg14 + v8 < v1.getRight() && arg15 + v9 >= v1.getTop() && arg15 + v9 < v1.getBottom() && (this.canScroll(v1, true, arg13, arg14 + v8 - v1.getLeft(), arg15 + v9 - v1.getTop()))) {
                            return true;
                        }
                    }
                }

                if((arg12) && (ViewCompat.canScrollVertically(arg11, -arg13))) {
                    return true;
                }

                v0 = false;
            }
        }
        else {
        	if((arg11 instanceof ViewGroup)) {
                View v6 = arg11;
                int v8 = arg11.getScrollX();
                int v9 = arg11.getScrollY();
                int v7;
                for(v7 = ((ViewGroup)v6).getChildCount() - 1; v7 >= 0; --v7) {
                    View v1 = ((ViewGroup)v6).getChildAt(v7);
                    if(arg14 + v8 >= v1.getLeft() && arg14 + v8 < v1.getRight() && arg15 + v9 >= v1.getTop() && arg15 + v9 < v1.getBottom() && (this.canScroll(v1, true, arg13, arg14 + v8 - v1.getLeft(), arg15 + v9 - v1.getTop()))) {
                        return true;
                    }
                }
            }

            if((arg12) && (ViewCompat.canScrollVertically(arg11, -arg13))) {
                return true;
            }

            v0 = false;
        }

        return v0;
    }

    public boolean dispatchTouchEvent(MotionEvent arg2) {
        arg2.getAction();
        return super.dispatchTouchEvent(arg2);
    }

    void init() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        ViewConfiguration v0 = ViewConfiguration.get(this.getContext());
        this.mTouchSlop = v0.getScaledTouchSlop();
        this.mMinimumVelocity = v0.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = v0.getScaledMaximumFlingVelocity();
        float v0_1 = this.getResources().getDisplayMetrics().density;
        this.mFlingDistance = ((int)(25f * v0_1));
        this.mCloseEnough = ((int)(2f * v0_1));
        this.mDefaultGutterSize = ((int)(v0_1 * 16f));
    }

    private boolean isGutterDrag(float arg4, float arg5) {
        boolean v0;
        if(arg4 >= (((float)this.mGutterSize)) || arg5 <= 0f) {
            if(arg4 > (((float)(this.getHeight() - this.mGutterSize))) && arg5 < 0f) {
            	return true;
            }

            v0 = false;
        }
        else {
        	return true;
        }
        return v0;
    }

    public boolean onInterceptTouchEvent(MotionEvent arg14) {
        float v0_1;
        int v3 = -1;
        boolean v2 = false;
        int v0 = arg14.getAction() & 255;
        if(v0 == 3 || v0 == 1) {
            if(this.mVelocityTracker == null) {
                return v2;
            }

            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        else {
            switch(v0) {
                case 0: {
                    v0_1 = arg14.getY();
                    this.mInitialMotionY = v0_1;
                    this.mLastMotionY = v0_1;
                    this.mLastMotionX = arg14.getX();
                    this.mActivePointerId = MotionEventCompat.getPointerId(arg14, 0);
                    this.mIsUnableToDrag = false;
                    this.mIsBeingDragged = true;
                    break;
                }
                case 2: {
                    v0 = this.mActivePointerId;
                    if(v0 != v3) {
                        v0 = MotionEventCompat.findPointerIndex(arg14, v0);
                        if(v0 != v3) {
                            float v6 = MotionEventCompat.getX(arg14, v0);
                            float v7 = Math.abs(v6 - this.mLastMotionX);
                            float v8 = MotionEventCompat.getY(arg14, v0);
                            float v9 = v8 - this.mLastMotionY;
                            float v10 = Math.abs(v8 - this.mLastMotionY);
                            if(v9 != 0f && !this.isGutterDrag(this.mLastMotionY, v9) && (this.canScroll(this, false, ((int)v9), ((int)v6), ((int)v8)))) {
                                this.mLastMotionY = v8;
                                this.mInitialMotionY = v8;
                                this.mLastMotionX = v6;
                                this.mIsUnableToDrag = true;
                                return v2;
                            }

                            if(v10 > (((float)this.mTouchSlop)) && v10 > v7) {
                                this.mIsBeingDragged = true;
                                v0_1 = v9 > 0f ? this.mInitialMotionY + (((float)this.mTouchSlop)) : this.mInitialMotionY - (((float)this.mTouchSlop));
                                this.mLastMotionY = v0_1;
                                break;
                            }

                            if(v7 <= (((float)this.mTouchSlop))) {
                            	 break;
                            }

                            this.mIsUnableToDrag = true;
                        }
                        else {
                        	 break;
                        }
                    }
                    else {
                       break;
                    }

                    return v2;
                }
            }

            if(this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }

            this.mVelocityTracker.addMovement(arg14);
            v2 = super.onInterceptTouchEvent(arg14);
        }

        return v2;
    }
    
    @Override
    public void requestChildFocus(View child, View focused) {
    	View view = this.getChildAt(0);
    	super.requestChildFocus(child, view);
    }
}

