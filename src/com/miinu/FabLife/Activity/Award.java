package com.miinu.FabLife.Activity ;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.ViewComponent.BotiqueHintView;
import android.app.Activity ;
import android.os.Bundle ;
import android.view.View ;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Award extends Activity implements OnClickListener
{
	private		ImageView		mBronze01View		= null ;
	private		ImageView		mSilver01View		= null ;
	private		ImageView		mGold01View			= null ;
	private		ImageView		mPlatinum01View		= null ;
	private		ImageView		mPink01View			= null ;
	private		ImageView		mBronze02View		= null ;
	private		ImageView		mSilver02View		= null ;
	private		ImageView		mGold02View			= null ;
	private		ImageView		mPlatinum02View		= null ;
	private		ImageView		mPink02View			= null ;
	private		ImageView		mBronze03View		= null ;
	private		ImageView		mSilver03View		= null ;
	private		ImageView		mGold03View			= null ;
	private		ImageView		mPlatinum03View		= null ;
	private		ImageView		mPink03View			= null ;
	private		ImageButton		mGoHomeButton		= null ;
	private		BotiqueHintView	mHintView			= null ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.award ) ;
        
        mBronze01View		= (ImageView)findViewById( R.id.Bronze01View ) ;
    	mSilver01View		= (ImageView)findViewById( R.id.Silver01View ) ;
    	mGold01View			= (ImageView)findViewById( R.id.Gold01View ) ;
    	mPlatinum01View		= (ImageView)findViewById( R.id.Platinum01View ) ;
    	mPink01View			= (ImageView)findViewById( R.id.Pink01View ) ;
    	mBronze02View		= (ImageView)findViewById( R.id.Bronze02View ) ;
    	mSilver02View		= (ImageView)findViewById( R.id.Silver02View ) ;
    	mGold02View			= (ImageView)findViewById( R.id.Gold02View ) ;
    	mPlatinum02View		= (ImageView)findViewById( R.id.Platinum02View ) ;
    	mPink02View			= (ImageView)findViewById( R.id.Pink02View ) ;
    	mBronze03View		= (ImageView)findViewById( R.id.Bronze03View ) ;
    	mSilver03View		= (ImageView)findViewById( R.id.Silver03View ) ;
    	mGold03View			= (ImageView)findViewById( R.id.Gold03View ) ;
    	mPlatinum03View		= (ImageView)findViewById( R.id.Platinum03View ) ;
    	mPink03View			= (ImageView)findViewById( R.id.Pink03View ) ;
    	mGoHomeButton		= (ImageButton)findViewById( R.id.AwardHomeButton ) ;
    	mHintView			= (BotiqueHintView)findViewById( R.id.HintView ) ;
    	
    	if ( FabLifeApplication.getBuiedManager().getAllCount() + FabLifeApplication.getAppliedManager().getAllCount() > 80 )
    	{
    		mBronze01View.setBackgroundResource( R.drawable.bronze1 ) ;
    	}
    	if ( FabLifeApplication.getBuiedManager().getAllCount() + FabLifeApplication.getAppliedManager().getAllCount() > 400 )
    	{
    		mSilver01View.setBackgroundResource( R.drawable.silver1 ) ;
    	}
    	if ( FabLifeApplication.getBuiedManager().getAllCount() + FabLifeApplication.getAppliedManager().getAllCount() > 1000 )
    	{
    		mGold01View.setBackgroundResource( R.drawable.gold1 ) ;
    	}
    	if ( FabLifeApplication.getBuiedManager().getAllCount() + FabLifeApplication.getAppliedManager().getAllCount() > 2000 )
    	{
    		mPlatinum01View.setBackgroundResource( R.drawable.platinum1 ) ;
    	}
    	if ( FabLifeApplication.getBuiedManager().getAllCount() + FabLifeApplication.getAppliedManager().getAllCount() > 3200 )
    	{
    		mPink01View.setBackgroundResource( R.drawable.pink1 ) ;
    	}
    	
    	if ( FabLifeApplication.getProfileManager().getFlirts() > 5 )
    	{
    		mBronze02View.setBackgroundResource( R.drawable.bronze2 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getFlirts() > 30 )
    	{
    		mSilver02View.setBackgroundResource( R.drawable.silver2 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getFlirts() > 100 )
    	{
    		mGold02View.setBackgroundResource( R.drawable.gold2 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getFlirts() > 200 )
    	{
    		mPlatinum02View.setBackgroundResource( R.drawable.platinum2 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getFlirts() > 400 )
    	{
    		mPink02View.setBackgroundResource( R.drawable.pink2 ) ;
    	}
    	
    	if ( FabLifeApplication.getProfileManager().getEarning() > 1000 )
    	{
    		mBronze03View.setBackgroundResource( R.drawable.bronze3 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getEarning() > 10000 )
    	{
    		mSilver03View.setBackgroundResource( R.drawable.silver3 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getEarning() > 100000 )
    	{
    		mGold03View.setBackgroundResource( R.drawable.gold3 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getEarning() > 850000 )
    	{
    		mPlatinum03View.setBackgroundResource( R.drawable.platinum3 ) ;
    	}
    	if ( FabLifeApplication.getProfileManager().getEarning() > 2000000 )
    	{
    		mPink03View.setBackgroundResource( R.drawable.pink3 ) ;
    	}
    	
    	mBronze01View.setOnClickListener( this ) ;
    	mSilver01View.setOnClickListener( this ) ;
    	mGold01View.setOnClickListener( this ) ;
    	mPlatinum01View.setOnClickListener( this ) ;
    	mPink01View.setOnClickListener( this ) ;
    	mBronze02View.setOnClickListener( this ) ;
    	mSilver02View.setOnClickListener( this ) ;
    	mGold02View.setOnClickListener( this ) ;
    	mPlatinum02View.setOnClickListener( this ) ;
    	mPink02View.setOnClickListener( this ) ;
    	mBronze03View.setOnClickListener( this ) ;
    	mSilver03View.setOnClickListener( this ) ;
    	mGold03View.setOnClickListener( this ) ;
    	mPlatinum03View.setOnClickListener( this ) ;
    	mPink03View.setOnClickListener( this ) ;
    	mGoHomeButton.setOnClickListener( this ) ;
    	
        overridePendingTransition( R.anim.activity_slide_top_to_bottom, R.anim.hold ) ;
    }

	@Override
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.AwardHomeButton )
		{
			finish() ;
		}
		else
		{
			mHintView.startHint( view.getId() - R.id.Bronze01View + 1 ) ;
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