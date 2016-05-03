package com.bareillyinfoapp.category;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class GalleryDemoActivity extends Activity {
    private Activity activity;
    private ImageView selectedImageView, leftArrowImageView, rightArrowImageView;
    private Gallery gallery;
    private int selectedImagePosition = 0;
    private GalleryImageAdapter galImageAdapter;
    float startXValue = 1;
    String advertisement_id;
    ArrayList<String> galleryImages;
    public ImageLoader imageLoader;
    private TextView noImageTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        advertisement_id = getIntent().getExtras().getString("advertisement_id");
        setContentView(R.layout.main);
        activity = this;
        imageLoader = new ImageLoader(activity);
        getDrawablesList();
        setupUI();
    }

    private void setupUI() {

        selectedImageView = (ImageView) findViewById(R.id.selected_imageview);
        noImageTextView = (TextView) findViewById(R.id.noImage_textview);


        leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
        rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
        gallery = (Gallery) findViewById(R.id.gallery);



        selectedImageView.setOnTouchListener(new View.OnTouchListener() {
            float nPicturePositionM = 0;

            public boolean onTouch(final View view, final MotionEvent event) {

                float endXValue = 0;
                float x1 = event.getAxisValue(MotionEvent.AXIS_X);
                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        startXValue = event.getAxisValue(MotionEvent.AXIS_X);

                        return true;

                    case (MotionEvent.ACTION_UP):
                        endXValue = event.getAxisValue(MotionEvent.AXIS_X);
                        if (endXValue > startXValue) {
                            if (endXValue - startXValue > 100) {
                                System.out.println("Left-Right");
                                if (selectedImagePosition > 0) {
                                    --selectedImagePosition;

                                }

                                gallery.setSelection(selectedImagePosition, false);
                            }
                        } else {
                            if (startXValue - endXValue > 100) {
                                System.out.println("Right-Left");

                                if (selectedImagePosition < galleryImages.size() - 1) {
                                    ++selectedImagePosition;
                                }

                                gallery.setSelection(selectedImagePosition, false);
                            }
                        }
                        return true;

                    default:
                        return true;
                }
            }
        });

        leftArrowImageView.setOnClickListener(new OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      if (selectedImagePosition > 0) {
                                                          --selectedImagePosition;
                                                      }
                                                      gallery.setSelection(selectedImagePosition, false);
                                                  }
                                              }
        );

        rightArrowImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition < galleryImages.size() - 1) {
                    ++selectedImagePosition;

                }

                gallery.setSelection(selectedImagePosition, false);

            }
        });

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                selectedImagePosition = pos;

                if (selectedImagePosition > 0 && selectedImagePosition < galleryImages.size() - 1) {

                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_enabled));
                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_enabled));

                } else if (selectedImagePosition == 0) {

                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_disabled));

                } else if (selectedImagePosition == galleryImages.size() - 1) {

                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
                }

                changeBorderForSelectedImage(selectedImagePosition);
                setSelectedImage(selectedImagePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });



        if (galleryImages.size() > 0) {

            gallery.setSelection(selectedImagePosition, false);

        }

        if (galleryImages.size() == 1) {

            rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
        }

    }

    private void changeBorderForSelectedImage(int selectedItemPos) {

        int count = gallery.getChildCount();

        for (int i = 0; i < count; i++) {

            ImageView imageView = (ImageView) gallery.getChildAt(i);
            imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
            imageView.setPadding(3, 3, 3, 3);

        }

        ImageView imageView = (ImageView) gallery.getSelectedView();
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
        imageView.setPadding(3, 3, 3, 3);
    }

    private void getDrawablesList() {

        galleryImages = new ArrayList<String>();

        getGalleryImages();
    }


    class ProcessGetImages extends AsyncTask<Integer, Integer, ArrayList<String>> {
        ProgressDialog nDialog;

        @Override
        protected void onPreExecute()
        {
            nDialog = new ProgressDialog(GalleryDemoActivity.this);
            nDialog.setMessage("Receiving data...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... arg0)
        {
            DataSource dS = new DataSource();
            Log.i("here", "doInBackground: adver id"+ advertisement_id);
            ArrayList<String> ObjectItemData = dS.getAllGalleryImages(Integer.parseInt(advertisement_id));

            return ObjectItemData;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result)
        {
            if(result.size() > 0) {
                for(int i = 0; i< result.size(); i++) {
                    galleryImages.add(result.get(i));
                }
                galImageAdapter = new GalleryImageAdapter(activity, galleryImages);
                leftArrowImageView.setVisibility(View.VISIBLE);
                rightArrowImageView.setVisibility(View.VISIBLE);
                gallery.setAdapter(galImageAdapter);
            } else {
                leftArrowImageView.setVisibility(View.INVISIBLE);
                rightArrowImageView.setVisibility(View.INVISIBLE);
                noImageTextView.setVisibility(View.VISIBLE);
            }

            nDialog.dismiss();
        }

    }

    public void getGalleryImages()
    {
        try
        {
            new ProcessGetImages().execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setSelectedImage(int selectedImagePosition) {
//
//        BitmapDrawable bd = (BitmapDrawable) galleryImages.get(selectedImagePosition);
//        Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
//        selectedImageView.setImageBitmap(b);
        imageLoader.DisplayImage("http://dwarkainfo.nextalphabet.com/images/adds/" + galleryImages.get(selectedImagePosition), selectedImageView);
        selectedImageView.setScaleType(ScaleType.FIT_XY);

    }
}