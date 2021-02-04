package com.sales.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sales.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Integer>> postItems;
	int count = 0;
	
	public MenuAdapter(Context context, ArrayList<HashMap<String, Integer>> arraylist){
		this.context = context;
		postItems = arraylist;
	}
	@Override
	public int getCount() {
		return postItems.size();
	}
	@Override
	public HashMap<String, Integer> getItem(int position) {
		return postItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)
			context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.navigation_drawer_list, null);
		}

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map = getItem(position);

		TextView tvOption = (TextView)convertView.findViewById(R.id.tv_navigation_option);
		tvOption.setText(map.get("option"));

		ImageView ivOption = (ImageView)convertView.findViewById(R.id.iv_navigation_option);
		ivOption.setBackgroundResource(map.get("image"));
		return convertView;
	}
}

