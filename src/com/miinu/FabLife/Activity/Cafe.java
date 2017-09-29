package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.ViewComponent.StatusBarCash;
import com.miinu.FabLife.ViewComponent.StatusBarCoin;
import com.miinu.FabLife.ViewComponent.StatusBarEnergy;
import com.miinu.FabLife.ViewComponent.StatusBarLevel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class Cafe extends Activity implements OnClickListener
{
	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private		ImageButton 		strawberry_btn 			= null ;
	private		ImageButton 		espresso_btn 			= null ;
	private		ImageButton 		milkshake_btn 			= null ;
	private		ImageButton 		hamburger_btn 			= null ;
	private		ImageButton 		sushi_btn 				= null ;
	private		ImageButton 		cheesecake_btn 			= null ;
	private		ImageButton 		chocolate_btn 			= null ;
	private		ImageButton 		macaron_btn 			= null ;
	private		ImageButton 		gohome_btn				= null ;
	
	private		int					select_cash				= 0 ;
	
	private		boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.cafe ) ;
        
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
        
        strawberry_btn 	= (ImageButton)	findViewById( R.id.StrawberryButton ) ;
        espresso_btn 	= (ImageButton)	findViewById( R.id.EspressoButton ) ;
        milkshake_btn 	= (ImageButton)	findViewById( R.id.MilkshakeButton ) ;
        hamburger_btn 	= (ImageButton)	findViewById( R.id.HamburgerButton ) ;
        sushi_btn 		= (ImageButton)	findViewById( R.id.SushiButton ) ;
        cheesecake_btn 	= (ImageButton)	findViewById( R.id.CheesecakeButton ) ;
        chocolate_btn 	= (ImageButton)	findViewById( R.id.ChocolateButton ) ;
        macaron_btn 	= (ImageButton)	findViewById( R.id.MacaronButton ) ;
        gohome_btn		= (ImageButton)	findViewById( R.id.CafeGoHomeButton ) ;
        
        strawberry_btn.setOnClickListener( this ) ;
        espresso_btn.setOnClickListener( this ) ;
        milkshake_btn.setOnClickListener( this ) ;
        hamburger_btn.setOnClickListener( this ) ;
        sushi_btn.setOnClickListener( this ) ;
        cheesecake_btn.setOnClickListener( this ) ;
        chocolate_btn.setOnClickListener( this ) ;
        macaron_btn.setOnClickListener( this ) ;
        gohome_btn.setOnClickListener( this ) ;
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

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		select_cash = 0 ;
		switch ( v.getId() )
		{
			case R.id.StrawberryButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 3 ;
				break ;
			case R.id.EspressoButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 8 ;
				break ;
			case R.id.MilkshakeButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 15 ;
				break ;
			case R.id.HamburgerButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 20 ;
				break ;
			case R.id.SushiButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 25 ;
				break ;
			case R.id.CheesecakeButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 35 ;
				break ;
			case R.id.ChocolateButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 40 ;
				break ;
			case R.id.MacaronButton :
				FabLifeApplication.playTapEffect() ;
				select_cash = 45 ;
				break ;
			case R.id.CafeGoHomeButton :
				FabLifeApplication.playHomeEffect() ;
				action = true ;
				Intent intent = new Intent( Cafe.this, main.class ) ;
				startActivity( intent ) ;
				finish() ;
				break ;
		}
		if ( select_cash > 0 )
		{
			if ( FabLifeApplication.getProfileManager().getCash() < select_cash )
			{
				showNotEnoughDialog() ;	
			}
			else
			{
				showBuyDialog( select_cash ) ;
			}
		}
	}
	
	private void showBuyDialog( int cash )
	{
		String message = "Are you sure you want to buy " + cash + " energy for " + cash + " cash?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() - select_cash ) ;
        			FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() + select_cash ) ;
        	    	
        			mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
        			mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
        			mStatusBarLayout.invalidate() ;
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

	private void showNotEnoughDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.cash_not_enough ) )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			gotoBank() ;
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
	
	void gotoBank()
	{
		action = true ;
		Intent intent = new Intent( Cafe.this, Bank.class ) ;
		startActivity( intent ) ;
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
	}

	@Override
	public void onBackPressed() 
	{
	}
}
