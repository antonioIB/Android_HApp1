package com.econolodge.econolodgeapp3;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaintenanceFragment extends Fragment {


    public MaintenanceFragment() {
        // Required empty public constructor
    }
    String listStrings[];
    ListView listview;
    View globalview;
    protected static final int CONTEXT_MENU_OPTION1 = 1;
    protected static final int CONTEXT_MENU_OPTION2 = 2;
    List<MRowItem> mRowItems;


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(mRowItems.get(info.position).getTitle() + ":Select Option");
        menu.add(Menu.NONE, CONTEXT_MENU_OPTION1, 0, "images");
        menu.add(Menu.NONE, CONTEXT_MENU_OPTION2, 1, "work status");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView available = (TextView) listview.getChildAt(info.position).findViewById(R.id.Available);
        switch (item.getItemId()) {
            case CONTEXT_MENU_OPTION1:
                listview.getChildAt(info.position).setBackgroundResource(R.color.Green);
                Intent intent = new Intent(getActivity(), MPhotoActivity2.class);
                startActivity(intent);
                //Intent i = new Intent(getActivity().getApplicationContext(), HousekeepingFragment.class);
              /*  switch (available.getText().toString()) {
                    case ("Available") :
                        Toast.makeText(getActivity(), "Error: Can't check out if room is AVAILABLE", Toast.LENGTH_LONG).show();
                        return true;
                    case ("Occupied") :
                        //TODO do server work
                        break;
                    case ("Dirty") :
                        Toast.makeText(getActivity(), "Error: Room already checked out", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
                //i.putExtra("room", room[info.position].toString());
                //available = (TextView) listView.getChildAt(info.position).findViewById(R.id.Available);
                //startActivity(i);
                //Toast.makeText(getActivity(), room[info.position] + " -- DIRTY", Toast.LENGTH_LONG).show();
                return true;*/
                return true;

            case CONTEXT_MENU_OPTION2:
                listview.getChildAt(info.position).setBackgroundResource(R.color.red);
                //available = (TextView) listView.getChildAt(info.position).findViewById(R.id.Available);
                //Available.setText("Available");
                /*switch (available.getText().toString()) {
                    case ("Available") :
                        Toast.makeText(getActivity(), "Error: Room already clean", Toast.LENGTH_LONG).show();
                        return true;
                    case ("Occupied") :
                        Toast.makeText(getActivity(), "Error: Can't clean an OCCUPIED room", Toast.LENGTH_LONG).show();
                        return true;
                    case ("Dirty") :
                        Intent i = new Intent(getActivity(), CleanApprovalActivity.class);
                        i.putExtra("room", room[info.position]);
                        //TODO get pictures
                        break;
                    default:
                        return false;
                }*/
                return true;
        }
        return super.onContextItemSelected(item);
    }




    public class MRowItem {
        private String title;
        private String description;
        private String time;
        private int id;
        public MRowItem(String title, String description, String time, int i)
        {   this.title=title;
            this.description=description;
            this.time=time;
            this.id = i;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title){
            this.title=title;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description){
            this.description=description;
        }
        public String getTime() {
            return time;
        }
        public void setTime(String time){
            this.time=time;
        }
        @Override
        public String toString(){
            return title+ "/n"+description+ "/n"+time;
        }
    }

    class GetTask extends AsyncTask<Void, Void, ArrayList<MRowItem>> {
        @Override
        protected ArrayList<MRowItem> doInBackground(Void... args) {
            ArrayList<MRowItem> out = new ArrayList<>();
            try {
                URL url = new URL("http://192.168.2.125/maintenance.php");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                MRowItem taskBuf;
                while ((line = reader.readLine()) != null) {
                    Log.d("GetTasksBuf: ", line);
                    String[] splitString = line.split(",");
                    if(splitString.length == 4) {
                        taskBuf = new MRowItem(splitString[0], splitString[1], splitString[2], Integer.parseInt(splitString[3]));
                        out.add(taskBuf);
                    }
                    else
                        Log.d("GetTask", line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return out;
        }

        @Override
        protected void onPostExecute(ArrayList<MRowItem> mL)
        {
            mRowItems = mL;
            listStrings = new String[mL.size()];
            /*
            for(int i = 0; i < mL.size(); i++)
            {
                 MRowItem item  = new MRowItem(listStrings[i], mL.get(i).getDescription(), mL.get(i).getTime());
            }*/


            listview = (ListView) globalview.findViewById(R.id.listviewMaintainence);
            MListViewAdapter adapter = new MListViewAdapter(getActivity(),
                    R.layout.fragment_maintenance, mRowItems);
            listview.setAdapter(adapter);
            //prDialog.hide();
            registerForContextMenu(listview);
        }

    }

    private class MListViewAdapter extends ArrayAdapter<MRowItem> {
        Context context;
        public MListViewAdapter(Context context, int fragment_maintenance, List<MRowItem> mRowItems) {
            super(getActivity().getApplicationContext(), fragment_maintenance, mRowItems);
            this.context= context;
            notifyDataSetChanged();
        }

        private class ViewHolder {
            TextView txtTitle;
            TextView txtDescription;
            TextView txtTime;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;
            MRowItem rowItem = getItem(position);
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.maintainencelistitems, null);
                holder= new ViewHolder();
                holder.txtTitle=(TextView) convertView.findViewById(R.id.Task);
                holder.txtDescription=(TextView) convertView.findViewById(R.id.Description);
                holder.txtTime=(TextView) convertView.findViewById(R.id.Time);
                convertView.setTag(holder);
            }else
                holder =(ViewHolder) convertView.getTag();
            holder.txtTitle.setText(rowItem.getTitle());
            holder.txtDescription.setText(rowItem.getDescription());
            holder.txtTime.setText(rowItem.getTime());
            return  convertView;
        }
    }
}




