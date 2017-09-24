package io.github.goobynater.runningperformance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class LogTime extends AppCompatActivity {

    TextView prompt;
    EditText timetf1;
    EditText timetf2;
    EditText timetf3;
    EditText timetf4;
    EditText timetf5;
    Button submit;

    static final String CONST_400M = "400mtime";
    static final String CONST_800M = "800mtime";
    static final String CONST_1600M = "1600mtime";
    static final String CONST_3200M = "3200mtime";
    static final String CONST_5000M = "5ktime";
    private static LogTime sInstance;


    public SharedPreferences.Editor editor;
    public SharedPreferences timepreference;


    final String[] times = new String[5];



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


/*    public LogTime() {
         timepreference = this.getSharedPreferences("MyTimes", Context.MODE_PRIVATE);
        editor = timepreference.edit();
    }
   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sInstance = this;
        setContentView(R.layout.activity_log_time);


        prompt = (TextView) findViewById(R.id.prompt);
        timetf1 = (EditText) findViewById(R.id.timetf1);
        timetf2 = (EditText) findViewById(R.id.timetf2);
        timetf3 = (EditText) findViewById(R.id.timetf3);
        timetf4 = (EditText) findViewById(R.id.timetf4);
        timetf5 = (EditText) findViewById(R.id.timetf5);
        submit = (Button) findViewById(R.id.getTimes);

        final EditText[] textfields = new EditText[]{timetf1, timetf2, timetf3, timetf4, timetf5};
        final LogTime selfref = get();

        int count = 0;
        for(String arg : new String[] {CONST_400M, CONST_800M, CONST_1600M, CONST_3200M, CONST_5000M}) {
            textfields[count].setText(MyPreferenceSaver.get(selfref).getString(arg));
            count++;
        }



        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View x) {
                String storestring;

                for(int i=0; i < textfields.length; i++) {
                        times[i] = textfields[i].getText().toString();
                        switch(i) {
                            case 0:
                                storestring = CONST_400M;
                                break;
                            case 1:
                                storestring = CONST_800M;
                                break;
                            case 2:
                                storestring = CONST_1600M;
                                break;
                            case 3:
                                storestring = CONST_3200M;
                                break;
                            case 4:
                                storestring = CONST_5000M;
                                break;
                            default:
                                storestring = "invalid value";
                                break;

                        }


                        MyPreferenceSaver.get(selfref).setString(storestring, times[i]);
                        String time = MyPreferenceSaver.get(selfref).getString(CONST_5000M);


                }
            launchhomescreen();

            }
        });






        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Running Performance");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    private void launchhomescreen() {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }




    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }



    public static LogTime get() {
        return sInstance;
    }
}




