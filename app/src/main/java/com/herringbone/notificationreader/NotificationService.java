package com.herringbone.notificationreader;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.ByteArrayOutputStream;

public class NotificationService extends NotificationListenerService {
    private String TAG = this .getClass().getSimpleName() ;
    Context context ;
    static NotificationListener myListener ;
    @Override
    public void onCreate () {
        super .onCreate() ;
        context = getApplicationContext() ;
    }
    @Override
    public void onNotificationPosted (StatusBarNotification sbn) {
        Log. i ( TAG , "********** onNotificationPosted" ) ;
        Log. i ( TAG , "ID :" + sbn.getId() + " \t " + sbn.getNotification(). tickerText + " \t " + sbn.getPackageName()) ;
        String ticker ="";
        if(sbn.getNotification().tickerText !=null) {
            ticker = sbn.getNotification().tickerText.toString();
        }
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        myListener .setValue( "Post: " + title + "\tBody: " + text) ;
//        String pack = sbn.getPackageName();
//        String ticker ="";
//        if(sbn.getNotification().tickerText !=null) {
//            ticker = sbn.getNotification().tickerText.toString();
//        }
//        Bundle extras = sbn.getNotification().extras;
//        String title = extras.getString("android.title");
//        String text = extras.getCharSequence("android.text").toString();
//        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
//        Bitmap id = sbn.getNotification().largeIcon;
//        Log.i("Package",pack);
//        Log.i("Ticker",ticker);
//        Log.i("Title",title);
//        Log.i("Text",text);
//        Intent msgrcv = new Intent("Msg");
//        msgrcv.putExtra("package", pack);
//        msgrcv.putExtra("ticker", ticker);
//        msgrcv.putExtra("title", title);
//        msgrcv.putExtra("text", text);
//        if(id != null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] byteArray = stream.toByteArray();
//            msgrcv.putExtra("icon",byteArray);
//        }
//        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
    }
    @Override
    public void onNotificationRemoved (StatusBarNotification sbn) {
        Log. i ( TAG , "********** onNotificationRemoved" ) ;
        Log. i ( TAG , "ID :" + sbn.getId() + " \t " + sbn.getNotification(). tickerText + " \t " + sbn.getPackageName()) ;
        myListener .setValue( "Remove: " + sbn.getPackageName()) ;
    }

    public void setListener (NotificationListener myListener) {
        NotificationService. myListener = myListener ;
    }
}
