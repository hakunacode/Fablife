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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.PorterDuff ;

public class StoreList extends Activity implements SurfaceHolder.Callback, OnClickListener, OnTouchListener
{
	private 	SurfaceView 		m_Surface 		= null ;
	private 	SurfaceHolder 		mHolder 		= null ;
	
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private 	ImageButton 		mGoHomeBtn		= null ;
	private 	ImageButton 		mDot01			= null ;
	private		ImageButton 		mDot02			= null ;
	private		ImageButton 		mDot03			= null ;
	private		ImageButton 		mDot04			= null ;
	
	private 	ImageView			mImageView01	= null ;
	
	private		Bitmap 			shop01			= null ;
	private		Bitmap 			shop02 			= null ;
	private		Bitmap 			shop03 			= null ;
	private		Bitmap 			shop04 			= null ;
	private		Bitmap 			shop05 			= null ;
	private		Bitmap 			shop06 			= null ;
	private		Bitmap 			shop07 			= null ;
	private		Bitmap 			shop08 			= null ;
	private		Bitmap 			shop09 			= null ;
	private		Bitmap			shop10			= null ;
	private		Bitmap			shop11			= null ;
	private		Bitmap			shop12			= null ;
	private		Bitmap			shop13			= null ;
	private		Bitmap			shop14			= null ;
	private		Bitmap 			shop_btn 		= null ;
	private		Bitmap 			lock 			= null ;
	
	private		Paint 			paint	 		= null ;
	private		Paint			t_paint			= null ;
	
	private		int 			text_size		= 14 ;
	
	private		final int 		step			= 20 ;
	private		int 			icon_size 		= 93 ;
	
	private		int 			width 			= 320 ;
	private 	float			scale			= 1.0f ;
	
	private		int 			current_page	= 1 ;
	private		float			page01_pos		= 0.0f ;
	private		float			page02_pos		= width ;
	private		float			page03_pos		= 2 * width ;
	private		float			page04_pos		= 3 * width ;
	
	private		BitmapFactory.Options options = new BitmapFactory.Options() ;
	
	private 	boolean			isAniming		= false ;
	private		boolean			isRunning		= false ;
	private		boolean			action					= false ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.store_list ) ;
        
        m_Surface 	= (SurfaceView)		findViewById( R.id.StoreSelectSurface ) ;
        mHolder = m_Surface.getHolder() ; mHolder.addCallback( this ) ; mHolder.setType( SurfaceHolder.SURFACE_TYPE_NORMAL ) ;
        
    	mStatusLevelView		= (StatusBarLevel)	findViewById( R.id.StatusLevelView ) ;
    	mStatusEnergyView		= (StatusBarEnergy)	findViewById( R.id.StatusEnergyView ) ;
    	mStatusCoinView			= (StatusBarCoin)	findViewById( R.id.StatusCoinView ) ;
    	mStatusCashView			= (StatusBarCash)	findViewById( R.id.StatusCashView ) ;
    	mCaptionView			= (TextView)		findViewById( R.id.StatusCaptionView ) ;
    	mTimerView				= (TextView)		findViewById( R.id.StatusTimerView ) ;
        
        mGoHomeBtn	= (ImageButton)		findViewById( R.id.StoreListGoHomeButton ) ;
        mDot01		= (ImageButton)		findViewById( R.id.StorePageDot01 ) ;
        mDot02		= (ImageButton)		findViewById( R.id.StorePageDot02 ) ;
        mDot03		= (ImageButton)		findViewById( R.id.StorePageDot03 ) ;
        mDot04		= (ImageButton)		findViewById( R.id.StorePageDot04 ) ;
        
        mImageView01 = (ImageView)		findViewById( R.id.ImageView01 ) ;
        
        paint = new Paint() ; paint.setFilterBitmap( true ) ; paint.setAntiAlias( true ) ;
		Typeface face = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
		t_paint = new Paint() ; t_paint.setTypeface( face ) ; t_paint.setTextAlign( Align.CENTER ) ;
		t_paint.setFilterBitmap( true ) ; t_paint.setAntiAlias( true ) ;
		t_paint.setColor( getResources().getColor( R.color.level_color ) ) ;
		
		options.inPurgeable = true ;
		
        mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
        mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
        mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
        mStatusEnergyView.setVipMember( FabLifeApplication.getProfileManager().isVIPMember() ) ;
        mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
        mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
        mCaptionView.setText( getString( R.string.level01_name + FabLifeApplication.getProfileManager().getLevel() - 1 ) ) ;
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mCaptionView.setTypeface( mFace ) ; mTimerView.setTypeface( mFace ) ;
        
        if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
        {
        	mImageView01.setVisibility( View.VISIBLE ) ;
        	m_Surface.setOnTouchListener( this ) ;
        }
        else
        {
	        m_Surface.setOnTouchListener( this ) ;
	        mGoHomeBtn.setOnClickListener( this ) ;
	        mDot01.setOnClickListener( this ) ;
	        mDot02.setOnClickListener( this ) ;
	        mDot03.setOnClickListener( this ) ;
	        mDot04.setOnClickListener( this ) ;
        }
        overridePendingTransition( R.anim.activity_slide_right, R.anim.hold ) ;
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
	public void surfaceCreated( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
		draw() ;
	}

	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height )
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void surfaceDestroyed( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
	}
	
	private void drawPage01( Canvas c )
	{
		float shop01_pos = 20.0f * scale ;
		float shop02_pos = 110.0f * scale ;
		float shop03_pos = 200.0f * scale ;
		float shop04_pos = 290.0f * scale ;
		c.drawBitmap( shop01, page01_pos, shop01_pos, paint ) ;
		c.drawBitmap( shop02, page01_pos, shop02_pos, paint ) ;
		c.drawBitmap( shop03, page01_pos, shop03_pos, paint ) ;
		c.drawBitmap( shop04, page01_pos, shop04_pos, paint ) ;
		c.drawBitmap( shop_btn, page01_pos + 260.0f * scale, shop01_pos + 33.4f * scale, paint ) ;
		c.drawBitmap( shop_btn, page01_pos + 260.0f * scale, shop02_pos + 33.4f * scale, paint ) ;
		c.drawBitmap( shop_btn, page01_pos + 260.0f * scale, shop03_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 3 )
		{
			c.drawBitmap( lock, page01_pos + 273.4f * scale, shop04_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop04_level ), page01_pos + 286.7f * scale, shop04_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
		{
			c.drawBitmap( shop_btn, page01_pos + 260.0f * scale , shop04_pos + 33.4f * scale, paint ) ;
		}
	}
	
	private void drawPage02( Canvas c )
	{
		float shop05_pos = 20.0f * scale ;
		float shop06_pos = 110.0f * scale ;
		float shop07_pos = 200.0f * scale ;
		float shop08_pos = 290.0f * scale ;
		c.drawBitmap( shop05, page02_pos, shop05_pos, paint ) ;
		c.drawBitmap( shop06, page02_pos, shop06_pos, paint ) ;
		c.drawBitmap( shop07, page02_pos, shop07_pos, paint ) ;
		c.drawBitmap( shop08, page02_pos, shop08_pos, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 6 )
		{
			c.drawBitmap( lock, page02_pos + 273.4f * scale, shop05_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop05_level ), page02_pos + 286.7f * scale, shop05_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page02_pos + 260.0f * scale, shop05_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 7 )
		{
			c.drawBitmap( lock, page02_pos + 273.4f * scale, shop06_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop06_level ), page02_pos + 286.7f * scale, shop06_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page02_pos + 260.0f * scale, shop06_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 8 )
		{
			c.drawBitmap( lock, page02_pos + 273.4f * scale, shop07_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop07_level ), page02_pos + 286.7f * scale, shop07_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page02_pos + 260.0f * scale, shop07_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 10 )
		{
			c.drawBitmap( lock, page02_pos + 273.4f * scale, shop08_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop08_level ), page02_pos + 286.7f * scale, shop08_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page02_pos + 260.0f * scale, shop08_pos + 33.4f * scale, paint ) ;
	}
	
	private void drawPage03( Canvas c )
	{
		float shop09_pos = 20.0f * scale ;
		float shop10_pos = 110.0f * scale ;
		float shop11_pos = 200.0f * scale ;
		float shop12_pos = 290.0f * scale ;
		
		c.drawBitmap( shop09, page03_pos, shop09_pos, paint ) ;
		c.drawBitmap( shop10, page03_pos, shop10_pos, paint ) ;
		c.drawBitmap( shop11, page03_pos, shop11_pos, paint ) ;
		c.drawBitmap( shop12, page03_pos, shop12_pos, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 13 )
		{
			c.drawBitmap( lock, page03_pos + 273.4f * scale, shop09_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop09_level ), page03_pos + 286.7f * scale, shop09_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page03_pos + 260.0f * scale, shop09_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 18 )
		{
			c.drawBitmap( lock, page03_pos + 273.4f * scale, shop10_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop10_level ), page03_pos + 286.7f * scale, shop10_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page03_pos + 260.0f * scale, shop10_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 22 )
		{
			c.drawBitmap( lock, page03_pos + 273.4f * scale, shop11_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop11_level ), page03_pos + 286.7f * scale, shop11_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page03_pos + 260.0f * scale, shop11_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 25 )
		{
			c.drawBitmap( lock, page03_pos + 273.4f * scale, shop12_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop12_level ), page03_pos + 286.7f * scale, shop12_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page03_pos + 260.0f * scale, shop12_pos + 33.4f * scale, paint ) ;
	}
	
	private void drawPage04( Canvas c )
	{
		float shop13_pos = 20.0f * scale ;
		float shop14_pos = 110.0f * scale ;
		
		c.drawBitmap( shop13, page04_pos, shop13_pos, paint ) ;
		c.drawBitmap( shop14, page04_pos, shop14_pos, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 30 )
		{
			c.drawBitmap( lock, page04_pos + 273.4f * scale, shop13_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop13_level ), page04_pos + 286.7f * scale, shop13_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page04_pos + 260.0f * scale, shop13_pos + 33.4f * scale, paint ) ;
		
		if ( FabLifeApplication.getProfileManager().getLevel() < 34 )
		{
			c.drawBitmap( lock, page04_pos + 273.4f * scale, shop14_pos + 23.4f * scale, paint ) ;
			c.drawText( getString( R.string.shop14_level ), page04_pos + 286.7f * scale, shop14_pos + 56.7f * scale + text_size, t_paint ) ;
		}
		else
			c.drawBitmap( shop_btn, page04_pos + 260.0f * scale, shop14_pos + 33.4f * scale, paint ) ;
	}
	
	private float getPos( int page )
	{
		float pos = 0 ;
		if ( page == 1 )
			pos = page01_pos ;
		else if ( page == 2 )
			pos = page02_pos ;
		else if ( page == 3 )
			pos = page03_pos ;
		else if ( page == 4 )
			pos = page04_pos ;
		return pos ;
	}
	
	private void animation( int target_page )
	{
		float diff = ( getPos( current_page ) - getPos( target_page ) ) / step ;
		for ( int i = 0 ; i < step ; i++ )
		{
			Canvas c = null ;
			try
			{
				c = mHolder.lockCanvas( null ) ;
				synchronized ( mHolder )
				{
					c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
					page01_pos += diff ;
					page02_pos += diff ;
					page03_pos += diff ;
					page04_pos += diff ;
					drawPage01( c ) ;
					drawPage02( c ) ;
					drawPage03( c ) ;
					drawPage04( c ) ;
				}
			}
			finally
			{
				if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
			}
		}
		current_page = target_page ;
		diff = -getPos( current_page ) ;
		Canvas c = null ;
		try
		{
			c = mHolder.lockCanvas( null ) ;
			synchronized ( mHolder )
			{
				c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
				page01_pos += diff ;
				page02_pos += diff ;
				page03_pos += diff ;
				page04_pos += diff ;
				drawPage01( c ) ;
				drawPage02( c ) ;
				drawPage03( c ) ;
				drawPage04( c ) ;
			}
		}
		finally
		{
			if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
		}
		setPage() ;
	}
	
	private void drag_animation()
	{
		float diff = 0 ;
		if ( current_page == 1 )
		{
			if ( getPos( current_page ) <= ( -( width / 2 ) ) )
			{
				diff = ( Math.abs( getPos( current_page ) ) - width ) / step ;
				current_page = 2 ;
			}
			else if ( getPos( current_page ) > ( -( width / 2 ) ) )
			{
				diff = -getPos( current_page ) / step ;
			}
		}
		else if ( current_page == 2 )
		{
			if ( getPos( current_page ) <= ( -( width / 2 ) ) )
			{
				diff = ( Math.abs( getPos( current_page ) ) - width ) / step ;
				current_page = 3 ;
			}
			else if ( ( getPos( current_page ) > ( -( width / 2 ) ) ) && ( getPos( current_page ) <= ( width / 2 ) ) )
			{
				diff = -getPos( current_page ) / step ;
			}
			else if ( getPos( current_page ) > ( width / 2 ) )
			{
				diff = ( width - getPos( current_page ) ) / step ;
				current_page = 1 ;
			}
		}
		else if ( current_page == 3 )
		{
			if ( getPos( current_page ) <= ( -( width / 2 ) ) )
			{
				diff = ( Math.abs( getPos( current_page ) ) - width ) / step ;
				current_page = 4 ;
			}
			else if ( ( getPos( current_page ) > ( -( width / 2 ) ) ) && ( getPos( current_page ) <= ( width / 2 ) ) )
			{
				diff = -getPos( current_page ) / step ;
			}
			else if ( getPos( current_page ) > ( width / 2 ) )
			{
				diff = ( width - getPos( current_page ) ) / step ;
				current_page = 2 ;
			}
		}
		else if ( current_page == 4 )
		{
			if ( getPos( current_page ) <= ( width / 2 ) )
			{
				diff = -getPos( current_page ) / step ;
			}
			else if ( getPos( current_page ) > ( width / 2 ) )
			{
				diff = ( width - getPos( current_page ) ) / step ;
				current_page = 3 ;
			}
		}

		for ( int i = 0 ; i < step ; i++ )
		{
			Canvas c = null ;
			try
			{
				c = mHolder.lockCanvas( null ) ;
				synchronized ( mHolder )
				{
					c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
					page01_pos += diff ;
					page02_pos += diff ;
					page03_pos += diff ;
					page04_pos += diff ;
					drawPage01( c ) ;
					drawPage02( c ) ;
					drawPage03( c ) ;
					drawPage04( c ) ;
				}
			}
			finally
			{
				if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
			}
		}
		
		diff = -getPos( current_page ) ;
		Canvas c = null ;
		try
		{
			c = mHolder.lockCanvas( null ) ;
			synchronized ( mHolder )
			{
				c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
				page01_pos += diff ;
				page02_pos += diff ;
				page03_pos += diff ;
				page04_pos += diff ;
				drawPage01( c ) ;
				drawPage02( c ) ;
				drawPage03( c ) ;
				drawPage04( c ) ;
			}
		}
		finally
		{
			if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
		}
		setPage() ;
	}

	private void draw()
	{
		// TODO Auto-generated method stub
		width = m_Surface.getWidth() ;
		
		scale = (float)( width / 320.0f ) ;
        icon_size = (int)( 93 * scale ) ;
        text_size = (int)( 14 * scale ) ;
        t_paint.setTextSize( text_size ) ;
		
		page02_pos		= width ;
		page03_pos		= 2 * width ;
		page04_pos		= 3 * width ;
		
		if ( shop01 == null )
		{
			shop01 = BitmapFactory.decodeResource( getResources(), R.drawable.shop01, options ) ;
			shop01 = Bitmap.createScaledBitmap( shop01, width, icon_size, true ) ;
			shop02 = BitmapFactory.decodeResource( getResources(), R.drawable.shop02, options ) ;
			shop02 = Bitmap.createScaledBitmap( shop02, width, icon_size, true ) ;
			shop03 = BitmapFactory.decodeResource( getResources(), R.drawable.shop03, options ) ;
			shop03 = Bitmap.createScaledBitmap( shop03, width, icon_size, true ) ;
			shop04 = BitmapFactory.decodeResource( getResources(), R.drawable.shop04, options ) ;
			shop04 = Bitmap.createScaledBitmap( shop04, width, icon_size, true ) ;
			shop05 = BitmapFactory.decodeResource( getResources(), R.drawable.shop05, options ) ;
			shop05 = Bitmap.createScaledBitmap( shop05, width, icon_size, true ) ;
			shop06 = BitmapFactory.decodeResource( getResources(), R.drawable.shop06, options ) ;
			shop06 = Bitmap.createScaledBitmap( shop06, width, icon_size, true ) ;
			shop07 = BitmapFactory.decodeResource( getResources(), R.drawable.shop07, options ) ;
			shop07 = Bitmap.createScaledBitmap( shop07, width, icon_size, true ) ;
			shop08 = BitmapFactory.decodeResource( getResources(), R.drawable.shop08, options ) ;
			shop08 = Bitmap.createScaledBitmap( shop08, width, icon_size, true ) ;
			shop09 = BitmapFactory.decodeResource( getResources(), R.drawable.shop09, options ) ;
			shop09 = Bitmap.createScaledBitmap( shop09, width, icon_size, true ) ;
			shop10 = BitmapFactory.decodeResource( getResources(), R.drawable.shop10, options ) ;
			shop10 = Bitmap.createScaledBitmap( shop10, width, icon_size, true ) ;
			shop11 = BitmapFactory.decodeResource( getResources(), R.drawable.shop11, options ) ;
			shop11 = Bitmap.createScaledBitmap( shop11, width, icon_size, true ) ;
			shop12 = BitmapFactory.decodeResource( getResources(), R.drawable.shop12, options ) ;
			shop12 = Bitmap.createScaledBitmap( shop12, width, icon_size, true ) ;
			shop13 = BitmapFactory.decodeResource( getResources(), R.drawable.shop13, options ) ;
			shop13 = Bitmap.createScaledBitmap( shop13, width, icon_size, true ) ;
			shop14 = BitmapFactory.decodeResource( getResources(), R.drawable.shop14, options ) ;
			shop14 = Bitmap.createScaledBitmap( shop14, width, icon_size, true ) ;
			shop_btn = BitmapFactory.decodeResource( getResources(), R.drawable.goshop_btn, options ) ;
			shop_btn = Bitmap.createScaledBitmap( shop_btn, (int)( 53.4f * scale ), (int)( 33.4f * scale ), true ) ;
			lock = BitmapFactory.decodeResource( getResources(), R.drawable.store_locked, options ) ;
			lock = Bitmap.createScaledBitmap( lock, (int)( 26.7f * scale ), (int)( 29.4f * scale ), true ) ;
		}
		
		Canvas c = null ;
		try
		{
			c = mHolder.lockCanvas( null ) ;
			synchronized ( mHolder )
			{
				c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
				drawPage01( c ) ;
			}
		}
		finally
		{
			if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
		}
	}
	
	public void setPage()
	{
		mDot01.setBackgroundResource( R.drawable.page_dot_off ) ;
		mDot02.setBackgroundResource( R.drawable.page_dot_off ) ;
		mDot03.setBackgroundResource( R.drawable.page_dot_off ) ;
		mDot04.setBackgroundResource( R.drawable.page_dot_off ) ;
	
		if ( current_page == 1 )
		{
			mDot01.setBackgroundResource( R.drawable.page_dot_on ) ;
		}
		else if ( current_page == 2 )
		{
			mDot02.setBackgroundResource( R.drawable.page_dot_on ) ;
		}
		else if ( current_page == 3 )
		{
			mDot03.setBackgroundResource( R.drawable.page_dot_on ) ;
		}
		else if ( current_page == 4 )
		{
			mDot04.setBackgroundResource( R.drawable.page_dot_on ) ;
		}
	}

	@Override
	public void onClick( View v )
	{
		if ( isAniming )
			return ;
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.StoreListGoHomeButton )
		{
			FabLifeApplication.playHomeEffect() ;
			clear() ;
			try
			{
				System.gc() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			action = true ;
			Intent intent = new Intent( this, main.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( v.getId() == R.id.StorePageDot01 )
		{
			FabLifeApplication.playTapEffect() ;
			if ( current_page == 1 )
				return ;
			isAniming = true ;
			animation( 1 ) ;
			isAniming = false ;
		}
		else if ( v.getId() == R.id.StorePageDot02 )
		{
			FabLifeApplication.playTapEffect() ;
			if ( current_page == 2 )
				return ;
			isAniming = true ;
			animation( 2 ) ;
			isAniming = false ;
		}
		else if ( v.getId() == R.id.StorePageDot03 )
		{
			FabLifeApplication.playTapEffect() ;
			if ( current_page == 3 )
				return ;
			isAniming = true ;
			animation( 3 ) ;
			isAniming = false ;
		}
		else if ( v.getId() == R.id.StorePageDot04 )
		{
			FabLifeApplication.playTapEffect() ;
			if ( current_page == 4 )
				return ;
			isAniming = true ;
			animation( 4 ) ;
			isAniming = false ;
		}
	}
	
	float tmpx ;
	int select_shop = 0 ;
	@Override
	public boolean onTouch( View v, MotionEvent event )
	{
		if ( isAniming )
			return true ;
		// TODO Auto-generated method stub
		if ( event.getAction() == MotionEvent.ACTION_DOWN )
		{
			tmpx = event.getX() ;
			float x = event.getX() ;
			float y = event.getY() ;
			
			float shop01_pos = 20.0f * scale ;
			float shop02_pos = 110.0f * scale ;
			float shop03_pos = 200.0f * scale ;
			float shop04_pos = 290.0f * scale ;
			
			if ( current_page == 01 )
			{
				if ( ( x > ( 260.0f * scale ) ) && ( x < ( 313.4f * scale ) ) )
				{
					if ( ( y > ( shop01_pos + 26.7f * scale ) ) && ( y < ( shop01_pos + 66.7f * scale ) ) )
					{
						FabLifeApplication.playTapEffect() ;
						clear() ;
						try
						{
							System.gc() ;
						}
						catch( Exception e )
						{
							e.printStackTrace() ;
						}
						action = true ;
						Intent intent = new Intent( this, Shop.class ) ;
						intent.putExtra( "name", getString( R.string.shop01_name ) ) ;
						startActivity( intent ) ;
						finish() ;
						return false ;
					}
					else if ( ( y > ( shop02_pos + 26.7f * scale ) ) && ( y < ( shop02_pos + 66.7f * scale ) ) ) 
					{
						if ( FabLifeApplication.getProfileManager().getTutorialFinished() )
						{
							FabLifeApplication.playTapEffect() ;
							clear() ;
							try
							{
								System.gc() ;
							}
							catch( Exception e )
							{
								e.printStackTrace() ;
							}
							action = true ;
							Intent intent = new Intent( this, Shop.class ) ;
							intent.putExtra( "name", getString( R.string.shop02_name ) ) ;
							startActivity( intent ) ;
							finish() ;
						}
						return false ;
					}
					else if ( ( y > ( shop03_pos + 26.7f * scale ) ) && ( y < ( shop03_pos + 66.7f * scale ) ) ) 
					{
						if ( FabLifeApplication.getProfileManager().getTutorialFinished() )
						{
							FabLifeApplication.playTapEffect() ;
							clear() ;
							try
							{
								System.gc() ;
							}
							catch( Exception e )
							{
								e.printStackTrace() ;
							}
							action = true ;
							Intent intent = new Intent( this, Shop.class ) ;
							intent.putExtra( "name", getString( R.string.shop03_name ) ) ;
							startActivity( intent ) ;
							finish() ;
						}
						return false ;
					}
					else if ( ( y > ( shop04_pos + 26.7f * scale ) ) && ( y < ( shop04_pos + 66.7f * scale ) ) ) 
					{
						if ( FabLifeApplication.getProfileManager().getTutorialFinished() )
						{
							FabLifeApplication.playTapEffect() ;
							if ( FabLifeApplication.getProfileManager().getLevel() >= 3 )
							{
								clear() ;
								try
								{
									System.gc() ;
								}
								catch( Exception e )
								{
									e.printStackTrace() ;
								}
								action = true ;
								Intent intent = new Intent( this, Shop.class ) ;
								intent.putExtra( "name", getString( R.string.shop04_name ) ) ;
								startActivity( intent ) ;
								finish() ;
							}
						}
						return false ;
					}
				}						
			}
			else if ( current_page == 02 )
			{
				if ( ( x > ( 260.0f * scale ) ) && ( x < ( 313.4f * scale ) ) )
				{
					if ( ( y > ( shop01_pos + 26.7f * scale ) ) && ( y < ( shop01_pos + 66.7f * scale ) ) )
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 6 )
						{
							clear() ;
							try
							{
								System.gc() ;
							}
							catch( Exception e )
							{
								e.printStackTrace() ;
							}
							action = true ;
							Intent intent = new Intent( this, Shop.class ) ;
							intent.putExtra( "name", getString( R.string.shop05_name ) ) ;
							startActivity( intent ) ;
							finish() ;
						}
						return false ;
					}
					else if ( ( y > ( shop02_pos + 26.7f * scale ) ) && ( y < ( shop02_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 7 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 6 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
					else if ( ( y > ( shop03_pos + 26.7f * scale ) ) && ( y < ( shop03_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 8 )
						{
							if ( FabLifeApplication.getProfileManager().isVIPMember() )
							{
								if ( FabLifeApplication.getProfileManager().getCash() < 5 )
									showNotEnoughDialog() ;
								else
								{
									select_shop = 7 ;
									showTravelDialog() ;
								}
							}
							else
							{
								showVIPDialog() ;
							}
						}
						return false ;
					}
					else if ( ( y > ( shop04_pos + 26.7f * scale ) ) && ( y < ( shop04_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 10 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 8 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
				}
			}
			else if ( current_page == 03 )
			{
				if ( ( x > ( 260.0f * scale ) ) && ( x < ( 313.4f * scale ) ) )
				{
					if ( ( y > ( shop01_pos + 26.7f * scale ) ) && ( y < ( shop01_pos + 66.7f * scale ) ) )
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 13 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 9 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
					else if ( ( y > ( shop02_pos + 26.7f * scale ) ) && ( y < ( shop02_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 18 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 10 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
					else if ( ( y > ( shop03_pos + 26.7f * scale ) ) && ( y < ( shop03_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 22 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 11 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
					else if ( ( y > ( shop04_pos + 26.7f * scale ) ) && ( y < ( shop04_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 25 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 12 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
				}
			}
			else if ( current_page == 04 )
			{
				if ( ( x > ( 260.0f * scale ) ) && ( x < ( 313.4f * scale ) ) )
				{
					if ( ( y > ( shop01_pos + 26.7f * scale ) ) && ( y < ( shop01_pos + 66.7f * scale ) ) )
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 30 )
						{
							if ( FabLifeApplication.getProfileManager().isVIPMember() )
							{
								if ( FabLifeApplication.getProfileManager().getCash() < 5 )
									showNotEnoughDialog() ;
								else
								{
									select_shop = 13 ;
									showTravelDialog() ;
								}
							}
							else
							{
								showVIPDialog() ;
							}
						}
						return false ;
					}
					else if ( ( y > ( shop02_pos + 26.7f * scale ) ) && ( y < ( shop02_pos + 66.7f * scale ) ) ) 
					{
						FabLifeApplication.playTapEffect() ;
						if ( FabLifeApplication.getProfileManager().getLevel() >= 34 )
						{
							if ( FabLifeApplication.getProfileManager().getCash() < 5 )
								showNotEnoughDialog() ;
							else
							{
								select_shop = 14 ;
								showTravelDialog() ;
							}
						}
						return false ;
					}
				}
			}
		}
		else if ( event.getAction() == MotionEvent.ACTION_MOVE )
		{
			if ( FabLifeApplication.getProfileManager().getTutorialFinished() )
			{
				page01_pos += ( event.getX() - tmpx ) ;
				page02_pos += ( event.getX() - tmpx ) ;
				page03_pos += ( event.getX() - tmpx ) ;
				page04_pos += ( event.getX() - tmpx ) ;
				Canvas c = null ;
				try
				{
					c = mHolder.lockCanvas( null ) ;
					synchronized ( mHolder )
					{
						c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
						drawPage01( c ) ;
						drawPage02( c ) ;
						drawPage03( c ) ;
						drawPage04( c ) ;
					}
				}
				finally
				{
					if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
				}
				tmpx = event.getX() ;
			}
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP )
		{
			if ( FabLifeApplication.getProfileManager().getTutorialFinished() )
				drag_animation() ;
		}
		return true ;
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
        			clear() ;
        			try
					{
						System.gc() ;
					}
					catch( Exception e )
					{
						e.printStackTrace() ;
					}
        			action = true ;
        			Intent intent = new Intent( StoreList.this, Bank.class ) ;
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
	
	private void showTravelDialog()
	{
		String message = "Are you sure you want to travel for 5 cash?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setCancelable( false )
		.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			if ( select_shop >= 6 && select_shop <= 14 )
	    			{
	    				FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() - 5 ) ;
	    				clear() ;
	    				try
						{
							System.gc() ;
						}
						catch( Exception e )
						{
							e.printStackTrace() ;
						}
	    				action = true ;
	    				Intent intent = new Intent( getApplicationContext(), Shop.class ) ;
	    				intent.putExtra( "name", getString( R.string.shop06_name + select_shop - 6 ) ) ;
	    				startActivity( intent ) ;
						finish() ;
	    			}
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
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	private void showVIPDialog()
	{
		String message = "You are not a VIP memeber. Would you like to become a VIP member?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setCancelable( false )
		.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			clear() ;
	    			try
					{
						System.gc() ;
					}
					catch( Exception e )
					{
						e.printStackTrace() ;
					}
	    			action = true ;
	    			Intent intent = new Intent( StoreList.this, VIP.class ) ;
	    			startActivity( intent ) ;
	    			finish() ;
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
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	void clear()
	{
		if ( shop01	!= null ) { if ( !shop01.isRecycled() ) shop01.recycle() ; shop01 = null ; }
		if ( shop02	!= null ) { if ( !shop02.isRecycled() ) shop02.recycle() ; shop02 = null ; }
		if ( shop03	!= null ) { if ( !shop03.isRecycled() ) shop03.recycle() ; shop03 = null ; }
		if ( shop04	!= null ) { if ( !shop04.isRecycled() ) shop04.recycle() ; shop04 = null ; }
		if ( shop05	!= null ) { if ( !shop05.isRecycled() ) shop05.recycle() ; shop05 = null ; }
		if ( shop06	!= null ) { if ( !shop06.isRecycled() ) shop06.recycle() ; shop06 = null ; }
		if ( shop07	!= null ) { if ( !shop07.isRecycled() ) shop07.recycle() ; shop07 = null ; }
		if ( shop08	!= null ) { if ( !shop08.isRecycled() ) shop08.recycle() ; shop08 = null ; }
		if ( shop09	!= null ) { if ( !shop09.isRecycled() ) shop09.recycle() ; shop09 = null ; }
		if ( shop10	!= null ) { if ( !shop10.isRecycled() ) shop10.recycle() ; shop10 = null ; }
		if ( shop11	!= null ) { if ( !shop12.isRecycled() ) shop11.recycle() ; shop11 = null ; }
		if ( shop12	!= null ) { if ( !shop12.isRecycled() ) shop12.recycle() ; shop12 = null ; }
		if ( shop13	!= null ) { if ( !shop13.isRecycled() ) shop13.recycle() ; shop13 = null ; }
		if ( shop14	!= null ) { if ( !shop14.isRecycled() ) shop14.recycle() ; shop14 = null ; }
		if ( shop_btn	!= null ) { if ( !shop_btn.isRecycled() ) shop_btn.recycle() ; shop_btn = null ; }
		if ( lock	!= null ) { if ( !lock.isRecycled() ) lock.recycle() ; lock = null ; }
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
		super.onStart();
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
		super.onStop();
	}

	@Override
	public void onBackPressed() 
	{
	}
}
