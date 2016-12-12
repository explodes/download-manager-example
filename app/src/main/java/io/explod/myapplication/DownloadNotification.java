package io.explod.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class DownloadNotification {

	private static final String TAG = "DownloadNotification";

	private static final int NOTIFICATION_ID = 41;

	private static final int NOTIFICATION_PROGRESS_PRECISION = 10000;

	@NonNull
	private final Context mContext;

	@NonNull
	private final NotificationManager mNm;

	public DownloadNotification(@NonNull Context context) {
		mContext = context.getApplicationContext();
		mNm = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
	}

	public void showNotification(@NonNull DownloadTotaller.Totals totals) {
		Notification note = new NotificationCompat.Builder(mContext)
			.setContentTitle((totals.getNumFinishedDownloads() + totals.getNumRunning()) + " / " + totals.getNumDownloads())
			.setSmallIcon(R.mipmap.ic_launcher)
			.setProgress(NOTIFICATION_PROGRESS_PRECISION, (int) (totals.getDownloadPercentage() * NOTIFICATION_PROGRESS_PRECISION), false)
			.build();
		mNm.notify(NOTIFICATION_ID, note);
	}
}
