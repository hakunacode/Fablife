package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Settings extends Activity implements OnTouchListener, OnClickListener
{
	ImageButton					exit_btn 		= null ;
	TextView					music_text		= null ;
	TextView					effect_text		= null ;
	ImageButton					music_btn		= null ;
	ImageButton					effect_btn		= null ;
	
	boolean 					music_switch	= true ;
	boolean						effect_switch	= true ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.settings ) ;
        
        exit_btn 		= (ImageButton)	findViewById( R.id.SettingExitButton ) ;
        music_text		= (TextView)	findViewById( R.id.SettingMusicLabel ) ;
        effect_text		= (TextView)	findViewById( R.id.SettingEffectLabel ) ;
        music_btn		= (ImageButton)	findViewById( R.id.SettingMusicButton ) ;
        effect_btn		= (ImageButton)	findViewById( R.id.SettingEffectButton ) ;
        
        exit_btn.setOnTouchListener( this ) ;
        music_btn.setOnClickListener( this ) ;
        effect_btn.setOnClickListener( this ) ;
        
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        music_text.setTypeface( mFace ) ;
        effect_text.setTypeface( mFace ) ;
  
        music_switch = FabLifeApplication.getProfileManager().getMusic() ;
        effect_switch = FabLifeApplication.getProfileManager().getEffect() ;
        if ( music_switch )
		{
			music_btn.setBackgroundResource( R.drawable.on ) ;
		}
		else
		{
			music_btn.setBackgroundResource( R.drawable.off ) ;
		}
		if ( effect_switch )
		{
			effect_btn.setBackgroundResource( R.drawable.on ) ;
		}
		else
		{
			effect_btn.setBackgroundResource( R.drawable.off ) ;
		}
    }
	
	@Override
	public boolean onTouch( View view, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.SettingExitButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( event.getAction() == MotionEvent.ACTION_DOWN )
			{
				exit_btn.setBackgroundResource( R.drawable.exit_c ) ;
			}
			else if ( event.getAction() == MotionEvent.ACTION_OUTSIDE || event.getAction() == MotionEvent.ACTION_UP )
			{
				exit_btn.setBackgroundResource( R.drawable.exit ) ;
				finish() ;
			}
		}
		return false;
	}

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		FabLifeApplication.playTapEffect() ;
		if ( v.getId() == R.id.SettingMusicButton )
		{
			if ( music_switch )
			{
				music_btn.setBackgroundResource( R.drawable.off ) ;
				music_switch = false ;
			}
			else
			{
				music_btn.setBackgroundResource( R.drawable.on ) ;
				music_switch = true ;
			}
			FabLifeApplication.getProfileManager().setMusic( music_switch ) ;
			FabLifeApplication.stopMainMusic() ;
			FabLifeApplication.playMainMusic() ;
		}
		else if ( v.getId() == R.id.SettingEffectButton )
		{
			if ( effect_switch )
			{
				effect_btn.setBackgroundResource( R.drawable.off ) ;
				effect_switch = false ;
			}
			else
			{
				effect_btn.setBackgroundResource( R.drawable.on ) ;
				effect_switch = true ;
			}
			FabLifeApplication.getProfileManager().setEffect( effect_switch ) ;
		}
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
