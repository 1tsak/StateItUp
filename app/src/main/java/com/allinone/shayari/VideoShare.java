package com.allinone.shayari;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VideoShare extends AsyncTask<String, Integer, String> {
    public Context context;
    public String videoToDownload;
    public String fileName;
    public String type;
    public ProgressDialog progressBar;
    File outputFile;

    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //showDialog(progress_bar_type);
        progressBar = new ProgressDialog(context);
        progressBar.setMessage("Loading...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);//initially progress is 0
        progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

    @Override
    protected String doInBackground(String... params) {
        int count;

        try {
            mp4load(videoToDownload);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public void mp4load(String urling) {
        try {
            System.out.println("Downloading");
            URL url = new URL(urling);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //c.setDoOutput(true);
            con.connect();

            // String downloadsPath = Environment.getExternalStoragePublicDirectory();
            File SDCardRoot = Environment.getExternalStorageDirectory();

             outputFile = new File(SDCardRoot, fileName+".mp4");

            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(outputFile);

            int status = con.getResponseCode();

            InputStream is = con.getInputStream();
            int fileLength = con.getContentLength();
            long total = 0;
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
                total += len1;
                progressBar.setProgress((int) (total * 100 / fileLength));
            }
            fos.close();
            is.close();
            System.out.println("Downloaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
      progressBar.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
       // dismissDialog(progress_bar_type);
        progressBar.dismiss();
        if (type.equals("whatsapp")){
            shareWhatsapp();
        }else {
            share();
        }
    }

    public void shareWhatsapp(){
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", outputFile);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("video/*");
        i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(Intent.EXTRA_STREAM,uri);
        i.setPackage("com.whatsapp");
        context.startActivity(i);
    }
    public void share(){
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", outputFile);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("video/*");
        i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(Intent.EXTRA_STREAM,uri);
        context.startActivity(i);
    }
}