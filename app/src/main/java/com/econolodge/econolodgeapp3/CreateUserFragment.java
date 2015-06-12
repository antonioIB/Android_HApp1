package com.econolodge.econolodgeapp3;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUserFragment extends Fragment {

    private EditText txtDate, leavingDate, hirDate, fname, lname, city, zip, phoneno, email, address, ssn;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog leaveDialog;
    private DatePickerDialog hireDialog;
    private SimpleDateFormat dateFormatter;
    private View rootView;

    private String txtD;
    private String lDate;
    private String hDate;
    private String sFName;
    private String slname;
    private String scity;
    private String szip;
    private String sphoneno;
    private String semail;
    private Spinner employeetype;
    private String spinner;
    private Spinner active;
    private String spinner2;
    private String saddress;
    private String sssn;

    ProgressDialog prgDialog;
    public CreateUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_user, container, false);

        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setMessage("Adding Task"); //doesn't work
        prgDialog.setCancelable(false);
        Log.d("CreateTask", "log working");

        fname = (EditText) rootView.findViewById(R.id.fName);
        fname.setInputType(InputType.TYPE_NULL);
        lname = (EditText) rootView.findViewById(R.id.lName);
        lname.setInputType(InputType.TYPE_NULL);
        address = (EditText) rootView.findViewById(R.id.address);
        address.setInputType(InputType.TYPE_NULL);
        city = (EditText) rootView.findViewById(R.id.city);
        city.setInputType(InputType.TYPE_NULL);
        zip = (EditText) rootView.findViewById(R.id.zip);
        zip.setInputType(InputType.TYPE_NULL);
        phoneno = (EditText) rootView.findViewById(R.id.phone);
        phoneno.setInputType(InputType.TYPE_NULL);
        email = (EditText) rootView.findViewById(R.id.registerEmail);
        email.setInputType(InputType.TYPE_NULL);
        fname = (EditText) rootView.findViewById(R.id.fName);
        fname.setInputType(InputType.TYPE_NULL);
        txtDate = (EditText) rootView.findViewById(R.id.txtDate);
        txtDate.setInputType(InputType.TYPE_NULL);
        leavingDate = (EditText) rootView.findViewById(R.id.leavingDate);
        leavingDate.setInputType(InputType.TYPE_NULL);
        hirDate = (EditText) rootView.findViewById(R.id.hirDate);
        hirDate.setInputType(InputType.TYPE_NULL);
        ssn = (EditText) rootView.findViewById(R.id.ssn);
        ssn.setInputType(InputType.TYPE_NULL);
        employeetype = (Spinner) rootView.findViewById(R.id.positionspinner);
        active = (Spinner) rootView.findViewById(R.id.activespinner);
        Button register = (Button) rootView.findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "test", Toast.LENGTH_LONG).show();
                Log.d("RegisterUser", "Test");
                final ProgressDialog prgDialog = new ProgressDialog(getActivity());
                egister();

            }
        });

        return rootView;
    }
    //employeetype.setInputType(InputType.TYPE_NULL);
    /*private void findViewById() {
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtDate.setInputType(InputType.TYPE_NULL);
        leavingDate = (EditText) findViewById(R.id.leavingDate);
        leavingDate.setInputType(InputType.TYPE_NULL);
        hirDate = (EditText) findViewById(R.id.hirDate);
        hirDate.setInputType(InputType.TYPE_NULL);
    }*/

    /*private void setDateTimeField() {
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        leavingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity();
            }
        });
        hirDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity();
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtDate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar leaveCalendar = Calendar.getInstance();
        leaveDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                leavingDate.setText(dateFormatter.format(newDate.getTime()));

            }
        },leaveCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar hireCalendar = Calendar.getInstance();
        hireDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                hirDate.setText(dateFormatter.format(newDate.getTime()));

            }
        },hireCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }*/

    public void egister() {
         txtD = txtDate.getText().toString();
        lDate = leavingDate.getText().toString();
        hDate = hirDate.getText().toString();
        sFName = fname.getText().toString();
        slname = lname.getText().toString();
        semail = email.getText().toString();
        scity = city.getText().toString();
        sphoneno = phoneno.getText().toString();
        szip = zip.getText().toString();
        sssn = ssn.getText().toString();
        spinner = employeetype.getSelectedItem().toString();
        spinner2 = active.getSelectedItem().toString();
        saddress = address.getText().toString();
        prgDialog.show();
        new RegisterUser().execute(slname, sFName, semail, sphoneno, saddress, scity, szip, txtD, hDate, lDate, sssn, spinner2, spinner);
        prgDialog.hide();
    }

    class RegisterUser extends AsyncTask<String, Void, String> {
        String tag = "RegisterUser";

        @Override
        protected String doInBackground(String... args) {
            String error_msg = "empty";
            try {
                String data = URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(args[0], "UTF-8");
                data += "&" + URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(args[1], "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(args[2], "UTF-8");
                data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(args[3], "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(args[4], "UTF-8");
                data += "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(args[5], "UTF-8");
                data += "&" + URLEncoder.encode("zip", "UTF-8") + "=" + URLEncoder.encode(args[6], "UTF-8");
                data += "&" + URLEncoder.encode("birthdate", "UTF-8") + "=" + URLEncoder.encode(args[7], "UTF-8");
                data += "&" + URLEncoder.encode("hiredate", "UTF-8") + "=" + URLEncoder.encode(args[8], "UTF-8");
                data += "&" + URLEncoder.encode("leavedate", "UTF-8") + "=" + URLEncoder.encode(args[9], "UTF-8");
                data += "&" + URLEncoder.encode("ssn", "UTF-8") + "=" + URLEncoder.encode(args[10], "UTF-8");
                data += "&" + URLEncoder.encode("active", "UTF-8") + "=" + URLEncoder.encode(args[11], "UTF-8");
                data += "&" + URLEncoder.encode("position", "UTF-8") + "=" + URLEncoder.encode(args[12], "UTF-8");
                URL url = new URL("http://192.168.2.129/createUser.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if ((error_msg = reader.readLine()) == null) {
                    Log.d(tag, "Reader null");
                } else
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
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}


