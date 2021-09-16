package com.herringbone.notificationreader;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NotificationListener {
    private String TAG = this .getClass().getSimpleName() ;
    private TextView txtView ;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super .onCreate(savedInstanceState) ;
        setContentView(R.layout. activity_main ) ;
        new NotificationService().setListener( this ) ;
        txtView = findViewById(R.id. textView ) ;
        Button btnCreateNotification = findViewById(R.id. btnCreateNotification ) ;
//        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

        btnCreateNotification.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id ) ;
                mBuilder.setContentTitle( "My Notification" ) ;
                mBuilder.setContentText( "Notification Listener Service Example" ) ;
                mBuilder.setTicker( "Notification Listener Service Example" ) ;
                mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
                mBuilder.setAutoCancel( true ) ;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
                    int importance = NotificationManager. IMPORTANCE_HIGH ;
                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel) ;
                }
                assert mNotificationManager != null;
                mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
            }
        }) ;
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu. menu_main , menu) ; //Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id. action_settings :
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS" ) ;
                startActivity(intent) ;
                return true;
            default :
                return super .onOptionsItemSelected(item) ;
        }
    }

    @Override
    public void setValue (String packageName) {
        txtView .append( " \n " + packageName) ;
    }

    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            //int id = intent.getIntExtra("icon",0);
            Log. i ( TAG , "********** received" + title ) ;
            Log. i ( TAG , "********** received2" + text ) ;

            Context remotePackageContext = null;
            try {
                byte[] byteArray =intent.getByteArrayExtra("icon");
                Bitmap bmp = null;
                if(byteArray !=null) {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
//                Model model = new Model();
//                model.setName(title +" " +text);
//                model.setImage(bmp);
//
//                if(modelList !=null) {
//                    modelList.add(model);
//                    adapter.notifyDataSetChanged();
//                }else {
//                    modelList = new ArrayList<Model>();
//                    modelList.add(model);
//                    adapter = new CustomListAdapter(getApplicationContext(), modelList);
//                    list=(ListView)findViewById(R.id.list);
//                    list.setAdapter(adapter);
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}