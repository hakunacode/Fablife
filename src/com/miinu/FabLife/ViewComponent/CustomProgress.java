package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class CustomProgress extends View 
{
	Bitmap				box			= null ;
	Bitmap 				icon 		= null	;
	int 				percent 	= 0 ;
	int 				filled_icon = 0 ;
	int 				step 		= 0 ;
	
	Handler				mHandler 	= null ;
	
	public CustomProgress( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		BitmapFactory.Options options = new BitmapFactory.Options() ;
		options.inPurgeable = true ;
		icon = BitmapFactory.decodeResource( getResources(), R.drawable.myjob_heart_empty, options ) ;
		box = BitmapFactory.decodeResource( getResources(), R.drawable.box, options ) ;
	}
	
	public void setStep( int s )
	{
		step = s ;
	}
	
	private Bitmap get_rating( Bitmap src, int index )
	{
		int p = percent - ( filled_icon ) * 100 ;
		Bitmap dst = Bitmap.createBitmap( src ) ;
		if ( index == filled_icon )
		{
			int width = src.getWidth() ;
			int height = src.getHeight() ;
			for ( int h = 0 ; h < height ; h++ )
			{
				for ( int w = 0 ; w < width ; w++ )
				{
					int color = src.getPixel( w, h ) ;
					if ( w < width * p / 100 )
					{
						if ( color != 0 )
						{
							int alpha = ( color >> 24 ) & 0xFF ;
							int rating_color = getResources().getColor( R.color.rating_color ) ;
							rating_color = ( alpha << 24 ) + rating_color ;
							dst.setPixel( w, h, rating_color ) ;
						}
					}
				}
			}
		}
		else if ( index < filled_icon )
		{
			int width = src.getWidth() ;
			int height = src.getHeight() ;
			for ( int h = 0 ; h < height ; h++ )
			{
				for ( int w = 0 ; w < width ; w++ )
				{
					int color = src.getPixel( w, h ) ;
					if ( color != 0 )
					{
						int alpha = ( color >> 24 ) & 0xFF ;
						int rating_color = getResources().getColor( R.color.rating_color ) ;
						rating_color = ( alpha << 24 ) + rating_color ;
						dst.setPixel( w, h, rating_color ) ;
					}
				}
			}
		}
		return dst ;
	}
	
	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub		
		int p = percent - ( filled_icon ) * 100 ;
		if ( filled_icon == 3 )
		{
			p = 100 ;
		}
		
		int height = getHeight() ;
		int width = getWidth() ;
		
		int rect_height = height / 3 ;
		int rect_width = width ;
		Paint rect_paint = new Paint() ; rect_paint.setAntiAlias( true ) ; rect_paint.setColor( Color.BLACK ) ;
		rect_paint.setFilterBitmap( true ) ; rect_paint.setStyle( Style.STROKE ) ;
		Bitmap box_bitmap = Bitmap.createScaledBitmap( box, rect_width, rect_height, true ) ;
		canvas.drawBitmap( box_bitmap, 0, 0, rect_paint ) ;
		box_bitmap.recycle() ; box_bitmap = null ;
		int rating_color = getResources().getColor( R.color.rating_color ) ;
		rect_paint.setColor( rating_color ) ; rect_paint.setStyle( Style.FILL ) ;
		canvas.drawRect( 1, 1, rect_width * p / 100 - 1 , rect_height - 1, rect_paint ) ;
		
		int icon_width = ( width - 20 ) / 3 ;
		int icon_height = icon.getHeight() * icon_width / icon.getWidth() ;
		Paint icon_paint = new Paint() ; icon_paint.setAntiAlias( true ) ; icon_paint.setFilterBitmap( true ) ;
		Bitmap scaled_icon = Bitmap.createScaledBitmap( icon, icon_width, icon_height, true ) ;
		Bitmap scaled_icon01 = get_rating( scaled_icon, 0 ) ;
		Bitmap scaled_icon02 = get_rating( scaled_icon, 1 ) ;
		Bitmap scaled_icon03 = get_rating( scaled_icon, 2 ) ;
		canvas.drawBitmap( scaled_icon01, 5 , height - icon_height - 5, icon_paint ) ;
		canvas.drawBitmap( scaled_icon02, 10 + icon_width , height - icon_height - 5, icon_paint ) ;
		canvas.drawBitmap( scaled_icon03, 15 + 2 * icon_width, height - icon_height - 5, icon_paint ) ;
		
		scaled_icon.recycle() ; scaled_icon = null ;
		scaled_icon01.recycle() ; scaled_icon01 = null ;
		scaled_icon02.recycle() ; scaled_icon02 = null ;
		scaled_icon03.recycle() ; scaled_icon03 = null ;
	}
	
	public void advance()
	{
		if ( getPercent() >= 300 )
		{
			return ;
		}
		else
		{
			int icons = ( getPercent() + step ) / 100 ;
			if ( ( icons - filled_icon ) == 1 )
			{
				if ( mHandler != null )
					mHandler.sendEmptyMessage( icons ) ;
			}
			setPercent( getPercent() + step ) ;
			filled_icon = getPercent() / 100 ;
			invalidate() ;
		}
	}
	
	public void setPercent( int p )
	{
		percent = p ;
		filled_icon = percent / 100 ;
	}
	
	public int getPercent()
	{
		return percent ;
	}
	
	public void setHandler( Handler h )
	{
		mHandler = h ;
	}
}
