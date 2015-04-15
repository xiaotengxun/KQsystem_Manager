package edu.sdjzu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import edu.sdjzu.manager.R;

public class NetWorkChangeBroadCast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
				|| Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
			Intent startServiceIntent = new Intent(context.getString(R.string.ACTION_NETWORK_CHANGED));
			startServiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(startServiceIntent);
		}
	}

}
