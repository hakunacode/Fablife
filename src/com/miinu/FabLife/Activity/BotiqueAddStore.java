package com.miinu.FabLife.Activity;

import java.util.ArrayList;

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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.PorterDuff ;

public class BotiqueAddStore extends Activity implements SurfaceHolder.Callback, Runnable, OnClickListener, OnTouchListener
{
	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private		Thread 				thread 					= null ;
	private		SurfaceView 		m_Surface 				= null ;
	private		SurfaceHolder 		mHolder 				= null ;
	
	private		ImageButton 		mBackBtn				= null ;
	private		LinearLayout		mPageLayout				= null ;
	
	private		Paint 				paint	 				= null ;
	private		Paint				t_paint					= null ;
	
	private		int 				text_size				= 14 ;
	
	private		final int 			step					= 10 ;
	private		int 				icon_size 				= 90 ;
	
	private		int 				width 					= 320 ;
	private		int 				height 					= 430 ;
	private		float				scale					= 1.0f ;
	
	private		int 				current_page			= 0 ;
	private		int 				page_count				= 8 ;
	private		ArrayList<Bitmap>	mBitmaps				= new ArrayList<Bitmap>() ;
	private		Bitmap				bg						= null ;
	private		Bitmap				btn						= null ;
	private		float				x_pos					= 0.0f ;
	
	private		BitmapFactory.Options options 				= new BitmapFactory.Options() ;
	
	private		MediaPlayer			mBotiquePlayer			= null ;
	private		boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.botique_addstore ) ;
        
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
        
        m_Surface 	= (SurfaceView)		findViewById( R.id.BotiqueShopSurface ) ;
        mHolder = m_Surface.getHolder() ; mHolder.addCallback( this ) ; mHolder.setType( SurfaceHolder.SURFACE_TYPE_NORMAL ) ;
        
        mBackBtn	= (ImageButton)		findViewById( R.id.BotiqueBackButton ) ;
        mPageLayout	= (LinearLayout)	findViewById( R.id.BotiquePageLayout ) ;
    
        m_Surface.setOnTouchListener( this ) ;
        mBackBtn.setOnClickListener( this ) ;
        
        paint = new Paint() ; paint.setFilterBitmap( true ) ; paint.setAntiAlias( true ) ;
		t_paint = new Paint() ; t_paint.setTypeface( mFace ) ; t_paint.setTextAlign( Align.CENTER ) ;
		t_paint.setFilterBitmap( true ) ; t_paint.setAntiAlias( true ) ;
		t_paint.setColor( getResources().getColor( R.color.white ) ) ;
		
		options.inPurgeable = true ;
		
		mBotiquePlayer		= MediaPlayer.create( getApplicationContext(), R.raw.boutique ) ;
        mBotiquePlayer.setLooping( true ) ;
        
		initPageLayout() ;
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
    	thread = new Thread( this ) ;
    	thread.start() ;
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
		thread.stop() ;
	}
	
	void getBitmaps() throws Exception
	{
		for ( int i = 0 ; i < mBitmaps.size() ; i++ )
		{
			mBitmaps.get( i ).recycle() ;
		}
		mBitmaps.clear() ;
		try
		{
			System.gc() ;
		}
		catch ( Exception e )
		{
			throw new Exception( "Get Bitmaps Fail!" ) ;
		}
		
		int shop01_pos = 20 ;
		int shop02_pos = 110  ;
		int shop03_pos = 200 ;
		int shop04_pos = 290 ;
		
		int count = FabLifeApplication.getBotiqueShopManager().getRemainCount() ;
		page_count = count / 4 ;
		int mod = count % 4 ;
		if ( mod != 0 ) page_count++ ;
		
		for ( int i = 0 ; i < page_count ; i++ )
		{
			Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 ) ;
			Canvas canvas = new Canvas( bitmap ) ;
			
			for ( int j = 0 ; j < 4 ; j++ )
			{
				if ( i * 4 + j < count )
				{
					Bitmap shop = BitmapFactory.decodeResource( getResources(), R.drawable.botique_shop01 + FabLifeApplication.getBotiqueShopManager().getRemainShop( i * 4 + j ), options ) ;
					int shop_pos = shop01_pos ;
					switch ( j % 4 )
					{
						case 0 :
							shop_pos = shop01_pos ;
							break ;
						case 1 :
							shop_pos = shop02_pos ;
							break ;
						case 2 :
							shop_pos = shop03_pos ;
							break ;
						case 3 :
							shop_pos = shop04_pos ;
							break ;
					}
					RectF shop_rect = new RectF( 0, shop_pos * scale, width, ( shop_pos + icon_size ) * scale ) ;
					canvas.drawBitmap( shop, null, shop_rect, paint ) ;
					
					RectF btn_rect = new RectF( 246 * scale, ( shop_pos + 28 ) * scale, 306 * scale, ( shop_pos + 64 ) * scale ) ;
					canvas.drawBitmap( btn, null, btn_rect, paint ) ;

					canvas.drawText( "" + FabLifeApplication.getBotiqueShopManager().getConstructCoins( FabLifeApplication.getBotiqueShopManager().getRemainShop( i * 4 + j ) ), 278 * scale, ( shop_pos + 28 + text_size ) * scale, t_paint ) ;
					canvas.drawText( "coins", 278 * scale, ( shop_pos + 45 + text_size ) * scale, t_paint ) ;
					shop.recycle() ; shop = null ;
				}
			}
			mBitmaps.add( bitmap ) ;
		}
	}
	
	OnClickListener dotClickListener = new OnClickListener()
	{
		@Override
		public void onClick( View v )
		{
			// TODO Auto-generated method stub
			animation( v.getId() ) ;
		}	
	} ;
	
	void draw()
	{
		Canvas c = null ;
		try
		{
			c = mHolder.lockCanvas( null ) ;
			synchronized ( mHolder )
			{
				c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
				Rect bg_rect = new Rect( 0, 0, width, height ) ;
				c.drawBitmap( bg, null, bg_rect, paint ) ;
				if ( mBitmaps.size() > 0 )
				{
					if ( current_page > 0 )
					{
						Rect rect01 = new Rect( (int)( x_pos - width ), 0, (int)x_pos, height ) ;
						c.drawBitmap( mBitmaps.get( current_page - 1 ), null, rect01, paint ) ;
					}
					Rect rect02 = new Rect( (int)x_pos, 0, (int)( x_pos + width ), height ) ;
					c.drawBitmap( mBitmaps.get( current_page ), null, rect02, paint ) ;
					if ( current_page < page_count - 1 )
					{
						Rect rect03 = new Rect( (int)( x_pos + width ), 0, (int)( x_pos + 2 * width ), height ) ;
						c.drawBitmap( mBitmaps.get( current_page + 1 ), null, rect03, paint ) ;
					}
				}
			}
		}
		finally
		{
			if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
		}
	}
	
	void initPageLayout()
	{
		int count = FabLifeApplication.getBotiqueShopManager().getRemainCount() ;
		page_count = count / 4 ;
		int mod = count % 4 ;
		if ( mod != 0 ) page_count++ ;
		
		mPageLayout.removeAllViews() ;
		for ( int i = 0 ; i < page_count ; i++ )
		{
			ImageButton dot = new ImageButton( this ) ;
			dot.setId( i ) ;
			dot.setOnClickListener( dotClickListener ) ;
			dot.setBackgroundResource( R.drawable.page_dot_off ) ;
			mPageLayout.addView( dot, (int)( 20 * scale ), (int)( 20 * scale ) ) ;
		}
		
		if ( current_page >= page_count )
		{
			current_page = page_count - 1 ;
		}
		
		ImageButton view = (ImageButton)mPageLayout.findViewById( current_page ) ;
		if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ; 
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		width = m_Surface.getWidth() ;
		height = m_Surface.getHeight() ;
		
		scale = width / 320.0f ;
        
		bg = BitmapFactory.decodeResource( getResources(), R.drawable.openastore_background, options ) ;
		btn = BitmapFactory.decodeResource( getResources(), R.drawable.boutique_button_bg, options ) ;
		
		t_paint.setTextSize( text_size * scale ) ;
		
		try
		{
			getBitmaps() ;
		}
		catch( Exception e )
		{
			e.printStackTrace() ;
		}
        
        draw() ;
	}
	
	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.BotiqueBackButton )
		{
			FabLifeApplication.playTapEffect() ;
			for ( int i = 0 ; i < mBitmaps.size() ; i++ )
			{
				mBitmaps.get( i ).recycle() ;
			}
			mBitmaps.clear() ;
			try
			{
				System.gc() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			if ( mBotiquePlayer != null )
			{
				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
				mBotiquePlayer.release() ; mBotiquePlayer = null ;
			}
			action = true ;
			Intent intent = new Intent( this, BotiqueMain.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
	}
	
	float tmpx ;
	int select_shop ;
	@Override
	public boolean onTouch( View v, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( event.getAction() == MotionEvent.ACTION_DOWN )
		{
			tmpx = event.getX() ;
			float x = event.getX() ;
			float y = event.getY() ;
			
			float shop01_pos = 20 * scale ;
			float shop02_pos = 110 * scale ;
			float shop03_pos = 200 * scale ;
			float shop04_pos = 290 * scale ;
			
			if ( x > 246 * scale && x < 306 * scale )
			{
				if ( ( y > ( shop01_pos + 26 * scale ) ) && ( y < ( shop01_pos + 62 * scale ) ) )
				{
					if ( current_page >= 0 && current_page * 4 < FabLifeApplication.getBotiqueShopManager().getRemainCount() )
					{
						FabLifeApplication.playTapEffect() ;
						select_shop = current_page * 4 ;
						showConfirmDialog() ;
					}
				}
				else if ( ( y > ( shop02_pos + 26 * scale ) ) && ( y < ( shop02_pos + 62 * scale ) ) )
				{
					if ( current_page >= 0 && current_page * 4 + 1 < FabLifeApplication.getBotiqueShopManager().getRemainCount() )
					{
						FabLifeApplication.playTapEffect() ;
						select_shop = current_page * 4 + 1 ;
						showConfirmDialog() ;
					}
				}
				else if ( ( y > ( shop03_pos + 26 * scale ) ) && ( y < ( shop03_pos + 62 * scale ) ) ) 
				{
					if ( current_page >= 0 && current_page * 4 + 2 < FabLifeApplication.getBotiqueShopManager().getRemainCount() )
					{
						FabLifeApplication.playTapEffect() ;
						select_shop = current_page * 4 + 2 ;
						showConfirmDialog() ;
					}
				}
				else if ( ( y > ( shop04_pos + 26 * scale ) ) && ( y < ( shop04_pos + 62 * scale ) ) )
				{
					if ( current_page >= 0 && current_page * 4 + 3 < FabLifeApplication.getBotiqueShopManager().getRemainCount() )
					{
						FabLifeApplication.playTapEffect() ;
						select_shop = current_page * 4 + 3 ;
						showConfirmDialog() ;
					}
				}
			}
		}
		else if ( event.getAction() == MotionEvent.ACTION_MOVE )
		{
			x_pos += ( event.getX() - tmpx ) ;
			draw() ;
			tmpx = event.getX() ;
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP )
		{
			drag_animation() ;
		}
		return true ;
	}
	
	private void drag_animation()
	{
		float diff = 0 ;
		
		if ( x_pos <= ( -( width / 2 ) ) )
		{
			if ( current_page + 1 < page_count )
			{
				diff = ( Math.abs( x_pos ) - width ) / step ;
				anim( diff ) ;
				ImageButton view = (ImageButton)mPageLayout.findViewById( current_page ) ;
				if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_off ) ; 
				current_page++ ;
				view = (ImageButton)mPageLayout.findViewById( current_page ) ;
				if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ; 
			}
			else
			{
				diff = -x_pos / step ;
				anim( diff ) ;
			}
		}
		else if ( x_pos > ( -( width / 2 ) ) && ( x_pos <= ( width / 2 ) ) )
		{
			diff = -x_pos / step ;
			anim( diff ) ;
		}
		else if ( x_pos > ( width / 2 ) )
		{
			if ( current_page > 0 )
			{
				diff = ( width - x_pos ) / step ;
				anim( diff ) ;
				ImageButton view = (ImageButton)mPageLayout.findViewById( current_page ) ;
				if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_off ) ; 
				current_page-- ;
				view = (ImageButton)mPageLayout.findViewById( current_page ) ;
				if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ; 
			}
			else
			{
				diff = -x_pos / step ;
				anim( diff ) ;
			}
		}
	}
	
	void anim( float diff )
	{
		for ( int i = 0 ; i < step ; i++ )
		{
			x_pos += diff ;
			draw() ;
		}
		x_pos = 0.0f ;
	}
	
	void anim_page( float diff )
	{
		for ( int i = 0 ; i < 4 ; i++ )
		{
			x_pos += diff ;
			draw() ;
		}
		x_pos = 0.0f ;
	}
	
	private void animation( int target_page )
	{
		float diff = 0 ;
		if ( current_page != target_page )
		{
			if ( current_page < target_page )
			{
				for ( int i = current_page ; i < target_page ; i++ )
				{
					diff = ( x_pos - width ) / 4 ;
					anim_page( diff ) ;
					ImageButton view = (ImageButton)mPageLayout.findViewById( current_page ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_off ) ; 
					current_page++ ;
					view = (ImageButton)mPageLayout.findViewById( current_page ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ; 
				}
			}
			else
			{
				for ( int i = current_page ; i > target_page ; i-- )
				{
					diff = ( width - x_pos ) / 4 ;
					anim_page( diff ) ;
					ImageButton view = (ImageButton)mPageLayout.findViewById( current_page ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_off ) ; 
					current_page-- ;
					view = (ImageButton)mPageLayout.findViewById( current_page ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
				}
			}
		}
	}
	
	private void buyShop()
	{
		FabLifeApplication.getBotiqueShopManager().buy( select_shop ) ;
		mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
		mStatusBarLayout.invalidate() ;
		
		if ( FabLifeApplication.getProfileManager().isFirstAddStore() )
		{
			for ( int i = 0 ; i < mBitmaps.size() ; i++ )
			{
				mBitmaps.get( i ).recycle() ;
			}
			mBitmaps.clear() ;
			try
			{
				System.gc() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			if ( mBotiquePlayer != null )
			{
				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
				mBotiquePlayer.release() ; mBotiquePlayer = null ;
			}
			action = true ;
			Intent intent = new Intent( this, BotiqueMain.class ) ;
			intent.putExtra( "firstadd", true ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else
		{
			try
			{
				getBitmaps() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			initPageLayout() ;
			draw() ;
		}
	}
	
	private void showConfirmDialog()
	{
		if ( FabLifeApplication.getBotiqueShopManager().getConstructCoinsByIndex( select_shop  ) > FabLifeApplication.getProfileManager().getCoin() )
		{
			AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
			builder.setMessage( getString( R.string.coin_not_enough ) )
	        .setCancelable( false )
	        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
	        	{
	        		public void onClick( DialogInterface dialog, int id )
	        		{
	        			for ( int i = 0 ; i < mBitmaps.size() ; i++ )
	        			{
	        				mBitmaps.get( i ).recycle() ;
	        			}
	        			mBitmaps.clear() ;
	        			try
	        			{
	        				System.gc() ;
	        			}
	        			catch( Exception e )
	        			{
	        				e.printStackTrace() ;
	        			}
	        			if ( mBotiquePlayer != null )
	        			{
	        				if ( mBotiquePlayer.isPlaying() ) mBotiquePlayer.stop() ;
	        				mBotiquePlayer.release() ; mBotiquePlayer = null ;
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
		else
		{
			String message = "Confirm to add this store for " ;
			message += FabLifeApplication.getBotiqueShopManager().getConstructCoins( FabLifeApplication.getBotiqueShopManager().getRemainShop( select_shop ) ) ; message += " coins?" ;
			AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
			builder.setMessage( message )
			.setCancelable( false )
			.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
		    	{
		    		public void onClick( DialogInterface dialog, int id )
		    		{
		    			buyShop() ;
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
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		return ;
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
}
