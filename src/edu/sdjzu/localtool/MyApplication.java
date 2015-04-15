package edu.sdjzu.localtool;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("chen", "MyApplication   onCreate>>>>>>>>>>>>");
		// startService(new Intent(getString(R.string.ACTION_NETWORK_CHANGED)));
		// Log.i("chen", "start server");
	}

	@Override
	public void onTerminate() {
		Log.i("chen", "onTerminate>>>>>>>>>>>>");
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		Log.i("chen", "onLowMemory>>>>>>>>>>>>");
		super.onLowMemory();
	}
	
}
