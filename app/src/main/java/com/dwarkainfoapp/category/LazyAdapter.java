package com.dwarkainfoapp.category;
import java.util.ArrayList;
import java.util.Locale;

import com.dwarkainfoapp.category.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dwarkainfoapp.category.Category_List;

public class LazyAdapter extends BaseAdapter
{
	 private Activity activity;
	    private ArrayList<Category> data = null;
	    private static LayoutInflater inflater=null;
	    public ImageLoader imageLoader;
		private ArrayList<Category> arrayList = null;
		private ArrayList<Category> cloneList = null;

	    public LazyAdapter(Activity a, ArrayList<Category> d)
	    {
			arrayList = (ArrayList<Category>) d;
	        activity = a;
	        if(data !=null)
	        {
		        data.clear();
		    }

	         data=d;	        
	        notifyDataSetChanged();        
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        imageLoader=new ImageLoader(activity.getApplicationContext());	       
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
	        View vi=convertView;	       
	        notifyDataSetChanged();	        
	        if(convertView==null)
	        
	       // vi = inflater.inflate(R.layout.list_view_row_item, null);
	        vi = inflater.inflate(R.layout.list_view_row_item, null);	        
	       TextView category_name  = (TextView)vi.findViewById(R.id.category_name); 
	       TextView discri = (TextView)vi.findViewById(R.id.category_description); 
	        ImageView thumb_image=(ImageView)vi.findViewById(R.id.category_image); 	 
	        Category p = data.get(position);	        

	        
	        //for alternate color on list view
	        if(position % 2 == 0)
	        {
	                //vi.setBackgroundColor(Color.parseColor("#FFFFFF"));
	                vi.setBackgroundResource(R.drawable.list_selector);	                
	        }
	        else
	        {
	                //vi.setBackgroundColor(Color.parseColor("#FFFFFF"));
	                vi.setBackgroundResource(R.drawable.list_selector);
	               // vi.setBackgroundResource(R.drawable.list_selector_alternate);		                
	        }
        
	       
	        
	     //   String bhk_options_str = p.mStakeholderId.replace("0,","").replace(", 0", "") + " BHK";
	        category_name.setText(p.mCategoryName);	        
	        discri.setText(p.mDisc);   
	        imageLoader.DisplayImage("http://dwarkainfo.nextalphabet.com/images/" + p.mphoto, thumb_image);
	        return vi;
	    }

	// Filter Class
	public void filter(CharSequence charText) {
//		charText = charText.toLowerCase(Locale.getDefault());

//		data.clear();

		if (charText.length() == 0) {
			data.addAll(arrayList);
		}
		else
		{
			for (int i = 0; i < data.size(); i++)
			{

				if (data.get(i).mCategoryName.toLowerCase(Locale.getDefault()).contains(charText))
				{
					cloneList.add(data.get(i));
				}
			}


		}

		notifyDataSetChanged();
	}
}
