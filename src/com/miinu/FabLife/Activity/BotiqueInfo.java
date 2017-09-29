package com.miinu.FabLife.Activity ;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity ;
import android.graphics.Typeface;
import android.os.Bundle ;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class BotiqueInfo extends Activity implements OnClickListener
{
	TextView		mEarningView	= null ;
	TextView		mStoreView		= null ;
	TextView		mCustomerView	= null ;
	ImageButton		mOkBtn			= null ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.botique_info ) ;
        
        mEarningView	= (TextView)findViewById( R.id.BotiqueEarningView ) ;
        mStoreView		= (TextView)findViewById( R.id.BotiqueStoreView ) ;
        mCustomerView	= (TextView)findViewById( R.id.BotiqueCustomerView ) ;
        mOkBtn			= (ImageButton)findViewById( R.id.BotiqueInfoOkButton ) ;
        
        mOkBtn.setOnClickListener( this ) ;
        
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        
        mEarningView.setTypeface( mFace ) ;
        mStoreView.setTypeface( mFace ) ;
        mCustomerView.setTypeface( mFace ) ;
        
        mEarningView.setText( "" + FabLifeApplication.getProfileManager().getEarning() ) ;
        mStoreView.setText( "" +   getIntent().getIntExtra( "shop", 0 ) ) ;
        mCustomerView.setText( "" + getIntent().getIntExtra( "customer", 0 ) ) ;
        
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
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		finish() ;
	}
}