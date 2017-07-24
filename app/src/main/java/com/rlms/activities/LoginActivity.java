package com.rlms.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.rlms.R;
import com.rlms.model.RLMSAPIResponse;
import com.rlms.model.RegisterTechnician;
import com.rlms.model.Technician;
import com.rlms.network.RetrofitBuilder;
import com.rlms.network.webapi.APIRegisterTechnician;
import com.rlms.permissions.LocationPermissions;
import com.rlms.utils.NetworkUtils;
import com.rlms.utils.Parser;
import com.rlms.utils.Preferences;
import com.rlms.utils.RLMSApplication;
import com.rlms.utils.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {

    @BindView(R.id.buttonSignin)
    Button buttonSignIn;
    @BindView(R.id.editTextMobile)
    EditText editTextMobile;
//    @BindView(R.id.editTextAddress)
//    EditText editTextAddress;
//    @BindView(R.id.textViewSignUp)
//    TextView textViewSignup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;
    private String TAG = "LoginActivity";
    LocationManager locationManager;
    double latitude = 0.0;
    double longitude = 0.0;
    String localityWithPincode = "";
    private Context mContext;
    RLMSApplication rlmsApplication;

    //Any random number you can take
    public static final int REQUEST_PERMISSION_LOCATION = 10;

    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    protected final static String KEY_LOCATION = "location";
    protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;


    // Labels.
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates = false;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;


    int RQS_GooglePlayServices = 0;

    @Override
    protected void onStart() {
        super.onStart();

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
            mGoogleApiClient.connect();
        } else {
            googleAPI.getErrorDialog(this, resultCode, RQS_GooglePlayServices);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                //  Toast.makeText(FusedLocationWithSettingsDialog.this, "location was already on so detecting location now", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
            }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = LoginActivity.this;

        Log.d(TAG,"12-May-2017 12:55:23 PM* Date time value = "+ StringUtils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString()));

        rlmsApplication = (RLMSApplication) getApplicationContext();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){

            //opening profile activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

            //close this activity
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // Kick off the process of building the GoogleApiClient, LocationRequest, and
        // LocationSettingsRequest objects.

        //step 1
        buildGoogleApiClient();

        //step 2
        createLocationRequest();

        //step 3
        buildLocationSettingsRequest();

        checkLocationSettings();

        editTextMobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i== EditorInfo.IME_ACTION_GO||i==R.id.buttonSignin){
                    return true;
                }
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);

    }

    @OnClick(R.id.buttonSignin)
    public void signInClik(){
        userLogin();
    }


    //method for user login
    private void userLogin(){
        final String mobileStr = editTextMobile.getText().toString().trim();
//        String password  = editTextPassword.getText().toString().trim();

        //checking if mobileStr and passwords are empty
        if(mobileStr.length() != 10){
            Toast.makeText(this,getString(R.string.pl_enter_mobile),Toast.LENGTH_LONG).show();
            return;
        }
//
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(this,getString(R.string.enter_password),Toast.LENGTH_LONG).show();
//            return;
//        }

        //if the mobileStr and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage(getString(R.string.singing_in));
        progressDialog.show();

//        //logging in the user
//        firebaseAuth.signInWithEmailAndPassword(mobileStr+"@test.com", ""+getString(R.string.app_name))
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        //if the task is successfull
//                        if(task.isSuccessful()){
//                            //start the profile activity
//                            Log.d(TAG,"fcm login success");
//                            callRegisterUserToServer(firebaseAuth.getCurrentUser());
//
//                        }else{
//                            Toast.makeText(mContext,getString(R.string.login_failed),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        firebaseAuth.createUserWithEmailAndPassword(mobileStr+"@testtech.com", "12345"+getString(R.string.app_name))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            Log.d(TAG,"fcm login success");
                            callRegisterUserToServer(firebaseAuth.getCurrentUser());

                        }else{
                            firebaseAuth.signInWithEmailAndPassword(mobileStr+"@testtech.com", "12345"+getString(R.string.app_name))
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                //start the profile activity
                                                Log.d(TAG, "fcm login success");
                                                callRegisterUserToServer(firebaseAuth.getCurrentUser());
                                            }else{
                                                Toast.makeText(mContext,getString(R.string.login_failed),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    /*
    * Call our app server to register technician with it
    * */
    private void callRegisterUserToServer(FirebaseUser currentUser) {

        Log.d(TAG, "callRegisterUserToServer currentUser id = " + currentUser.getUid()
                + " name = "+currentUser.getDisplayName()+" email = "+currentUser.getEmail()+
                " provide id = "+currentUser.getProviderId());

        APIRegisterTechnician apiRegisterTechnician = RetrofitBuilder.getClient().create(APIRegisterTechnician.class);

        RegisterTechnician registerTechnician = new RegisterTechnician();

        // hard core values
        registerTechnician.setAddress(""+localityWithPincode);
        registerTechnician.setAppRegId(currentUser.getUid());
        registerTechnician.setContactNumber(""+editTextMobile.getText().toString().trim());
        registerTechnician.setLongitude(latitude);
        registerTechnician.setLatitude(longitude);

        // call login api
        Call<RLMSAPIResponse> call = apiRegisterTechnician.callLoginUser(registerTechnician);

        Log.d(TAG, "apiRegisterTechnician url = " + call.request().url());

        call.enqueue(new Callback<RLMSAPIResponse>() {

            @Override
            public void onResponse(Call<RLMSAPIResponse> call, Response<RLMSAPIResponse> response) {

                progressDialog.dismiss();

                int statusCode = response.code();
                Log.d(TAG, "apiRegisterTechnician onResponse: statusCode " + statusCode);
                Log.d(TAG, "apiRegisterTechnician onResponse: message " + response.message());

                if (statusCode == 200) {

                    RLMSAPIResponse rlmsapiResponse = response.body();

                    if(rlmsapiResponse != null) {
                        Log.d(TAG, "string rlmsapiResponse = " + rlmsapiResponse.toString());
                        if (rlmsapiResponse.isStatus()) {
                            Log.d(TAG, "success registering user response = "+rlmsapiResponse.getResponse());
                            Technician technician = Parser.getParsedTechnician(rlmsapiResponse.getResponse());
                            Log.d(TAG, "success technician string = "+technician.toString());
                            new Preferences(mContext).storeTechnicianDetails(technician);
                            Toast.makeText(mContext, "" + getString(R.string.tech_registration_succes), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "rlmsapiResponse.isStatus() flag is false");
                            Toast.makeText(mContext, "" + rlmsapiResponse.getResponse(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.d(TAG, "flag false");
                        Toast.makeText(mContext, "" + getString(R.string.failed_to_register_tech), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d(TAG, "apiRegisterTechnician statusCode is not 200" + statusCode);
                    Toast.makeText(mContext, "" + mContext.getString(R.string.failed_to_register_tech), Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }

            @Override
            public void onFailure(Call<RLMSAPIResponse> call, Throwable t) {
                Log.d(TAG, "apiRegisterTechnician on failure wifi connect  = " + t.getMessage());
                progressDialog.dismiss();

                if (new NetworkUtils(mContext).isNetworkAvailable()) {

                    Toast.makeText(mContext, "" + mContext.getString(R.string.failed_to_register_tech), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.network_connection_error), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    //step 1
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //step 2
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    //step 3
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    //step 4

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }


    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            goAndDetectLocation();
        }

    }

    public void goAndDetectLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
                //     setButtonsEnabledState();
            }
        });
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
                //   setButtonsEnabledState();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goAndDetectLocation();
                }
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i(TAG, "Connected to GoogleApiClient");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateLocationParams();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateLocationParams();
//        Toast.makeText(this, getResources().getString(R.string.location_updated_message),
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    /**
     * Invoked when settings dialog is opened and action taken
     * @param locationSettingsResult
     *	This below OnResult will be used by settings dialog actions.
     */

    //step 5
    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {

        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");

//                Toast.makeText(LoginActivity.this, "Location is already on.", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
//                    Toast.makeText(LoginActivity.this, "Location dialog will be open", Toast.LENGTH_SHORT).show();
                    //

                    //move to step 6 in onActivityResult to check what action user has taken on settings dialog
                    status.startResolutionForResult(LoginActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }


    /**
     *	This OnActivityResult will listen when
     *	case LocationSettingsStatusCodes.RESOLUTION_REQUIRED: is called on the above OnResult
     */
    //step 6:

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }
    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationParams() {
        if (mCurrentLocation != null) {
            latitude =  mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
//            mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
//                    mLastUpdateTime));

            updateCityAndPincode();
        }
    }


    /**
     *	This updateCityAndPincode method uses Geocoder api to map the latitude and longitude into city location or pincode.
     *	We can retrieve many details using this Geocoder class.
     *
     And yes the Geocoder will not work unless you have data connection or wifi connected to internet.
     */




    private void updateCityAndPincode()
    {
        try
        {
            Geocoder gcd = new Geocoder(LoginActivity.this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0)
            {
                localityWithPincode = "City="+addresses.get(0).getLocality()+"\n"+
                                      "Pincode="+addresses.get(0).getPostalCode();
                //  System.out.println(addresses.get(0).getLocality());
            }

        }

        catch (Exception e)
        {
            Log.e(TAG,"exception:"+e.toString());
        }

    }


}
