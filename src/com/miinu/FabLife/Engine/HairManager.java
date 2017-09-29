package com.miinu.FabLife.Engine;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

public class HairManager 
{
	final int					HAIRCOLOR_1			= 1 ;
	final int					HAIRCOLOR_2			= 2 ;
	final int					HAIRCOLOR_3			= 3 ;
	final int					HAIRCOLOR_4			= 4 ;
	final int					HAIRCOLOR_5			= 5 ;
	final int					HAIRCOLOR_6			= 6 ;
	
	Context						mCtx				= null ;
	AssetManager				mMgr				= null ;
	
	public HairManager( Context context )
	{
		mCtx = context ;
		mMgr = mCtx.getAssets() ;
	}
	
	public void initialize()
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Hair", Activity.MODE_PRIVATE ) ;
		SharedPreferences.Editor editor = mSPref.edit() ;
		editor.clear() ;
		try 
		{
			String[] names = mMgr.list( "items/shop3" ) ;
			for ( int i = 0 ; i < names.length ; i++ )
			{
				editor.putInt( "items/shop3/" + names[ i ], 1 ) ;
			}
			names = null ;
			names = mMgr.list( "items/shop6" ) ;
			for ( int i = 0 ; i < names.length ; i++ )
			{
				editor.putInt( "items/shop6/" + names[ i ], 1 ) ;
			}
			names = mMgr.list( "items/shop11" ) ;
			for ( int i = 0 ; i < names.length ; i++ )
			{
				editor.putInt( "items/shop11/" + names[ i ], 1 ) ;
			}
			names = null ;
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		editor.commit() ;
	}
	
	public boolean contains( String name )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Hair", Activity.MODE_PRIVATE ) ;
		return mSPref.contains( name ) ;
	}
	
	public int	getHairColor( String name )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Hair", Activity.MODE_PRIVATE ) ;
		return mSPref.getInt( name, 1 ) ;
	}
	
	public void setHairColor( String name, int index )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Hair", Activity.MODE_PRIVATE ) ;
		SharedPreferences.Editor editor = mSPref.edit() ;
		editor.putInt( name, index ) ;
		editor.commit() ;
	}
	
	public void remove( String name )
	{
		SharedPreferences mSPref = mCtx.getSharedPreferences( "Hair", Activity.MODE_PRIVATE ) ;
		SharedPreferences.Editor editor = mSPref.edit() ;
		editor.remove( name ) ;
		editor.commit() ;
	}
}
