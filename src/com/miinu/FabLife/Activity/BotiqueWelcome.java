package com.miinu.FabLife.Activity ;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity ;
import android.content.Intent;
import android.os.Bundle ;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BotiqueWelcome extends Activity implements OnClickListener
{
	ImageButton start_btn 	= null ;
	ImageButton	home_btn	= null ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.botique_welcome ) ;
        
        start_btn = (ImageButton)findViewById( R.id.BotiqueStartButton ) ;
        home_btn = (ImageButton)findViewById( R.id.BotiqueHomeButton ) ;
        
        start_btn.setOnClickListener( this ) ;
        home_btn.setOnClickListener( this ) ;
        
        overridePendingTransition( android.R.anim.fade_in, R.anim.hold ) ;
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
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.BotiqueStartButton )
		{
			FabLifeApplication.playTapEffect() ;
			FabLifeApplication.getProfileManager().setFirstBotique() ;
			Intent intent = new Intent( this, BotiqueMain.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( view.getId() == R.id.BotiqueHomeButton )
		{
			FabLifeApplication.playTapEffect() ;
			Intent intent = new Intent( this, main.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
	}
}