package edu.sdjzu.localtool;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
//		startService(new Intent(getString(R.string.ACTION_NETWORK_CHANGED)));
//		Log.i("chen", "start server");
	}
}
