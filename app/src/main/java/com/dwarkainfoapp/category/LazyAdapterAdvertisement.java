package com.dwarkainfoapp.category;
import java.util.ArrayList;

import com.dwarkainfoapp.category.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

public class LazyAdapterAdvertisement extends BaseAdapter
{
	    private Activity activity;
	    private ArrayList<Advertisement> data = null;
	    private static LayoutInflater inflater=null;
	    public ImageLoader imageLoader;
		Dialog myDialog ;

		public LazyAdapterAdvertisement(Activity a, ArrayList<Advertisement> d)
	    {
	        activity = a;	        
	        if(data !=null)
	        {
		        data.clear();
		    }	        
	        data=d;	        
	        notifyDataSetChanged();        
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        imageLoader = new ImageLoader(activity.getApplicationContext());
	    }
	    
	    public int getCount() 
	    {
	        return data.size();
	    }

	    public Object getItem(int position) 
	    {
	        return position;
	    }

	    public long getItemId(int position) 
	    {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
			final String advertisementId;

			View vi=convertView;
	        notifyDataSetChanged();	        
	        if(convertView==null)
	        
	       // vi = inflater.inflate(R.layout.list_view_row_item, null);
	        vi = inflater.inflate(R.layout.advertise_list_view, null);	        
	        TextView advertisement_name  = (TextView)vi.findViewById(R.id.ad_name);	      
	        TextView description = (TextView)vi.findViewById(R.id.ad_dec); 
	        TextView mobile_no = (TextView)vi.findViewById(R.id.mobile); 
	        TextView email = (TextView)vi.findViewById(R.id.email); 	        
	        TextView address = (TextView)vi.findViewById(R.id.address);
	        ImageView thumb_image=(ImageView)vi.findViewById(R.id.imageView1);



			ImageButton offerButton = (ImageButton)vi.findViewById(R.id.imageButton1);

			Advertisement p = data.get(position);

			advertisementId = p.mAdvertisementId;

			offerButton.setTag(advertisementId);
			offerButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					getOffer((String) view.getTag());
				}

			});

			//open gallery on click of thumb image
			thumb_image.setOnClickListener(new View.OnClickListener() {

				public void onClick(View view) {
					Intent myIntent = new Intent(activity, GalleryDemoActivity.class);

					Log.i("my id", "onClick: adver id"+ advertisementId);
					myIntent.putExtra("advertisement_id", advertisementId); //Optional parameters
					activity.startActivity(myIntent);
				}
			});

			if(p.mOfferExist.equals("0")) {

				offerButton.setVisibility(View.INVISIBLE);
			} else {
				offerButton.setVisibility(View.VISIBLE);
			}

	        //for alternate color on list view
	        if(position % 2 == 0)
	        {
	                vi.setBackgroundColor(Color.parseColor("#FFFFFF"));
	                //vi.setBackgroundResource(R.drawable.list_selector);	                
	        }
	        else
	        {
	                vi.setBackgroundColor(Color.parseColor("#EFEFEF"));
	               // vi.setBackgroundResource(R.drawable.list_selector_alternate);		                
	        }
        

	        
	     //   String bhk_options_str = p.mStakeholderId.replace("0,","").replace(", 0", "") + " BHK";
	        advertisement_name.setText(p.madvertiseName);
	        description.setText(p.mDescription);
	         mobile_no.setText(p.mMobileNo);
	         mobile_no.setOnClickListener(new View.OnClickListener() {
				 public void onClick(View v) {
					 TextView temp = (TextView) v;
					 Intent intent = new Intent(Intent.ACTION_DIAL);
					 intent.setData(Uri.parse("tel:" + temp.getText()));
					 v.getContext().startActivity(intent);
				 }
			 });
	         email.setText(p.mEmail);
	        address.setText(p.mAddress);    
	       
	        //imageLoader.DisplayImage("http://192.168.1.2/www.yellowsheet.com/images/" + p.mphoto, thumb_image);
			imageLoader.DisplayImage("http://dwarkainfo.nextalphabet.com/images/" + p.mphoto, thumb_image);
	        return vi;
	    }


	class processGetAdvertise extends AsyncTask<String, Integer, Offer> {
		ProgressDialog nDialog;

		protected void onPreExecute() {

			nDialog = new ProgressDialog(activity);
			nDialog.setMessage("receiving data...");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		protected Offer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			DataSource dS = new DataSource();
			Offer ObjectItemData = null;
			try{
				Log.i("in async task", "doInBackground: "+arg0[0]);
				ObjectItemData = dS.getoffer(arg0[0]);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			nDialog.dismiss();

			return ObjectItemData;
		}

		protected void onPostExecute(Offer result) {

			try {
				if (result != null) {
					Log.i("poop", "onPostExecute: i m here");
					myDialog = new Dialog(activity);
					myDialog.setContentView(R.layout.offer_form);
					myDialog.setTitle("Offer");

					//Button save = (Button) myDialog.findViewById(R.id.imageButton1);


					TextView offer_1 = (TextView) myDialog.findViewById(R.id.textView1);
					TextView offer_2 = (TextView) myDialog.findViewById(R.id.textView2);
					TextView offer_3 = (TextView) myDialog.findViewById(R.id.textView3);

					offer_1.setText(result.moffer_1);
					offer_2.setText(result.moffer_2);
					offer_3.setText(result.moffer_3);

					myDialog.show();
				}
			}catch(Exception ex) {

			}
		}





	}

	public void getOffer(String advertisementId){
		try{
			new processGetAdvertise().execute(advertisementId);
		}
		catch (Exception e){
			//e.printStackTrace();
		}}
}
