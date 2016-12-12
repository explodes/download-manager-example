package io.explod.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DownloadReceiver extends BroadcastReceiver {

	private static final String TAG = "DownloadReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive: " + intent);

		Bundle extras = intent.getExtras();
		if (extras == null) {
			Log.d(TAG, "  no extras");
		} else {
			for (String key : extras.keySet()) {
				Log.d(TAG, "  " + key + "=" + extras.get(key));
			}
		}
	}

}
