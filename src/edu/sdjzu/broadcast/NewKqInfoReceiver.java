package edu.sdjzu.broadcast;

import edu.sdjzu.managetools.ManageTool;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NewKqInfoReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ManageTool manageTool = new ManageTool(context);
		String msg = intent.getStringExtra("info");
		if (null == msg) {
			msg = "";
		}
		Log.i("chen","NewKqInfoReceiver 2222222");
		manageTool.noticeNewKq(msg);
	}

}
