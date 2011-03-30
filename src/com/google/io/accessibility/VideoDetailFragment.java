package com.google.io.accessibility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.io.accessibility.data.Video;

public class VideoDetailFragment extends Fragment {
	private static final String TAG = VideoDetailFragment.class.getSimpleName();

	private static final String ARG_VIDEO = "video";

	private Video mVideo;

	private TextView mTitle;
	private TextView mDescription;
	private Button mPlayButton;
	private Button mShareButton;

	public static VideoDetailFragment newInstance(Video video) {
		VideoDetailFragment fragment = new VideoDetailFragment();

		Bundle args = new Bundle();
		args.putSerializable( ARG_VIDEO, video );
		fragment.setArguments( args );

		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach( activity );
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );

		this.mVideo = (Video) getArguments().get( ARG_VIDEO );

		setRetainInstance( true );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_video_detail, container, false );

		mTitle = (TextView) view.findViewById( R.id.video_detail_title );
		mDescription = (TextView) view.findViewById( R.id.video_detail_description );
		mPlayButton = (Button) view.findViewById( R.id.video_detail_play_button );
		mShareButton = (Button) view.findViewById( R.id.video_detail_share_button );

		mTitle.setText( mVideo.title );
		mDescription.setText( mVideo.description );
		mPlayButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( mVideo.player.defaultUrl ) );

				startActivity( intent );
			}
		} );

		mShareButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( Intent.ACTION_SEND );
				intent.setType( "text/plain" );
				intent.putExtra( Intent.EXTRA_SUBJECT, "YouTube video recommendation" );
				intent.putExtra( Intent.EXTRA_TEXT, mVideo.player.defaultUrl );

				startActivity( Intent.createChooser( intent, "Share video link" ) );
			}
		} );

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated( savedInstanceState );
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}