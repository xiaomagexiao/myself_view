package com.example.myselfview.sections.gridview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myself_view.R;
import com.example.myselfview.adapter.BaseAdapterHelper;
import com.example.myselfview.adapter.QuickAdapter;
import com.example.myselfview.index.MoocApplication;
import com.example.myselfview.sections.jdtoast.MCToast;

public class GridviewDemoActivity extends Activity implements OnItemClickListener {

	private HeaderGridView pic_list;
	private QuickAdapter adapter;
	private RelativeLayout gridview_header;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.gridview_activity);

		initAdapter();

		initView();

		initData();

		initEvent();
	}

	private void initAdapter() {

		this.adapter = new QuickAdapter(this, R.layout.gridview_item) {
			protected void convert(BaseAdapterHelper helper, MCPicModel item) {
				// 这里给item设置属性

				helper.setText(R.id.pic_name, item.getName());
				ImageView image = (ImageView) helper.getView(R.id.image);
				// image.setImageUrl(item.getLink());
			}

			protected void convert(BaseAdapterHelper helper, Object obj) {
				this.convert(helper, ((MCPicModel) obj));
			}
		};
	}

	private void initEvent() {
		this.pic_list.setOnItemClickListener(this);
	}

	private void initData() {
		List<MCPicModel> list = new ArrayList<MCPicModel>();

		for (int i = 0; i < 9; i++) {
			MCPicModel pic = new MCPicModel();
			pic.setName("图片" + i);
			pic.setLink("");
			list.add(pic);
		}
		this.adapter.addAll(list);
	}

	private void initView() {
		this.pic_list = (HeaderGridView) this.findViewById(R.id.pic_list);

		LayoutInflater inflater = (LayoutInflater) MoocApplication.getInstance()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.gridview_header = (RelativeLayout) inflater.inflate(R.layout.gridview_header, null);

		this.pic_list.addHeaderView(gridview_header);
		this.pic_list.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MCToast.show("点击了条目" + position);
	}
}
