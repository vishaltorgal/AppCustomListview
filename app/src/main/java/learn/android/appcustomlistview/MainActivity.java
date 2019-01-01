package learn.android.appcustomlistview;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private static final String TAG_EMPLOYEE_NAME = "name";
    private static final String TAG_EMPLOYEE_CONTACT = "contact";
    private static final String TAG_EMPLOYEE_GENDER = "gender";
    ArrayList<HashMap<String, String>> arraylist;
    CustomList adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listview = (ListView) findViewById(R.id.list);
        arraylist = new ArrayList<HashMap<String, String>>();
        adapter = new CustomList(this, arraylist);
        listview.setAdapter(adapter);


        new Task().execute();


    }

    public String FetchJSONFileFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("employee.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private class Task extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @SuppressLint("CommitTransaction")
        protected Void doInBackground(Void... params) {


            try {

                JSONArray arr = new JSONArray(FetchJSONFileFromAsset());

                for (int i = 0; i < arr.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject obj = arr.getJSONObject(i);

                    map.put("name", obj.getString(TAG_EMPLOYEE_NAME));
                    map.put("contact", obj.getString(TAG_EMPLOYEE_CONTACT));
                    map.put("gender", obj.getString(TAG_EMPLOYEE_GENDER));

                    arraylist.add(map);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            adapter.notifyDataSetChanged();

        }

    }
}