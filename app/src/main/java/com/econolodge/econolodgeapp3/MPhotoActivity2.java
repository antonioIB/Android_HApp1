package com.econolodge.econolodgeapp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.widget.ExpandableListView;

public class MPhotoActivity2 extends ActionBarActivity {

    private Uri pic;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    static ArrayList<Image> images = new ArrayList<>();

    ScrollView parent;
    LinearLayout table;

    private class Image
    {
        public String status;
        public Bitmap bitmap;
        Image(String status, Bitmap bitmap)
        {
            this.status = status;
            this.bitmap = bitmap;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mphoto_activity2);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        setContentView(R.layout.activity_mphoto_activity3);

        /*
        parent = new ScrollView(this);
        table = new LinearLayout(this);

        table.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        table.setLayoutParams(layoutParams);



        Intent i = getIntent();
        int id = i.getIntExtra("id", -1);

        String sId = Integer.toString(id);
        Log.d("OnCreate", "Calling getPicture");
        new GetPicture().execute(sId);
*/


    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Before");
        listDataHeader.add("During");
        listDataHeader.add("After");

        // Adding child data
        List<String> Before = new ArrayList<String>();
        Before.add("The Shawshank Redemption");
        Before.add("The Godfather");
        Before.add("The Godfather: Part II");
        Before.add("Pulp Fiction");
        Before.add("The Good, the Bad and the Ugly");
        Before.add("The Dark Knight");
        Before.add("12 Angry Men");

        List<String> During= new ArrayList<String>();
        During.add("The Conjuring");
        During.add("Despicable Me 2");
        During.add("Turbo");
        During.add("Grown Ups 2");
        During.add("Red 2");
        During.add("The Wolverine");

        List<String> After = new ArrayList<String>();
        After.add("2 Guns");
        After.add("The Smurfs 2");
        After.add("The Spectacular Now");
        After.add("The Canyons");
        After.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), Before); // Header, Child data
        listDataChild.put(listDataHeader.get(1), During);
        listDataChild.put(listDataHeader.get(2), After);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mphoto_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final int SUCCESS = 1;

    public void picClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, SUCCESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUCCESS) {
            if (resultCode == RESULT_OK) {
                pic = data.getData();
                Bitmap bmPic = (Bitmap) data.getExtras().get("data");
                PictureTask background = new PictureTask(this);
                background.execute(bmPic);
            }
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Picture Task~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private class PictureTask extends AsyncTask<Bitmap, Void, Void> {

        private Context context;

        private PictureTask(Context c) {
            context = c;
        }

        @Override
        protected Void doInBackground(Bitmap... args) {
            Bitmap download = null;
            String line = null;


            //compress and encode
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            args[0].compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy_hhmmss");
            String timeString = s.format(new Date());
            Log.d("Time", timeString);

            //upload
            try{
                InetAddress serverAddr = InetAddress.getByName(Message.SERVERIP);
                Message.PictureMessage pictureMessage = new Message.PictureMessage(byte_arr, timeString);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    private class GetPicture extends AsyncTask<String, Void, Void>
    {
        @Override
        public Void doInBackground(String... id)
        {
            try {
                Log.d("DownloadPictureTask: ", "In GetPictures");
                URL url = new URL("http://192.168.2.125/econolodgeapp/getImageUrl.php");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id[0], "UTF-8");

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                String line = "error";
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Image iBuf;
                Log.d("beforewhile ", line);
                while ((line = reader.readLine()) != null) {
                    Log.d("inwhile", line);
                    String[] splitString = line.split(",");

                    URL imageUrl = new URL("http://" + splitString[0]);
                    HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);

                    iBuf = new Image(splitString[1], myBitmap);
                    images.add(iBuf);
                }
                Log.d("afterWhile", line);
                Log.d("afterWhile", Integer.toString(images.size()));

                /*
                String imageUrl = "http://192.168.2.125/images" + id + ".jpg";
                URL url2 = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;*/
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
            Void v = null;
            return v;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            Log.d("DownloadPictureTask: ", "In postexecute");
            Log.d("postexecute", Integer.toString(images.size()));
            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            ImageView view;
            for(int x = 0; x < images.size(); x++)
            {
                Log.d("For Loop", images.get(x).status);
                view = new ImageView(getApplicationContext());
                view.setLayoutParams(imageParams);
                view.setImageBitmap(images.get(x).bitmap);
                table.addView(view);
            }

            parent.addView(table);

            setContentView(parent, layoutParams);
        }
    }
}
