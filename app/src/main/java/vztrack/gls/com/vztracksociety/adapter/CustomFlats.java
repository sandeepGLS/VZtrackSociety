/*
package vztrack.gls.com.vztracksociety.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import vztrack.gls.com.vztracksociety.R;


public class CustomFlats extends ArrayAdapter<String>{


 private final Activity context;
    ArrayList<String> arrayOfFlats;
    public CustomFlats(Activity context, ArrayList<String> arrayOfFlats) {
       super(context, arrayOfFlats);
        this.context = context;
        this.arrayOfFlats = arrayOfFlats;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_custom, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.flatNo);
        txtTitle.setText(arrayOfFlats.get(position));
        return rowView;
    }

}*/
