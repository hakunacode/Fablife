package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class StatusBarCash extends View 
{
	private Bitmap 				mIcon 			= null ;
	private int 				mnTextSize 		= 18 ;
	private Typeface 			mTypeFace		= null ;
	
	private int					mnCash			= 0 ;
	
	public StatusBarCash( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		mIcon 		= BitmapFactory.decodeResource( getResources(), R.drawable.home_cash ) ;
		mTypeFace 	= Typeface.createFromAsset( context.getAssets(), "font/Big Bimbo NC.ttf" ) ;
	}
	
	public void setCash( int cash )
	{
		mnCash = cash ;
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		int height = getHeight() ;
		int width = getWidth() ;
		
		Rect bitmap_rect = new Rect( 0, 0, width, height ) ;
		Paint icon_paint = new Paint() ; icon_paint.setAntiAlias( true ) ; icon_paint.setFilterBitmap( true ) ;
		canvas.drawBitmap( mIcon, null, bitmap_rect, icon_paint ) ;
		
		mnTextSize = (int) ( height * 0.4 ) ;
		String text = "" + mnCash ;
		Paint t_paint = new Paint() ; t_paint.setColor( Color.BLACK ) ; t_paint.setTextSize( mnTextSize ) ;
		t_paint.setAntiAlias( true ) ; t_paint.setTypeface( mTypeFace ) ; t_paint.setTextAlign( Align.CENTER ) ;
		canvas.drawText( text, width * 96 / 128, ( height + mnTextSize ) / 2, t_paint ) ;
	}
}
