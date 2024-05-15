package com.example.audiobook_app.Domain;


import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;
import androidx.media3.database.DatabaseProvider;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.NoOpCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;import android.content.ContentResolver;

public class DownloadHandler {
    private Context context;

    private Uri downloadLocation;

    public DownloadHandler(Context context, Uri downloadLocation)
    {
        this.downloadLocation = downloadLocation;
        this.context = context;
    }

    public long downloadChapterOLD(String url, String fileName) {

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


        request.setDestinationUri(Uri.withAppendedPath(downloadLocation, fileName + ".mp3"));



        return manager.enqueue(request);
    }

    public void downloadChapter(String url, String fileName) {
        new Thread(() -> {
            try {
                // Get a DocumentFile representing the selected directory
                DocumentFile directory = DocumentFile.fromTreeUri(context, downloadLocation);

                // Create a new file in the selected directory
                DocumentFile file = directory.createFile("audio/mp3", fileName + ".mp3");

                // Open an OutputStream to write to the file
                OutputStream outputStream = context.getContentResolver().openOutputStream(file.getUri());

                // Download the file
                URL downloadUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                InputStream inputStream = connection.getInputStream();

                // Write the input stream to the output stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Close the streams
                outputStream.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String getDownloadPath(String fileName) {
        return fileName + ".mp3";
    }


}


