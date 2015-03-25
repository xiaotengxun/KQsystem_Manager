package edu.sdjzu.manager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.kqsystem_manager.R;

import edu.sdjzu.attr.Attr;
import edu.sdjzu.managetools.ManageTool;

public class SpashAct extends Activity {
	private Runnable delayRun;
	private Handler handler = null;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		delayRun = new Runnable() {
			@Override
			public void run() {
				Intent mainIntent = new Intent(SpashAct.this, LoginAct.class);
				SpashAct.this.startActivity(mainIntent);
				handler.removeCallbacks(delayRun);
				SpashAct.this.finish();
			}
		};
		handler = new Handler();
		handler.postDelayed(delayRun, Attr.SPASH_TIME);
		test();
	}

	private void test() {
		new Thread() {
			@Override
			public void run() {
				ManageTool managerTool = new ManageTool(getApplicationContext());
			}
		}.start();
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
