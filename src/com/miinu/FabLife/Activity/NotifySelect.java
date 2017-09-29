package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class NotifySelect extends Activity
{
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        NotificationManager mNM = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        mNM.cancel( R.drawable.icon ) ;
        showNotEnoughDialog() ;
    }
	
	private void showNotEnoughDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "Enery is Full!" )
        .setCancelable( false )
        .setPositiveButton( "To FabLife ", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			Intent intent = new Intent( getApplicationContext(), Splash.class ) ;
        			startActivity( intent ) ;
        			finish() ;
                }
        	}
        )
		.setNegativeButton( "Exit", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			finish() ;
	            }
	    	}
		) ;
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		return ;
	}
}
