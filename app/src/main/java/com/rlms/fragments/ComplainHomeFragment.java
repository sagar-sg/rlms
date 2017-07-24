package com.rlms.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rlms.R;
import com.rlms.activities.MainActivity;
import com.rlms.activities.MapsActivity;
import com.rlms.activities.UpdateStatusActivity;
import com.rlms.adapters.ComplaintsRecyclerAdapter;
import com.rlms.callback.OnFetchItemsFinishCallback;
import com.rlms.callback.OnUpdateStatusClickListener;
import com.rlms.callback.RecyclerViewItemClickListener;
import com.rlms.constants.Params;
import com.rlms.customviews.EmptySupportRecyclerView;
import com.rlms.model.Complaint;
import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.Technician;
import com.rlms.model.UserRole;
import com.rlms.network.RetrofitBuilder;
import com.rlms.network.webapi.APIGetComplaints;
import com.rlms.network.webapi.APIToMarkAsResolved;
import com.rlms.utils.Log;
import com.rlms.utils.NetworkUtils;
import com.rlms.utils.Parser;
import com.rlms.utils.Preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainHomeFragment extends Fragment implements OnFetchItemsFinishCallback, OnUpdateStatusClickListener {

    @BindView(R.id.recyclerView1)
    EmptySupportRecyclerView complaintsRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyViewTv;
    private Unbinder unbinder;

    private static final String TAG = "ComplainHomeFragment";
    private ArrayList<Complaint> complaintsArrayList = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    ComplaintsRecyclerAdapter mAdapter;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    Log Log = new Log();
    private NetworkUtils mNetworkUtils;

    public ComplainHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        mNetworkUtils = new NetworkUtils(mContext);

        View v = inflater
                .inflate(R.layout.fragment_complaint, container, false);
        unbinder = ButterKnife.bind(this, v);
        gridLayoutManager = new GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false);
        complaintsRecyclerView.setHasFixedSize(true);
        complaintsRecyclerView.setLayoutManager(gridLayoutManager);
        complaintsRecyclerView.setEmptyView(emptyViewTv);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

        fetchComplaints();
    }

    private void fetchComplaints() {

        mProgressDialog.setMessage(mContext.getString(R.string.loading_complaints));
        mProgressDialog.show();

        APIGetComplaints apiGetComplaints = RetrofitBuilder.getClient().create(APIGetComplaints.class);

        Preferences pref = new Preferences(getActivity());
        Technician technician = pref.getStoreTechnician();
        int userRoleID =0;
        if(technician!=null) {
            userRoleID = technician.getUserRoleId();
        }

        Call<RLMSAPIResponse> call = apiGetComplaints.getAllComplaintsFor(new UserRole(""+userRoleID));
        Log.err(TAG, "apiGetComplaints send call: " + call.request().url() + " mRetry:");

        call.enqueue(new Callback<RLMSAPIResponse>() {

            @Override
            public void onResponse(Call<RLMSAPIResponse> call, Response<RLMSAPIResponse> response) {

                mProgressDialog.dismiss();

                int statusCode = response.code();
                RLMSAPIResponse RLMSAPIResponse = response.body();

                Log.err(TAG, "send onResponse: statusCode " + statusCode);
                Log.err(TAG, "send onResponse: message " + response.message());
                Log.d(TAG, "success RLMSAPIResponse = " + RLMSAPIResponse.toString());

                if (statusCode == 200 || statusCode == 201) {

                    Log.d(TAG, "success fecthed complaints");
//                    Toast.makeText(mContext, "" + getString(R.string.successfully_fetched_complaints), Toast.LENGTH_SHORT).show();

                    if (RLMSAPIResponse.isStatus()) {

                        if (RLMSAPIResponse.getResponse().length() != 0) {

                            Log.d(TAG, "not != 0 complaints response string = " + RLMSAPIResponse.getResponse());
                            complaintsArrayList = Parser.getParsedComplaintsList(RLMSAPIResponse.getResponse());
                            mAdapter = new ComplaintsRecyclerAdapter(mContext, complaintsArrayList, listItemClikListnr, getDirectionsClikListnr, ComplainHomeFragment.this);
                            complaintsRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        }
                    } else {
//                        Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.err(TAG, "not 200 something went wrong");
                    Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RLMSAPIResponse> call, Throwable t) {

                mProgressDialog.dismiss();

                Log.d(TAG, "on failure getting complaints value  = " + t.getMessage());
                t.printStackTrace();
                if (mNetworkUtils.isNetworkAvailable()) {

                    Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.network_connection_error), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onFetchItemsCallback(List<Complaint> fetchedComplaintsList) {

        Log.d(TAG, "onFetchItemsCallbackcalled fetchedCategoryList size = " + fetchedComplaintsList.size());
        complaintsArrayList = (ArrayList<Complaint>) fetchedComplaintsList;
        mAdapter = new ComplaintsRecyclerAdapter(mContext, complaintsArrayList, listItemClikListnr, getDirectionsClikListnr, ComplainHomeFragment.this);
        complaintsRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    RecyclerViewItemClickListener listItemClikListnr = new RecyclerViewItemClickListener() {
        @Override
        public void OnItemClick(View v, int position) {
            Log.err(TAG, "mark as resolved clicked");

//            markAsResolvedCall(complaintsArrayList.get(position));
        }
    };

    RecyclerViewItemClickListener getDirectionsClikListnr = new RecyclerViewItemClickListener() {
        @Override
        public void OnItemClick(View v, int position) {
            Log.err(TAG, "getDirections clicked");

            String lat = "0.0", lng = "0.0";
            lat = complaintsArrayList.get(position).getLatitude();
            lng = complaintsArrayList.get(position).getLongitude();
            if (lat.equalsIgnoreCase("")) {
                lat = "0.0";
            }
            if (lng.equalsIgnoreCase("")) {
                lng = "0.0";
            }
            Intent launchMap = new Intent(getActivity(), MapsActivity.class);
            launchMap.putExtra("latitude", lat);
            launchMap.putExtra("longitude", lng);
            startActivity(launchMap);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void markAsResolvedCall(Complaint complaint) {

        mProgressDialog.setMessage(mContext.getString(R.string.updating_status));
        mProgressDialog.show();

        Log.d(TAG, "markAsResolvedCall complaint.getComplaintId() = " + complaint.getComplaintId());

        APIToMarkAsResolved markAsResolvedApi = RetrofitBuilder.getClient().create(APIToMarkAsResolved.class);

        Call<RLMSAPIResponse> call = markAsResolvedApi.updateComplaintStatus(new Complaint(complaint.getComplaintId(), Params.COMPLETED));
        Log.err(TAG, "markAsResolvedApi send call: " + call.request().url() + " mRetry:");

        call.enqueue(new Callback<RLMSAPIResponse>() {

            @Override
            public void onResponse(Call<RLMSAPIResponse> call, Response<RLMSAPIResponse> response) {

                mProgressDialog.dismiss();

                int statusCode = response.code();
                RLMSAPIResponse RLMSAPIResponse = response.body();

                Log.err(TAG, "send onResponse: statusCode " + statusCode);
                Log.err(TAG, "send onResponse: message " + response.message());
                Log.d(TAG, "success RLMSAPIResponse = " + RLMSAPIResponse.toString());

                if (statusCode == 200 || statusCode == 201) {

                    Log.d(TAG, "success fetched complaints");

                    if (RLMSAPIResponse.getResponse().length() != 0) {
                        if (RLMSAPIResponse.isStatus() == true) {

                            Log.d(TAG, "not != 0 complaints response string = " + RLMSAPIResponse.getResponse());
                            Toast.makeText(mContext, "" + RLMSAPIResponse.getResponse(), Toast.LENGTH_SHORT).show();
                            fetchComplaints();

                        } else {
                            Log.err(TAG, "not true something went wrong");
                            Toast.makeText(mContext, "" + RLMSAPIResponse.getResponse(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.err(TAG, "not 200 something went wrong");
                    Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RLMSAPIResponse> call, Throwable t) {

                mProgressDialog.dismiss();

                Log.d(TAG, "on failure getting complaints value  = " + t.getMessage());
                t.printStackTrace();
                if (mNetworkUtils.isNetworkAvailable()) {

                    Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.network_connection_error), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.complaints_menu, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Toast.makeText(getActivity(), "Profile is clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_filter) {
            Toast.makeText(getActivity(), "Filter is clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(View v, int position) {

        Log.d(TAG,"on update item clicked pos = "+position);
        //opening profile activity
        startActivity(new Intent(getActivity(), UpdateStatusActivity.class)
                .putExtra("complaintTechMapId",""
                        +complaintsArrayList.get(position).getComplaintTechMapId())
        .putExtra("Complaint", (Serializable) complaintsArrayList.get(position)));


    }
}
