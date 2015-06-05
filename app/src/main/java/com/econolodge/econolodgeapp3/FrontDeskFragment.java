package com.econolodge.econolodgeapp3;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrontDeskFragment extends Fragment {

    public View globalView;
    public ProgressDialog prDialog;
    protected static final int CONTEXT_MENU_OPTION1 = 1;
    protected static final int CONTEXT_MENU_OPTION2 = 2;
    protected static final int CONTEXT_MENU_OPTION3 = 3;
    public static final String[] room = new String[]
            {"101", "102", "103", "104", "105", "106", "107", "108",
                    "111", "112", "113", "114", "115", "116", "117", "118",
                    "200", "201", "202", "203", "204", "205", "206", "207", "208",
                    "210", "211", "212", "213", "214", "215", "216", "217", "218"};
    ListView listView;
    List<RowItem> rowItems;

    public String[] availability = new String[room.length];


    public FrontDeskFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_front_desk, container, false);
        globalView = rootView;
        //prDialog = new ProgressDialog(getApplicationContext());
        //prDialog.setMessage("Loading Rooms");
        //prDialog.setCancelable(true);
        //prDialog.show();
        new RoomNetwork().execute(room);
//        registerForContextMenu(listView);
        return rootView;
    }




    public class RowItem {
        private String room;
        private String avail;
        public RowItem(String room, String avail)
        {   this.room=room;
            this.avail=avail;}
        public String getroom() {
            return room;
        }
        public void setRoom(String room){
            this.room=room;
        }
        public String getavail() {
            return avail;
        }
        public void setAvail(String avail){
            this.avail=avail;
        }
        @Override
        public String toString(){
            return room+ "/n"+avail;
        }
    }

    public class ListViewAdapter extends ArrayAdapter<RowItem> {
        Context context;
        public ListViewAdapter(Context context, int resourceId, List<RowItem> items) {
            super(context,resourceId, items);
            this.context= context;
            notifyDataSetChanged();
        }
        private class ViewHolder {
            TextView txtRoomNo;
            TextView txtAvailable;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;
            RowItem rowItem = getItem(position);
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.listitem, null);
                holder= new ViewHolder();
                holder.txtRoomNo=(TextView) convertView.findViewById(R.id.RoomNo);
                holder.txtAvailable=(TextView) convertView.findViewById(R.id.Available);
                convertView.setTag(holder);
            }else
                holder =(ViewHolder) convertView.getTag();
            holder.txtRoomNo.setText(rowItem.getroom());
            holder.txtAvailable.setText(rowItem.getavail());
            return  convertView;
        }
    }

    class RoomNetwork extends AsyncTask<String[], Void, String[]> {
        @Override
        protected String[] doInBackground(String[]... args) {
            String lines[] = new String[args[0].length];
            try {
                String data = URLEncoder.encode("room0", "UTF-8") + "=" + URLEncoder.encode(args[0][0], "UTF-8");
                final String link = "http://192.168.1.4/rooms.php";
                for (int i = 1; i < args[0].length; i++) {
                    data += "&" + URLEncoder.encode("room" + i, "UTF-8") + "=" + URLEncoder.encode(args[0][i], "UTF-8");
                }

                //Log.d("ASyncTask", data);

                URL url = new URL(link);

                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                int i = 0;
                while (i < 34 && (lines[i] = reader.readLine()) != null) {
                    Log.d("ASyncTask", "reading line");
                    Log.d("ASyncTask", lines[i]);
                    i++;
                }

            } catch (Exception e) {
                e.printStackTrace();
                for (int i = 0; i < args[0].length; i++) {
                    lines[i] = "Error";
                    //prDialog.hide();
                }
            }
            return lines;
        }

        @Override
        public void onPostExecute(String[] lines) {
            rowItems = new ArrayList<RowItem>();
            for (int i = 0; i < room.length; i++) {

                RowItem item = new RowItem(room[i], lines[i]);
                rowItems.add(item);
            }

            listView = (ListView) globalView.findViewById(R.id.listview1);
            ListViewAdapter adapter = new ListViewAdapter(getActivity(),
                    R.layout.fragment_front_desk, rowItems);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(room[info.position] + ":Select Option");
        menu.add(Menu.NONE, CONTEXT_MENU_OPTION1, 0, "Checked Out");
        menu.add(Menu.NONE, CONTEXT_MENU_OPTION2, 1, "Clean");
        menu.add(Menu.NONE, CONTEXT_MENU_OPTION3, 2, "In House");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_OPTION1:
                listView.getChildAt(info.position).setBackgroundResource(R.color.red);
                Intent i = new Intent(getActivity().getApplicationContext(), HousekeepingFragment.class);
                i.putExtra("room", room[info.position].toString());
                startActivity(i);
                Toast.makeText(getActivity(), room[info.position] + " -- DIRTY", Toast.LENGTH_LONG).show();
                return true;
            case CONTEXT_MENU_OPTION2:
                listView.getChildAt(info.position).setBackgroundResource(R.color.Green);
                TextView Available = (TextView) listView.getChildAt(info.position).findViewById(R.id.Available);
                Available.setText("Available");
                Toast.makeText(getActivity(), room[info.position] + " -- clean", Toast.LENGTH_LONG).show();
                return true;
            case CONTEXT_MENU_OPTION3:
                listView.getChildAt(info.position).setBackgroundResource(R.color.Return);
                TextView occupied = (TextView) listView.getChildAt(info.position).findViewById(R.id.Available);
                occupied.setText("Occupied");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}