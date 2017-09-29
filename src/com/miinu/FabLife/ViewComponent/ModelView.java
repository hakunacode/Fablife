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
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class ModelView extends View
{
	static public final int		VIEW_WHOLE			= 1 ;
	static public final int		VIEW_HALF			= 2 ;
	
	private final int			SUBJECT_ALL			= 1 ;
	private final int			SUBJECT_TOP			= 2 ;
	private final int			SUBJECT_BOTTOM_1	= 3 ;
	private final int			SUBJECT_BOTTOM_2	= 4 ;
	private final int			SUBJECT_DRESS_1		= 5 ;
	private final int			SUBJECT_DRESS_2		= 6 ;
	private final int			SUBJECT_OUTERWEAR	= 7 ;
	private final int			SUBJECT_SHOES_1		= 8 ;
	private final int			SUBJECT_SHOES_2		= 9 ;
	private final int			SUBJECT_BAG_1		= 10 ;
	private final int			SUBJECT_BAG_2		= 11 ;
	private final int			SUBJECT_BAG_3		= 12 ;
	private final int			SUBJECT_BAG_4		= 13 ;
	private final int			SUBJECT_JEWEL_1		= 14 ;
	private final int			SUBJECT_JEWEL_2		= 15 ;
	private final int			SUBJECT_JEWEL_3		= 16 ;
	private final int			SUBJECT_JEWEL_4		= 17 ;
	private final int			SUBJECT_JEWEL_5		= 18 ;
	private final int			SUBJECT_JEWEL_6		= 19 ;
	private final int			SUBJECT_JEWEL_7		= 20 ;
	private final int			SUBJECT_JEWEL_8		= 21 ;
	private final int			SUBJECT_HAIR		= 22 ;
	private final int			SUBJECT_FUNKY		= 23 ;
	private final int			SUBJECT_SKINCOLOR	= 24 ;
	private final int			SUBJECT_BLUSHON		= 25 ;
	private final int			SUBJECT_EYESHADOW	= 26 ;
	private final int			SUBJECT_MASCARA		= 27 ;
	private final int			SUBJECT_EYEBROW		= 28 ;
	private final int			SUBJECT_EYECOLOR	= 29 ;
	private final int			SUBJECT_LIPS		= 30 ;
	
	private Context 			ctx		 			= 	null ;
	private Paint 				paint				= 	null ;
	
	private int					view_mode			=	VIEW_WHOLE ;
	private float				scale				= 	1.0f ;
	private final int			body_x				= 	128 ;
	private final int			body_y				= 	20 ;
	
	private int					width				= 	240 ;
	private int					height				= 	370 ;
	
	private Bitmap				avatar_bitmap01		= null ;
	private Bitmap				avatar_bitmap02		= null ;
	
	private BitmapFactory.Options		options				= new BitmapFactory.Options() ;
	
	private boolean				cleared				= false ;
	private boolean 			mbIsEyeClosed		= false ;
	private	Handler				mHandler			= new Handler()
	{
		@Override
		public void handleMessage( Message msg )
		{
			// TODO Auto-generated method stub
			invalidate() ;
		}
	} ;
	
	public ModelView( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		
		ctx = context ;
		paint = new Paint() ; paint.setFilterBitmap( true ) ; paint.setAntiAlias( true ) ;
		options.inPurgeable = true ;
	}
	
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
	
	private String getBodyURLFromSkinName( String skin_name )
	{
		String ret = "avatar/skin1/girl_1.png" ;
		if ( skin_name.contains( "skincolor-1" ) )
		{
			ret = "avatar/skin1/girl_1.png" ;
		}
		else if ( skin_name.contains( "skincolor-2" ) )
		{
			ret = "avatar/skin2/girl_2.png" ;
		}
		else if ( skin_name.contains( "skincolor-3" ) )
		{
			ret = "avatar/skin3/girl_3.png" ;
		}
		else if ( skin_name.contains( "skincolor-4" ) )
		{
			ret = "avatar/skin4/girl_4.png" ;
		}
		else if ( skin_name.contains( "skincolor-5" ) )
		{
			ret = "avatar/skin5/girl_5.png" ;
		}
		else if ( skin_name.contains( "skincolor-6" ) )
		{
			ret = "avatar/skin6/girl_6.png" ;
		}
		return ret ;
	}
	
	private Bitmap getBodyBitmap()
	{
		String body_url = "avatar/skin1/girl_1.png" ;
		String item = FabLifeApplication.getAppliedManager().getItemFromSubject( SUBJECT_SKINCOLOR ) ;
		if ( item != null )
		{
			body_url = getBodyURLFromSkinName( item ) ;
		}
		try
		{
			Bitmap body = null ;
			InputStream is = ctx.getAssets().open( body_url );
			body = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			return body ;
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		return null ;
	}
	
	private Bitmap getEyeBitmap()
	{
		String eye_url = "avatar/skin1/closeeyes_1.png" ;
		String item = FabLifeApplication.getAppliedManager().getItemFromSubject( SUBJECT_SKINCOLOR ) ;
		if ( item != null )
		{
			if ( item.contains( "skincolor-1" ) )
			{
				eye_url = "avatar/skin1/closeeyes_1.png" ;
			}
			else if ( item.contains( "skincolor-2" ) )
			{
				eye_url = "avatar/skin2/closeeyes_2.png" ;
			}
			else if ( item.contains( "skincolor-3" ) )
			{
				eye_url = "avatar/skin3/closeeyes_3.png" ;
			}
			else if ( item.contains( "skincolor-4" ) )
			{
				eye_url = "avatar/skin4/closeeyes_4.png" ;
			}
			else if ( item.contains( "skincolor-5" ) )
			{
				eye_url = "avatar/skin5/closeeyes_5.png" ;
			}
			else if ( item.contains( "skincolor-6" ) )
			{
				eye_url = "avatar/skin6/closeeyes_6.png" ;
			}
		}
		try
		{
			Bitmap eye = null ;
			InputStream is = ctx.getAssets().open( eye_url );
			eye = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			return eye ;
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		return null ;
	}
	
	private Point getItemPos( String url, boolean closeEye )
	{
		int subject = getSubjectFromName( url ) ;
		if ( subject == SUBJECT_ALL || subject == SUBJECT_SKINCOLOR ||
				( closeEye && ( subject == SUBJECT_EYECOLOR ) ) )
			return null ;
		Point pos = new Point( 0, 0 ) ;
		switch( subject )
		{
			case SUBJECT_TOP :
				pos.x = -42 ; pos.y = 64 ;
				break ;
			case SUBJECT_BOTTOM_1 :
				pos.x = -24 ; pos.y = 207 ;
				break ;
			case SUBJECT_BOTTOM_2 :
				pos.x = -53 ; pos.y = 206 ;
				break ;
			case SUBJECT_DRESS_1 :
				pos.x = -75 ; pos.y = 67 ;
				break ;
			case SUBJECT_DRESS_2 :
				pos.x = -117 ; pos.y = 60 ;
				break ;
			case SUBJECT_OUTERWEAR :
				pos.x = -105 ; pos.y = 68 ;
				if ( url.equalsIgnoreCase( "items/shop2/outerwear-5.png" ) )
				{
					pos.x = -108 ; pos.y = -102 ;
				}
				break ;
			case SUBJECT_SHOES_1 :
				pos.x = 17 ; pos.y = 610 ;
				break ;
			case SUBJECT_SHOES_2 :
				pos.x = -16 ; pos.y = 421 ;
				break ;
			case SUBJECT_BAG_1 :
				pos.x = -88 ; pos.y = 78 ;
				break ;
			case SUBJECT_BAG_2 :
				pos.x = -153 ; pos.y = 287 ;
				break ;
			case SUBJECT_BAG_3 :
				pos.x = 20 ; pos.y = 253 ;
				break ;
			case SUBJECT_BAG_4 :
				pos.x = -109 ; pos.y = 54 ;
				break ;
			case SUBJECT_JEWEL_1 :
				pos.x = 141 ; pos.y = 257 ;					
				break ;
			case SUBJECT_JEWEL_2 :
				pos.x = -22 ; pos.y = 238 ;
				break ;
			case SUBJECT_JEWEL_3 :
				pos.x = 31 ; pos.y = 53 ;
				break ;
			case SUBJECT_JEWEL_4 :
				pos.x = 0 ; pos.y = -55 ;
				break ;
			case SUBJECT_JEWEL_5 :
				pos.x = 29 ; pos.y = 75 ;
				break ;
			case SUBJECT_JEWEL_6 :
				pos.x = -51 ; pos.y = 45 ;
				break ;
			case SUBJECT_JEWEL_7 :
				pos.x = 0 ; pos.y = 219 ;
				break ;
			case SUBJECT_JEWEL_8 :
				pos.x = 41 ; pos.y = 20 ;
				break ;
			case SUBJECT_HAIR :
				pos.x = -16 ; pos.y = -68 ;
				break ;
			case SUBJECT_FUNKY :
				pos.x = -16 ; pos.y = -68 ;
				break ;
			case SUBJECT_BLUSHON :
				pos.x = 67 ; pos.y = 32 ;
				break ;
			case SUBJECT_EYESHADOW :
				pos.x = 66 ; pos.y = 35 ;
				break ;
			case SUBJECT_MASCARA :
				pos.x = 67 ; pos.y = 34 ;
				break ;
			case SUBJECT_EYEBROW :
				pos.x = 67 ; pos.y = 34;
				break ;
			case SUBJECT_EYECOLOR : 
				pos.x = 67 ; pos.y = 34 ;
				break ;
			case SUBJECT_LIPS :
				pos.x = 67 ; pos.y = 34 ;
				break ;
		}
		return pos ;
	}
	
	private void drawIntoBitmap( Bitmap bm, boolean closedEye )
	{
		Canvas c = new Canvas( bm ) ;
		
		Bitmap body = getBodyBitmap() ;
		c.drawBitmap( body, body_x, body_y, paint ) ;
		body.recycle() ; body = null ;
		if ( closedEye )
		{
			Bitmap eye = getEyeBitmap() ;
			c.drawBitmap( eye, body_x + 77, body_y + 46, paint ) ;
			eye.recycle() ; eye = null ;
		}
		
		int count = FabLifeApplication.getAppliedManager().getMakeUpCount() ;
		for ( int i = 0 ; i < count ; i++ )
		{
			String item = FabLifeApplication.getAppliedManager().getMakeUpItemByIndex( i ) ;
			Point pos = getItemPos( item, closedEye ) ;
			if ( pos != null )
			{
				try
				{
					InputStream is = ctx.getAssets().open( item );
					Bitmap item_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
					is.close() ;
					
					c.drawBitmap( item_bitmap, body_x + pos.x, body_y + pos.y, paint ) ;
					item_bitmap.recycle() ; item_bitmap = null ;
				}
				catch ( IOException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace() ;
				}
			}
		}		
		
		count = FabLifeApplication.getAppliedManager().getNoMakeUpCount() ;
		for ( int i = count - 1 ; i >= 0 ; i-- )
		{
			String item = FabLifeApplication.getAppliedManager().getNoMakeUpItemByIndex( i ) ;
			Point pos = getItemPos( item, closedEye ) ;
			if ( pos != null )
			{
				try
				{
					Bitmap item_bitmap = null ;
					String url = null ;
					int index = 0 ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
					{
						 index = item.indexOf( "_color" ) ;
					     url = item.substring( 0, index ) ;
					}
					else
					{
						url = item ;
					}
					InputStream is = ctx.getAssets().open( url );
					item_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
					is.close() ;
					
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
					{
						item_bitmap = Bitmap.createScaledBitmap( item_bitmap, item_bitmap.getWidth(), item_bitmap.getHeight(), true ) ;
						getColoredHair( item_bitmap, Integer.parseInt( "" + item.charAt( index + 6 ) ) ) ;
					}
					c.drawBitmap( item_bitmap, body_x + pos.x, body_y + pos.y, paint ) ;
					item_bitmap.recycle() ; item_bitmap = null ;
				}
				catch ( IOException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace() ;
				}
			}
		}
	}
	
	@Override
	protected void onDraw( Canvas canvas )
	{
		// TODO Auto-generated method stub
		if ( cleared )
			return ;
		
		width = getWidth() ;
		height = getHeight() ;
		
		scale = width / 240.0f ;
		
		if ( avatar_bitmap01 == null || avatar_bitmap02 == null )
		{
			try
			{
				getAvatarBitmap() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
		
		if ( avatar_bitmap01 == null || avatar_bitmap02 == null )
			return ;
		
		mbIsEyeClosed = ( Math.random() > 0.5 ) ;
		
		if ( view_mode == VIEW_WHOLE )
		{
			int b_width = (int)( avatar_bitmap01.getWidth() * height / avatar_bitmap01.getHeight() ) ;
			Rect dst = new Rect( ( width - b_width ) / 2, 0, ( width - b_width ) / 2 + b_width, height ) ;
			if ( mbIsEyeClosed )
			{
				if ( avatar_bitmap02 != null && !avatar_bitmap02.isRecycled() )
					canvas.drawBitmap( avatar_bitmap02, null, dst, paint ) ;
			}
			else
			{
				if ( avatar_bitmap01 != null && !avatar_bitmap01.isRecycled() )
					canvas.drawBitmap( avatar_bitmap01, null, dst, paint ) ;
			}
		}
		else
		{
			if ( avatar_bitmap01 != null && !avatar_bitmap01.isRecycled() && avatar_bitmap02 != null && !avatar_bitmap02.isRecycled() )
			{
				int b_height = (int)( avatar_bitmap01.getHeight() * ( width * 2 ) / avatar_bitmap01.getWidth() ) ;
				Rect dst = new Rect( - width / 2, (int) (40 * scale), width * 3 / 2, (int) (40 * scale + b_height ) ) ;
				if ( mbIsEyeClosed )
					canvas.drawBitmap( avatar_bitmap02, null, dst, paint ) ;
				else
					canvas.drawBitmap( avatar_bitmap01, null, dst, paint ) ;
			}
		}
		if ( mbIsEyeClosed )
			mHandler.sendEmptyMessageDelayed( 0, (long)( 500 + Math.random() * 100 ) ) ;
		else
			mHandler.sendEmptyMessageDelayed( 0, (long)( 3000 + Math.random() * 2000 ) ) ;
	}
	
	public void wholeAnimation()
	{
		view_mode = VIEW_WHOLE ;
	}
	
	public void halfAnimation()
	{
		view_mode = VIEW_HALF ;
	}
	
	public void getAvatarBitmap() throws Exception
	{
		if ( avatar_bitmap01 == null )
		{
			avatar_bitmap01 = Bitmap.createBitmap( 480, 840, Bitmap.Config.ARGB_8888 ) ;
		}
		else
		{
			avatar_bitmap01.eraseColor( Color.TRANSPARENT ) ;
		}
		if ( avatar_bitmap02 == null )
		{
			avatar_bitmap02 = Bitmap.createBitmap( 480, 840, Bitmap.Config.ARGB_8888 ) ;
		}
		else
		{
			avatar_bitmap02.eraseColor( Color.TRANSPARENT ) ;
		}
		drawIntoBitmap( avatar_bitmap01, false ) ;
		drawIntoBitmap( avatar_bitmap02, true ) ;
	}
	
	public Bitmap getSaveBitmap()
	{
		return avatar_bitmap01 ;
	}
	
	public void clear() throws Exception
	{
		cleared = true ;
		if ( avatar_bitmap01 != null )
		{
			if ( !avatar_bitmap01.isRecycled() ) avatar_bitmap01.recycle() ;
		}
		if ( avatar_bitmap02 != null )
		{
			if ( !avatar_bitmap02.isRecycled() ) avatar_bitmap02.recycle() ;
		}
		
		avatar_bitmap01 = null ;
		avatar_bitmap02 = null ;
		System.gc() ;
	}
}
