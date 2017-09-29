package com.miinu.FabLife.Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import android.content.Context;

public class AppliedItemManager 
{
	public final int			SUBJECT_ALL			= 1 ;
	public final int			SUBJECT_TOP			= 2 ;
	public final int			SUBJECT_BOTTOM		= 3 ;

	public final int			SUBJECT_DRESS		= 5 ;

	public final int			SUBJECT_OUTERWEAR	= 7 ;
	public final int			SUBJECT_SHOES		= 8 ;

	public final int			SUBJECT_BAG_1		= 10 ;
	public final int			SUBJECT_BAG_2		= 11 ;
	public final int			SUBJECT_BAG_3		= 12 ;
	public final int			SUBJECT_BAG_4		= 13 ;
	public final int			SUBJECT_JEWEL_1		= 14 ;
	public final int			SUBJECT_JEWEL_2		= 15 ;
	public final int			SUBJECT_JEWEL_3		= 16 ;
	public final int			SUBJECT_JEWEL_4		= 17 ;
	public final int			SUBJECT_JEWEL_5		= 18 ;
	public final int			SUBJECT_JEWEL_6		= 19 ;
	public final int			SUBJECT_JEWEL_7		= 20 ;
	public final int			SUBJECT_JEWEL_8		= 21 ;
	public final int			SUBJECT_HAIR		= 22 ;
	public final int			SUBJECT_FUNKY		= 23 ;
	public final int			SUBJECT_SKINCOLOR	= 24 ;
	public final int			SUBJECT_BLUSHON		= 25 ;
	public final int			SUBJECT_EYESHADOW	= 26 ;
	public final int			SUBJECT_MASCARA		= 27 ;
	public final int			SUBJECT_EYEBROW		= 28 ;
	public final int			SUBJECT_EYECOLOR	= 29 ;
	public final int			SUBJECT_LIPS		= 30 ;
	
	Context						mCtx				= null ;
	
	boolean						isTemp				= false ;
	
	ArrayList<String>			mMakeUpItems		= new ArrayList<String>() ;
	ArrayList<String>			mNoMakeUpItems		= new ArrayList<String>() ;
	ArrayList<String>			mMakeUpTemp			= new ArrayList<String>() ;
	ArrayList<String>			mNoMakeUpTemp		= new ArrayList<String>() ;
	
	public AppliedItemManager( Context context )
	{
		mCtx = context ;
		readFromFile() ;
	}
	
	void readFromFile()
	{
		try
    	{
    		InputStreamReader isr = new InputStreamReader( mCtx.openFileInput( "applied.txt" ) ) ;
            BufferedReader br = new BufferedReader( isr ) ;
            String str ;
            while( ( str = br.readLine() ) != null )
            {
            	if ( str.contains( "skincolor" ) || str.contains( "blushon" ) || str.contains( "eyeshadow" )
            			|| str.contains( "mascara" ) || str.contains( "eyebrow" ) || str.contains( "eyecolor" )
            			|| str.contains( "lips" ) )
            	{
            		mMakeUpItems.add( str ) ;
            	}
            	else
            		mNoMakeUpItems.add( str ) ;
            }
            isr.close();
    	}
    	catch ( Exception e ) { e.printStackTrace(); } 
	}
	
	public void writeToFile()
	{
		 try
    	 {
    		 OutputStreamWriter osw = new OutputStreamWriter( mCtx.openFileOutput( "applied.txt", Context.MODE_PRIVATE ) ) ;
    		 for( int i = 0 ; i < mMakeUpItems.size() ; i++ )
    		 {
    			 osw.write( mMakeUpItems.get( i ) ) ;
    			 osw.write( "\n" ) ;
    		 }
    		 for( int i = 0 ; i < mNoMakeUpItems.size() ; i++ )
    		 {
    			 osw.write( mNoMakeUpItems.get( i ) ) ;
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
			case SUBJECT_BAG_1 :
				ret = "bag-1" ;
				break ;
			case SUBJECT_BAG_2 :
				ret = "bag-2" ;
				break ;
			case SUBJECT_BAG_3 :
				ret = "bag-3" ;
				break ;
			case SUBJECT_BAG_4 :
				ret = "bag-4" ;
				break ;
			case SUBJECT_JEWEL_1 :
				ret = "jewel-1" ;
				break ;
			case SUBJECT_JEWEL_2 :
				ret = "jewel-2" ;
				break ;
			case SUBJECT_JEWEL_3 :
				ret = "jewel-3" ;
				break ;
			case SUBJECT_JEWEL_4 :
				ret = "jewel-4" ;
				break ;
			case SUBJECT_JEWEL_5 :
				ret = "jewel-5" ;
				break ;
			case SUBJECT_JEWEL_6 :
				ret = "jewel-6" ;
				break ;
			case SUBJECT_JEWEL_7 :
				ret = "jewel-7" ;
				break ;
			case SUBJECT_JEWEL_8 :
				ret = "jewel-8" ;
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
		return mMakeUpItems.size() + mNoMakeUpItems.size() ;
	}
	
	public int getNoMakeUpCount()
	{
		if ( isTemp )
		{
			return mNoMakeUpTemp.size() ;
		}
		else
		{
			return mNoMakeUpItems.size() ;
		}
	}
	
	public String getNoMakeUpItemByIndex( int index )
	{
		if ( isTemp )
		{
			String item = mNoMakeUpTemp.get( index ) ;
			return item ;
		}
		else
		{
			String item = mNoMakeUpItems.get( index ) ;
			return item ;
		}
	}
	
	public int getMakeUpCount()
	{
		if ( isTemp )
		{
			return mMakeUpTemp.size() ;
		}
		else
		{
			return mMakeUpItems.size() ;
		}
	}
	
	public String getMakeUpItemByIndex( int index )
	{
		if ( isTemp )
		{
			String item = mMakeUpTemp.get( index ) ;
			return item ;
		}
		else
		{
			String item = mMakeUpItems.get( index ) ;
			return item ;
		}
	}
	
	public String getItemFromSubject( int subject )
	{
		String compare = getCompareStringFromSubject( subject ) ;
		if ( isTemp )
		{
			if ( subject >= SUBJECT_SKINCOLOR )
			{
				for ( int i = 0 ; i < mMakeUpTemp.size() ; i++ )
				{
					String item = mMakeUpTemp.get( i ) ;
					if ( item.contains( compare ) )
					{
						return item ;
					}
				}
			}
			else
			{
				for ( int i = 0 ; i < mNoMakeUpTemp.size() ; i++ )
				{
					String item = mNoMakeUpTemp.get( i ) ;
					if ( subject == SUBJECT_HAIR )
					{
						if ( item.contains( compare ) || item.contains( "funky" ) )
							return item ;
					}
					else
					{
						if ( item.contains( compare ) )
						{
							return item ;
						}
					}
				}
			}
		}
		else
		{
			if ( subject >= SUBJECT_SKINCOLOR )
			{
				for ( int i = 0 ; i < mMakeUpItems.size() ; i++ )
				{
					String item = mMakeUpItems.get( i ) ;
					if ( item.contains( compare ) )
					{
						return item ;
					}
				}
			}
			else
			{
				for ( int i = 0 ; i < mNoMakeUpItems.size() ; i++ )
				{
					String item = mNoMakeUpItems.get( i ) ;
					if ( subject == SUBJECT_HAIR )
					{
						if ( item.contains( compare ) || item.contains( "funky" ) )
							return item ;
					}
					else
					{
						if ( item.contains( compare ) )
						{
							return item ;
						}
					}
				}
			}
		}
		return null ;
	}
	
	public int getSubjectFromName( String name )
	{
		for ( int i = SUBJECT_TOP ; i <= SUBJECT_LIPS ; i++ )
		{
			String compare = getCompareStringFromSubject( i ) ;
			if ( compare != null )
			{
				if ( name.contains( compare ) )
				{
					return i ;
				}
			}
		}
		
		if ( name.contains( "funky" ) )
			return SUBJECT_HAIR ;
		
		return SUBJECT_ALL ;
	}
	
	public void addNoMakeUpItem( int index, String name )
	{
		if ( index >= getNoMakeUpCount() )
		{
			mNoMakeUpTemp.add( name ) ;
		}
		else
			mNoMakeUpTemp.add( index, name ) ;
	}
	
	void removeSubject( int subject )
	{	
		String compare = getCompareStringFromSubject( subject ) ;	
		if ( subject >= SUBJECT_SKINCOLOR )
		{
			for ( int i = 0 ; i < mMakeUpTemp.size() ; i++ )
			{
				String item = mMakeUpTemp.get( i ) ;
				if ( item.contains( compare ) )
				{
					mMakeUpTemp.remove( i ) ;
				}
			}
		}
		else
		{
			for ( int i = 0 ; i < mNoMakeUpTemp.size() ; i++ )
			{
				String item = mNoMakeUpTemp.get( i ) ;
				if ( subject == SUBJECT_HAIR )
				{
					if ( item.contains( compare ) || item.contains( "funky" ) )
					{
						mNoMakeUpTemp.remove( i ) ;
					}
				}
				else
				{
					if ( item.contains( compare ) )
					{
						mNoMakeUpTemp.remove( i ) ;
					}
				}
			}
		}
	}
	
	public String addItem( String name )
	{
		String ret = null ;
		int subject = getSubjectFromName( name ) ;
		String item = getItemFromSubject( subject ) ;
		if ( item == null )
		{
			if ( subject >= SUBJECT_SKINCOLOR )
			{
				mMakeUpTemp.add( 0, name ) ;
			}
			else
			{
				mNoMakeUpTemp.add( 0, name ) ;
			}
		}
		else
		{
			ret = item ;
			removeSubject( subject ) ;
			if ( subject >= SUBJECT_SKINCOLOR )
			{
				mMakeUpTemp.add( 0, name ) ;
			}
			else
			{
				mNoMakeUpTemp.add( 0, name ) ;
			}
		}
		return ret ;
	}
	
	public void removeMakeUpItemByName( String name )
	{
		if ( isTemp )
		{
			mMakeUpTemp.remove( name ) ;
		}
	}
	
	public void removeNoMakeUpItemByName( String name )
	{
		if ( isTemp )
		{
			mNoMakeUpTemp.remove( name ) ;	
		}
	}
	
	public int getAllTempCount()
	{
		return mMakeUpTemp.size() + mNoMakeUpTemp.size() ;
	}
	
	public String getTempItemFromAll( int index )
	{
		String ret = null ;
		if ( index < mMakeUpTemp.size() )
		{
			ret = mMakeUpTemp.get( index ) ;
		}
		else
		{
			ret = mNoMakeUpTemp.get( index - mMakeUpTemp.size() ) ;
		}
		return ret ;
	}
	
	public void removeAll()
	{
		if ( isTemp )
		{
			mMakeUpTemp.clear() ;
			mNoMakeUpTemp.clear() ;
		}
	}
	
	public boolean containsHair( String hair_name )
	{
		return mNoMakeUpItems.contains( hair_name ) ;
	}
	
	public boolean containsItem( String name )
	{
		return ( mMakeUpItems.contains( name ) | mNoMakeUpItems.contains( name ) ) ;
	}
	
	public void initialize()
	{
		mNoMakeUpItems.clear() ;
		mNoMakeUpItems.add( "items/default/top-1.png" ) ;
		mNoMakeUpItems.add( "items/default/shoes-1-1.png" ) ;
		mNoMakeUpItems.add( "items/default/bottom-2-1.png" ) ;
		mNoMakeUpItems.add( "items/shop3/hair-25.png_color1" ) ;
		writeToFile() ;
	}
	
	@SuppressWarnings("unchecked")
	public void createTemp()
	{
		isTemp = true ;
		mMakeUpTemp.clear() ;
		mNoMakeUpTemp.clear() ;
		
		mMakeUpTemp = null ;
		mMakeUpTemp = (ArrayList<String>)mMakeUpItems.clone() ;
		mNoMakeUpTemp = null ;
		mNoMakeUpTemp = (ArrayList<String>)mNoMakeUpItems.clone() ;
	}
	
	public void clearTemp()
	{
		mMakeUpTemp.clear() ;
		mNoMakeUpTemp.clear() ;
		isTemp = false ;
	}
	
	@SuppressWarnings("unchecked")
	public void save()
	{
		mMakeUpItems.clear() ;
		mMakeUpItems = null ;
		mMakeUpItems = (ArrayList<String>)mMakeUpTemp.clone() ;
		
		mNoMakeUpItems.clear() ;
		mNoMakeUpItems = null ;
		mNoMakeUpItems = (ArrayList<String>)mNoMakeUpTemp.clone() ;
		
		writeToFile() ;
	}
	
	public void clear()
	{
		mMakeUpItems.clear() ;
		mNoMakeUpItems.clear() ;
		mMakeUpTemp.clear() ;
		mNoMakeUpTemp.clear() ;
		
		mMakeUpItems = null ;
		mNoMakeUpItems = null ;
		mMakeUpTemp = null ;
		mNoMakeUpTemp = null ;
	}
}
