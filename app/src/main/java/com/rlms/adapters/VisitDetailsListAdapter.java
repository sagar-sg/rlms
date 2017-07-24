package com.rlms.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rlms.R;
import com.rlms.callback.RecyclerViewItemClickListener;
import com.rlms.constants.Params;
import com.rlms.model.Complaint;
import com.rlms.model.VisitDetails;
import com.rlms.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swapnil on 4/12/2017.
 */

public class VisitDetailsListAdapter extends RecyclerView.Adapter<VisitDetailsListAdapter.SimpleViewHolder> {
    //    private List<Complaint> mData;
    Context context;
    int viewId = 0;
    String TAG = "VisitDetailsListAdapter";
    private RecyclerViewItemClickListener itemClkLstnr;
    ArrayList<VisitDetails> visitDetailsArrayList;

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView fromDateTv, toDateTv, remarksTv,totalTimeTv;
        ImageView statusIv;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            toDateTv = (TextView) itemView.findViewById(R.id.to_date_value_tv);
            fromDateTv = (TextView) itemView.findViewById(R.id.from_date_value_tv);
            remarksTv = (TextView) itemView.findViewById(R.id.remark_value_tv);
            totalTimeTv = (TextView) itemView.findViewById(R.id.total_time_value_tv);
        }
    }

    public VisitDetailsListAdapter(Context context, ArrayList<VisitDetails> visitDetailsArrayList,RecyclerViewItemClickListener itemClkLstnr) {

        this.context = context;
        this.visitDetailsArrayList = visitDetailsArrayList;
        this.itemClkLstnr = itemClkLstnr;
    }

    @Override
    public VisitDetailsListAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.site_visit_details_row_item, parent, false);

        return new VisitDetailsListAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VisitDetailsListAdapter.SimpleViewHolder holder, final int position) {


        VisitDetails visitDetails = visitDetailsArrayList.get(position);

        holder.fromDateTv.setText(""+visitDetails.getFromDateDtr());
        holder.toDateTv.setText(""+visitDetails.getToDateStr());
        holder.remarksTv.setText(""+visitDetails.getRemark());
        holder.totalTimeTv.setText(""+visitDetails.getTotalTime());

        holder.fromDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClkLstnr.OnItemClick(view,position);
            }
        });

        holder.toDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClkLstnr.OnItemClick(view,position);
            }
        });

        holder.remarksTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClkLstnr.OnItemClick(view,position);
            }
        });

        holder.totalTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClkLstnr.OnItemClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
//        if (mData == null) {
//            return 0;
//        }
        return visitDetailsArrayList.size();
    }

}

