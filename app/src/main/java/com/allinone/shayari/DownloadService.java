package com.allinone.shayari;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadService {
    private static long downloadID;
    private static Context Context;
   // public long downloadID;

   public static void DownloadImage(Context mContext, String url,String name) {


        //File file=new File(mContext.getExternalFilesDir(null),name+".jpg");
    Context = mContext;
    /*
    Create a DownloadManager.Request with all the information necessary to start the download
     */
    DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url))
            .setTitle(name+".jpg")// Title of the Download Notification
            .setDescription("Downloading")// Description of the Download Notification
            //.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
            //.setDestinationUri(Uri.fromFile(file))// Uri of the destination file
            //.setRequiresCharging(false)// Set if charging is required to begin the download
            .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,name+".jpg")
            .setAllowedOverRoaming(true);// Set if download is allowed on roaming network


    DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
    downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
        Toast.makeText(Context, "Download Completed", Toast.LENGTH_SHORT).show();
}
    public static void DownloadVideo(Context mContext, String url,String name) {


        //File file=new File(mContext.getExternalFilesDir(null),name+".jpg");
        Context = mContext;
    /*
    Create a DownloadManager.Request with all the information necessary to start the download
     */
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url))
                .setTitle(name+".mp4")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                //.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                //.setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                //.setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,name+".mp4")
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network


        DownloadManager downloadManager= (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
        Toast.makeText(Context, "Download Completed", Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(Context, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
