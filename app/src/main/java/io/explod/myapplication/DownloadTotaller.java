package io.explod.myapplication;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadTotaller {

	private static final String TAG = "DownloadTotaller";

	@NonNull
	private final DownloadManager mDownloadManager;

	public DownloadTotaller(@NonNull Context context) {
		mDownloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
	}

	@NonNull
	public Totals getDownloadTotals() {
		DownloadManager.Query q = new DownloadManager.Query();
		Cursor cursor = mDownloadManager.query(q);

		Totals totals = new Totals();

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

			switch (status) {
				case DownloadManager.STATUS_FAILED: {
					totals.failures++;
					break;
				}
				case DownloadManager.STATUS_PAUSED: {
					totals.paused++;
					break;
				}
				case DownloadManager.STATUS_PENDING: {
					totals.pending++;
					break;
				}
				case DownloadManager.STATUS_RUNNING: {
					totals.running++;
					break;
				}
				case DownloadManager.STATUS_SUCCESSFUL: {
					totals.success++;
					break;
				}
			}

			long bytesTotalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
			if (bytesTotalSize != -1) {
				totals.bytesTotalSize += bytesTotalSize;
				totals.bytesDownloaded += cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
			}
		}

		Log.d(TAG, "downloads totals=" + totals);

		return totals;
	}


	public static final class Totals {

		private int failures = 0;
		private int success = 0;
		private int running = 0;
		private int pending = 0;
		private int paused = 0;
		private long bytesDownloaded = 0;
		private long bytesTotalSize = 0;

		public int getNumActiveOrPendingDownloads() {
			return running + pending + paused;
		}

		public int getNumFinishedDownloads() {
			return failures + success;
		}

		public int getNumDownloads() {
			return running + pending + paused + failures + success;
		}

		public int getNumFailed() {
			return failures;
		}

		public int getNumSuccessful() {
			return success;
		}

		public int getNumRunning() {
			return running;
		}

		public int getNumPending() {
			return pending;
		}

		public int getNumPaused() {
			return paused;
		}

		public long getBytesDownloaded() {
			return bytesDownloaded;
		}

		public long getBytesTotalSize() {
			return bytesTotalSize;
		}

		public double getDownloadPercentage() {
			return (double) bytesDownloaded / (double) bytesTotalSize;
		}

		@Override
		public String toString() {
			return "failures=" + failures
				+ " success=" + success
				+ " running=" + running
				+ " pending=" + pending
				+ " paused=" + paused
				+ " bytesDownloaded=" + bytesDownloaded
				+ " bytesTotalSize=" + bytesTotalSize;
		}
	}
}
