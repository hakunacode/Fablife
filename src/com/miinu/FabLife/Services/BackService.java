package com.miinu.FabLife.Services;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Activity.NotifySelect;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service ;
import android.content.Intent ;
import android.os.Handler;
import android.os.IBinder ;
import android.os.Message;

public class BackService extends Service 
{
	NotificationManager mNM = null ;
	int					time = 0 ;
	
	@Override
	public IBinder onBind( Intent intent )
	{
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate() ;
		mNM = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
	}
	
	@Override
    public int onStartCommand( Intent intent, int flags, int startId )
	{
		time = intent.getIntExtra( "time", 0 ) ;
		if ( time != 0 )
		{
			Handler handler = new Handler()
			{
				@Override
				public void handleMessage( Message msg ) 
				{
					// TODO Auto-generated method stub
					Notification notification = new Notification() ;
					notification.flags = 2 ;
					notification.icon = R.drawable.icon ;
					notification.tickerText = "Energy Full" ;

			        // The PendingIntent to launch our activity if the user selects this notification
			        PendingIntent contentIntent = PendingIntent.getActivity( getApplicationContext(), 0, new Intent( getApplicationContext(), NotifySelect.class ).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ), PendingIntent.FLAG_UPDATE_CURRENT ) ;

			        // Set the info for the views that show in the notification panel.
			        notification.setLatestEventInfo( getApplicationContext(), "FabLife", "Energy is Full", contentIntent ) ;

			        // Send the notification.
			        // We use a layout id because it is a unique number.  We use it later to cancel.
			        mNM.notify( R.drawable.icon, notification ) ;
			        
			        stopForeground( true ) ;
			        stopSelf() ;
				}
			} ;
			handler.sendEmptyMessageDelayed( 0, time ) ;
		}
		return super.onStartCommand( intent, flags, startId ) ;
    }
}
