package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelUp extends Activity implements OnClickListener
{
	private	TextView	msg_view		= null ;
	private	ImageButton ok_btn 			= null ;
	
	private	MediaPlayer	mPlayer			= null ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.levelup ) ;
        
        msg_view	= (TextView)	findViewById( R.id.LevelUpMsgView ) ;
        ok_btn 	= (ImageButton)	findViewById( R.id.LevelUpOkButton ) ;
        
        String msg = "You are now level" + FabLifeApplication.getProfileManager().getLevel() + "." ;
        msg += "\nYou got bonus of " + ( 30 + ( FabLifeApplication.getProfileManager().getLevel() - 2 ) * 20 ) + " coins!" ;
        
        Typeface t_face = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        msg_view.setTypeface( t_face ) ;
        msg_view.setText( msg ) ;
        
        ok_btn.setOnClickListener( this ) ;
        
        if ( FabLifeApplication.getProfileManager().getEffect() )
        {
        	mPlayer = MediaPlayer.create( this, R.raw.uplevel ) ;
        	mPlayer.start() ;
        }
        overridePendingTransition( android.R.anim.fade_in, R.anim.hold ) ;
    }

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( mPlayer != null )
		{
			if ( mPlayer.isPlaying() ) mPlayer.stop() ;
			mPlayer.release() ;
			mPlayer = null ;
		}
		finish() ;
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		if ( Shop.mShopPlayer != null ) 	Shop.mShopPlayer.pause() ;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		if ( Shop.mShopPlayer != null )
		{
			if ( FabLifeApplication.getProfileManager().getEffect() )
				Shop.mShopPlayer.start() ;
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		return ;
	}
}
