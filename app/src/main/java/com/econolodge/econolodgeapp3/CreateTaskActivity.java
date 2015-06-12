package com.econolodge.econolodgeapp3;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class CreateTaskActivity extends ActionBarActivity {

    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Adding Task"); //doesn't work
        prgDialog.setCancelable(false);
        Log.d("CreateTask", "log working");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_task, menu);
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

    public void addTask (View view) {
        final ProgressDialog prgDialog = new ProgressDialog(this);

        EditText eTitle = (EditText) findViewById(R.id.tasktitle);
        String tTitle = eTitle.getText().toString();

        EditText eDesc = (EditText) findViewById(R.id.taskdesc);
        String tDesc = eDesc.getText().toString();

        Spinner eSpin = (Spinner) findViewById(R.id.spinner);
        String tSpin = eSpin.getSelectedItem().toString();

        prgDialog.show();
        new SendTask().execute(tTitle, tDesc, tSpin);
        prgDialog.hide();

        /*
        RequestParams params = new RequestParams();
        params.put("title", tTitle);
        params.put("description", tDesc);
        params.put("schedule", tSpin);

        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.2.128/econolodgeapp/addtask.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int responseCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();
                Toast.makeText(getApplicationContext(), "Task Added Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                String response = new String(bytes);
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Log.d("CreateTaskNetwork", "in StatusCode = 500");
                    Log.d("CreateTaskNetwork", response);
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    class SendTask extends AsyncTask<String, Void, String>
    {
        String tag = "SendTask";
        @Override
        protected String doInBackground(String... args) {
            String error_msg = "empty";
            try {
                String data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(args[0], "UTF-8");
                data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(args[1], "UTF-8");
                data += "&" + URLEncoder.encode("schedule", "UTF-8") + "=" + URLEncoder.encode(args[2], "UTF-8");

                URL url = new URL("http://192.168.2.128/econolodgeapp/addtask.php");

                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if((error_msg = reader.readLine()) == null)
                {
                    Log.d(tag, "Reader null");
                }
                else
                    Log.d(tag, error_msg);

            } catch (Exception e) {
                error_msg = "Error: Network Exception";
                e.printStackTrace();
            }
            return error_msg;
        }
        @Override
        public void onPostExecute(String msg) {
            prgDialog.hide();  //doesn't work
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
