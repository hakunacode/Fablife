package com.miinu.FabLife.Activity;

import java.io.UnsupportedEncodingException;
import com.miinu.FabLife.R;
import com.openfeint.api.OpenFeint;
import com.openfeint.api.resource.Score;
import com.openfeint.api.ui.Dashboard;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class OpenfeintActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState ) ;
		setContentView( R.layout.openfeint_main ) ;
		
		try
		{
			Dashboard.openLeaderboards() ;
			
			Score.setBlobDownloadedDelegate( new Score.BlobDownloadedDelegate()
			{
				@Override 
				public void blobDownloadedForScore( Score score )
				{
					Dashboard.close() ;
					String str = "decode error" ;
					try 
					{
						str = new String( score.blob, "UTF-8" ) ;
					} 
					catch ( UnsupportedEncodingException e )
					{
					}
					Toast.makeText( OpenfeintActivity.this, str, Toast.LENGTH_SHORT).show() ;
				}
			}) ;
		}
		catch( Exception e )
		{
			e.printStackTrace() ;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OpenFeint.onExit();
	}

	@Override
	protected void onPause() {
		super.onPause();
		OpenFeint.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		OpenFeint.onResume();
	}

}
