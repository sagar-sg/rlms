package com.rlms.callback;

import com.rlms.model.Complaint;
import com.rlms.model.Complaints;

import java.util.ArrayList;
import java.util.List;

public interface OnFetchItemsFinishCallback {

//    public void onFetchItemsCallback(List<Complaints> fetchedComplaintsList);

    public void onFetchItemsCallback(List<Complaint> fetchedComplaintsList);

}