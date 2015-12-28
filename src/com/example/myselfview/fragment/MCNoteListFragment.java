package com.example.myselfview.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.myself_view.R;
import com.example.myselfview.adapter.BaseAdapterHelper;
import com.example.myselfview.adapter.QuickAdapter;
import com.example.myselfview.model.MCMyNoteModel;
import com.example.myselfview.view.MCPullToRefreshView;
import com.example.myselfview.view.MCPullToRefreshView.OnFooterRefreshListener;
import com.example.myselfview.view.MCPullToRefreshView.OnHeaderRefreshListener;

public class MCNoteListFragment extends Fragment implements AdapterView.OnItemClickListener, OnFooterRefreshListener, OnHeaderRefreshListener {

	private int type;
	private String courseId;
	private MCPullToRefreshView mListView;
	private int currentPosition;
	private BroadcastReceiver mNetworkListener;
	private QuickAdapter adapter;
	private int mCurrentPage;

	public MCNoteListFragment() {
		super();
		this.mCurrentPage = 1;
	}

	private void createDate() {
		this.mListView.onHeaderRefreshComplete();
		this.mListView.onFooterRefreshComplete();
		List<MCMyNoteModel> list = new ArrayList<MCMyNoteModel>();
		for (int i = 0; i < 8; i++) {
			MCMyNoteModel model = new MCMyNoteModel();
			list.add(model);
		}
		this.adapter.addAll(list);

	}

	public static MCNoteListFragment newInstance(int type, String courseId) {
		MCNoteListFragment frag = new MCNoteListFragment();
		Bundle args = new Bundle();
		args.putInt("type", type);
		args.putString("courseId", courseId);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getInt("type");
		courseId = getArguments().getString("courseId");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v0 = inflater.inflate(R.layout.media_talk_layout, null);
		this.mListView = (MCPullToRefreshView) v0.findViewById(R.id.listview);
		this.mListView.setAdapterViewPadding(0, 0, 0, 0);
		this.mListView.setOnItemClickListener(((AdapterView.OnItemClickListener) this));
		this.mListView.setOnHeaderRefreshListener(((OnHeaderRefreshListener) this));
		this.mListView.setOnFooterRefreshListener(((OnFooterRefreshListener) this));
		return v0;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.adapter = new QuickAdapter(this.getActivity(), R.layout.item_note) {

			@Override
			protected void convert(BaseAdapterHelper helper, Object arg2) {
				this.convert(helper, ((MCMyNoteModel) arg2));
				if(type==0){
					helper.setText(R.id.note_text, "这是左侧内容……………………");
				}else{
					helper.setText(R.id.note_text, "这是右侧内容……………………");
				}
			}

			protected void convert(BaseAdapterHelper helper, MCMyNoteModel item) {

			}
		};

		this.mListView.setDataAdapter(this.adapter);
		mListView.headerRefreshing();
	}

	public void onFooterRefresh(MCPullToRefreshView view) {
		++this.mCurrentPage;
		System.out.println("onFooterRefresh");
		createDate();
	}

	public void onHeaderRefresh(MCPullToRefreshView view) {
		this.mCurrentPage = 1;
		createDate();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

}
