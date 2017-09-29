package com.miinu.FabLife.Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.content.Context;

public class JobManager 
{	
	Context						mCtx			= null ;
	int[]						mJobs			= new int[ 24 ] ;
	
	public JobManager( Context context )
	{
		mCtx = context ;
		readFromFile() ;
	}
	
	void readFromFile()
	{
		try
    	{
    		InputStreamReader isr = new InputStreamReader( mCtx.openFileInput( "jobs.ini" ) ) ;
            BufferedReader br = new BufferedReader( isr ) ;
            String str ;
            int index = 0 ;
            while( ( str = br.readLine() ) != null )
            {
            	mJobs[ index ] = Integer.parseInt( str ) ;
            	index++ ;
            }
            isr.close();
    	}
    	catch ( Exception e ) { e.printStackTrace(); } 
	}
	
	public void writeToFile()
	{
		try
    	{
    		OutputStreamWriter osw = new OutputStreamWriter( mCtx.openFileOutput( "jobs.ini", Context.MODE_PRIVATE ) ) ;
    		for( int i = 0 ; i < 24 ; i++ )
    		{
    			String str = "" ;
    			str += mJobs[ i ] ;
    			osw.write( str ) ;
    			osw.write( "\n" ) ;
    		}
            osw.close();
        }
    	catch ( Exception e ) { e.printStackTrace(); }
	}
	
	public int getJobPercent( int job )
	{
		return mJobs[ job - 1 ] ;
	}
	
	public void setJobPercent( int job, int percent )
	{
		mJobs[ job - 1 ] = percent ;
	}
}
