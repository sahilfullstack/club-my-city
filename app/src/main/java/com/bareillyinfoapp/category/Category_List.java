package com.bareillyinfoapp.category;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.bareillyinfoapp.category.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ProgressBar;

public class Category_List extends Activity {
	private Activity activity;
	AlertDialog alertDialogStores;
	ListView listViewItems;
	ArrayList<Category> categoryList;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list);
		activity = this;
		getCategory();	
	}

	class processGetCategory extends AsyncTask<Integer, Integer, ArrayList<Category>> {
		private ProgressDialog mDialog;
        protected void onPreExecute() 
        {
        	mDialog = new ProgressDialog(Category_List.this);
			mDialog.setMessage("receiving data..");
			mDialog.setIndeterminate(false);
			mDialog.setCancelable(true);
			mDialog.show();
        }

        
        protected void onPostExecute(ArrayList<Category> result) {

			try{
				categoryList = (ArrayList<Category>) result;
				LazyAdapter adapter = new LazyAdapter(activity, categoryList);
				listViewItems = (android.widget.ListView) findViewById(R.id.listView1);
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

						advertisementIntent.putExtra("category_id",	itemValue.mCategory_Id);

						startActivity(advertisementIntent);
					}
				});

			}catch(Exception ex)
			{

			}

			mDialog.dismiss();
		}
    		
        

    	@Override
    	protected ArrayList<Category> doInBackground(Integer... arg0) 
    	{
    		// TODO Auto-generated method stub
    		DataSource dS = new DataSource();
    		ArrayList<Category> ObjectItemData = dS.getAllCategory();		    		
    		return ObjectItemData;		    		
    	}		    	
    	}	

    	public void getCategory()
    	{
    		try
    		{
    			new processGetCategory().execute();
    	     }
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    		}
}
