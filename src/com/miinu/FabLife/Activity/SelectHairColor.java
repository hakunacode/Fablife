package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SelectHairColor extends Activity implements OnClickListener
{
	ImageButton 		exit_btn 		= null ;
	ImageButton 		color1_btn 		= null ;
	ImageButton 		color2_btn 		= null ;
	ImageButton 		color3_btn 		= null ;
	ImageButton 		color4_btn 		= null ;
	ImageButton 		color5_btn 		= null ;
	ImageButton 		color6_btn 		= null ;
	ImageButton 		color7_btn 		= null ;
	ImageButton 		color8_btn 		= null ;
	
	String 				url				= null ;
	int					color			= 1 ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.haircolor ) ;
        
        exit_btn 	= (ImageButton)	findViewById( R.id.ExitButton ) ;
        color1_btn	= (ImageButton)	findViewById( R.id.Color1Button ) ;
        color2_btn	= (ImageButton)	findViewById( R.id.Color2Button ) ;
        color3_btn	= (ImageButton)	findViewById( R.id.Color3Button ) ;
        color4_btn	= (ImageButton)	findViewById( R.id.Color4Button ) ;
        color5_btn	= (ImageButton)	findViewById( R.id.Color5Button ) ;
        color6_btn	= (ImageButton)	findViewById( R.id.Color6Button ) ;
        color7_btn	= (ImageButton)	findViewById( R.id.Color7Button ) ;
        color8_btn	= (ImageButton)	findViewById( R.id.Color8Button ) ;
        
        exit_btn.setOnClickListener( this ) ;
        color1_btn.setOnClickListener( this ) ;
        color2_btn.setOnClickListener( this ) ;
        color3_btn.setOnClickListener( this ) ;
        color4_btn.setOnClickListener( this ) ;
        color5_btn.setOnClickListener( this ) ;
        color6_btn.setOnClickListener( this ) ;
        color7_btn.setOnClickListener( this ) ;
        color8_btn.setOnClickListener( this ) ;
        
        url = getIntent().getStringExtra( "url" ) ;
        color = FabLifeApplication.getHairManager().getHairColor( url ) ;
        switch( color )
        {
        	case 1 :
        		color1_btn.setBackgroundResource( R.drawable.haircolor_1_c ) ;
        		break ;
        	case 2 :
        		color2_btn.setBackgroundResource( R.drawable.haircolor_2_c ) ;
        		break ;
        	case 3 :
        		color3_btn.setBackgroundResource( R.drawable.haircolor_3_c ) ;
        		break ;
        	case 4 :
        		color4_btn.setBackgroundResource( R.drawable.haircolor_4_c ) ;
        		break ;
        	case 5 :
        		color5_btn.setBackgroundResource( R.drawable.haircolor_5_c ) ;
        		break ;
        	case 6 :
        		color6_btn.setBackgroundResource( R.drawable.haircolor_6_c ) ;
        		break ;
        	case 7 :
        		color7_btn.setBackgroundResource( R.drawable.haircolor_7_c ) ;
        		break ;
        	case 8 :
        		color8_btn.setBackgroundResource( R.drawable.haircolor_8_c ) ;
        		break ;
        }
        
        for ( int i = 1 ; i <= 8 ; i++ )
		{
			String hair_compare = url ;
			hair_compare += ( "_color" + i ) ;
			if ( FabLifeApplication.getBuiedManager().contains( hair_compare ) || FabLifeApplication.getAppliedManager().containsHair( hair_compare ) )
			{
				getButtonFromColor( i ).setBackgroundResource( R.drawable.haircolor_1_c + ( i - 1 ) * 2 ) ;
				getButtonFromColor( i ).setEnabled( false ) ;
			}
		}
    }
	
	private View getButtonFromColor( int index )
	{
		View view = null ;
		switch( index )
        {
        	case 1 :
        		view = color1_btn ;
        		break ;
        	case 2 :
        		view = color2_btn ;
        		break ;
        	case 3 :
        		view = color3_btn ;
        		break ;
        	case 4 :
        		view = color4_btn ;
        		break ;
        	case 5 :
        		view = color5_btn ;
        		break ;
        	case 6 :
        		view = color6_btn ;
        		break ;
        	case 7 :
        		view = color7_btn ;
        		break ;
        	case 8 :
        		view = color8_btn ;
        		break ;
        }
		return view ;
	}

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.ExitButton )
		{
			finish() ;
		}
		else
		{
			int select = ( v.getId() - R.id.Color1Button ) + 1 ;
			if ( color != select )
			{
				getButtonFromColor( color ).setBackgroundResource( R.drawable.haircolor_1 + ( color - 1 ) * 2 ) ;
				getButtonFromColor( select ).setBackgroundResource( R.drawable.haircolor_1_c + ( select - 1 ) * 2 ) ;
				FabLifeApplication.getHairManager().setHairColor( url, select ) ;
				color = select ;
			}
		}
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
