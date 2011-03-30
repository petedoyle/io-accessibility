package com.google.io.accessibility;

import java.io.IOException;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.io.accessibility.data.Video;
import com.google.io.accessibility.data.VideoFeed;
import com.google.io.accessibility.data.YouTubeUrl;
import com.google.io.accessibility.util.IncrementalAsyncTaskLoaderCallbacks;
import com.google.io.accessibility.util.YouTubeLoader;

public class VideoListFragment extends ListFragment implements TextWatcher {
	private static final String TAG = VideoListFragment.class.getSimpleName();

	private VideoListAdapter mAdapter;
	private YouTubeLoaderCallbacks mCallbacks;
	
	private EditText mSearchBox;
	private ProgressBar mProgressBar;
	
	private String mQuery;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach( activity );
		
		mCallbacks = new YouTubeLoaderCallbacks();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		
		this.mAdapter = new VideoListAdapter( getActivity(), R.layout.fragment_video_list_row );
		setListAdapter( mAdapter );
		setRetainInstance( true );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_video_list, container, false );

		mSearchBox = (EditText) view.findViewById( R.id.search_box );
		mProgressBar = (ProgressBar) view.findViewById( R.id.search_progress );
		mSearchBox.addTextChangedListener( this );
		mSearchBox.requestFocus();
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick( l, v, position, id );
		
		Video video = mAdapter.getItem( position );
		
		Fragment fragment = VideoDetailFragment.newInstance( video );
		getFragmentManager()
			.beginTransaction()
			.replace( R.id.main_layout, fragment )
			.addToBackStack( null )
			.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
			.commit();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated( savedInstanceState );
		
		getLoaderManager().initLoader( 0, null, mCallbacks );
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		mCallbacks = null;
	}

	private class YouTubeLoaderCallbacks implements IncrementalAsyncTaskLoaderCallbacks<Void, VideoFeed> {
		@Override
		public Loader<VideoFeed> onCreateLoader(int id, Bundle args) {
			
			return new YouTubeLoader( getActivity(), this ) {
				@Override
				public VideoFeed loadInBackground() {
					// set up the JSON-C parser
					JsonCParser parser = new JsonCParser();
					parser.jsonFactory = new JacksonFactory();

					// set up the Google headers
					GoogleHeaders headers = new GoogleHeaders();
					headers.setApplicationName( "GoogleIO-Accessibility Demo/1.0" );
					headers.gdataVersion = "2";

					// set up the HTTP transport
					HttpTransport transport;
					if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD ) {
						transport = new NetHttpTransport(); // use HttpUrlConnection on pre-Gingerbread
					} else {
						transport = new ApacheHttpTransport(); // use Apache HttpClient on Gingerbread+
					}
					transport.defaultHeaders = headers;
					transport.addParser( parser );

					// build the YouTube URL
					YouTubeUrl url = new YouTubeUrl( "https://gdata.youtube.com/feeds/api/videos" );
					
					url.q = mQuery;
					url.format = 6; // TODO: is there a HQ mobile format?
					url.maxResults = 20;
					url.caption = true;
					
					// build the HTTP GET request
					HttpRequest request = transport.buildGetRequest();
					request.url = url;
					
					Log.v( TAG, "YouTube URL: " + url );

					// execute the request and the parse video feed
					VideoFeed feed = null;
					try {
						feed = request.execute().parseAs( VideoFeed.class );
					} catch( IOException e ) {
						Log.e( TAG, "Failed to load feed", e );
						final FragmentActivity activity = getActivity();
						activity.runOnUiThread( new Runnable() {
							@Override
							public void run() {
								Toast.makeText( activity, "Failed to load feed", Toast.LENGTH_LONG ).show();
							}
						});
					}
					return feed;
				}
			};
		}

		@Override
		public void onLoadFinished(Loader<VideoFeed> loader, VideoFeed feed) {
			if( null != feed && null != feed.items ) {
				mAdapter.clear();
	
				for( Video video : feed.items ) {
					mAdapter.add( video );
				}
			} else {
				final FragmentActivity activity = getActivity();
				activity.runOnUiThread( new Runnable() {
					@Override
					public void run() {
						Toast.makeText( activity, "Failed to load videos", Toast.LENGTH_LONG ).show();
					}
				});
			}
		}

		@Override
		public void onLoaderReset(Loader<VideoFeed> feed) {
			mProgressBar.setEnabled( false );
			mProgressBar.setVisibility( View.INVISIBLE );
		}

		@Override
		public void onPreExecute() {
			mProgressBar.setEnabled( true );
			mProgressBar.setVisibility( View.VISIBLE );
		}

		@Override
		public void onProgressUpdate(Void progress) {
		}

		@Override
		public void onPostExecute(VideoFeed result) {
			mProgressBar.setEnabled( false );
			mProgressBar.setVisibility( View.INVISIBLE );
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence newQuery, int start, int before, int count) {
		mQuery = !TextUtils.isEmpty( newQuery ) ? newQuery.toString() : null;
		getLoaderManager().restartLoader( 0, null, mCallbacks );
	}
	
}