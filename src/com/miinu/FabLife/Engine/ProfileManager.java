package com.miinu.FabLife.Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.os.Environment;

public class ProfileManager 
{	
	Context						mCtx				= null ;
	boolean						mbTutorialFinished	= false ;
	int							mnLevel				= 1 ;
	int							mnExperience		= 150 ;
	int							mnEnergy			= 45 ;
	int							mnCoin				= 10000 ;
	int							mnCash				= 10000 ;
	boolean						mbIsVipUser			= false ;
	int							mnTask				= 1 ;
	String						msTaskCompleteDate	= "000000" ;
	boolean						mbPlayMusic			= true ;
	boolean						mbPlayEffect		= true ;
	boolean						mbFirstBotique		= true ;
	boolean						mbFirstAddStore		= true ;
	boolean						mbFirstOpen			= true ;
	boolean						mbFirstRestock		= true ;
	boolean						mbFirstCollect		= true ;
	int							mnFlirts			= 0 ;
	int							mnEarning			= 0 ;
	long						mnFinishTime		= 0 ;
	int							mnCurrentTimerTime	= 0 ;
	long						mnBotiqueFinishTime	= 0 ;
	
	public ProfileManager( Context context )
	{
		mCtx = context ;
		readFromFile() ;
	}
	
	void readFromFile()
	{
		try
    	{
    		InputStreamReader isr = new InputStreamReader( mCtx.openFileInput( "game_profile.ini" ) ) ;
            BufferedReader br = new BufferedReader( isr ) ;
            String str ;
            int index = 0 ;
            while( ( str = br.readLine() ) != null )
            {
            	switch( index )
            	{
            		case 0 :
            		{
            			int tutorialfinished = Integer.parseInt( str ) ;
            			if ( tutorialfinished == 0 )
            			{
            				mbTutorialFinished = false ;
            			}
            			else
            			{
            				mbTutorialFinished	= true ;
            			}
            			break ;
            		}
            		case 1 :
            		{
            			mnLevel = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 2 :
            		{
            			mnExperience = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 3 :
            		{
            			mnEnergy = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 4 :
            		{
            			mnCoin = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 5 :
            		{
            			mnCash = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 6 :
            		{
            			int isVipUser = Integer.parseInt( str ) ;
            			if ( isVipUser == 0 )
            			{
            				mbIsVipUser = false ;
            			}
            			else
            			{
            				mbIsVipUser	= true ;
            			}
            			break ;
            		}
            		case 7 :
            		{
            			mnTask = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 8 :
            		{
            			msTaskCompleteDate = str ;
            			break ;
            		}
            		case 9 :
            		{
            			int playMusic = Integer.parseInt( str ) ;
            			if ( playMusic == 0 )
            			{
            				mbPlayMusic = false ;
            			}
            			else
            			{
            				mbPlayMusic	= true ;
            			}
            			break ;
            		}
            		case 10 :
            		{
            			int playEffect = Integer.parseInt( str ) ;
            			if ( playEffect == 0 )
            			{
            				mbPlayEffect = false ;
            			}
            			else
            			{
            				mbPlayEffect = true ;
            			}
            			break ;
            		}
            		case 11 :
            		{
            			int firstBotique = Integer.parseInt( str ) ;
            			if ( firstBotique == 0 )
            			{
            				mbFirstBotique = false ;
            			}
            			else
            			{
            				mbFirstBotique = true ;
            			}
            			break ;
            		}
            		case 12 :
            		{
            			int firstAddStore = Integer.parseInt( str ) ;
            			if ( firstAddStore == 0 )
            			{
            				mbFirstAddStore = false ;
            			}
            			else
            			{
            				mbFirstAddStore = true ;
            			}
            			break ;
            		}
            		case 13 :
            		{
            			int firstOpen = Integer.parseInt( str ) ;
            			if ( firstOpen == 0 )
            			{
            				mbFirstOpen = false ;
            			}
            			else
            			{
            				mbFirstOpen = true ;
            			}
            			break ;
            		}
            		case 14 :
            		{
            			int firstRestock = Integer.parseInt( str ) ;
            			if ( firstRestock == 0 )
            			{
            				mbFirstRestock = false ;
            			}
            			else
            			{
            				mbFirstRestock = true ;
            			}
            			break ;
            		}
            		case 15 :
            		{
            			int firstCollect = Integer.parseInt( str ) ;
            			if ( firstCollect == 0 )
            			{
            				mbFirstCollect = false ;
            			}
            			else
            			{
            				mbFirstCollect = true ;
            			}
            			break ;
            		}
            		case 16 :
            		{
            			mnFlirts = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 17 :
            		{
            			mnEarning = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 18 :
            		{
            			mnFinishTime = Long.parseLong( str ) ;
            			break ;
            		}
            		case 19 :
            		{
            			mnCurrentTimerTime = Integer.parseInt( str ) ;
            			break ;
            		}
            		case 20 :
            		{
            			mnBotiqueFinishTime = Long.parseLong( str ) ;
            			break ;
            		}
            	}
            	index++ ;
            }
            isr.close();
    	}
    	catch ( Exception e ) { e.printStackTrace(); }
    	try
    	{
    		String in_path = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.miinu.FabLife/profile.ini" ;
    		File in = new File( in_path ) ;
			if ( in.exists() )
			{
				FileInputStream is = new FileInputStream( in ) ;
				InputStreamReader isr = new InputStreamReader( is ) ;
				BufferedReader br = new BufferedReader( isr ) ;
				String str ;
				if ( ( str = br.readLine() ) != null )
				{
					int isVipUser = Integer.parseInt( str ) ;
        			if ( isVipUser == 0 )
        			{
        				mbIsVipUser = false ;
        			}
        			else
        			{
        				mbIsVipUser	= true ;
        			}
				}
				br.close() ;
	            isr.close() ;
	            is.close() ;
			}
    	}
    	catch( Exception e )
    	{
    		e.printStackTrace() ;
    	}
	}
	
	public void writeToFile()
	{
		try
    	{
    		OutputStreamWriter osw = new OutputStreamWriter( mCtx.openFileOutput( "game_profile.ini", Context.MODE_PRIVATE ) ) ;
    		for( int i = 0 ; i < 21 ; i++ )
    		{
    			String str = "" ;
    			switch( i )
				{
					case 0 :
					{
						if ( mbTutorialFinished )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 1 :
					{
						str += mnLevel ;
						break ;
					}
					case 2 :
					{
						str += mnExperience ;
						break ;
					}
					case 3 :
					{
						str += mnEnergy ;
						break ;
					}
					case 4 :
					{
						str += mnCoin ;
						break ;
					}
					case 5 :
					{
						str += mnCash ;
						break ;
					}
					case 6 :
					{
						if ( mbIsVipUser )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 7 :
					{
						str += mnTask ;
						break ;
					}
					case 8 :
					{
						str = msTaskCompleteDate ;
						break ;
					}
					case 9 :
					{
						if ( mbPlayMusic )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 10 :
					{
						if ( mbPlayEffect )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 11 :
					{
						if ( mbFirstBotique )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 12 :
					{
						if ( mbFirstAddStore )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 13 :
					{
						if ( mbFirstOpen )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 14 :
					{
						if ( mbFirstRestock )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 15 :
					{
						if ( mbFirstCollect )
						{
							str += 1 ;
						}
						else
						{
							str += 0 ;
						}
						break ;
					}
					case 16 :
					{
						str += mnLevel ;
						break ;
					}
					case 17 :
					{
						str += mnEarning ;
						break ;
					}
					case 18 :
					{
						str += mnFinishTime ;
						break ;
					}
					case 19 :
					{
						str += mnCurrentTimerTime ;
						break ;
					}
					case 20 :
					{
						str += mnBotiqueFinishTime ;
						break ;
					}
				}
    			osw.write( str ) ;
    			osw.write( "\n" ) ;
    		}
            osw.close();
        }
    	catch ( Exception e ) { e.printStackTrace(); }
    	if ( Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED ) )
    	{
    		try
    		{
	    		String ext_dir = Environment.getExternalStorageDirectory().getPath() ;
	    		ext_dir += "/Android/data/com.miinu.FabLife/" ;
	    		File out_dir = new File( ext_dir ) ;
	    		if ( !out_dir.isDirectory() )
	    		{
	    			out_dir.mkdirs() ;
	    		}
	    		String out_path = ext_dir + "profile.ini" ;
	    		File out = new File( out_path ) ;
				if ( !out.exists() )
				{
					out.createNewFile() ;
				}
				FileOutputStream os = new FileOutputStream( out ) ;
	            OutputStreamWriter osw = new OutputStreamWriter( os ) ;
	            String str = "" ;
	            if ( mbIsVipUser )
				{
					str += 1 ;
				}
				else
				{
					str += 0 ;
				}
        		osw.write( str ) ;
        		osw.flush() ;
        		os.flush() ;
        		osw.close() ;
        		os.close() ;
    		}
    		catch( Exception e )
    		{
    			e.printStackTrace() ;
    		}
    	}
	}
	
	public boolean getTutorialFinished()
	{
		return mbTutorialFinished ;
	}
	
	public void setVIPMember()
	{
		mbIsVipUser = true ;
	}
	
	public boolean isVIPMember()
	{
		return mbIsVipUser ;
	}
	
	public void setMusic( boolean isMusic )
	{
		mbPlayMusic = isMusic ;
	}
	
	public boolean getMusic()
	{
		return mbPlayMusic ;
	}
	
	public void setEffect( boolean isEffect )
	{
		mbPlayEffect = isEffect ;
	}
	
	public boolean getEffect()
	{
		return mbPlayEffect ;
	}
	
	public void setLevel( int level )
	{
		mnLevel = level ;
	}
	
	public int getLevel()
	{
		return mnLevel ;
	}
	
	public void setExperience( int xp )
	{
		mnExperience = xp ;
	}
	
	public int getExperience()
	{
		return mnExperience ;
	}
	
	public void setEnergy( int energy )
	{
		mnEnergy = energy ;
	}
	
	public int getEnergy()
	{
		return mnEnergy ;
	}
	
	public void setCoin( int coin )
	{
		mnCoin = coin ;
	}
	
	public int getCoin()
	{
		return mnCoin ;
	}
	
	public void setCash( int cash )
	{
		mnCash = cash ;
	}
	
	public int getCash()
	{
		return mnCash ;
	}
	
	public static int getLevelXp( int level )
	{
		if ( level < 1 )
			return 0 ;
		int ret = 150 + 100 * ( level - 1 ) ;
		return ret ;
	}
	
	public int getTask()
	{
		return mnTask ;
	}
	
	public boolean isPastDate()
	{
		try 
		{
			Date date = new SimpleDateFormat( "yyMMdd" ).parse( msTaskCompleteDate ) ;
			Date today = new Date() ;
			
			if ( ( today.getYear() > date.getYear() ) ||
				( ( today.getYear() == date.getYear() ) && ( today.getMonth() > date.getMonth() ) ) ||
				( ( today.getYear() == date.getYear() ) && ( today.getMonth() == date.getMonth() ) && ( today.getDate() > date.getDate() ) ) )
			{
				return true ;
			}
		} 
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		return false ;
	}
	
	public void setCompleteTaskDate()
	{
		Date today = new Date() ;
		msTaskCompleteDate = new SimpleDateFormat( "yyMMdd" ).format( today ) ;
		if ( mnTask == 70 )
			mnTask = 1 ;
		else
			mnTask++ ;
	}
	
	public void setTutorialFinished()
	{
		mbTutorialFinished 	= true ;
		mnLevel				= 1 ;
		mnExperience		= 150 ;
		if ( mbIsVipUser )
			mnEnergy		= 55 ;
		else
			mnEnergy		= 45 ;
		mnCoin				= 10000 ;
		mnCash				= 10000 ;
//		mbIsVipUser			= false ;
		mnTask				= 1 ;
		msTaskCompleteDate	= "000000" ;
		mbPlayMusic			= true ;
		mbPlayEffect		= true ;
		mbFirstBotique		= true ;
		mbFirstAddStore		= true ;
		mbFirstOpen			= true ;
		mbFirstRestock		= true ;
		mbFirstCollect		= true ;
		mnFlirts			= 0 ;
		mnEarning			= 0 ;
		mnFinishTime		= 0 ;
		mnCurrentTimerTime	= 0 ;
		mnBotiqueFinishTime	= 0 ;
		writeToFile() ;
	}
	
	public boolean isFirstBotique()
	{
		return mbFirstBotique ;
	}
	
	public void setFirstBotique()
	{
		mbFirstBotique = false ;
	}
	
	public boolean isFirstAddStore()
	{
		return mbFirstAddStore ;
	}
	
	public void setFirstAddStore()
	{
		mbFirstAddStore = false ;
	}

	public boolean isFirstOpen()
	{
		return mbFirstOpen ;
	}
	
	public void setFirstOpen()
	{
		mbFirstOpen = false ;
	}

	public boolean isFirstRestock()
	{
		return mbFirstRestock ;
	}
	
	public void setFirstRestock()
	{
		mbFirstRestock = false ;
	}
	
	public boolean isFirstCollect()
	{
		return mbFirstCollect ;
	}
	
	public void setFirstCollect()
	{
		mbFirstCollect = false ;
	}
	
	public void addFlirts()
	{
		mnFlirts++ ;
	}
	
	public int getFlirts()
	{
		return mnFlirts ;
	}
	
	public void setEarning( int n )
	{
		mnEarning = n ;
	}
	
	public int getEarning()
	{
		return mnEarning ;
	}
	
	public void setFinishTime( long time )
	{
		mnFinishTime = time ;
	}
	
	public long getFinishTime()
	{
		return mnFinishTime ;
	}
	
	public void setCurrentTimerTime( int time )
	{
		mnCurrentTimerTime = time ;
	}
	
	public int getCurrentTimerTime()
	{
		return mnCurrentTimerTime ;
	}
	
	public void setBotiqueFinishTime( long time )
	{
		mnBotiqueFinishTime = time ;
	}
	
	public long getBotiqueFinishTime()
	{
		return mnBotiqueFinishTime ;
	}
}
