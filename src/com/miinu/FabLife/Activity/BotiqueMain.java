package com.miinu.FabLife.Activity ;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.BotiqueShopManager;
import com.miinu.FabLife.ViewComponent.BotiqueItemView;
import com.miinu.FabLife.ViewComponent.StatusBarCash;
import com.miinu.FabLife.ViewComponent.StatusBarCoin;
import com.miinu.FabLife.ViewComponent.StatusBarEnergy;
import com.miinu.FabLife.ViewComponent.StatusBarLevel;
import android.app.Activity ;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle ;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BotiqueMain extends Activity implements OnClickListener
{
	static public final int			MESSAGE_NOENERGY		= 1 ;
	static public final int			MESSAGE_NOMONEY			= 2 ;
	static public final int 		MESSAGE_PLAY_OPEN		= 3 ;
	static public final int 		MESSAGE_PLAY_RESTOCK	= 4 ;
	static public final int 		MESSAGE_PLAY_COLLECT	= 5 ;
	static public final int 		MESSAGE_FINISH_CONSTRUCTION	= 6 ;
	
	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private		ImageView			mArrowView				= null ;
	private		ImageButton			mAddStoreBtn 			= null ;
	private		ImageButton			mInfoBtn				= null ;
	private		ImageButton			mGoHomeBtn				= null ;
	
	private		FrameLayout			mShop05Layout			= null ;
	private		FrameLayout			mShop06Layout			= null ;
	private		FrameLayout			mShop07Layout			= null ;
	private		FrameLayout			mShop08Layout			= null ;
	private		FrameLayout			mShop09Layout			= null ;
	private		FrameLayout			mShop10Layout			= null ;
	
	private		BotiqueItemView		mView01					= null ;
	private		BotiqueItemView		mView02					= null ;
	private		BotiqueItemView		mView03					= null ;
	private		BotiqueItemView		mView04					= null ;
	private		BotiqueItemView		mView05					= null ;
	private		BotiqueItemView		mView06					= null ;
	private		BotiqueItemView		mView07					= null ;
	private		BotiqueItemView		mView08					= null ;
	private		BotiqueItemView		mView09					= null ;
	private		BotiqueItemView		mView10					= null ;
	private		BotiqueItemView		mView11					= null ;
	private		BotiqueItemView		mView12					= null ;
	private		BotiqueItemView		mView13					= null ;
	private		BotiqueItemView		mView14					= null ;
	private		BotiqueItemView		mView15					= null ;
	private		BotiqueItemView		mView16					= null ;
	private		BotiqueItemView		mView17					= null ;
	private		BotiqueItemView		mView18					= null ;
	private		BotiqueItemView		mView19					= null ;
	private		BotiqueItemView		mView20					= null ;
	private		BotiqueItemView		mView21					= null ;
	private		BotiqueItemView		mView22					= null ;
	private		BotiqueItemView		mView23					= null ;
	private		BotiqueItemView		mView24					= null ;
	private		BotiqueItemView		mView25					= null ;
	private		BotiqueItemView		mView26					= null ;
	private		BotiqueItemView		mView27					= null ;
	private		BotiqueItemView		mView28					= null ;
	private		BotiqueItemView		mView29					= null ;
	
	private		Handler				mEnvHandler				= null ;
	
	public static MediaPlayer		mBotiquePlayer			= null ;
	private		MediaPlayer			mOpenPlayer				= null ;
	private		MediaPlayer			mRestockPlayer			= null ;
	private		MediaPlayer			mCollectPlayer			= null ;
	
	private		boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.botique_main ) ;
        
        mStatusBarLayout		= (FrameLayout)		findViewById( R.id.StatusBarLayout ) ;
    	mStatusLevelView		= (StatusBarLevel)	findViewById( R.id.StatusLevelView ) ;
    	mStatusEnergyView		= (StatusBarEnergy)	findViewById( R.id.StatusEnergyView ) ;
    	mStatusCoinView			= (StatusBarCoin)	findViewById( R.id.StatusCoinView ) ;
    	mStatusCashView			= (StatusBarCash)	findViewById( R.id.StatusCashView ) ;
    	mCaptionView			= (TextView)		findViewById( R.id.StatusCaptionView ) ;
    	mTimerView				= (TextView)		findViewById( R.id.StatusTimerView ) ;
    	
    	mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
        mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
        mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
        mStatusEnergyView.setVipMember( FabLifeApplication.getProfileManager().isVIPMember() ) ;
        mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
        mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
        mCaptionView.setText( getString( R.string.level01_name + FabLifeApplication.getProfileManager().getLevel() - 1 ) ) ;
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mCaptionView.setTypeface( mFace ) ; mTimerView.setTypeface( mFace ) ;
        
        mArrowView		= (ImageView)		findViewById( R.id.BotiqueArrowView ) ;
        mAddStoreBtn 	= (ImageButton)		findViewById( R.id.BotiqueAddStroreButton ) ;
        mInfoBtn 		= (ImageButton)		findViewById( R.id.BotiqueInfoButton ) ;
        mGoHomeBtn		= (ImageButton)		findViewById( R.id.BotiqueGoHomeButton ) ;
        
        mShop05Layout	= (FrameLayout)		findViewById( R.id.Shop05Layout ) ;
        mShop06Layout	= (FrameLayout)		findViewById( R.id.Shop06Layout ) ;
        mShop07Layout	= (FrameLayout)		findViewById( R.id.Shop07Layout ) ;
        mShop08Layout	= (FrameLayout)		findViewById( R.id.Shop08Layout ) ;
        mShop09Layout	= (FrameLayout)		findViewById( R.id.Shop09Layout ) ;
        mShop10Layout	= (FrameLayout)		findViewById( R.id.Shop10Layout ) ;
        
        mView01			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView01 ) ;
        mView02			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView02 ) ;
        mView03			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView03 ) ;
        mView04			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView04 ) ;
        mView05			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView05 ) ;
        mView06			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView06 ) ;
        mView07			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView07 ) ;
        mView08			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView08 ) ;
        mView09			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView09 ) ;
        mView10			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView10 ) ;
        mView11			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView11 ) ;
        mView12			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView12 ) ;
        mView13			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView13 ) ;
        mView14			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView14 ) ;
        mView15			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView15 ) ;
        mView16			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView16 ) ;
        mView17			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView17 ) ;
        mView18			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView18 ) ;
        mView19			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView19 ) ;
        mView20			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView20 ) ;
        mView21			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView21 ) ;
        mView22			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView22 ) ;
        mView23			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView23 ) ;
        mView24			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView24 ) ;
        mView25			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView25 ) ;
        mView26			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView26 ) ;
        mView27			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView27 ) ;
        mView28			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView28 ) ;
        mView29			= (BotiqueItemView)	findViewById( R.id.BotiqueShopView29 ) ;
        
        mAddStoreBtn.setOnClickListener( this ) ;
        mInfoBtn.setOnClickListener( this ) ;
        mGoHomeBtn.setOnClickListener( this ) ;
        
        mBotiquePlayer	= MediaPlayer.create( getApplicationContext(), R.raw.boutique ) ;
        mBotiquePlayer.setLooping( true ) ;
		mOpenPlayer = MediaPlayer.create( getApplicationContext(), R.raw.hooray ) ;
		mRestockPlayer = MediaPlayer.create( getApplicationContext(), R.raw.restock ) ;
		mCollectPlayer = MediaPlayer.create( getApplicationContext(), R.raw.collectmoney ) ;
        
        mEnvHandler = new Handler()
        {
			@Override
			public void handleMessage( Message msg )
			{
				switch( msg.what )
				{
					case MESSAGE_NOENERGY :
						showNoEnergyDialog() ;
						break ;
					case MESSAGE_NOMONEY :
						showNoMoneyDialog() ;
						break ;
					case MESSAGE_PLAY_OPEN :
						if ( FabLifeApplication.getProfileManager().getEffect() )
						{
							if ( mOpenPlayer != null ) mOpenPlayer.start() ;
						}
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						break ;
					case MESSAGE_PLAY_RESTOCK :
						if ( FabLifeApplication.getProfileManager().getEffect() )
						{
							if ( mRestockPlayer != null ) mRestockPlayer.start() ;
						}
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						break ;
					case MESSAGE_PLAY_COLLECT :
						if ( FabLifeApplication.getProfileManager().getEffect() )
						{
							if ( mCollectPlayer != null ) mCollectPlayer.start() ;
						}
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						break ;
					case MESSAGE_FINISH_CONSTRUCTION :
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						break ;
				}
				mStatusBarLayout.invalidate() ;
			}
        } ;
        
        if ( getIntent().getBooleanExtra( "firstadd", false ) )
        {
        	Handler handler = new Handler()
        	{
				@Override
				public void handleMessage( Message msg )
				{
					Intent intent = new Intent( getApplicationContext(), BotiqueTip.class ) ;
		        	startActivity( intent ) ;
				}
        	} ;
        	handler.sendEmptyMessageDelayed( 0, 1000 ) ;
        }
        
        if ( FabLifeApplication.getBotiqueShopManager().getSelledCount() == 0 )
        {
        	startArrowTip() ;
        }
        overridePendingTransition( R.anim.activity_slide_bottom_to_top, R.anim.hold ) ;
    }
    
    int energy_limit = 45 ;
    Handler mTimerHandler = new Handler()
    {
		@Override
		public void handleMessage( Message msg )
		{
			// TODO Auto-generated method stub
			if ( !isRunning )
				return ;
			
			energy_limit = FabLifeApplication.getProfileManager().isVIPMember() ? 55 : 45 ;
			if ( FabLifeApplication.getProfileManager().getEnergy() < energy_limit )
			{
				if ( FabLifeApplication.mTimerTime <= 0 )
				{
					FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() + 1 ) ;
					mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
					mStatusEnergyView.invalidate() ;
					FabLifeApplication.mTimerTime = 180 ;
				}
				else
				{
					FabLifeApplication.mTimerTime-- ;
				}
				
				if ( FabLifeApplication.getProfileManager().getEnergy() >= energy_limit )
				{
					mTimerView.setText( "" ) ;						
				}
				else
				{
					int minutes = FabLifeApplication.mTimerTime / 60 ;
					int second = FabLifeApplication.mTimerTime % 60 ;
					
					String time = "" ;
					
					if ( minutes < 10 ) time += ( "0" + minutes + ":" ) ;
					else	time += ( minutes + ":" ) ;
					
					if ( second < 10 ) time += ( "0" + second ) ;
					else	time += ( second ) ;
					mTimerView.setText( time  ) ;
				}
			}
			else
			{
				mTimerView.setText( ""  ) ;
				FabLifeApplication.mTimerTime = 180 ;
			}
			sendEmptyMessageDelayed( 0, 1000 ) ;
		}
    } ;
    
    void startArrowTip()
    {
    	Handler handler = new Handler()
    	{
    		@Override
			public void handleMessage( Message msg )
			{
    			if ( msg.what == 1 )
				{
					mArrowView.setBackgroundResource( R.drawable.boutique_arrow1 ) ;
					sendEmptyMessageDelayed( 2, 200 ) ;
				}
    			else if ( msg.what == 2 )
				{
					mArrowView.setBackgroundResource( R.drawable.boutique_arrow2 ) ;
					sendEmptyMessageDelayed( 1, 200 ) ;
				}
			}
    	} ;
    	handler.sendEmptyMessageDelayed( 1, 200 ) ;
    }
    
    void fillItems()
    {
    	int count = FabLifeApplication.getBotiqueShopManager().getSelledCount() ;
    	for ( int i = 0 ; i < count ; i++ )
    	{
    		int shop = FabLifeApplication.getBotiqueShopManager().getSelledShop( i ) ;
    		switch( i )
    		{
    			case 0 :
    				mView01.setHandler( mEnvHandler ) ;
    				mView01.setShop( shop ) ;
    				break ;
    			case 1 :
    				mView02.setHandler( mEnvHandler ) ;
    				mView02.setShop( shop ) ;
    				break ;
    			case 2 :
    				mView03.setHandler( mEnvHandler ) ;
    				mView03.setShop( shop ) ;
    				break ;
    			case 3 :
    				mView04.setHandler( mEnvHandler ) ;
    				mView04.setShop( shop ) ;
    				break ;
    			case 4 :
    				mView05.setHandler( mEnvHandler ) ;
    				mView05.setShop( shop ) ;
    				break ;
    			case 5 :
    				mView06.setHandler( mEnvHandler ) ;
    				mView06.setShop( shop ) ;
    				break ;
    			case 6 :
    				mView07.setHandler( mEnvHandler ) ;
    				mView07.setShop( shop ) ;
    				break ;
    			case 7 :
    				mView08.setHandler( mEnvHandler ) ;
    				mView08.setShop( shop ) ;
    				break ;
    			case 8 :
    				mView09.setHandler( mEnvHandler ) ;
    				mView09.setShop( shop ) ;
    				break ;
    			case 9 :
    				mView10.setHandler( mEnvHandler ) ;
    				mView10.setShop( shop ) ;
    				break ;
    			case 10 :
    				mView11.setHandler( mEnvHandler ) ;
    				mView11.setShop( shop ) ;
    				break ;
    			case 11 :
    				mView12.setHandler( mEnvHandler ) ;
    				mView12.setShop( shop ) ;
    				break ;
    			case 12 :
    				mView13.setHandler( mEnvHandler ) ;
    				mView13.setShop( shop ) ;
    				break ;
    			case 13 :
    				mView14.setHandler( mEnvHandler ) ;
    				mView14.setShop( shop ) ;
    				break ;
    			case 14 :
    				mView15.setHandler( mEnvHandler ) ;
    				mView15.setShop( shop ) ;
    				break ;
    			case 15 :
    				mView16.setHandler( mEnvHandler ) ;
    				mView16.setShop( shop ) ;
    				break ;
    			case 16 :
    				mView17.setHandler( mEnvHandler ) ;
    				mView17.setShop( shop ) ;
    				break ;
    			case 17 :
    				mView18.setHandler( mEnvHandler ) ;
    				mView18.setShop( shop ) ;
    				break ;
    			case 18 :
    				mView19.setHandler( mEnvHandler ) ;
    				mView19.setShop( shop ) ;
    				break ;
    			case 19 :
    				mView20.setHandler( mEnvHandler ) ;
    				mView20.setShop( shop ) ;
    				break ;
    			case 20 :
    				mView21.setHandler( mEnvHandler ) ;
    				mView21.setShop( shop ) ;
    				break ;
    			case 21 :
    				mView22.setHandler( mEnvHandler ) ;
    				mView22.setShop( shop ) ;
    				break ;    				
    			case 22 :
    				mView23.setHandler( mEnvHandler ) ;
    				mView23.setShop( shop ) ;
    				break ;
    			case 23 :
    				mView24.setHandler( mEnvHandler ) ;
    				mView24.setShop( shop ) ;
    				break ;
    			case 24 :
    				mView25.setHandler( mEnvHandler ) ;
    				mView25.setShop( shop ) ;
    				break ;
    			case 25 :
    				mView26.setHandler( mEnvHandler ) ;
    				mView26.setShop( shop ) ;
    				break ;
    			case 26 :
    				mView27.setHandler( mEnvHandler ) ;
    				mView27.setShop( shop ) ;
    				break ;
    			case 27 :
    				mView28.setHandler( mEnvHandler ) ;
    				mView28.setShop( shop ) ;
    				break ;
    			case 28 :
    				mView29.setHandler( mEnvHandler ) ;
    				mView29.setShop( shop ) ;
    				break ;
    		}
    	}
    	
    	if ( count < 27 )
    	{
    		mShop10Layout.setVisibility( View.GONE ) ;
    	}
    	
    	if ( count < 24 )
    	{
    		mShop09Layout.setVisibility( View.GONE ) ;
    	}
    	
    	if ( count < 21 )
    	{
    		mShop08Layout.setVisibility( View.GONE ) ;
    	}
    	
    	if ( count < 18 )
    	{
    		mShop07Layout.setVisibility( View.GONE ) ;
    	}
    	
    	if ( count < 15 )
    	{
    		mShop06Layout.setVisibility( View.GONE ) ;
    	}
    	
    	if ( count < 12 )
    	{
    		mShop05Layout.setVisibility( View.GONE ) ;
    	}
    }
    
    void save()
    {
    	int count = FabLifeApplication.getBotiqueShopManager().getSelledCount() ;
    	Editor editor = FabLifeApplication.getBotiqueShopManager().getEditor() ;
    	for ( int i = 0 ; i < count ; i++ )
    	{
    		int shop = FabLifeApplication.getBotiqueShopManager().getSelledShop( i ) ;
    		int status = BotiqueShopManager.STATUS_NONE ;
    		int time = 0 ;
    		switch( i )
    		{
    			case 0 :
    				status = mView01.getStatus() ;
    				time = mView01.getTime() ;
    				mView01.clear() ;
    				break ;
    			case 1 :
    				status = mView02.getStatus() ;
    				time = mView02.getTime() ;
    				mView02.clear() ;
    				break ;
    			case 2 :
    				status = mView03.getStatus() ;
    				time = mView03.getTime() ;
    				mView03.clear() ;
    				break ;
    			case 3 :
    				status = mView04.getStatus() ;
    				time = mView04.getTime() ;
    				mView04.clear() ;
    				break ;
    			case 4 :
    				status = mView05.getStatus() ;
    				time = mView05.getTime() ;
    				mView05.clear() ;
    				break ;
    			case 5 :
    				status = mView06.getStatus() ;
    				time = mView06.getTime() ;
    				mView06.clear() ;
    				break ;
    			case 6 :
    				status = mView07.getStatus() ;
    				time = mView07.getTime() ;
    				mView07.clear() ;
    				break ;
    			case 7 :
    				status = mView08.getStatus() ;
    				time = mView08.getTime() ;
    				mView08.clear() ;
    				break ;
    			case 8 :
    				status = mView09.getStatus() ;
    				time = mView09.getTime() ;
    				mView09.clear() ;
    				break ;
    			case 9 :
    				status = mView10.getStatus() ;
    				time = mView10.getTime() ;
    				mView10.clear() ;
    				break ;
    			case 10 :
    				status = mView11.getStatus() ;
    				time = mView11.getTime() ;
    				mView11.clear() ;
    				break ;
    			case 11 :
    				status = mView12.getStatus() ;
    				time = mView12.getTime() ;
    				mView12.clear() ;
    				break ;
    			case 12 :
    				status = mView13.getStatus() ;
    				time = mView13.getTime() ;
    				mView13.clear() ;
    				break ;
    			case 13 :
    				status = mView14.getStatus() ;
    				time = mView14.getTime() ;
    				mView14.clear() ;
    				break ;
    			case 14 :
    				status = mView15.getStatus() ;
    				time = mView15.getTime() ;
    				mView15.clear() ;
    				break ;
    			case 15 :
    				status = mView16.getStatus() ;
    				time = mView16.getTime() ;
    				mView16.clear() ;
    				break ;
    			case 16 :
    				status = mView17.getStatus() ;
    				time = mView17.getTime() ;
    				mView17.clear() ;
    				break ;
    			case 17 :
    				status = mView18.getStatus() ;
    				time = mView18.getTime() ;
    				mView18.clear() ;
    				break ;
    			case 18 :
    				status = mView19.getStatus() ;
    				time = mView19.getTime() ;
    				mView19.clear() ;
    				break ;
    			case 19 :
    				status = mView20.getStatus() ;
    				time = mView20.getTime() ;
    				mView20.clear() ;
    				break ;
    			case 20 :
    				status = mView21.getStatus() ;
    				time = mView21.getTime() ;
    				mView21.clear() ;
    				break ;
    			case 21 :
    				status = mView22.getStatus() ;
    				time = mView22.getTime() ;
    				mView22.clear() ;
    				break ;    				
    			case 22 :
    				status = mView23.getStatus() ;
    				time = mView23.getTime() ;
    				mView23.clear() ;
    				break ;
    			case 23 :
    				status = mView24.getStatus() ;
    				time = mView24.getTime() ;
    				mView24.clear() ;
    				break ;
    			case 24 :
    				status = mView25.getStatus() ;
    				time = mView25.getTime() ;
    				mView25.clear() ;
    				break ;
    			case 25 :
    				status = mView26.getStatus() ;
    				time = mView26.getTime() ;
    				mView26.clear() ;
    				break ;
    			case 26 :
    				status = mView27.getStatus() ;
    				time = mView27.getTime() ;
    				mView27.clear() ;
    				break ;
    			case 27 :
    				status = mView28.getStatus() ;
    				time = mView28.getTime() ;
    				mView28.clear() ;
    				break ;
    			case 28 :
    				status = mView29.getStatus() ;
    				time = mView29.getTime() ;
    				mView29.clear() ;
    				break ;
    		}
    		String key_status 	= "" + shop + "_status" ;
    		String key_time		= "" + shop + "_time" ;
    		editor.putInt( key_status, status ) ;
    		editor.putInt( key_time, time ) ;
    	}
    	FabLifeApplication.getBotiqueShopManager().commit() ;
    }
    
    int getCustomerCount()
    {
    	int nCount = 0 ;
    	int count = FabLifeApplication.getBotiqueShopManager().getSelledCount() ;
    	for ( int i = 0 ; i < count ; i++ )
    	{
    		switch( i )
    		{
    			case 0 :
    				if ( mView01.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 1 :
    				if ( mView02.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 2 :
    				if ( mView03.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 3 :
    				if ( mView04.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 4 :
    				if ( mView05.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 5 :
    				if ( mView06.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 6 :
    				if ( mView07.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 7 :
    				if ( mView08.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 8 :
    				if ( mView09.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 9 :
    				if ( mView10.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 10 :
    				if ( mView11.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 11 :
    				if ( mView12.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 12 :
    				if ( mView13.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 13 :
    				if ( mView14.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 14 :
    				if ( mView15.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 15 :
    				if ( mView16.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 16 :
    				if ( mView17.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 17 :
    				if ( mView18.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 18 :
    				if ( mView19.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 19 :
    				if ( mView20.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 20 :
    				if ( mView21.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 21 :
    				if ( mView22.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;    				
    			case 22 :
    				if ( mView23.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 23 :
    				if ( mView24.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 24 :
    				if ( mView25.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 25 :
    				if ( mView26.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 26 :
    				if ( mView27.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 27 :
    				if ( mView28.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    			case 28 :
    				if ( mView29.getStatus() == BotiqueShopManager.STATUS_RUN )
    					nCount += ( 3 + Math.floor( Math.random() * 4 ) ) ;
    				break ;
    		}
    	}
    	return nCount ;
    }
    
    private void showNoEnergyDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.energy_not_enough ) )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			save() ;
        			if ( mBotiquePlayer != null )
        			{
        				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
        				mBotiquePlayer.release() ;
        				mBotiquePlayer = null ;
        			}
        			if ( mOpenPlayer != null )
        			{
        				if ( mOpenPlayer.isPlaying() ) mOpenPlayer.stop() ;
        				mOpenPlayer.release() ;
        				mOpenPlayer = null ;
        			}
        			if ( mRestockPlayer != null )
        			{
        				if ( mRestockPlayer.isPlaying() ) mRestockPlayer.stop() ;
        				mRestockPlayer.release() ;
        				mRestockPlayer = null ;
        			}
        			if ( mCollectPlayer != null )
        			{
        				if ( mCollectPlayer.isPlaying() ) mCollectPlayer.stop() ;
        				mCollectPlayer.release() ;
        				mCollectPlayer = null ;
        			}
        			
        			action = true ;
        			Intent intent = new Intent( getApplicationContext(), Cafe.class ) ;
        			startActivity( intent ) ;
        			finish() ;
                }
        	}
        )
		.setNegativeButton( "No", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
    
    private void showNoMoneyDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.cash_not_enough ) )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			save() ;
        			if ( mBotiquePlayer != null )
        			{
        				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
        				mBotiquePlayer.release() ;
        				mBotiquePlayer = null ;
        			}
        			if ( mOpenPlayer != null )
        			{
        				if ( mOpenPlayer.isPlaying() ) mOpenPlayer.stop() ;
        				mOpenPlayer.release() ;
        				mOpenPlayer = null ;
        			}
        			if ( mRestockPlayer != null )
        			{
        				if ( mRestockPlayer.isPlaying() ) mRestockPlayer.stop() ;
        				mRestockPlayer.release() ;
        				mRestockPlayer = null ;
        			}
        			if ( mCollectPlayer != null )
        			{
        				if ( mCollectPlayer.isPlaying() ) mCollectPlayer.stop() ;
        				mCollectPlayer.release() ;
        				mCollectPlayer = null ;
        			}
        			
        			action = true ;
        			Intent intent = new Intent( getApplicationContext(), Bank.class ) ;
        			startActivity( intent ) ;
        			finish() ;
                }
        	}
        )
		.setNegativeButton( "No", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.BotiqueAddStroreButton )
		{
			FabLifeApplication.playTapEffect() ;
			save() ;
			if ( mBotiquePlayer != null )
			{
				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
				mBotiquePlayer.release() ;
				mBotiquePlayer = null ;
			}
			if ( mOpenPlayer != null )
			{
				if ( mOpenPlayer.isPlaying() ) mOpenPlayer.stop() ;
				mOpenPlayer.release() ;
				mOpenPlayer = null ;
			}
			if ( mRestockPlayer != null )
			{
				if ( mRestockPlayer.isPlaying() ) mRestockPlayer.stop() ;
				mRestockPlayer.release() ;
				mRestockPlayer = null ;
			}
			if ( mCollectPlayer != null )
			{
				if ( mCollectPlayer.isPlaying() ) mCollectPlayer.stop() ;
				mCollectPlayer.release() ;
				mCollectPlayer = null ;
			}
			action = true ;
			Intent intent = new Intent( this, BotiqueAddStore.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.BotiqueInfoButton )
		{
			FabLifeApplication.playTapEffect() ;
			Intent intent = new Intent( this, BotiqueInfo.class ) ;
			intent.putExtra( "shop", FabLifeApplication.getBotiqueShopManager().getSelledCount() ) ;
			intent.putExtra( "customer", getCustomerCount() ) ;
			startActivity( intent ) ;
		}
		else if ( view.getId() == R.id.BotiqueGoHomeButton )
		{
			FabLifeApplication.playHomeEffect() ;
			save() ;
			if ( mBotiquePlayer != null )
			{
				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
				mBotiquePlayer.release() ;
				mBotiquePlayer = null ;
			}
			if ( mOpenPlayer != null )
			{
				if ( mOpenPlayer.isPlaying() ) mOpenPlayer.stop() ;
				mOpenPlayer.release() ;
				mOpenPlayer = null ;
			}
			if ( mRestockPlayer != null )
			{
				if ( mRestockPlayer.isPlaying() ) mRestockPlayer.stop() ;
				mRestockPlayer.release() ;
				mRestockPlayer = null ;
			}
			if ( mCollectPlayer != null )
			{
				if ( mCollectPlayer.isPlaying() ) mCollectPlayer.stop() ;
				mCollectPlayer.release() ;
				mCollectPlayer = null ;
			}

			action = true ;
			Intent intent = new Intent( getApplicationContext(), main.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		if ( mBotiquePlayer != null )
			mBotiquePlayer.pause() ;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		if ( FabLifeApplication.getProfileManager().getEffect() )
		{
			if ( mBotiquePlayer != null ) mBotiquePlayer.start() ;
		}
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart() ;
		if ( FabLifeApplication.getProfileManager().getBotiqueFinishTime() != 0 )
        {
        	long time_diff = System.currentTimeMillis() - FabLifeApplication.getProfileManager().getBotiqueFinishTime() ;
        	time_diff = time_diff / 1000 ;
        	
	        int count = FabLifeApplication.getBotiqueShopManager().getSelledCount() ;
	        Editor editor = FabLifeApplication.getBotiqueShopManager().getEditor() ;
	        for ( int i = 0 ; i < count ; i++ )
	        {
	        	int shop = FabLifeApplication.getBotiqueShopManager().getSelledShop( i ) ;
	        	int status = FabLifeApplication.getBotiqueShopManager().getShopStatus( shop ) ;
	        	int time = FabLifeApplication.getBotiqueShopManager().getShopTime( shop ) ;
	        	if ( status == BotiqueShopManager.STATUS_CONSTRUCT )
	        	{
	        		time = time - (int)time_diff ;
	        		if ( time <= 0 ) { time = 0 ; status = BotiqueShopManager.STATUS_OPEN ; }
	        	}
	        	else if ( status == BotiqueShopManager.STATUS_RUN )
	        	{
	        		time = time - (int)time_diff ;
	        		if ( time <= 0 ) { time = 0 ; status = BotiqueShopManager.STATUS_COLLECT ; }
	        	}
	        	String key_status 	= "" + shop + "_status" ;
	        	String key_time		= "" + shop + "_time" ;
	        	editor.putInt( key_status, status ) ;
	        	editor.putInt( key_time, time ) ;
	        }
	        FabLifeApplication.getBotiqueShopManager().commit() ;
        }
        fillItems() ;
		if ( FabLifeApplication.getProfileManager().getTutorialFinished() && FabLifeApplication.getProfileManager().getFinishTime() != 0 )
        {
        	long time_diff = System.currentTimeMillis() - FabLifeApplication.getProfileManager().getFinishTime() ;
        	time_diff = time_diff / 1000 ;
        	
        	int energy_diff = (int) (time_diff / 180) ;
        	int second_diff = (int) (time_diff % 180) ;
        	
        	int c_time = FabLifeApplication.getProfileManager().getCurrentTimerTime() ;
        	if ( ( c_time - second_diff ) <= 0 ) energy_diff++ ;
        	
        	if ( FabLifeApplication.getProfileManager().getEnergy() < energy_limit )
        	{
        		FabLifeApplication.getProfileManager().setEnergy(FabLifeApplication. getProfileManager().getEnergy() + energy_diff ) ;
        		if ( FabLifeApplication.getProfileManager().getEnergy() >= energy_limit )
        		{
        			FabLifeApplication.getProfileManager().setEnergy( energy_limit ) ;
        			FabLifeApplication.mTimerTime = 180 ;
        		}
        		else
        		{
        			if ( ( c_time - second_diff ) <= 0 )
        			{
        				FabLifeApplication.mTimerTime = 180 - ( second_diff - c_time ) ;
        			}
        			else
        			{
        				FabLifeApplication.mTimerTime = c_time - second_diff ;
        			}
        		}
        	}
        }
		isRunning = true ;
		mTimerHandler.sendEmptyMessage( 0 ) ;
	}

	@Override
	protected void onStop() 
	{
		// TODO Auto-generated method stub
		super.onStop() ;
		isRunning = false ;
		if ( FabLifeApplication.getProfileManager() != null )
		{
			FabLifeApplication.getProfileManager().setBotiqueFinishTime( System.currentTimeMillis() ) ;
			if ( action )
			{
				FabLifeApplication.getProfileManager().setFinishTime( 0 ) ;
			}
			else
			{
				FabLifeApplication.getProfileManager().setFinishTime( System.currentTimeMillis() ) ;
				FabLifeApplication.getProfileManager().setCurrentTimerTime( FabLifeApplication.mTimerTime ) ;
			}
			FabLifeApplication.getProfileManager().writeToFile() ;
		}
		try
		{
			System.gc() ;
		}
		catch( Exception e )
		{
			e.printStackTrace() ;
		}
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
	}
}