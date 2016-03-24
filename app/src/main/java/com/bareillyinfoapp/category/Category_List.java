package com.bareillyinfoapp.category;


import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Button;
import android.widget.Toast;

public class Category_List extends Activity {
	private Activity activity;
	ListView listViewItems;
	public static ArrayList<Category> categoryList;
	EditText inputSearch;
	LazyAdapter adapter;
	private ArrayList<Category> cloneList = null;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

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
				Log.i("heehe", "onQueryTextSubmit: "+query);
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

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;



		if (Connectivity.isNetworkAvailable(activity)) {
			// do something
			// Set the layout
			setContentView(R.layout.category_list);
			getCategory();
		} else {

			getNotNetworkView();
		}
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}


	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Category_List Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.bareillyinfoapp.category/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Category_List Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.bareillyinfoapp.category/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
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
					getCategory();
				} else {
					Toast.makeText(activity, "No Internet Connection",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	class processGetCategory extends AsyncTask<Integer, Integer, ArrayList<Category>> {
		private ProgressDialog mDialog;

		protected void onPreExecute() {
			mDialog = new ProgressDialog(Category_List.this);
			mDialog.setMessage("receiving data..");
			mDialog.setIndeterminate(false);
			mDialog.setCancelable(true);
			mDialog.show();
		}


		protected void onPostExecute(ArrayList<Category> result) {

			try {
				categoryList = (ArrayList<Category>) result;
				cloneList = new ArrayList<Category> ();

				adapter = new LazyAdapter(activity, categoryList);
				listViewItems = (ListView) findViewById(R.id.listView1);
				listViewItems.setAdapter(adapter);
//				inputSearch = (EditText) findViewById(R.id.inputSearch1);

				/**
				 * Enabling Search Filter
				 * */
//				inputSearch.addTextChangedListener(new TextWatcher() {
//
//					@Override
//					public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//						// When user changed the Text
//						Log.i("blah blah", "onTextChanged: text:"+cs);
////						makeFilter(cs);
//					}
//
//					@Override
//					public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//												  int arg3) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void afterTextChanged(Editable arg0) {
//						// TODO Auto-generated method stub
//					}
//				});
				listViewItems.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {

						// ListView Clicked item index
						int itemPosition = position;

						// ListView Clicked item value
						Category itemValue = (Category) categoryList.get(position);
						Intent advertisementIntent = new Intent(getApplicationContext(), Advertisement_List.class);

						advertisementIntent.putExtra("category_id", itemValue.mCategory_Id);

						startActivity(advertisementIntent);
					}
				});

			} catch (Exception ex) {

			}

			mDialog.dismiss();
		}


		@Override
		protected ArrayList<Category> doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			DataSource dS = new DataSource();
			ArrayList<Category> ObjectItemData = dS.getAllCategory();
			return ObjectItemData;
		}
		// Filter Class
//		public void makeFilter(CharSequence charText) {
//
//			if (charText.length() == 0) {
//				adapter = new LazyAdapter(activity, categoryList);
//				listViewItems.setAdapter(adapter);
//			}
//			else
//			{
//				charText = charText.toString().toLowerCase();
//				cloneList.clear();
//				for(int i = 0; i < categoryList.size(); i++)
//				{
//					if (categoryList.get(i).mCategoryName.trim().toLowerCase().contains(charText))
//					{
//						cloneList.add(categoryList.get(i));
//					}
//				}
//				adapter = new LazyAdapter(activity, cloneList);
//				listViewItems.setAdapter(adapter);
//			}
//		}

	}

	// Filter Class
	public void makeFilter2(CharSequence charText) {

		if (charText.length() == 0) {
			adapter = new LazyAdapter(activity, categoryList);
			listViewItems.setAdapter(adapter);

			listViewItems.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {

					// ListView Clicked item index
					int itemPosition = position;

					// ListView Clicked item value
					Category itemValue = (Category) categoryList.get(position);
					Intent advertisementIntent = new Intent(getApplicationContext(), Advertisement_List.class);

					advertisementIntent.putExtra("category_id", itemValue.mCategory_Id);

					startActivity(advertisementIntent);
				}
			});
		}
		else
		{
			charText = charText.toString().toLowerCase();
			cloneList.clear();
			for(int i = 0; i < categoryList.size(); i++)
			{
				if (categoryList.get(i).mCategoryName.trim().toLowerCase().contains(charText))
				{
					cloneList.add(categoryList.get(i));
				}
			}
			adapter = new LazyAdapter(activity, cloneList);
			listViewItems.setAdapter(adapter);

			listViewItems.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {

					// ListView Clicked item index
					int itemPosition = position;

					// ListView Clicked item value
					Category itemValue = (Category) cloneList.get(position);
					Intent advertisementIntent = new Intent(getApplicationContext(), Advertisement_List.class);

					advertisementIntent.putExtra("category_id", itemValue.mCategory_Id);

					startActivity(advertisementIntent);
				}
			});
		}
	}


	public void getCategory() {
		try {
			new processGetCategory().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
