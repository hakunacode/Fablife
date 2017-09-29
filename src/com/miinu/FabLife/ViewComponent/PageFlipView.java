package com.miinu.FabLife.ViewComponent;

import android.content.Context;
import android.graphics.*;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import kcc.redstar.effect.BookPageTurnEffect;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Activity.LookBook_View;

import android.view.View.OnTouchListener;

public class PageFlipView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener
{
	static final int			EVENT_MOUSEDOWN				= 0 ;
	static final int			EVENT_MOUSEMOVE				= 1 ;
	static final int			EVENT_MOUSEUP				= 2 ;
	static final int			EVENT_PAUSE					= 3 ;
	static final int			EVENT_FLIPACTION			= 4 ;
	
	static final int			FLIP_ORIENT_DEC				= 0 ;
	static final int			FLIP_ORIENT_INC				= 1 ;
	static final int			FLIP_ORIENT_INIT			= 4 ;
	
	static final int			FLAG_PREVDRAW				= 0 ;
	static final int			FLAG_NEXTDRAW				= 1 ;
	static final int			FLAG_CURRDRAW				= 2 ;
	
	private	SurfaceHolder 		mHolder 					= null ;
	private Bitmap 				mFrontBitmap 				= null ;
	private	Bitmap 				mBackBitmap 				= null ;
	private BookPageTurnEffect 	mBookPageTurnEffect 		= null ;
	private	Canvas 				mBuffCanvas 				= null ;
	public 	Handler 			mHandler 					= null ;
	private	int 				mnHeight 					= 480 ;
	private int					mnWidth 					= 320 ;
	
	public 	int					mnCurrentPage				= 0 ;
	public 	int					mnCount						= 0 ;
	
	private Paint				mPaint						= null ;
	
	private	int 				mnPageTurnningDuration 		= 800 ;
	
	private	int					mnTouchX					= 0 ;
	private	int					mnTouchY					= 0 ;
	
	private	boolean 			mbIsDragging 				= false ;
	
	private	int 				mnPageScrollDir 			= FLIP_ORIENT_INIT ;
	
	LookBook_View				mParent						= null ;
	private	String				url							= null ;
	private	ArrayList<String>	mLists						= new ArrayList<String>() ;
	private	BitmapFactory.Options options					= new BitmapFactory.Options() ;
	
	public PageFlipView( Context context, AttributeSet attrs )
	{
		super( context, attrs ) ;
		// TODO Auto-generated constructor stub
		mHolder = getHolder() ;
		mHolder.addCallback( this ) ;
		
		mBookPageTurnEffect = new BookPageTurnEffect() ;
		mBuffCanvas = new Canvas() ;
		mHandler = new FlipHandler() ;
		
		mPaint = new Paint() ; mPaint.setAntiAlias( true ) ; mPaint.setFilterBitmap( true ) ;
		
		setOnTouchListener( this ) ;
		
        url = Environment.getExternalStorageDirectory().getPath() + "/apps/FabLife/lookbook_saved" ;
        options.inPurgeable = true ;
        File file = new File( url ) ;
        if ( file.exists() )
        {
        	String[] lists = file.list() ;
        	for ( int i = 0 ; i < lists.length ; i++ )
        	{
        		mLists.add( lists[ i ] ) ;
        	}
        	sortList() ;
        	mnCount = lists.length ;
        	lists = null ;
        }
	}
	
	void sortList()
	{
		for ( int i = 0 ; i < mLists.size() ; i++ )
    	{
    		String name01 = mLists.get( i ) ;
    		for ( int j = i + 1 ; j < mLists.size() ; j++ )
    		{
    			String name02 = mLists.get( j ) ;
    			if ( name01.compareTo( name02 ) > 0 )
    			{
    				String tmp = new String( name01 ) ;
    				name01 = null ;
    				name01 = new String( name02 ) ;
    				name02 = new String( tmp ) ;
    				tmp = null ;
    				mLists.set( i, name01 ) ;
    				mLists.set( j, name02 ) ;
    			}
    		}
    	}
	}
	
	public void delete()
	{
		if ( mnCount > 0 )
		{
			if ( mnCount <= mnCurrentPage )
				return ;
			
			mnCount-- ;
			if ( mnCount < 0 ) mnCount = 0 ;
			
			deleteFile( mnCurrentPage ) ;
			
			if ( mnCurrentPage == mnCount )
			{
				mnCurrentPage-- ;
				if ( mnCurrentPage < 0 )
					mnCurrentPage = 0 ;
			}
			mBuffCanvas.setBitmap( mFrontBitmap ) ;
			drawPage( mBuffCanvas, mnCurrentPage ) ;
			draw( mFrontBitmap ) ;
			mParent.setVisibility() ;
			mParent.setPageText() ;
		}
	}
	
	void deleteFile( int index )
	{
		File file = new File( url ) ;
		if ( file.exists() )
		{
			File f = new File( url + "/" + mLists.get( index ) ) ;
			if ( f.exists() )
			{
				f.delete() ;
				mLists.remove( index ) ;
			}
		}
	}
	
	public void setParent( LookBook_View v )
	{
		mParent = v ;
	}
	
	private class FlipHandler extends Handler
	{
		public void handleMessage( Message message )
		{
			switch ( message.what )
			{
				case EVENT_MOUSEDOWN :
				{
					try
					{
						startDraggingRealBook() ;
					}
					catch ( UnsupportedEncodingException e )
					{
						e.printStackTrace() ;
					}
					break ;
				}
				case EVENT_MOUSEMOVE :
				{
					try
					{
						moveDraggingRealBook() ;
					}
					catch ( UnsupportedEncodingException e )
					{
						e.printStackTrace() ;
					}
					break ;
				}
				case EVENT_MOUSEUP :
				{
					try
					{
						upDraggingRealBook() ;
					}
					catch( UnsupportedEncodingException e )
					{
						e.printStackTrace() ;
					}
					break ;
				}
				case EVENT_PAUSE :
				{
					if ( mBookPageTurnEffect.isAnimation() )
					{
						mBookPageTurnEffect.abortAnimation() ;
					}
					mbIsDragging = false ;
					draw( mFrontBitmap ) ;
					break ;
				}	
				case EVENT_FLIPACTION :
				{
					try
					{
						realBookPageTurning() ;
					}
					catch ( UnsupportedEncodingException e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace() ;
					}
					break ;
				}
				default :
					break ;
			}
		}
	}

	public final boolean canBack() throws UnsupportedEncodingException
	{
		return ( mnCurrentPage > 0 ) ? true : false ;
	}

	public final boolean canForward() throws UnsupportedEncodingException
	{
		return ( mnCurrentPage < mnCount ) ? true : false ;
	}

	public void clearMem()
	{
		if ( mBuffCanvas != null )
			mBuffCanvas = null ;
		if ( mBackBitmap != null )
		{
			mBackBitmap.recycle() ;
			mBackBitmap = null ;
		}
		if ( mFrontBitmap != null )
		{
			mFrontBitmap.recycle() ;
			mFrontBitmap = null ;
		}
	}
	
	protected void draw( Bitmap bitmap )
	{
		Canvas canvas = null ;
		try 
		{
			canvas = mHolder.lockCanvas() ;
			if ( canvas != null )
			{
				synchronized ( mHolder )
				{
					canvas.drawBitmap( bitmap, 0, 0, mPaint ) ;
				}
			}
		}
		finally
		{
			if ( canvas != null ) mHolder.unlockCanvasAndPost( canvas ) ;
		}
	}
	
	protected void drawBitmapForDragging( int endPage )
	{
		mBuffCanvas.setBitmap( mBackBitmap ) ;
		drawPage( mBuffCanvas, endPage ) ;
	}
	
	protected void startDraggingRealBook() throws UnsupportedEncodingException
	{
    	mnPageScrollDir = mFirstPt.x >= mnWidth / 2 ? FLIP_ORIENT_INC : FLIP_ORIENT_DEC ;
    	if ( mnPageScrollDir == FLIP_ORIENT_INC && canForward() )
    	{
    		drawBitmapForDragging( mnCurrentPage + 1 ) ;
            mbIsDragging = true ;
            mBookPageTurnEffect.setFolderBgColor( 0xdead ) ;
            mBookPageTurnEffect.startFlip( mnTouchX, mnTouchY, mnWidth, mnHeight, mFrontBitmap, mBackBitmap, true ) ;
            mnCurrentPage++ ;
    		mParent.setPageText() ;
    		mParent.setVisibility() ;
    	}
    	else if ( mnPageScrollDir == FLIP_ORIENT_DEC && canBack() )
    	{
    		drawBitmapForDragging( mnCurrentPage - 1 ) ;
            mbIsDragging = true ;
            mBookPageTurnEffect.setFolderBgColor( 0xdead ) ;
            mBookPageTurnEffect.startFlip( mnTouchX, mnTouchY, mnWidth, mnHeight, mFrontBitmap, mBackBitmap, false ) ;
            mnCurrentPage-- ;
    		mParent.setPageText() ;
    		mParent.setVisibility() ;
    	}
    	else
    	{
    		mbIsDragging = false ;
    		return ;
    	}
        
    	Canvas canvas = null ;
        try
        {
        	canvas = mHolder.lockCanvas() ;
        	if ( canvas != null )
        	{
	        	synchronized ( mHolder )
	        	{
	                mBookPageTurnEffect.draw( canvas ) ;
	        	}
        	}
        }
        finally
        {
            if ( canvas != null ) mHolder.unlockCanvasAndPost( canvas ) ;
        }
	}

	protected void moveDraggingRealBook() throws UnsupportedEncodingException
	{
        if ( mbIsDragging )
        {
            mBookPageTurnEffect.fliping( mnTouchX, mnTouchY ) ;
        }
    	Canvas canvas = null ;
        try
        {
        	canvas = mHolder.lockCanvas() ;
        	if ( canvas != null )
        	{
	        	synchronized ( mHolder )
	        	{
	                mBookPageTurnEffect.draw( canvas ) ;
	        	}
        	}
        }
        finally
        {
            if ( canvas != null ) mHolder.unlockCanvasAndPost( canvas ) ;
        }
	}

	protected void upDraggingRealBook() throws UnsupportedEncodingException
	{
        mBookPageTurnEffect.startAnimation( getContext(), mnPageTurnningDuration ) ;
        mBookPageTurnEffect.doAnimation() ;
        mHandler.sendEmptyMessage( EVENT_FLIPACTION ) ;
	}
	
	protected void realBookPageTurning() throws UnsupportedEncodingException 
	{
		Canvas canvas = null ;
		try
		{
			canvas = mHolder.lockCanvas() ;
			if ( canvas != null )
			{
				synchronized ( mHolder )
				{
					if ( !mBookPageTurnEffect.doAnimation() )
					{
						mBuffCanvas.setBitmap( mFrontBitmap ) ;
						drawPage( mBuffCanvas, mnCurrentPage ) ;
					}
					else
					{
						mBookPageTurnEffect.draw( canvas ) ;
						mHandler.sendEmptyMessage( EVENT_FLIPACTION ) ;
					}
				}
			}
		}
		finally
		{
			if ( canvas != null ) mHolder.unlockCanvasAndPost( canvas ) ;
		}
	}

	protected void prepareBitmap()
	{
		if ( mFrontBitmap != null )
		{
			mFrontBitmap.recycle() ;
			mFrontBitmap = null ;
		}
		mFrontBitmap = Bitmap.createBitmap( mnWidth, mnHeight, Bitmap.Config.ARGB_8888 ) ;
		if ( mBackBitmap != null )
		{
			mBackBitmap.recycle() ;
			mBackBitmap = null ;
		}
		mBackBitmap = Bitmap.createBitmap( mnWidth, mnHeight, Bitmap.Config.ARGB_8888 ) ;
	}

	protected final void startRealBookPageTurning( int endPage ) throws Exception
	{
		mBookPageTurnEffect.abortAnimation() ;
		mHandler.removeMessages( EVENT_FLIPACTION ) ;
		drawBitmapForDragging( endPage ) ;
        mBookPageTurnEffect.setFolderBgColor( 0xdead ) ;
		boolean orient = false ;
		int startx = 0, starty = mnHeight * 4 / 5 ;
		if ( endPage > mnCurrentPage )
		{
			mnPageScrollDir = FLIP_ORIENT_INC ;
		}
		else
		{
			mnPageScrollDir = FLIP_ORIENT_DEC ;
		}
		if ( mnPageScrollDir == FLIP_ORIENT_INC )
		{
			orient = true ;
			startx = mnWidth * 9 / 10 ;
		}
		else
		{
			orient = false ;
			startx = mnWidth / 10 ;
		}
		mBookPageTurnEffect.startFlip( startx, starty, mnWidth, mnHeight, mFrontBitmap, mBackBitmap, orient ) ;
		mBookPageTurnEffect.startAnimation( getContext(), mnPageTurnningDuration ) ;
		mHandler.sendEmptyMessage( EVENT_FLIPACTION ) ;
		mnCurrentPage = endPage ;
		mParent.setPageText() ;
		mParent.setVisibility() ;
	}
	
	public void goNext() throws Exception
	{
		if ( mnCurrentPage < ( mnCount - 1 ) )
		{
			if ( ( mnCurrentPage + 5 ) < mnCount )
			{
				startRealBookPageTurning( mnCurrentPage + 5 ) ;
			}
			else
			{
				startRealBookPageTurning( mnCount - 1 ) ;
			}
		}
	}
	
	public void goPrev() throws Exception
	{
		if ( mnCurrentPage > 0 )
		{
			if ( ( mnCurrentPage - 5 ) > 0 )
			{
				startRealBookPageTurning( mnCurrentPage - 5 ) ;
			}
			else
			{
				startRealBookPageTurning( 0 ) ;
			}
		}
	}
	
	public void goLast() throws Exception
	{
		if ( mnCurrentPage < ( mnCount - 1 ) )
		{
			startRealBookPageTurning( mnCount - 1 ) ;
		}
	}
	
	public void goFirst() throws Exception
	{
		if ( mnCurrentPage > 0 )
		{
			startRealBookPageTurning( 0 ) ;
		}
	}
	
	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height )
	{
		// TODO Auto-generated method stub	
		if ( mBookPageTurnEffect.isAnimation() )
		{ 
			mBookPageTurnEffect.abortAnimation() ;
		}
        mnWidth = width ;
        mnHeight = height ;
		prepareBitmap() ;
		mBackBitmap.eraseColor( 0 ) ;
		mBuffCanvas.setBitmap( mFrontBitmap ) ;
		drawPage( mBuffCanvas, mnCurrentPage ) ;
		draw( mFrontBitmap ) ;
		mbIsDragging = false ;
	}
	
	@Override
	public void surfaceCreated( SurfaceHolder surfaceholder )
	{
	}

	@Override
	public void surfaceDestroyed( SurfaceHolder surfaceholder )
	{
		if ( mBookPageTurnEffect.isAnimation() )
		{ 
			mBookPageTurnEffect.abortAnimation() ;
		}
		mbIsDragging = false ;
	}

	private void drawPage( Canvas c, int pageNo )
	{
		Bitmap page = getBitmapFromPageNo( pageNo ) ;
		c.drawBitmap( page, 0, 0, mPaint ) ;
		page.recycle() ; page = null ;
	}
	
	public Bitmap getBitmapFromPageNo( int PageNo )
	{
		Bitmap page = null ;
		if ( mnCount <= 0 || PageNo >= mnCount )
		{
			page = BitmapFactory.decodeResource( getResources(), R.drawable.make_a_lookbook, options ) ;
			page = Bitmap.createScaledBitmap( page, mnWidth, mnHeight, true ) ;
		}
		else
		{
			page = BitmapFactory.decodeFile( url + "/" + mLists.get( PageNo ), options ) ;
			page = Bitmap.createScaledBitmap( page, mnWidth, mnHeight, true ) ;
		}
		return page ;
	}

	private Point mFirstPt = new Point( 0, 0 ) ;
	public boolean onTouch( View v, MotionEvent event )
	{
		if ( mBookPageTurnEffect.isAnimation() )
		{
			return true ;
		}
		
		int action = event.getAction() ;
		float x = event.getX() ;
		float y = event.getY() ;
		if ( action == MotionEvent.ACTION_DOWN )
		{			
			if ( !mbIsDragging )
			{
				if ( y <= mnHeight / 3 || y >= mnHeight * 2 / 3 )
				{
					mFirstPt.set( (int) x, (int) y ) ;
				}
				else
				{
					if ( y <= mnHeight / 3 )
					{
						mFirstPt.set( (int)x, mnHeight / 3 ) ;
					}
					else
					{
						mFirstPt.set( (int)x, mnHeight * 2 / 3 ) ;
					}
				}
				mnTouchX = (int) x ;
		        mnTouchY = (int) y ;
		        mHandler.sendEmptyMessage( EVENT_MOUSEDOWN ) ;
			}
		}
		else if ( action == MotionEvent.ACTION_UP )
		{
			if ( mbIsDragging )
			{
				mbIsDragging = false ;
				mHandler.sendEmptyMessage( EVENT_MOUSEUP ) ;
			}
			else
			{
				try
				{
					if ( !canForward() )
					{
						mParent.gotoCloset() ;
					}
				}
				catch ( UnsupportedEncodingException e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace() ;
				}
			}
		}
		else if ( action == MotionEvent.ACTION_MOVE )
		{
			if ( mbIsDragging )
			{
				if ( mFirstPt.y <= mnHeight / 2 )
				{
					if ( y > mnHeight / 3 )
					{
						y = mnHeight / 3 ;
					}
				}
				else
				{
					if ( y < mnHeight * 2 / 3 )
					{
						y = mnHeight * 2 / 3 ;
					}
				}
				mnTouchX = (int) x ;
		        mnTouchY = (int) y ;
		        mHandler.sendEmptyMessage( EVENT_MOUSEMOVE ) ;
			}
		}
		return true ;
	}
	
	public final void onPause()
	{
		mHandler.sendEmptyMessage( EVENT_PAUSE ) ;
	}

	public final void onResume()
	{
		if ( mBuffCanvas == null )
		{
			mBuffCanvas = new Canvas() ;
		}
	}
}
