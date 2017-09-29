package com.miinu.FabLife.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import com.miinu.FabLife.R;
import com.miinu.FabLife.Engine.AppliedItemManager;
import com.miinu.FabLife.Engine.BotiqueShopManager;
import com.miinu.FabLife.Engine.BuiedItemManager;
import com.miinu.FabLife.Engine.HairManager;
import com.miinu.FabLife.Engine.ProfileManager;
import com.openfeint.api.OpenFeint;
import com.openfeint.api.OpenFeintDelegate;
import com.openfeint.api.OpenFeintSettings;
import com.openfeint.api.resource.Achievement;
import com.openfeint.api.resource.Leaderboard;

public class FabLifeApplication extends Application 
{	
	private static ProfileManager		mProfileManager		= null ;
	private static AppliedItemManager	mAppliedItemManager	= null ;
	private static HairManager			mHairManager		= null ;
	private static BuiedItemManager		mBuiedItemManager	= null ;
	private static BotiqueShopManager	mBotiqueShopManager	= null ;
	
	public static boolean				mbIsRunning			= false ;
	public static int					mTimerTime			= 180 ;
	
	public static Bitmap				mNoClosedBitmap		= null ;
	public static Bitmap				mClosedBitmap		= null ;
	
	private static MediaPlayer			mMainMusicPlayer	= null ;
	private static MediaPlayer			mTapEffectPlayer	= null ;
	private static MediaPlayer			mHomeEffectPlayer	= null ;
	
    @Override
    public void onCreate()
    {
        super.onCreate() ;

        try
        {
	        Map<String, Object> options = new HashMap<String, Object>() ;
	        options.put( OpenFeintSettings.SettingCloudStorageCompressionStrategy, OpenFeintSettings.CloudStorageCompressionStrategyDefault ) ;
	
	        OpenFeintSettings settings = new OpenFeintSettings( "FabLife", "5YyzzyCB22y9yfg0G1iDlA", "8t7GvTGsb4gRpkAz5wU6gDkVmkFcV5iGeCAKeAvbJc", "358913", options ) ;
	        
	        OpenFeint.initialize( this, settings, new OpenFeintDelegate() {} ) ;
	        
	        Achievement.list( new Achievement.ListCB()
	        {
				@Override
				public void onSuccess( List<Achievement> _achievements )
				{
				}
			}) ;
	        
	        Leaderboard.list( new Leaderboard.ListCB()
	        {
				@Override
				public void onSuccess( List<Leaderboard> _leaderboards )
				{
				}
			});
        }
        catch( Exception e )
        {
        	e.printStackTrace() ;
        }
        
        mProfileManager 		= new ProfileManager( getApplicationContext() ) ;
        mAppliedItemManager 	= new AppliedItemManager( getApplicationContext() ) ;
        mHairManager			= new HairManager( getApplicationContext() ) ;
        mBuiedItemManager		= new BuiedItemManager( getApplicationContext() ) ;
        mBotiqueShopManager		= new BotiqueShopManager( getApplicationContext() ) ;
        
        mMainMusicPlayer		= MediaPlayer.create( this, R.raw.main ) ;
        mMainMusicPlayer.setLooping( true ) ;
        mTapEffectPlayer		= MediaPlayer.create( this, R.raw.tap ) ;
        mHomeEffectPlayer		= MediaPlayer.create( this, R.raw.home ) ;
    }

    public static void init( Context context )
    {
    	if ( mProfileManager == null ) 			mProfileManager = new ProfileManager( context ) ;
        if ( mAppliedItemManager == null ) 		mAppliedItemManager = new AppliedItemManager( context ) ;
        if ( mHairManager == null ) 			mHairManager = new HairManager( context ) ;
        if ( mBuiedItemManager == null )		mBuiedItemManager = new BuiedItemManager( context ) ;
        if ( mBotiqueShopManager == null )		mBotiqueShopManager = new BotiqueShopManager( context ) ;
        
        if ( mMainMusicPlayer == null )
        {
        	mMainMusicPlayer = MediaPlayer.create( context, R.raw.main ) ;
        	mMainMusicPlayer.setLooping( true ) ;
        }
        if ( mTapEffectPlayer == null )			mTapEffectPlayer = MediaPlayer.create( context, R.raw.tap ) ;
        if ( mHomeEffectPlayer == null )		mHomeEffectPlayer = MediaPlayer.create( context, R.raw.home ) ;
    }
    
    public static ProfileManager getProfileManager()
    {
    	return  mProfileManager ;
    }
    
    public static AppliedItemManager getAppliedManager()
    {
    	return  mAppliedItemManager ;
    }
    
    public static HairManager getHairManager()
    {
    	return  mHairManager ;
    }
    
    public static BuiedItemManager getBuiedManager()
    {
    	return  mBuiedItemManager ;
    }
    
    public static BotiqueShopManager getBotiqueShopManager()
    {
    	return  mBotiqueShopManager ;
    }
    
    public static void playMainMusic()
    {
    	if ( mMainMusicPlayer != null )
    	{
    		if ( mProfileManager.getMusic() )
    			mMainMusicPlayer.start() ;
    	}
    }
    
    public static void stopMainMusic()
    {
    	if ( mMainMusicPlayer != null )
    	{
    		mMainMusicPlayer.pause() ;
    	}
    }
    
    public static void playTapEffect()
    {
    	if ( mTapEffectPlayer != null )
    	{
    		if ( mProfileManager.getEffect() )
    			mTapEffectPlayer.start() ;
    	}
    }
    
    public static void playHomeEffect()
    {
    	if ( mHomeEffectPlayer != null )
    	{
    		if ( mProfileManager.getEffect() )
    			mHomeEffectPlayer.start() ;
    	}
    }
    
    public static void clear()
    {
    	if ( mAppliedItemManager != null ) 	mAppliedItemManager.clear() ;
    	if ( mBuiedItemManager != null )	mBuiedItemManager.clear() ;
    	
    	mProfileManager		= null ;
    	mAppliedItemManager	= null ;
    	mHairManager		= null ;
    	mBuiedItemManager	= null ;
    	mBotiqueShopManager	= null ;
    	
    	if ( mNoClosedBitmap != null ) { mNoClosedBitmap.recycle() ; mNoClosedBitmap = null ; }
    	if ( mClosedBitmap != null ) { mClosedBitmap.recycle() ; mClosedBitmap = null ; }
    	
    	if ( mMainMusicPlayer != null ) { if ( mMainMusicPlayer.isPlaying() ) mMainMusicPlayer.stop() ; mMainMusicPlayer.release() ; mMainMusicPlayer = null ; }
    	if ( mTapEffectPlayer != null ) { if ( mTapEffectPlayer.isPlaying() ) mTapEffectPlayer.stop() ; mTapEffectPlayer.release() ; mTapEffectPlayer = null ; }
    	if ( mHomeEffectPlayer != null ) { if ( mHomeEffectPlayer.isPlaying() ) mHomeEffectPlayer.stop() ; mHomeEffectPlayer.release() ; mHomeEffectPlayer = null ; }
    }
}
