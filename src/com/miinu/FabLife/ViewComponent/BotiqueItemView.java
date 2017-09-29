package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Activity.BotiqueMain;
import com.miinu.FabLife.Activity.BotiqueTip;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.BotiqueShopManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BotiqueItemView extends View implements OnTouchListener
{
	Context						mContext			= null ;
	Handler						mHandler			= null ;
	int							mStatus				= BotiqueShopManager.STATUS_NONE ;
	int							mTime				= 0 ;
	int							mShop				= 0 ;
	
	Bitmap						bitmap_none			= null ;
	Bitmap						bitmap_shop 		= null ;
	Bitmap						bitmap_donein		= null ;
	Bitmap						bitmap_construct	= null ;
	Bitmap						bitmap_open01		= null ;
	Bitmap						bitmap_open02		= null ;
	Bitmap						bitmap_woman01		= null ;
	Bitmap						bitmap_woman02		= null ;
	Bitmap						bitmap_woman03		= null ;
	Bitmap						bitmap_woman04		= null ;
	Bitmap						bitmap_finishnow	= null ;
	Bitmap						bitmap_collect		= null ;
	Bitmap						bitmap_restock		= null ;
	
	Paint						paint				= null ;
	Paint						t_paint				= null ;
	
	int							width				= 76 ;
	int 						height				= 96 ;
	float						scale				= 1.0f ;
	
	//	Finish now
	int							finishnow_width 	= 70 ;
	int							finishnow_height	= 30 ;
	float						finishnow_top		= 60.0f ;
	float						finishnow_left		= 3.0f ;
	
	//	Done in
	int							donein_width 		= 76 ;
	int							donein_height 		= 40 ;
	float						donein01_top 		= 6.0f ;
	float						donein01_left 		= 0.0f ;
	
	//	Collect
	int							collect_width 		= 70 ;
	int							collect_height 		= 40 ;
	float						collect_top 		= 40.0f ;
	float						collect_left 		= 3.0f ;
	
	//	Restock
	int							restock_width 		= 70 ;
	int							restock_height 		= 40 ;
	float						restock_top			= 40.0f ;
	float						restock_left 		= 3.0f ;
	
	//	DoneIn
	float						donein02_top 		= 40.0f ;
	float						donein02_left 		= 0.0f ;

	boolean						mShowTimer			= false ;
	boolean						mOpenFlag			= false ;
	
	int							avatar_step			= 0 ;
	
	int 						text_size 			= 12 ;
	Typeface 					t_face 				= null ;
	BitmapFactory.Options		options				= new BitmapFactory.Options() ;
	boolean						destroyed			= false ;
	
	public BotiqueItemView( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		
		mContext = context ;
		
		setOnTouchListener( this ) ;
		
		paint	= new Paint() ; paint.setAntiAlias( true ) ; paint.setFilterBitmap( true ) ;
		
		t_face = Typeface.createFromAsset( context.getAssets(), "font/abeatbykai.ttf" ) ;
		t_paint = new Paint() ; t_paint.setTextAlign( Align.CENTER ) ; t_paint.setTextSize( text_size ) ;
		t_paint.setColor( Color.WHITE ) ; t_paint.setTypeface( t_face ) ; t_paint.setAntiAlias( true ) ;
		t_paint.setFilterBitmap( true ) ; t_paint.setTextScaleX( 0.8f ) ;
		options.inPurgeable = true ;
	}
	
	public void setHandler( Handler h )
	{
		mHandler = h ;
	}
	
	public void setShop( int s )
	{
		mShop = s ;
		mStatus = FabLifeApplication.getBotiqueShopManager().getShopStatus( mShop ) ;
		mTime = FabLifeApplication.getBotiqueShopManager().getShopTime( mShop ) ;
		if ( mStatus == BotiqueShopManager.STATUS_CONSTRUCT )
		{
			mTimer.sendEmptyMessage( 0 ) ;
		}
		else if ( mStatus == BotiqueShopManager.STATUS_OPEN )
		{
			mOpenHandler.sendEmptyMessage( 0 ) ;
		}
		else if ( mStatus == BotiqueShopManager.STATUS_RESTOCK )
		{
		}
		else if ( mStatus == BotiqueShopManager.STATUS_RUN )
		{
			mRunHandler.sendEmptyMessage( 0 ) ;
			mTimer.sendEmptyMessage( 0 ) ;
		}
		else if ( mStatus == BotiqueShopManager.STATUS_COLLECT )
		{
		}
	}
	
	public int getStatus()
	{
		return mStatus ;
	}
	
	public int getTime()
	{
		return mTime ;
	}
	
	boolean isShowingDialog = false ;
	@Override
	public boolean onTouch( View v, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( event.getAction() == MotionEvent.ACTION_UP )
		{
			float x = event.getX() ;
			float y = event.getY() ;
			if ( mStatus == BotiqueShopManager.STATUS_NONE )
			{
				return true ;
			}
			else if ( mStatus == BotiqueShopManager.STATUS_CONSTRUCT )
			{
				if ( x > finishnow_left && x < ( finishnow_left + finishnow_width )
						&& y > finishnow_top && y < ( finishnow_top + finishnow_height ) )
				{
					if ( FabLifeApplication.getProfileManager().getCash() > FabLifeApplication.getBotiqueShopManager().getConstructFinish( mShop ) )
					{
						showFinishDialog() ;
					}
					else
					{
						mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_NOMONEY ) ;
					}
				}
				else
				{
					if ( !mShowTimer )
					{
						mShowTimer = true ;
						invalidate() ;
						Handler handler = new Handler()
						{
							@Override
							public void handleMessage( Message msg )
							{
								// TODO Auto-generated method stub
								mShowTimer = false ;
								invalidate() ;
							}
						} ;
						handler.sendEmptyMessageDelayed( 0, 5000 ) ;
					}
				}
			}
			else if ( mStatus == BotiqueShopManager.STATUS_OPEN )
			{
				if ( FabLifeApplication.getProfileManager().getEnergy() > 18 )
				{
					FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() - 18 ) ;
					if ( FabLifeApplication.getProfileManager().isFirstOpen() )
					{
						Intent intent = new Intent( mContext, BotiqueTip.class );
						mContext.startActivity( intent ) ;
					}
					mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_PLAY_OPEN ) ;
					mStatus = BotiqueShopManager.STATUS_RESTOCK ;
					invalidate() ;
				}
				else
					mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_NOENERGY ) ;
			}
			else if ( mStatus == BotiqueShopManager.STATUS_RESTOCK )
			{
				if ( x > restock_left && x < ( restock_left + restock_width )
						&& y > restock_top && y < ( restock_top + restock_height ) )
				{
					if ( FabLifeApplication.getProfileManager().getEnergy() < FabLifeApplication.getBotiqueShopManager().getRestockEnergy( mShop ) )
					{
						mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_NOENERGY ) ;
						return true ;
					}
					
					if ( FabLifeApplication.getProfileManager().getCoin() < FabLifeApplication.getBotiqueShopManager().getRestockCoins( mShop ) )
					{
						mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_NOMONEY ) ;
						return true ;
					}
					FabLifeApplication.getProfileManager().setEnergy(  FabLifeApplication.getProfileManager().getEnergy() - FabLifeApplication.getBotiqueShopManager().getRestockEnergy( mShop ) ) ;
					FabLifeApplication.getProfileManager().setCoin(  FabLifeApplication.getProfileManager().getCoin() - FabLifeApplication.getBotiqueShopManager().getRestockCoins( mShop ) ) ;
					
					mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_PLAY_RESTOCK ) ;
					
					if ( FabLifeApplication.getProfileManager().isFirstRestock() )
					{
						Intent intent = new Intent( mContext, BotiqueTip.class );
						mContext.startActivity( intent ) ;
					}
					mTime = FabLifeApplication.getBotiqueShopManager().getConstructTime( mShop ) ;
					mStatus = BotiqueShopManager.STATUS_RUN ;
					mTimer.sendEmptyMessage( 0 ) ;
					mRunHandler.sendEmptyMessage( 0 ) ;
				}
			}
			else if ( mStatus == BotiqueShopManager.STATUS_RUN )
			{
				if ( !mShowTimer )
				{
					mShowTimer = true ;
					Handler handler = new Handler()
					{
						@Override
						public void handleMessage( Message msg )
						{
							// TODO Auto-generated method stub
							mShowTimer = false ;
							invalidate() ;
						}
					} ;
					handler.sendEmptyMessageDelayed( 0, 5000 ) ;
				}
			}
			else if ( mStatus == BotiqueShopManager.STATUS_COLLECT )
			{
				if ( x > collect_left && x < ( collect_left + collect_width )
						&& y > collect_top && y < ( collect_top + collect_height ) )
				{
					FabLifeApplication.getProfileManager().setCoin(  FabLifeApplication.getProfileManager().getCoin() + FabLifeApplication.getBotiqueShopManager().getCollectCoins( mShop ) ) ;
					FabLifeApplication.getProfileManager().setEarning(  FabLifeApplication.getProfileManager().getEarning() + FabLifeApplication.getBotiqueShopManager().getCollectCoins( mShop ) ) ;
					
					mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_PLAY_COLLECT ) ;
					if ( FabLifeApplication.getProfileManager().isFirstCollect() )
					{
						Intent intent = new Intent( mContext, BotiqueTip.class );
						mContext.startActivity( intent ) ;
					}
					mStatus = BotiqueShopManager.STATUS_RESTOCK ;
					invalidate() ;
				}
			}
		}
		return true ;
	}
	
	String getTimeString()
	{
		String ret = "" ;
		int hour = mTime / 3600 ;		
		int minute = ( mTime % 3600 ) / 60 ;
		int second = mTime % 60 ;
		
		if ( hour > 0 )
		{
			ret += hour ; ret += " h " ; ret += minute ; ret += " m" ;
		}
		else
		{
			ret += minute ; ret += " m " ; ret += second ; ret += " s" ;
		}
		return ret ;
	}

	@Override
	protected void onDraw( Canvas canvas ) 
	{
		// TODO Auto-generated method stub
		if ( destroyed )
			return ;
		
		if ( bitmap_none == null )
		{
			width = getWidth() ;
			height = getHeight() ;
			scale = width / 76.0f ;
			
			text_size = (int) ( 12 * scale ) ;
			t_paint.setTextSize( text_size ) ;
			
			//	finishnow
			finishnow_width 	= (int)( 70 * scale ) ;
			finishnow_height	= (int)( 30 * scale ) ;
			finishnow_top		= 60.0f * scale ;
			finishnow_left		= 3.0f * scale ;
			
			//	Done in
			donein_width 		= (int)( 76 * scale ) ;
			donein_height 		= (int)( 40 * scale ) ;
			donein01_top 		= 6.0f * scale ;
			donein01_left 		= 0.0f * scale ;
			
			//	Collect
			collect_width 		= (int)( 70 * scale ) ;
			collect_height 		= (int)( 40 * scale ) ;
			collect_top 		= 40.0f * scale ;
			collect_left 		= 3.0f * scale ;
			
			//	Restock
			restock_width 		= (int)( 70 * scale ) ;
			restock_height 		= (int)( 40 * scale ) ;
			restock_top			= 40.0f * scale ;
			restock_left 		= 3.0f * scale ;
			
			//	DoneIn
			donein02_top 		= 40.0f * scale ;
			donein02_left 		= 0.0f * scale ;
			
			bitmap_none = BitmapFactory.decodeResource( getResources(), R.drawable.nostoreyet, options ) ;
			
			if ( mStatus != BotiqueShopManager.STATUS_NONE )
			{
				bitmap_shop 		= BitmapFactory.decodeResource( getResources(), R.drawable.bs_02 + mShop, options ) ;
				
				bitmap_donein		= BitmapFactory.decodeResource( getResources(), R.drawable.donein, options ) ;
				
				bitmap_construct	= BitmapFactory.decodeResource( getResources(), R.drawable.underconstruction, options ) ;
				
				bitmap_open01	= BitmapFactory.decodeResource( getResources(), R.drawable.openbutton_1, options ) ;				
				bitmap_open02	= BitmapFactory.decodeResource( getResources(), R.drawable.openbutton_2, options ) ;
				
				if ( mShop % 3 == 0 )
				{
					bitmap_woman01		= BitmapFactory.decodeResource( getResources(), R.drawable.women_a1, options ) ;
					bitmap_woman02		= BitmapFactory.decodeResource( getResources(), R.drawable.women_a2, options ) ;
					bitmap_woman03		= BitmapFactory.decodeResource( getResources(), R.drawable.women_a3, options ) ;
					bitmap_woman04		= BitmapFactory.decodeResource( getResources(), R.drawable.women_a4, options ) ;
				}
				else if ( mShop % 3 == 1 )
				{
					bitmap_woman01		= BitmapFactory.decodeResource( getResources(), R.drawable.women_b1, options ) ;
					bitmap_woman02		= BitmapFactory.decodeResource( getResources(), R.drawable.women_b2, options ) ;
					bitmap_woman03		= BitmapFactory.decodeResource( getResources(), R.drawable.women_b3, options ) ;
					bitmap_woman04		= BitmapFactory.decodeResource( getResources(), R.drawable.women_b4, options ) ;
				}
				else
				{
					bitmap_woman01		= BitmapFactory.decodeResource( getResources(), R.drawable.women_c1, options ) ;
					bitmap_woman02		= BitmapFactory.decodeResource( getResources(), R.drawable.women_c2, options ) ;
					bitmap_woman03		= BitmapFactory.decodeResource( getResources(), R.drawable.women_c3, options ) ;
					bitmap_woman04		= BitmapFactory.decodeResource( getResources(), R.drawable.women_c4, options ) ;
				}
				
				bitmap_finishnow	= BitmapFactory.decodeResource( getResources(), R.drawable.finisihnow, options ) ;
				
				bitmap_collect		= BitmapFactory.decodeResource( getResources(), R.drawable.collect, options ) ;
				
				bitmap_restock		= BitmapFactory.decodeResource( getResources(), R.drawable.restock, options ) ;
			}
		}
		if ( mStatus == BotiqueShopManager.STATUS_NONE )
		{
			Rect none_rect = new Rect( 0, 0, width, height ) ;
			canvas.drawBitmap( bitmap_none, null, none_rect, paint ) ;
		}
		else if ( mStatus == BotiqueShopManager.STATUS_CONSTRUCT )
		{
			Rect cons_rect = new Rect( 0, 0, width, height ) ;
			canvas.drawBitmap( bitmap_construct, null, cons_rect, paint ) ;
			Rect finish_rect = new Rect( (int)finishnow_left, (int)finishnow_top, (int)( finishnow_left + finishnow_width ), (int)( finishnow_top + finishnow_height ) ) ;
			canvas.drawBitmap( bitmap_finishnow, null, finish_rect, paint ) ;
			if ( mShowTimer )
			{
				Rect donein01_rect = new Rect( (int)donein01_left, (int)donein01_top, (int)( donein01_left + donein_width ), (int)( donein01_top + donein_height ) ) ;
				canvas.drawBitmap( bitmap_donein, null, donein01_rect, paint ) ;
				canvas.drawText( getTimeString(), width / 2, donein01_top + donein_height / 2 + text_size, t_paint ) ;
			}
		}
		else if ( mStatus == BotiqueShopManager.STATUS_OPEN )
		{
			Rect rect = new Rect( 0, 0, width, height ) ;
			if ( mOpenFlag )
			{ 
				canvas.drawBitmap( bitmap_open01, null, rect, paint ) ;
			}
			else
			{
				canvas.drawBitmap( bitmap_open02, null, rect, paint ) ;
			}
		}
		else if ( mStatus == BotiqueShopManager.STATUS_RESTOCK )
		{
			Rect shop_rect = new Rect( 0, 0, width, height ) ;
			canvas.drawBitmap( bitmap_shop, null, shop_rect, paint ) ;
			Rect restock_rect = new Rect( (int)( restock_left ), (int)( restock_top ), (int)( restock_left + restock_width ), (int)( restock_top + restock_height ) ) ;
			canvas.drawBitmap( bitmap_restock, null, restock_rect, paint ) ;
		}
		else if ( mStatus == BotiqueShopManager.STATUS_RUN )
		{
			Rect shop_rect = new Rect( 0, 0, width, height ) ;
			canvas.drawBitmap( bitmap_shop, null, shop_rect, paint ) ;
			Bitmap avatar = null ;
			float avatar_pos = 0.0f ;
			if ( avatar_step < 10 )
			{
				if ( avatar_step % 2 == 0 )
					avatar = bitmap_woman01 ;
				else
					avatar = bitmap_woman02 ;
				avatar_pos = width * 2 / 10 * avatar_step - width ;
			}
			else
			{
				if ( avatar_step % 2 == 0 )
					avatar = bitmap_woman03 ;
				else
					avatar = bitmap_woman04 ;
				avatar_pos = width * 2 / 10 * ( 20 - avatar_step ) - width ;
			}
			Rect avatar_rect = new Rect( (int)( avatar_pos ), 0 , (int)( avatar_pos + width ), height ) ;
			canvas.drawBitmap( avatar, null, avatar_rect, paint ) ;
			if ( mShowTimer )
			{
				Rect donein02_rect = new Rect( (int)donein02_left, (int)donein02_top, (int)( donein02_left + donein_width ), (int)( donein02_top + donein_height ) ) ;
				canvas.drawBitmap( bitmap_donein, null, donein02_rect, paint ) ;
				canvas.drawText( getTimeString(), width / 2, donein02_top + donein_height / 2 + text_size, t_paint ) ;
			}
		}
		else if ( mStatus == BotiqueShopManager.STATUS_COLLECT )
		{
			Rect shop_rect = new Rect( 0, 0, width, height ) ;
			canvas.drawBitmap( bitmap_shop, null, shop_rect, paint ) ;
			Rect col_rect = new Rect( (int)collect_left, (int)collect_top, (int)( collect_left + collect_width ), (int)( collect_top + collect_height ) ) ;
			canvas.drawBitmap( bitmap_collect, null, col_rect, paint ) ;
		}
	}
	
	Handler mTimer = new Handler()
	{
		@Override
		public void handleMessage( Message msg )
		{
			// TODO Auto-generated method stub
			if ( mTime > 0 )
			{
				if ( !isShowingDialog )
				{
					mTime-- ;
				}
				if ( mShowTimer ) invalidate() ;
				sendEmptyMessageDelayed( 0, 1000 ) ;
			}
			else
			{
				if ( mStatus == BotiqueShopManager.STATUS_CONSTRUCT )
				{
					mStatus = BotiqueShopManager.STATUS_OPEN ;
					mOpenHandler.sendEmptyMessage( 0 ) ;
				}
				else if ( mStatus == BotiqueShopManager.STATUS_RUN )
				{
					mStatus = BotiqueShopManager.STATUS_COLLECT ;
					invalidate() ;
				}
			}
		}
	} ;
	
	Handler mOpenHandler = new Handler()
	{
		@Override
		public void handleMessage( Message msg )
		{
			// TODO Auto-generated method stub
			if ( mStatus == BotiqueShopManager.STATUS_OPEN )
			{
				mOpenFlag = !mOpenFlag ;
				invalidate() ;
				sendEmptyMessageDelayed( 0, 500 ) ;
			}
		}
	} ;
	
	Handler mRunHandler = new Handler()
	{
		@Override
		public void handleMessage( Message msg )
		{
			// TODO Auto-generated method stub
			if ( mStatus == BotiqueShopManager.STATUS_RUN )
			{
				avatar_step ++ ;
				if ( avatar_step > 20 )
					avatar_step = 0 ;
				invalidate() ;
				sendEmptyMessageDelayed( 0, 600 ) ;
			}
		}
	} ;
	
	private void showFinishDialog()
	{
		isShowingDialog = true ;
		String message = "Would you like to instant build the shop now for " + FabLifeApplication.getBotiqueShopManager().getConstructFinish( mShop ) + " cash?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( mContext ) ;
		builder.setMessage( message )
	    .setCancelable( false )
	    .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
	    {
	    	public void onClick( DialogInterface dialog, int id )
	    	{
	    		mTime = 0 ;
	    		FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() - FabLifeApplication.getBotiqueShopManager().getConstructFinish( mShop ) ) ;
	    		mHandler.sendEmptyMessage( BotiqueMain.MESSAGE_FINISH_CONSTRUCTION ) ;
	    		isShowingDialog = false ;
	        }
	    })
		.setNegativeButton( "No", new DialogInterface.OnClickListener() 
		{
			public void onClick( DialogInterface dialog, int id )
	    	{
				isShowingDialog = false ;
	        }
	    }) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	public void clear()
	{
		destroyed = true ;
		if ( bitmap_none != null ) 		{ if ( !bitmap_none.isRecycled() ) bitmap_none.recycle() ; bitmap_none = null ; }
		if ( bitmap_shop != null ) 		{ if ( !bitmap_shop.isRecycled() ) bitmap_shop.recycle() ; bitmap_shop = null ; }
		if ( bitmap_donein != null ) 	{ if ( !bitmap_donein.isRecycled() ) bitmap_donein.recycle() ; bitmap_donein = null ; }
		if ( bitmap_construct != null ) { if ( !bitmap_construct.isRecycled() ) bitmap_construct.recycle() ; bitmap_construct = null ; }
		if ( bitmap_open01 != null ) 	{ if ( !bitmap_open01.isRecycled() ) bitmap_open01.recycle() ; bitmap_open01 = null ; }
		if ( bitmap_open02 != null ) 	{ if ( !bitmap_open02.isRecycled() ) bitmap_open02.recycle() ; bitmap_open02 = null ; }
		if ( bitmap_woman01 != null ) 	{ if ( !bitmap_woman01.isRecycled() ) bitmap_woman01.recycle() ; bitmap_woman01 = null ; }
		if ( bitmap_woman02 != null ) 	{ if ( !bitmap_woman02.isRecycled() ) bitmap_woman02.recycle() ; bitmap_woman02 = null ; }
		if ( bitmap_woman03 != null ) 	{ if ( !bitmap_woman03.isRecycled() ) bitmap_woman03.recycle() ; bitmap_woman03 = null ; }
		if ( bitmap_woman04 != null ) 	{ if ( !bitmap_woman04.isRecycled() ) bitmap_woman04.recycle() ; bitmap_woman04 = null ; }
		if ( bitmap_finishnow != null ) { if ( !bitmap_finishnow.isRecycled() ) bitmap_finishnow.recycle() ; bitmap_finishnow = null ; }
		if ( bitmap_collect != null ) 	{ if ( !bitmap_collect.isRecycled() ) bitmap_collect.recycle() ; bitmap_collect = null ; }
		if ( bitmap_restock != null ) 	{ if ( !bitmap_restock.isRecycled() ) bitmap_restock.recycle() ; bitmap_restock = null ; }
	}
}
