package com.example.audiobook_app.Domain;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.media3.database.DatabaseProvider;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.NoOpCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;

import java.io.File;
import java.util.concurrent.Executor;

public class DownloadHandler {
    private Context context;

    private static final String DOWNLOAD_LOCATION = Environment.DIRECTORY_DOWNLOADS;

    public DownloadHandler(Context context) {
        this.context = context;
    }

    public long downloadChapter(String url, String fileName) {

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(fileName);
       // request.setMimeType("audio/mp3");
        request.setDescription("Downloading " + fileName);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Set the destination to a custom "Audiobooks" directory in the external storage
//        File audiobooksDir = new File(Environment.getExternalStorageDirectory(), "Audiobooks");
//        if (!audiobooksDir.exists() && !audiobooksDir.mkdirs()) {
//            Log.e("DownloadHandler", "Failed to create directory: " + audiobooksDir.getPath());
//            return null;
//        }
        //request.setDestinationInExternalPublicDir(audiobooksDir.getPath(), fileName + ".mp3");
        request.setDestinationInExternalPublicDir(DOWNLOAD_LOCATION, fileName+ ".mp3");




        return manager.enqueue(request);
    }

    public String getDownloadPath(String fileName) {
        return fileName + ".mp3";
    }


}


