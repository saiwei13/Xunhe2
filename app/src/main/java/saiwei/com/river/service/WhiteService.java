package saiwei.com.river.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import saiwei.com.river.Constant;
import saiwei.com.river.MapActivity;
import saiwei.com.river.R;
import timber.log.Timber;


/**
 * 正常的系统前台进程，会在系统通知栏显示一个Notification通知图标
 *
 * @author clock
 * @since 2016-04-12
 */
public class WhiteService extends Service {

//    private final static String TAG = WhiteService.class.getSimpleName();

    private final static int FOREGROUND_ID = 1000;

    Notification.Builder builder;
    Notification notification;

    @Override
    public void onCreate() {
//        Log.i(TAG, "WhiteService->onCreate");
        Timber.d("onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i(TAG, "WhiteService->onStartCommand");
        Timber.d("onStartCommand()");

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        if(intent != null){

            Bundle bundle = intent.getExtras();

            String xunhe_statue = bundle.getString(Constant.XUNHE_STATUE);

            Timber.d("onStartCommand()  xunhe_statue=%s",xunhe_statue);

            if(xunhe_statue.equals(Constant.XUNHE_STATUE_START)){

                builder  = new Notification.Builder(this);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("河长制");
                builder.setContentText("正在巡河中。。。");
                builder.setContentInfo("");
                builder.setWhen(System.currentTimeMillis());
                Intent activityIntent = new Intent(this, MapActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                notification = builder.build();
                startForeground(FOREGROUND_ID, notification);

            } else if(xunhe_statue.equals(Constant.XUNHE_STATUE_CONTINUE)){

            } else if(xunhe_statue.equals(Constant.XUNHE_STATUE_END)){
//                notification.
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager Nmang = (NotificationManager) getApplicationContext()
                        .getSystemService(ns);
                Nmang.cancel(FOREGROUND_ID);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        Timber.d("onBind()");

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onDestroy() {

        Timber.d("onDestroy()");

        super.onDestroy();
    }
}
