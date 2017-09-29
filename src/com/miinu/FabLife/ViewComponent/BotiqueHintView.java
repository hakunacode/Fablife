package com.miinu.FabLife.ViewComponent;

import com.miinu.FabLife.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class BotiqueHintView extends View 
{
	Bitmap 					bg	 		= null ;
	Paint					paint		= null ;
	int 					text_size 	= 18 ;
	Typeface 				t_face 		= null ;
	BitmapFactory.Options	options		= new BitmapFactory.Options() ;
	
	int						width		= 100 ;
	int						height		= 60 ;
	float					scale		= 0.0f ;
	int						alpha		= 255 ;
	float					x_pos		= 0.0f ;
	float					y_pos		= 0.0f ;
	boolean					isAniming	= false ;
	
	public BotiqueHintView( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		
		options.inPurgeable = true ;
		
		paint = new Paint() ; paint.setAntiAlias( true ) ; paint.setFilterBitmap( true ) ;
		paint.setColor( Color.WHITE ) ; paint.setTextAlign( Align.CENTER ) ;
		t_face = Typeface.createFromAsset( context.getAssets(), "font/abeatbykai.ttf" ) ;
		paint.setTypeface( t_face ) ; paint.setTextScaleX( 0.8f ) ;
	}
	
	int id = 1 ;
	public void startHint( int i )
	{
		id = i ;
		if ( isAniming )
		{
			alpha = 255 ;
		}
		else
		{
			setVisibility( View.VISIBLE ) ;
			isAniming = true ;
			Handler handler = new Handler()
			{
				@Override
				public void handleMessage( Message msg )
				{
					// TODO Auto-generated method stub
					if ( alpha > 0 )
					{
						paint.setAlpha( alpha ) ;
						alpha -= 2 ;
						invalidate() ;
						sendEmptyMessageDelayed( 0, 5 ) ;
					}
					else
					{
						isAniming = false ;
						setVisibility( View.GONE ) ;
						alpha = 255 ;
					}
				}
			} ;
			handler.sendEmptyMessage( 0 ) ;
		}
	}
	
	String type = "" ;
	String value = "" ;
	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		scale = (float)( getWidth() / 320.0f ) ;
		width = (int)( 100 * scale ) ;
		height = (int)( 60 * scale ) ;
		
		if ( bg == null )
		{
			paint.setTextSize( text_size * scale ) ;
			bg = BitmapFactory.decodeResource( getResources(), R.drawable.award_tip_background, options ) ;
			bg = Bitmap.createScaledBitmap( bg, width, height, true ) ;
		}
		
		switch( id )
		{
			case 1 :
			{
				type = "Outfits" ;
				value = "80" ;
				x_pos = 80 * scale ;
				y_pos = 110 * scale ;
				break ;
			}
			case 2 :
			{
				type = "Outfits" ;
				value = "400" ;
				x_pos = 130 * scale ;
				y_pos = 110 * scale ;
				break ;
			}
			case 3 :
			{
				type = "Outfits" ;
				value = "1000" ;
				x_pos = 184 * scale ;
				y_pos = 110 * scale ;
				break ;
			}
			case 4 :
			{
				type = "Outfits" ;
				value = "2000" ;
				x_pos = 90 * scale ;
				y_pos = 110 * scale ;
				break ;
			}
			case 5 :
			{
				type = "Outfits" ;
				value = "3200" ;
				x_pos = 140 * scale ;
				y_pos = 110 * scale ;
				break ;
			}
			case 6 :
			{
				type = "Total Flirts" ;
				value = "5" ;
				x_pos = 80 * scale ;
				y_pos = 227 * scale ;
				break ;
			}
			case 7 :
			{
				type = "Total Flirts" ;
				value = "30" ;
				x_pos = 130 * scale ;
				y_pos = 227 * scale ;
				break ;
			}
			case 8 :
			{
				type = "Total Flirts" ;
				value = "100" ;
				x_pos = 184 * scale ;
				y_pos = 227 * scale ;
				break ;
			}
			case 9 :
			{
				type = "Total Flirts" ;
				value = "200" ;
				x_pos = 90 * scale ;
				y_pos = 227 * scale ;
				break ;
			}
			case 10 :
			{
				type = "Total Flirts" ;
				value = "400" ;
				x_pos = 140 * scale ;
				y_pos = 227 * scale ;
				break ;
			}
			case 11 :
			{
				type = "Total Earning" ;
				value = "1000" ;
				x_pos = 80 * scale ;
				y_pos = 350 * scale ;
				break ;
			}
			case 12 :
			{
				type = "Total Earning" ;
				value = "10000" ;
				x_pos = 130 * scale ;
				y_pos = 350 * scale ;
				break ;
			}
			case 13 :
			{
				type = "Total Earning" ;
				value = "100000" ;
				x_pos = 184 * scale ;
				y_pos = 350 * scale ;
				break ;
			}
			case 14 :
			{
				type = "Total Earning" ;
				value = "850000" ;
				x_pos = 90 * scale ;
				y_pos = 350 * scale ;
				break ;
			}
			case 15 :
			{
				type = "Total Earning" ;
				value = "2000000" ;
				x_pos = 140 * scale ;
				y_pos = 350 * scale ;
				break ;
			}
		}
		canvas.drawBitmap( bg, x_pos, y_pos, paint ) ;
		canvas.drawText( type, x_pos + width / 2.0f, y_pos + 8.0f * scale + text_size * scale, paint ) ;
		canvas.drawText( value, x_pos + width / 2.0f, y_pos + 16.0f * scale + 2 * text_size * scale, paint ) ;
	}
}
