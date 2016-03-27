package com.bareillyinfoapp.category;

import java.util.ArrayList;

import com.bareillyinfoapp.category.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


public class Advertisement_List extends Activity
{
		private Activity activity;
		ListView listViewItems;
		int category_Id =0;
		LazyAdapterAdvertisement adapter;
		ArrayList<Advertisement> userList = null;
		ArrayList<Advertisement> cloneList = null;
		EditText inputSearch;
		// for menu

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater menuInflater = getMenuInflater();
			menuInflater.inflate(R.menu.category_menu, menu);

			// Associate searchable configuration with the SearchView
			SearchManager searchManager =
					(SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView =
					(SearchView) menu.findItem(R.id.action_search1).getActionView();
			searchView.setSearchableInfo(
					searchManager.getSearchableInfo(getComponentName()));
			//***setOnQueryTextListener***
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					Log.i("heehe", "onQueryTextSubmit: " + query);
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub

					makeFilter2(newText);
					return false;
				}
			});

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

	        category_Id = getIntent().getExtras().getInt("category_id");
	        activity = this;

			if (Connectivity.isNetworkAvailable(activity)) {
				// do something
				// Set the layout
				setContentView(R.layout.advertise_list);
				getAdvertisement();

			} else {

				getNotNetworkView();
			}
		}

		public void getNotNetworkView() {
			// Set the layout
			setContentView(R.layout.no_network_page);

			RelativeLayout noNetworkView = (RelativeLayout) findViewById(R.id.rlNoNework);
			Button retryNetworkButton = (Button) noNetworkView.findViewById(R.id.retryNetConnectionButton);



			retryNetworkButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (Connectivity.isNetworkAvailable(activity)) {
						setContentView(R.layout.category_list);
						getAdvertisement();
					} else {
						Toast.makeText(activity, "No Internet Connection",
								Toast.LENGTH_LONG).show();
					}
				}
			});
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
	        	userList = (ArrayList<Advertisement>) result;
				cloneList = new ArrayList<Advertisement> ();

	        	if (userList.size() > 0)
	        	{
	        	adapter = new LazyAdapterAdvertisement(activity, userList);
	    		listViewItems = (android.widget.ListView) findViewById(R.id.listView1);		    		 
	    		listViewItems.setAdapter(adapter);

//					inputSearch = (EditText) findViewById(R.id.inputSearch2);
//
//					/**
//					 * Enabling Search Filter
//					 * */
//					inputSearch.addTextChangedListener(new TextWatcher() {
//
//						@Override
//						public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//							// When user changed the Text
//							Log.i("blah blah", "onTextChanged: text:" + cs);
//							makeFilter(cs);
//						}
//
//						@Override
//						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//													  int arg3) {
//							// TODO Auto-generated method stub
//
//						}
//
//						@Override
//						public void afterTextChanged(Editable arg0) {
//							// TODO Auto-generated method stub
//						}
//					});
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

			// Filter Class
//			public void makeFilter(CharSequence charText) {
//
//				if (charText.length() == 0) {
//					adapter = new LazyAdapterAdvertisement(activity, userList);
//					listViewItems.setAdapter(adapter);
//				} else {
//					charText = charText.toString().toLowerCase();
//					cloneList.clear();
//					for (int i = 0; i < userList.size(); i++) {
//						if (userList.get(i).madvertiseName.trim().toLowerCase().contains(charText)) {
//							cloneList.add(userList.get(i));
//						}
//					}
//					adapter = new LazyAdapterAdvertisement(activity, cloneList);
//					listViewItems.setAdapter(adapter);
//				}
//			}
		}

	public void makeFilter2(CharSequence charText) {

				if (charText.length() == 0) {
					adapter = new LazyAdapterAdvertisement(activity, userList);
					listViewItems.setAdapter(adapter);
				} else {
					charText = charText.toString().toLowerCase();
					cloneList.clear();
					for (int i = 0; i < userList.size(); i++) {
						if (userList.get(i).madvertiseName.trim().toLowerCase().contains(charText)
								|| userList.get(i).mAddress.trim().toLowerCase().contains(charText)) {
							cloneList.add(userList.get(i));
						}
					}
					adapter = new LazyAdapterAdvertisement(activity, cloneList);
					listViewItems.setAdapter(adapter);
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
