package com.google.io.accessibility;

import com.google.io.accessibility.data.Video;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VideoListAdapter extends ArrayAdapter<Video> {

	public VideoListAdapter(Context context, int layout) {
		super( context, layout );
	}

	class ViewHolder {
		Video video;
		TextView title;
		TextView description;
		TextView bottom;

		ViewHolder() {
		}

		ViewHolder(ViewHolder holder) {
			title = holder.title;
			description = holder.description;
			bottom = holder.bottom;
			video = holder.video;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if( convertView == null ) {
			convertView = View.inflate( getContext(), R.layout.fragment_video_list_row, null );

			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById( R.id.video_list_row_title );
			holder.description = (TextView) convertView.findViewById( R.id.video_list_row_description );
			holder.bottom = (TextView) convertView.findViewById( R.id.video_list_row_bottom );
			
			convertView.setTag( holder );
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Video video = getItem( position );

		holder.title.setText( video.title );
		holder.description.setText( video.description );

		return convertView;
	}
}