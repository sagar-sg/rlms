package com.rlms.adapters;

/**
 * Created by user on 22/2/17.
 */

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rlms.R;
import com.rlms.callback.OnUpdateStatusClickListener;
import com.rlms.callback.RecyclerViewItemClickListener;
import com.rlms.constants.Params;
import com.rlms.model.Complaint;
import com.rlms.utils.StringUtils;

import java.util.List;

public class ComplaintsRecyclerAdapter extends RecyclerView.Adapter<ComplaintsRecyclerAdapter.SimpleViewHolder> {
    private List<Complaint> mData;
    private Context context;
    private int viewId = 0;
    private RecyclerViewItemClickListener itemClkLstnr , getDirectionsClikListnr;
    private String TAG = "ComplaintsAdapter";
    private OnUpdateStatusClickListener onUpdateStatusClickListener;

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView complainTitleTv, addressTv, detailsTv, pendingTv,markAsResolvedTv,getDirectionsTv,updateStatusTv;
        ImageView statusIv;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            addressTv = (TextView) itemView.findViewById(R.id.address_tv);
            complainTitleTv = (TextView) itemView.findViewById(R.id.complaints_title_name_tv);
            detailsTv = (TextView) itemView.findViewById(R.id.details_tv);
            pendingTv = (TextView) itemView.findViewById(R.id.pending_tv);
            updateStatusTv = (TextView) itemView.findViewById(R.id.update_tv);
            statusIv = (ImageView) itemView.findViewById(R.id.status_iv);
            markAsResolvedTv = (TextView)itemView.findViewById(R.id.mark_as_resolve_tv);
            getDirectionsTv = (TextView)itemView.findViewById(R.id.get_directions_tv);
        }
    }
    public ComplaintsRecyclerAdapter(Context context, List<Complaint> arr, RecyclerViewItemClickListener itemClkLstnr,
                                     RecyclerViewItemClickListener getDirectionsClikListnr, OnUpdateStatusClickListener onUpdateStatusClickListener) {
        this.mData = arr;
        this.context = context;
        this.itemClkLstnr = itemClkLstnr;
        this.getDirectionsClikListnr = getDirectionsClikListnr;
        this.onUpdateStatusClickListener = onUpdateStatusClickListener;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.complaints_row_item, parent, false);

        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        if (mData.size() > 0) {
            final Complaint complain = mData.get(position);
//            if(record.isActive()) {
            holder.complainTitleTv.setText("" + complain.getCustomerName());
            holder.addressTv.setText("" + complain.getLiftNumber());
            holder.pendingTv.setText(StringUtils.getConvertedDate(complain.getRegistrationDate()));
            holder.detailsTv.setText(complain.getRemark());

            Log.d(TAG,"status ="+complain.getStatus()+ " position  = "+position);

            if(complain.getStatus().equalsIgnoreCase(Params.ASSIGNED)
                    || complain.getStatus().equalsIgnoreCase(Params.PENDING)){

                holder.statusIv.setImageResource(R.drawable.red_indicator);

                Log.d(TAG,"status assigned or pending");
                holder.markAsResolvedTv.setText(context.getString(R.string.mark_resolve));
                holder.markAsResolvedTv.setAlpha(1f);
                holder.markAsResolvedTv.setClickable(true);
                holder.markAsResolvedTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClkLstnr != null) {
                            itemClkLstnr.OnItemClick(view, position);
                        }
                    }
                });

                holder.getDirectionsTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getDirectionsClikListnr!=null){
                            getDirectionsClikListnr.OnItemClick(view,position);
                        }
                    }
                });

                holder.updateStatusTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onUpdateStatusClickListener!=null){
                            onUpdateStatusClickListener.OnItemClick(view,position);
                        }
                    }
                });

            }else{

                holder.statusIv.setImageResource(R.drawable.green_indicator);

                Log.d(TAG,"status not assigned or pending");
                holder.markAsResolvedTv.setText(context.getString(R.string.resolved));
                holder.markAsResolvedTv.setAlpha((float) 0.5);
                holder.markAsResolvedTv.setClickable(false);
                holder.markAsResolvedTv.setOnClickListener(null);
                holder.getDirectionsTv.setOnClickListener(null);

            }

            holder.updateStatusTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onUpdateStatusClickListener!=null){
                        onUpdateStatusClickListener.OnItemClick(view,position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

}
