package com.rlms.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rlms.R;
import com.rlms.callback.OnDateAndTimeSelectedListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateAndTimePickerDialog extends Dialog {

    String TAG = "DateAndTimePickerDialog";

    @BindView(R.id.remarks_et)
    EditText editText;
    @BindView(R.id.fromDateTv)
    TextView fromDateTv;
    @BindView(R.id.toDateTv)
    TextView toDateTv;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;
    @BindView(R.id.updateBtn)
    Button updateBtn;
    @BindView(R.id.selectFromDateBtn)
    Button selectFromDateBtn;
    @BindView(R.id.selectFromTimeBtn)
    Button selectFromTimeBtn;
    @BindView(R.id.selectToDateBtn)
    Button selectToDateBtn;
    @BindView(R.id.selectToTimeBtn)
    Button selectToTimeBtn;

    private Context context;
    String remarks = "";
    private String fromDateStr = "",fromTimeStr = "", toDateStr = "", toTimeStr = "";
    private OnDateAndTimeSelectedListener onDateAndTimeSelectedListener;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DateAndTimePickerDialog(Context context, OnDateAndTimeSelectedListener onDateAndTimeSelectedListener) {
        super(context);
        this.context  = context;
        this.onDateAndTimeSelectedListener = onDateAndTimeSelectedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.manual_remarks_entry_view);

        ButterKnife.bind(this);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Calendar calendar = Calendar.getInstance();
        // Get the current hour and minute
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        selectFromDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
//                12-May-2017 12:55:23 PM
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                fromDateStr = dayOfMonth + "-" + MONTHS[monthOfYear] + "-" + year;
                                Log.d(TAG,"fromDateStr = "+fromDateStr);
                                fromDateTv.setText(""+fromDateStr+" "+ fromTimeStr);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        selectToDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog1 = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                toDateStr = dayOfMonth + "-" + MONTHS[monthOfYear] + "-" + year;
                                Log.d(TAG,"toDateStr = "+toDateStr);
                                toDateTv.setText(""+toDateStr+" "+ toTimeStr);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog1.show();

            }
        });

        selectFromTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TimePickerDialog Theme : THEME_HOLO_LIGHT
                TimePickerDialog tpd = new TimePickerDialog(context,
                        0,fromTimeListener,hour,minute,false);
                tpd.show();
            }
        });

        selectToTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// TimePickerDialog Theme : THEME_HOLO_LIGHT
                TimePickerDialog tpd = new TimePickerDialog(context,
                        0,toTimeListener,hour,minute,false);
                tpd.show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remarks = editText.getText().toString().trim();

                if(remarks.length()!=0) {

                    if(fromDateStr.length()==0) {
                        Toast.makeText(context,"Please select fromDate",Toast.LENGTH_SHORT).show();;
                        return;
                    }
                    if(fromTimeStr.length()==0) {
                        Toast.makeText(context,"Please select fromTime",Toast.LENGTH_SHORT).show();;
                        return;
                    }
                    if(toTimeStr.length()==0) {
                        Toast.makeText(context,"Please select toTime",Toast.LENGTH_SHORT).show();;
                        return;
                    }
                    if(toDateStr.length()==0) {
                        Toast.makeText(context,"Please select toDate",Toast.LENGTH_SHORT).show();;
                        return;
                    }

                    onDateAndTimeSelectedListener.onDateAndTimeSelected(fromDateStr, fromTimeStr, toDateStr, toTimeStr,remarks );
                    dismiss();
                }else{
                    Toast.makeText(context,"Please enter remarks",Toast.LENGTH_SHORT).show();;
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


    TimePickerDialog.OnTimeSetListener fromTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

            fromTimeStr = getAMPMString(hourOfDay,minute);
            Log.d(TAG,"fromTimeStr = "+fromTimeStr);
            fromDateTv.setText(""+fromDateStr+" "+ fromTimeStr);

        }
    };

    TimePickerDialog.OnTimeSetListener toTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

            toTimeStr = getAMPMString(hourOfDay,minute);
            Log.d(TAG,"toTimeStr = "+toTimeStr);
            toDateTv.setText(""+toDateStr+" "+ toTimeStr);

        }
    };

    public String getAMPMString(int hourOfDay, int minute) {

        // Set a variable to hold the current time AM PM Status
        // Initially we set the variable value to AM
        String status = "AM";

        if (hourOfDay > 11) {
            // If the hour is greater than or equal to 12
            // Then the current AM PM status is PM
            status = "PM";
        }

        // Initialize a new variable to hold 12 hour format hour value
        int hour_of_12_hour_format = 0;

        if (hourOfDay > 11) {

            // If the hour is greater than or equal to 12
            // Then we subtract 12 from the hour to make it 12 hour format time
            hour_of_12_hour_format = hourOfDay - 12;
        } else {
            hour_of_12_hour_format = hourOfDay;
        }

        // Get the calling activity TextView reference
//            TextView tv = (TextView) getActivity().findViewById(R.id.tv);
        // Display the 12 hour format time in app interface
//            tv.setText(hour_of_12_hour_format + " : " + minute + " : " + status);
//        String.format("%02d:%02d", hourOfDay, minute)
        return String.format("%02d:%02d",hour_of_12_hour_format , minute) + ":" +"00"+" "+ status;

    }

}
