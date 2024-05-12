package com.example.audiobook_app.Domain;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class DownloadHandler {
    private Context context;

    public DownloadHandler(Context context) {
        this.context = context;
    }

    public String downloadChapter(String url, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading " + fileName);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Set the destination to a custom "Audiobooks" directory in the external storage
        File audiobooksDir = new File(Environment.getExternalStorageDirectory(), "Audiobooks");
        if (!audiobooksDir.exists() && !audiobooksDir.mkdirs()) {
            Log.e("DownloadHandler", "Failed to create directory: " + audiobooksDir.getPath());
            return null;
        }
        request.setDestinationInExternalPublicDir(audiobooksDir.getPath(), fileName + ".mp3");

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        return audiobooksDir.getPath() + "/" + fileName + ".mp3";
    }
    }
