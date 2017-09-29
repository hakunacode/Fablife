package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Activity.Shop;
import com.miinu.FabLife.Engine.ShopManager;
import com.miinu.FabLife.Engine.ShopManager.Item;
import android.content.Context;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.Scroller;
import android.graphics.*;

public class ShopItemView extends SurfaceView implements View.OnTouchListener, SurfaceHolder.Callback
{
	Context			mCtx 			= null ;
	
	int 			width 			= 280 ;
	int 			height 			= 280 ;
	public float	first_pos 		= 0.0f ;
	Paint 			paint 			= null ;
	Paint			tpaint			= null ;
	
	ShopManager		mManager		= null ;
	public int		startIndex		= 0 ;
	float			scale			= 1.0f ;
	int 			text_size		= 14 ;
	
	SurfaceHolder	mHolder			= null ;
	
	float 			box_x 			= 0.0f ;
	float 			box_y 			= 0.0f ;
	int 			box_width 		= 0 ;
	int 			box_height 		= 0 ;
	
	float 			experience_x 	= 0.0f ;
	float 			experience_y 	= 0.0f ;
	
	float 			buy_x	 		= 0.0f ;
	float 			buy_y	 		= 0.0f ;
	int 			buy_width 		= 0 ;
	int 			buy_height 		= 0 ;
	
	float 			coin_x			= 0.0f ;
	float 			coin_y			= 0.0f ;
	int 			coin_width		= 0 ;
	int 			coin_height		= 0 ;
	
	float 			cash_x			= 0.0f ;
	float 			cash_y			= 0.0f ;
	int 			cash_width		= 0 ;
	int 			cash_height		= 0 ;
	
	float 			price_x			= 0.0f ;
	float 			price_y			= 0.0f ;
	
	int 			item_x			= 0 ;
	int 			item_y			= 0 ;
	int 			item_width		= 0 ;
	int 			item_height		= 0 ;
	
	float 			try_x			= 0.0f ;
	float 			try_y			= 0.0f ;
	int 			try_width		= 0 ;
	int 			try_height		= 0 ;
	
	float 			picker_x		= 0.0f ;
	float 			picker_y		= 0.0f ;
	int 			picker_width	= 0 ;
	int 			picker_height	= 0 ;
	
	int 			x_diff 			= 0 ;
	int 			y_diff 			= 0 ;
	
	float 			bg_x 			= 0.0f ;
	float 			bg_y			= 0.0f ;
	int 			bg_width		= 0 ;
	int				bg_height		= 0 ;
	
	Bitmap 			box				= null ;
	Bitmap 			buy 			= null ;
	Bitmap 			coin			= null ;
	Bitmap 			cash			= null ;
	Bitmap 			try_bitmap		= null ;
	Bitmap 			picker			= null ;
	
	Bitmap 			prev_bitmap 	= null ;
	Bitmap 			cur_bitmap		= null ;
	Bitmap 			next_bitmap		= null ;
	
	Bitmap 			background		= null ;
	
	Handler 		mHandler		= null ;
	
	BitmapFactory.Options options	= new BitmapFactory.Options() ;
	
	public ShopItemView( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		// TODO Auto-generated constructor stub
		mCtx = context ;
		
		mHolder = getHolder() ;
		mHolder.addCallback( this ) ;
		mHolder.setType( SurfaceHolder.SURFACE_TYPE_NORMAL ) ;
		mHolder.setFormat( PixelFormat.TRANSPARENT ) ;
		
		paint = new Paint() ; paint.setAntiAlias( true ) ; paint.setFilterBitmap( true ) ;
		
		Typeface face = Typeface.createFromAsset( mCtx.getAssets(), "font/abeatbykai.ttf" ) ;
		tpaint = new Paint() ; tpaint.setAntiAlias( true ) ; tpaint.setColor( Color.BLACK ) ;
		tpaint.setTypeface( face ) ; tpaint.setTextSize( text_size ) ;
		
		setOnTouchListener( this ) ;
	}
	
	void setScale( float s )
	{
		scale = s ; 
		
		width = (int)( 280 * scale ) ;
		height = (int)( 280 * scale ) ;
		
		text_size = (int) ( text_size * scale ) ;
		
		box_x 			= 5 * scale ;
		box_y 			= 5 * scale ;
		box_width 		= (int)( 130 * scale ) ;
		box_height 		= (int)( 130 * scale ) ;
		
		experience_x 	= 10 * scale ;
		experience_y 	= 15 * scale + text_size / 2 ;
		
		buy_x	 		= 45 * scale ;
		buy_y	 		= 10 * scale ;
		buy_width 		= (int)( 50 * scale ) ;
		buy_height 		= (int)( 20 * scale ) ;
		
		coin_x			= 120 * scale ;
		coin_y			= 10 * scale ;
		coin_width		= (int)( 10 * scale ) ;
		coin_height		= (int)( 10 * scale ) ;
		
		cash_x			= 110 * scale ;
		cash_y			= 10 * scale ;
		cash_width		= (int)( 20 * scale ) ;
		cash_height		= (int)( 10 * scale ) ;
		
		price_x			= 130 * scale ;
		price_y			= 25 * scale + text_size / 2 ;
		
		item_x			= (int)( 20 * scale ) ;
		item_y			= (int)( 24 * scale ) ;
		item_width		= (int)( 100 * scale ) ;
		item_height		= (int)( 100 * scale ) ;
		
		try_x			= 106 * scale ;
		try_y			= 106 * scale ;
		try_width		= (int)( 24 * scale ) ;
		try_height		= (int)( 24 * scale ) ;
		
		picker_x		= 5 * scale ;
		picker_y		= 115 * scale ;
		picker_width	= (int)( 20 * scale ) ;
		picker_height	= (int)( 20 * scale ) ;
		
		x_diff 			= (int)( 140 * scale ) ;
		y_diff 			= (int)( 140 * scale ) ;
		
		bg_x 			= - 20 * scale ;
		bg_y			= - 110 * scale ;
		bg_width		= (int)( 320 * scale ) ;
		bg_height		= (int)( 430 * scale ) ;
		
		box = BitmapFactory.decodeResource( mCtx.getResources(), R.drawable.item_box, options ) ;
		box = Bitmap.createScaledBitmap( box, box_width, box_height, true ) ;
		buy = BitmapFactory.decodeResource( mCtx.getResources(), R.drawable.shop_buy_btn, options ) ;
		buy = Bitmap.createScaledBitmap( buy, buy_width, buy_height, true ) ;
		coin =  BitmapFactory.decodeResource( getResources(), R.drawable.coin_icon, options ) ;
		coin = Bitmap.createScaledBitmap( coin, coin_width, coin_height, true ) ;
		cash =  BitmapFactory.decodeResource( getResources(), R.drawable.cash_icon, options ) ;
		cash = Bitmap.createScaledBitmap( cash, cash_width, cash_height, true ) ;
		try_bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.shop_item_try, options ) ;
		try_bitmap = Bitmap.createScaledBitmap( try_bitmap, try_width, try_height, true ) ;
		picker = BitmapFactory.decodeResource( getResources(), R.drawable.shop_hair_picker, options ) ;
		picker = Bitmap.createScaledBitmap( picker, picker_width, picker_height, true ) ;
		background = Bitmap.createScaledBitmap( background, bg_width, bg_height, true ) ;
	}
	
	public void setBackground( Bitmap bitmap )
	{
		background = bitmap ;
	}
	
	public void setHandler( Handler h )
	{
		mHandler = h ;
	}
	
	public void setShopManager( ShopManager mgr )
	{
		mManager = mgr ;
		getPrevBitmap() ;
		getCurBitmap() ;
		getNextBitmap() ;
		draw() ;
	}
	
	void getPrevBitmap()
	{
		if ( prev_bitmap == null )
		{
			prev_bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 ) ;
		}
		else
		{
			prev_bitmap.eraseColor( Color.TRANSPARENT ) ;
		}
		
		Canvas c = new Canvas( prev_bitmap ) ;
		
		drawFirstItem( c, startIndex - 4 ) ;
		drawSecondItem( c, startIndex - 3 ) ;
		drawThirdItem( c, startIndex - 2 ) ;
		drawFourthItem( c, startIndex - 1 ) ;
	}
	
	void getCurBitmap()
	{
		if ( cur_bitmap == null )
		{
			cur_bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 ) ;
		}
		else
		{
			cur_bitmap.eraseColor( Color.TRANSPARENT ) ;
		}
		
		Canvas c = new Canvas( cur_bitmap ) ;
		
		drawFirstItem( c, startIndex ) ;
		drawSecondItem( c, startIndex + 1 ) ;
		drawThirdItem( c, startIndex + 2 ) ;
		drawFourthItem( c, startIndex + 3 ) ;
	}
	
	void getNextBitmap()
	{
		if ( next_bitmap == null )
		{
			next_bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 ) ;
		}
		else
		{
			next_bitmap.eraseColor( Color.TRANSPARENT ) ;
		}
		
		Canvas c = new Canvas( next_bitmap ) ;
		
		drawFirstItem( c, startIndex + 4 ) ;
		drawSecondItem( c, startIndex + 5 ) ;
		drawThirdItem( c, startIndex + 6 ) ;
		drawFourthItem( c, startIndex + 7 ) ;
	}
	
	public void getBitmap() throws Exception
	{
		try
		{
			getPrevBitmap() ;
			getCurBitmap() ;
			getNextBitmap() ;
			System.gc() ;
		}
		catch( Exception e )
		{
			e.printStackTrace() ;
		}
	}
	
	public void draw()
	{
		// TODO Auto-generated method stub
		Canvas c = null ;
		try
		{
			c = mHolder.lockCanvas( null ) ;
	 		synchronized ( mHolder )
	 		{
	 			c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
	 			if ( background != null && !background.isRecycled() )
	 				c.drawBitmap( background, bg_x, bg_y, paint ) ;
	 			if ( prev_bitmap != null && !prev_bitmap.isRecycled() )
	 				c.drawBitmap( prev_bitmap, first_pos - width, 0, paint ) ;
	 			if ( cur_bitmap != null && !cur_bitmap.isRecycled() )
	 				c.drawBitmap( cur_bitmap, first_pos, 0, paint ) ;
	 			if ( next_bitmap != null && !next_bitmap.isRecycled() )
	 				c.drawBitmap( next_bitmap, first_pos + width, 0, paint ) ;
	 		}//sync
		}
		finally
		{
			if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; } 
		}
	}
	
	void drawFirstItem( Canvas c, int index )
	{
		if ( index >= 0 && index < mManager.getCount() )
		{
			Item item = mManager.getItemByIndex( index ) ;
			
			c.drawBitmap( box, box_x, box_y, paint ) ;
			
			Bitmap bitmap = item.bitmap ;
			if ( bitmap.getWidth() > bitmap.getHeight() )
			{
				int h = (int)( bitmap.getHeight() * item_width / bitmap.getWidth() ) ;
				int diff = (int)( ( item_height - h ) / 2 ) ;
				
				Rect dst = new Rect( item_x, item_y + diff, item_x + item_width, item_y + diff + h ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			else
			{
				int w = (int)( bitmap.getWidth() * item_height / bitmap.getHeight() ) ;
				int diff = (int)( ( item_width - w ) / 2 ) ;
			
				Rect dst = new Rect( item_x + diff, item_y, item_x + diff + w, item_y + item_height ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			
			tpaint.setTextAlign( Align.LEFT ) ;
			c.drawText( "" + item.experience + "XP", experience_x, experience_y, tpaint ) ;
			
			c.drawBitmap( buy, buy_x, buy_y, paint ) ;
			
			if ( item.pricetype == 1 )
			{
				c.drawBitmap( coin, coin_x, coin_y, paint ) ;
			}
			else
			{
				c.drawBitmap( cash, cash_x, cash_y, paint ) ;
			}
			
			tpaint.setTextAlign( Align.RIGHT ) ;
			c.drawText( "" + item.price, price_x, price_y, tpaint ) ;
	
			c.drawBitmap( try_bitmap, try_x, try_y, paint ) ;
			
			if ( item.isHair )
			{
				c.drawBitmap( picker, picker_x, picker_y, paint ) ;
			}
		}
	}
	
	void drawSecondItem( Canvas c, int index )
	{
		if ( index >= 0 && index < mManager.getCount() )
		{
			Item item = mManager.getItemByIndex( index ) ;
			
			c.drawBitmap( box, box_x, y_diff + box_y, paint ) ;
			
			Bitmap bitmap = item.bitmap ;
			if ( bitmap.getWidth() > bitmap.getHeight() )
			{
				int h = (int)( bitmap.getHeight() * item_width / bitmap.getWidth() ) ;
				int diff = (int)( ( item_height - h ) / 2 ) ;
				
				Rect dst = new Rect( item_x, y_diff + item_y + diff, item_x + item_width, y_diff + item_y + diff + h ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			else
			{
				int w = (int)( bitmap.getWidth() * item_height / bitmap.getHeight() ) ;
				int diff = (int)( ( item_width - w ) / 2 ) ;
			
				Rect dst = new Rect( item_x + diff, y_diff + item_y, item_x + diff + w, y_diff + item_y + item_height ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			
			tpaint.setTextAlign( Align.LEFT ) ;
			c.drawText( "" + item.experience + "XP", experience_x, y_diff + experience_y, tpaint ) ;
			
			c.drawBitmap( buy, buy_x, y_diff + buy_y, paint ) ;
			
			if ( item.pricetype == 1 )
			{
				c.drawBitmap( coin, coin_x, y_diff + coin_y, paint ) ;
			}
			else
			{
				c.drawBitmap( cash, cash_x, y_diff + cash_y, paint ) ;
			}
			
			tpaint.setTextAlign( Align.RIGHT ) ;
			c.drawText( "" + item.price, price_x, y_diff + price_y, tpaint ) ;

			c.drawBitmap( try_bitmap, try_x, y_diff + try_y, paint ) ;
			
			if ( item.isHair )
			{
				c.drawBitmap( picker, picker_x, y_diff + picker_y, paint ) ;
			}
		}
	}
	
	void drawThirdItem( Canvas c, int index )
	{
		if ( index >= 0 && index < mManager.getCount() )
		{
			Item item = mManager.getItemByIndex( index ) ;
			
			c.drawBitmap( box, x_diff + box_x, box_y, paint ) ;
			
			Bitmap bitmap = item.bitmap ;
			if ( bitmap.getWidth() > bitmap.getHeight() )
			{
				int h = (int)( bitmap.getHeight() * item_width / bitmap.getWidth() ) ;
				int diff = (int)( ( item_height - h ) / 2 ) ;
				
				Rect dst = new Rect( x_diff + item_x, item_y + diff, x_diff + item_x + item_width, item_y + diff + h ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			else
			{
				int w = (int)( bitmap.getWidth() * item_height / bitmap.getHeight() ) ;
				int diff = (int)( ( item_width - w ) / 2 ) ;
			
				Rect dst = new Rect( x_diff + item_x + diff, item_y, x_diff + item_x + diff + w, item_y + item_height ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			
			tpaint.setTextAlign( Align.LEFT ) ;
			c.drawText( "" + item.experience + "XP", x_diff + experience_x, experience_y, tpaint ) ;
			
			c.drawBitmap( buy, x_diff + buy_x, buy_y, paint ) ;
			
			if ( item.pricetype == 1 )
			{
				c.drawBitmap( coin, x_diff + coin_x, coin_y, paint ) ;
			}
			else
			{
				c.drawBitmap( cash, x_diff + cash_x, cash_y, paint ) ;
			}
			
			tpaint.setTextAlign( Align.RIGHT ) ;
			c.drawText( "" + item.price, x_diff + price_x, price_y, tpaint ) ;
	
			c.drawBitmap( try_bitmap, x_diff + try_x, try_y, paint ) ;
			
			if ( item.isHair )
			{
				c.drawBitmap( picker, x_diff + picker_x, picker_y, paint ) ;
			}
		}
	}
	
	void drawFourthItem( Canvas c, int index )
	{
		if ( index >= 0 && index < mManager.getCount() )
		{
			Item item = mManager.getItemByIndex( index ) ;
			
			c.drawBitmap( box, x_diff + box_x, y_diff + box_y, paint ) ;
			
			Bitmap bitmap = item.bitmap ;
			if ( bitmap.getWidth() > bitmap.getHeight() )
			{
				int h = (int)( bitmap.getHeight() * item_width / bitmap.getWidth() ) ;
				int diff = (int)( ( item_height - h ) / 2 ) ;
				
				Rect dst = new Rect( x_diff + item_x, y_diff + item_y + diff, x_diff + item_x + item_width, y_diff + item_y + diff + h ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			else
			{
				int w = (int)( bitmap.getWidth() * item_height / bitmap.getHeight() ) ;
				int diff = (int)( ( item_width - w ) / 2 ) ;
			
				Rect dst = new Rect( x_diff + item_x + diff, y_diff + item_y, x_diff + item_x + diff + w, y_diff + item_y + item_height ) ;
				c.drawBitmap( bitmap, null, dst, paint ) ;
				bitmap.recycle() ; bitmap = null ;
			}
			
			tpaint.setTextAlign( Align.LEFT ) ;
			c.drawText( "" + item.experience + "XP", x_diff + experience_x, y_diff + experience_y, tpaint ) ;
			
			c.drawBitmap( buy, x_diff + buy_x, y_diff + buy_y, paint ) ;
			
			if ( item.pricetype == 1 )
			{
				c.drawBitmap( coin, x_diff + coin_x, y_diff + coin_y, paint ) ;
			}
			else
			{
				c.drawBitmap( cash, x_diff + cash_x, y_diff + cash_y, paint ) ;
			}
			
			tpaint.setTextAlign( Align.RIGHT ) ;
			c.drawText( "" + item.price, x_diff + price_x, y_diff + price_y, tpaint ) ;

			c.drawBitmap( try_bitmap, x_diff + try_x, y_diff + try_y, paint ) ;
			
			if ( item.isHair )
			{
				c.drawBitmap( picker, x_diff + picker_x, y_diff + picker_y, paint ) ;
			}
		}
	}
	
	Scroller scroller = null ;
	public int	mSelectedIndex = 0 ;
	float tmpx ;
	float tmpy ;
	@Override
	public boolean onTouch( View v, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( mManager == null )
			return true ;
		if ( isAnimation() )
		{
			return true ;
		}
		if ( event.getAction() == MotionEvent.ACTION_DOWN )
		{
			tmpx = event.getX() ;
			tmpy = event.getY() ;
			if ( tmpx >= buy_x && tmpx <= ( buy_x + buy_width ) && tmpy >= buy_y && tmpy <= ( buy_y + buy_height ) ) // buy button 01
			{
				if ( startIndex < mManager.getCount() )
				{
					mSelectedIndex = startIndex ;
					mHandler.sendEmptyMessage( Shop.BUY ) ;
				}
			}
			else if ( tmpx >= buy_x && tmpx <= ( buy_x + buy_width ) && tmpy >= ( y_diff + buy_y ) && tmpy <= ( y_diff + buy_y + buy_height ) ) // buy button 02
			{
				if ( ( startIndex + 1 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 1 ;
					mHandler.sendEmptyMessage( Shop.BUY ) ;
				}
			}
			else if ( tmpx >= ( x_diff + buy_x ) && tmpx <= ( x_diff + buy_x + buy_width ) && tmpy >= buy_y && tmpy <= ( buy_y + buy_height ) ) // buy button 03
			{
				if ( ( startIndex + 2 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 2 ;
					mHandler.sendEmptyMessage( Shop.BUY ) ;
				}
			}
			else if ( tmpx >= ( x_diff + buy_x ) && tmpx <= ( x_diff + buy_x + buy_width ) && tmpy >= ( y_diff + buy_y ) && tmpy <= ( y_diff + buy_y + buy_height ) ) // buy button 04
			{
				if ( ( startIndex + 3 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 3 ;
					mHandler.sendEmptyMessage( Shop.BUY ) ;
				}
			}
			
			if ( tmpx >= try_x && tmpx <= ( try_x + try_width ) && tmpy >= try_y && tmpy <= ( try_y + try_height ) ) // try button 01
			{
				if ( startIndex < mManager.getCount() )
				{
					mSelectedIndex = startIndex ;
					mHandler.sendEmptyMessage( Shop.TRY ) ;
				}
			}
			else if ( tmpx >= try_x && tmpx <= ( try_x + try_width ) && tmpy >= ( y_diff + try_y ) && tmpy <= ( y_diff + try_y + try_height ) ) // try button 02
			{
				if ( ( startIndex + 1 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 1 ;
					mHandler.sendEmptyMessage( Shop.TRY ) ;
				}
			}
			else if ( tmpx >= ( x_diff + try_x ) && tmpx <= ( x_diff + try_x + try_width ) && tmpy >= try_y && tmpy <= ( try_y + try_height ) ) // try button 03
			{
				if ( ( startIndex + 2 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 2 ;
					mHandler.sendEmptyMessage( Shop.TRY ) ;
				}
			}
			else if ( tmpx >= ( x_diff + try_x ) && tmpx <= ( x_diff + try_x + try_width ) && tmpy >= ( y_diff + try_y ) && tmpy <= ( y_diff + try_y + try_height ) ) // try button 04
			{
				if ( ( startIndex + 3 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 3 ;
					mHandler.sendEmptyMessage( Shop.TRY ) ;
				}
			}
			
			if ( tmpx >= picker_x && tmpx <= ( picker_x + picker_width ) && tmpy >= picker_y && tmpy <= ( picker_y + picker_height ) ) // picker button 01
			{
				if ( startIndex < mManager.getCount() )
				{
					mSelectedIndex = startIndex ;
					mHandler.sendEmptyMessage( Shop.PICKER ) ;
				}
			}
			else if ( tmpx >= picker_x && tmpx <= ( picker_x + picker_width ) && tmpy >= ( y_diff + picker_y ) && tmpy <= ( y_diff + picker_y + picker_height ) ) // picker button 02
			{
				if ( ( startIndex + 1 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 1 ;
					mHandler.sendEmptyMessage( Shop.PICKER ) ;
				}
			}
			else if ( tmpx >= ( x_diff + picker_x ) && tmpx <= ( x_diff + picker_x + picker_width ) && tmpy >= picker_y && tmpy <= ( picker_y + picker_height ) ) // picker button 03
			{
				if ( ( startIndex + 2 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 2 ;
					mHandler.sendEmptyMessage( Shop.PICKER ) ;
				}
			}
			else if ( tmpx >= ( x_diff + picker_x ) && tmpx <= ( x_diff + picker_x + picker_width ) && tmpy >= ( y_diff + picker_y ) && tmpy <= ( y_diff + picker_y + picker_height ) ) // picker button 04
			{
				if ( ( startIndex + 3 ) < mManager.getCount() )
				{
					mSelectedIndex = startIndex + 3 ;
					mHandler.sendEmptyMessage( Shop.PICKER ) ;
				}
			}
		}
		else if ( event.getAction() == MotionEvent.ACTION_MOVE )
		{
			first_pos = (int)( event.getX() - tmpx ) ;
			if ( startIndex == 0 )
			{
				if ( first_pos > 0 )
					first_pos = 0 ;
			}
			else if ( ( startIndex + 4 ) >= mManager.getCount() )
			{
				if ( first_pos < 0 )
					first_pos = 0 ;
			}
			draw() ;
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP )
		{
			if ( first_pos < - ( width / 2 ) )
			{
				if ( ( startIndex + 4 ) < mManager.getCount() )
				{
					pageUpAnim() ;
				}
			}
			else if ( first_pos >= - ( width / 2 ) && first_pos <= ( width / 2 ) )
			{
				float x_step = -( first_pos / 10 ) ;
				for ( int i = 0 ; i < 10 ; i ++ )
				{
					first_pos += x_step ;
					draw() ;
				}
			}
			else if ( first_pos > ( width / 2 ) )
			{
				if ( startIndex - 4 >= 0 )
				{
					pageDownAnim() ;
				}
			}
		}
		return true ;
	}
	
	public void pageUpAnim()
	{
		if ( scroller == null )
		{
			scroller = new Scroller( mCtx ) ;
		}
		scroller.startScroll( (int)first_pos, 0, -( width + (int)first_pos ), 0, 400 ) ;
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				if ( scroller != null )
		        {
		            scroller.computeScrollOffset() ;
		            first_pos = scroller.getCurrX() ;
		            draw() ;
		        }
				if ( scroller.isFinished() )
				{
					first_pos = 0.0f ;
					startIndex += 4 ;
					try
					{
						getBitmap() ;
					}
					catch( Exception e )
					{
						e.printStackTrace() ;
					}
					draw() ;
					mHandler.sendEmptyMessage( Shop.PAGE_UP ) ;
				}
				else
				{
					sendEmptyMessage( 0 ) ;
				}
			}
		} ;
		handler.sendEmptyMessage( 0 ) ;
	}
	
	public void pageDownAnim()
	{
		if ( scroller == null )
		{
			scroller = new Scroller( mCtx ) ;
		}
		scroller.startScroll( (int)first_pos, 0, width - (int)first_pos, 0, 400 ) ;
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				if ( scroller != null )
		        {
		            scroller.computeScrollOffset() ;
		            first_pos = scroller.getCurrX() ;
		            draw() ;
		        }
				if ( scroller.isFinished() )
				{
					first_pos = 0.0f ;
					startIndex -= 4 ;
					try
					{
						getBitmap() ;
					}
					catch( Exception e )
					{
						e.printStackTrace() ;
					}
					draw() ;
					mHandler.sendEmptyMessage( Shop.PAGE_DOWN ) ;
				}
				else
				{
					sendEmptyMessage( 0 ) ;
				}
			}
		} ;
		handler.sendEmptyMessage( 0 ) ;
	}

	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
		float s = getWidth() / 280.0f ;
		setScale( s ) ;
		draw() ;
	}

	@Override
	public void surfaceDestroyed( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
		
	}
	
	public void clear() throws Exception
	{
		if ( box != null ) { if ( !box.isRecycled() ) box.recycle() ; box = null ; }
		if ( buy != null ) { if ( !buy.isRecycled() ) buy.recycle() ; buy = null ; }
		if ( coin != null ) { if ( !coin.isRecycled() ) coin.recycle() ; coin = null ; }
		if ( cash != null ) { if ( !cash.isRecycled() ) cash.recycle() ; cash = null ; }
		if ( try_bitmap != null ) { if ( !try_bitmap.isRecycled() ) try_bitmap.recycle() ; try_bitmap = null ; }
		if ( picker != null ) { if ( !picker.isRecycled() ) picker.recycle() ; picker = null ; }
		
		if ( prev_bitmap != null ) { if ( !prev_bitmap.isRecycled() ) prev_bitmap.recycle() ; prev_bitmap = null ; }
		if ( cur_bitmap != null ) { if ( !cur_bitmap.isRecycled() ) cur_bitmap.recycle() ; cur_bitmap = null ; }
		if ( next_bitmap != null ) { if ( !next_bitmap.isRecycled() ) next_bitmap.recycle() ; next_bitmap = null ; }
		if ( background != null ) { if ( !background.isRecycled() ) background.recycle() ; background = null ; }
		System.gc() ;
	}
	
	public boolean isAnimation()
	{
		if ( scroller != null && !scroller.isFinished() )
			return true ;
		else
			return false ;
	}
}
