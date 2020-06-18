package com.allinone.shayari;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bumptech.glide.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WhatsappService {
    static public void shareImage(String url, final Context context) {
        Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }



            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public void shareImageWhatsapp(String url, final Context context) {
        Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setPackage("com.whatsapp");
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }



            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }
    static public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.close();
            bmpUri =  FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
    static public void ShareText(Context mContext,String text){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            mContext.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext,"Whatsapp have not been installed.",Toast.LENGTH_SHORT);
        }
    }
    static public void ShareVideo(Context mContext,String url){

        Uri uri =  Uri.parse( url );
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("video/*");
        i.putExtra(Intent.EXTRA_TEXT, "https://bit.ly/asPxMyg");
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(Intent.EXTRA_STREAM,uri);

        mContext.startActivity(Intent.createChooser(i, "Share Image"));

    }
}
