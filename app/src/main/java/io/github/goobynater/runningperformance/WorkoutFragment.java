package io.github.goobynater.runningperformance;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.id.input;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    List<String> workouts;
    List<String> reasons;
    String todaysworkout;
    Float todaysmileage;
    final String ADDWORKOUT = "Add a new Workout";
    final String DELWORKOUT = "Delete a Workout";
    final String MYMILEAGE = "todaysmileage";
    EditText entermileage;
    Boolean flag;
    Boolean delflag = false;
    int length = 0;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_workout, container, false);

        final Spinner workoutSpin = (Spinner) v.findViewById(R.id.workoutspin);
        final Spinner reasonsSpin = (Spinner) v.findViewById(R.id.reasonspinner);
        workouts = new ArrayList<>();






        final ArrayAdapter<String> myadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, workouts);


        loadWorkouts();



        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutSpin.setAdapter(myadapter);
        workoutSpin.setPrompt("Select a Workout for Today");

        String[] reasonsarr = {"Lack of Sleep", "Not enough Food", "Dehydrated", "Mental Weakness", "Soreness"};
        reasons = new ArrayList<>();
        for(String x : reasonsarr )
            reasons.add(x);

        final ArrayAdapter<String> reasonsadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, reasons);
        reasonsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonsSpin.setEnabled(false);





        final RadioButton noButton = (RadioButton) v.findViewById(R.id.noRadio);
        final RadioButton yesButton = (RadioButton) v.findViewById(R.id.yesRadio);
        noButton.setEnabled(false);
        yesButton.setEnabled(false);


        Calendar workoutcal = Calendar.getInstance();
        final String year = Integer.toString(workoutcal.get(workoutcal.YEAR));
        final String month = Integer.toString(workoutcal.get(workoutcal.MONTH) + 1);
        final String day = Integer.toString(workoutcal.get(workoutcal.DAY_OF_MONTH));

        final String mileageondate = "mileage"+year+month+day;
        final String workoutondate = "workout"+year+month+day;
        final String statusondate = "status"+year+month+day;







        workoutSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (workoutSpin.getSelectedItem().toString() != ADDWORKOUT && (workoutSpin.getSelectedItem().toString() != DELWORKOUT) && delflag == false) {
                    todaysworkout = workoutSpin.getSelectedItem().toString();
                //NOTE!!!!!! This conditional doesn't really pass when new workout is created.

                    // Whenever regular item selected, it saves all the stuff to the sharedprefs.

                    shareToPrefs(myadapter);








                }
                //If NEW WORKOUT is selected
                else if ((workoutSpin.getSelectedItem().toString() == ADDWORKOUT)){
                    delflag = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Add a Workout");
                    builder.setMessage("Please add the name of your new workout");

                    final EditText input = new EditText(getContext());
                    builder.setView(input);

                    builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String toAdd = input.getText().toString();
                            myadapter.insert(toAdd, 0);
                            myadapter.notifyDataSetChanged();
                            workoutSpin.setSelection(0);
                            todaysworkout = workoutSpin.getSelectedItem().toString();
                            if(todaysworkout != "") {
                                shareToPrefs(myadapter);
                            }
                        }

                    });

                    AlertDialog addworkout = builder.create();
                    addworkout.show();




                }

                else if (delflag == true) {

                    if (workoutSpin.getSelectedItem().toString() != (DELWORKOUT) && workoutSpin.getSelectedItem().toString() !=  (ADDWORKOUT)) {





                        myadapter.remove(workoutSpin.getSelectedItem().toString());

                        length = myadapter.getCount();
                        MyPreferenceSaver.get(getContext()).setInt("workout_array_size", length);

                        // Note: When the following line has test return as ' "" ', that means that the value cannot be retrieved from sharedprefs.

                        //String test = MyPreferenceSaver.get(getContext()).getString("array_" + (workoutSpin.getSelectedItemPosition() -1));
                        int x = workoutSpin.getSelectedItemPosition();
                        String test = MyPreferenceSaver.get(getContext()).getString("array_" + x);
                        int selecteditempos = workoutSpin.getSelectedItemPosition();
                        String removedstring = MyPreferenceSaver.get(getContext()).remove("array_" + (workoutSpin.getSelectedItemPosition()));
                        // NOTE FOR DEBUGGING!!! If you try to delete the last element, 3 lines above won't retrieve its name at index 0 of sharedprefs.
                        shareToPrefs(myadapter);


                        Toast deltoast = Toast.makeText(getContext(), "Workout has been deleted", Toast.LENGTH_SHORT);
                        deltoast.show();

                        delflag = false;
                    }                  }










                else if ((workoutSpin.getSelectedItem().toString() == (DELWORKOUT))) {

                    Toast notify = Toast.makeText(getContext(), "Please Re-select your workout to delete", Toast.LENGTH_SHORT);
                    notify.show();

                    delflag = true;




                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        entermileage = (EditText) v.findViewById(R.id.enterMileage);

        entermileage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                todaysmileage = Float.parseFloat(entermileage.getText().toString());







                if(todaysworkout != null) {
                    yesButton.setEnabled(true);
                    noButton.setEnabled(true);
                }


                return false;
            }
        });


        yesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                noButton.setEnabled(false);
                MyPreferenceSaver.get(getContext()).setString(statusondate, "Good");
                MyPreferenceSaver.get(getContext()).setFloat("mileage"+year+month+day, todaysmileage);
                MyPreferenceSaver.get(getContext()).setString("workout"+year+month+day, todaysworkout);

            }
        });

        noButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                yesButton.setEnabled(false);


                reasonsSpin.setPrompt("What was wrong with your run today?");
                reasonsSpin.setEnabled(true);
                reasonsSpin.setAdapter(reasonsadapter);



                reasonsSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String reason = reasonsSpin.getSelectedItem().toString();
                        MyPreferenceSaver.get(getContext()).setString(statusondate, reason);
                        MyPreferenceSaver.get(getContext()).setFloat("mileage"+year+month+day, todaysmileage);
                        MyPreferenceSaver.get(getContext()).setString("workout"+year+month+day, todaysworkout);
                        Toast.makeText(getContext(), "Workout Entered", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });




            }
        });








        return v;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onWorkoutFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    @Override
    public void onResume() {
        super.onResume();
        length = MyPreferenceSaver.get(getContext()).getInt("workout_array_size");
        loadWorkouts();



    }

    private void shareToPrefs(ArrayAdapter<String> myadapter) {
        // NOTE:: When ran in debugger, while working properly with 6 elements, length evaluated to 49
        length = myadapter.getCount();
        MyPreferenceSaver.get(getContext()).setInt("workout_array_size", length);
        for(int x = 0; x < length; x++) {
            String myelem = myadapter.getItem(x);
            switch(myelem) {
                case "Add a new Workout":
                    x++;
                    break;
                case "Delete a Workout":
                    x++;
                    break;
                case "":
                    x++;
                    break;
                default:
                    MyPreferenceSaver.get(getContext()).setString("array_" + x, myadapter.getItem(x));
                    String teststring = MyPreferenceSaver.get(getContext()).getString("array_" + x);
                    System.out.println(teststring);

            }


        }

        if(length < 3) {
            for(int x = 0; x < 100; x++) {
                MyPreferenceSaver.get(getContext()).remove("array_"+ x);
            }
        }



    }




    private void loadWorkouts() {
        length = MyPreferenceSaver.get(getContext()).getInt("workout_array_size");
            for(int x = length; x > -1; x--) {

                String tobeadded = (MyPreferenceSaver.get(getContext()).getString("array_" + x));
                switch (tobeadded) {
                    case ADDWORKOUT:
                        //MyPreferenceSaver.get(getContext()).remove("array_" + x);
                        break;
                    case DELWORKOUT:
                        //MyPreferenceSaver.get(getContext()).remove("array_" + x);
                        break;
                    case "":
                        //MyPreferenceSaver.get(getContext()).remove("array_" + x);
                        break;
                    default:
                        if(! (workouts.contains(tobeadded)))
                            workouts.add(0, tobeadded);
                        else System.out.println("Same element");
                }

                }

            if(!(workouts.contains(ADDWORKOUT) && workouts.contains(DELWORKOUT))) {
                workouts.add(ADDWORKOUT);
                workouts.add(DELWORKOUT);

            }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onWorkoutFragmentInteraction(Uri uri);
    }
}
