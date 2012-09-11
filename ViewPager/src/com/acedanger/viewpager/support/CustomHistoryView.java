package com.acedanger.viewpager.support;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.acedanger.viewpager.R;

public class CustomHistoryView extends BaseAdapter {
	private static ArrayList<History> wods;
	private LayoutInflater mInflator;
	private Context context;

	public CustomHistoryView(Context ctx, ArrayList<History> items) {
		context = ctx;
		wods = items;
		mInflator = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		return wods != null ? wods.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return wods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			convertView = mInflator.inflate(R.layout.rowhistory, null);
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.rowHistoryDate);
			holder.description = (TextView) convertView.findViewById(R.id.rowHistoryDesc);
			holder.result = (TextView) convertView.findViewById(R.id.rowHistoryResult);
			holder.rx = (TextView) convertView.findViewById(R.id.rowHistoryRx);
			holder.id = (TextView) convertView.findViewById(R.id.rowHistoryId);
			holder.notes = (TextView) convertView.findViewById(R.id.rowHistoryNotes);
			holder.hasNote = (ImageView) convertView.findViewById(R.id.rowHistoryNoteIcon);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.result.setText(wods.get(position).getResults());
		holder.description.setText(wods.get(position).getDescription());
		holder.date.setText(wods.get(position).getDate());
		holder.rx.setText(wods.get(position).getRx());
		holder.id.setText(wods.get(position).getId());
		holder.notes.setText(wods.get(position).getNotes());
		String wodNotes = wods.get(position).getNotes();
		if (wodNotes == null || wodNotes.equalsIgnoreCase("")) {
			holder.hasNote.setImageResource(0);
		} else {
			holder.hasNote.setImageResource(0);
		}

//		holder.hasNote.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				History wod = wods.get(position);
//				Intent i = new Intent(context, WodTracker.class);
//				i.putExtra("editWod", wod);
//				context.startActivity(i);
//			}
//		});

		return convertView;
	}

	static class ViewHolder {
		TextView date;
		TextView rx;
		TextView description;
		TextView result;
		TextView id;
		TextView notes;
		ImageView hasNote;
	}
}
