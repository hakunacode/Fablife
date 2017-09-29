package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Flirt extends Activity implements OnClickListener
{
	private	ImageView	mManView		= null ;
	private	ImageView	mHeart01		= null ;
	private	ImageView	mHeart02		= null ;
	private	ImageView	mHeart03		= null ;
	private	ImageView	mHeart04		= null ;
	private	ImageView	mHeart05		= null ;
	private	ImageView	mHeart06		= null ;
	private	ImageView	mHeart07		= null ;
	private	ImageView	mHeart08		= null ;
	
	private	ImageButton mThanksBtn	 	= null ;
	private	ImageButton mNoThanksBtn 	= null ;
	
	private	TextView	mTextView		= null ;
	
	private	MediaPlayer	mFlirtPlayer	= null ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.flirt ) ;
        
        mManView		= (ImageView)	findViewById( R.id.ManView ) ;
        mHeart01		= (ImageView)	findViewById( R.id.HeartView01 ) ;
        mHeart02		= (ImageView)	findViewById( R.id.HeartView02 ) ;
        mHeart03		= (ImageView)	findViewById( R.id.HeartView03 ) ;
        mHeart04		= (ImageView)	findViewById( R.id.HeartView04 ) ;
        mHeart05		= (ImageView)	findViewById( R.id.HeartView05 ) ;
        mHeart06		= (ImageView)	findViewById( R.id.HeartView06 ) ;
        mHeart07		= (ImageView)	findViewById( R.id.HeartView07 ) ;
        mHeart08		= (ImageView)	findViewById( R.id.HeartView08 ) ;
        
        mThanksBtn		= (ImageButton)	findViewById( R.id.FlirtThankButton ) ;
        mNoThanksBtn	= (ImageButton)	findViewById( R.id.FlirtNoThankButton ) ;
        
        mTextView		= (TextView)	findViewById( R.id.FlirtTextView ) ;
        
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mTextView.setTypeface( mFace ) ;
        
        int flirt = ( (int)( Math.random() * 100 ) ) % 30 ;
        
        mTextView.setText( getString( R.string.flirt01 + flirt ) ) ;
        
        mThanksBtn.setOnClickListener( this ) ;
        mNoThanksBtn.setOnClickListener( this ) ;
        
        flirt = ( (int)( Math.random() * 100 ) ) % 10 ;
        mManView.setBackgroundResource( R.drawable.flirt_1 + flirt ) ;
        
        Handler handler = new Handler()
        {
			@Override
			public void handleMessage( Message msg )
			{
				// TODO Auto-generated method stub
				int heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart01.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart02.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart03.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart04.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart05.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart06.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart07.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				heart = ( (int)( Math.random() * 100 ) ) % 4 ;
				mHeart08.setBackgroundResource( R.drawable.heart_frame1 + heart ) ;
				sendEmptyMessageDelayed( 0, 200 ) ;
			}	
        } ;
        handler.sendEmptyMessage( 0 ) ;
        
        mFlirtPlayer = MediaPlayer.create( this, R.raw.flirt ) ;
        mFlirtPlayer.setLooping( true ) ;
        
        if ( FabLifeApplication.getProfileManager().getEffect() )
        {
        	mFlirtPlayer.start() ;
        }
        overridePendingTransition( R.anim.zoom_enter, R.anim.hold ) ;
    }

	@Override
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		switch ( view.getId() )
		{
			case R.id.FlirtThankButton :
				FabLifeApplication.playTapEffect() ;
				showBonusDialog() ;
				break ;
			case R.id.FlirtNoThankButton :
				FabLifeApplication.playTapEffect() ;
				if ( mFlirtPlayer != null )
				{
					if ( mFlirtPlayer.isPlaying() ) mFlirtPlayer.stop() ;
					mFlirtPlayer.release() ;
					mFlirtPlayer = null ;
				}
				finish() ;
				break ;
		}
	}
	
	private void showBonusDialog()
	{
		String message = "Flirt Bonus! You've got reward of 18 coins for being hot and fablous! The more you dress up abd being hot, the most chance to get flirted by hot guys." ;
		FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 18 ) ;
		
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
		.setCancelable( false )
		.setPositiveButton( "OK", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			FabLifeApplication.getProfileManager().addFlirts() ;
	    			if ( mFlirtPlayer != null )
					{
						if ( mFlirtPlayer.isPlaying() ) mFlirtPlayer.stop() ;
						mFlirtPlayer.release() ;
						mFlirtPlayer = null ;
					}
	    			finish() ;
	            }
	    	}
	    ) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		if ( mFlirtPlayer != null )
		{
			mFlirtPlayer.pause() ;
		}
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		if ( mFlirtPlayer != null )
		{
			mFlirtPlayer.start() ;
		}
	}
	
	@Override
	public void onBackPressed() 
	{
	}
}
