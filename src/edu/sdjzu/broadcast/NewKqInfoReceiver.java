package edu.sdjzu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import edu.sdjzu.manager.R;
import edu.sdjzu.managetools.ManageUtil;

public class NewKqInfoReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String msg = intent.getStringExtra("info");
		if (null == msg) {
			msg = "";
		}
		Log.i("chen", "NewKqInfoReceiver 2222222");
		intent.putExtra("index", 1);
		ManageUtil.noticeNewInfo(context, intent, context.getString(R.string.kq_new_kq_tip),
				context.getString(R.string.kq_new_kq_tip), ManageUtil.NEW_KQ_INFO);
	}

}
