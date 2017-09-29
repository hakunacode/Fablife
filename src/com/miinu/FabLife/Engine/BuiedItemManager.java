package com.miinu.FabLife.Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import android.content.Context;

public class BuiedItemManager 
{
	final int			SUBJECT_ALL			= 1 ;
	final int			SUBJECT_TOP			= 2 ;
	final int			SUBJECT_BOTTOM		= 3 ;
	final int			SUBJECT_DRESS		= 4 ;
	final int			SUBJECT_OUTERWEAR	= 5 ;
	final int			SUBJECT_SHOES		= 6 ;
	final int			SUBJECT_BAG			= 7 ;
	final int			SUBJECT_JEWEL		= 8 ;
	final int			SUBJECT_HAIR		= 9 ;
	final int			SUBJECT_MAKEUP		= 10 ;
	
	final int			SUBJECT_SKINCOLOR	= 11 ;
	final int			SUBJECT_BLUSHON		= 12 ;
	final int			SUBJECT_EYESHADOW	= 13 ;
	final int			SUBJECT_MASCARA		= 14 ;
	final int			SUBJECT_EYEBROW		= 15 ;
	final int			SUBJECT_EYECOLOR	= 16 ;
	final int			SUBJECT_LIPS		= 17 ;
	
	Context				mCtx				= null ;
	int					mSubject			= SUBJECT_ALL ;
	
	boolean				isTemp				= false ;
	ArrayList<String>	mItems				= new ArrayList<String>() ;
	ArrayList<String>	mTemp				= new ArrayList<String>() ;
	
	public BuiedItemManager( Context context )
	{
		mCtx = context ;
		readFromFile() ;
	}
	
	void readFromFile()
	{
		try
    	{
    		InputStreamReader isr = new InputStreamReader( mCtx.openFileInput( "buied.txt" ) ) ;
            BufferedReader br = new BufferedReader( isr ) ;
            String str ;
            while( ( str = br.readLine() ) != null )
            {
            	mItems.add( str ) ;
            }
            isr.close();
    	}
    	catch ( Exception e ) { e.printStackTrace(); } 
	}
	
	public void writeToFile()
	{
		 try
    	 {
    		 OutputStreamWriter osw = new OutputStreamWriter( mCtx.openFileOutput( "buied.txt", Context.MODE_PRIVATE ) ) ;
    		 for( int i = 0 ; i < mItems.size() ; i++ )
    		 {
    			 osw.write( mItems.get( i ) ) ;
    			 osw.write( "\n" ) ;
    		 }
             osw.close();
         }
    	 catch ( Exception e ) { e.printStackTrace(); }
	}
	
	String getCompareStringFromSubject( int subject )
	{
		String ret = null ;
		switch( subject )
		{
			case SUBJECT_TOP :
				ret = "top" ;
				break ;
			case SUBJECT_BOTTOM :
				ret = "bottom" ;
				break ;
			case SUBJECT_DRESS :
				ret = "dress" ;
				break ;
			case SUBJECT_OUTERWEAR :
				ret = "outerwear" ;
				break ;
			case SUBJECT_SHOES :
				ret = "shoes" ;
				break ;
			case SUBJECT_BAG :
				ret = "bag" ;
				break ;
			case SUBJECT_JEWEL :
				ret = "jewel" ;
				break ;
			case SUBJECT_HAIR :
				ret = "hair" ;
				break ;
			case SUBJECT_SKINCOLOR :
				ret = "skincolor" ;
				break ;
			case SUBJECT_BLUSHON :
				ret = "blushon" ;
				break ;
			case SUBJECT_EYESHADOW :
				ret = "eyeshadow" ;
				break ;
			case SUBJECT_MASCARA :
				ret = "mascara" ;
				break ;
			case SUBJECT_EYEBROW :
				ret = "eyebrow" ;
				break ;
			case SUBJECT_EYECOLOR :
				ret = "eyecolor" ;
				break ;
			case SUBJECT_LIPS :
				ret = "lips" ;
				break ;
		}
		return ret ;
	}
	
	public int getAllCount()
	{
		return mItems.size() ;
	}
	
	public int getCount()
	{
		int count = 0 ;
		String compare = getCompareStringFromSubject( mSubject ) ;
		if ( isTemp )
		{
			if ( mSubject == SUBJECT_ALL )
			{
				count = mTemp.size() ;
			}
			else if ( mSubject == SUBJECT_HAIR )
			{
				for ( int i = 0 ; i < mTemp.size() ; i++ )
				{
					String item = mTemp.get( i ) ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
						count++ ;
				}
			}
			else
			{
				for ( int i = 0 ; i < mTemp.size() ; i++ )
				{
					String item = mTemp.get( i ) ;
					if ( item.contains( compare ) )
						count++ ;
				}
			}
		}
		else
		{
			if ( mSubject == SUBJECT_ALL )
			{
				count = mItems.size() ;
			}
			else if ( mSubject == SUBJECT_HAIR )
			{
				for ( int i = 0 ; i < mItems.size() ; i++ )
				{
					String item = mItems.get( i ) ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
						count++ ;
				}
			}
			else
			{
				for ( int i = 0 ; i < mItems.size() ; i++ )
				{
					String item = mItems.get( i ) ;
					if ( item.contains( compare ) )
						count++ ;
				}
			}
		}
		return count ;
	}
	
	public String getItemByIndex( int index )
	{
		int count = 0 ;
		String compare = getCompareStringFromSubject( mSubject ) ;
		if ( isTemp )
		{
			if ( mSubject == SUBJECT_ALL )
			{
				return mTemp.get( index ) ;
			}
			else if ( mSubject == SUBJECT_HAIR )
			{
				Iterator<String> it = mTemp.iterator() ;
				while( it.hasNext() )
				{
					String item = it.next() ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
						count++ ;
					if ( ( count - 1 ) == index )
					{
						return item ;
					}
				}
			}
			else
			{	
				Iterator<String> it = mTemp.iterator() ;
				while( it.hasNext() )
				{
					String item = it.next() ;
					if ( item.contains( compare ) )
						count++ ;
					if ( ( count - 1 ) == index )
					{
						return item ;
					}
				}
			}
		}
		else
		{
			if ( mSubject == SUBJECT_ALL )
			{
				return mItems.get( index ) ;
			}
			else if ( mSubject == SUBJECT_HAIR )
			{
				Iterator<String> it = mItems.iterator() ;
				while( it.hasNext() )
				{
					String item = it.next() ;
					if ( item.contains( "hair" ) || item.contains( "funky" ) )
						count++ ;
					if ( ( count - 1 ) == index )
					{
						return item ;
					}
				}
			}
			else
			{	
				Iterator<String> it = mItems.iterator() ;
				while( it.hasNext() )
				{
					String item = it.next() ;
					if ( item.contains( compare ) )
						count++ ;
					if ( ( count - 1 ) == index )
					{
						return item ;
					}
				}
			}
		}
		return null ;
	}
	
	public void addItem( String name )
	{
		if ( isTemp )
		{
			mTemp.add( name ) ;
		}
		else
		{
			mItems.add( name ) ;
		}
	}
	
	public void removeByName( String name )
	{
		if ( isTemp )
		{
			mTemp.remove( name ) ;
		}
		else
		{
			mItems.remove( name ) ;
		}
	}
	
	public void setSubject( int subject )
	{
		mSubject = subject ;
	}
	
	public boolean contains( String name )
	{
		return mItems.contains( name ) ;
	}
	
	@SuppressWarnings("unchecked")
	public void createTemp()
	{
		isTemp = true ;
		mSubject = SUBJECT_ALL ;
		mTemp.clear() ;
		mTemp = null ;
		mTemp = (ArrayList<String>)mItems.clone() ;
	}
	
	public void clearTemp()
	{
		mSubject = SUBJECT_ALL ;
		mTemp.clear() ;
		isTemp = false ;
	}
	
	@SuppressWarnings("unchecked")
	public void save()
	{
		mItems.clear() ;
		mItems = null ;
		mItems = (ArrayList<String>)mTemp.clone() ;
		
		writeToFile() ;
	}
	
	public void clear()
	{
		mItems.clear() ;
		mItems = null ;
		mTemp.clear() ;
		mTemp = null ;
	}
}
