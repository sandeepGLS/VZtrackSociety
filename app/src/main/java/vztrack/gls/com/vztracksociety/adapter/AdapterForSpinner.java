package vztrack.gls.com.vztracksociety.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vztrack.gls.com.vztracksociety.R;

/**
 * Created by sandeep on 18/4/16.
 */
public class AdapterForSpinner extends ArrayAdapter {


    String[] t1;
    String[] t2;
    String[] t3;
    Context context;

    public AdapterForSpinner(Context context, int textViewResourceId, String[] objects1, String[] objects2, String[] objects3) {
        super(context, textViewResourceId, objects1);
        this.t1 = objects1;
        this.context = context;
        this.t2 = objects2;
        this.t3 = objects3;
    }

    public View getCustomView(int position, View convertView,ViewGroup parent) {

// Inflating the layout for the custom Spinner
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_spinner, parent, false);

// Declaring and Typecasting the textview in the inflated layout
        TextView TvTitle0 = (TextView) layout.findViewById(R.id.t0);
        TextView TvTitle1 = (TextView) layout.findViewById(R.id.t1);
        TextView TvTitle2 = (TextView) layout.findViewById(R.id.t2);
        TextView TvTitle3 = (TextView) layout.findViewById(R.id.t3);

// Setting the text using the array
        if(position!=0)
        TvTitle0.setText(""+position);
        TvTitle1.setText(t1[position]);
        TvTitle2.setText(t2[position]);
        TvTitle3.setText(t3[position]);

        return layout;
    }


    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}