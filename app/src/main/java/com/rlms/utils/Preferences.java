package com.rlms.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rlms.constants.AppConstants;
import com.rlms.constants.Params;
import com.rlms.model.Technician;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    private SharedPreferences preferences;
    Context mContext;

    public Preferences(Context context) {

        preferences = context.getSharedPreferences(AppConstants.PREF_FILE_NAME,MODE_PRIVATE);
        mContext = context;
    }

    public void storeTechnicianDetails(Technician technician){

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Params.USER_ID_TECH, technician.getUserId()).apply();
        editor.putString(Params.USER_NAME_TECH, technician.getUserRoleName()).apply();
        editor.putInt(Params.USER_ROLE_ID_TECH, technician.getUserRoleId()).apply();
        editor.putString(Params.FISRT_NAME_TECH, technician.getFirstName()).apply();
        editor.putString(Params.LAST_NAME_TECH, technician.getLastName()).apply();
        editor.putString(Params.BRANCH_NAME_TECH, technician.getBranchCompanyMapId()).apply();
        editor.putString(Params.BRANCH_NAME_TECH, technician.getBranchName()).apply();
        editor.putString(Params.COMPANY_NAME_TECH, technician.getCompanyName()).apply();
        editor.putString(Params.CONTACT_NO, technician.getContactNumber()).apply();
        editor.putString(Params.ADD_REG_ID, technician.getAppRegId()).apply();
        editor.putString(Params.ADDRESS, technician.getAddress()).apply();
        editor.putString(Params.EMAILID, technician.getEmailId()).apply();
        editor.putString(Params.COMNY_ID, technician.getCompanyId()).apply();

        editor.commit();

    }

    /**
     *Stored
     * @return Technician
     */
    public Technician getStoreTechnician() {

        Technician technician = new Technician();

        technician.setUserRoleName(preferences.getString(Params.USER_NAME_TECH, ""));
        technician.setUserId(preferences.getString(Params.USER_ID_TECH, ""));
        technician.setUserRoleId(preferences.getInt(Params.USER_ROLE_ID_TECH, 0));
        technician.setFirstName(preferences.getString(Params.FISRT_NAME_TECH, ""));
        technician.setLastName(preferences.getString(Params.LAST_NAME_TECH, ""));
        technician.setBranchCompanyMapId(preferences.getString(Params.BRANCH_COMP_ID, ""));
        technician.setBranchName(preferences.getString(Params.BRANCH_NAME_TECH, ""));
        technician.setCompanyName(preferences.getString(Params.COMPANY_NAME_TECH, ""));
        technician.setContactNumber(preferences.getString(Params.CONTACT_NO, ""));
        technician.setAppRegId(preferences.getString(Params.ADD_REG_ID, ""));
        technician.setAddress(preferences.getString(Params.ADDRESS, ""));
        technician.setEmailId(preferences.getString(Params.EMAILID, ""));
        technician.setCompanyId(preferences.getString(Params.COMNY_ID, ""));

        return technician;
    }

}
