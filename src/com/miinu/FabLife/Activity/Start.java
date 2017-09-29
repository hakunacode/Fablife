package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Start extends Activity implements OnClickListener
{
	ImageView start_btn 		= null ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.start ) ;
        
        start_btn 	= (ImageView)	findViewById( R.id.ImageView02 ) ;
        
        start_btn.setOnClickListener( this ) ;
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
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		finish() ;
	}

	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
	}
}
