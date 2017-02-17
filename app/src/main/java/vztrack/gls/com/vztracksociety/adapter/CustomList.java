package vztrack.gls.com.vztracksociety.adapter;

/**
 * Created by sandeep on 18/4/16.
 */
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import vztrack.gls.com.vztracksociety.R;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] flat_no;
    private final String[] vehicle_no;
    private final String[] time;
    private final String[] out_time;
    private final String[] owner_name;
    private  String UI;

    public CustomList(Activity context, String[] flat_no, String[] vehicle_no, String[] time,String[] out_time,String[] owner_name,String UI)
    {
        super(context, R.layout.list_single, flat_no);
        this.context = context;
        this.flat_no = flat_no;
        this.vehicle_no = vehicle_no;
        this.owner_name = owner_name;
        this.time = time;
        this.out_time = out_time;
        this.UI = UI;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView tvFlatNo = (TextView) rowView.findViewById(R.id.tvFlatNo);
        TextView tvVehicleNo = (TextView) rowView.findViewById(R.id.tvVehicleNo);
        TextView  tvTime= (TextView) rowView.findViewById(R.id.tvtime);
        TextView  tvOutTime= (TextView) rowView.findViewById(R.id.tvouttime);
        TextView  tvOwenerName= (TextView) rowView.findViewById(R.id.tvOwenerName);
        if(UI.equals("0"))
            tvOutTime.setVisibility(View.GONE);
        tvFlatNo.setText(flat_no[position]);
        tvVehicleNo.setText(vehicle_no[position]);
        tvTime.setText(time[position]);
        tvOutTime.setText(out_time[position]);
        tvOwenerName.setText(owner_name[position]);
        return rowView;
    }
}