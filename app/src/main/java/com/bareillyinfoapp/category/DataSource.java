package com.bareillyinfoapp.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DataSource 
{
	// the instance
		static DataSource instance = null;

		private static String url_get_all_category = "/index.php?/json/get_all_category/";
		private static String url_get_all_advertisement = "/index.php?/json/get_all_advertisement/";
	//	private static String url_add_user = "/index.php?/Json/adduser/";

		//for category
		private static final String TAG_CATEGORY = "category";
		private static final String TAG_CATEGORY_ID = "category_id";
		private static final String TAG_CATEGORY_NAME = "category_name";
		private static final String TAG_DESCRIPTION = "discri";
		private static final String TAG_PHOTO_NAME = "ad_photo";
		
		//for advertisement
		private static final String TAG_ADVERTISEMENT = "advertisement";
		private static final String TAG_ADVERTISEMENT_NAME = "advertisement_name";
		private static final String TAG_AD_DECRIPTION = "description";
		private static final String TAG_MOBILE_NO = "mobile_no";
		private static final String TAG_EMAIL = "email";
		private static final String TAG_ADDRESS = "address";
		
		JSONArray contacts = null;

		/** Returns the singleton instance of this class. */
		public static DataSource getInstance() 
		{
			if (instance == null) 
			{
				instance = new DataSource();
			}
			return instance;
		}

		
		public Category getUser(int categoryId) 
		{		
			ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
			JSONParser jParser = new JSONParser();
			try 
			{
				JSONObject json = jParser.getJSONFromUrl(url_get_all_category + categoryId);
				contacts = json.getJSONArray(TAG_CATEGORY);
				for (int i = 0; i < contacts.length(); i++) 				
				{
					JSONObject c = contacts.getJSONObject(i);
					String category_id= c.getString(TAG_CATEGORY_ID);
					String category_name = c.getString(TAG_CATEGORY_NAME);
					String discri= c.getString(TAG_DESCRIPTION);
					String ad_photo = c.getString(TAG_PHOTO_NAME);
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_CATEGORY_NAME, category_name);
					map.put(TAG_CATEGORY_ID, category_id);
					map.put(TAG_DESCRIPTION, discri);
					map.put(TAG_PHOTO_NAME,ad_photo);	                
		            categoryList.add(map);
				}
			} 
			catch (JSONException e) 			
			{
				e.printStackTrace();
			}
			Category u = new Category();
			u.mCategoryName = "category_name";
			
			u.mDisc = "discri";
			u.mphoto = "ad_photo";
			return u;
		}
		

		public ArrayList<Category> getAllCategory() 
		{
			ArrayList<Category> returnArray = new ArrayList<Category>();			
			JSONParser jParser = new JSONParser();
			try 
			{
				JSONObject json = jParser.getJSONFromUrl(url_get_all_category);
				contacts = json.getJSONArray(TAG_CATEGORY);
				for (int i = 0; i < contacts.length(); i++) 				
				{
					JSONObject c = contacts.getJSONObject(i);
					String category_name = c.getString(TAG_CATEGORY_NAME);
					String category_id = c.getString(TAG_CATEGORY_ID);
					String discri = c.getString(TAG_DESCRIPTION);
					String ad_photo = c.getString(TAG_PHOTO_NAME);
					
					Category u = new Category();
					u.mCategoryName = category_name;
					u.mDisc = discri;
					u.mCategory_Id = Integer.parseInt(category_id);
					u.mphoto = ad_photo;	                
			returnArray.add(u);
		}
		} 
			catch (JSONException e) 			
			{
				e.printStackTrace();
			}
	     	return returnArray;
		}
		
		
		
		
		public ArrayList<Advertisement> getAllAdvertisement(int category_Id) 
		{
			ArrayList<Advertisement> returnArray = new ArrayList<Advertisement>();			
			JSONParser jParser = new JSONParser();
			try 
			{
				JSONObject json = jParser.getJSONFromUrl(url_get_all_advertisement + category_Id);
				contacts = json.getJSONArray(TAG_ADVERTISEMENT);
				for (int i = 0; i < contacts.length(); i++) 				
				{
					JSONObject c = contacts.getJSONObject(i);
					String advertisement_name = c.getString(TAG_ADVERTISEMENT_NAME);
					String decription = c.getString(TAG_AD_DECRIPTION);
					String mobile_no = c.getString(TAG_MOBILE_NO);
					String email = c.getString(TAG_EMAIL);
					String address = c.getString(TAG_ADDRESS);
					String ad_photo = c.getString(TAG_PHOTO_NAME);					
					
					Advertisement u = new Advertisement();
					u.madvertiseName = advertisement_name;
					u.mDescription = decription;
					u.mMobileNo = mobile_no;
					u.mEmail = email;
					u.mAddress = address;
					u.mphoto = ad_photo;	                
			returnArray.add(u);
		}
		} 
			catch (JSONException e) 			
			{
				e.printStackTrace();
			}
	     	return returnArray;
		}
	}
