package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.ViewComponent.ItemPreview;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Preview extends Activity implements OnClickListener
{
	ImageButton btn = null ;
	String name = null ;
	
	ItemPreview prev = null ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.preview ) ;
        
        btn = (ImageButton) findViewById( R.id.ImageButton01 ) ;
        prev = (ItemPreview)findViewById( R.id.ItemPreview ) ;
        
        btn.setOnClickListener( this ) ;
        
        name = getIntent().getStringExtra( "url" ) ;
        prev.setUrl( name ) ;
        
        overridePendingTransition( R.anim.activity_slide_bottom_to_top, R.anim.hold ) ;
    }

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
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
	}
}
