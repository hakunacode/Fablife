package com.miinu.FabLife.Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.ViewComponent.PageFlipView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LookBook_View extends Activity implements OnClickListener
{
	private	PageFlipView		mFlipView 			= null ;
	private	ImageButton			mSettingBtn			= null ;
	private	FrameLayout			mLayout				= null ;
	private	ImageButton			mDeleteBtn			= null ;
	private FrameLayout			mSubLayout			= null ;
	private	ImageButton			mLayoutSettingBtn	= null ;
	private	ImageButton			mFaceBookBtn		= null ;
	private	ImageButton			mTwiterBtn			= null ;
	private	ImageButton			mThumbBtn			= null ;
	private	ImageButton			mSaveBtn			= null ;
	private	ImageButton			mMailBtn			= null ;
	private	ImageButton			mLastBtn			= null ;
	private	ImageButton			mNextBtn			= null ;
	private	TextView			mPageView			= null ;
	private	ImageButton			mPrevBtn			= null ;
	private	ImageButton			mFirstBtn			= null ;
	private	ImageButton			mBackBtn			= null ;
	
	private	boolean				isMinimum			= true ;
	private boolean				fromCloset			= false ;
	
	private final String 		FACEBOOK_APP_ID 		= "400652259950348" ;
//	private final String 		FACEBOOK_SECRET_KEY		= "defa84ea1c4a55c0c400b5cbda2e1fb3" ;
//	private final String 		FACEBOOK_PROXY_URL		= "http://api.facebook.com/1.0/facebook.xsd" ;
	private final String 		TWITTER_PREFS_NAME 		= "twitter-fablife-pref" ;
	private final String 		TWITTER_TOKEN 			= "twitter-fablife-token" ;
	private final String 		TWITTER_TOKEN_SECRET 	= "twitter-fablife-token-secret" ;
	private final String 		TWITTER_CONSUMER_KEY 	= "ePhjQrcmFvymAVdOwBS4Q" ;
	private final String 		TWITTER_SECRET_KEY 		= "I5N2P2pICvAtSNQp15iF7rRKjgEIjvYyhpKaPg" ;
	private Facebook 			facebook				= null ;
	private	Twitter 			twitter					= null ;
	private	RequestToken 		mRequestToken 			= null ;
//	private	SharedPreferences 	twitterSettings 		= null ;
	private	int 				TWITTER_AUTH_CODE 		= 0 ;
	private	Dialog 				tumblrDialog 			= null ;
	private	String 				alertMessage 			= "" ;
	private final String[] 		PERMISSIONS 			= new String[] { "publish_stream", "read_stream", "offline_access" } ;
	
	Handler handler = new Handler()
	{
		public void handleMessage( Message msg )
		{
			switch ( msg.what )
			{
				case 0 :
					Toast.makeText( LookBook_View.this, alertMessage, Toast.LENGTH_SHORT ).show() ;
					break ;
				case 1 :
					//Tumblr upload
					tumblrDialog = new Dialog( LookBook_View.this ) ;
			    	tumblrDialog.setContentView( R.layout.tumblr_dialog ) ;
			    	tumblrDialog.setTitle( "Upload photo to Tumblr" ) ;
				    Button ok = (Button) tumblrDialog.findViewById( R.id.upload ) ;
				    final EditText user = (EditText) tumblrDialog.findViewById( R.id.user ) ;
				    final EditText pass = (EditText) tumblrDialog.findViewById( R.id.password ) ;
				    
				    ok.setOnClickListener( new OnClickListener() 
				    {
						@Override
						public void onClick( View v )
						{
							if ( "".equals( user.getText().toString()) || "".equals( pass.getText().toString() ) )
							{
								Toast.makeText( getApplicationContext(), "Email or password is empty!", Toast.LENGTH_SHORT ).show() ;
								return ;
							}
							
							try 
							{
								org.apache.commons.httpclient.HttpClient httpclient = new org.apache.commons.httpclient.HttpClient();
							    String path = Environment.getExternalStorageDirectory().toString() ;
								OutputStream fOut = null ;
								Calendar ca = Calendar.getInstance() ;
								File fPath = new File( path + "/FabLife/Attachments" ) ;
								if ( !fPath.exists() )
									fPath.mkdirs() ;
								String fileName = "tmp_attach_" + ca.getTimeInMillis() ;
								File file = new File( fPath + "/" + fileName + ".png" ) ;
	
								fOut = new FileOutputStream( file ) ;
	
								Bitmap bitmap = mFlipView.getBitmapFromPageNo( mFlipView.mnCurrentPage ) ;
							    bitmap.compress( Bitmap.CompressFormat.PNG, 85, fOut ) ;
								fOut.flush() ;
								fOut.close() ;
								
							    String weblintURL = "http://tumblr.com/api/write" ;
								MultipartPostMethod tmethod = new MultipartPostMethod( weblintURL ) ;
	
							    tmethod.addParameter( "email", user.getText().toString() ) ;
							    tmethod.addParameter( "password", pass.getText().toString() ) ;
							    tmethod.addParameter( "type", "photo" ) ;
							    tmethod.addParameter( "data", file ) ;
							    tmethod.addParameter( "caption", "My lookbook created by the best fashion Game , Fab Life for Android http://bit.ly/znpTAa." ) ;
							    tmethod.addParameter( "text", Html.fromHtml( "<a href=\"http://www.google.com\">Amazing look on my lookbook created by FabLife game for Android.</a> " ).toString() ) ;
							    tmethod.addParameter( "description", Html.fromHtml( "<a href=\"http://itunes.apple.com/us/app/fab-life/id458075948?ls=1&mt=8\">Amazing look on my lookbook created by FabLife game for Android.</a> " ).toString() ) ;
							    tmethod.addParameter( "tags", "FashionAndroid" ) ;
	
							    // Execute and print response
							    httpclient.executeMethod( tmethod ) ;
							    String response = tmethod.getResponseBodyAsString( ) ;
							    if ( response.contains( "Invalid credentials" ) )
							    {
							    	Toast.makeText( getApplicationContext(), "Invalid credentials!", Toast.LENGTH_SHORT ).show() ;
							    }
							    else
							    {
							    	Toast.makeText( getApplicationContext(), "Upload photo to Tumbrl successfully!", Toast.LENGTH_SHORT ).show() ;
							    }
							    tmethod.releaseConnection() ;
							    tumblrDialog.dismiss() ;
							} 
							catch ( Exception e )
							{
								Toast.makeText( LookBook_View.this, "Image upload failed. Error : " + e.getMessage(), Toast.LENGTH_SHORT ).show() ;
							}
						}
					} ) ;
				    tumblrDialog.show() ;
					break ;
				default :
					break ;
			}
		}
	} ;
	
	@Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.lookbook_view ) ;
        
        mFlipView 		= (PageFlipView)	findViewById( R.id.SurfaceView01 ) ;
        
        Typeface t_face = Typeface.createFromAsset( getAssets(), "font/abeatbykai.ttf" ) ;
 
    	mSettingBtn			= (ImageButton) findViewById( R.id.LookBookSettingButton ) ;
    	
    	mLayout				= (FrameLayout)	findViewById( R.id.FrameLayout02 ) ;
    	mDeleteBtn			= (ImageButton)	findViewById( R.id.LookBookDeleteButton ) ;
    	mSubLayout			= (FrameLayout)	findViewById( R.id.FrameLayout03 ) ;
    	mLayoutSettingBtn	= (ImageButton) findViewById( R.id.LookBookLayoutSettingButton ) ;
    	mFaceBookBtn		= (ImageButton) findViewById( R.id.LookBookFaceBookButton ) ;
    	mTwiterBtn			= (ImageButton) findViewById( R.id.LookBookTwiterButton ) ;
    	mThumbBtn			= (ImageButton) findViewById( R.id.LookBookThumbButton ) ;
    	mSaveBtn			= (ImageButton) findViewById( R.id.LookBookSaveButton ) ;
    	mMailBtn			= (ImageButton) findViewById( R.id.LookBookMailButton ) ;
    	mLastBtn			= (ImageButton) findViewById( R.id.LookBookLastButton ) ;
    	mNextBtn			= (ImageButton) findViewById( R.id.LookBookNextButton ) ;
    	mPageView			= (TextView) 	findViewById( R.id.LookBookPageView ) ;
    	mPrevBtn			= (ImageButton) findViewById( R.id.LookBookPrevButton ) ;
    	mFirstBtn			= (ImageButton) findViewById( R.id.LookBookFirstButton ) ;
    	mBackBtn			= (ImageButton) findViewById( R.id.LookBookBackButton ) ;
    	
    	mSettingBtn.setOnClickListener( this ) ;
    	mDeleteBtn.setOnClickListener( this ) ;
    	mLayoutSettingBtn.setOnClickListener( this ) ;
    	mFaceBookBtn.setOnClickListener( this ) ;
    	mTwiterBtn.setOnClickListener( this ) ;
    	mThumbBtn.setOnClickListener( this ) ;
    	mSaveBtn.setOnClickListener( this ) ;
    	mMailBtn.setOnClickListener( this ) ;
    	mLastBtn.setOnClickListener( this ) ;
    	mNextBtn.setOnClickListener( this ) ;
    	mPrevBtn.setOnClickListener( this ) ;
    	mFirstBtn.setOnClickListener( this ) ;
    	mBackBtn.setOnClickListener( this ) ;
    	
    	mPageView.setTypeface( t_face ) ;
        
        if ( mFlipView.mnCount != 0 )
        {
        	fromCloset = getIntent().getBooleanExtra( "fromCloset", false ) ;
        	if ( fromCloset )
        	{
        		mFlipView.mnCurrentPage = mFlipView.mnCount - 1 ;
        	}
        	mSettingBtn.setVisibility( View.VISIBLE ) ;
        }
        
        mFlipView.setParent( LookBook_View.this ) ;
        
        setPageText() ;
        
        overridePendingTransition( R.anim.activity_slide_right, R.anim.hold ) ;
    }
		
	@Override
	public void onClick( View v )
	{
		// TODO Auto-generated method stub
		if ( v.getId() == R.id.LookBookSettingButton )
		{
			FabLifeApplication.playTapEffect() ;
			mLayout.setVisibility( View.VISIBLE ) ;
			mSettingBtn.setVisibility( View.GONE ) ;
			isMinimum = false ;
			Animation anim = AnimationUtils.loadAnimation( LookBook_View.this, R.anim.slide_in ) ;
			anim.setDuration( 500 ) ;
			mSubLayout.startAnimation( anim ) ;
		}
		else if ( v.getId() == R.id.LookBookLayoutSettingButton )
		{
			FabLifeApplication.playTapEffect() ;
			Animation anim = AnimationUtils.loadAnimation( LookBook_View.this, R.anim.slide_out ) ;
			anim.setDuration( 500 ) ;
			anim.setAnimationListener( new AnimationListener()
			{
				@Override
				public void onAnimationEnd( Animation animation )
				{
					// TODO Auto-generated method stub
					mLayout.setVisibility( View.GONE ) ;
					mSettingBtn.setVisibility( View.VISIBLE ) ;
					isMinimum = true ;
				}

				@Override
				public void onAnimationRepeat( Animation animation )
				{
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationStart( Animation animation )
				{
					// TODO Auto-generated method stub
					
				}
			}) ;
			mSubLayout.startAnimation( anim ) ;
		}
		else if ( v.getId() == R.id.LookBookNextButton )
		{
			FabLifeApplication.playTapEffect() ;
			try
			{
				mFlipView.goNext() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
		else if ( v.getId() == R.id.LookBookPrevButton )
		{
			FabLifeApplication.playTapEffect() ;
			try
			{
				mFlipView.goPrev() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
		else if ( v.getId() == R.id.LookBookLastButton )
		{
			FabLifeApplication.playTapEffect() ;
			try
			{
				mFlipView.goLast() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
		else if ( v.getId() == R.id.LookBookFirstButton )
		{
			FabLifeApplication.playTapEffect() ;
			try
			{
				mFlipView.goFirst() ;
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
		else if ( v.getId() == R.id.LookBookDeleteButton )
		{
			FabLifeApplication.playTapEffect() ;
			try
			{
				if ( mFlipView.canForward() )
				{
					showDeleteDialog() ;
				}
			}
			catch ( UnsupportedEncodingException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace() ;
			}
		}
		else if ( v.getId() == R.id.LookBookBackButton )
		{
			FabLifeApplication.playTapEffect() ;
			Intent intent = new Intent( this, LookBook.class ) ;
			startActivity( intent ) ;
			finish() ;
		}
		else if ( v.getId() == R.id.LookBookFaceBookButton )
		{
			FabLifeApplication.playTapEffect() ;
			facebook = new Facebook( FACEBOOK_APP_ID ) ;
			facebook.authorize( LookBook_View.this, PERMISSIONS, new DialogListener()
			{
				@Override
				public void onFacebookError( FacebookError e )
				{
					alertMessage = "Image upload failed. Error : " + e.getMessage() ;
					handler.sendEmptyMessage( 0 ) ;
				}
				
				@Override
				public void onError( DialogError e )
				{
					alertMessage = "Image upload failed. Error : " + e.getMessage() ;
					handler.sendEmptyMessage( 0 ) ;
				}
				
				@Override
				public void onComplete( Bundle values )
				{
					byte[] data = null ;
		            ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
		            Bitmap bitmap = mFlipView.getBitmapFromPageNo( mFlipView.mnCurrentPage ) ;
		            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, baos ) ;
		            data = baos.toByteArray() ;

//		            private void postPhoto_Message()
//		            {
//		            Bundle params = new Bundle();       
//		            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		            BitmapDrawable bd =     (BitmapDrawable)getResources().getDrawable(R.drawable.icon);    
//		            Bitmap mutableBitmap = bd.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
//		            mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//		            byte[] imgData = stream.toByteArray();  
//		            params.putByteArray("photo",imgData);           
//		            params.putString("caption", "My score is !");               
//		            params.putString("href","http://www.zazzle.com/blackoakgames");        
//		            params.putString("attachment", "{\"name\":\""+"seriesname"+"\","
//		            +"\"href\":\""+"http://www.google.com\","+"\"description\":\""+"description"+"\"}");
//		            params.putString("link", "http://www.blackoakgames.com/ck");
//		            Utility.mAsyncRunner.request("me/photos", params, "POST",
//		            new PhotoUploadListener(), null);
//					}
		            
		            Bundle params = new Bundle() ;
		            params.putString( Facebook.TOKEN, facebook.getAccessToken() ) ;
		            params.putString( "caption", "My lookbook created by the best fashion Game , Fab Life for Android http://bit.ly/znpTAa." ) ;
		            params.putString( "description", Html.fromHtml( "<a href=\"http://itunes.apple.com/us/app/fab-life/id458075948?ls=1&mt=8\">Amazing look on my lookbook created by FabLife game for Android.</a> " ).toString() ) ;
		            params.putString( "method", "photos.upload" ) ;
		            params.putByteArray( "picture", data ) ;

		            AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner( facebook ) ;
		            mAsyncRunner.request( null, params, "POST", new RequestListener() 
		            {
		            	@Override
						public void onMalformedURLException( MalformedURLException e, Object state )
						{
		            		alertMessage = "Image upload failed. Error : " + e.getMessage() ;
							handler.sendEmptyMessage( 0 ) ;
						}
						
						@Override
						public void onIOException( IOException e, Object state )
						{
							alertMessage = "Image upload failed. Error : " + e.getMessage() ;
							handler.sendEmptyMessage( 0 ) ;
						}
						
						@Override
						public void onFileNotFoundException( FileNotFoundException e, Object state )
						{
							alertMessage = "Image upload failed. Error : " + e.getMessage() ;
							handler.sendEmptyMessage( 0 ) ;
						}
						
						@Override
						public void onFacebookError( FacebookError e, Object state )
						{
							alertMessage = "Image upload failed. Error : " + e.getMessage() ;
							handler.sendEmptyMessage( 0 ) ;
						}
						
						@Override
						public void onComplete( String response, Object state )
						{
							alertMessage = "Share photo to facebook completed!";
							handler.sendEmptyMessage( 0 ) ;
						}
		            }, null ) ;
				}
				
				@Override
				public void onCancel() 
				{
					alertMessage = "Image upload canceled." ;
					handler.sendEmptyMessage(0);
				}
			} ) ;
		}
		else if ( v.getId() == R.id.LookBookTwiterButton )
		{
			FabLifeApplication.playTapEffect() ;
			AccessToken token = getTwitterToken() ;
			if ( token == null )
			{
				twitter = new TwitterFactory().getInstance() ;
				twitter.setOAuthConsumer( TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY ) ;
				String callbackURL = getResources().getString( R.string.twitter_callback ) ;
				try
				{
				     mRequestToken = twitter.getOAuthRequestToken( callbackURL ) ;
				     Intent i = new Intent( getApplicationContext(), TwitterWebActivity.class ) ;
				     i.putExtra( "URL", mRequestToken.getAuthenticationURL() ) ;
				     startActivityForResult( i, TWITTER_AUTH_CODE ) ;
				}
				catch ( TwitterException e )
				{
					alertMessage = "Image upload failed. Error : " + e.getMessage() ;
					handler.sendEmptyMessage( 0 ) ;
				}
			}
			else
			{
				doUploadTwitterPhoto( token.getToken(), token.getTokenSecret() ) ;
			}
		}
		else if ( v.getId() == R.id.LookBookThumbButton )
		{
			FabLifeApplication.playTapEffect() ;
			handler.sendEmptyMessage( 1 ) ;
		}
		else if ( v.getId() == R.id.LookBookSaveButton )
		{
			FabLifeApplication.playTapEffect() ;
			try
			{
				String url = saveFile() ;
				if ( url != null )
				{
					sendBroadcast( new Intent( Intent.ACTION_MEDIA_MOUNTED, Uri.parse( "file://"
		                + Environment.getExternalStorageDirectory() ) ) ) ;
					Toast.makeText( this, "Image is saved to \"" + url + "\".", Toast.LENGTH_SHORT ).show() ;
				}
			}
			catch( Exception e )
			{
				e.printStackTrace() ;
			}
		}
		else if ( v.getId() == R.id.LookBookMailButton )
		{
			FabLifeApplication.playTapEffect() ;
			sendMail() ;
		}
	}
	
	String saveFile() throws Exception
	{
		if ( !Environment.getExternalStorageState().equalsIgnoreCase( Environment.MEDIA_MOUNTED ) )
		{
			Toast.makeText( this, "Your device has no external storage.", Toast.LENGTH_SHORT ).show() ;
			return null ;
		}
		String file_name = null ;
		try 
		{
			String out_dir = Environment.getExternalStorageDirectory().getPath() + "/FabLife" ;
			File file = new File( out_dir ) ;
			if ( !file.isDirectory() )
			{
				file.mkdirs() ;
			}
			
			Bitmap bitmap = mFlipView.getBitmapFromPageNo( mFlipView.mnCurrentPage ) ;
			if ( bitmap != null )
			{
				Date today = new Date() ;
				String fileName = new SimpleDateFormat( "yyMMddHHmmss" ).format( today )+ ".jpg" ;
				file = new File( out_dir + "/" + fileName ) ;
				if( !file.exists() )
				{
					file.createNewFile() ;
				}
				FileOutputStream out = new FileOutputStream( file ) ;
				bitmap.compress( Bitmap.CompressFormat.JPEG, 100, out ) ;
				out.flush() ;
				out.close() ;
				bitmap.recycle() ;
				file_name = out_dir + "/" + fileName ;
			}
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			Toast.makeText( this, "File save failed. Error: " + e.getMessage(), Toast.LENGTH_SHORT ) ;
			return null ;
		}
		return file_name ;
	}
	
	void sendMail()
	{
		try 
		{
			String out_dir = Environment.getExternalStorageDirectory().getPath() + "/FabLife" ;
			File file = new File( out_dir ) ;
			if ( !file.isDirectory() )
			{
				file.mkdirs() ;
			}
			
			Bitmap bitmap = mFlipView.getBitmapFromPageNo( mFlipView.mnCurrentPage ) ;
			if ( bitmap != null )
			{
				Date today = new Date() ;
				String fileName = new SimpleDateFormat( "yyMMddHHmmss" ).format( today )+ ".jpg" ;
				file = new File( out_dir + "/" + fileName ) ;
				if( !file.exists() )
				{
					file.createNewFile() ;
				}
				FileOutputStream out = new FileOutputStream( file ) ;
				bitmap.compress( Bitmap.CompressFormat.JPEG, 100, out ) ;
				out.flush() ;
				out.close() ;

				sendBroadcast( new Intent( Intent.ACTION_MEDIA_MOUNTED, Uri.parse( "file://"
		                + Environment.getExternalStorageDirectory() ) ) ) ;
				
				Intent emailIntent= new Intent( Intent.ACTION_SEND ) ;
		        emailIntent.setType( "image/jpeg" ) ;
				
				final PackageManager pm = getPackageManager() ;
				final List<ResolveInfo> matches = pm.queryIntentActivities( emailIntent, 0 ) ;
				ResolveInfo best = null ;
				for ( final ResolveInfo info : matches )
				{
					if ( info.activityInfo.packageName.endsWith( ".email" ) || info.activityInfo.packageName.endsWith( ".gm" ) || info.activityInfo.name.toLowerCase().contains( "gmail" ) )
						best = info ;
				}
				if ( best != null )
				{
					emailIntent.setClassName( best.activityInfo.packageName, best.activityInfo.name ) ;
				}
				
				Uri uri = Uri.fromFile( file ) ;
		        emailIntent.putExtra( Intent.EXTRA_STREAM, uri ) ;
				emailIntent.putExtra( Intent.EXTRA_TITLE, "My lookbook created by the best fashion Game , Fab Life for Android.");
				emailIntent.putExtra( Intent.EXTRA_SUBJECT, "My lookbook created by the best fashion Game , Fab Life for Android.");
				emailIntent.putExtra( Intent.EXTRA_TEXT, "<a href='http://itunes.apple.com/us/app/fab-life/id458075948?ls=1&mt=8'>Amazing look on my lookbook created by FabLife game for Android.</a>" ) ;
			    startActivity( emailIntent ) ;				
			}
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
	}
	
	public void gotoCloset()
	{
		Intent intent = new Intent( this, MyCloset.class ) ;
		intent.putExtra( "type", 1 ) ;
		startActivity( intent ) ;
		finish() ;
	}
	
	public void setVisibility()
	{
		try
		{
			if ( mFlipView.mnCount <= 0 || !mFlipView.canForward() )
			{
				if ( isMinimum ) mSettingBtn.setVisibility( View.GONE ) ;
				else mLayout.setVisibility( View.GONE ) ;
			}
			else
			{
				if ( isMinimum ) mSettingBtn.setVisibility( View.VISIBLE ) ;
				else mLayout.setVisibility( View.VISIBLE ) ;
			}
		}
		catch ( UnsupportedEncodingException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
	}
	
	public void setPageText()
	{
		String page_text = null ;
		if ( mFlipView.mnCount > 0 )
		{
			if ( mFlipView.mnCount != mFlipView.mnCurrentPage )
				page_text = "" + ( mFlipView.mnCurrentPage + 1 ) + "/" + mFlipView.mnCount ;
		}
		mPageView.setText( page_text ) ;
	}
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
	}
	
	private void showDeleteDialog()
	{
		String message = "Are you sure you delete this item?" ;
		AlertDialog.Builder builder = new AlertDialog.Builder( this ) ;
		builder.setMessage( message )
        .setCancelable( false )
        .setPositiveButton( "YES", new DialogInterface.OnClickListener() 
        	{
        		public void onClick( DialogInterface dialog, int id )
        		{
        			mFlipView.delete() ;
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
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause() ;
		FabLifeApplication.stopMainMusic() ;
	}
	
	private void doUploadTwitterPhoto( String token, String tokenSecret )
	{
		try
		{
			// Pair up our request with the response	
			Configuration conf = new ConfigurationBuilder()
//			    .setMediaProviderAPIKey( "0256be84ffb4e09364e3e569d0e3dfd7" )
			    .setOAuthConsumerKey( TWITTER_CONSUMER_KEY )
			    .setOAuthConsumerSecret( TWITTER_SECRET_KEY )
			    .setOAuthAccessToken( token )
			    .setOAuthAccessTokenSecret( tokenSecret )
			    .build() ;
			
			twitter = new TwitterFactory( conf ).getInstance() ;
			String path = Environment.getExternalStorageDirectory().getPath() ;
			OutputStream fOut = null ;
			Calendar ca = Calendar.getInstance() ;
			File fPath = new File( path + "/FabLife/Attachments" ) ;
			if ( !fPath.exists() )
				fPath.mkdirs() ;
			String fileName = "tmp_attach_" + ca.getTimeInMillis() ;
			File file = new File( fPath + "/" + fileName + ".png" ) ;
			fOut = new FileOutputStream( file ) ;
			Bitmap bitmap = mFlipView.getBitmapFromPageNo( mFlipView.mnCurrentPage ) ;
			bitmap.compress( Bitmap.CompressFormat.PNG, 100, fOut ) ;
			fOut.flush() ;
			fOut.close() ;
			
			ImageUpload upload = new ImageUploadFactory( conf ).getInstance( MediaProvider.TWITTER ) ;
			upload.upload( file, "My lookbook created by the best fashion Game , Fab Life for Android http://bit.ly/znpTAa." ) ;
			
			alertMessage = "Upload photo to twitter completed!" ;
			handler.sendEmptyMessage( 0 ) ;
		} 
		catch ( Exception e )
		{
			alertMessage = "Error: " + e.getMessage() ;
			handler.sendEmptyMessage( 0 ) ;
		}
	}
	
	private void saveTwitterToken( String token, String tokenSecret )
	{
		SharedPreferences sp = getSharedPreferences( TWITTER_PREFS_NAME, Context.MODE_PRIVATE ) ;
		Editor editor = sp.edit() ;
		editor.putString( TWITTER_TOKEN, token ) ;
		editor.putString( TWITTER_TOKEN_SECRET, tokenSecret ) ;
		editor.commit() ;
	}
	
	private AccessToken getTwitterToken()
	{
		SharedPreferences settings = getSharedPreferences( TWITTER_PREFS_NAME, Context.MODE_PRIVATE ) ;
		String oauthTokenString = settings.getString( TWITTER_TOKEN, null ) ;
		String tokenSecret = settings.getString( TWITTER_TOKEN_SECRET, null ) ;
		if ( oauthTokenString == null || tokenSecret == null )
			return null ;
		AccessToken token = new AccessToken( oauthTokenString, tokenSecret ) ;
		return token ;
	}
	
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data )
	{
		if ( requestCode == TWITTER_AUTH_CODE )
		{
			if ( resultCode == Activity.RESULT_OK )
			{
				String oauthVerifier = (String) data.getExtras().get( "oauth_verifier" ) ;
				if ( oauthVerifier == null )
					return ;
				AccessToken at = null ;
				try 
				{
					// Pair up our request with the response
					at = twitter.getOAuthAccessToken( mRequestToken, oauthVerifier ) ;
					saveTwitterToken( at.getToken(), at.getTokenSecret() ) ;
					doUploadTwitterPhoto( at.getToken(), at.getTokenSecret() ) ;
				} 
				catch ( Exception e )
				{
					alertMessage = "Error: " + e.getMessage() ;
					handler.sendEmptyMessage( 0 ) ;
				}
			}
		}
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume() ;
		FabLifeApplication.playMainMusic() ;
	}
}
