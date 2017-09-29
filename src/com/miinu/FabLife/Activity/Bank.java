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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Bank extends Activity implements OnClickListener
{
	private		FrameLayout			mStatusBarLayout		= null ;
	private		StatusBarLevel		mStatusLevelView		= null ;
	private		StatusBarEnergy		mStatusEnergyView		= null ;
	private		StatusBarCoin		mStatusCoinView			= null ;
	private		StatusBarCash		mStatusCashView			= null ;
	private 	TextView			mCaptionView			= null ;
	private		TextView			mTimerView				= null ;
	
	private 	ImageButton 		cash20_btn	 			= null ;
	private		ImageButton 		cash53_btn	 			= null ;
	private		ImageButton 		cash110_btn 			= null ;
	private		ImageButton 		cash288_btn 			= null ;
	private		ImageButton 		cash600_btn 			= null ;
	private		ImageButton 		cash1270_btn 			= null ;
	
	private		ImageButton 		coin400_btn 			= null ;
	private		ImageButton 		coin1050_btn 			= null ;
	private		ImageButton 		coin2200_btn 			= null ;
	private		ImageButton 		coin5750_btn 			= null ;
	private		ImageButton 		coin12000_btn 			= null ;
	private		ImageButton 		coin25500_btn 			= null ;
	
	private		ImageButton 		gohome_btn				= null ;
	private		boolean				isRunning				= false ;
	private		boolean				action					= false ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.bank ) ;
        
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
        
        cash20_btn		= (ImageButton)	findViewById( R.id.Cash20Button ) ;
        cash53_btn		= (ImageButton)	findViewById( R.id.Cash53Button ) ;
        cash110_btn		= (ImageButton)	findViewById( R.id.Cash110Button ) ;
        cash288_btn		= (ImageButton)	findViewById( R.id.Cash288Button ) ;
        cash600_btn		= (ImageButton)	findViewById( R.id.Cash600Button ) ;
        cash1270_btn	= (ImageButton)	findViewById( R.id.Cash1270Button ) ;
        
        coin400_btn		= (ImageButton)	findViewById( R.id.Coin400Button ) ;
        coin1050_btn	= (ImageButton)	findViewById( R.id.Coin1050Button ) ;
        coin2200_btn	= (ImageButton)	findViewById( R.id.Coin2200Button ) ;
        coin5750_btn	= (ImageButton)	findViewById( R.id.Coin5750Button ) ;
        coin12000_btn	= (ImageButton)	findViewById( R.id.Coin12000Button ) ;
        coin25500_btn	= (ImageButton)	findViewById( R.id.Coin25500Button ) ;
        
        gohome_btn		= (ImageButton)	findViewById( R.id.BankGoHomeButton ) ;
        
        cash20_btn.setOnClickListener( this ) ;
        cash53_btn.setOnClickListener( this ) ;
        cash110_btn.setOnClickListener( this ) ;
        cash288_btn.setOnClickListener( this ) ;
        cash600_btn.setOnClickListener( this ) ;
        cash1270_btn.setOnClickListener( this ) ;
        
        coin400_btn.setOnClickListener( this ) ;
        coin1050_btn.setOnClickListener( this ) ;
        coin2200_btn.setOnClickListener( this ) ;
        coin5750_btn.setOnClickListener( this ) ;
        coin12000_btn.setOnClickListener( this ) ;
        coin25500_btn.setOnClickListener( this ) ;
        
        gohome_btn.setOnClickListener( this ) ;
        
        mHandler = new Handler();
        mBankPurchaseObserver = new BankPurchaseObserver( mHandler ) ;
        mBillingService = new BillingService() ;
        mBillingService.setContext( this ) ;

        mPurchaseDatabase = new PurchaseDatabase( this ) ;

        // Check if billing is supported.
        ResponseHandler.register( mBankPurchaseObserver ) ;
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
	public void onClick( View view )
	{
		// TODO Auto-generated method stub
		switch ( view.getId() )
		{
			case R.id.Cash20Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.cash_20", "cash 20" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 20 ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Cash53Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.cash_53", "cash 53" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 53 ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Cash110Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.cash_110", "cash 110" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 110 ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Cash288Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.cash_288", "cash 288" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 288 ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Cash600Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.cash_600", "cash 600" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 600 ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Cash1270Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.cash_1270", "cash 1270" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCash( FabLifeApplication.getProfileManager().getCash() + 1270 ) ;
						mStatusCashView.setCash( FabLifeApplication.getProfileManager().getCash() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Coin400Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.coin_400", "coin 400" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 400 ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Coin1050Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.coin_1050", "coin 1050" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 1050 ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Coin2200Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.coin_2200", "coin 2200" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 2200 ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Coin5750Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.coin_5750", "coin 5750" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 5750 ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Coin12000Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.coin_12000", "coin 12000" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 12000 ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.Coin25500Button :
				FabLifeApplication.playTapEffect() ;
				if ( !mBillingService.checkBillingSupported() )
		        {
					showNoConnectDialog() ;
		        }
				else
				{
					if ( !mBillingService.requestPurchase( "com.miinu.fablife.coin_25500", "coin 25500" ) ) 
					{
						showNoSupportIdDialog() ;
					}
					else
					{
						FabLifeApplication.getProfileManager().setCoin( FabLifeApplication.getProfileManager().getCoin() + 25500 ) ;
						mStatusCoinView.setCoin( FabLifeApplication.getProfileManager().getCoin() ) ;
						mStatusBarLayout.invalidate() ;
					}
	            }
				break ;
			case R.id.BankGoHomeButton :
				FabLifeApplication.playHomeEffect() ;
				action  = true ;
				Intent intent = new Intent( Bank.this, main.class ) ;
				startActivity( intent ) ;
				finish() ;
				break ;
		}		
	}
	
	private void showNoConnectDialog()
	{
		String message = "You can't buy the product. Check your internal connection and please try again." ;
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
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		return ;
	}
	
	
	private static final String TAG = "Bank" ;
    
    private static final String DB_INITIALIZED = "db_initialized" ;
	
	private BankPurchaseObserver mBankPurchaseObserver ;
    private Handler mHandler ;

    private BillingService mBillingService ;
    private PurchaseDatabase mPurchaseDatabase ;
    private Set<String> mOwnedItems = new HashSet<String>() ;

    /**
     * The developer payload that is sent with subsequent
     * purchase requests.
     */
//    private String mPayloadContents = null ;
    
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
        ResponseHandler.register( mBankPurchaseObserver ) ;
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
        ResponseHandler.unregister( mBankPurchaseObserver ) ;
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
    
//    private void prependLogEntry( CharSequence cs )
//    {
//        SpannableStringBuilder contents = new SpannableStringBuilder(cs);
//        contents.append('\n');
//    }
//    
//    private void logProductActivity( String product, String activity )
//    {
//        SpannableStringBuilder contents = new SpannableStringBuilder() ;
//        contents.append( Html.fromHtml("<b>" + product + "</b>: ") ) ;
//        contents.append( activity ) ;
//        prependLogEntry( contents ) ;
//    }
    
    /**
     * A {@link PurchaseObserver} is used to get callbacks when Android Market sends
     * messages to this application so that we can update the UI.
     */
    private class BankPurchaseObserver extends PurchaseObserver
    {
        public BankPurchaseObserver( Handler handler )
        {
            super( Bank.this, handler ) ;
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
//                logProductActivity( itemId, purchaseState.toString() ) ;
            } 
            else
            {
//                logProductActivity( itemId, purchaseState + "\n\t" + developerPayload ) ;
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
//                logProductActivity( request.mProductId, "sending purchase request" ) ;
            } 
            else if ( responseCode == ResponseCode.RESULT_USER_CANCELED )
            {
                if ( Consts.DEBUG )
                {
                    Log.i( TAG, "user canceled purchase" ) ;
                }
//                logProductActivity( request.mProductId, "dismissed purchase dialog" ) ;
            } 
            else
            {
                if ( Consts.DEBUG )
                {
                    Log.i( TAG, "purchase failed" ) ;
                }
//                logProductActivity( request.mProductId, "request purchase returned " + responseCode ) ;
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
