package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.JobManager;
import com.miinu.FabLife.ViewComponent.CustomProgress;
import com.miinu.FabLife.ViewComponent.StatusBarCash;
import com.miinu.FabLife.ViewComponent.StatusBarCoin;
import com.miinu.FabLife.ViewComponent.StatusBarEnergy;
import com.miinu.FabLife.ViewComponent.StatusBarLevel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyJob extends Activity implements OnClickListener
{
	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private 	TextView 			jobName01 				= null ;
	private 	TextView 			energy01 				= null ;
	private 	TextView 			coin01 					= null ;
	private 	CustomProgress 		progress01 				= null ;
	private	 	ImageButton 		dojob01 				= null ;
	
	private 	TextView 			jobName02 				= null ;
	private		TextView 			energy02 				= null ;
	private 	TextView 			coin02 					= null ;
	private 	CustomProgress 		progress02 				= null ;
	private 	ImageButton 		dojob02 				= null ;
	
	private 	TextView 			jobName03 				= null ;
	private 	TextView 			energy03 				= null ;
	private 	TextView 			coin03 					= null ;
	private 	CustomProgress 		progress03 				= null ;
	private 	ImageButton 		dojob03 				= null ;
	
	private 	TextView 			jobName04 				= null ;
	private 	TextView 			energy04 				= null ;
	private 	TextView 			coin04 					= null ;
	private 	CustomProgress 		progress04 				= null ;
	private 	ImageButton 		dojob04 				= null ;
	
	private 	ImageButton			level1_btn				= null ;
	private 	ImageButton			level5_btn				= null ;
	private 	ImageButton			level9_btn				= null ;
	private 	ImageButton			level13_btn				= null ;
	private 	ImageButton			level17_btn				= null ;
	private 	ImageButton			level26_btn				= null ;
	
	private 	ImageButton			gohome_btn				= null ;
	
	private 	ImageView			mView					= null ;
	
	private 	int					page 					= 1 ;
	private 	int					level					= 1 ;
	private 	Handler				mHandler				= null ;
	
	private 	JobManager			mJobManager				= null ;
	
	private 	MediaPlayer			mJobPlayer				= null ;
	
	private 	boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.myjob ) ;
        
        mStatusBarLayout		= (FrameLayout)		findViewById( R.id.StatusBarLayout ) ;
    	mStatusLevelView		= (StatusBarLevel)	findViewById( R.id.StatusLevelView ) ;
    	mStatusEnergyView		= (StatusBarEnergy)	findViewById( R.id.StatusEnergyView ) ;
    	mStatusCoinView			= (StatusBarCoin)	findViewById( R.id.StatusCoinView ) ;
    	mStatusCashView			= (StatusBarCash)	findViewById( R.id.StatusCashView ) ;
    	mCaptionView			= (TextView)		findViewById( R.id.StatusCaptionView ) ;
    	mTimerView				= (TextView)		findViewById( R.id.StatusTimerView ) ;
    	
        jobName01 				= (TextView)		findViewById( R.id.JobName01 ) ;
        energy01 				= (TextView)		findViewById( R.id.Energy01 ) ;
        coin01					= (TextView)		findViewById( R.id.Coin01 ) ;
        progress01				= (CustomProgress)	findViewById( R.id.Progress01 ) ;
        dojob01					= (ImageButton)		findViewById( R.id.DoJobButton01 ) ;
        
        jobName02 				= (TextView)		findViewById( R.id.JobName02 ) ;
        energy02 				= (TextView)		findViewById( R.id.Energy02 ) ;
        coin02					= (TextView)		findViewById( R.id.Coin02 ) ;
        progress02				= (CustomProgress)	findViewById( R.id.Progress02 ) ;
        dojob02					= (ImageButton)		findViewById( R.id.DoJobButton02 ) ;
        
        jobName03 				= (TextView)		findViewById( R.id.JobName03 ) ;
        energy03 				= (TextView)		findViewById( R.id.Energy03 ) ;
        coin03					= (TextView)		findViewById( R.id.Coin03 ) ;
        progress03				= (CustomProgress)	findViewById( R.id.Progress03 ) ;
        dojob03					= (ImageButton)		findViewById( R.id.DoJobButton03 ) ;
        
        jobName04 				= (TextView)		findViewById( R.id.JobName04 ) ;
        energy04 				= (TextView)		findViewById( R.id.Energy04 ) ;
        coin04					= (TextView)		findViewById( R.id.Coin04 ) ;
        progress04				= (CustomProgress)	findViewById( R.id.Progress04 ) ;
        dojob04					= (ImageButton)		findViewById( R.id.DoJobButton04 ) ;
        
        level1_btn				= (ImageButton)		findViewById( R.id.Level1Button ) ;
        level5_btn				= (ImageButton)		findViewById( R.id.Level5Button ) ;
        level9_btn				= (ImageButton)		findViewById( R.id.Level9Button ) ;
        level13_btn				= (ImageButton)		findViewById( R.id.Level13Button ) ;
        level17_btn				= (ImageButton)		findViewById( R.id.Level17Button ) ;
        level26_btn				= (ImageButton)		findViewById( R.id.Level26Button ) ;
        
        gohome_btn				= (ImageButton)		findViewById( R.id.MyJobGoHomeButton ) ;
        
        mView					= (ImageView)		findViewById( R.id.ImageView01 ) ;
        
        mJobManager 			= new JobManager( getApplicationContext() ) ;
        
        mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
        mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
        mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
        mStatusEnergyView.setVipMember( FabLifeApplication.getProfileManager().isVIPMember() ) ;
        mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
        mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
        mCaptionView.setText( getString( R.string.level01_name + FabLifeApplication.getProfileManager().getLevel() - 1 ) ) ;
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mCaptionView.setTypeface( mFace ) ; mTimerView.setTypeface( mFace ) ;
        
        if ( FabLifeApplication.getProfileManager().getEffect() )
        {
        	mJobPlayer = MediaPlayer.create( this, R.raw.dojob ) ;
        }
        
        level = FabLifeApplication.getProfileManager().getLevel() ;
        level1_btn.setBackgroundResource( R.drawable.job1 ) ;
        if ( level >= 5 )
        {
        	level5_btn.setBackgroundResource( R.drawable.job2 ) ;
        }
        else
        {
        	level5_btn.setBackgroundResource( R.drawable.job2_lock ) ;
        }
        
        if ( level >= 9 )
        {
        	level9_btn.setBackgroundResource( R.drawable.job3 ) ;
        }
        else
        {
        	level9_btn.setBackgroundResource( R.drawable.job3_lock ) ;
        }
        
        if ( level >= 13 )
        {
        	level13_btn.setBackgroundResource( R.drawable.job4 ) ;
        }
        else
        {
        	level13_btn.setBackgroundResource( R.drawable.job4_lock ) ;
        }
        
        if ( level >= 17 )
        {
        	level17_btn.setBackgroundResource( R.drawable.job5 ) ;
        }
        else
        {
        	level17_btn.setBackgroundResource( R.drawable.job5_lock ) ;
        }
        
        if ( level >= 26 )
        {
        	level26_btn.setBackgroundResource( R.drawable.job6 ) ;
        }
        else
        {
        	level26_btn.setBackgroundResource( R.drawable.job6_lock ) ;
        }
        
        jobName01.setTypeface( mFace ) ;
        jobName02.setTypeface( mFace ) ;
        jobName03.setTypeface( mFace ) ;
        jobName04.setTypeface( mFace ) ;
        
        mHandler = new Handler()
        {
			@Override
			public void handleMessage( Message msg )
			{
				// TODO Auto-generated method stub
				showRewardedCashDialog( msg.what ) ;
			}
        	
        } ;
        progress01.setHandler( mHandler ) ;
        progress02.setHandler( mHandler ) ;
        progress03.setHandler( mHandler ) ;
        progress04.setHandler( mHandler ) ;
        
        initPage( page ) ;
        
        if ( FabLifeApplication.getProfileManager().getTutorialFinished() )
        {
	        level1_btn.setOnClickListener( this ) ;
	        level5_btn.setOnClickListener( this ) ;
	        level9_btn.setOnClickListener( this ) ;
	        level13_btn.setOnClickListener( this ) ;
	        level17_btn.setOnClickListener( this ) ;
	        level26_btn.setOnClickListener( this ) ;
	        
	        dojob01.setOnClickListener( this ) ;
	        dojob02.setOnClickListener( this ) ;
	        dojob03.setOnClickListener( this ) ;
	        dojob04.setOnClickListener( this ) ;
	        
	        gohome_btn.setOnClickListener( this ) ;
        }
        else
        {
        	dojob01.setOnClickListener( this ) ;
        	mView.setVisibility( View.VISIBLE ) ;
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
	
	void initPage( int page_no )
	{
		switch( page_no )
		{
			case 1 :
			{
				jobName01.setText( getText( R.string.Job01 ) ) ;
		        jobName02.setText( getText( R.string.Job02 ) ) ;
		        jobName03.setText( getText( R.string.Job03 ) ) ;
		        jobName04.setText( getText( R.string.Job04 ) ) ;
		        
		        energy01.setText( "1" ) ;
		        coin01.setText( "3" ) ;
		        energy02.setText( "2" ) ;
		        coin02.setText( "8" ) ;
		        energy03.setText( "3" ) ;
		        coin03.setText( "13" ) ;
		        energy04.setText( "4" ) ;
		        coin04.setText( "18" ) ;
		        
		        progress01.setPercent( mJobManager.getJobPercent( 1 ) ) ;
		        progress02.setPercent( mJobManager.getJobPercent( 2 ) ) ;
		        progress03.setPercent( mJobManager.getJobPercent( 3 ) ) ;
		        progress04.setPercent( mJobManager.getJobPercent( 4 ) ) ;
		        
		        progress01.setStep( 10 ) ;
		        progress02.setStep( 8 ) ;
		        progress03.setStep( 4 ) ;
		        progress04.setStep( 2 ) ;
		        break ;
			}
			case 2 :
			{
				jobName01.setText( getText( R.string.Job05 ) ) ;
		        jobName02.setText( getText( R.string.Job06 ) ) ;
		        jobName03.setText( getText( R.string.Job07 ) ) ;
		        jobName04.setText( getText( R.string.Job08 ) ) ;
		        
		        energy01.setText( "1" ) ;
		        coin01.setText( "5" ) ;
		        energy02.setText( "2" ) ;
		        coin02.setText( "12" ) ;
		        energy03.setText( "3" ) ;
		        coin03.setText( "21" ) ;
		        energy04.setText( "4" ) ;
		        coin04.setText( "29" ) ;
		        
		        progress01.setPercent( mJobManager.getJobPercent( 5 ) ) ;
		        progress02.setPercent( mJobManager.getJobPercent( 6 ) ) ;
		        progress03.setPercent( mJobManager.getJobPercent( 7 ) ) ;
		        progress04.setPercent( mJobManager.getJobPercent( 8 ) ) ;
		        
		        progress01.setStep( 10 ) ;
		        progress02.setStep( 8 ) ;
		        progress03.setStep( 4 ) ;
		        progress04.setStep( 2 ) ;
		        break ;
			}
			case 3 :
			{
				jobName01.setText( getText( R.string.Job09 ) ) ;
		        jobName02.setText( getText( R.string.Job10 ) ) ;
		        jobName03.setText( getText( R.string.Job11 ) ) ;
		        jobName04.setText( getText( R.string.Job12 ) ) ;
		        
		        energy01.setText( "1" ) ;
		        coin01.setText( "7" ) ;
		        energy02.setText( "2" ) ;
		        coin02.setText( "16" ) ;
		        energy03.setText( "3" ) ;
		        coin03.setText( "25" ) ;
		        energy04.setText( "4" ) ;
		        coin04.setText( "35" ) ;
		        
		        progress01.setPercent( mJobManager.getJobPercent( 9 ) ) ;
		        progress02.setPercent( mJobManager.getJobPercent( 10 ) ) ;
		        progress03.setPercent( mJobManager.getJobPercent( 11 ) ) ;
		        progress04.setPercent( mJobManager.getJobPercent( 12 ) ) ;
		        
		        progress01.setStep( 10 ) ;
		        progress02.setStep( 8 ) ;
		        progress03.setStep( 4 ) ;
		        progress04.setStep( 2 ) ;
		        break ;
			}
			case 4 :
			{
				jobName01.setText( getText( R.string.Job13 ) ) ;
		        jobName02.setText( getText( R.string.Job14 ) ) ;
		        jobName03.setText( getText( R.string.Job15 ) ) ;
		        jobName04.setText( getText( R.string.Job16 ) ) ;
		        
		        energy01.setText( "1" ) ;
		        coin01.setText( "9" ) ;
		        energy02.setText( "2" ) ;
		        coin02.setText( "19" ) ;
		        energy03.setText( "3" ) ;
		        coin03.setText( "29" ) ;
		        energy04.setText( "4" ) ;
		        coin04.setText( "39" ) ;
		        
		        progress01.setPercent( mJobManager.getJobPercent( 13 ) ) ;
		        progress02.setPercent( mJobManager.getJobPercent( 14 ) ) ;
		        progress03.setPercent( mJobManager.getJobPercent( 15 ) ) ;
		        progress04.setPercent( mJobManager.getJobPercent( 16 ) ) ;
		        
		        progress01.setStep( 10 ) ;
		        progress02.setStep( 8 ) ;
		        progress03.setStep( 4 ) ;
		        progress04.setStep( 2 ) ;
		        break ;
			}
			case 5 :
			{
				jobName01.setText( getText( R.string.Job17 ) ) ;
		        jobName02.setText( getText( R.string.Job18 ) ) ;
		        jobName03.setText( getText( R.string.Job19 ) ) ;
		        jobName04.setText( getText( R.string.Job20 ) ) ;
		        
		        energy01.setText( "1" ) ;
		        coin01.setText( "11" ) ;
		        energy02.setText( "2" ) ;
		        coin02.setText( "23" ) ;
		        energy03.setText( "3" ) ;
		        coin03.setText( "36" ) ;
		        energy04.setText( "4" ) ;
		        coin04.setText( "49" ) ;
		        
		        progress01.setPercent( mJobManager.getJobPercent( 17 ) ) ;
		        progress02.setPercent( mJobManager.getJobPercent( 18 ) ) ;
		        progress03.setPercent( mJobManager.getJobPercent( 19 ) ) ;
		        progress04.setPercent( mJobManager.getJobPercent( 20 ) ) ;
		        
		        progress01.setStep( 10 ) ;
		        progress02.setStep( 8 ) ;
		        progress03.setStep( 4 ) ;
		        progress04.setStep( 2 ) ;
		        break ;
			}
			case 6 :
			{
				jobName01.setText( getText( R.string.Job21 ) ) ;
		        jobName02.setText( getText( R.string.Job22 ) ) ;
		        jobName03.setText( getText( R.string.Job23 ) ) ;
		        jobName04.setText( getText( R.string.Job24 ) ) ;
		        
		        energy01.setText( "1" ) ;
		        coin01.setText( "23" ) ;
		        energy02.setText( "2" ) ;
		        coin02.setText( "50" ) ;
		        energy03.setText( "3" ) ;
		        coin03.setText( "77" ) ;
		        energy04.setText( "4" ) ;
		        coin04.setText( "105" ) ;
		        
		        progress01.setPercent( mJobManager.getJobPercent( 21 ) ) ;
		        progress02.setPercent( mJobManager.getJobPercent( 22 ) ) ;
		        progress03.setPercent( mJobManager.getJobPercent( 23 ) ) ;
		        progress04.setPercent( mJobManager.getJobPercent( 24 ) ) ;
		        
		        progress01.setStep( 10 ) ;
		        progress02.setStep( 8 ) ;
		        progress03.setStep( 4 ) ;
		        progress04.setStep( 2 ) ;
		        break ;
			}
		}		
	}

	int count = 0 ;
	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.Level1Button )
		{
			FabLifeApplication.playTapEffect() ;
			if ( page == 1 )
				return ;
			else
			{
				page = 1 ;
				initPage( page ) ;
			}
		}
		else if ( v.getId() == R.id.Level5Button )
		{
			FabLifeApplication.playTapEffect() ;
			if ( page == 2 )
				return ;
			else
			{
				if ( level >= 5 )
				{
					page = 2 ;
					initPage( page ) ;
				}
				else
				{
					showNoAccessDialog() ;
				}
			}
		}
		else if ( v.getId() == R.id.Level9Button )
		{
			FabLifeApplication.playTapEffect() ;
			if ( page == 3 )
				return ;
			else
			{
				if ( level >= 9 )
				{
					page = 3 ;
					initPage( page ) ;
				}
				else
				{
					showNoAccessDialog() ;
				}
			}
		}
		else if ( v.getId() == R.id.Level13Button )
		{
			FabLifeApplication.playTapEffect() ;
			if ( page == 4 )
				return ;
			else
			{
				if ( level >= 13 )
				{
					page = 4 ;
					initPage( page ) ;
				}
				else
				{
					showNoAccessDialog() ;
				}
			}
		}
		else if ( v.getId() == R.id.Level17Button )
		{
			FabLifeApplication.playTapEffect() ;
			if ( page == 5 )
				return ;
			else
			{
				if ( level >= 17 )
				{
					page = 5 ;
					initPage( page ) ;
				}
				else
				{
					showNoAccessDialog() ;
				}
			}
		}
		else if ( v.getId() == R.id.Level26Button )
		{
			FabLifeApplication.playTapEffect() ;
			if ( page == 6 )
				return ;
			else
			{
				if ( level >= 26 )
				{
					page = 6 ;
					initPage( page ) ;
				}
				else
				{
					showNoAccessDialog() ;
				}
			}
		}
		else if ( v.getId() == R.id.MyJobGoHomeButton )
		{
			FabLifeApplication.playHomeEffect() ;
			if ( mJobPlayer != null )
			{
				if ( mJobPlayer.isPlaying() ) mJobPlayer.stop() ;
				mJobPlayer.release() ;
				mJobPlayer = null ;
			}
			action = true ;
			Intent intent = new Intent( MyJob.this, main.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( v.getId() == R.id.DoJobButton01 )
		{
			if ( FabLifeApplication.getProfileManager().getEffect() )
	        {
				if ( mJobPlayer != null )
					mJobPlayer.start() ;
	        }
			
			if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
			{				
				if ( count < 2 )
				{
					FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
					FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 3 ) ;
					
					mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
					mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
					mStatusBarLayout.invalidate() ;
					progress01.advance() ;
				}
				count++ ;
				if ( count == 1 )
				{
					mView.setBackgroundResource( R.drawable.job_hint1 ) ;
				}
				else if ( count == 2 )
				{
					mView.setBackgroundResource( R.drawable.job_hint2 ) ;
					mView.setOnClickListener( new OnClickListener()
					{
						@Override
						public void onClick( View v )
						{
							// TODO Auto-generated method stub
							action = true ;
							FabLifeApplication.playTapEffect() ;
							Intent intent = new Intent( MyJob.this, main.class ) ;
							intent.putExtra( main.STRING_STEP, main.STEP_MYJOB ) ;
							startActivity( intent ) ;
							finish() ;
						}
					}) ;
				}
				return ;
			}
			
			switch ( page )
			{
				case 1 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  1 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 3 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress01.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 1, progress01.getPercent() ) ;
					}
					break ;
				}
				case 2 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  1 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 5 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress01.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 1, progress01.getPercent() ) ;
					}
					break ;
				}
				case 3 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  1 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 7 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress01.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 1, progress01.getPercent() ) ;
					}
					break ;
				}
				case 4 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  1 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 9 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress01.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 1, progress01.getPercent() ) ;
					}
					break ;
				}
				case 5 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  1 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 11 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress01.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 1, progress01.getPercent() ) ;
					}
					break ;
				}
				case 6 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  1 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 1 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 23 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress01.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 1, progress01.getPercent() ) ;
					}
					break ;
				}
			}
		}
		else if ( v.getId() == R.id.DoJobButton02 )
		{
			if ( FabLifeApplication.getProfileManager().getEffect() )
	        {
				if ( mJobPlayer != null )
					mJobPlayer.start() ;
	        }
			
			switch ( page )
			{
				case 1 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  2 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 2 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 8 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress02.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 2, progress02.getPercent() ) ;
					}
					break ;
				}
				case 2 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  2 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 2 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 12 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress02.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 2, progress02.getPercent() ) ;
					}
					break ;
				}
				case 3 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  2 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 2 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 16 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress02.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 2, progress02.getPercent() ) ;
					}
					break ;
				}
				case 4 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  2 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 2 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 19 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress02.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 2, progress02.getPercent() ) ;
					}
					break ;
				}
				case 5 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  2 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 2 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 23 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress02.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 2, progress02.getPercent() ) ;
					}
					break ;
				}
				case 6 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  2 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 2 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 50 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress02.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 2, progress02.getPercent() ) ;
					}
					break ;
				}
			}
		}
		else if ( v.getId() == R.id.DoJobButton03 )
		{
			if ( FabLifeApplication.getProfileManager().getEffect() )
	        {
				if ( mJobPlayer != null )
					mJobPlayer.start() ;
	        }
			
			switch ( page )
			{
				case 1 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  3 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 3 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 13 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress03.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 3, progress03.getPercent() ) ;
					}
					break ;
				}
				case 2 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  3 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 3 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 21 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress03.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 3, progress03.getPercent() ) ;
					}
					break ;
				}
				case 3 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  3 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 3 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 25 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress03.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 3, progress03.getPercent() ) ;
					}
					break ;
				}
				case 4 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  3 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 3 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 29 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress03.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 3, progress03.getPercent() ) ;
					}
					break ;
				}
				case 5 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  3 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 3 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 36 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress03.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 3, progress03.getPercent() ) ;
					}
					break ;
				}
				case 6 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  3 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 3 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 77 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress03.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 3, progress03.getPercent() ) ;
					}
					break ;
				}
			}
		}
		else if ( v.getId() == R.id.DoJobButton04 )
		{
			if ( FabLifeApplication.getProfileManager().getEffect() )
	        {
				if ( mJobPlayer != null )
					mJobPlayer.start() ;
	        }
			
			switch ( page )
			{
				case 1 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  4 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 4 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 18 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress04.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 4, progress04.getPercent() ) ;
					}
					break ;
				}
				case 2 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  4 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 4 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 29 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress04.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 4, progress04.getPercent() ) ;
					}
					break ;
				}
				case 3 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  4 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 4 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 35 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress04.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 4, progress04.getPercent() ) ;
					}
					break ;
				}
				case 4 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  4 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 4 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 39 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress04.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 4, progress04.getPercent() ) ;
					}
					break ;
				}
				case 5 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  4 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 4 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 49 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress04.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 4, progress04.getPercent() ) ;
					}
					break ;
				}
				case 6 :
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() <  4 )
					{
						showNoEnergyDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 4 ) ;
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 105 ) ;
						
						mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
						
						progress04.advance() ;
						mJobManager.setJobPercent( ( page - 1 ) * 4 + 4, progress04.getPercent() ) ;
					}
					break ;
				}
			}
		}
	}
	
	private void showNoAccessDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.level_no_access ) )
        .setCancelable( false )
        .setPositiveButton( "OK", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
                }
        	}
        ) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	void goCafe()
	{
		if ( mJobPlayer != null )
		{
			if ( mJobPlayer.isPlaying() ) mJobPlayer.stop() ;
			mJobPlayer.release() ;
			mJobPlayer = null ;
		}
		action = true ;
		Intent intent = new Intent( getApplicationContext(), Cafe.class ) ;
		startActivity( intent ) ;
		finish() ;
	}
	
	private void showNoEnergyDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.energy_not_enough ) )
        .setCancelable( false )
        .setPositiveButton( "YES", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			goCafe() ;
                }
        	}
        )
        .setNegativeButton( "NO", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
                }
        	}
        ) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void showRewardedCashDialog( int count )
	{
		FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + count * 2 ) ;
		mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
		mStatusBarLayout.invalidate() ;
		
		String message = "You have been rewarded with " + ( count * 2 ) + " cash for completing the job." ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
        .setCancelable( false )
        .setPositiveButton( "OK", new DialogInterface.OnClickListener() 
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
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
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
		if ( mJobManager != null )
		{
			mJobManager.writeToFile() ;
		}
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
	}
}
