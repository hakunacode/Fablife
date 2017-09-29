package com.miinu.FabLife.Activity;

import java.io.IOException;
import java.io.InputStream;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.ViewComponent.ModelView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.* ;

public class MyCloset extends Activity implements OnClickListener, OnTouchListener, SurfaceHolder.Callback
{
	private final String		STRING_NONE			= "none" ;
	
	private final int			SUBJECT_ALL			= 1 ;
	private final int			SUBJECT_MAKEUP		= 10 ;
	
	private final int			SUBJECT_SKINCOLOR	= 11 ;
	private final int			SUBJECT_BLUSHON		= 12 ;
	private final int			SUBJECT_EYESHADOW	= 13 ;
	private final int			SUBJECT_MASCARA		= 14 ;
	private final int			SUBJECT_EYEBROW		= 15 ;
	private final int			SUBJECT_EYECOLOR	= 16 ;
	private final int			SUBJECT_LIPS		= 17 ;
	
	private TextView			mItemCountView		= null ;

	private ImageButton			mSubjectAllButton	= null ;
	private ImageButton			mSubject01Button	= null ;
	private ImageButton			mSubject02Button	= null ;
	private ImageButton			mSubject03Button	= null ;
	private ImageButton			mSubject04Button	= null ;
	private ImageButton			mSubject05Button	= null ;
	private ImageButton			mSubject06Button	= null ;
	private ImageButton			mSubject07Button	= null ;
	private ImageButton			mSubject08Button	= null ;
	private ImageButton			mSubject09Button	= null ;
	
	private FrameLayout			mMakeUpSubLayout	= null ;
	private ImageButton			mSkinButton			= null ;
	private ImageButton			mBlushButton		= null ;
	private ImageButton			mEyeShadowButton	= null ;
	private ImageButton			mMascaraButton		= null ;
	private ImageButton			mEyeBrowButton		= null ;
	private ImageButton			mEyeColorButton		= null ;
	private ImageButton			mLipsButton			= null ;
	
	private ImageButton			mWholeButton		= null ;
	private ImageButton			mHalfButton			= null ;
	
	private ListView			mItemListView		= null ;
	private CustomAdapter		mAdapter			= new CustomAdapter() ;
	private LayoutInflater 		mInflater 			= null ;
	private ImageButton			mBuiedUpButton		= null ;
	private ImageButton			mBuiedDownButton	= null ;
	
	private SurfaceView			mAppliedItemView	= null ;
	private SurfaceHolder		mAppliedItemHolder	= null ;
	private ImageButton			mAppliedUpButton	= null ;
	private ImageButton			mAppliedDownButton	= null ;
	
	private ImageButton			mQueryButton		= null ;
	private ImageButton			mClearAllButton		= null ;
	private ImageButton			mLookBookButton		= null ;
	private ImageButton			mSaveButton			= null ;
	private ImageButton			mGoHomeButton		= null ;
	
	private ModelView			mModelView			= null ;
	
	private ImageView			mExclamationView	= null ;
	private TextView			mTaskView			= null ;
	
	private ImageView 			mView01				= null ;
	private ImageView 			mView02				= null ;
	private ImageView 			mView03				= null ;
	
	private ImageView 			mBtn01 				= null ;
	private ImageButton 		mBtn02 				= null ;
	
	private int					mSelectSubject		= SUBJECT_ALL ;
	private boolean				whole_view			= true ;
	private int					mnType				= 0 ;
	private Options 			options 			= null ;
	
	private	Bitmap				bg					= null ;
	private	Bitmap 				item_bg				= null ;
	
	private	int 				width 				= 100 ;
	private	int 				height				= 100 ;
	private	float 				first_pos 			= 0.0f ;
	private	int					bg_x				= 0 ;
	private	int					bg_y				= 0 ;
	private	int					bg_width			= 320 ;
	private	int 				bg_height			= 480 ;
	private	Paint 				paint 				= null ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.closet ) ;
        
        mItemCountView		= (TextView)	findViewById( R.id.ClosetItemCountView ) ;
        
        mSubjectAllButton	= (ImageButton)	findViewById( R.id.SubjectAllButton ) ;
        mSubject01Button	= (ImageButton)	findViewById( R.id.Subject01Button ) ;
        mSubject02Button	= (ImageButton)	findViewById( R.id.Subject02Button ) ;
        mSubject03Button	= (ImageButton)	findViewById( R.id.Subject03Button ) ;
        mSubject04Button	= (ImageButton)	findViewById( R.id.Subject04Button ) ;
        mSubject05Button	= (ImageButton)	findViewById( R.id.Subject05Button ) ;
        mSubject06Button	= (ImageButton)	findViewById( R.id.Subject06Button ) ;
        mSubject07Button	= (ImageButton)	findViewById( R.id.Subject07Button ) ;
        mSubject08Button	= (ImageButton)	findViewById( R.id.Subject08Button ) ;
        mSubject09Button	= (ImageButton)	findViewById( R.id.Subject09Button ) ;
        
        mMakeUpSubLayout	= (FrameLayout)	findViewById( R.id.ClosetMakeUpSubLayout ) ;
    	mSkinButton			= (ImageButton)	findViewById( R.id.SkinButton ) ;
    	mBlushButton		= (ImageButton)	findViewById( R.id.BlushOnButton ) ;
    	mEyeShadowButton	= (ImageButton)	findViewById( R.id.EyeShadowButton ) ;
    	mMascaraButton		= (ImageButton)	findViewById( R.id.MascaraButton ) ;
    	mEyeBrowButton		= (ImageButton)	findViewById( R.id.EyeBrowButton ) ;
    	mEyeColorButton		= (ImageButton)	findViewById( R.id.EyeColorButton ) ;
    	mLipsButton			= (ImageButton)	findViewById( R.id.LipsButton ) ;
    	
    	mWholeButton		= (ImageButton)	findViewById( R.id.WholeButton ) ;
    	mHalfButton			= (ImageButton)	findViewById( R.id.HalfButton ) ;
    	
    	mBuiedUpButton		= (ImageButton)	findViewById( R.id.BuiedItemUpButton ) ;
    	mItemListView		= (ListView)	findViewById( R.id.ItemListView ) ;
    	mItemListView.setAdapter( mAdapter ) ;
    	mBuiedDownButton	= (ImageButton)	findViewById( R.id.BuiedItemDownButton ) ;
    	mInflater	 		= LayoutInflater.from( getApplicationContext() ) ;
    	
    	mModelView			= (ModelView)	findViewById( R.id.ModelView ) ;
    	
    	mAppliedItemView	= (SurfaceView)	findViewById( R.id.AppliedItemView ) ;
    	mAppliedItemHolder	= mAppliedItemView.getHolder() ;
    	mAppliedItemHolder.addCallback( this ) ;
    	
    	mAppliedUpButton	= (ImageButton)	findViewById( R.id.AppliedItemUpButton ) ;
    	mAppliedDownButton	= (ImageButton)	findViewById( R.id.AppliedItemDownButton ) ;
    	
    	mQueryButton		= (ImageButton)	findViewById( R.id.CLosetQueryButton ) ;
    	mClearAllButton		= (ImageButton)	findViewById( R.id.ClosetClearAllButton ) ;
    	mLookBookButton		= (ImageButton)	findViewById( R.id.CLosetLookBookButton ) ;
    	mSaveButton			= (ImageButton)	findViewById( R.id.CLosetSaveButton ) ;
    	mGoHomeButton		= (ImageButton)	findViewById( R.id.CLosetGoHomeButton ) ;
    	
    	mExclamationView	= (ImageView)	findViewById( R.id.ClosetExclamationView ) ;
    	mTaskView			= (TextView)	findViewById( R.id.ClosetTaskView ) ;
    	
    	mView01 			= (ImageView)	findViewById( R.id.ImageView01 ) ;
        mView02		 		= (ImageView)	findViewById( R.id.ImageView02 ) ;
        mView03 			= (ImageView)	findViewById( R.id.ImageView03 ) ;
        
        mBtn01 				= (ImageView)	findViewById( R.id.ImageButton01 ) ;
        mBtn02				= (ImageButton)	findViewById( R.id.ImageButton02 ) ;
        
    	/**
    	 * 0 normal
    	 * 1 from lookbook_view
    	 * 2 from lookbook
    	 */
    	mnType = getIntent().getIntExtra( "type", 0 ) ;
    	if ( mnType == 1 )
    	{
    		mSaveButton.setVisibility( View.GONE ) ;
    	}
    	else if ( mnType == 2 )
    	{
    		mSaveButton.setVisibility( View.GONE ) ;
    		mExclamationView.setVisibility( View.VISIBLE ) ;
    		mTaskView.setVisibility( View.VISIBLE ) ;
    		mTaskView.setText( getString( R.string.duty01_name + FabLifeApplication.getProfileManager().getTask() - 1 ) ) ;
    	}
    	
    	FabLifeApplication.getBuiedManager().createTemp() ;
    	FabLifeApplication.getAppliedManager().createTemp() ;
    	
    	options = new Options() ; options.inPurgeable = true ;
 		
    	bg = BitmapFactory.decodeResource( getResources(), R.drawable.closet_bg, options ) ;
		item_bg = BitmapFactory.decodeResource( getResources(), R.drawable.item_bg, options ) ;
		paint = new Paint() ; paint.setAntiAlias( true ) ; paint.setFilterBitmap( true ) ;
		
 		invalidateArrowButton() ;
 		
 		mItemCountView.setText( "" + FabLifeApplication.getBuiedManager().getCount() ) ;
 		invalidateItemList() ;
 		
        if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
        {
        	mView01.setVisibility( View.VISIBLE ) ;
			mView02.setVisibility( View.VISIBLE ) ;
			mBtn01.setVisibility( View.VISIBLE ) ;
			
            mBtn01.setOnClickListener( this ) ;
            mBtn02.setOnClickListener( this ) ;
        }
        else
        {
        	mSubjectAllButton.setOnClickListener( this ) ;
            mSubject01Button.setOnClickListener( this ) ;
            mSubject02Button.setOnClickListener( this ) ;
            mSubject03Button.setOnClickListener( this ) ;
            mSubject04Button.setOnClickListener( this ) ;
            mSubject05Button.setOnClickListener( this ) ;
            mSubject06Button.setOnClickListener( this ) ;
            mSubject07Button.setOnClickListener( this ) ;
            mSubject08Button.setOnClickListener( this ) ;
            mSubject09Button.setOnClickListener( this ) ;
            
            mSkinButton.setOnClickListener( this ) ;
        	mBlushButton.setOnClickListener( this ) ;
        	mEyeShadowButton.setOnClickListener( this ) ;
        	mMascaraButton.setOnClickListener( this ) ;
        	mEyeBrowButton.setOnClickListener( this ) ;
        	mEyeColorButton.setOnClickListener( this ) ;
        	mLipsButton.setOnClickListener( this ) ;
        	
        	mWholeButton.setOnClickListener( this ) ;
        	mHalfButton.setOnClickListener( this ) ;
        	
        	mBuiedUpButton.setOnClickListener( this ) ;
        	mBuiedDownButton.setOnClickListener( this ) ;
        	
        	mAppliedUpButton.setOnClickListener( this ) ;
        	mAppliedDownButton.setOnClickListener( this ) ;
        	
        	mQueryButton.setOnClickListener( this ) ;
        	mClearAllButton.setOnClickListener( this ) ;
        	mLookBookButton.setOnClickListener( this ) ;
        	mSaveButton.setOnClickListener( this ) ;
        	mGoHomeButton.setOnClickListener( this ) ;
        	
        	mBtn01.setOnClickListener( this ) ;
            mBtn02.setOnClickListener( this ) ;
            
            mAppliedItemView.setOnTouchListener( this ) ;
        }
        
        overridePendingTransition( R.anim.zoom_enter, R.anim.hold ) ;
    }
	
	int select_item = 0 ;
	private void showDeleteItemDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "Are you sure you want to remove this item you wear currently?" )
		.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			String item = FabLifeApplication.getAppliedManager().getNoMakeUpItemByIndex( select_item ) ;
	    			FabLifeApplication.getAppliedManager().removeNoMakeUpItemByName( item ) ;
	    			FabLifeApplication.getBuiedManager().addItem( item ) ;
	    			invalidateItemList() ;
	    			invalidateAppliedItemList() ;
	    	 		invalidateArrowButton() ;
	    	 		try
	    	 		{
	    	 			mModelView.getAvatarBitmap() ;
	    	 		}
	    	 		catch( Exception e )
	    	 		{
	    	 			e.printStackTrace() ;
	    	 		}
	            }
	    	}
	    )
		.setNegativeButton( "NO", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	void getColoredHair( Bitmap hair, int out_color )
	{
		if ( out_color == 1 )
			return ;
		
    	float r_scale = 1.0f ;
    	float g_scale = 1.0f ;
    	float b_scale = 1.0f ;
    	
    	int source_color = getResources().getColor( R.color.haircolor01 ) ;
    	int hair_color = getResources().getColor( R.color.haircolor01 + ( out_color - 1 ) ) ;
    	r_scale = (float)( ( hair_color >> 16 ) & 0xFF ) / ( ( source_color >> 16 ) & 0xFF ) ;
    	g_scale = (float)( ( hair_color >> 8 ) & 0xFF ) / ( ( source_color >> 8 ) & 0xFF ) ;
    	b_scale = (float)( hair_color & 0xFF ) / ( source_color & 0xFF ) ;
    	
    	for ( int h = 40 ; h < 290 ; h++ )
    	{
    		for ( int w = 30 ; w < 180 ; w++ )
    		{
    			int color = hair.getPixel( w, h ) ;
    			if ( color != Color.TRANSPARENT )
    			{
    				int source_alpha	= ( color >> 24 ) & 0xFF ;
    				int source_r		= ( color >> 16 ) & 0xFF ;
    				int source_g		= ( color >> 8 ) & 0xFF ;
    				int source_b		= color & 0xFF ;

    				int r	= (int) ( source_r * r_scale ) ;
    				int g	= (int) ( source_g * g_scale ) ;
    				int b	= (int) ( source_b * b_scale ) ;
    				int c = ( source_alpha << 24 ) | ( r << 16 ) | ( g << 8 ) | b ;
    				hair.setPixel( w, h, c ) ;
    			}
    		}
    	}
	}

	void getAppliedColoredHair( Bitmap hair, int out_color )
	{
		if ( out_color == 1 )
			return ;
		
    	float r_scale = 1.0f ;
    	float g_scale = 1.0f ;
    	float b_scale = 1.0f ;
    	
    	int source_color = getResources().getColor( R.color.haircolor01 ) ;
    	int hair_color = getResources().getColor( R.color.haircolor01 + ( out_color - 1 ) ) ;
    	r_scale = (float)( ( hair_color >> 16 ) & 0xFF ) / ( ( source_color >> 16 ) & 0xFF ) ;
    	g_scale = (float)( ( hair_color >> 8 ) & 0xFF ) / ( ( source_color >> 8 ) & 0xFF ) ;
    	b_scale = (float)( hair_color & 0xFF ) / ( source_color & 0xFF ) ;
    	
    	for ( int h = 0 ; h < hair.getHeight() ; h++ )
    	{
    		for ( int w = 0 ; w < hair.getWidth() ; w++ )
    		{
    			int color = hair.getPixel( w, h ) ;
    			if ( color != Color.TRANSPARENT )
    			{
    				int source_alpha	= ( color >> 24 ) & 0xFF ;
    				int source_r		= ( color >> 16 ) & 0xFF ;
    				int source_g		= ( color >> 8 ) & 0xFF ;
    				int source_b		= color & 0xFF ;

    				int r	= (int) ( source_r * r_scale ) ;
    				int g	= (int) ( source_g * g_scale ) ;
    				int b	= (int) ( source_b * b_scale ) ;
    				int c = ( source_alpha << 24 ) | ( r << 16 ) | ( g << 8 ) | b ;
    				hair.setPixel( w, h, c ) ;
    			}
    		}
    	}
	}
	
	void invalidateItemList()
	{
		int count = FabLifeApplication.getBuiedManager().getCount() ;
		if ( mSelectSubject >= SUBJECT_SKINCOLOR )
		{
			if ( count > 4 )
			{
				mBuiedUpButton.setVisibility( View.VISIBLE ) ;
				mBuiedDownButton.setVisibility( View.VISIBLE ) ;
			}
			else
			{
				mBuiedUpButton.setVisibility( View.GONE ) ;
				mBuiedDownButton.setVisibility( View.GONE ) ;
			}
		}
		else
		{
			if ( count > 5 )
			{
				mBuiedUpButton.setVisibility( View.VISIBLE ) ;
				mBuiedDownButton.setVisibility( View.VISIBLE ) ;
			}
			else
			{
				mBuiedUpButton.setVisibility( View.GONE ) ;
				mBuiedDownButton.setVisibility( View.GONE ) ;
			}
		}
		mAdapter.restore() ;
	}
	
	void invalidateAppliedItemList()
	{
		draw() ;
	}
	
	void invalidateArrowButton()
	{
		if ( FabLifeApplication.getAppliedManager().getNoMakeUpCount() > 5 )
		{
			mAppliedUpButton.setVisibility( View.VISIBLE ) ;
			mAppliedDownButton.setVisibility( View.VISIBLE ) ;
		}
		else
		{
			mAppliedUpButton.setVisibility( View.GONE ) ;
			mAppliedDownButton.setVisibility( View.GONE ) ;
		}
	}

	@Override
	public void onClick( View view )
	{
		if ( view.getId() >= R.id.SubjectAllButton && view.getId() <= R.id.Subject09Button )
		{
			FabLifeApplication.playTapEffect() ;
			int select_subject = ( view.getId() - R.id.SubjectAllButton ) / 2 +  1 ;
			
			if ( select_subject == mSelectSubject )
				return ;
			
			if ( mSelectSubject > SUBJECT_MAKEUP )
			{
				mSelectSubject = SUBJECT_MAKEUP ;
			}
			
			findViewById( R.id.SubjectAllLayout + ( mSelectSubject - 1 ) * 2 ).setBackgroundDrawable( null ) ;
			findViewById( R.id.SubjectAllLayout + view.getId() - R.id.SubjectAllButton ).setBackgroundResource( R.drawable.subject_select ) ;
			mSelectSubject = select_subject ;
			if ( mSelectSubject == SUBJECT_MAKEUP )
			{
				mMakeUpSubLayout.setVisibility( View.VISIBLE ) ;
				mSelectSubject = SUBJECT_SKINCOLOR ;
				selectMakeUpView() ;
			}
			else
			{
				mMakeUpSubLayout.setVisibility( View.GONE ) ;
			}
			FabLifeApplication.getBuiedManager().setSubject( mSelectSubject ) ;
			invalidateItemList() ;
		}
		else if ( view.getId() >= R.id.SkinButton && view.getId() <= R.id.LipsButton )
		{
			FabLifeApplication.playTapEffect() ;
			int select_subject = SUBJECT_SKINCOLOR + view.getId() - R.id.SkinButton ;
			
			if ( select_subject == mSelectSubject )
				return ;
			mSelectSubject = select_subject ;
			FabLifeApplication.getBuiedManager().setSubject( mSelectSubject ) ;
			selectMakeUpView() ;
			invalidateItemList() ;
		}
		else if ( view.getId() == R.id.WholeButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( whole_view )
				return ;
			else
			{
				mWholeButton.setBackgroundResource( R.drawable.whole_c ) ;
				mHalfButton.setBackgroundResource( R.drawable.half ) ;
				whole_view = true ;
				mModelView.wholeAnimation() ;
			}
		}
		else if ( view.getId() == R.id.HalfButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( !whole_view )
				return ;
			else
			{
				mWholeButton.setBackgroundResource( R.drawable.whole ) ;
				mHalfButton.setBackgroundResource( R.drawable.half_c ) ;
				whole_view = false ;
				mModelView.halfAnimation() ;
			}
		}
		else if ( view.getId() == R.id.AppliedItemUpButton )
		{
			FabLifeApplication.playTapEffect() ;
			upAnim() ;
		}
		else if ( view.getId() == R.id.AppliedItemDownButton )
		{
			FabLifeApplication.playTapEffect() ;
			downAnim() ;
		}
		else if ( view.getId() == R.id.BuiedItemUpButton )
		{
			FabLifeApplication.playTapEffect() ;
			mItemListView.smoothScrollBy( mItemListView.getWidth() * 5, 400 ) ;
		}
		else if ( view.getId() == R.id.BuiedItemDownButton )
		{
			FabLifeApplication.playTapEffect() ;
			mItemListView.smoothScrollBy( - mItemListView.getWidth() * 5, 400 ) ;
		}
		else if ( view.getId() == R.id.CLosetQueryButton )
		{
			FabLifeApplication.playTapEffect() ;
			mView01.setVisibility( View.VISIBLE ) ;
			mView02.setVisibility( View.VISIBLE ) ;
			mBtn01.setVisibility( View.VISIBLE ) ;
		}
		else if ( view.getId() == R.id.ImageButton01 )
		{
			FabLifeApplication.playTapEffect() ;
			mView01.setVisibility( View.GONE ) ;
			mView02.setVisibility( View.GONE ) ;
			mBtn01.setVisibility( View.GONE ) ;
			
			mView03.setVisibility( View.VISIBLE ) ;
			mBtn02.setVisibility( View.VISIBLE ) ;
		}
		else if ( view.getId() == R.id.ImageButton02 )
		{
			FabLifeApplication.playTapEffect() ;
			if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
			{
				FabLifeApplication.getBuiedManager().clearTemp() ;
				FabLifeApplication.getAppliedManager().clearTemp() ;
				try
    	 		{
    	 			mModelView.clear() ;
    	 		}
    	 		catch( Exception e )
    	 		{
    	 			e.printStackTrace() ;
    	 		}
				Intent intent = new Intent( this, main.class ) ;
				intent.putExtra( main.STRING_STEP, main.STEP_MYCLOSET ) ;
				startActivity( intent ) ;
				finish() ;
			}
			else
			{
				mView03.setVisibility( View.GONE ) ;
				mBtn02.setVisibility( View.GONE ) ;
			}
		}
		else if ( view.getId() == R.id.ClosetClearAllButton )
		{
			FabLifeApplication.playTapEffect() ;
			showRemoveAllDialog() ;
		}
		else if ( view.getId() == R.id.CLosetLookBookButton )
		{
			FabLifeApplication.playTapEffect() ;
			showApplyLookBookDialog() ;
		}
		else if ( view.getId() == R.id.CLosetSaveButton )
		{
			FabLifeApplication.playTapEffect() ;
			showSavedDialog() ;
			FabLifeApplication.getBuiedManager().save() ;
			FabLifeApplication.getAppliedManager().save() ;
			if ( FabLifeApplication.mNoClosedBitmap != null ) { FabLifeApplication.mNoClosedBitmap.recycle() ; FabLifeApplication.mNoClosedBitmap = null ; }
	    	if ( FabLifeApplication.mClosedBitmap != null ) { FabLifeApplication.mClosedBitmap.recycle() ; FabLifeApplication.mClosedBitmap = null ; }
		}
		else if ( view.getId() == R.id.CLosetGoHomeButton )
		{
			FabLifeApplication.playHomeEffect() ;
			showExitDialog() ;
		}
		else
		{
			String name = (String)( view.getTag() ) ;
			if ( name.equalsIgnoreCase( STRING_NONE ) )
			{
				String ret = FabLifeApplication.getAppliedManager().getItemFromSubject( mSelectSubject + 13 ) ;
				if ( ret != null )
				{
					FabLifeApplication.getAppliedManager().removeMakeUpItemByName( ret ) ;
					FabLifeApplication.getBuiedManager().addItem( ret ) ;
					invalidateItemList() ;
					invalidateAppliedItemList() ;
			 		invalidateArrowButton() ;
			 		try
	    	 		{
	    	 			mModelView.getAvatarBitmap() ;
	    	 		}
	    	 		catch( Exception e )
	    	 		{
	    	 			e.printStackTrace() ;
	    	 		}
				}
			}
			else
			{
				FabLifeApplication.getBuiedManager().removeByName( name ) ;
				String ret = FabLifeApplication.getAppliedManager().addItem( name ) ;
				if ( ret != null )
				{
					FabLifeApplication.getBuiedManager().addItem( ret ) ;
				}
				invalidateItemList() ;
				invalidateAppliedItemList() ;
		 		invalidateArrowButton() ;
		 		try
    	 		{
    	 			mModelView.getAvatarBitmap() ;
    	 		}
    	 		catch( Exception e )
    	 		{
    	 			e.printStackTrace() ;
    	 		}
			}
		}
	}
	
	void selectMakeUpView()
	{
		mSkinButton.setBackgroundResource( R.drawable.makeup_item01 ) ;
    	mBlushButton.setBackgroundResource( R.drawable.makeup_item02 ) ;
    	mEyeShadowButton.setBackgroundResource( R.drawable.makeup_item03 ) ;
    	mMascaraButton.setBackgroundResource( R.drawable.makeup_item04 ) ;
    	mEyeBrowButton.setBackgroundResource( R.drawable.makeup_item05 ) ;
    	mEyeColorButton.setBackgroundResource( R.drawable.makeup_item06 ) ;
    	mLipsButton.setBackgroundResource( R.drawable.makeup_item07 ) ;
    	
    	switch( mSelectSubject )
    	{
    		case SUBJECT_SKINCOLOR :
    			mSkinButton.setBackgroundResource( R.drawable.makeup_item01_c ) ;
    			break ;
    		case SUBJECT_BLUSHON :
    			mBlushButton.setBackgroundResource( R.drawable.makeup_item02_c ) ;
    			break ;
    		case SUBJECT_EYESHADOW :
    			mEyeShadowButton.setBackgroundResource( R.drawable.makeup_item03_c ) ;
    			break ;
    		case SUBJECT_MASCARA :
    			mMascaraButton.setBackgroundResource( R.drawable.makeup_item04_c ) ;
    			break ;
    		case SUBJECT_EYEBROW :
    			mEyeBrowButton.setBackgroundResource( R.drawable.makeup_item05_c ) ;
    			break ;
    		case SUBJECT_EYECOLOR :
    			mEyeColorButton.setBackgroundResource( R.drawable.makeup_item06_c ) ;
    			break ;
    		case SUBJECT_LIPS :
    			mLipsButton.setBackgroundResource( R.drawable.makeup_item07_c ) ;
    			break ;
    	}
	}
	
	private void showRemoveAllDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.removeall ) )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			removeAllAppliedItems() ;
                }
        	}
        )
		.setNegativeButton( "No", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void showSavedDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.saved_changes ) )
        .setCancelable( false )
        .setPositiveButton( "OK", null ) ;
		AlertDialog alert = builder.create() ;
		alert.show();
	}
	
	private void showApplyLookBookDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.apply_changes ) )
		.setPositiveButton( getString( R.string.lookbook_only ), new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			Intent intent = new Intent( MyCloset.this, LookBook_Edit.class ) ;
	    			intent.putExtra( "type", mnType ) ;
	    			startActivity( intent ) ;
	    			finish() ;
	            }
	    	}
	    )
		.setNegativeButton( getString( R.string.lookbook_avatar ), new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			FabLifeApplication.getAppliedManager().save() ;
	    			FabLifeApplication.getBuiedManager().save() ;
	    			if ( FabLifeApplication.mNoClosedBitmap != null ) { FabLifeApplication.mNoClosedBitmap.recycle() ; FabLifeApplication.mNoClosedBitmap = null ; }
	    	    	if ( FabLifeApplication.mClosedBitmap != null ) { FabLifeApplication.mClosedBitmap.recycle() ; FabLifeApplication.mClosedBitmap = null ; }
	    			Intent intent = new Intent( MyCloset.this, LookBook_Edit.class ) ;
	    			intent.putExtra( "type", mnType ) ;
	    			startActivity( intent ) ;
	    			finish() ;
	            }
	    	}
		) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	private void showExitDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "Are you sure you want to exit?" )
		.setPositiveButton( "YES", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	    			FabLifeApplication.getBuiedManager().clearTemp() ;
	    			FabLifeApplication.getAppliedManager().clearTemp() ;
	    			try
	    	 		{
	    	 			mModelView.clear() ;
	    	 		}
	    	 		catch( Exception e )
	    	 		{
	    	 			e.printStackTrace() ;
	    	 		}
	    	 		Intent intent = new Intent( MyCloset.this, main.class ) ;
	    	 		startActivity( intent ) ;
	    			finish() ;
	            }
	    	}
	    )
		.setNegativeButton( "NO", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	void removeAllAppliedItems()
	{
		int count = FabLifeApplication.getAppliedManager().getAllTempCount() ;
		for ( int i = 0 ; i < count ; i++ )
		{
			FabLifeApplication.getBuiedManager().addItem( FabLifeApplication.getAppliedManager().getTempItemFromAll( i ) ) ;
		}
		FabLifeApplication.getAppliedManager().removeAll() ;
		invalidateItemList() ;
		invalidateAppliedItemList() ;
 		invalidateArrowButton() ;
 		try
 		{
 			mModelView.getAvatarBitmap() ;
 		}
 		catch( Exception e )
 		{
 			e.printStackTrace() ;
 		}
	}
	
	public void draw()
	{
		Canvas canvas = null ;
		try
		{
			canvas = mAppliedItemHolder.lockCanvas( null ) ;
	 		synchronized ( mAppliedItemHolder )
	 		{
	 			canvas.drawColor( Color.TRANSPARENT, PorterDuff.Mode.CLEAR ) ;
	 			Rect bg_rect = new Rect( bg_x, bg_y, bg_x + bg_width, bg_y + bg_height ) ;
	 			canvas.drawBitmap( bg, null, bg_rect, paint ) ;
	 			int count = FabLifeApplication.getAppliedManager().getNoMakeUpCount() ;
	 			for ( int i = 0 ; i < count ; i++ )
	 			{
	 				String item = FabLifeApplication.getAppliedManager().getNoMakeUpItemByIndex( i ) ;
	 				try
	 				{	 					
	 					String url = null ;
	 					int index = 0 ;
	 					if ( item.contains( "hair" ) || item.contains( "funky" ) )
	 					{
	 						 index = item.indexOf( "_color" ) ;
	 					     url = item.substring( 0, index ) ;
	 					}
	 					else
	 					{
	 						url = item ;
	 					}
	 					
	 					InputStream is = getAssets().open( url );
	 					Bitmap item_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
	 					is.close() ;
	 					
	 					int ih = 0 ;
 						int iw = 0 ;
	 					if ( item_bitmap.getHeight() > item_bitmap.getWidth() )
	 					{
	 						ih = Math.round( width * 0.8f ) ;
	 						iw = item_bitmap.getWidth() * ih / item_bitmap.getHeight() ;
	 						item_bitmap = Bitmap.createScaledBitmap( item_bitmap, iw, ih, false ) ;
	 					}
	 					else
	 					{
	 						iw = Math.round( width * 0.8f ) ;
	 						ih = item_bitmap.getHeight() * iw / item_bitmap.getWidth() ;
	 						item_bitmap = Bitmap.createScaledBitmap( item_bitmap, iw, ih, false ) ;
	 					}
	 					
	 					if ( item.contains( "hair" ) || item.contains( "funky" ) )
	 					{
	 						getAppliedColoredHair( item_bitmap, Integer.parseInt( "" + item.charAt( index + 6 ) ) ) ;
	 					}
	 					
	 					Rect item_bg_rect = new Rect( 0, (int)( first_pos + i * width ), width, (int)( first_pos + ( i + 1 ) * width ) ) ;
	 					canvas.drawBitmap( item_bg, null, item_bg_rect, paint ) ;
	 					canvas.drawBitmap( item_bitmap, ( width - iw ) / 2, first_pos + i * width + ( width - ih ) / 2, paint ) ;
	 					item_bitmap.recycle() ; item_bitmap = null ;
	 				}
	 				catch ( Exception e )
	 				{
	 					// TODO Auto-generated catch block
	 					e.printStackTrace() ;
	 				}
	 			}
	 			if ( selected_index != 65536 )
	 			{
	 				if ( selected_index >= FabLifeApplication.getAppliedManager().getNoMakeUpCount() )
	 					selected_index = FabLifeApplication.getAppliedManager().getNoMakeUpCount() - 1 ;
	 				String item = FabLifeApplication.getAppliedManager().getNoMakeUpItemByIndex( selected_index ) ;
	 				try
	 				{
	 					String url = null ;
	 					int index = 0 ;
	 					if ( item.contains( "hair" ) || item.contains( "funky" ) )
	 					{
	 						 index = item.indexOf( "_color" ) ;
	 					     url = item.substring( 0, index ) ;
	 					}
	 					else
	 					{
	 						url = item ;
	 					}
	 					
	 					InputStream is = getAssets().open( url );
	 					Bitmap item_bitmap = BitmapFactory.decodeStream( is, null, options ) ;
	 					is.close() ;
	 					
	 					int ih = 0 ;
 						int iw = 0 ;
	 					if ( item_bitmap.getHeight() > item_bitmap.getWidth() )
	 					{
	 						ih = Math.round( width * 0.8f ) ;
	 						iw = item_bitmap.getWidth() * ih / item_bitmap.getHeight() ;
	 						item_bitmap = Bitmap.createScaledBitmap( item_bitmap, iw, ih, false ) ;
	 					}
	 					else
	 					{
	 						iw = Math.round( width * 0.8f ) ;
	 						ih = item_bitmap.getHeight() * iw / item_bitmap.getWidth() ;
	 						item_bitmap = Bitmap.createScaledBitmap( item_bitmap, iw, ih, false ) ;
	 					}
	 					
	 					if ( item.contains( "hair" ) || item.contains( "funky" ) )
	 					{
	 						getAppliedColoredHair( item_bitmap, Integer.parseInt( "" + item.charAt( index + 6 ) ) ) ;
	 					}
	 					paint.setAlpha( 0x80 ) ;
	 					canvas.drawBitmap( item_bitmap, x - iw / 2, y - ih / 2, paint ) ;
	 					paint.setAlpha( 0xFF ) ;
	 					item_bitmap.recycle() ; item_bitmap = null ;
	 				}
	 				catch ( IOException e )
	 				{
	 					// TODO Auto-generated catch block
	 					e.printStackTrace() ;
	 				}
	 			}
	 		}//sync
		}
		finally
		{
			if ( canvas != null ) { mAppliedItemHolder.unlockCanvasAndPost( canvas ) ; } 
		}
	}
	
	public void upAnim()
	{
		int count = FabLifeApplication.getAppliedManager().getNoMakeUpCount() ;
		float step = width / 5.0f ;
		for ( int i = 0 ; i < 5 ; i++ )
		{
			first_pos -= step ;
			if ( ( first_pos + width * count ) < height )
			{
				first_pos = height - width * count ;
			}
			draw() ;
		}
	}
	
	public void downAnim()
	{
		float step = width / 5.0f ;
		for ( int i = 0 ; i < 5 ; i++ )
		{
			first_pos += step ;
			if ( first_pos > 0.0f )
			{
				first_pos = 0.0f ;
			}
			draw() ;
		}
	}

	int selected_index = 65536 ;
	float x = 0 ;
	float y = 0 ;
	long touchtime = 0 ;
	@Override
	public boolean onTouch( View view, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( view.getId() == R.id.AppliedItemView )
		{
			if ( event.getAction() == MotionEvent.ACTION_DOWN )
			{
				if ( ( System.currentTimeMillis() - touchtime < 800 ) &&
						( Math.abs( event.getX() - x ) < 20 ) && ( Math.abs( event.getY() - y ) < 20 ) )
				{
					int index = (int)( y + Math.abs( first_pos ) ) / width ;
					if ( index < FabLifeApplication.getAppliedManager().getNoMakeUpCount() )
					{
						select_item = index ;
						showDeleteItemDialog() ;
					}
					touchtime = 0 ;
					selected_index = 65536 ;
					x = 0 ;
					y = 0 ;
					draw() ;
					return true ;
				}
				touchtime = System.currentTimeMillis() ;
				x = event.getX() ;
				y = event.getY() ;
				int count = FabLifeApplication.getAppliedManager().getNoMakeUpCount() ;
				if ( y >= 0 && y < ( first_pos + count * width ) )
				{
					float item_pos = ( y + Math.abs( first_pos ) ) % width ;
					if ( item_pos > 0 && item_pos < width 
						&& x > 0 && x < width	)
					{
						selected_index = (int)( y + Math.abs( first_pos ) ) / width ;
					}
				}
			}
			else if ( event.getAction() == MotionEvent.ACTION_MOVE )
			{
				x = event.getX() ;
				y = event.getY() ;
				draw() ;
			}
			else if ( event.getAction() == MotionEvent.ACTION_UP )
			{
				if ( selected_index != 65536 )
				{
					if ( selected_index < FabLifeApplication.getAppliedManager().getNoMakeUpCount() )
					{
						int index = (int)( y + Math.abs( first_pos ) ) / width ;
						String item = FabLifeApplication.getAppliedManager().getNoMakeUpItemByIndex( selected_index ) ;
						FabLifeApplication.getAppliedManager().removeNoMakeUpItemByName( item ) ;
						FabLifeApplication.getAppliedManager().addNoMakeUpItem( index, item ) ;
						try
		    	 		{
		    	 			mModelView.getAvatarBitmap() ;
		    	 		}
		    	 		catch( Exception e )
		    	 		{
		    	 			e.printStackTrace() ;
		    	 		}
					}
				}
				selected_index = 65536 ;
				draw() ;
			}
		}
		return true ;
	}

	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height )
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
		width = mAppliedItemView.getWidth() ;
		height = mAppliedItemView.getHeight() ;
		
		float scale = width / 36.0f ;
		
		bg_x = (int)( - 280 * scale ) ;
		bg_y = (int)( - 102 * scale ) ;
		bg_width = (int)( 320 * scale ) ;
		bg_height = (int)( 480 * scale ) ;
		
		draw() ;
	}

	@Override
	public void surfaceDestroyed( SurfaceHolder holder )
	{
		// TODO Auto-generated method stub
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
	
	private class CustomAdapter extends BaseAdapter
	{
		public void restore()
		{
			try
			{
				System.gc() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			notifyDataSetChanged() ;
		}
		
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			if ( mSelectSubject >= SUBJECT_SKINCOLOR )
			{
				return ( FabLifeApplication.getBuiedManager().getCount() + 1 ) ;
			}
			else
			{
				return FabLifeApplication.getBuiedManager().getCount() ;
			}
		}

		@Override
		public Object getItem( int position )
		{
			// TODO Auto-generated method stub
			return null ;
		}

		@Override
		public long getItemId( int position )
		{
			// TODO Auto-generated method stub
			return position ;
		}

		@Override
		public View getView( int position, View convertView, ViewGroup parent )
		{
			// TODO Auto-generated method stub
			ImageView view ;
			if ( convertView == null ) // convertView == null 
			{
				view = (ImageView)	mInflater.inflate( R.layout.item_list, null ) ;
				view.setOnClickListener( MyCloset.this ) ;
				view.setSoundEffectsEnabled( false ) ;
				view.setAdjustViewBounds( true ) ;
		        view.setLayoutParams( new AbsListView.LayoutParams( mItemListView.getWidth(), mItemListView.getWidth() ) ) ;
			}
			else
			{
				view = (ImageView)	convertView ;
				String tag = (String)view.getTag() ;
				if ( !tag.equalsIgnoreCase( STRING_NONE ) )
				{
					BitmapDrawable dr = (BitmapDrawable) view.getDrawable() ;
					if ( dr != null )
					{
						Bitmap bm = dr.getBitmap() ;
						if ( bm != null )
						{
							bm.recycle() ; bm = null ;
						}
						dr = null ;
					}
					view.setTag( null ) ;
				}
			}
			
			if ( mSelectSubject >= SUBJECT_SKINCOLOR ) // mSelectSubject >= SUBJECT_SKINCOLOR
			{
				if ( position == 0 )
				{
					view.setTag( STRING_NONE ) ;
					view.setImageResource( R.drawable.nomakeup ) ;
				}
				else
				{
					String item = FabLifeApplication.getBuiedManager().getItemByIndex( FabLifeApplication.getBuiedManager().getCount() - position ) ;
					view.setTag( item ) ;
					try
					{
						Bitmap item_bitmap = null ;
						String url = null ;
						int index = 0 ;
						if ( item.contains( "hair" ) || item.contains( "funky" ) )
						{
							 index = item.indexOf( "_color" ) ;
						     url = item.substring( 0, index ) ;
						}
						else
						{
							url = item ;
						}
						InputStream is = getAssets().open( url );
						item_bitmap = BitmapFactory.decodeStream( is ) ;
						is.close() ;
						if ( item.contains( "hair" ) || item.contains( "funky" ) )
						{
							try
							{
								item_bitmap = Bitmap.createScaledBitmap( item_bitmap, item_bitmap.getWidth(), item_bitmap.getHeight(), true ) ;
							}
							catch( OutOfMemoryError e )
							{
								e.printStackTrace() ;
							}
							if ( item_bitmap != null )
								getColoredHair( item_bitmap, Integer.parseInt( "" + item.charAt( index + 6 ) ) ) ;
						}
						
						if ( item_bitmap != null )
						{
							view.setImageBitmap( item_bitmap ) ;
						}
					}
					catch ( Exception e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace() ;
					}
				}
			}
			else
			{
				String item = FabLifeApplication.getBuiedManager().getItemByIndex( FabLifeApplication.getBuiedManager().getCount() - position - 1 ) ;
				view.setTag( item ) ;
				try
				{
					Bitmap item_bitmap = null ;
					String url = null ;
					int index = 0 ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
					{
						 index = item.indexOf( "_color" ) ;
					     url = item.substring( 0, index ) ;
					}
					else
					{
						url = item ;
					}
					InputStream is = getAssets().open( url );
					item_bitmap = BitmapFactory.decodeStream( is ) ;
					is.close() ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
					{
						try
						{
							item_bitmap = Bitmap.createScaledBitmap( item_bitmap, item_bitmap.getWidth(), item_bitmap.getHeight(), true ) ;
						}
						catch( OutOfMemoryError e )
						{
							e.printStackTrace() ;
						}
						if ( item_bitmap != null )
							getColoredHair( item_bitmap, Integer.parseInt( "" + item.charAt( index + 6 ) ) ) ;
					}
					
					if ( item_bitmap != null )
					{
						view.setImageBitmap( item_bitmap ) ;
					}
				}
				catch ( Exception e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace() ;
				}
			}
			return  view ;
		}
	}
}
