package edu.sdjzu.localtool;

import com.example.kqsystem_manager.R;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		startService(new Intent(getString(R.string.ACTION_NETWORK_CHANGED)));
		Log.i("chen", "start server");
	}
}
