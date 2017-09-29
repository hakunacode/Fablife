package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class LookBook extends Activity implements OnClickListener
{
	private FrameLayout		lb_layout			= null ;
	private FrameLayout		task_layout			= null ;
	private TextView		task_name_view		= null ;
	private TextView		task_desc_view		= null ;
	private ImageButton 	do_task_btn 		= null ;
	private ImageButton		gohome_btn			= null ;

	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.lookbook ) ;
        
        lb_layout			= (FrameLayout)	findViewById( R.id.LBLayout ) ;
        task_layout			= (FrameLayout)	findViewById( R.id.LookBookTaskLayout ) ;
        task_name_view		= (TextView)	findViewById( R.id.LookBookTaskNameView ) ;
        task_desc_view		= (TextView)	findViewById( R.id.LookBookTaskDescView ) ;
        do_task_btn 		= (ImageButton)	findViewById( R.id.DoTaskButton ) ;
        gohome_btn	 		= (ImageButton)	findViewById( R.id.LookBookGoHomeButton ) ;
        
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        task_name_view.setTypeface( mFace ) ;
        task_desc_view.setTypeface( mFace ) ;
        
        if ( FabLifeApplication.getProfileManager().isPastDate() )
        {
        	task_name_view.setText( getString( R.string.duty01_name + FabLifeApplication.getProfileManager().getTask() - 1 ) ) ;
        	task_desc_view.setText( getString( R.string.duty01_description + FabLifeApplication.getProfileManager().getTask() - 1 ) ) ;
        }
        else
        {
        	 task_layout.setVisibility( View.GONE ) ;
        }
        
        do_task_btn.setOnClickListener( this ) ;
        gohome_btn.setOnClickListener( this ) ;
        
        lb_layout.setClickable( true ) ;
        lb_layout.setFocusable( true ) ;
        lb_layout.setOnClickListener( this ) ;
        
        overridePendingTransition( R.anim.activity_slide_left, R.anim.hold ) ;
    }
	
	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.LBLayout )
		{
			FabLifeApplication.playTapEffect() ;
			Intent intent = new Intent( this, LookBook_View.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( v.getId() == R.id.DoTaskButton )
		{
			FabLifeApplication.playTapEffect() ;
			Intent intent = new Intent( this, MyCloset.class ) ;
			intent.putExtra( "type", 2 ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( v.getId() == R.id.LookBookGoHomeButton )
		{
			Intent intent = new Intent( this, main.class ) ;
			startActivity( intent ) ;
			finish() ;
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
