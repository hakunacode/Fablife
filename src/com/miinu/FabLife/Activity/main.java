package com.miinu.FabLife.Activity ;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.BackgroundManager;
import com.miinu.FabLife.Services.BackService;
import com.miinu.FabLife.ViewComponent.AvatarView;
import com.miinu.FabLife.ViewComponent.StatusBarCash;
import com.miinu.FabLife.ViewComponent.StatusBarCoin;
import com.miinu.FabLife.ViewComponent.StatusBarEnergy;
import com.miinu.FabLife.ViewComponent.StatusBarLevel;
import android.app.Activity ;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle ;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class main extends Activity implements OnClickListener
{
	public static final String		STRING_STEP				= "step" ;
	public static final int			STEP_WELCOME			= 0 ;
	public static final int			STEP_MYJOB				= 1 ;
	public static final int			STEP_SHOP				= 2 ;
	public static final int			STEP_MYCLOSET			= 3 ;
	
	private final int				ACTIVITY_START			= 10 ;
	private final int				ACTIVITY_SETTINGS		= 11 ;
	private final int				ACTIVITY_OPENFEINT		= 12 ;
	
	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private		AvatarView			mAvatarView				= null ;
	
	private		ImageButton			mBtnJob					= null ;
	private		ImageButton			mBtnCloset				= null ;
	private		ImageButton			mBtnBank				= null ;
	private		ImageButton			mBtnSetting				= null ;
	private		ImageButton			mBtnShops				= null ;
	private		ImageButton			mBtnLookbook			= null ;
	private 	ImageView			mExclamationView		= null ;
	private		ImageButton			mBtnEnergy				= null ;
	private		ImageButton			mBtnVip					= null ;
	private		ImageButton			mBtnAward				= null ;
	private		ImageButton			mBtnOpenFeint			= null ;
	private		ImageButton			mBtnOpenBoutique		= null ;
	
	private		ImageView			mClickIt01				= null ;
	private		ImageView			mClickIt02				= null ;
	private		ImageView			mClickIt03				= null ;
	private		ImageView			mHintView				= null ;
	
	private		boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.main ) ;
        
        stopService( new Intent( main.this, BackService.class ) ) ;
        NotificationManager mNM = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        mNM.cancel( R.drawable.icon ) ;
        
        mStatusBarLayout		= (FrameLayout)		findViewById( R.id.StatusBarLayout ) ;
    	mStatusLevelView		= (StatusBarLevel)	findViewById( R.id.StatusLevelView ) ;
    	mStatusEnergyView		= (StatusBarEnergy)	findViewById( R.id.StatusEnergyView ) ;
    	mStatusCoinView			= (StatusBarCoin)	findViewById( R.id.StatusCoinView ) ;
    	mStatusCashView			= (StatusBarCash)	findViewById( R.id.StatusCashView ) ;
    	mCaptionView			= (TextView)		findViewById( R.id.StatusCaptionView ) ;
    	mTimerView				= (TextView)		findViewById( R.id.StatusTimerView ) ;
    	
    	mAvatarView				= (AvatarView)		findViewById( R.id.MainAvatarView ) ;
    	
        mBtnJob					= (ImageButton) 	findViewById( R.id.Button_Job ) ;
        mBtnCloset				= (ImageButton) 	findViewById( R.id.Button_Closet ) ;
        mBtnBank				= (ImageButton) 	findViewById( R.id.Button_Bank ) ;
        mBtnSetting				= (ImageButton) 	findViewById( R.id.Button_Setting ) ;
        mBtnShops				= (ImageButton) 	findViewById( R.id.Button_Shops ) ;
        mBtnLookbook			= (ImageButton) 	findViewById( R.id.Button_LookBook ) ;
        mExclamationView		= (ImageView)		findViewById( R.id.ExclamationView ) ;
        mBtnEnergy				= (ImageButton) 	findViewById( R.id.Button_Energy ) ;
        mBtnVip					= (ImageButton) 	findViewById( R.id.Button_Vip ) ;
        mBtnAward				= (ImageButton)		findViewById( R.id.Button_Award ) ;
        mBtnOpenFeint			= (ImageButton)		findViewById( R.id.Button_OpenFeint ) ;
        mBtnOpenBoutique		= (ImageButton)		findViewById( R.id.Button_MyBotique ) ;
        
        mClickIt01				= (ImageView)		findViewById( R.id.ClickItView01 ) ;
        mClickIt02				= (ImageView)		findViewById( R.id.ClickItView02 ) ;
        mClickIt03				= (ImageView)		findViewById( R.id.ClickItView03 ) ;
        mHintView				= (ImageView)		findViewById( R.id.HintView ) ;
        
        FabLifeApplication.init( getApplicationContext() ) ;
        
        mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
        mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
        mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
        mStatusEnergyView.setVipMember( FabLifeApplication.getProfileManager().isVIPMember() ) ;
        mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
        mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
        mCaptionView.setText( getString( R.string.level01_name + FabLifeApplication.getProfileManager().getLevel() - 1 ) ) ;
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mCaptionView.setTypeface( mFace ) ; mTimerView.setTypeface( mFace ) ;
        
        overridePendingTransition( android.R.anim.fade_in, R.anim.hold ) ;
        
        if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
        {
        	int step = getIntent().getIntExtra( STRING_STEP, STEP_WELCOME ) ;
        	switch( step )
        	{
	        	case STEP_WELCOME :
	        	{
	        		if ( FabLifeApplication.getAppliedManager() != null ) FabLifeApplication.getAppliedManager().initialize() ;
			        Handler handler = new Handler()
			    	{
						@Override
						public void handleMessage( Message msg )
						{
							// TODO Auto-generated method stub
				        	Intent intent = new Intent( getApplicationContext(), Welcome.class ) ;
				        	startActivityForResult( intent, STEP_WELCOME ) ;
						}
			    	} ;
			    	handler.sendEmptyMessageDelayed( 0, 1000 ) ;
			    	break ;
	        	}
	        	case STEP_MYJOB :
	        	{
	        		mClickIt02.setVisibility( View.VISIBLE ) ;
					mBtnShops.setOnClickListener( new OnClickListener()
					{
						@Override
						public void onClick( View view )
						{
							// TODO Auto-generated method stub
							FabLifeApplication.playTapEffect() ;
							action = true ;
							Intent intent = new Intent( getApplicationContext(), StoreList.class ) ;
							startActivity( intent ) ;
							finish() ;
						}
					});
					break ;
	        	}
	        	case STEP_SHOP :
	        	{
	        		mClickIt03.setVisibility( View.VISIBLE ) ;
	    			mBtnCloset.setOnClickListener( new OnClickListener()
	    			{
	    				@Override
	    				public void onClick( View view )
	    				{
	    					// TODO Auto-generated method stub
	    					FabLifeApplication.playTapEffect() ;
	    					Intent intent = new Intent( getApplicationContext(), MyCloset.class ) ;
	    					startActivity( intent ) ;
	    					finish() ;
	    				}
	    			});
	        		break ;
	        	}
	        	case STEP_MYCLOSET :
	        	{
	        		Intent intent = new Intent( getApplicationContext(), Start.class ) ;
	    			startActivityForResult( intent, ACTIVITY_START ) ;
	        		break ;
	        	}
        	}
        }
        else
        {
        	setClickListener() ;
        }
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
			
			if ( FabLifeApplication.getProfileManager().isPastDate() ) mExclamationView.setVisibility( View.VISIBLE ) ;
			else mExclamationView.setVisibility( View.GONE ) ;
			
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

	@Override
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		FabLifeApplication.playTapEffect() ;
		if ( view.getId() == R.id.Button_Job )
		{
			action = true ;
			Intent intent = new Intent( this, MyJob.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_Shops )
		{
			action = true ;
			Intent intent = new Intent( this, StoreList.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_Closet )
		{
			Intent intent = new Intent( this, MyCloset.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_LookBook )
		{
			Intent intent = new Intent( this, LookBook.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_Bank )
		{
			action = true ;
			Intent intent = new Intent( this, Bank.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_Energy )
		{
			action = true ;
			Intent intent = new Intent( this, Cafe.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_Setting )
		{
			Intent intent = new Intent( this, Settings.class ) ;
			startActivityForResult( intent, ACTIVITY_SETTINGS ) ;
		}
		else if ( view.getId() == R.id.Button_Vip )
		{
			action = true ;
			Intent intent = new Intent( this, VIP.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.Button_Award )
		{
			Intent intent = new Intent( this, Award.class ) ;
			startActivity( intent ) ;
		}
		else if ( view.getId() == R.id.Button_OpenFeint )
		{
			Intent intent = new Intent( this, OpenfeintActivity.class ) ;
			startActivityForResult( intent, ACTIVITY_OPENFEINT ) ;
		}
		else if ( view.getId() == R.id.Button_MyBotique )
		{
			if ( FabLifeApplication.getProfileManager().isFirstBotique() )
			{
				Intent intent = new Intent( this, BotiqueWelcome.class ) ;
				startActivity( intent ) ;
				finish() ;
			}
			else
			{
				action = true ;
				Intent intent = new Intent( this, BotiqueMain.class ) ;
				startActivity( intent ) ;
				finish() ;
			}
		}
	}
	
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		// TODO Auto-generated method stub
		switch( requestCode )
		{
			case STEP_WELCOME :
			{
				mClickIt01.setVisibility( View.VISIBLE ) ;
				mBtnJob.setOnClickListener( new OnClickListener()
				{
					@Override
					public void onClick( View view )
					{
						// TODO Auto-generated method stub
						FabLifeApplication.playTapEffect() ;
						action = true ;
						Intent intent = new Intent( getApplicationContext(), MyJob.class ) ;
						startActivity( intent ) ;
						finish() ;
					}
				});
				break ;
			}
			case ACTIVITY_START :
			{
				mHintView.setVisibility( View.VISIBLE ) ;
				FabLifeApplication.getProfileManager().setTutorialFinished() ;
				mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
				mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
				mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
				mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
				mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
				mStatusBarLayout.invalidate() ;
				
				FabLifeApplication.getHairManager().initialize() ;
				BackgroundManager mgr = new BackgroundManager( getApplicationContext() ) ;
				mgr.initialize() ;
				
				Handler handler = new Handler()
				{
					@Override
					public void handleMessage( Message msg )
					{
						// TODO Auto-generated method stub
						mHintView.setVisibility( View.GONE ) ;
						setClickListener() ;
					}	
				} ;
				handler.sendEmptyMessageDelayed( 0, 3000 ) ;
			}
			case ACTIVITY_SETTINGS :
			{
				if ( FabLifeApplication.getProfileManager() != null )
					FabLifeApplication.getProfileManager().writeToFile() ;
				break ;
			}
			case ACTIVITY_OPENFEINT :
			{
				break ;
			}
		}
	}

	public void setClickListener()
	{
		mBtnJob.setOnClickListener( this ) ;
        mBtnCloset.setOnClickListener( this ) ;
        mBtnBank.setOnClickListener( this ) ;
        mBtnSetting.setOnClickListener( this ) ;
        mBtnShops.setOnClickListener( this ) ;
        mBtnLookbook.setOnClickListener( this ) ;
        mBtnEnergy.setOnClickListener( this ) ;
        mBtnVip.setOnClickListener( this ) ;
        mBtnAward.setOnClickListener( this ) ;
        mBtnOpenFeint.setOnClickListener( this ) ;
        mBtnOpenBoutique.setOnClickListener( this ) ;
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		energy_limit = FabLifeApplication.getProfileManager().isVIPMember() ? 55: 45 ;
		if ( FabLifeApplication.getProfileManager().getEnergy() < energy_limit )
		{
			if ( FabLifeApplication.getProfileManager() != null )
			{
				FabLifeApplication.getProfileManager().setFinishTime( System.currentTimeMillis() ) ;
				FabLifeApplication.getProfileManager().setCurrentTimerTime( FabLifeApplication.mTimerTime ) ;
			}
			
			int time = ( energy_limit - FabLifeApplication.getProfileManager().getEnergy() - 1 ) * 180 * 1000 + FabLifeApplication.mTimerTime * 1000 ;
			Intent serviceIntent = new Intent( getApplicationContext(), BackService.class ) ;
			serviceIntent.putExtra( "time", time ) ;
			startService( serviceIntent ) ;
		}
		else
		{
			FabLifeApplication.getProfileManager().setFinishTime( 0 ) ;
		}
		
		if ( FabLifeApplication.getProfileManager() != null )
		{
			FabLifeApplication.getProfileManager().writeToFile() ;
		}
		
		if ( FabLifeApplication.getBuiedManager() != null )
		{
			FabLifeApplication.getBuiedManager().writeToFile() ;
		}
		
		if ( FabLifeApplication.getAppliedManager() != null )
		{
			FabLifeApplication.getAppliedManager().writeToFile() ;
		}
		
		mAvatarView.clear() ;
		
		isRunning = false ;
		
		FabLifeApplication.clear() ;
        finish() ;
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		FabLifeApplication.stopMainMusic() ;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		FabLifeApplication.playMainMusic() ;
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart() ;
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
}