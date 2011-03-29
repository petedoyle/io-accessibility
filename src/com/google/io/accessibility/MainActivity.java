package com.google.io.accessibility;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {
	
	private VideoListFragment mFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );
		
		// only init fragment the first time onCreate() is called.
		if( null == savedInstanceState ) {
			mFragment = new VideoListFragment();
			
			// do not add to backstack, or user will be able to press back and
			// view MainActivity's blank layout with nothing in it.
			// In this case, we want the back button to exit the app.
			getSupportFragmentManager()
				.beginTransaction()
				.add( R.id.main_layout, mFragment )
				.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE )
				.commit();
		}
	}
}