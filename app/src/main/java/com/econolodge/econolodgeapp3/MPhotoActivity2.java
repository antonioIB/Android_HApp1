package com.econolodge.econolodgeapp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MPhotoActivity2 extends ActionBarActivity {

    private Uri pic;
    private String msg;
    ImageView current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mphoto_activity2);
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
        try {
            current = (ImageView) view;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, SUCCESS);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUCCESS) {
            if (resultCode == RESULT_OK) {
                pic = data.getData();
                Bitmap bmPic = (Bitmap) data.getExtras().get("data");
                current.setImageBitmap(bmPic);
                PictureTask background = new PictureTask(this);
                background.execute(bmPic);
            }
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Picture Task~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private class PictureTask extends AsyncTask<Bitmap, Void, String> {

        private Context context;

        private PictureTask(Context c) {
            context = c;
        }

        @Override
        protected String doInBackground(Bitmap... args) {
            final String link = "http://192.168.2.110/econolodgeapp/picture.php";
            String line = null;


            //compress and encode
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            args[0].compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

            //upload
            try {
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                data += "&" + URLEncoder.encode("picture", "UTF-8") + "=" + URLEncoder.encode(image_str, "UTF-8");

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if ((line = reader.readLine()) == null) {
                    Log.d("PictureTask: ", "null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return line;
        }

        @Override
        protected void onPostExecute(String args) {
            try {
                Log.d("PictureTask", args);
               // TextView textView = (TextView) findViewById(R.id.message);
               // textView.setText(args);
                msg = args;
                Log.d("PhotoNetwork", msg);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //private class DownloadPicture extends AsyncTask<>

}
