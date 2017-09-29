package com.miinu.FabLife.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class BackgroundManager 
{	
	Context						mCtx			= null ;	
	
	public BackgroundManager( Context context )
	{
		mCtx = context ;
	}
	
	public void initialize()
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Background", Activity.MODE_PRIVATE ) ;
		SharedPreferences.Editor editor = mSPref.edit() ;
		editor.putInt( "lbnew2.png", 80 ) ;
		editor.putInt( "lbnew25.png", 100 ) ;
		editor.putInt( "lbnew31.png", 150 ) ;
		editor.putInt( "lbnew24.png", 200 ) ;
		editor.putInt( "lbnew18.png", 100 ) ;
		editor.putInt( "lbnew20.png", 300 ) ;
		editor.putInt( "lbnew26.png", 300 ) ;
		editor.putInt( "lbnew11.png", 100 ) ;
		editor.putInt( "lbnew6.png", 90 ) ;
		editor.putInt( "lbnew29.png", 150 ) ;
		editor.putInt( "lbnew22.png", 250 ) ;
		editor.putInt( "lbnew15.png", 100 ) ;
		editor.putInt( "lbnew21.png", 150 ) ;
		editor.putInt( "lbnew27.png", 150 ) ;
		editor.putInt( "lbnew19.png", 125 ) ;
		editor.putInt( "lbnew16.png", 80 ) ;
		editor.putInt( "lbnew20.png", 300 ) ;
		
		editor.commit() ;
	}
	
	public boolean contains( String name )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Background", Activity.MODE_PRIVATE ) ;
		return mSPref.contains( name ) ;
	}
	
	public int getCoins( String name )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Background", Activity.MODE_PRIVATE ) ;
		return mSPref.getInt( name, 0 ) ;
	}
	
	public void remove( String name )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Background", Activity.MODE_PRIVATE ) ;
		SharedPreferences.Editor editor = mSPref.edit() ;
		editor.remove( name ) ;
		editor.commit() ;
	}
}
