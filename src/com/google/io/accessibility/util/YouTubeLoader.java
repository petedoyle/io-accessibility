package com.google.io.accessibility.util;

import android.content.Context;
import android.net.Uri;

import com.google.io.accessibility.data.VideoFeed;

public abstract class YouTubeLoader extends IncrementalAsyncTaskLoader<Uri, Void, VideoFeed> {
	
	public YouTubeLoader(Context context, IncrementalAsyncTaskLoaderCallbacks<Void, VideoFeed> callbacks) {
		super( context, callbacks );
	}

	/* Runs on the UI thread */
	@Override
	public void deliverResult(VideoFeed feed) {
		if( isReset() ) {
			// An async query came in while the loader is stopped
			return;
		}

		if( isStarted() ) {
			super.deliverResult( feed );
		}
	}

	/**
	 * Must be called from the UI thread
	 */
	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	/**
	 * Must be called from the UI thread
	 */
	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override
	public void onCanceled(VideoFeed feed) {
		//TODO cancel http request
	}

	@Override
	protected void onReset() {
		super.onReset();

		// Ensure the loader is stopped
		onStopLoading();
	}
}