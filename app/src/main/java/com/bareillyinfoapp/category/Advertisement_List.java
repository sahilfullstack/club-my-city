package com.bareillyinfoapp.category;

import java.util.ArrayList;

import com.bareillyinfoapp.category.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;



public class Advertisement_List extends Activity
{

		private View view;
		private Activity activity;
		AlertDialog alertDialogStores;
		ListView listViewItems;
		int category_Id =0;
		// for menu
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater menuInflater = getMenuInflater();
			menuInflater.inflate(R.menu.category_menu, menu);
			return true;
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case R.id.about_us: {
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				String str = getString(R.string.menu_aboutus_detail);
				final ScrollView s_view = new ScrollView(getApplicationContext());
				final TextView t_view = new TextView(getApplicationContext());
				t_view.setBackgroundColor(Color.BLACK);
				t_view.setTextColor(Color.GREEN);
				t_view.setPadding(10, 10, 10, 10);
				t_view.setText(str);
				t_view.setTextSize(14);
				s_view.addView(t_view);
				alertDialog.setTitle("Smart City Information");
				alertDialog.setView(s_view);
				alertDialog.show();

			}
			return true;
			case R.id.contact_us: {
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				String str = getString(R.string.menu_contact_us);
				final ScrollView s_view = new ScrollView(getApplicationContext());
				final TextView t_view = new TextView(getApplicationContext());
				t_view.setBackgroundColor(Color.BLACK);
				t_view.setTextColor(Color.GREEN);
				t_view.setPadding(10, 10, 10, 10);
				t_view.setText(str);
				t_view.setTextSize(14);
				s_view.addView(t_view);
				alertDialog.setTitle("Smart City Information");
				alertDialog.setView(s_view);
				alertDialog.show();
			}
			return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
		
		/** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.advertise_list);  
	        category_Id = getIntent().getExtras().getInt("category_id");
	        activity = this;
	        getAdvertisement();			
		}    	
	    
	    class processGetAdvertise extends AsyncTask<Integer, Integer, ArrayList<Advertisement>> {
	    	ProgressDialog nDialog;
	        protected void onPreExecute() 
	        {
	        	nDialog = new ProgressDialog(Advertisement_List.this);
				nDialog.setMessage("Receiving data...");
				nDialog.setIndeterminate(false);
				nDialog.setCancelable(true);
				nDialog.show();

	        }

	        
	        protected void onPostExecute(ArrayList<Advertisement> result) 
	        {		        	
	        	ArrayList<Advertisement> userList = (ArrayList<Advertisement>) result;	
	        	if (userList.size() > 0)
	        	{
	        	LazyAdapterAdvertisement adapter = new LazyAdapterAdvertisement(activity, userList);		    		 
	    		listViewItems = (android.widget.ListView) findViewById(R.id.listView1);		    		 
	    		listViewItems.setAdapter(adapter);
	        	}
	    		nDialog.dismiss();
	    		
	        }
	        
    
	    	@Override
	    	protected ArrayList<Advertisement> doInBackground(Integer... arg0) 
	    	{
	    		// TODO Auto-generated method stub
	    		DataSource dS = new DataSource();
	    		ArrayList<Advertisement> ObjectItemData = dS.getAllAdvertisement(category_Id);		    		
	    		return ObjectItemData;		    		
	    	}		    	
	    	}	

	    	public void getAdvertisement()
	    	{
	    		try
	    		{
	    			new processGetAdvertise().execute();
	    	     }
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}
	    		}	
}
