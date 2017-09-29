package com.miinu.FabLife.Engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.miinu.FabLife.R;
import com.miinu.FabLife.Application.FabLifeApplication;
import com.miinu.FabLife.Engine.ItemPriceTable.PriceItem;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class ShopManager 
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
	AssetManager		mMgr				= null ;
	String 				mName 				= null ;
	int					mSubject			= SUBJECT_ALL ;
	
	ArrayList<PriceItem>	mAllList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mTopList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mBottomList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mDressList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mOuterwearList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mShoesList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mBagList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mJewelList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mHairList		= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mSkinColorList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mBlushonList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mEyeShadowList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mMascaraList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mEyeBrowList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mEyeColorList	= new ArrayList<PriceItem>() ;
	ArrayList<PriceItem>	mLipsList		= new ArrayList<PriceItem>() ;
	
	BitmapFactory.Options options = new BitmapFactory.Options() ;
	
	public ShopManager( Context context, String shop_name )
	{
		mCtx = context ;
		mMgr = mCtx.getAssets() ;
		mName = shop_name ;
		
		options.inPurgeable = true ;
		
		ItemPriceTable mPriceTable = new ItemPriceTable( context, shop_name ) ;
		
		setItemLists( mPriceTable ) ;
	}
	
	void setItemLists( ItemPriceTable mPriceTable )
	{
		PriceItem table[] = mPriceTable.item_table ;
		int length = table.length ;
		
		for ( int i = 0 ; i < length ; i++ )
		{
			String name = table[ i ].name ;
			
			if ( !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mAllList.add( table[ i ] ) ;
			}
			
			if ( name.contains( "top" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mTopList.add( table[ i ] ) ;
			}
			else if ( name.contains( "bottom" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mBottomList.add( table[ i ] ) ;
			}
			else if ( name.contains( "dress" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mDressList.add( table[ i ] ) ;
			}
			else if ( name.contains( "outerwear" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mOuterwearList.add( table[ i ] ) ;
			}
			else if ( name.contains( "shoes" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mShoesList.add( table[ i ] ) ;
			}
			else if ( name.contains( "bag" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mBagList.add( table[ i ] ) ;
			}
			else if ( name.contains( "jewel" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mJewelList.add( table[ i ] ) ;
			}
			else if ( ( name.contains( "hair" ) || name.contains( "funky" ) ) && FabLifeApplication.getHairManager().contains( name ) )
			{
				mHairList.add( table[ i ] ) ;
			}
			else if ( name.contains( "skincolor" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mSkinColorList.add( table[ i ] ) ;
			}
			else if ( name.contains( "blushon" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mBlushonList.add( table[ i ] ) ;
			}
			else if ( name.contains( "eyeshadow" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mEyeShadowList.add( table[ i ] ) ;
			}
			else if ( name.contains( "mascara" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mMascaraList.add( table[ i ] ) ;
			}
			else if ( name.contains( "eyebrow" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mEyeBrowList.add( table[ i ] ) ;
			}
			else if ( name.contains( "eyecolor" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mEyeColorList.add( table[ i ] ) ;
			}
			else if ( name.contains( "lips" ) && !FabLifeApplication.getBuiedManager().contains( name ) && !FabLifeApplication.getAppliedManager().containsItem( name ) )
			{
				mLipsList.add( table[ i ] ) ;
			}
		}
	}
	
	public int getCount()
	{
		int count = 0 ;
		switch( mSubject )
		{
			case SUBJECT_ALL :
				count = mAllList.size() ;
				break ;
			case SUBJECT_TOP :
				count = mTopList.size() ;
				break ;
			case SUBJECT_BOTTOM :
				count = mBottomList.size() ;
				break ;
			case SUBJECT_DRESS :
				count = mDressList.size() ;
				break ;
			case SUBJECT_OUTERWEAR :
				count = mOuterwearList.size() ;
				break ;
			case SUBJECT_SHOES :
				count = mShoesList.size() ;
				break ;
			case SUBJECT_BAG :
				count = mBagList.size() ;
				break ;
			case SUBJECT_JEWEL :
				count = mJewelList.size() ;
				break ;
			case SUBJECT_HAIR :
				count = mHairList.size() ;
				break ;
			case SUBJECT_SKINCOLOR :
				count = mSkinColorList.size() ;
				break ;
			case SUBJECT_BLUSHON :
				count = mBlushonList.size() ;
				break ;
			case SUBJECT_EYESHADOW :
				count = mEyeShadowList.size() ;
				break ;
			case SUBJECT_MASCARA :
				count = mMascaraList.size() ;
				break ;
			case SUBJECT_EYEBROW :
				count = mEyeBrowList.size() ;
				break ;
			case SUBJECT_EYECOLOR :
				count = mEyeColorList.size() ;
				break ;
			case SUBJECT_LIPS :
				count = mLipsList.size() ;
				break ;
		}
		return count ;
	}
	
	public void	buy( int index ) throws IndexOutOfBoundsException
	{
		String name = getItemNameByIndex( index ) ;
		if ( name.contains( "hair" ) || name.contains( "funky" ) )
		{
			FabLifeApplication.getBuiedManager().addItem( name + "_color" + FabLifeApplication.getHairManager().getHairColor( name ) ) ;
			for ( int j = 1 ; j <= 8 ; j++ )
			{
				String hair_compare = name ;
				hair_compare += ( "_color" + j ) ;
				if ( !FabLifeApplication.getBuiedManager().contains( hair_compare ) && !FabLifeApplication.getAppliedManager().containsHair( hair_compare ) )
				{
					FabLifeApplication.getHairManager().setHairColor( name, j ) ;
					return ;
				}
			}
			FabLifeApplication.getHairManager().remove( name ) ;
			removeItemByName( mAllList, name ) ;
			removeItemByName( mHairList, name ) ;
		}
		else
		{
			FabLifeApplication.getBuiedManager().addItem( name ) ;
			removeItemByName( mAllList, name ) ;
			
			if ( name.contains( "top" ) )
			{
				removeItemByName( mTopList, name ) ;
			}
			else if ( name.contains( "bottom" ) )
			{
				removeItemByName( mBottomList, name ) ;
			}
			else if ( name.contains( "dress" ) )
			{
				removeItemByName( mDressList, name ) ;
			}
			else if ( name.contains( "outerwear" ) )
			{
				removeItemByName( mOuterwearList, name ) ;
			}
			else if ( name.contains( "shoes" ) )
			{
				removeItemByName( mShoesList, name ) ;
			}
			else if ( name.contains( "bag" ) )
			{
				removeItemByName( mBagList, name ) ;
			}
			else if ( name.contains( "jewel" ) )
			{
				removeItemByName( mJewelList, name ) ;
			}
			else if ( name.contains( "skincolor" ) )
			{
				removeItemByName( mSkinColorList, name ) ;
			}
			else if ( name.contains( "blushon" ) )
			{
				removeItemByName( mBlushonList, name ) ;
			}
			else if ( name.contains( "eyeshadow" ) )
			{
				removeItemByName( mEyeShadowList, name ) ;
			}
			else if ( name.contains( "mascara" ) )
			{
				removeItemByName( mMascaraList, name ) ;
			}
			else if ( name.contains( "eyebrow" ) )
			{
				removeItemByName( mEyeBrowList, name ) ;
			}
			else if ( name.contains( "eyecolor" ) )
			{
				removeItemByName( mEyeColorList, name ) ;
			}
			else if ( name.contains( "lips" ) )
			{
				removeItemByName( mLipsList, name ) ;
			}
		}
	}
	
	void removeItemByName( ArrayList<PriceItem> mTable, String name ) throws IndexOutOfBoundsException
	{
		for ( int i = 0 ; i < mTable.size() ; i++ )
		{
			if ( mTable.get( i ).name.equalsIgnoreCase( name ) )
				mTable.remove( i ) ;
		}
	}
	
	void getColoredHair( Bitmap hair, int out_color )
	{
		if ( out_color == 1 )
			return ;
		
    	float r_scale = 1.0f ;
    	float g_scale = 1.0f ;
    	float b_scale = 1.0f ;
    	
    	int source_color = mCtx.getResources().getColor( R.color.haircolor01 ) ;
    	int hair_color = mCtx.getResources().getColor( R.color.haircolor01 + ( out_color - 1 ) ) ;
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
	
	public String getItemNameByIndex( int index ) throws IndexOutOfBoundsException
	{
		PriceItem item = null ;
		switch( mSubject )
		{
			case SUBJECT_ALL :
				item = mAllList.get( index ) ;
				break ;
			case SUBJECT_TOP :
				item = mTopList.get( index ) ;
				break ;
			case SUBJECT_BOTTOM :
				item = mBottomList.get( index ) ;
				break ;
			case SUBJECT_DRESS :
				item = mDressList.get( index ) ;
				break ;
			case SUBJECT_OUTERWEAR :
				item = mOuterwearList.get( index ) ;
				break ;
			case SUBJECT_SHOES :
				item = mShoesList.get( index ) ;
				break ;
			case SUBJECT_BAG :
				item = mBagList.get( index ) ;
				break ;
			case SUBJECT_JEWEL :
				item = mJewelList.get( index ) ;
				break ;
			case SUBJECT_HAIR :
				item = mHairList.get( index ) ;
				break ;
			case SUBJECT_SKINCOLOR :
				item = mSkinColorList.get( index ) ;
				break ;
			case SUBJECT_BLUSHON :
				item = mBlushonList.get( index ) ;
				break ;
			case SUBJECT_EYESHADOW :
				item = mEyeShadowList.get( index ) ;
				break ;
			case SUBJECT_MASCARA :
				item = mMascaraList.get( index ) ;
				break ;
			case SUBJECT_EYEBROW :
				item = mEyeBrowList.get( index ) ;
				break ;
			case SUBJECT_EYECOLOR :
				item = mEyeColorList.get( index ) ;
				break ;
			case SUBJECT_LIPS :
				item = mLipsList.get( index ) ;
				break ;
		}
		return item.name ;
	}
	
	public Item getItemByIndex( int index ) throws IndexOutOfBoundsException
	{
		Item ret = new Item() ;
		PriceItem item = null ;
		switch( mSubject )
		{
			case SUBJECT_ALL :
				item = mAllList.get( index ) ;
				break ;
			case SUBJECT_TOP :
				item = mTopList.get( index ) ;
				break ;
			case SUBJECT_BOTTOM :
				item = mBottomList.get( index ) ;
				break ;
			case SUBJECT_DRESS :
				item = mDressList.get( index ) ;
				break ;
			case SUBJECT_OUTERWEAR :
				item = mOuterwearList.get( index ) ;
				break ;
			case SUBJECT_SHOES :
				item = mShoesList.get( index ) ;
				break ;
			case SUBJECT_BAG :
				item = mBagList.get( index ) ;
				break ;
			case SUBJECT_JEWEL :
				item = mJewelList.get( index ) ;
				break ;
			case SUBJECT_HAIR :
				item = mHairList.get( index ) ;
				break ;
			case SUBJECT_SKINCOLOR :
				item = mSkinColorList.get( index ) ;
				break ;
			case SUBJECT_BLUSHON :
				item = mBlushonList.get( index ) ;
				break ;
			case SUBJECT_EYESHADOW :
				item = mEyeShadowList.get( index ) ;
				break ;
			case SUBJECT_MASCARA :
				item = mMascaraList.get( index ) ;
				break ;
			case SUBJECT_EYEBROW :
				item = mEyeBrowList.get( index ) ;
				break ;
			case SUBJECT_EYECOLOR :
				item = mEyeColorList.get( index ) ;
				break ;
			case SUBJECT_LIPS :
				item = mLipsList.get( index ) ;
				break ;
		}
		
		ret.name = item.name ;
		try 
		{
			InputStream is = mMgr.open( item.name );
			ret.bitmap = BitmapFactory.decodeStream( is, null, options ) ;
			if ( item.name.contains( "hair" ) || item.name.contains( "funky" ) )
				ret.isHair = true ;
			is.close() ;
		} 
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
		
		if ( ret.name.contains( "hair" ) || ret.name.contains( "funky" ) )
		{
			ret.bitmap = Bitmap.createScaledBitmap( ret.bitmap, ret.bitmap.getWidth(),
					ret.bitmap.getHeight(), true ) ;
			getColoredHair( ret.bitmap, FabLifeApplication.getHairManager().getHairColor( ret.name ) ) ;
		}
		
		ret.pricetype = item.price_type ;
		ret.price = item.price ;
		ret.experience = item.experience ;
		
		return ret ;
	}
	
	public void clear() throws Exception
	{
		mAllList.clear() ; mAllList = null ;
		mTopList.clear() ; mTopList = null ;
		mBottomList.clear() ; mBottomList = null ;
		mDressList.clear() ; mDressList = null ;
		mOuterwearList.clear() ; mOuterwearList = null ;
		mShoesList.clear() ; mShoesList = null ;
		mBagList.clear() ; mBagList = null ;
		mJewelList.clear() ; mJewelList = null ;
		mHairList.clear() ; mHairList = null ;
		mSkinColorList.clear() ; mSkinColorList = null ;
		mBlushonList.clear() ; mBlushonList = null ;
		mEyeShadowList.clear() ; mEyeShadowList = null ;
		mMascaraList.clear() ; mMascaraList = null ;
		mEyeBrowList.clear() ; mEyeBrowList = null ;
		mEyeColorList.clear() ; mEyeColorList = null ;
		mLipsList.clear() ; mLipsList = null ;
	}
	
	public void setSubject( int subject )
	{
		mSubject = subject ;
	}
	
	public final int 			TYPE_CASH = 0 ;
	public final int			TYPE_COIN = 1 ;
	public class Item
	{
		public Bitmap					bitmap 		= null ;
		public String					name 		= null ;
		public int						experience	=	0 ;
		public int						pricetype 	= TYPE_CASH ;
		public int						price 		= 0 ;
		public boolean					isHair 		= false ;
	}
}
