package io.github.goobynater.runningperformance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GraphFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String[] xData;
    float[] yData;
    Calendar workoutcal = Calendar.getInstance();
    String year = Integer.toString(workoutcal.get(workoutcal.YEAR));
    String month = Integer.toString(workoutcal.get(workoutcal.MONTH) + 1);
    String day = Integer.toString(workoutcal.get(workoutcal.DAY_OF_MONTH));
    TextView mileageview;
    Button nextbtn;


    PieChart workoutchart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphFragment newInstance(String param1, String param2) {
        GraphFragment fragment = new GraphFragment();
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
        View v = inflater.inflate(R.layout.fragment_graph, container, false);

        workoutchart = (PieChart) v.findViewById(R.id.workoutChart);
        mileageview = (TextView) v.findViewById(R.id.mileageview);
        nextbtn = (Button)v.findViewById(R.id.nextbtn);

        workoutchart.setRotationEnabled(true);
        workoutchart.setUsePercentValues(true);
        workoutchart.setDescription(null);
        workoutchart.setHoleRadius(50);
        workoutchart.setCenterText("Most Common Workouts (Past 2 Weeks)");
        workoutchart.setCenterTextSize(15);



        final String mileageondate = "mileage"+year+month+day;
        String workoutondate = "workout"+year+month+day;
        final String statusondate = "status"+year+month+day;

        workoutgraphsetup();
        float mileage = getmileage();



        mileageview.setText("Mileage of Past Two Weeks: " + mileage);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Graph2Activity.class);
                startActivity(intent);
            }
        });


        return v;
    }

    private int maxday(int month) {
        Integer[] thirtyone = new Integer[]{1, 3, 5, 7, 8, 10, 12};
        Integer[] thirty = new Integer[] {4, 6, 9, 11};

        if (Arrays.asList(thirtyone).contains(month))
            return 31;
        else if (Arrays.asList(thirty).contains(month))
            return 30;

        else
            if (Calendar.getInstance().YEAR % 4 == 0)
                return 29;
            return 28;
    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i=0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i=0; i< xData.length; i++) {
            String data = xData[i];
            xEntrys.add(xData[i]);
        }

        // create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Workout Percentages");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        colors.add(Color.BLACK);
        colors.add(Color.LTGRAY);
        colors.add(Color.DKGRAY);
        colors.add(Color.rgb(153, 255, 102));
        colors.add(Color.rgb(255, 153, 0));
        colors.add(Color.rgb(255, 102, 102));
        colors.add(Color.rgb(153, 153, 102));

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        workoutchart.setData(pieData);
        workoutchart.invalidate();















    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onGraphFragmentInteraction(uri);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onGraphFragmentInteraction(Uri uri);
    }

    private void workoutgraphsetup() {
        String todaysworkout = MyPreferenceSaver.get(getContext()).getString("workout"+year+month+day);

        int dynamicday = Integer.parseInt(day);
        int dynamicmonth = Integer.parseInt(month);
        int dynamicyear = Integer.parseInt(year);

        String unfilteredworkouts[] = new String[14];

        for(int i = 0; i < 14; ) {

            if(dynamicday - 1 > 0) {
                year = Integer.toString(dynamicyear);
                month = Integer.toString(dynamicmonth);
                day = Integer.toString(dynamicday);
                unfilteredworkouts[i] = MyPreferenceSaver.get(getContext()).getString("workout"+year+month+day);
                String workout = unfilteredworkouts[i];
                dynamicday -= 1;
                i++;

            }
            else {
                year = Integer.toString(dynamicyear);
                month = Integer.toString(dynamicmonth);
                day = Integer.toString(dynamicday);
                unfilteredworkouts[i] = MyPreferenceSaver.get(getContext()).getString("workout"+year+month+day);
                System.out.println(dynamicday);
                dynamicday = maxday(Integer.parseInt(month) - 1);
                // If the month - 1 is less than 1 (January to December), then set the month to December and the year back one
                if(dynamicmonth - 1 < 1) {
                    dynamicmonth = 12;
                    dynamicyear -= 1;
                    dynamicday = maxday(dynamicmonth);
                }
                else dynamicmonth -= 1;

                i++;
            }


        }


        String filteredworkouts[] = new String[14];
        float tempdata[] = new float[14];
        int totalrealelems = 0;
        int skipby = 0;
        for(int x = 0; x < unfilteredworkouts.length; x++)
            if (unfilteredworkouts[x] != "") totalrealelems++;

        for(int x = 0; x < unfilteredworkouts.length; x++) {
            String elem = unfilteredworkouts[x];
            int count = 0;

            for(int i = 0; i < unfilteredworkouts.length; i++) {
                String comparestring = unfilteredworkouts[i];
                if(comparestring.equals(elem)) {
                    count++;
                }
            }

            System.out.println(count);
            if (elem != "") {

                if (!Arrays.asList(filteredworkouts).contains(elem)) {
                    float percent = (float) count / (float) totalrealelems;



                    tempdata[x + skipby] = percent;
                    filteredworkouts[x + skipby] = elem;
                    System.out.println(tempdata[0]);
                }
                else skipby--;
            }
            else skipby--;

        }
        System.out.println(filteredworkouts);
        // what the length of xData will be based off of if an element is used or not.
        int xDatalength = 0;
        for(String x: filteredworkouts) {
            if (x != null)
                xDatalength++;
            else
                System.out.println("element is null");
        }

        // Actually make xData based off of length and contents
        xData = new String[xDatalength];
        yData = new float[xDatalength];
        for(int x = 0; x < xData.length; x++) {
            xData[x] = filteredworkouts[x];
            yData[x] = tempdata[x];
        }




        System.out.print(xData);
        System.out.print(yData);








        addDataSet();

        workoutchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String gotString = e.toString();
                String highlight = h.toString();
                String foo = "bar";
                int pos1 = e.toString().indexOf("Entry,");
                String percentage = e.toString().substring(pos1 + 17);

                for(int i = 0; i < yData.length; i++) {
                    if(yData[i] == Float.parseFloat(percentage)) {
                        pos1 = i;
                        System.out.println(pos1);
                        break;
                    }
                }

                String workout = xData[Integer.parseInt(h.toString().substring(14, 15))];
                Toast.makeText(getContext(), "Workout " + workout + "\n" + "Percentage: " + (Float.parseFloat(percentage) * 100) + "%", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected() {

            }
        });
        resetdate();


    }

    private float getmileage() {
        int dynamicday = Integer.parseInt(day);
        int dynamicmonth = Integer.parseInt(month);
        int dynamicyear = Integer.parseInt(year);

        float mileages[] = new float[14];

        for(int i = 0; i < 14; ) {

            if(dynamicday - 1 > 0) {
                year = Integer.toString(dynamicyear);
                month = Integer.toString(dynamicmonth);
                day = Integer.toString(dynamicday);
                mileages[i] = MyPreferenceSaver.get(getContext()).getFloat("mileage"+year+month+day);
                Float mileage = mileages[i];
                dynamicday -= 1;
                i++;

            }
            else {
                year = Integer.toString(dynamicyear);
                month = Integer.toString(dynamicmonth);
                day = Integer.toString(dynamicday);
                float yesterdaymileage = MyPreferenceSaver.get(getContext()).getFloat("mileage"+year+month+day);
                mileages[i] = MyPreferenceSaver.get(getContext()).getFloat("mileage"+year+month+day);
                System.out.println(dynamicday);
                dynamicday = maxday(Integer.parseInt(month) - 1);
                // If the month - 1 is less than 1 (January to December), then set the month to December and the year back one
                if(dynamicmonth - 1 < 1) {
                    dynamicmonth = 12;
                    dynamicyear -= 1;
                    dynamicday = maxday(dynamicmonth);
                }
                else dynamicmonth -= 1;

                i++;
            }


            }
        float mileagesum = 0;
        for(float x : mileages ) {
            if (x > 0) mileagesum += x;

        }
        return mileagesum;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void resetdate() {
        year = Integer.toString(workoutcal.get(workoutcal.YEAR));
        month = Integer.toString(workoutcal.get(workoutcal.MONTH) + 1);
        day = Integer.toString(workoutcal.get(workoutcal.DAY_OF_MONTH));
    }
}
