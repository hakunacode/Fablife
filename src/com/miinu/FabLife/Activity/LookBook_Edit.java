package com.miinu.FabLife.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.BackgroundManager;
import com.miinu.FabLife.ViewComponent.ModelView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LookBook_Edit extends Activity implements SurfaceHolder.Callback, Runnable, OnTouchListener, OnClickListener, OnKeyListener
{
	private Thread			mThread			= null ;
	
	private SurfaceView		mSurfaceView	= null ;
	private SurfaceHolder 	mHolder 		= null ;
	
	private ImageView		mBarView		= null ;
	private TextView		mTitleView		= null ;
	private EditText		mCaptionEdit	= null ;
	private TextView		mCaptionView	= null ;
	private ImageButton		mEditButton		= null ;
	private ImageButton		mLockButton		= null ;
	private ImageButton		mUnlockButton	= null ;
	private ImageButton		mPrevButton		= null ;
	private ImageButton		mNextButton		= null ;
	private ImageButton		mBackButton		= null ;
	private ImageButton		mSaveButton		= null ;
	private ModelView		mModelView		= null ;
	
	private int 			width			= 320 ;
	private int 			height			= 480 ;
	
	private String 			url				= "lookbook_background" ;
	private String[]		mFiles			= null ;
	private int				count			= 0 ;
	private int				current			= 0 ;
	private float			cur_pos			= 0.0f ;
	
	private Bitmap			cur_bitmap		= null ;
	private Bitmap			prev_bitmap		= null ;
	private Bitmap			next_bitmap		= null ;
	
	private Paint			paint			= null ;
	
	private int				type			= 0 ;
	
	private BitmapFactory.Options	options = new BitmapFactory.Options() ;
	
	private MediaPlayer		mRewardPlayer	= null ;
	
	private BackgroundManager	mBgManager = null ;
	
	@Override
	public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.lookbook_edit ) ;
        
        mSurfaceView 	= (SurfaceView)	findViewById( R.id.SurfaceView01 ) ;
        mHolder = mSurfaceView.getHolder() ; mHolder.addCallback( this ) ; mHolder.setType( SurfaceHolder.SURFACE_TYPE_NORMAL ) ;
        
        mBarView		= (ImageView)	findViewById( R.id.EditBarView ) ;
        mTitleView		= (TextView)	findViewById( R.id.EditTitleView ) ;
    	mCaptionEdit	= (EditText)	findViewById( R.id.EditCaptionEdit ) ;
    	mCaptionView	= (TextView)	findViewById( R.id.EditCaptionView ) ;
    	mEditButton		= (ImageButton)	findViewById( R.id.EditCaptionButton ) ;
    	mLockButton		= (ImageButton)	findViewById( R.id.EditLockedButton ) ;
    	mUnlockButton	= (ImageButton)	findViewById( R.id.EditUnlockButton ) ;
    	mPrevButton		= (ImageButton) findViewById( R.id.EditPrevButton ) ;
    	mNextButton		= (ImageButton) findViewById( R.id.EditNextButton ) ;
    	mBackButton		= (ImageButton) findViewById( R.id.EditBackButton ) ;
    	mSaveButton		= (ImageButton) findViewById( R.id.EditSaveButton ) ;
    	
    	mModelView		= (ModelView)	findViewById( R.id.EditModelView ) ;
    	
    	mCaptionEdit.setOnKeyListener( this ) ;
     	mEditButton.setOnClickListener( this ) ;
     	mUnlockButton.setOnClickListener( this ) ;
     	mPrevButton.setOnClickListener( this ) ;
     	mNextButton.setOnClickListener( this ) ;
     	mBackButton.setOnClickListener( this ) ;
     	mSaveButton.setOnClickListener( this ) ;

        mSurfaceView.setOnTouchListener( this ) ;
        
        type = getIntent().getIntExtra( "type", 0 ) ;
        
        if ( FabLifeApplication.getProfileManager().getEffect() )
        {
        	mRewardPlayer = MediaPlayer.create( this, R.raw.cash_dutydone ) ;
        }
        
        Typeface face = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mTitleView.setTypeface( face ) ;
        mCaptionEdit.setTypeface( face ) ;
        mCaptionView.setTypeface( face ) ;
        
        paint = new Paint() ; paint.setAntiAlias( true ) ; paint.setFilterBitmap( true ) ;
        options.inPurgeable = true ;
        try 
        {
        	mFiles = getAssets().list( url ) ;
        	count = mFiles.length ;
        }
        catch ( Exception e )
        {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        
	    Date today = new Date() ;
        String date = new SimpleDateFormat( "dd MMMMMMMMM yyyy" ).format( today ) ;
        
        mTitleView.setText( date ) ;
		
		if ( type == 2 )
		{
			mEditButton.setVisibility( View.GONE ) ;
			mCaptionEdit.setVisibility( View.GONE ) ;
			mCaptionView.setText( getString( R.string.duty01_name + FabLifeApplication.getProfileManager().getTask() - 1 ) ) ;
		}
		
		mBgManager = new BackgroundManager( this ) ;
		overridePendingTransition( android.R.anim.fade_in, R.anim.hold ) ;
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
		width = mSurfaceView.getWidth() ;
		height = mSurfaceView.getHeight() ;
		mThread = new Thread( this ) ;
		mThread.start() ;
	}

	@Override
	public void surfaceDestroyed( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
		mThread.stop() ;
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try 
		{
			InputStream is = getAssets().open( url + "/" + mFiles[ 0 ] ) ;
			cur_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			
			Canvas c = null ;
			try
			{
				c = mHolder.lockCanvas( null ) ;
				synchronized ( mHolder )
				{
					c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
					Rect rect = new Rect( 0, 0, width, height ) ;
					c.drawBitmap( cur_bitmap, null, rect, paint ) ;
				}
			}
			finally
			{
				if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
			}
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cur_bitmap.recycle() ; cur_bitmap = null ;
	}
	
	void drawBitmaps()
	{
		Canvas c = null ;
		try
		{
			c = mHolder.lockCanvas( null ) ;
			synchronized ( mHolder )
			{
				c.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
				if ( prev_bitmap != null && !prev_bitmap.isRecycled() )
				{
					Rect rect = new Rect( (int)( cur_pos - width ), 0, (int)( cur_pos ), height ) ;
					c.drawBitmap( prev_bitmap, null, rect, paint ) ;
				}
				if ( cur_bitmap != null && !cur_bitmap.isRecycled() )
				{
					Rect rect = new Rect( (int)( cur_pos ), 0, (int)( cur_pos + width ), height ) ;
					c.drawBitmap( cur_bitmap, null, rect, paint ) ;
				}
				if ( next_bitmap != null && !next_bitmap.isRecycled() )
				{
					Rect rect = new Rect( (int)( cur_pos + width ), 0, (int)( cur_pos + 2 * width ), height ) ;
					c.drawBitmap( next_bitmap, null, rect, paint ) ;
				}
			}
		}
		finally
		{
			if ( c != null ) { mHolder.unlockCanvasAndPost( c ) ; }
		}
	}
	
	void plusPageAnimation()
	{
		try
		{
			getBitmaps() ;
		}
		catch( Exception e )
		{
			e.printStackTrace() ;
		}
		int step = 20 ;
		float diff = -( width / step ) ;
		for ( int i = 0 ; i < step ; i++ )
		{
			cur_pos += diff ;
			drawBitmaps() ;
		}
		current++ ;
		cur_pos = 0 ;
		if ( mBgManager.contains( mFiles[ current ] ) )
		{
		   	mLockButton.setVisibility( View.VISIBLE ) ;
	     	mUnlockButton.setVisibility( View.VISIBLE ) ;
		}
		else
		{
			mLockButton.setVisibility( View.GONE ) ;
	     	mUnlockButton.setVisibility( View.GONE ) ;
		}
	}
	
	void minusPageAnimation()
	{
		try
		{
			getBitmaps() ;
		}
		catch( Exception e )
		{
			e.printStackTrace() ;
		}
		int step = 20 ;
		float diff = width / step ;
		for ( int i = 0 ; i < step ; i++ )
		{
			cur_pos += diff ;
			drawBitmaps() ;
		}
		current-- ;
		cur_pos = 0 ;
		if ( mBgManager.contains( mFiles[ current ] ) )
		{
		   	mLockButton.setVisibility( View.VISIBLE ) ;
	     	mUnlockButton.setVisibility( View.VISIBLE ) ;
		}
		else
		{
			mLockButton.setVisibility( View.GONE ) ;
	     	mUnlockButton.setVisibility( View.GONE ) ;
		}
	}
	
	void touchupAnimation()
	{
		float diff = 0 ;
		int step = 10 ;
		if ( current == 0 )
		{
			if ( cur_pos <= ( -( width / 2 ) ) )
			{
				diff = ( Math.abs( cur_pos ) - width ) / step ;
				current++ ;
			}
			else if ( cur_pos > ( -( width / 2 ) ) )
			{
				diff = -( cur_pos / step ) ;
			}
		}
		else if ( current > 0 && ( current + 1 ) < count )
		{
			if ( cur_pos <= ( -( width / 2 ) ) )
			{
				diff = ( Math.abs( cur_pos ) - width ) / step ;
				current++ ;
			}
			else if ( ( cur_pos > ( -( width / 2 ) ) ) && ( cur_pos <= ( width / 2 ) ) )
			{
				diff = -cur_pos / step ;
			}
			else if ( cur_pos > ( width / 2 ) )
			{
				diff = ( width - cur_pos ) / step ;
				current-- ;
			}
		}
		else if ( ( current + 1 ) == count )
		{
			if ( cur_pos <= ( width / 2 ) )
			{
				diff = -cur_pos / step ;
			}
			else if ( cur_pos > ( width / 2 ) )
			{
				diff = ( width - cur_pos ) / step ;
				current-- ;
			}
		}

		for ( int i = 0 ; i < step ; i++ )
		{
			cur_pos += diff ;
			drawBitmaps() ;
		}
		
		if ( mBgManager.contains( mFiles[ current ] ) )
		{
		   	mLockButton.setVisibility( View.VISIBLE ) ;
	     	mUnlockButton.setVisibility( View.VISIBLE ) ;
		}
		else
		{
			mLockButton.setVisibility( View.GONE ) ;
	     	mUnlockButton.setVisibility( View.GONE ) ;
		}
		cur_pos = 0 ;
	}
	
	void getBitmaps() throws Exception
	{
		if ( prev_bitmap != null ) prev_bitmap.recycle() ;
		if ( cur_bitmap	!= null ) cur_bitmap.recycle() ;
		if ( next_bitmap != null ) next_bitmap.recycle() ;
		System.gc() ;
		try
		{
			InputStream is = null ;
			if ( current != 0 )
			{
				is = getAssets().open( url + "/" + mFiles[ current - 1 ] ) ;
				prev_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
				is.close() ;
			}
			
			is = getAssets().open( url + "/" + mFiles[ current ] ) ;
			cur_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			
			if ( ( current + 1 ) < count )
			{
				is = getAssets().open( url + "/" + mFiles[ current + 1 ] ) ;
				next_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
				is.close() ;
			}
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	float tmpx ;
	@Override
	public boolean onTouch( View view, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.SurfaceView01 )
		{
			if ( event.getAction() == MotionEvent.ACTION_DOWN )
			{
				tmpx = event.getX() ;
				try
				{
					getBitmaps() ;
				}
				catch( Exception e )
				{
					e.printStackTrace() ;
				}
			}
			else if ( event.getAction() == MotionEvent.ACTION_MOVE )
			{
				cur_pos += ( event.getX() - tmpx ) ;
				drawBitmaps() ;
				tmpx = event.getX() ;
			}
			else if ( event.getAction() == MotionEvent.ACTION_UP )
			{
				touchupAnimation() ;
			}
		}
		return true ;
	}

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.EditCaptionButton )
		{
			FabLifeApplication.playTapEffect() ;
			mCaptionView.setVisibility( View.GONE ) ;
			mCaptionEdit.setVisibility( View.VISIBLE ) ;
		}
		else if ( v.getId() == R.id.EditBackButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( mRewardPlayer != null )
			{
				if ( mRewardPlayer.isPlaying() ) mRewardPlayer.stop() ;
				mRewardPlayer.release() ;
				mRewardPlayer = null ;
			}
			try
	 		{
	 			mModelView.clear() ;
	 		}
	 		catch( Exception e )
	 		{
	 			e.printStackTrace() ;
	 		}
			FabLifeApplication.getBuiedManager().clearTemp() ;
			FabLifeApplication.getAppliedManager().clearTemp() ;
			try
			{
				System.gc() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			Intent intent = new Intent( LookBook_Edit.this, MyCloset.class ) ;
			intent.putExtra( "type", type ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( v.getId() == R.id.EditNextButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( ( current + 1 ) < count )
			{
				plusPageAnimation() ;
			}
		}
		else if ( v.getId() == R.id.EditPrevButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( current > 0 )
			{
				minusPageAnimation() ;
			}
		}
		else if ( v.getId() == R.id.EditUnlockButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( mBgManager.contains( mFiles[ current ] ) )
			{
				if ( FabLifeApplication.getProfileManager().getCoin() < mBgManager.getCoins( mFiles[ current ] ) )
				{
					showNotEnoughDialog() ;
				}
				else
				{
					showApplyDialog() ;
				}
			}
		}
		else if ( v.getId() == R.id.EditSaveButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( mBgManager.contains( mFiles[ current ] ) )
			{
				showUnlockDialog() ;
			}
			else
			{
				if ( saveBitmap() )
					showSavedDialog() ;
			}
		}
	}
	
	boolean saveBitmap()
	{
		if ( !Environment.getExternalStorageState().equalsIgnoreCase( Environment.MEDIA_MOUNTED ) )
		{
			Toast.makeText( this, "Your device has no external storage.", Toast.LENGTH_SHORT ).show() ;
			return false ;
		}
		String out_url = Environment.getExternalStorageDirectory().getPath() + "/apps/FabLife/lookbook_saved" ;
		try
		{
			Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 ) ;
			
			InputStream is = getAssets().open( url + "/" + mFiles[ current ] ) ;
			Bitmap bg = BitmapFactory.decodeStream( is, null, options ) ;
			is.close() ;
			
			Canvas c = new Canvas() ;
			c.setBitmap( bitmap ) ;
			
			Rect bg_rect = new Rect( 0, 0, width, height ) ;
			c.drawBitmap( bg, null, bg_rect, paint ) ;
			
			Bitmap avatar = mModelView.getSaveBitmap() ;
			int x = mModelView.getLeft() ;
			int y = mModelView.getTop() ;
			int w = mModelView.getWidth() ;
			int h = mModelView.getHeight() ;
			int b_width = (int)( avatar.getWidth() * h / avatar.getHeight() ) ;
			Rect dst = new Rect( x + ( w - b_width ) / 2, y, x + ( w - b_width ) / 2 + b_width, y + h ) ;
			c.drawBitmap( avatar, null, dst, paint ) ;
			
			String title = mTitleView.getText().toString() ;
			String caption = mCaptionView.getText().toString() ;
			
			Typeface t_face = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
			Paint tpaint = new Paint() ; tpaint.setTextAlign( Align.CENTER ) ; tpaint.setColor( Color.WHITE ) ;
	        tpaint.setAntiAlias( true ) ; tpaint.setTypeface( t_face ) ; tpaint.setTextScaleX( 0.8f ) ; 
	        float 	scale = width / 320.0f ;
			int 	date_text_size		= (int)( 16 * scale ) ;
			int		caption_text_size	= (int)( 14 * scale ) ;
			int		date_x_pos			= width / 2 ;
			int		date_y_pos			= (int)( 10 * scale ) ;
			int		caption_x_pos		= width / 2 ;
			int		caption_y_pos		= (int)( 30 * scale ) ;
			
			Rect bar_rect = new Rect( 0, 0, width, (int)( 63 * scale ) ) ;
			BitmapDrawable d = (BitmapDrawable) mBarView.getBackground() ;
			Bitmap bar = d.getBitmap() ;
			c.drawBitmap( bar, null, bar_rect, null ) ;
			tpaint.setTextSize( date_text_size ) ;
			c.drawText( title, date_x_pos, date_y_pos + date_text_size, tpaint ) ;
			
			tpaint.setTextSize( caption_text_size ) ;
			c.drawText( caption, caption_x_pos, caption_y_pos + ( caption_y_pos + caption_text_size ) / 2, tpaint ) ;
			
			Date today = new Date() ;
			String fileName = "/" +  new SimpleDateFormat( "yyMMddHHmmss" ).format( today ) ;
			fileName += ".timg" ;
			FileOutputStream out ;
			File file = new File( out_url ) ;
			if( !file.isDirectory() )
			{
				file.mkdirs() ;
			}
			
			file = new File( out_url + fileName ) ;
			if( !file.exists() ) 
			{
				file.createNewFile() ;
			}
			out =  new FileOutputStream( file ) ;
			bitmap.compress( Bitmap.CompressFormat.PNG, 100, out ) ;
			out.flush() ;
			out.close() ;
			
			bitmap.recycle() ; bitmap = null ;
		}
		catch( Exception e )
		{
			Toast.makeText( this, "File save failed. Error : " + e.getMessage(), Toast.LENGTH_SHORT ).show() ;
			return false ;
		}
		return true ;
	}
	
	private void showApplyDialog()
	{
		String message = "Would you like to unlock this background for " ;
		message += mBgManager.getCoins( mFiles[ current ] ) ;
		message += " coins?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() - mBgManager.getCoins( mFiles[current] ) ) ;
	    			mBgManager.remove( mFiles[ current ] ) ;
	    			mLockButton.setVisibility( View.GONE ) ;
	    			mUnlockButton.setVisibility( View.GONE ) ;
	            }
	    	}
	    )
		.setNegativeButton( "NO", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	private void showUnlockDialog()
	{
		String message = "This background is locked. Please tap \"Unlock\" to use it." ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setPositiveButton( "OK", null ) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	private void showNotEnoughDialog()
	{
		String message = "Not enough coins? Would you like to go to ATM?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setCancelable( false )
		.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			if ( mRewardPlayer != null )
	    			{
	    				if ( mRewardPlayer.isPlaying() ) mRewardPlayer.stop() ;
	    				mRewardPlayer.release() ;
	    				mRewardPlayer = null ;
	    			}
	    			try
	    	 		{
	    	 			mModelView.clear() ;
	    	 		}
	    	 		catch( Exception e )
	    	 		{
	    	 			e.printStackTrace() ;
	    	 		}
	    			FabLifeApplication.getBuiedManager().clearTemp() ;
	    			FabLifeApplication.getAppliedManager().clearTemp() ;
	    			try
	    			{
	    				System.gc() ;
	    			}
	    			catch( Exception e )
	    			{
	    				e.printStackTrace() ;
	    			}
	    			Intent intent = new Intent( LookBook_Edit.this, Bank.class ) ;
	    			startActivity( intent ) ;
	    			finish() ;
	            }
	    	}
	    )
		.setNegativeButton( "NO", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	private void showSavedDialog()
	{
		String message = "Saved to your LookBook." ;
		if ( type == 2 )
		{
			message = "You have been rewarded with 1 Cash for completing the task." ;
			FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 1 ) ;
			if ( mRewardPlayer != null )
			{
				if ( FabLifeApplication.getProfileManager().getEffect() )
				{
					mRewardPlayer.start() ;
				}
			}
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setCancelable( false )
		.setPositiveButton( "OK", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			if ( type == 2 )
					{
						FabLifeApplication.getProfileManager().setCompleteTaskDate() ;
						FabLifeApplication.getProfileManager().writeToFile() ;
					}

					if ( Math.random() * 10 < 4 )
					{
						Intent intent = new Intent( LookBook_Edit.this, Flirt.class ) ;
						startActivityForResult( intent, 0 ) ;
					}
					else
					{
						if ( mRewardPlayer != null )
		    			{
		    				if ( mRewardPlayer.isPlaying() ) mRewardPlayer.stop() ;
		    				mRewardPlayer.release() ;
		    				mRewardPlayer = null ;
		    			}
						try
		    	 		{
		    	 			mModelView.clear() ;
		    	 		}
		    	 		catch( Exception e )
		    	 		{
		    	 			e.printStackTrace() ;
		    	 		}
		    			FabLifeApplication.getBuiedManager().clearTemp() ;
		    			FabLifeApplication.getAppliedManager().clearTemp() ;
		    			try
		    			{
		    				System.gc() ;
		    			}
		    			catch( Exception e )
		    			{
		    				e.printStackTrace() ;
		    			}
						Intent intent = new Intent( LookBook_Edit.this, LookBook_View.class ) ;
						intent.putExtra( "fromCloset", true ) ;
						startActivity( intent ) ;
						finish() ;
					}
	            }
	    	}
	    ) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) 
	{
		if ( requestCode == 0 )
		{
			if ( mRewardPlayer != null )
			{
				if ( mRewardPlayer.isPlaying() ) mRewardPlayer.stop() ;
				mRewardPlayer.release() ;
				mRewardPlayer = null ;
			}
			try
	 		{
	 			mModelView.clear() ;
	 		}
	 		catch( Exception e )
	 		{
	 			e.printStackTrace() ;
	 		}
			FabLifeApplication.getBuiedManager().clearTemp() ;
			FabLifeApplication.getAppliedManager().clearTemp() ;
			try
			{
				System.gc() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			Intent intent = new Intent( this, LookBook_View.class ) ;
			intent.putExtra( "fromCloset", true ) ;
			startActivity( intent ) ;
			finish() ;
		}
	}

	@Override
	public boolean onKey( View v, int keyCode, KeyEvent event )
	{
		// TODO Auto-generated method stub
		if ( keyCode == KeyEvent.KEYCODE_ENTER )
		{
			if ( v.getVisibility() == View.VISIBLE )
			{
				mCaptionView.setVisibility( View.VISIBLE ) ;
				mCaptionView.setText( mCaptionEdit.getText().toString() ) ;
				mCaptionEdit.setVisibility( View.GONE ) ;
			}
		}
		return false ;
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		FabLifeApplication.stopMainMusic() ;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		FabLifeApplication.playMainMusic() ;
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		return ;
	}
}
