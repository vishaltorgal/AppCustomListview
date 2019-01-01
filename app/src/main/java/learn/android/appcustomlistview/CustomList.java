package learn.android.appcustomlistview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

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

                Toast.makeText(context.getApplicationContext(), "Row " + obj.get("name") + " was clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    static class ViewHolder {

        TextView enname, encontact;
        ImageView engender;
        LinearLayout ll_customlist;

    }


}
