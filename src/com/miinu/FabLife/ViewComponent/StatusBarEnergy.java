package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class StatusBarEnergy extends View 
{
	private Bitmap			mIcon			= null ;
	private int 			mnTextSize 		= 18 ;
	private Typeface 		mTypeFace		= null ;
	private boolean			mbIsVipMember	= false ;
	private int				mnEnergy		= 0 ;
	
	public StatusBarEnergy( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		mIcon 		= BitmapFactory.decodeResource( getResources(), R.drawable.home_energy ) ;
		mTypeFace 	= Typeface.createFromAsset( context.getAssets(), "font/Big Bimbo NC.ttf" ) ;
	}
	
	public void setEnergy( int energy )
	{
		mnEnergy = energy ;
	}
	
	public void setVipMember( boolean isVipMember )
	{
		mbIsVipMember = isVipMember ;
	}
	
	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		int height = getHeight() ;
		int width = getWidth() ;
		
		mnTextSize = (int) ( height * 0.4 ) ;
		
		int percent = 0 ;
		int energy_limit = mbIsVipMember ? 55 : 45 ;
		if ( mnEnergy >= energy_limit )
			percent = 100 ;
		else
			percent = mnEnergy * 100 / energy_limit ;
		if ( percent != 0 )
		{
			Paint paint = new Paint() ; paint.setColor( getResources().getColor( R.color.rating_color ) ) ; paint.setStyle( Paint.Style.FILL ) ;
			RectF sub_rect = new RectF( width * 64.0f / 178.0f, height * 15.0f / 69.0f + 1, width * 64.0f / 178.0f + ( width * 112.0f / 178.0f ) * percent / 100, height * 55.0f / 69.0f - 0.5f ) ;
			canvas.drawRect( sub_rect, paint ) ;
		}
		
		Rect bitmap_rect = new Rect( 0, 0, width, height ) ;
		Paint icon_paint = new Paint() ; icon_paint.setAntiAlias( true ) ; icon_paint.setFilterBitmap( true ) ;
		canvas.drawBitmap( mIcon, null, bitmap_rect, icon_paint ) ;
		
		String text = "" + mnEnergy + "/" + energy_limit ;
		Paint t_paint = new Paint() ; t_paint.setColor( Color.BLACK ) ; t_paint.setTextSize( mnTextSize ) ;
		t_paint.setAntiAlias( true ) ; t_paint.setTypeface( mTypeFace ) ; t_paint.setTextAlign( Align.CENTER ) ;		
		canvas.drawText( text, width * 120.0f / 178.0f, ( height + mnTextSize ) / 2, t_paint ) ;
	}
}
