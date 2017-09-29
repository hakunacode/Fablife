package com.miinu.FabLife.Activity ;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity ;
import android.os.Bundle ;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BotiqueTip extends Activity implements OnClickListener
{
	ImageView	mView	= null ;
	ImageButton	mBtn	= null ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.botique_tip ) ;
        
        mView = (ImageView)findViewById( R.id.BotiqueTipView ) ;
        mBtn = (ImageButton)findViewById( R.id.OkButton ) ;
        
        mBtn.setOnClickListener( this ) ;
        
        if ( FabLifeApplication.getProfileManager().isFirstAddStore() )
        {
        	mView.setBackgroundResource( R.drawable.botique_build_one ) ;
        	FabLifeApplication.getProfileManager().setFirstAddStore() ;
        }
        else if ( FabLifeApplication.getProfileManager().isFirstOpen() )
        {
        	mView.setBackgroundResource( R.drawable.botique_build_two ) ;
        	FabLifeApplication.getProfileManager().setFirstOpen() ;
        }
        else if ( FabLifeApplication.getProfileManager().isFirstRestock() )
        {
        	mView.setBackgroundResource( R.drawable.botique_build_three) ;
        	FabLifeApplication.getProfileManager().setFirstRestock() ;
        }
        else if ( FabLifeApplication.getProfileManager().isFirstCollect() )
        {
        	mView.setBackgroundResource( R.drawable.botique_build_four ) ;
        	FabLifeApplication.getProfileManager().setFirstCollect() ;
        }
        
        overridePendingTransition( android.R.anim.fade_in, R.anim.hold ) ;
    }
    
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		if ( BotiqueMain.mBotiquePlayer != null )
			BotiqueMain.mBotiquePlayer.pause() ;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		if ( BotiqueMain.mBotiquePlayer != null )
		{
			if ( FabLifeApplication.getProfileManager().getEffect() )
				BotiqueMain.mBotiquePlayer.start() ;
		}
	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.OkButton )
		{
			finish() ;
		}
	}
}