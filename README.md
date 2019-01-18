
## Custom ListView in Android

<a href="https://github.com/vishaltorgal/AppCustomListview/raw/master/app/customlistview.apk"><img src="https://github.com/vishaltorgal/SendingEmails/blob/master/dlapk.png" width="150" height="80" title="White flower" alt="Flower"></a>

<br>

<img src="https://github.com/vishaltorgal/AppCustomListview/blob/master/app/clv1.png" alt="alt text" width="300" height="450">
<br>


    MainActivity

```java


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
        
 ```
 
 ```java
 
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
 
 ```
 
 ```java
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
 
 ```
 
    CustomList
    
 ```java
 
 public class CustomList extends BaseAdapter {

    private final Activity context;
    ArrayList<HashMap<String, String>> data;


    public CustomList(Activity context,
                      ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.layout_customlist, null);
            holder = new ViewHolder();
            holder.enname = (TextView) view.findViewById(R.id.enname);
            holder.encontact = (TextView) view.findViewById(R.id.encontact);
            holder.engender = (ImageView) view.findViewById(R.id.engender);
            holder.ll_customlist = (LinearLayout) view.findViewById(R.id.ll_customlist);

            view.setTag(holder);

        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) view.getTag();
        }

        HashMap<String, String> obj = data.get(position);

        holder.enname.setText(obj.get("name"));
        holder.encontact.setText(obj.get("contact"));

        if (obj.get("gender").equals("male")) {
            holder.engender.setImageResource(R.drawable.img_male);
        } else {
            holder.engender.setImageResource(R.drawable.img_female);
        }

        holder.ll_customlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> obj = data.get(position);

                Toast.makeText(context.getApplicationContext(), "Row " + obj.get("name") + " was clicked!",  
                Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    static class ViewHolder {
        TextView enname, encontact;
        ImageView engender;
        LinearLayout ll_customlist;
    }
 
 ```
 
