package com.miinu.FabLife.Activity;

import java.util.HashSet;
import java.util.Set;

import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Purchase.BillingService;
import com.miinu.FabLife.Purchase.Consts;
import com.miinu.FabLife.Purchase.PurchaseDatabase;
import com.miinu.FabLife.Purchase.PurchaseObserver;
import com.miinu.FabLife.Purchase.ResponseHandler;
import com.miinu.FabLife.Purchase.BillingService.RequestPurchase;
import com.miinu.FabLife.Purchase.BillingService.RestoreTransactions;
import com.miinu.FabLife.Purchase.Consts.PurchaseState;
import com.miinu.FabLife.Purchase.Consts.ResponseCode;
import com.miinu.FabLife.ViewComponent.StatusBarCash;
import com.miinu.FabLife.ViewComponent.StatusBarCoin;
import com.miinu.FabLife.ViewComponent.StatusBarEnergy;
import com.miinu.FabLife.ViewComponent.StatusBarLevel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class VIP extends Activity implements OnTouchListener, OnClickListener
{
	private		FrameLayout			mLayout					= null ;
	
//	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private		ImageButton 		buy_btn 				= null ;
	private		ImageButton			home_btn				= null ;
	
	private		boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.vip ) ;
       
        mLayout					= (FrameLayout)		findViewById( R.id.VIPLayout ) ;
        
//        mStatusBarLayout		= (FrameLayout)		findViewById( R.id.StatusBarLayout ) ;
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
        
        buy_btn = (ImageButton)findViewById( R.id.VipBuyButton ) ;
        home_btn = (ImageButton) findViewById( R.id.VipHomeButton ) ;
        
        buy_btn.setOnTouchListener( this ) ;
        home_btn.setOnClickListener( this ) ;
        
        if ( FabLifeApplication.getProfileManager().isVIPMember() )
        {
        	 mLayout.setBackgroundResource( R.drawable.vip_bought ) ;
        	 buy_btn.setVisibility( View.GONE ) ;
        }
        else
        {
        	mLayout.setBackgroundResource( R.drawable.vip_background01 ) ;
        }
        
        mHandler = new Handler();
        mVipPurchaseObserver = new VIPPurchaseObserver( mHandler ) ;
        mBillingService = new BillingService() ;
        mBillingService.setContext( this ) ;

        mPurchaseDatabase = new PurchaseDatabase( this ) ;

        // Check if billing is supported.
        ResponseHandler.register( mVipPurchaseObserver ) ;
        
        overridePendingTransition( android.R.anim.fade_in, R.anim.hold ) ;
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


	@Override
	public boolean onTouch( View view, MotionEvent event )
	{
		// TODO Auto-generated method stub
		if ( event.getAction() == MotionEvent.ACTION_DOWN )
		{
			FabLifeApplication.playTapEffect() ;
			buy_btn.setBackgroundResource( R.drawable.vip_buy_btn_c ) ;
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE )
		{
			buy_btn.setBackgroundResource( R.drawable.vip_buy_btn ) ;
			if ( FabLifeApplication.getProfileManager().getLevel() < 8 )
			{
				showNoLevelDialog() ;
			}
			else
			{
				showSureDialog() ;
			}
		}
		return false;
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
		FabLifeApplication.playHomeEffect() ;
		action = true ;
		Intent intent = new Intent( VIP.this, main.class ) ;
		startActivity( intent ) ;
		finish() ;
	}
	
	private void showNoLevelDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "Sorry. you are not eligible yet to be a VIP. Work and shop harder to gain higher level to unlock VIP membership." )
        .setCancelable( false )
        .setPositiveButton( "OK", null ) ;
		AlertDialog alert = builder.create() ;
		alert.show() ;
	}
	
	private void showSureDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "Are you sure want to be a VIP member?" )
        .setCancelable( false )
        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			if ( !mBillingService.checkBillingSupported() )
    		        {
    					showNoConnectDialog() ;
    		        }
    				else
    				{
    					if ( !mBillingService.requestPurchase( "com.miinu.fablife.vip", "vip" ) ) 
    					{
    						showNoSupportIdDialog() ;
    					}
    					else
    					{
    						FabLifeApplication.getProfileManager().setVIPMember() ;
    					}
    	            }
                }
        	}
        )
		.setNegativeButton( "Not", new DialogInterface.OnClickListener() 
	    	{
	    		public void onClick( DialogInterface dialog, int id )
	    		{
	            }
	    	}
		) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void showNoSupportIdDialog()
	{
		String message = "The Market billing service is not available at this time.  You can continue to use this app but you won't be able to make purchases." ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
        .setCancelable( false )
        .setPositiveButton( "OK", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
                }
        	}
        ) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void showNoConnectDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( "You cann't buy the product. Check your Internet connection and please try again." )
        .setCancelable( false )
        .setPositiveButton( "Waiting...", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
                }
        	}
        ) ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private static final String TAG = "VIP" ;
//  private static final String LOG_TEXT_KEY = "VIP_LOG_TEXT" ;
  
	private static final String DB_INITIALIZED = "db_initialized" ;
	
	private VIPPurchaseObserver mVipPurchaseObserver ;
	private Handler mHandler ;

	private BillingService mBillingService ;
	private PurchaseDatabase mPurchaseDatabase ;
	private Set<String> mOwnedItems = new HashSet<String>() ;

	/**
	 * The developer payload that is sent with subsequent
	 * purchase requests.
	 */
//  private String mPayloadContents = null ;
	
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
	
	/**
	 * Called when this activity becomes visible.
	 */
	@Override
	protected void onStart() 
	{
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
		ResponseHandler.register( mVipPurchaseObserver ) ;
		initializeOwnedItems() ;
	}

	/**
	 * Called when this activity is no longer visible.
	 */
	@Override
	protected void onStop() 
	{
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
		ResponseHandler.unregister( mVipPurchaseObserver ) ;
	}

  @Override
  protected void onDestroy()
  {
      super.onDestroy() ;
      mPurchaseDatabase.close() ;
      mBillingService.unbind() ;
  }

  /**
   * Save the context of the log so simple things like rotation will not
   * result in the log being cleared.
   */
  @Override
  protected void onSaveInstanceState( Bundle outState )
  {
      super.onSaveInstanceState( outState ) ;
  }

  /**
   * Restore the contents of the log if it has previously been saved.
   */
  @Override
  protected void onRestoreInstanceState( Bundle savedInstanceState )
  {
      super.onRestoreInstanceState( savedInstanceState ) ;
  }
  
  /**
   * Creates a background thread that reads the database and initializes the
   * set of owned items.
   */
  private void initializeOwnedItems() 
  {
      new Thread( new Runnable()
      {
          public void run()
          {
              doInitializeOwnedItems() ;
          }
      } ).start() ;
  }

  /**
   * Reads the set of purchased items from the database in a background thread
   * and then adds those items to the set of owned items in the main UI
   * thread.
   */
  private void doInitializeOwnedItems() 
  {
      Cursor cursor = mPurchaseDatabase.queryAllPurchasedItems() ;
      if ( cursor == null )
      {
          return ;
      }

      final Set<String> ownedItems = new HashSet<String>() ;
      try 
      {
          int productIdCol = cursor.getColumnIndexOrThrow( PurchaseDatabase.PURCHASED_PRODUCT_ID_COL ) ;
          while ( cursor.moveToNext() )
          {
              String productId = cursor.getString( productIdCol ) ;
              ownedItems.add( productId ) ;
          }
      } 
      finally 
      {
          cursor.close() ;
      }

      // We will add the set of owned items in a new Runnable that runs on
      // the UI thread so that we don't need to synchronize access to
      // mOwnedItems.
      mHandler.post( new Runnable()
      {
          public void run() 
          {
              mOwnedItems.addAll( ownedItems ) ;
          }
      } ) ;
  }
	
  private void restoreDatabase()
	{
  	SharedPreferences prefs = getPreferences( MODE_PRIVATE ) ;
  	boolean initialized = prefs.getBoolean( DB_INITIALIZED, false ) ;
	    if ( !initialized )
	    {
	    	mBillingService.restoreTransactions() ;
	        Toast.makeText( this, "Restoring transactions", Toast.LENGTH_LONG ).show() ;
	    }
	}
  
//  private void prependLogEntry( CharSequence cs )
//  {
//      SpannableStringBuilder contents = new SpannableStringBuilder(cs);
//      contents.append('\n');
//  }
//  
//  private void logProductActivity( String product, String activity )
//  {
//      SpannableStringBuilder contents = new SpannableStringBuilder() ;
//      contents.append( Html.fromHtml("<b>" + product + "</b>: ") ) ;
//      contents.append( activity ) ;
//      prependLogEntry( contents ) ;
//  }
  
  /**
   * A {@link PurchaseObserver} is used to get callbacks when Android Market sends
   * messages to this application so that we can update the UI.
   */
  private class VIPPurchaseObserver extends PurchaseObserver
  {
      public VIPPurchaseObserver( Handler handler )
      {
          super( VIP.this, handler ) ;
      }

      @Override
      public void onBillingSupported( boolean supported )
      {
          if ( Consts.DEBUG )
          {
              Log.i( TAG, "supported: " + supported ) ;
          }
          if ( supported )
          {
              restoreDatabase() ;
          } 
          else 
          {
          	showNoSupportIdDialog() ;
          }
      }

      @Override
      public void onPurchaseStateChange( PurchaseState purchaseState, String itemId, int quantity, long purchaseTime, String developerPayload )
      {
          if ( Consts.DEBUG )
          {
              Log.i( TAG, "onPurchaseStateChange() itemId: " + itemId + " " + purchaseState ) ;
          }

          if ( developerPayload == null )
          {
//              logProductActivity( itemId, purchaseState.toString() ) ;
          } 
          else
          {
//              logProductActivity( itemId, purchaseState + "\n\t" + developerPayload ) ;
          }

          if ( purchaseState == PurchaseState.PURCHASED )
          {
              mOwnedItems.add( itemId ) ;
          }
      }

      @Override
      public void onRequestPurchaseResponse( RequestPurchase request, ResponseCode responseCode )
      {
          if ( Consts.DEBUG )
          {
              Log.d( TAG, request.mProductId + ": " + responseCode ) ;
          }
          if ( responseCode == ResponseCode.RESULT_OK )
          {
              if ( Consts.DEBUG )
              {
                  Log.i( TAG, "purchase was successfully sent to server" ) ;
              }
//              logProductActivity( request.mProductId, "sending purchase request" ) ;
          } 
          else if ( responseCode == ResponseCode.RESULT_USER_CANCELED )
          {
              if ( Consts.DEBUG )
              {
                  Log.i( TAG, "user canceled purchase" ) ;
              }
//              logProductActivity( request.mProductId, "dismissed purchase dialog" ) ;
          } 
          else
          {
              if ( Consts.DEBUG )
              {
                  Log.i( TAG, "purchase failed" ) ;
              }
//              logProductActivity( request.mProductId, "request purchase returned " + responseCode ) ;
          }
      }

      @Override
      public void onRestoreTransactionsResponse( RestoreTransactions request, ResponseCode responseCode )
      {
          if ( responseCode == ResponseCode.RESULT_OK )
          {
              if ( Consts.DEBUG )
              {
                  Log.d( TAG, "completed RestoreTransactions request" ) ;
              }
              // Update the shared preferences so that we don't perform
              // a RestoreTransactions again.
              SharedPreferences prefs = getPreferences( Context.MODE_PRIVATE ) ;
              SharedPreferences.Editor edit = prefs.edit() ;
              edit.putBoolean( DB_INITIALIZED, true ) ;
              edit.commit() ;
          } 
          else
          {
              if ( Consts.DEBUG )
              {
                  Log.d( TAG, "RestoreTransactions error: " + responseCode ) ;
              }
          }
      }
  }
}
