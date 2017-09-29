package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class Splash extends Activity 
{
	private boolean isFadeIn = true ;
	private FrameLayout splashLayout = null ;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		// TODO Auto-generated method stub
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.splash ) ;
		
		splashLayout = ( FrameLayout ) findViewById( R.id.SplashLayout ) ;
		
		overridePendingTransition( 0, 0 ) ;
		
		Animation anim = AnimationUtils.loadAnimation( getApplicationContext(), android.R.anim.fade_in ) ;
		anim.setDuration( 2000 ) ;
		anim.setAnimationListener( animListener ) ;
		splashLayout.startAnimation( anim ) ;
	}
	
	animationListener animListener = new animationListener() ;
	private final class animationListener implements AnimationListener
	{
		@Override
		public void onAnimationEnd( Animation animation )
		{			
			if( isFadeIn )
			{
				isFadeIn = false ;
				Animation anim = AnimationUtils.loadAnimation( getApplicationContext(), android.R.anim.fade_out );
				anim.setDuration( 2000 ) ;
				anim.setAnimationListener( this ) ;
				splashLayout.startAnimation( anim ) ;				
			}
			else
			{
				splashLayout.setVisibility( View.GONE ) ;
				Intent result = new Intent( getApplicationContext(), main.class ) ;
				startActivity( result ) ;
				finish() ;
			}
		}

		@Override
		public void onAnimationRepeat( Animation animation )
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onAnimationStart( Animation animation )
		{
			// TODO Auto-generated method stub
		}
	}
}
