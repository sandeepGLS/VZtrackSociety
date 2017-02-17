package vztrack.gls.com.vztracksociety.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

import vztrack.gls.com.vztracksociety.BaseActivity;
import vztrack.gls.com.vztracksociety.R;
import vztrack.gls.com.vztracksociety.SearchActivity;
import vztrack.gls.com.vztracksociety.beans.VisitorBean;
import vztrack.gls.com.vztracksociety.profile.SheredPref;
import vztrack.gls.com.vztracksociety.utils.CallFor;
import vztrack.gls.com.vztracksociety.utils.CheckConnection;
import vztrack.gls.com.vztracksociety.utils.GetData;

public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    public  static String nameForOut;
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView name;
        TextView flat;
        TextView mobile;
        TextView vehicle;
        TextView time;

        Button buttonDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            flat = (TextView) itemView.findViewById(R.id.tv_flat);
            mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            vehicle = (TextView) itemView.findViewById(R.id.tv_vehicle_no);
            time = (TextView) itemView.findViewById(R.id.tv_in_time);

            buttonDelete = (Button) itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                    Toast.makeText(view.getContext(), "Exit Visitor : " + name.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Context mContext;
    private ArrayList<VisitorBean> mDataset;

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public RecyclerViewAdapter(Context context, ArrayList<VisitorBean> objects) {
        this.mContext = context;
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final int id = mDataset.get(position).getVisitor_id();
        final String f_name = mDataset.get(position).getFirst_name();
        final String l_name = mDataset.get(position).getLast_name();
        final String name_hindi = mDataset.get(position).getNameInHindi();
        final int total_visitors = mDataset.get(position).getNo_of_visitors();
        String flatno = mDataset.get(position).getFlat_no();
        String mobile = mDataset.get(position).getMobile_no();
        String vehicle = mDataset.get(position).getVehicle_no();
        String time = mDataset.get(position).getIn_time();
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                //layout.findViewById(R.id.exitVisitorName).settext;
                TextView tv = (TextView) layout.findViewById(R.id.exitVisitorName);
                tv.setText("Exit Visitor\n"+f_name +" "+l_name+ "-" +name_hindi+"?");
            }
        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                //Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckConnection  cc = new CheckConnection(mContext);
                if (cc.isConnectingToInternet()) {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mDataset.size());
                    mItemManger.closeAllItems();
                    new GetData((BaseActivity) mContext, CallFor.EXIT_VISITOR, ""+id).execute();
                    nameForOut = viewHolder.name.getText().toString();
                    int cnt = getItemCount();
                    if(cnt==0 && SearchActivity.ShowNoVisitor==1){
                        SearchActivity.hideUI();
                    }
                }
                else{
                    ShowToast("Please check internet connection\nइंटरनेट कनेक्शन की जाँच करें");
                }
            }
        });

        if(!SheredPref.getType(mContext).equals("0")){
            viewHolder.name.setText(f_name+" "+l_name+"\n"+total_visitors);
        }
        else{
            viewHolder.name.setText(f_name+" "+l_name+"\n"+name_hindi);
        }
        viewHolder.flat.setText(flatno);
        viewHolder.mobile.setText(mobile);
        viewHolder.vehicle.setText(vehicle);
        viewHolder.time.setText(time);
        mItemManger.bind(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void ShowToast(String Text) {
        Toast toast = Toast.makeText(mContext, Text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_bg);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setGravity(Gravity.CENTER);
        toastTV.setTextSize(20);
        toastTV.setTypeface(null, Typeface.BOLD);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
