package com.rlms.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rlms.R;
import com.rlms.adapters.VisitDetailsListAdapter;
import com.rlms.callback.OnDateAndTimeSelectedListener;
import com.rlms.callback.RecyclerViewItemClickListener;
import com.rlms.constants.Params;
import com.rlms.dialogs.DateAndTimePickerDialog;
import com.rlms.model.Complaint;
import com.rlms.model.GetVisitDetails;
import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.SaveVisitDetails;
import com.rlms.model.Technician;
import com.rlms.model.VisitDetails;
import com.rlms.model.VisitDetailsResponse;
import com.rlms.network.RetrofitBuilder;
import com.rlms.network.webapi.APIGetSiteVisitDetails;
import com.rlms.network.webapi.APIToMarkAsResolved;
import com.rlms.network.webapi.APIUpdateVisitDetails;
import com.rlms.utils.Log;
import com.rlms.utils.NetworkUtils;
import com.rlms.utils.Parser;
import com.rlms.utils.Preferences;
import com.rlms.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Swapnil on 4/12/2017.
 */

public class UpdateStatusActivity extends AppCompatActivity implements OnDateAndTimeSelectedListener,RecyclerViewItemClickListener{

    @BindView(R.id.start_visit_btn)
    Button startVisitBtn;
    @BindView(R.id.mark_resolve_btn)
    Button markAsResolveBtn;
    @BindView(R.id.edit_description)
    EditText editTextDescription;
    @BindView(R.id.edit_title)
    EditText editTextTitle;
    @BindView(R.id.visit_details_list)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<VisitDetails> visitDetailsArrayList = new ArrayList<>();

    private String TAG = "UpdateStatusActivity";
    private Context mContext;
    private com.rlms.utils.Log Log = new Log();
    private ProgressDialog mProgressDialog;
    private NetworkUtils mNetworkUtils;
    String complaintTechMapId = "";
    String userEnteredRemarks = "";
    Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        mContext = UpdateStatusActivity.this;
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            complaintTechMapId = extras.getString("complaintTechMapId", "" + 0);
            complaint = (Complaint) extras.getSerializable("Complaint");
        }


        // Initialize progress dialog
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);


        // Initialize progress dialog
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mNetworkUtils = new NetworkUtils(mContext);

        // adapter for recyclerview to display items for members with sections
        mAdapter = new VisitDetailsListAdapter(mContext, visitDetailsArrayList,this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mProgressDialog = new ProgressDialog(this);
        startVisitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                createManualRemarkEntryDialog();
//                callUpdateVisitAPI();
                DateAndTimePickerDialog dateAndTimePickerDialog = new DateAndTimePickerDialog(mContext,UpdateStatusActivity.this);
                dateAndTimePickerDialog.show();

            }
        });


        getAllVisitDetails();

    }

    @OnClick(R.id.mark_resolve_btn)
    public void markAsResolveBtnClick(View v) {
        markAsResolvedCall(complaint);
    }

    /*
    * Call API to update visit details to server
    * */
    private void callUpdateVisitAPI(String fromDateStr, String toDateStr) {

        mProgressDialog.setMessage(mContext.getString(R.string.loading_complaints));
        mProgressDialog.show();

        APIUpdateVisitDetails apiUpdateVisitDetails = RetrofitBuilder.getClient().create(APIUpdateVisitDetails.class);

        Preferences pref = new Preferences(mContext);
        Technician technician = pref.getStoreTechnician();
        int userRoleID = 0;
        if (technician != null) {
            userRoleID = technician.getUserRoleId();
        }
//        CCA1590999
        SaveVisitDetails saveVisitDetails = new SaveVisitDetails();
        saveVisitDetails.setUserRoleId("" + userRoleID);

        saveVisitDetails.setComplaintTechMapId(complaintTechMapId);
//        saveVisitDetails.setFromDateDtr("" + StringUtils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString()));
//        saveVisitDetails.setToDateStr("" + StringUtils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString()));
        saveVisitDetails.setFromDateDtr(fromDateStr);
        saveVisitDetails.setToDateStr(toDateStr);
        saveVisitDetails.setRemark(userEnteredRemarks);

        Call<VisitDetailsResponse> call = apiUpdateVisitDetails.validateAndSaveSiteVisitDetails(saveVisitDetails);
        Log.err(TAG, "apiUpdateVisitDetails send call: " + call.request().url() + " mRetry:");

        call.enqueue(new Callback<VisitDetailsResponse>() {

            @Override
            public void onResponse(Call<VisitDetailsResponse> call, Response<VisitDetailsResponse> response) {

                mProgressDialog.dismiss();

                int statusCode = response.code();
                VisitDetailsResponse visitDetailsResponse = response.body();

                Log.err(TAG, "apiUpdateVisitDetails onResponse: statusCode " + statusCode);
                Log.err(TAG, "apiUpdateVisitDetails onResponse: message " + response.message());
                Log.d(TAG, "apiUpdateVisitDetails visitDetailsResponse = " + visitDetailsResponse.toString());

                if (statusCode == 200 || statusCode == 201) {

                    Log.d(TAG, "success apiUpdateVisitDetails updated visit details");
                    Toast.makeText(mContext, "Successfully updated visit details", Toast.LENGTH_SHORT).show();
                    getAllVisitDetails();

//                    if (visitDetailsResponse.isStatus()) {
//
//                        if (visitDetailsResponse.getResponse().length() != 0) {
//
//                            Log.d(TAG, "not != 0 complaints response string = " + visitDetailsResponse.getResponse());
//
//                        }else{
//                            Toast.makeText(mContext, "Empty reponse", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
//                    }

                } else {
                    Log.err(TAG, "not 200 something went wrong");
                    Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<VisitDetailsResponse> call, Throwable t) {

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

    /*
    * Call API to get all visit details
    * */
    private void getAllVisitDetails() {

        mProgressDialog.setMessage("Fetching all visit details...");
        mProgressDialog.show();

        APIGetSiteVisitDetails apiGetSiteVisitDetails = RetrofitBuilder.getClient().create(APIGetSiteVisitDetails.class);

        Preferences pref = new Preferences(mContext);
        Technician technician = pref.getStoreTechnician();
        int userRoleID = 0;
        if (technician != null) {
            userRoleID = technician.getUserRoleId();
        }

        GetVisitDetails getVisitDetails = new GetVisitDetails();
        getVisitDetails.setComplaintTechMapId(complaintTechMapId);
        getVisitDetails.setUserRoleId("" + userRoleID);
        getVisitDetails.setRemark("get site details");

        Call<VisitDetailsResponse> call = apiGetSiteVisitDetails.getAllComplaintsFor(getVisitDetails);
        Log.err(TAG, "apiGetSiteVisitDetails send call: " + call.request().url() + " mRetry:");

        call.enqueue(new Callback<VisitDetailsResponse>() {

            @Override
            public void onResponse(Call<VisitDetailsResponse> call, Response<VisitDetailsResponse> response) {

                mProgressDialog.dismiss();

                int statusCode = response.code();
                VisitDetailsResponse visitDetailsResponse = response.body();

                Log.err(TAG, "apiGetSiteVisitDetails onResponse: statusCode " + statusCode);
                Log.err(TAG, "apiGetSiteVisitDetails onResponse: message " + response.message());
                Log.d(TAG, "apiGetSiteVisitDetails visitDetailsResponse = " + visitDetailsResponse.toString());

                if (statusCode == 200 || statusCode == 201) {

                    Log.d(TAG, "success apiGetSiteVisitDetails updated visit details");
//                    Toast.makeText(mContext, "Successfully fetched visit details", Toast.LENGTH_SHORT).show();

                    if (visitDetailsResponse.isStatus()) {

                        if (visitDetailsResponse.getResponse().length() != 0) {

                            Log.d(TAG, "not != 0 visitDetailsResponse response string = " + visitDetailsResponse.getResponse());

                            visitDetailsArrayList = Parser.getParsedVisitDetailsList(visitDetailsResponse.getResponse());
                            mAdapter = new VisitDetailsListAdapter(mContext, visitDetailsArrayList,UpdateStatusActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(mContext, "Empty reponse", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<VisitDetailsResponse> call, Throwable t) {

                mProgressDialog.dismiss();

                Log.d(TAG, "on failure getting visitDetailsResponse value  = " + t.getMessage());
                t.printStackTrace();
                if (mNetworkUtils.isNetworkAvailable()) {

                    Toast.makeText(mContext, "" + getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.network_connection_error), Toast.LENGTH_SHORT).show();

                }
            }
        });

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


    public void createManualRemarkEntryDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(UpdateStatusActivity.this).create();

        LayoutInflater inflater = getLayoutInflater();
        //this is what I did to added the layout to the alert dialog
        View layout = inflater.inflate(R.layout.manual_remarks_entry_view, null);
        View layoutTitle = inflater.inflate(R.layout.manual_remarks_entry_title, null);
        alertDialog.setCustomTitle(layoutTitle);
        alertDialog.setView(layout);
        final EditText remarksEt = (EditText) layout.findViewById(R.id.remarks_et);
        remarksEt.postDelayed(new Runnable() {

            public void run() {
                remarksEt.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                remarksEt.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 200);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        String remarksEntered = remarksEt.getText().toString();
                        if (remarksEntered.length() > 0) {
                            userEnteredRemarks = remarksEntered;
//                            callUpdateVisitAPI();
                        }
                        hideSofKeyBoard(UpdateStatusActivity.this);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        hideSofKeyBoard(UpdateStatusActivity.this);
                    }
                });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                hideSofKeyBoard(UpdateStatusActivity.this);
            }
        });
        alertDialog.show();


    }

    /**
     * A method to hide soft keys.
     */
    public void hideSofKeyBoard(Activity act) {
        try {
            InputMethodManager imm = (InputMethodManager) act
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(act.getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDateAndTimeSelected(String fromDateStr, String fromTimeStr, String toDateStr, String toTimeStr, String remarks) {

        Log.d(TAG," fromDateStr = "+fromDateStr+" fromTimeStr = "+fromTimeStr+" toDateStr = "+toDateStr+" toTimeStr = "+toTimeStr);
        Log.d(TAG,"from date = |"+fromDateStr+" "+fromTimeStr);
        Log.d(TAG,"to date = |"+toDateStr+" "+toTimeStr);
        this.userEnteredRemarks = remarks;

        callUpdateVisitAPI(fromDateStr+" "+fromTimeStr,toDateStr+" "+toTimeStr);

    }

    @Override
    public void OnItemClick(View v, int position) {

//        VisitDetails visitDetails = visitDetailsArrayList.get(position);
//
//        Log.d(TAG,"clicked at pos = "+position+" tostring = "+visitDetails.toString());
//
//        callUpdateVisitAPI(visitDetails.getFromDateDtr(),visitDetails.getToDateStr());

    }
}
