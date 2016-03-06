package com.bareillyinfoapp.category;
import java.util.ArrayList;

import com.bareillyinfoapp.category.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class LazyAdapter extends BaseAdapter 
{
	 private Activity activity;
	    private ArrayList<Category> data = null;
	    private static LayoutInflater inflater=null;
	    public ImageLoader imageLoader; 
	 
	    public LazyAdapter(Activity a, ArrayList<Category> d) 
	    {
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
	        imageLoader.DisplayImage("http://www.smartcityinfo.in/adminbly/images/" + p.mphoto, thumb_image);
	        return vi;
	    }
}
