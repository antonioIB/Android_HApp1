package com.econolodge.econolodgeapp3;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class MaintenanceFragment extends Fragment {


    public MaintenanceFragment() {
        // Required empty public constructor
    }
    static ArrayList<Task> taskList;
    String listStrings[];
    ListView listview;
    View globalview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maintenance, container, false);
        globalview= rootView;
        // Inflate the layout for this fragment
        listview = (ListView) rootView.findViewById(R.id.listviewMaintainence);

        new GetTask().execute();



        return rootView;
    }
    class Task {
        public String title;
        public String desc;
        public String time;
        public int id;

        public Task(String t, String d,String ti,int i)
        {
            title = t;
            desc = d;
            time = ti;
            id = i;
        }
    }

    class GetTask extends AsyncTask<Void, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(Void... args) {
            ArrayList<Task> out = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.2.105/econolodgeapp/maintenance.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                Task taskBuf;
                while ((line = reader.readLine()) != null) {
                    Log.d("GetTasksBuf: ", line);
                    String[] splitString = line.split(",");
                    taskBuf = new Task(splitString[0], splitString[1],splitString[2], Integer.parseInt(splitString[3]));
                    out.add(taskBuf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return out;
        }

        @Override
        protected void onPostExecute(ArrayList<Task> tL)
        {
            taskList = tL;
            listStrings = new String[taskList.size()];
            for(int i = 0; i < taskList.size(); i++)
            {
                listStrings[i] = taskList.get(i).title;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_2, android.R.id.text1, listStrings);
            listview.setAdapter(adapter);
        }
    }

}


