package com.econolodge.econolodgeapp3;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateUserFragment extends Fragment {

    private EditText txtDate,leavingDate,hirDate;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog leaveDialog;
    private DatePickerDialog hireDialog;
    private SimpleDateFormat dateFormatter;
    private View rootView;

    private String txtD, lDate, hDate;

    public CreateUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_user, container, false);

        txtDate = (EditText) rootView.findViewById(R.id.txtDate);
        txtDate.setInputType(InputType.TYPE_NULL);
        leavingDate = (EditText) rootView.findViewById(R.id.leavingDate);
        leavingDate.setInputType(InputType.TYPE_NULL);
        hirDate = (EditText) rootView.findViewById(R.id.hirDate);
        hirDate.setInputType(InputType.TYPE_NULL);


        return inflater.inflate(R.layout.fragment_create_user, container, false);

    }

/*
    private void findViewById() {
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtDate.setInputType(InputType.TYPE_NULL);
        leavingDate = (EditText) findViewById(R.id.leavingDate);
        leavingDate.setInputType(InputType.TYPE_NULL);
        hirDate = (EditText) findViewById(R.id.hirDate);
        hirDate.setInputType(InputType.TYPE_NULL);
    }*/
    /*
    private void setDateTimeField() {
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        leavingDate.setOnClickListener(this);
        hirDate.setOnClickListener(this);
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

    public void registerUser(View view) {
        txtD = txtDate.getText().toString();
        lDate = leavingDate.getText().toString();
        hDate = hirDate.getText().toString();
    }
}
