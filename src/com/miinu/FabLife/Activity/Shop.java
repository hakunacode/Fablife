package com.miinu.FabLife.Activity;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.ProfileManager;
import com.miinu.FabLife.Engine.ShopManager;
import com.miinu.FabLife.Engine.ShopManager.Item;
import com.miinu.FabLife.ViewComponent.ShopItemView;
import com.miinu.FabLife.ViewComponent.StatusBarCash;
import com.miinu.FabLife.ViewComponent.StatusBarCoin;
import com.miinu.FabLife.ViewComponent.StatusBarEnergy;
import com.miinu.FabLife.ViewComponent.StatusBarLevel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Shop extends Activity implements OnClickListener
{
	private final int				SUBJECT_ALL			= 1 ;
	private final int				SUBJECT_MAKEUP		= 10 ;
	
	private final int				SUBJECT_SKINCOLOR	= 11 ;
	private final int				SUBJECT_BLUSHON		= 12 ;
	private final int				SUBJECT_EYESHADOW	= 13 ;
	private final int				SUBJECT_MASCARA		= 14 ;
	private final int				SUBJECT_EYEBROW		= 15 ;
	private final int				SUBJECT_EYECOLOR	= 16 ;
	private final int				SUBJECT_LIPS		= 17 ;
	
	private	FrameLayout				mStatusBarLayout	= null ;
	private	StatusBarLevel			mStatusLevelView	= null ;
	private	StatusBarEnergy			mStatusEnergyView	= null ;
	private	StatusBarCoin			mStatusCoinView		= null ;
	private	StatusBarCash			mStatusCashView		= null ;
	private TextView				mCaptionView		= null ;
	private	TextView				mTimerView			= null ;
	
	private FrameLayout				mMainLayout			= null ;
	private TextView				mShopNameView		= null ;
	private ImageButton				mSubjectAllButton	= null ;
	private ImageButton				mSubject01Button	= null ;
	private ImageButton				mSubject02Button	= null ;
	private ImageButton				mSubject03Button	= null ;
	private ImageButton				mSubject04Button	= null ;
	private ImageButton				mSubject05Button	= null ;
	private ImageButton				mSubject06Button	= null ;
	private ImageButton				mSubject07Button	= null ;
	private ImageButton				mSubject08Button	= null ;
	private ImageButton				mSubject09Button	= null ;
	
	private FrameLayout				mMakeUpSubLayout	= null ;
	private ImageButton				mSkinButton			= null ;
	private ImageButton				mBlushButton		= null ;
	private ImageButton				mEyeShadowButton	= null ;
	private ImageButton				mMascaraButton		= null ;
	private ImageButton				mEyeBrowButton		= null ;
	private ImageButton				mEyeColorButton		= null ;
	private ImageButton				mLipsButton			= null ;
	
	private TextView				mNoItemView			= null ;
	
	private ShopItemView			mView				= null ;
	
	private ImageButton				mPrevButton			= null ;
	private ImageButton				mNextButton			= null ;
	
	private ImageButton				mBackButton			= null ;
	private ImageButton				mHomeButton			= null ;
	private LinearLayout			mPageLayout			= null ;
	
	private ImageView				mImageView01		= null ;
	private ImageView				mImageView02		= null ;
	
	private ImageView				mAnimView			= null ;
	
	private int						mSelectSubject		= SUBJECT_ALL ;
	
	private ShopManager				mShopManager		= null ;
	
	private boolean					fly_animation		= false ;
	
	private Handler					mHandler			= null ;
	
	static public int				BUY					= 0 ;
	static public int				TRY					= 1 ;
	static public int				PICKER				= 2 ;
	static public int				PAGE_UP				= 3 ;
	static public int				PAGE_DOWN			= 4 ;
	
	private boolean					isAnimationing		= true ;
	
	private BitmapFactory.Options	options				= new BitmapFactory.Options() ;
	
	public static MediaPlayer		mShopPlayer			= null ;
	private MediaPlayer				mPlanePlayer		= null ;
	
	private	boolean					mStarted			= false ;
	private boolean					isRunning			= false ;
	private	boolean					action				= false ;
	/**
	 * 0 - back
	 * 1 - home
	 * 2 - 
	 */
	private	int						mnAction			= 0 ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.shop ) ;
        
        mStatusBarLayout		= (FrameLayout)		findViewById( R.id.StatusBarLayout ) ;
    	mStatusLevelView		= (StatusBarLevel)	findViewById( R.id.StatusLevelView ) ;
    	mStatusEnergyView		= (StatusBarEnergy)	findViewById( R.id.StatusEnergyView ) ;
    	mStatusCoinView			= (StatusBarCoin)	findViewById( R.id.StatusCoinView ) ;
    	mStatusCashView			= (StatusBarCash)	findViewById( R.id.StatusCashView ) ;
    	mCaptionView			= (TextView)		findViewById( R.id.StatusCaptionView ) ;
    	mTimerView				= (TextView)		findViewById( R.id.StatusTimerView ) ;
    	
    	mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
        mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
        mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
        mStatusEnergyView.setVipMember( FabLifeApplication.getProfileManager().isVIPMember() ) ;
        mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
        mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
        mCaptionView.setText( getString( R.string.level01_name + FabLifeApplication.getProfileManager().getLevel() - 1 ) ) ;
        Typeface mFace = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
        mCaptionView.setTypeface( mFace ) ; mTimerView.setTypeface( mFace ) ;
        
        mMainLayout			= (FrameLayout)	findViewById( R.id.ShopMainLayout ) ;
        mShopNameView		= (TextView)	findViewById( R.id.ShopNameTextView ) ;
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
        
        mMakeUpSubLayout	= (FrameLayout)	findViewById( R.id.ShopMakeUpSubLayout ) ;
    	mSkinButton			= (ImageButton)	findViewById( R.id.SkinButton ) ;
    	mBlushButton		= (ImageButton)	findViewById( R.id.BlushOnButton ) ;
    	mEyeShadowButton	= (ImageButton)	findViewById( R.id.EyeShadowButton ) ;
    	mMascaraButton		= (ImageButton)	findViewById( R.id.MascaraButton ) ;
    	mEyeBrowButton		= (ImageButton)	findViewById( R.id.EyeBrowButton ) ;
    	mEyeColorButton		= (ImageButton)	findViewById( R.id.EyeColorButton ) ;
    	mLipsButton			= (ImageButton)	findViewById( R.id.LipsButton ) ;
    	
    	mNoItemView			= (TextView)	findViewById( R.id.ShopNoItemView ) ;
    	
    	mView				= (ShopItemView)findViewById( R.id.ShopItemView ) ;
    	
    	mPrevButton			= (ImageButton)	findViewById( R.id.ItemPagePrevButton ) ;
    	mNextButton			= (ImageButton)	findViewById( R.id.ItemPageNextButton ) ;
    	
    	mBackButton			= (ImageButton)	findViewById( R.id.ShopBackButton ) ;
    	mHomeButton			= (ImageButton)	findViewById( R.id.ShopGoHomeButton ) ;
        mPageLayout			= (LinearLayout)findViewById( R.id.PageLayout ) ;
        
        mImageView01		= (ImageView)	findViewById( R.id.ImageView01 ) ;
        mImageView02		= (ImageView)	findViewById( R.id.ImageView02 ) ;
        
        mAnimView			= (ImageView)	findViewById( R.id.AnimationView ) ;
        
		mShopNameView.setTypeface( mFace ) ; mNoItemView.setTypeface( mFace ) ;
		
		options.inPurgeable = true ;
		
		setBackground( getIntent().getStringExtra( "name" ) ) ;
		
		mHandler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				// TODO Auto-generated method stub
				if ( isAnimationing )
					return ;
				if ( msg.what == BUY )
				{
					buy( mView.mSelectedIndex ) ;
				}
				else if ( msg.what == TRY )
				{
					Item item = mShopManager.getItemByIndex( mView.mSelectedIndex ) ;
					Intent intent = new Intent( getApplicationContext(), Preview.class ) ;
					intent.putExtra( "url", item.name ) ;
					startActivity( intent ) ;
				}
				else if ( msg.what == PICKER )
				{
					Item item = mShopManager.getItemByIndex( mView.mSelectedIndex ) ;
					if ( item.isHair )
					{
						Intent intent = new Intent( getApplicationContext(), SelectHairColor.class ) ;
						intent.putExtra( "url", item.name ) ;
						startActivityForResult( intent, 1 ) ;
					}
				}
				else if ( msg.what == PAGE_UP )
				{
					int count = mShopManager.getCount() ;
					current_dot++ ;
					if ( current_dot > 4 )
					{
						current_dot = 0 ;
						mPageLayout.removeAllViews() ;
						ImageButton left_arrow = new ImageButton( getApplicationContext() ) ;
						left_arrow.setBackgroundResource( R.drawable.page_arrow_left ) ;
						left_arrow.setId( 10 ) ;
						left_arrow.setOnClickListener( aListener ) ;
						mPageLayout.addView( left_arrow, 30, 30 ) ;
						
						current_page++ ;

						int page_count = count - current_page * 5 * 4 ;
						if ( page_count <= 20 )
						{
							int dot_count = page_count / 4 ;
							int mod = page_count % 4 ;
							if ( mod != 0 ) dot_count++ ;
							for ( int i = 0 ; i < dot_count ; i++ )
							{
								ImageButton dot = new ImageButton( getApplicationContext() ) ;
								dot.setBackgroundResource( R.drawable.page_dot_off ) ;
								dot.setId( i ) ;
								dot.setOnClickListener( dListener ) ;
								mPageLayout.addView( dot, 30, 30 ) ;
							}
						}
						else
						{
							for ( int i = 0 ; i < 5 ; i++ )
							{
								ImageButton dot = new ImageButton( getApplicationContext() ) ;
								dot.setBackgroundResource( R.drawable.page_dot_off ) ;
								dot.setId( i ) ;
								dot.setOnClickListener( dListener ) ;
								mPageLayout.addView( dot, 30, 30 ) ;
							}
							ImageButton rigth_arrow = new ImageButton( getApplicationContext() ) ;
							rigth_arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
							rigth_arrow.setId( 11 ) ;
							rigth_arrow.setOnClickListener( aListener ) ;
							mPageLayout.addView( rigth_arrow, 30, 30 ) ;
						}
						View view = mPageLayout.findViewById( current_dot ) ;
						if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
					}
					else
					{
						View dot01 = mPageLayout.findViewById( current_dot - 1 ) ;
						View dot02 = mPageLayout.findViewById( current_dot ) ;
						if ( dot01 != null ) dot01.setBackgroundResource( R.drawable.page_dot_off ) ;
						if ( dot02 != null ) dot02.setBackgroundResource( R.drawable.page_dot_on ) ;
					}
					fillItems() ;
				}
				else if ( msg.what == PAGE_DOWN )
				{
					current_dot-- ;
					if ( current_dot < 0 )
					{
						current_dot = 4 ;
						current_page-- ;
						
						if ( current_page < 0 )
							current_page = 0 ;
						
						mPageLayout.removeAllViews() ;
						if ( current_page == 0 )
						{
							for ( int i = 0 ; i < 5 ; i++ )
							{
								ImageButton dot = new ImageButton( getApplicationContext() ) ;
								dot.setBackgroundResource( R.drawable.page_dot_off ) ;
								dot.setId( i ) ;
								dot.setOnClickListener( dListener ) ;
								mPageLayout.addView( dot, 30, 30 ) ;
							}
							View view = mPageLayout.findViewById( current_dot ) ;
							if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
							ImageButton rigth_arrow = new ImageButton( getApplicationContext() ) ;
							rigth_arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
							rigth_arrow.setId( 11 ) ;
							rigth_arrow.setOnClickListener( aListener ) ;
							mPageLayout.addView( rigth_arrow, 30, 30 ) ;
						}
						else
						{
							ImageButton left_arrow = new ImageButton( getApplicationContext() ) ;
							left_arrow.setBackgroundResource( R.drawable.page_arrow_left ) ;
							left_arrow.setId( 10 ) ;
							left_arrow.setOnClickListener( aListener ) ;
							mPageLayout.addView( left_arrow, 30, 30 ) ;
							for ( int i = 0 ; i < 5 ; i++ )
							{
								ImageButton dot = new ImageButton( getApplicationContext() ) ;
								dot.setBackgroundResource( R.drawable.page_dot_off ) ;
								dot.setId( i ) ;
								dot.setOnClickListener( dListener ) ;
								mPageLayout.addView( dot, 30, 30 ) ;
							}
							View view = mPageLayout.findViewById( current_dot ) ;
							if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
							ImageButton rigth_arrow = new ImageButton( getApplicationContext() ) ;
							rigth_arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
							rigth_arrow.setId( 11 ) ;
							rigth_arrow.setOnClickListener( aListener ) ;
							mPageLayout.addView( rigth_arrow, 30, 30 ) ;
						}
					}
					else
					{
						View dot01 = mPageLayout.findViewById( current_dot + 1 ) ;
						View dot02 = mPageLayout.findViewById( current_dot ) ;
						if ( dot01 != null ) dot01.setBackgroundResource( R.drawable.page_dot_off ) ;
						if ( dot02 != null ) dot02.setBackgroundResource( R.drawable.page_dot_on ) ;
					}
					fillItems() ;
				}
			}
		} ;
		
		mView.setHandler( mHandler ) ;
		
		mShopNameView.setText( getIntent().getStringExtra( "name" ) ) ;
		
		String shop_name = getIntent().getStringExtra( "name" ) ;
		if ( shop_name.equalsIgnoreCase( getString( R.string.shop06_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop07_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop08_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop09_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop10_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop11_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop12_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop13_name ) ) )
		{
			fly_animation = true ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop14_name ) ) )
		{
			fly_animation = true ;
		}
		
		mShopPlayer.setLooping( true ) ;
		
		if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
		{
			mImageView01.setVisibility( View.VISIBLE ) ;
			mImageView02.setVisibility( View.VISIBLE ) ;
			mImageView02.setOnClickListener( this ) ;
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
	    	
	    	mPrevButton.setOnClickListener( this ) ;
	    	mNextButton.setOnClickListener( this ) ;
	    	
	    	mBackButton.setOnClickListener( this ) ;
	    	mHomeButton.setOnClickListener( this ) ;
		}
		
		startAnimation() ;
		overridePendingTransition( R.anim.activity_slide_bottom_to_top, R.anim.hold ) ;
    }
	
	int energy_limit = 45 ;
    Handler mTimerHandler = new Handler()
    {
		@Override
		public void handleMessage( Message msg )
		{
			// TODO Auto-generated method stub
			if ( !isRunning )
				return ;
			
			energy_limit = FabLifeApplication.getProfileManager().isVIPMember() ? 55 : 45 ;
			if ( FabLifeApplication.getProfileManager().getEnergy() < energy_limit )
			{
				if ( FabLifeApplication.mTimerTime <= 0 )
				{
					FabLifeApplication.getProfileManager().setEnergy( FabLifeApplication.getProfileManager().getEnergy() + 1 ) ;
					mStatusEnergyView.setEnergy( FabLifeApplication.getProfileManager().getEnergy() ) ;
					mStatusEnergyView.invalidate() ;
					FabLifeApplication.mTimerTime = 180 ;
				}
				else
				{
					FabLifeApplication.mTimerTime-- ;
				}
				
				if ( FabLifeApplication.getProfileManager().getEnergy() >= energy_limit )
				{
					mTimerView.setText( "" ) ;						
				}
				else
				{
					int minutes = FabLifeApplication.mTimerTime / 60 ;
					int second = FabLifeApplication.mTimerTime % 60 ;
					
					String time = "" ;
					
					if ( minutes < 10 ) time += ( "0" + minutes + ":" ) ;
					else	time += ( minutes + ":" ) ;
					
					if ( second < 10 ) time += ( "0" + second ) ;
					else	time += ( second ) ;
					mTimerView.setText( time  ) ;
				}
			}
			else
			{
				mTimerView.setText( ""  ) ;
				FabLifeApplication.mTimerTime = 180 ;
			}
			sendEmptyMessageDelayed( 0, 1000 ) ;
		}
    } ;

	void startAnimation()
	{
		isAnimationing = true ;
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				// TODO Auto-generated method stub
				if ( msg.what == 0 )
				{
					if ( fly_animation )
					{
						if ( FabLifeApplication.getProfileManager().getEffect() )
						{
							mPlanePlayer = MediaPlayer.create( getApplicationContext(), R.raw.plane ) ;
							mPlanePlayer.start() ;
						}
						mAnimView.setBackgroundResource( R.drawable.traveling ) ;
						sendEmptyMessageDelayed( 1, 3000 ) ;
					}
					else
					{						
						mShopManager = new ShopManager( getApplicationContext(), getIntent().getStringExtra( "name" ) ) ;
						mView.setShopManager( mShopManager ) ;
						fillItems() ;
						initPageLayout() ;
						mAnimView.setVisibility( View.GONE ) ;
						isAnimationing = false ;
						if ( FabLifeApplication.getProfileManager().getEffect() )
							mShopPlayer.start() ;
						mStarted = true ;
					}
				}
				else if ( msg.what == 1 )
				{
					mShopManager = new ShopManager( getApplicationContext(), getIntent().getStringExtra( "name" ) ) ;
					mView.setShopManager( mShopManager ) ;
					fillItems() ;
					initPageLayout() ;
					mAnimView.setVisibility( View.GONE ) ;
					isAnimationing = false ;
					if ( FabLifeApplication.getProfileManager().getEffect() )
						mShopPlayer.start() ;
					mStarted = true ;
				}
			}
		} ;
		handler.sendEmptyMessageDelayed( 0, 2000 ) ;
	}
	
	void finishAnimation()
	{
		mAnimView.setVisibility( View.VISIBLE ) ;
		isAnimationing = true ;
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage( Message msg )
			{
				// TODO Auto-generated method stub
				if ( msg.what == 0 )
				{
					if ( fly_animation )
					{
						mAnimView.setBackgroundResource( R.drawable.loading_store ) ;
						sendEmptyMessageDelayed( 1, 2000 ) ;
					}
					else
					{
						try
						{
							mShopManager.clear() ;
							mView.clear() ;
						}
						catch( Exception e )
						{
							e.printStackTrace() ;
						}
						
						if ( mPlanePlayer != null )
						{
							if ( mPlanePlayer.isPlaying() ) mPlanePlayer.stop() ;
							mPlanePlayer.release() ; mPlanePlayer = null ;
						}
						if ( mShopPlayer != null )
						{
							if ( mShopPlayer .isPlaying() ) mShopPlayer.stop() ;
							mShopPlayer.release() ; mShopPlayer = null ;
						}
						action = true ;
						if ( !FabLifeApplication.getProfileManager().getTutorialFinished() )
						{
							Intent intent = new Intent( Shop.this, main.class ) ;
							intent.putExtra( main.STRING_STEP, main.STEP_SHOP ) ;
							startActivity( intent ) ;
						}
						else
						{
							switch( mnAction )
							{
								case 0 :
								{
									Intent intent = new Intent( Shop.this, StoreList.class ) ;
									startActivity( intent ) ;
									break ;
								}
								case 1 :
								{
									Intent intent = new Intent( Shop.this, main.class ) ;
									startActivity( intent ) ;
									break ;
								}
								case 2 :
								{
									Intent intent = new Intent( Shop.this, Bank.class ) ;
									startActivity( intent ) ;
									break ;
								}
							}
						}
						try
						{
							System.gc() ;
						}
						catch( Exception e )
						{
							e.printStackTrace() ;
						}
						finish() ;
					}
				}
				else if ( msg.what == 1 )
				{
					try
					{
						mShopManager.clear() ;
						mView.clear() ;
					}
					catch( Exception e )
					{
						e.printStackTrace() ;
					}
					if ( mPlanePlayer != null )
					{
						if ( mPlanePlayer.isPlaying() ) mPlanePlayer.stop() ;
						mPlanePlayer.release() ;
						mPlanePlayer = null ;
					}
					if ( mShopPlayer != null )
					{
						if ( mShopPlayer .isPlaying() ) mShopPlayer.stop() ;
						mShopPlayer.release() ; mShopPlayer = null ;
					}
					action = true ;
					switch( mnAction )
					{
						case 0 :
						{
							Intent intent = new Intent( Shop.this, StoreList.class ) ;
							startActivity( intent ) ;
							break ;
						}
						case 1 :
						{
							Intent intent = new Intent( Shop.this, main.class ) ;
							startActivity( intent ) ;
							break ;
						}
						case 2 :
						{
							Intent intent = new Intent( Shop.this, Bank.class ) ;
							startActivity( intent ) ;
							break ;
						}
					}
					try
					{
						System.gc() ;
					}
					catch( Exception e )
					{
						e.printStackTrace() ;
					}
					finish() ;
				}
			}
		} ;
		handler.sendEmptyMessageDelayed( 0, 3000 ) ;
		if ( FabLifeApplication.getProfileManager().getEffect() )
		{
			if ( mPlanePlayer != null )
				mPlanePlayer.start() ;
			if ( mShopPlayer != null )
				mShopPlayer.pause() ;
		}
	}
	
	int current_page = 0 ;
	int current_dot = 0 ;
	void initPageLayout()
	{
		mPageLayout.removeAllViews() ;
		int count = mShopManager.getCount() ;
		if ( count == 0 )
			mPageLayout.setVisibility( View.GONE ) ;
		else
		{
			current_page = 0 ;
			current_dot = 0 ;
			if ( count <= 20 )
			{
				int dot_count = count / 4 ;
				int mod = count % 4 ;
				if ( mod != 0 ) dot_count++ ;
				for ( int i = 0 ; i < dot_count ; i++ )
				{
					ImageButton dot = new ImageButton( this ) ;
					dot.setBackgroundResource( R.drawable.page_dot_off ) ;
					dot.setId( i ) ;
					dot.setOnClickListener( dListener ) ;
					mPageLayout.addView( dot, 30, 30 ) ;
				}
				View view = mPageLayout.findViewById( 0 ) ;
				if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
			}
			else if ( count > 20 )
			{
				for ( int i = 0 ; i < 5 ; i++ )
				{
					ImageButton dot = new ImageButton( this ) ;
					dot.setBackgroundResource( R.drawable.page_dot_off ) ;
					dot.setId( i ) ;
					dot.setOnClickListener( dListener ) ;
					mPageLayout.addView( dot, 30, 30 ) ;
				}
				
				View view = mPageLayout.findViewById( 0 ) ;
				if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
				
				ImageButton arrow = new ImageButton( this ) ;
				arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
				arrow.setId( 11 ) ;
				arrow.setOnClickListener( aListener ) ;
				mPageLayout.addView( arrow, 30, 30 ) ;
			}
		}
	}
	
	class DotClickListener implements OnClickListener
	{
		@Override
		public void onClick( View v )
		{
			// TODO Auto-generated method stub
			if ( current_dot != v.getId() )
			{
				v.setBackgroundResource( R.drawable.page_dot_on ) ;
				mPageLayout.findViewById( current_dot ).setBackgroundResource( R.drawable.page_dot_off ) ;
				current_dot = v.getId() ;
				mView.startIndex = current_page * 5 * 4 + current_dot * 4 ;
				try
				{
					mView.getBitmap() ;
				}
				catch( Exception e )
				{
					e.printStackTrace() ;
				}
				mView.draw() ;
			}
		}
	}
	DotClickListener dListener = new DotClickListener() ;

	class ArrowClickListener implements OnClickListener
	{
		@Override
		public void onClick( View v )
		{
			// TODO Auto-generated method stub
			if ( v.getId() == 11 )
			{
				mPageLayout.removeAllViews() ;
				ImageButton left_arrow = new ImageButton( getApplicationContext() ) ;
				left_arrow.setBackgroundResource( R.drawable.page_arrow_left ) ;
				left_arrow.setId( 10 ) ;
				left_arrow.setOnClickListener( aListener ) ;
				mPageLayout.addView( left_arrow, 30, 30 ) ;
				
				current_page++ ;
				
				int count = mShopManager.getCount() ;
				int page_count = count - current_page * 5 * 4 ;
				if ( page_count <= 20 )
				{
					int dot_count = page_count / 4 ;
					int mod = page_count % 4 ;
					if ( mod != 0 ) dot_count++ ;
					for ( int i = 0 ; i < dot_count ; i++ )
					{
						ImageButton dot = new ImageButton( getApplicationContext() ) ;
						dot.setBackgroundResource( R.drawable.page_dot_off ) ;
						dot.setId( i ) ;
						dot.setOnClickListener( dListener ) ;
						mPageLayout.addView( dot, 30, 30 ) ;
					}
					
					if ( ( current_dot + 1 ) >= dot_count )
					{
						current_dot = dot_count - 1 ;
					}
					View view = mPageLayout.findViewById( current_dot ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
				}
				else
				{
					for ( int i = 0 ; i < 5 ; i++ )
					{
						ImageButton dot = new ImageButton( getApplicationContext() ) ;
						dot.setBackgroundResource( R.drawable.page_dot_off ) ;
						dot.setId( i ) ;
						dot.setOnClickListener( dListener ) ;
						mPageLayout.addView( dot, 30, 30 ) ;
					}
					View view = mPageLayout.findViewById( current_dot ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
					
					ImageButton rigth_arrow = new ImageButton( getApplicationContext() ) ;
					rigth_arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
					rigth_arrow.setId( 11 ) ;
					rigth_arrow.setOnClickListener( aListener ) ;
					mPageLayout.addView( rigth_arrow, 30, 30 ) ;
				}
				mView.startIndex = current_page * 5 * 4 + current_dot * 4 ;
				try
				{
					mView.getBitmap() ;
				}
				catch( Exception e )
				{
					e.printStackTrace() ;
				}
				mView.draw() ;
			}
			else if ( v.getId() == 10 )
			{
				mPageLayout.removeAllViews() ;
				current_page-- ;
				if ( current_page <= 0 )
					current_page = 0 ;
				
				if ( current_page == 0 )
				{
					for ( int i = 0 ; i < 5 ; i++ )
					{
						ImageButton dot = new ImageButton( getApplicationContext() ) ;
						dot.setBackgroundResource( R.drawable.page_dot_off ) ;
						dot.setId( i ) ;
						dot.setOnClickListener( dListener ) ;
						mPageLayout.addView( dot, 30, 30 ) ;
					}
					View view = mPageLayout.findViewById( current_dot ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
					ImageButton rigth_arrow = new ImageButton( getApplicationContext() ) ;
					rigth_arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
					rigth_arrow.setId( 11 ) ;
					rigth_arrow.setOnClickListener( aListener ) ;
					mPageLayout.addView( rigth_arrow, 30, 30 ) ;
				}
				else
				{
					ImageButton left_arrow = new ImageButton( getApplicationContext() ) ;
					left_arrow.setBackgroundResource( R.drawable.page_arrow_left ) ;
					left_arrow.setId( 10 ) ;
					left_arrow.setOnClickListener( aListener ) ;
					mPageLayout.addView( left_arrow, 30, 30 ) ;
					for ( int i = 0 ; i < 5 ; i++ )
					{
						ImageButton dot = new ImageButton( getApplicationContext() ) ;
						dot.setBackgroundResource( R.drawable.page_dot_off ) ;
						dot.setId( i ) ;
						dot.setOnClickListener( dListener ) ;
						mPageLayout.addView( dot, 30, 30 ) ;
					}
					View view = mPageLayout.findViewById( current_dot ) ;
					if ( view != null ) view.setBackgroundResource( R.drawable.page_dot_on ) ;
					ImageButton rigth_arrow = new ImageButton( getApplicationContext() ) ;
					rigth_arrow.setBackgroundResource( R.drawable.page_arrow_right ) ;
					rigth_arrow.setId( 11 ) ;
					rigth_arrow.setOnClickListener( aListener ) ;
					mPageLayout.addView( rigth_arrow, 30, 30 ) ;
				}
				mView.startIndex = current_page * 5 * 4 + current_dot * 4 ;
				try
				{
					mView.getBitmap() ;
				}
				catch( Exception e )
				{
					e.printStackTrace() ;
				}
				mView.draw() ;
			}
		}
	}
	ArrowClickListener aListener = new ArrowClickListener() ;
	
	void setBackground( String shop_name )
	{
		if ( shop_name.equalsIgnoreCase( getString( R.string.shop01_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop01_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop01_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.makeup ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop02_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop02_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop02_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.fabcity ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop03_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop03_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop03_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.salon ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop04_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop04_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop04_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.wande ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop05_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop05_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop05_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.forchic ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop06_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop06_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop06_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.salon6 ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop07_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop07_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop07_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.paris ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop08_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop08_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop08_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.malibu ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop09_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop09_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop09_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.hollywood ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop10_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop10_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop10_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.japan ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop11_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop11_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop11_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.christossalon ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop12_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop12_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop12_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.enchanteur ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop13_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop13_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop13_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.deluxe ) ;
		}
		else if ( shop_name.equalsIgnoreCase( getString( R.string.shop14_name ) ) )
		{
			mMainLayout.setBackgroundResource( R.drawable.shop14_bg ) ;
			mView.setBackground( BitmapFactory.decodeResource( getResources(), R.drawable.shop14_bg, options ) ) ;
			mShopPlayer = MediaPlayer.create( getApplicationContext(), R.raw.thevanity ) ;
		}
	}

	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() >= R.id.SubjectAllButton && v.getId() <= R.id.Subject09Button )
		{
			FabLifeApplication.playTapEffect() ;
			int select_subject = ( v.getId() - R.id.SubjectAllButton ) / 2 +  1 ;
			
			if ( select_subject == mSelectSubject )
				return ;
			
			if ( mSelectSubject > SUBJECT_MAKEUP )
			{
				mSelectSubject = SUBJECT_MAKEUP ;
			}
			
			findViewById( R.id.SubjectAllLayout + ( mSelectSubject - 1 ) * 2 ).setBackgroundDrawable( null ) ;
			findViewById( R.id.SubjectAllLayout + v.getId() - R.id.SubjectAllButton ).setBackgroundResource( R.drawable.subject_select ) ;
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
			mShopManager.setSubject( mSelectSubject ) ;
			mView.startIndex = 0 ;
			fillItems() ;
			initPageLayout() ;
			if ( mView.getVisibility() == View.VISIBLE )
			{
				try
				{
					mView.getBitmap() ;
				}
				catch( Exception e )
				{
					e.printStackTrace() ;
				}
				mView.draw() ;
			}
		}
		else if ( v.getId() >= R.id.SkinButton && v.getId() <= R.id.LipsButton )
		{
			FabLifeApplication.playTapEffect() ;
			int select_subject = SUBJECT_SKINCOLOR + v.getId() - R.id.SkinButton ;
			
			if ( select_subject == mSelectSubject )
				return ;
			mSelectSubject = select_subject ;
			mShopManager.setSubject( mSelectSubject ) ;
			selectMakeUpView() ;
			mView.startIndex = 0 ;
			initPageLayout() ;
			fillItems() ;
			if ( mView.getVisibility() == View.VISIBLE )
			{
				try
				{
					mView.getBitmap() ;
				}
				catch( Exception e )
				{
					e.printStackTrace() ;
				}			
				mView.draw() ;
			}
		}
		else if ( v.getId() == R.id.ItemPagePrevButton )
		{
			FabLifeApplication.playTapEffect() ;
			if ( mView.startIndex >= 4 && !mView.isAnimation() )
			{
				mView.pageDownAnim() ;
			}
		}
		else if ( v.getId() == R.id.ItemPageNextButton )
		{
			FabLifeApplication.playTapEffect() ;
			int count = mShopManager.getCount() ;
			if ( mView.startIndex + 4 < count && !mView.isAnimation() )
			{
				mView.pageUpAnim() ;
			}			
		}
		else if ( v.getId() == R.id.ShopBackButton )
		{
			FabLifeApplication.playTapEffect() ;
			mnAction = 0 ;
			finishAnimation() ;
		}
		else if ( v.getId() == R.id.ShopGoHomeButton )
		{
			FabLifeApplication.playTapEffect() ;
			mnAction = 1 ;
			finishAnimation() ;
		}
		else if ( v.getId() == R.id.ImageView02 )
		{
			mnAction = 1 ;
			finishAnimation() ;
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
	
	void fillItems()
	{
		int count = mShopManager.getCount() ;
		
		if ( count == 0 )
		{
			mView.setVisibility( View.GONE ) ;
			mNoItemView.setVisibility( View.VISIBLE ) ;
			mPrevButton.setVisibility( View.GONE ) ;
			mNextButton.setVisibility( View.GONE ) ;
			mPageLayout.setVisibility( View.GONE ) ;
		}
		else
		{
			mNoItemView.setVisibility( View.GONE ) ;
			mView.setVisibility( View.VISIBLE ) ;
			mPageLayout.setVisibility( View.VISIBLE ) ;
			if ( mView.startIndex <= 0 )
			{
				mPrevButton.setVisibility( View.GONE ) ;
			}
			else
			{
				mPrevButton.setVisibility( View.VISIBLE ) ;
			}
			
			if ( mView.startIndex + 4 < count )
			{
				mNextButton.setVisibility( View.VISIBLE ) ;
			}
			else
			{
				mNextButton.setVisibility( View.GONE ) ;
			}
		}
	}
	
	int mIndex = 0 ;
	void buy( int index )
	{
		mIndex = index ;
		Item item = mShopManager.getItemByIndex( index ) ;
		
		if ( item.pricetype == 0 )
		{
			if ( FabLifeApplication.getProfileManager().getCash() < item.price )
			{
				showNotEnoughDialog() ;
			}
			else
				showBuyDialog( item ) ;
		}
		else
		{
			if ( FabLifeApplication.getProfileManager().getCoin() < item.price )
			{
				showNotEnoughDialog() ;
			}
			else
				showBuyDialog( item ) ;
		}
	}
	
	private void showBuyDialog( Item item )
	{
		String message = "Confirm to buy this item for " + item.price + " " ;
		String type = item.pricetype == 0 ?  "cash" : "coins" ;
		message = message + type + " ?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			try
        			{
	        			Item item = mShopManager.getItemByIndex( mIndex ) ;
	        			if ( item.pricetype == 0 )
	        			{
	        				FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() - item.price ) ;
	        				FabLifeApplication.getProfileManager().setExperience( FabLifeApplication.getProfileManager().getExperience() + item.experience ) ;
	        				
	        				mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
	        				mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
	        				mStatusBarLayout.invalidate() ;
	        			}
	        			else
	        			{
	        				FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() - item.price ) ;
	        				FabLifeApplication.getProfileManager().setExperience( FabLifeApplication.getProfileManager().getExperience() + item.experience ) ;
	        				
	        				mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
	        				mStatusLevelView.setExperience( FabLifeApplication.getProfileManager().getExperience() ) ;
	        				mStatusBarLayout.invalidate() ;
	        			}
	        			mShopManager.buy( mIndex ) ;
	        			if ( mView.startIndex >= mShopManager.getCount() )
	        			{
	        				mView.startIndex -= 4 ;
	        				if ( mView.startIndex < 0 )
	        					mView.startIndex = 0 ;
	        			}
						mView.getBitmap() ;
	        			if ( FabLifeApplication.getProfileManager().getExperience() > ProfileManager.getLevelXp( FabLifeApplication.getProfileManager().getLevel() ) )
	        			{
	        				if ( FabLifeApplication.getProfileManager().getLevel() < 40 )
	        				{
	        					FabLifeApplication.getProfileManager().setLevel( FabLifeApplication.getProfileManager().getLevel() + 1 ) ;
	        					FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 
		        						30 + ( FabLifeApplication.getProfileManager().getLevel() - 2 ) * 20 ) ;
	        					mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
	            				mStatusLevelView.setLevel( FabLifeApplication.getProfileManager().getLevel() ) ;
	            				mStatusBarLayout.invalidate() ;
	            				
		        				Intent intent = new Intent( getApplicationContext(), LevelUp.class ) ;
		        				startActivityForResult( intent, 0 ) ;
	        				}
	        			}
	    				fillItems() ;
	    				initPageLayout() ;
	    				if ( mView.getVisibility() == View.VISIBLE )
	    					mView.draw() ;
        			}
        			catch( Exception e )
        			{
        				e.printStackTrace() ;
        				if ( FabLifeApplication.getProfileManager() != null )
        					FabLifeApplication.getProfileManager().writeToFile() ;
        				if ( FabLifeApplication.getBuiedManager() != null )
        					FabLifeApplication.getBuiedManager().writeToFile() ;
        				if ( FabLifeApplication.getAppliedManager() != null )
        					FabLifeApplication.getAppliedManager().writeToFile() ;
        			}
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
	
	private void showNotEnoughDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( getString( R.string.cash_not_enough ) )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			gotoBank() ;
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
	
	private void showGoStoreListDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "You just unlocked a new store. Would you like go shopping in this cool new place?" )
        .setCancelable( false )
        .setPositiveButton( "Yes Sure", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			mnAction = 0 ;
        			finishAnimation() ;
                }
        	}
        )
		.setNegativeButton( "Not Now", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	void gotoBank()
	{
		mnAction = 2 ;
		finishAnimation() ;
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		if ( mPlanePlayer != null ) mPlanePlayer.pause() ;
		if ( mShopPlayer != null ) 	mShopPlayer.pause() ;
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		if ( mStarted )
		{
			if ( mShopPlayer != null )
			{
				if ( FabLifeApplication.getProfileManager().getEffect() )
					mShopPlayer.start() ;
			}
		}
	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart() ;
		if ( FabLifeApplication.getProfileManager().getTutorialFinished() && FabLifeApplication.getProfileManager().getFinishTime() != 0 )
        {
        	long time_diff = System.currentTimeMillis() - FabLifeApplication.getProfileManager().getFinishTime() ;
        	time_diff = time_diff / 1000 ;
        	
        	int energy_diff = (int) (time_diff / 180) ;
        	int second_diff = (int) (time_diff % 180) ;
        	
        	int c_time = FabLifeApplication.getProfileManager().getCurrentTimerTime() ;
        	if ( ( c_time - second_diff ) <= 0 ) energy_diff++ ;
        	
        	if ( FabLifeApplication.getProfileManager().getEnergy() < energy_limit )
        	{
        		FabLifeApplication.getProfileManager().setEnergy(FabLifeApplication. getProfileManager().getEnergy() + energy_diff ) ;
        		if ( FabLifeApplication.getProfileManager().getEnergy() >= energy_limit )
        		{
        			FabLifeApplication.getProfileManager().setEnergy( energy_limit ) ;
        			FabLifeApplication.mTimerTime = 180 ;
        		}
        		else
        		{
        			if ( ( c_time - second_diff ) <= 0 )
        			{
        				FabLifeApplication.mTimerTime = 180 - ( second_diff - c_time ) ;
        			}
        			else
        			{
        				FabLifeApplication.mTimerTime = c_time - second_diff ;
        			}
        		}
        	}
        }
		isRunning = true ;
		mTimerHandler.sendEmptyMessage( 0 ) ;
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop() ;
		isRunning = false ;
		if ( FabLifeApplication.getProfileManager() != null )
		{
			if ( action )
			{
				FabLifeApplication.getProfileManager().setFinishTime( 0 ) ;
			}
			else
			{
				FabLifeApplication.getProfileManager().setFinishTime( System.currentTimeMillis() ) ;
				FabLifeApplication.getProfileManager().setCurrentTimerTime( FabLifeApplication.mTimerTime ) ;
			}
			FabLifeApplication.getProfileManager().writeToFile() ;
		}
		if ( FabLifeApplication.getBuiedManager() != null )
			FabLifeApplication.getBuiedManager().writeToFile() ;
	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		// TODO Auto-generated method stub
		if ( requestCode == 0 )
		{
			int level = FabLifeApplication.getProfileManager().getLevel() ;
			if ( level == 3 || level == 6 || level == 7 || level == 8 || level == 10 || level == 13 )
			{
				showGoStoreListDialog() ;
			}
		}
		else if ( requestCode == 1 )
		{
			try
			{
				mView.getBitmap() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
			fillItems() ;
			if ( mView.getVisibility() == View.VISIBLE )
				mView.draw() ;
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		return ;
	}
}
