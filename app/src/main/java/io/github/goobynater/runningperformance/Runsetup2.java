package io.github.goobynater.runningperformance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.SearchableInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
















public class Runsetup2 extends AppCompatActivity {

    static SharedPreferences timespreference;
    int yearx, monthx, dayx;
    static final int DIALOGID = 0;
    EditText getTimeDate;
    CheckBox fourcb, eightcb, sixteencb, thirtytwocb, fivecb;
    Button submitbtn;
    static final String COMP_DAY = "competition day";
    static final String COMP_MONTH = "competition month";
    static final String COMP_YEAR = "competition year";
    static final String EVENT_400M = "400m";
    static final String EVENT_800M = "800m";
    static final String EVENT_1600M = "1600m";
    static final String EVENT_3200M = "3200m";
    static final String EVENT_5000M = "5000m";
    Runsetup2 sInstance;
    boolean compset;
    CheckBox[] cbarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runsetup2);
        sInstance = this;

        final Calendar cal = Calendar.getInstance();
        yearx = cal.get(Calendar.YEAR);
        monthx = cal.get(Calendar.MONTH);
        dayx = cal.get(Calendar.DAY_OF_MONTH);
        CheckBox fourcb = (CheckBox)findViewById(R.id.fourcheck);
        CheckBox eightcb = (CheckBox)findViewById(R.id.eightcheck);
        CheckBox sixteencb = (CheckBox)findViewById(R.id.sixteencheck);
        CheckBox thirtytwocb = (CheckBox)findViewById(R.id.thirtytwocheck);
        CheckBox fivecb = (CheckBox)findViewById(R.id.fivecheck);
        final Button submitbtn = (Button)findViewById(R.id.submitbtn);

        submitbtn.setEnabled(false);



        cbarray = new CheckBox[] {fourcb, eightcb, sixteencb, thirtytwocb, fivecb};
        final String[] events_running = {EVENT_400M, EVENT_800M, EVENT_1600M, EVENT_3200M, EVENT_5000M};


        for(String run : events_running) {
            MyPreferenceSaver.get(sInstance).setString(run, "");
        }

        for(CheckBox cb : cbarray) {
            cb.setEnabled(false);
        }


        for(CheckBox cb : cbarray) {
            final CheckBox currentCheckBox = cb;
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonview, boolean ischecked) {
                    if(ischecked) {
                        switch(currentCheckBox.getText().toString()) {

                            case "400 Meter":
                                MyPreferenceSaver.get(sInstance).setString(EVENT_400M, EVENT_400M);
                                break;
                            case "800 Meter":
                                MyPreferenceSaver.get(sInstance).setString(EVENT_800M, EVENT_800M);
                                break;
                            case "1600 Meter":
                                MyPreferenceSaver.get(sInstance).setString(EVENT_1600M, EVENT_1600M);
                                break;
                            case "3200 Meter":
                                MyPreferenceSaver.get(sInstance).setString(EVENT_3200M, EVENT_3200M);
                                break;
                            case "5000 Meter":
                                MyPreferenceSaver.get(sInstance).setString(EVENT_5000M, EVENT_5000M);
                                break;


                        }
                        if(compset == true) submitbtn.setEnabled(true);
                    }
                    if(!ischecked) {
                        switch(currentCheckBox.getText().toString()) {
                            case "400 Meter":
                                MyPreferenceSaver.get(sInstance).remove(EVENT_400M);
                                break;
                            case "800 Meter":
                                MyPreferenceSaver.get(sInstance).remove(EVENT_800M);
                                break;
                            case "1600 Meter":
                                MyPreferenceSaver.get(sInstance).remove(EVENT_1600M);
                                break;
                            case "3200 Meter":
                                MyPreferenceSaver.get(sInstance).remove(EVENT_3200M);
                                break;
                            case "5000 Meter":
                                MyPreferenceSaver.get(sInstance).remove(EVENT_5000M);
                                break;
                        }
                    }
                }
            });
        }


        showDialogOnButtonClick();




        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchhome();
            }
        });








    }


    public void showDialogOnButtonClick() { // method name is a total misnomer, its a textfield click and not a button click
        final EditText getTimeDate = (EditText)findViewById(R.id.getTimeDate);
        getTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        getTimeDate.setText(dayx + "/" + (monthx + 1) + "/" + yearx);
                        showDialog(DIALOGID);




            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOGID)
            return new DatePickerDialog(this, dpickerListener, yearx, monthx, dayx);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            yearx = i;
            monthx = i1;
            dayx = i2;
            getgetTimeDate().setText(dayx + "/" + (monthx + 1) + "/" + yearx);
            MyPreferenceSaver.get(sInstance).setInt(COMP_DAY, dayx);
            MyPreferenceSaver.get(sInstance).setInt(COMP_MONTH, monthx);
            MyPreferenceSaver.get(sInstance).setInt(COMP_YEAR, yearx);
            compset = true;
            for(CheckBox cb : cbarray) {
                cb.setEnabled(true);
            }

        }
    };

    private EditText getgetTimeDate() {
        final EditText getTimeDate = (EditText)findViewById(R.id.getTimeDate);
        return getTimeDate;
    }


    private void launchhome() {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

}
