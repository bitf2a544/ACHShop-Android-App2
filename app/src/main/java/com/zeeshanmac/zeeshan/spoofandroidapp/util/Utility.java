package com.zeeshanmac.zeeshan.spoofandroidapp.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.exceptions.SnapMediaSizeException;
import com.snapchat.kit.sdk.creative.exceptions.SnapVideoLengthException;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapPhotoFile;
import com.snapchat.kit.sdk.creative.media.SnapVideoFile;
import com.snapchat.kit.sdk.creative.models.SnapPhotoContent;
import com.snapchat.kit.sdk.creative.models.SnapVideoContent;

import java.io.File;

import static android.support.constraint.Constraints.TAG;


public class Utility {
    // UPDATED!
    public static String getVideoPath(Uri uri, Context context) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public static String getImagePath(Uri uri, Context context) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    public static void shareImageOnSnapChat(Context context, String selectedImageUriPath) {
        Log.e("shareImageOnSnapChat:", "inside=" + selectedImageUriPath);
        SnapCreativeKitApi snapCreativeKitApi = SnapCreative.getApi(context);
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(context);
        SnapPhotoFile photoFile;
        try {
            photoFile = snapMediaFactory.getSnapPhotoFromFile(new File(selectedImageUriPath));
            if (photoFile != null) {
                SnapPhotoContent snapPhotoContent = new SnapPhotoContent(photoFile);
                snapCreativeKitApi.send(snapPhotoContent);
            } else {
                Log.e("photoFile:", "Null");
            }
        } catch (
                SnapMediaSizeException e) {
            e.printStackTrace();

        }
    }

    public static void shareVideoOnSnapChat(Context context, String selectedVideoUriPath) {
        Log.e("shareVideoOnSnapChat:", "inside=" + selectedVideoUriPath);
        SnapCreativeKitApi snapCreativeKitApi = SnapCreative.getApi(context);
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(context);
        SnapVideoFile videoFile;
        try {
            videoFile = snapMediaFactory.getSnapVideoFromFile(new File(selectedVideoUriPath));
            if (videoFile != null) {
                SnapVideoContent snapVideoContent = new SnapVideoContent(videoFile);
                snapCreativeKitApi.send(snapVideoContent);
            } else {
                Log.e("photoFile:", "Null");
            }

        } catch (SnapMediaSizeException | SnapVideoLengthException e) {
            e.printStackTrace();
            return;
        }


    }

    public static String getRealVideoPathFromURI(ContentResolver contentResolver, Uri contentURI) {
        Cursor cursor = contentResolver.query(contentURI, null, null, null, null);
        if (cursor == null)
            return contentURI.getPath();
        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
            try {
                return cursor.getString(idx);
            } catch (Exception exception) {
                return null;
            }
        }
    }


    // UPDATED!
    public static String getVideoPath_new(Uri uri, Context context) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // if no network is available networkInfo will be null, otherwise check if we are connected
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            Log.e("isNetworkAvailable()", "" + e.getMessage());
        }
        return false;
    }

}


