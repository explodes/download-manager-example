package io.explod.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	private static final String SOURCE = "http://explod.io/hosted/gradle.gif";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 36; i++) {
					DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
					DownloadManager.Request request = new DownloadManager.Request(Uri.parse(SOURCE));
					request.setVisibleInDownloadsUi(false);
					//request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
					//request.addRequestHeader("X-Auth", "abc:123");
					//request.setTitle("Foo download");
					//request.setDescription("Foo description");
					//request.setDestinationUri(Uri.fromFile())
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
					dm.enqueue(request);
				}
			}
		}).start();

		new Thread(new Notifications(this)).start();

	}

	private class Notifications implements Runnable {

		private static final long SLEEP = 1000L;

		@NonNull
		private final DownloadNotification mDownloadNotification;

		@NonNull
		private final DownloadTotaller mDownloadTotaller;

		private Notifications(@NonNull Context context) {
			mDownloadNotification = new DownloadNotification(context);
			mDownloadTotaller = new DownloadTotaller(context);
		}

		@Override
		public void run() {
			while (true) {
				final DownloadTotaller.Totals t = mDownloadTotaller.getDownloadTotals();
				mDownloadNotification.showNotification(t);
				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
					Log.e(TAG, "Error during sleep", e);
				}
			}
		}
	}
}
