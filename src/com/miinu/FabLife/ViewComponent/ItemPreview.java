package com.miinu.FabLife.ViewComponent;

import java.io.IOException;
import java.io.InputStream;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class ItemPreview extends View
{
	public final int			SUBJECT_ALL			= 1 ;
	public final int			SUBJECT_TOP			= 2 ;
	public final int			SUBJECT_BOTTOM_1	= 3 ;
	public final int			SUBJECT_BOTTOM_2	= 4 ;
	public final int			SUBJECT_DRESS_1		= 5 ;
	public final int			SUBJECT_DRESS_2		= 6 ;
	public final int			SUBJECT_OUTERWEAR	= 7 ;
	public final int			SUBJECT_SHOES_1		= 8 ;
	public final int			SUBJECT_SHOES_2		= 9 ;
	public final int			SUBJECT_BAG_1		= 10 ;
	public final int			SUBJECT_BAG_2		= 11 ;
	public final int			SUBJECT_BAG_3		= 12 ;
	public final int			SUBJECT_BAG_4		= 13 ;
	public final int			SUBJECT_JEWEL_1		= 14 ;
	public final int			SUBJECT_JEWEL_2		= 15 ;
	public final int			SUBJECT_JEWEL_3		= 16 ;
	public final int			SUBJECT_JEWEL_4		= 17 ;
	public final int			SUBJECT_JEWEL_5		= 18 ;
	public final int			SUBJECT_JEWEL_6		= 19 ;
	public final int			SUBJECT_JEWEL_7		= 20 ;
	public final int			SUBJECT_JEWEL_8		= 21 ;
	public final int			SUBJECT_HAIR		= 22 ;
	public final int			SUBJECT_FUNKY		= 23 ;
	
	public final int			SUBJECT_SKINCOLOR	= 24 ;
	public final int			SUBJECT_BLUSHON		= 25 ;
	public final int			SUBJECT_EYESHADOW	= 26 ;
	public final int			SUBJECT_MASCARA		= 27 ;
	public final int			SUBJECT_EYEBROW		= 28 ;
	public final int			SUBJECT_EYECOLOR	= 29 ;
	public final int			SUBJECT_LIPS		= 30 ;
	
	Context 					ctx		 			= 	null ;
	Paint 						paint				= 	null ;
	
	String						url 				= 	null ;
	
	float						scale				= 	1.0f ;
	final int					body_width			=	225 ;
	final int					body_height			=	787 ;
	final int					body_x				= 	128 ;
	final int					body_y				= 	20 ;
	
	boolean 					isClosedEye			= 	false ;
	
	int 						width				= 100 ;
	int							height				= 350 ;
	
	BitmapFactory.Options		options				= new BitmapFactory.Options() ;
	
	String getCompareStringFromSubject( int subject )
	{
		String ret = null ;
		switch( subject )
		{
			case SUBJECT_TOP :
				ret = "top" ;
				break ;
			case SUBJECT_BOTTOM_1 :
				ret = "bottom-1" ;
				break ;
			case SUBJECT_BOTTOM_2 :
				ret = "bottom-2" ;
				break ;
			case SUBJECT_DRESS_1 :
				ret = "dress-1" ;
				break ;
			case SUBJECT_DRESS_2 :
				ret = "dress-2" ;
				break ;
			case SUBJECT_OUTERWEAR :
				ret = "outerwear" ;
				break ;
			case SUBJECT_SHOES_1 :
				ret = "shoes-1" ;
				break ;
			case SUBJECT_SHOES_2 :
				ret = "shoes-2" ;
				break ;
			case SUBJECT_BAG_1 :
				ret = "bag-1" ;
				break ;
			case SUBJECT_BAG_2 :
				ret = "bag-2" ;
				break ;
			case SUBJECT_BAG_3 :
				ret = "bag-3" ;
				break ;
			case SUBJECT_BAG_4 :
				ret = "bag-4" ;
				break ;
			case SUBJECT_JEWEL_1 :
				ret = "jewel-1" ;
				break ;
			case SUBJECT_JEWEL_2 :
				ret = "jewel-2" ;
				break ;
			case SUBJECT_JEWEL_3 :
				ret = "jewel-3" ;
				break ;
			case SUBJECT_JEWEL_4 :
				ret = "jewel-4" ;
				break ;
			case SUBJECT_JEWEL_5 :
				ret = "jewel-5" ;
				break ;
			case SUBJECT_JEWEL_6 :
				ret = "jewel-6" ;
				break ;
			case SUBJECT_JEWEL_7 :
				ret = "jewel-7" ;
				break ;
			case SUBJECT_JEWEL_8 :
				ret = "jewel-8" ;
				break ;
			case SUBJECT_HAIR :
				ret = "hair" ;
				break ;
			case SUBJECT_FUNKY :
				ret = "funky" ;
				break ;
			case SUBJECT_SKINCOLOR :
				ret = "skincolor" ;
				break ;
			case SUBJECT_BLUSHON :
				ret = "blushon" ;
				break ;
			case SUBJECT_EYESHADOW :
				ret = "eyeshadow" ;
				break ;
			case SUBJECT_MASCARA :
				ret = "mascara" ;
				break ;
			case SUBJECT_EYEBROW :
				ret = "eyebrow" ;
				break ;
			case SUBJECT_EYECOLOR :
				ret = "eyecolor" ;
				break ;
			case SUBJECT_LIPS :
				ret = "lips" ;
				break ;
		}
		return ret ;
	}
	
	int getSubjectFromName( String name )
	{
		for ( int i = SUBJECT_TOP ; i <= SUBJECT_LIPS ; i++ )
		{
			if ( name.contains( getCompareStringFromSubject( i ) ) )
			{
				return i ;
			}
		}
		return SUBJECT_ALL ;
	}
	
	public void setUrl( String URL )
	{
		url = URL ;
	}
	
	public ItemPreview( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		
		ctx = context ;
		paint = new Paint() ; paint.setFilterBitmap( true ) ; paint.setAntiAlias( true ) ;
		
		options.inPurgeable = true ;
		
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				// TODO Auto-generated method stub
				isClosedEye = ( Math.random() > 0.5 ) ;
				invalidate() ;
				if ( isClosedEye )
					sendEmptyMessageDelayed( 0, (long)( Math.random() * 500 ) ) ;
				else
					sendEmptyMessageDelayed( 0, (long)( Math.random() * 5000 ) ) ;
				
				super.handleMessage( msg ) ;
			}
		} ;
		handler.sendEmptyMessage( 0 ) ;
	}
	
	void getColoredHair( Bitmap hair, int out_color )
	{
		if ( out_color == 1 )
			return ;
		
    	float r_scale = 1.0f ;
    	float g_scale = 1.0f ;
    	float b_scale = 1.0f ;
    	
    	int source_color = ctx.getResources().getColor( R.color.haircolor01 ) ;
    	int hair_color = ctx.getResources().getColor( R.color.haircolor01 + ( out_color - 1 ) ) ;
    	r_scale = (float)( ( hair_color >> 16 ) & 0xFF ) / ( ( source_color >> 16 ) & 0xFF ) ;
    	g_scale = (float)( ( hair_color >> 8 ) & 0xFF ) / ( ( source_color >> 8 ) & 0xFF ) ;
    	b_scale = (float)( hair_color & 0xFF ) / ( source_color & 0xFF ) ;
    	
    	for ( int h = 40 ; h < 290 ; h++ )
    	{
    		for ( int w = 30 ; w < 180 ; w++ )
    		{
    			int color = hair.getPixel( w, h ) ;
    			if ( color != Color.TRANSPARENT )
    			{
    				int source_alpha	= ( color >> 24 ) & 0xFF ;
    				int source_r		= ( color >> 16 ) & 0xFF ;
    				int source_g		= ( color >> 8 ) & 0xFF ;
    				int source_b		= color & 0xFF ;

    				int r	= (int) ( source_r * r_scale ) ;
    				int g	= (int) ( source_g * g_scale ) ;
    				int b	= (int) ( source_b * b_scale ) ;
    				int c = ( source_alpha << 24 ) | ( r << 16 ) | ( g << 8 ) | b ;
    				hair.setPixel( w, h, c ) ;
    			}
    		}
    	}
	}

	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		if ( url == null )
		{
			return ;
		}
		else
		{
			width = getWidth() ;
			height = getHeight() ;
			
			Bitmap bitmap = Bitmap.createBitmap( 480, 840, Bitmap.Config.ARGB_8888 ) ;
			drawIntoBitmap( bitmap ) ;
			
			int b_width = (int)( 480 * height / 840 ) ;

			Rect rect = new Rect( ( width - b_width ) / 2, 0, ( width + b_width ) / 2, height ) ;
			canvas.drawBitmap( bitmap, null, rect, paint ) ;
			bitmap.recycle() ; bitmap = null ;
		}
	}
	
	private void drawIntoBitmap( Bitmap bm )
	{
		Canvas c = new Canvas( bm ) ;
		
		int subject = getSubjectFromName( url ) ;
		if ( subject == SUBJECT_ALL )
			return ;
		
		String body_url = "avatar/skin1/girl_1.png" ;
		String eye_url = "avatar/skin1/closeeyes_1.png" ;
		
		if ( subject == SUBJECT_SKINCOLOR )
		{
			if ( url.contains( "skincolor-1" ) )
			{
				body_url = "avatar/skin1/girl_1.png" ;
				eye_url = "avatar/skin1/closeeyes_1.png" ;
			}
			else if ( url.contains( "skincolor-2" ) )
			{
				body_url = "avatar/skin2/girl_2.png" ;
				eye_url = "avatar/skin2/closeeyes_2.png" ;
			}
			else if ( url.contains( "skincolor-3" ) )
			{
				body_url = "avatar/skin3/girl_3.png" ;
				eye_url = "avatar/skin3/closeeyes_3.png" ;
			}
			else if ( url.contains( "skincolor-4" ) )
			{
				body_url = "avatar/skin4/girl_4.png" ;
				eye_url = "avatar/skin4/closeeyes_4.png" ;
			}
			else if ( url.contains( "skincolor-5" ) )
			{
				body_url = "avatar/skin5/girl_5.png" ;
				eye_url = "avatar/skin5/closeeyes_5.png" ;
			}
			else if ( url.contains( "skincolor-6" ) )
			{
				body_url = "avatar/skin6/girl_6.png" ;
				eye_url = "avatar/skin6/closeeyes_6.png" ;
			}
		}
		
		try
		{
			Bitmap body = null ;
			Bitmap eye = null ;
			InputStream is = ctx.getAssets().open( body_url );
			body = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			if ( isClosedEye )
			{
				is = ctx.getAssets().open( eye_url ) ;
				eye = BitmapFactory.decodeStream( is, null, options ) ;
				is.close() ;
			}
			
			Bitmap item = null ;
			
			is = ctx.getAssets().open( url ) ;
			item = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			
			if ( url.contains( "hair" ) || url.contains( "funky" ) )
			{
				item = Bitmap.createScaledBitmap( item, item.getWidth(), item.getHeight(), true ) ;
				getColoredHair( item, FabLifeApplication.getHairManager().getHairColor( url ) ) ;
			}
			
			float diff_x = 0.0f ;
			float diff_y = 0.0f ;
			switch( subject )
			{
				case SUBJECT_TOP :
					diff_x = -42.0f ;
					diff_y = 64.0f ;
					break ;
				case SUBJECT_BOTTOM_1 :
					diff_x = -24.0f ;
					diff_y = 207.0f ;
					break ;
				case SUBJECT_BOTTOM_2 :
					diff_x = -53.0f ;
					diff_y = 206.0f ;
					break ;
				case SUBJECT_DRESS_1 :
					diff_x = -75.0f ;
					diff_y = 67.0f ;
					break ;
				case SUBJECT_DRESS_2 :
					diff_x = -117.0f ;
					diff_y = 60.0f ;
					break ;
				case SUBJECT_OUTERWEAR :
					diff_x = -105.0f ;
					diff_y = 68.0f ;
					if ( url.equalsIgnoreCase( "items/shop2/outerwear-5.png" ) )
					{
						diff_x = -108.0f ;
						diff_y = -102.0f ;
					}
					break ;
				case SUBJECT_SHOES_1 :
					diff_x = 17.0f ;
					diff_y = 610.0f ;
					break ;
				case SUBJECT_SHOES_2 :
					diff_x = -16.0f ;
					diff_y = 421.0f ;
					break ;
				case SUBJECT_BAG_1 :
					diff_x = -88.0f ;
					diff_y = 78.0f ;
					break ;
				case SUBJECT_BAG_2 :
					diff_x = -153.0f ;
					diff_y = 287.0f ;
					break ;
				case SUBJECT_BAG_3 :
					diff_x = 20.0f ;
					diff_y = 253.0f ;
					break ;
				case SUBJECT_BAG_4 :
					diff_x = -109.0f ;
					diff_y = 54.0f ;
					break ;
				case SUBJECT_JEWEL_1 :
					diff_x = 141.0f ;
					diff_y = 257.0f ;
					break ;
				case SUBJECT_JEWEL_2 :
					diff_x = -22.0f ;
					diff_y = 238.0f ;
					break ;
				case SUBJECT_JEWEL_3 :
					diff_x = 31.0f ;
					diff_y = 53.0f ;
					break ;
				case SUBJECT_JEWEL_4 :
					diff_x = 0.0f ;
					diff_y = -55.0f ;
					break ;
				case SUBJECT_JEWEL_5 :
					diff_x = 29.0f ;
					diff_y = 75.0f ;
					break ;
				case SUBJECT_JEWEL_6 :
					diff_x = -51.0f ;
					diff_y = 45.0f ;
					break ;
				case SUBJECT_JEWEL_7 :
					diff_x = 0.0f ;
					diff_y = 219.0f ;
					break ;
				case SUBJECT_JEWEL_8 :
					diff_x = 41.0f ;
					diff_y = 20.0f ;
					break ;
				case SUBJECT_HAIR :
					diff_x = -16.0f ;
					diff_y = -68.0f ;
					break ;
				case SUBJECT_FUNKY :
					diff_x = -16.0f ;
					diff_y = -68.0f ;
					break ;
				case SUBJECT_BLUSHON :
					diff_x = 67.0f ;
					diff_y = 32.0f ;
					break ;
				case SUBJECT_EYESHADOW :
					diff_x = 66.0f ;
					diff_y = 35.0f ;
					break ;
				case SUBJECT_MASCARA :
					diff_x = 67.0f ;
					diff_y = 34.0f ;
					break ;
				case SUBJECT_EYEBROW :
					diff_x = 67.0f ; ;
					diff_y = 34.0f ; ;
					break ;
				case SUBJECT_EYECOLOR : 
					diff_x = 67.0f ;
					diff_y = 34.0f ;
					break ;
				case SUBJECT_LIPS :
					diff_x = 67.0f ;
					diff_y = 34.0f ;
					break ;
			}
			
			c.drawBitmap( body, body_x, body_y, paint ) ;
			if ( eye != null )
				c.drawBitmap( eye, body_x + 77, body_y + 46, paint ) ;
			
			if ( subject == SUBJECT_SKINCOLOR )
			{
				if ( body != null ) { body.recycle() ; body = null ; } 
				if ( eye != null ) 	{ eye.recycle() ; eye = null ; }
				return ;
			}
			else if ( subject == SUBJECT_EYECOLOR )
			{
				if ( eye == null )
					c.drawBitmap( item, body_x + diff_x, body_y + diff_y, paint ) ;
			}
			else
				c.drawBitmap( item, body_x + diff_x, body_y + diff_y, paint ) ;
			
			if ( body != null ) { body.recycle() ; body = null ; }
			if ( eye != null ) 	{ eye.recycle() ; eye = null ; }
			if ( item != null ) { item.recycle() ; item = null ; }
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
     }
}
