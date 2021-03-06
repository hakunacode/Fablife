package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Engine.ProfileManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class StatusBarLevel extends View 
{
	private Bitmap 					mIcon 			= null ;
	private int 					mnTextSize 		= 18 ;
	private Typeface 				mTypeFace		= null ;
	private int						mnLevel			= 1 ;
	private int						mnExperience	= 0 ;
	
	public StatusBarLevel( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		mIcon	 	= BitmapFactory.decodeResource( getResources(), R.drawable.home_level ) ;
		mTypeFace 	= Typeface.createFromAsset( context.getAssets(), "font/Big Bimbo NC.ttf" ) ;
	}
	
	public void setExperience( int exp )
	{
		mnExperience = exp ;
	}
	
	public void setLevel( int level )
	{
		mnLevel = level ;
	}
	
	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		int height = getHeight() ;
		int width = getWidth() ;
		
		mnTextSize = (int) ( height * 0.5 ) ;
		
		int percent = ( mnExperience - ProfileManager.getLevelXp( mnLevel - 1 ) ) * 100 
						/ ( ProfileManager.getLevelXp( mnLevel ) - ProfileManager.getLevelXp( mnLevel - 1 ) ) ;
		if ( percent != 0 )
		{
			if ( percent > 100 )
				percent = 100 ;
			Paint paint = new Paint() ; paint.setColor( getResources().getColor( R.color.rating_color ) ) ; paint.setStyle( Paint.Style.FILL ) ;
			RectF sub_rect = new RectF( width * 54.0f / 194.0f, height * 8.0f / 54.0f, width * 54.0f / 194.0f + ( width * 136.0f / 194.0f ) * percent / 100, height * 48.0f / 54.0f ) ;
			canvas.drawRect( sub_rect, paint ) ;
		}
		
		Rect bitmap_rect = new Rect( 0, 0, width, height ) ;
		Paint icon_paint = new Paint() ; icon_paint.setAntiAlias( true ) ; icon_paint.setFilterBitmap( true ) ;
		canvas.drawBitmap( mIcon, null, bitmap_rect, icon_paint ) ;
		
		Paint t_paint = new Paint() ; t_paint.setColor( Color.BLACK ) ; t_paint.setTextSize( mnTextSize ) ;
		t_paint.setAntiAlias( true ) ; t_paint.setTypeface( mTypeFace ) ; t_paint.setTextAlign( Align.CENTER ) ;
		String text = "" + mnExperience + "XP" ;
		canvas.drawText( "" + mnLevel, width * 28.0f / 194.0f, ( height + mnTextSize ) / 2, t_paint ) ;
		canvas.drawText( text, width * 122.0f / 194.0f, ( height + mnTextSize ) / 2, t_paint ) ;
	}
}
