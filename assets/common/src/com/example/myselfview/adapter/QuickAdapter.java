package com.example.myselfview.adapter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public abstract class QuickAdapter extends BaseAdapter {
    private static final String TAG  = "QuickAdapter";
    private final Context context;
    private final List data;
    private boolean displayIndeterminateProgress;
    private final int layoutResId;

    public QuickAdapter() {
    	this.context = null;
    	   this.data=new ArrayList();
    	   this.layoutResId=0;
    }
    
    public QuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public QuickAdapter(Context context, int layoutResId, List arg4) {
        super();
        this.displayIndeterminateProgress = false;
        ArrayList v0 = arg4 == null ? new ArrayList() : new ArrayList(((Collection)arg4));
        this.data = ((List)v0);
        this.context = context;
        this.layoutResId = layoutResId;
    }

	public void add(int index, Object arg3) {
        this.data.add(index, arg3);
        this.notifyDataSetChanged();
    }

    public void add(Object arg2) {
        this.data.add(arg2);
        this.notifyDataSetChanged();
    }

    public void addAll(List arg2) {
        this.data.addAll(((Collection)arg2));
        this.notifyDataSetChanged();
    }

    public void clear() {
    	if(this.data!=null){
    		this.data.clear();
    	}
        this.notifyDataSetChanged();
    }

    public boolean contains(Object arg2) {
        return this.data.contains(arg2);
    }

    protected abstract void convert(BaseAdapterHelper arg1, Object arg2);

    private View createIndeterminateProgressView(View convertView, ViewGroup parent) {
        FrameLayout v4 = null;
        if(convertView == null) {
            FrameLayout v0 = new FrameLayout(this.context);
            v0.setForegroundGravity(17);
            v0.addView(new ProgressBar(this.context));
            v4 = v0;
        }

        return ((View)v4);
    }

    public List getAdapterList() {
        return this.data;
    }

    public int getCount() {
        int v0 = this.displayIndeterminateProgress ? 1 : 0;
        return this.data==null?0:this.data.size() + v0;
    }

    public Object getItem(int position) {
        Object v0 = position >= this.data.size() ? null : this.data.get(position);
        return v0;
    }

    public long getItemId(int position) {
        return ((long)position);
    }

    public int getItemViewType(int position) {
        int v0 = position >= this.data.size() ? 1 : 0;
        return v0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v1;
        if(this.getItemViewType(position) == 0) {
            BaseAdapterHelper v0 = BaseAdapterHelper.get(this.context, convertView, parent, this.layoutResId, position);
            this.convert(v0, this.getItem(position));
            v1 = v0.getView();
        }
        else {
            v1 = this.createIndeterminateProgressView(convertView, parent);
        }

        return v1;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public int indexOfItem(Object arg2) {
        return this.data.indexOf(arg2);
    }

    public boolean isEnabled(int position) {
        boolean v0 = position < this.data.size() ? true : false;
        return v0;
    }

    public void remove(int index) {
        this.data.remove(index);
        this.notifyDataSetChanged();
    }

    public void remove(Object arg2) {
        this.data.remove(arg2);
        this.notifyDataSetChanged();
    }

    public void removeAll(List arg2) {
        this.data.removeAll(((Collection)arg2));
        this.notifyDataSetChanged();
    }

    public void set(int index, Object arg3) {
        this.data.set(index, arg3);
        this.notifyDataSetChanged();
    }

    public void set(Object arg2, Object arg3) {
        this.set(this.data.indexOf(arg2), arg3);
    }

    public void showIndeterminateProgress(boolean display) {
        if(display != this.displayIndeterminateProgress) {
            this.displayIndeterminateProgress = display;
            this.notifyDataSetChanged();
        }
    }
    
    //通过id来替换adapter的某个bean， 常用于数据变更后的更新。
//    public void replaceModel(MCDataModel newBean) {
//    	boolean result = false;
//    	List<MCDataModel> list = this.getAdapterList();
//		for (MCDataModel model : list) {
//			if (newBean.getId().equals(model.getId())) {
//				int index = list.indexOf(model);
//				list.remove(index);
//				list.add(index, newBean);
//				result = true;
//				break;
//			}
//		}
//		if(result){
//			this.notifyDataSetChanged();
//		}
//    }
}

