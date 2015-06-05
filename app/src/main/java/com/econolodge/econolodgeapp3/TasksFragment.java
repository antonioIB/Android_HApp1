package com.econolodge.econolodgeapp3;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    static ArrayList<Task> taskList;
    String listStrings[];
    ListView listview;
    View globalView;
    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        globalView = rootView;

        listview = (ListView) rootView.findViewById(R.id.listview1);

        new GetTasks().execute();



        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.tasks_action_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TaskFragment", "onOptionsItemSelected itemid:" + item.getItemId() + "inflaterId:" + R.id.action_add_task);
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d("TaskFragment", "case matched");
                Intent intent = new Intent(getActivity(), CreateTaskActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    class Task {
        public String title;
        public String desc;
        public int id;

        public Task(String t, String d, int i)
        {
            title = t;
            desc = d;
            id = i;
        }
    }

    class GetTasks extends AsyncTask<Void, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(Void... args) {
            ArrayList<Task> out = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.2.128/econolodgeapp/gettasks.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                Task taskBuf;
                while ((line = reader.readLine()) != null) {
                    Log.d("GetTasksBuf: ", line);
                    String[] splitString = line.split(",");
                    taskBuf = new Task(splitString[0], splitString[1], Integer.parseInt(splitString[2]));
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
                    android.R.layout.simple_list_item_1, android.R.id.text1, listStrings);
            listview.setAdapter(adapter);
        }
    }

}
