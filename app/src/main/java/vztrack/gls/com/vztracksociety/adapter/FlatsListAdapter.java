package vztrack.gls.com.vztracksociety.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vztrack.gls.com.vztracksociety.R;
import vztrack.gls.com.vztracksociety.profile.FlatsList;

/**
 * Created by akshay on 1/2/15.
 */
public class FlatsListAdapter extends ArrayAdapter<FlatsList> {

    Context context;
    int resource, textViewResourceId;
    List<FlatsList> items, tempItems, suggestions;

    public FlatsListAdapter(Context context, int resource, int textViewResourceId, List<FlatsList> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<FlatsList>(items); // this makes the difference.
        suggestions = new ArrayList<FlatsList>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.autocomplite_lyout, parent, false);
        }
        FlatsList people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getFlats().trim());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((FlatsList) resultValue).getFlats();
            return str.trim();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();

                for (FlatsList people : tempItems) {

                    if(constraint.length()==1)
                    {
                        if ( people.getFlats().toLowerCase().startsWith(constraint.toString().toLowerCase(),0)) {
                            suggestions.add(people);
                        }
                    }
                    else
                    {
                        if ( people.getFlats().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(people);
                        }
                    }

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<FlatsList> filterList = (ArrayList<FlatsList>) results.values;
            if (results != null && results.count > 0) {
                clear();
                try{
                    for (FlatsList people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }catch (Exception e)
                {
                }

            }
        }
    };
}
