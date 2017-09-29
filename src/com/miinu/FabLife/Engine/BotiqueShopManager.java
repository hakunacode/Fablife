package com.miinu.FabLife.Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.miinu.FabLife.Application.FabLifeApplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class BotiqueShopManager 
{
	ArrayList<Integer>			mSelledShops	= new ArrayList<Integer>() ;
	ArrayList<Integer>			mRemainShops	= new ArrayList<Integer>() ;
	Context						mCtx			= null ;
	SharedPreferences 			mSPref			= null ;
	SharedPreferences.Editor	editor			= null ;
	
	public static final int		STATUS_NONE			= 0 ;
	public static final int		STATUS_CONSTRUCT	= 1 ;
	public static final int		STATUS_OPEN			= 2 ;
	public static final int		STATUS_RESTOCK		= 3 ;
	public static final int		STATUS_COLLECT		= 4 ;
	public static final int		STATUS_RUN			= 5 ;
	
	public BotiqueShopManager( Context context )
	{
		mCtx = context ;
		mSPref = mCtx.getSharedPreferences( "Botique", Activity.MODE_PRIVATE ) ;
		readFromFile() ;
	}
	
	void readFromFile()
	{
		try
    	{
    		InputStreamReader isr = new InputStreamReader( mCtx.openFileInput( "selled_shops.txt" ) ) ;
            BufferedReader br = new BufferedReader( isr ) ;
            String str ;
            while( ( str = br.readLine() ) != null )
            {
            	mSelledShops.add( Integer.valueOf( str ) ) ;
            }
            isr.close();
    	}
    	catch ( Exception e ) { e.printStackTrace(); } 
        
        for ( int i = 0 ; i < 29 ; i++ )
        {
        	if ( !mSelledShops.contains( Integer.valueOf( i ) ) )
        	{
        		mRemainShops.add( Integer.valueOf( i ) ) ;
        	}
        }
	}
	
	public int getConstructCoinsByIndex( int index )
	{
		return getConstructCoins( mRemainShops.get( index ).intValue() ) ;
	}
	
	public void buy( int index )
	{
		Integer item = mRemainShops.get( index ) ;
		mSelledShops.add( item ) ;
		mRemainShops.remove( index ) ;
		
		FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() - getConstructCoins( item.intValue() ) ) ;
		
		writeToFile() ;
		
		String key_status 	= item.toString() + "_status" ;
		String key_time		= item.toString() + "_time" ;
		editor = mSPref.edit() ;
		editor.putInt( key_status, STATUS_CONSTRUCT ) ;
		editor.putInt( key_time, getConstructTime( item.intValue() ) ) ;
		editor.commit() ;
	}
	
	public void writeToFile()
	{
		 try
    	 {
    		 OutputStreamWriter osw = new OutputStreamWriter( mCtx.openFileOutput( "selled_shops.txt", Context.MODE_PRIVATE ) ) ;
    		 for( int i = 0 ; i < mSelledShops.size() ; i++ )
    		 {
    			 osw.write( mSelledShops.get( i ).toString() ) ;
    			 osw.write( "\n" ) ;
    		 }
             osw.close();
         }
    	 catch ( Exception e ) { e.printStackTrace(); }
	}
	
	public int getSelledCount()
	{
		return mSelledShops.size() ;
	}
	
	public int getSelledShop( int index )
	{
		return mSelledShops.get( index ).intValue() ;
	}
	
	public int getShopStatus( int shop )
	{
		String key_status 	= "" + shop + "_status" ;
		int status = mSPref.getInt( key_status, STATUS_CONSTRUCT ) ;
		return status ;
	}
	
	public int getShopTime( int shop )
	{
		String key_time		= "" + shop + "_time" ;
		int time = mSPref.getInt( key_time, STATUS_CONSTRUCT ) ;
		mSPref.getInt( key_time, getConstructTime( shop ) ) ;
		return time ;
	}
	
	public SharedPreferences.Editor getEditor()
	{
		editor = mSPref.edit() ;
		return editor ;
	}
	
	public void commit()
	{
		editor.commit() ;
	}
	
	public int getRemainCount()
	{
		return mRemainShops.size() ;
	}
	
	public int getRemainShop( int index )
	{
		return mRemainShops.get( index ).intValue() ;
	}
	
	public int getCollectCoins( int shop )
	{
		int coins = 0 ;
		switch( shop )
		{
			case 0 :
				coins = 18 ;
				break ;
			case 1 :
				coins = 34 ;
				break ;
			case 2 :
				coins = 54 ;
				break ;
			case 3 :
				coins = 75 ;
				break ;
			case 4 :
				coins = 105 ;
				break ;
			case 5 :
				coins = 136 ;
				break ;
			case 6 :
				coins = 158 ;
				break ;
			case 7 :
				coins = 181 ;
				break ;
			case 8 :
				coins = 227 ;
				break ;
			case 9 :
				coins = 243 ;
				break ;
			case 10 :
				coins = 258 ;
				break ;
			case 11 :
				coins = 285 ;
				break ;
			case 12 :
				coins = 340 ;
				break ;
			case 13 :
				coins = 435 ;
				break ;
			case 14 :
				coins = 550 ;
				break ;
			case 15 :
				coins = 670 ;
				break ;
			case 16 :
				coins = 920 ;
				break ;
			case 17 :
				coins = 1110 ;
				break ;
			case 18 :
				coins = 1325 ;
				break ;
			case 19 :
				coins = 1600 ;
				break ;
			case 20 :
				coins = 1900 ;
				break ;
			case 21 :
				coins = 2150 ;
				break ;
			case 22 :
				coins = 2410 ;
				break ;
			case 23 :
				coins = 2680 ;
				break ;
			case 24 :
				coins = 3200 ;
				break ;
			case 25 :
				coins = 3800 ;
				break ;
			case 26 :
				coins = 4200 ;
				break ;
			case 27 :
				coins = 4600 ;
				break ;
			case 28 :
				coins = 5000 ;
				break ;
		}
		return coins ;
	}
	
	public int getConstructFinish( int shop )
	{
		int coins = 0 ;
		switch( shop )
		{
			case 0 :
				coins = 15 ;
				break ;
			case 1 :
				coins = 15 ;
				break ;
			case 2 :
				coins = 15 ;
				break ;
			case 3 :
				coins = 15 ;
				break ;
			case 4 :
				coins = 15 ;
				break ;
			case 5 :
				coins = 15 ;
				break ;
			case 6 :
				coins = 15 ;
				break ;
			case 7 :
				coins = 15 ;
				break ;
			case 8 :
				coins = 15 ;
				break ;
			case 9 :
				coins = 15 ;
				break ;
			case 10 :
				coins = 25 ;
				break ;
			case 11 :
				coins = 25 ;
				break ;
			case 12 :
				coins = 25 ;
				break ;
			case 13 :
				coins = 25 ;
				break ;
			case 14 :
				coins = 25 ;
				break ;
			case 15 :
				coins = 25 ;
				break ;
			case 16 :
				coins = 25 ;
				break ;
			case 17 :
				coins = 25 ;
				break ;
			case 18 :
				coins = 25 ;
				break ;
			case 19 :
				coins = 25 ;
				break ;
			case 20 :
				coins = 45 ;
				break ;
			case 21 :
				coins = 45 ;
				break ;
			case 22 :
				coins = 45 ;
				break ;
			case 23 :
				coins = 45 ;
				break ;
			case 24 :
				coins = 45 ;
				break ;
			case 25 :
				coins = 45 ;
				break ;
			case 26 :
				coins = 45 ;
				break ;
			case 27 :
				coins = 45 ;
				break ;
			case 28 :
				coins = 45 ;
				break ;
		}
		return coins ;
	}
	
	public int getConstructCoins( int shop )
	{
		int coins = 0 ;
		switch( shop )
		{
			case 0 :
				coins = 60 ;
				break ;
			case 1 :
				coins = 90 ;
				break ;
			case 2 :
				coins = 110 ;
				break ;
			case 3 :
				coins = 130 ;
				break ;
			case 4 :
				coins = 150 ;
				break ;
			case 5 :
				coins = 195 ;
				break ;
			case 6 :
				coins = 225 ;
				break ;
			case 7 :
				coins = 255 ;
				break ;
			case 8 :
				coins = 420 ;
				break ;
			case 9 :
				coins = 440 ;
				break ;
			case 10 :
				coins = 460 ;
				break ;
			case 11 :
				coins = 750 ;
				break ;
			case 12 :
				coins = 810 ;
				break ;
			case 13 :
				coins = 900 ;
				break ;
			case 14 :
				coins = 1400 ;
				break ;
			case 15 :
				coins = 1600 ;
				break ;
			case 16 :
				coins = 2000 ;
				break ;
			case 17 :
				coins = 2400 ;
				break ;
			case 18 :
				coins = 4000 ;
				break ;
			case 19 :
				coins = 5000 ;
				break ;
			case 20 :
				coins = 6000 ;
				break ;
			case 21 :
				coins = 7000 ;
				break ;
			case 22 :
				coins = 8000 ;
				break ;
			case 23 :
				coins = 9000 ;
				break ;
			case 24 :
				coins = 12000 ;
				break ;
			case 25 :
				coins = 14400 ;
				break ;
			case 26 :
				coins = 15600 ;
				break ;
			case 27 :
				coins = 16800 ;
				break ;
			case 28 :
				coins = 21000 ;
				break ;
		}
		return coins ;
	}
	
	public int getConstructTime( int shop )
	{
		int coins = 0 ;
		switch( shop )
		{
			case 0 :
				coins = 300 ;
				break ;
			case 1 :
				coins = 300 ;
				break ;
			case 2 :
				coins = 300 ;
				break ;
			case 3 :
				coins = 300 ;
				break ;
			case 4 :
				coins = 480 ;
				break ;
			case 5 :
				coins = 600 ;
				break ;
			case 6 :
				coins = 900 ;
				break ;
			case 7 :
				coins = 1200 ;
				break ;
			case 8 :
				coins = 1800 ;
				break ;
			case 9 :
				coins = 2400 ;
				break ;
			case 10 :
				coins = 3000 ;
				break ;
			case 11 :
				coins = 3600 ;
				break ;
			case 12 :
				coins = 7200 ;
				break ;
			case 13 :
				coins = 14400 ;
				break ;
			case 14 :
				coins = 21600 ;
				break ;
			case 15 :
				coins = 28800 ;
				break ;
			case 16 :
				coins = 43200 ;
				break ;
			case 17 :
				coins = 50400 ;
				break ;
			case 18 :
				coins = 57600 ;
				break ;
			case 19 :
				coins = 64800 ;
				break ;
			case 20 :
				coins = 72000 ;
				break ;
			case 21 :
				coins = 79200 ;
				break ;
			case 22 :
				coins = 86400 ;
				break ;
			case 23 :
				coins = 103680 ;
				break ;
			case 24 :
				coins = 120960 ;
				break ;
			case 25 :
				coins = 138240 ;
				break ;
			case 26 :
				coins = 155520 ;
				break ;
			case 27 :
				coins = 172800 ;
				break ;
			case 28 :
				coins = 190080 ;
				break ;
		}
		return coins ;
	}
	
	public int getRestockCoins( int shop )
	{
		int coins = 0 ;
		switch( shop )
		{
			case 0 :
				coins = 15 ;
				break ;
			case 1 :
				coins = 30 ;
				break ;
			case 2 :
				coins = 50 ;
				break ;
			case 3 :
				coins = 70 ;
				break ;
			case 4 :
				coins = 100 ;
				break ;
			case 5 :
				coins = 130 ;
				break ;
			case 6 :
				coins = 150 ;
				break ;
			case 7 :
				coins = 170 ;
				break ;
			case 8 :
				coins = 210 ;
				break ;
			case 9 :
				coins = 220 ;
				break ;
			case 10 :
				coins = 230 ;
				break ;
			case 11 :
				coins = 250 ;
				break ;
			case 12 :
				coins = 270 ;
				break ;
			case 13 :
				coins = 300 ;
				break ;
			case 14 :
				coins = 350 ;
				break ;
			case 15 :
				coins = 400 ;
				break ;
			case 16 :
				coins = 500 ;
				break ;
			case 17 :
				coins = 600 ;
				break ;
			case 18 :
				coins = 800 ;
				break ;
			case 19 :
				coins = 1000 ;
				break ;
			case 20 :
				coins = 1200 ;
				break ;
			case 21 :
				coins = 1400 ;
				break ;
			case 22 :
				coins = 1600 ;
				break ;
			case 23 :
				coins = 1800 ;
				break ;
			case 24 :
				coins = 2000 ;
				break ;
			case 25 :
				coins = 2400 ;
				break ;
			case 26 :
				coins = 2600 ;
				break ;
			case 27 :
				coins = 2800 ;
				break ;
			case 28 :
				coins = 3000 ;
				break ;
		}
		return coins ;
	}
	
	public int getRestockEnergy( int shop )
	{
		int coins = 0 ;
		switch( shop )
		{
			case 0 :
				coins = 1 ;
				break ;
			case 1 :
				coins = 1 ;
				break ;
			case 2 :
				coins = 1 ;
				break ;
			case 3 :
				coins = 1 ;
				break ;
			case 4 :
				coins = 1 ;
				break ;
			case 5 :
				coins = 1 ;
				break ;
			case 6 :
				coins = 3 ;
				break ;
			case 7 :
				coins = 3 ;
				break ;
			case 8 :
				coins = 3 ;
				break ;
			case 9 :
				coins = 3 ;
				break ;
			case 10 :
				coins = 3 ;
				break ;
			case 11 :
				coins = 3 ;
				break ;
			case 12 :
				coins = 7 ;
				break ;
			case 13 :
				coins = 7 ;
				break ;
			case 14 :
				coins = 7 ;
				break ;
			case 15 :
				coins = 7 ;
				break ;
			case 16 :
				coins = 7 ;
				break ;
			case 17 :
				coins = 14 ;
				break ;
			case 18 :
				coins = 14 ;
				break ;
			case 19 :
				coins = 14 ;
				break ;
			case 20 :
				coins = 14 ;
				break ;
			case 21 :
				coins = 14 ;
				break ;
			case 22 :
				coins = 14 ;
				break ;
			case 23 :
				coins = 22 ;
				break ;
			case 24 :
				coins = 22 ;
				break ;
			case 25 :
				coins = 22 ;
				break ;
			case 26 :
				coins = 22 ;
				break ;
			case 27 :
				coins = 22 ;
				break ;
			case 28 :
				coins = 22 ;
				break ;
		}
		return coins ;
	}
}
