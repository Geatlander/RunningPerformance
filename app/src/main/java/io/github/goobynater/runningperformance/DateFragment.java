package io.github.goobynater.runningperformance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView daycountdown, events;
    Button editcomp;
    static final String COMP_DAY = "competition day";
    static final String COMP_MONTH = "competition month";
    static final String COMP_YEAR = "competition year";
    static final String EVENT_400M = "400m";
    static final String EVENT_800M = "800m";
    static final String EVENT_1600M = "1600m";
    static final String EVENT_3200M = "3200m";
    static final String EVENT_5000M = "5000m";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateFragment newInstance(String param1, String param2) {
        DateFragment fragment = new DateFragment();
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
        View v = inflater.inflate(R.layout.fragment_date, container, false);
        daycountdown = (TextView) v.findViewById(R.id.compview);
        events = (TextView) v.findViewById(R.id.eventsrunning);
        editcomp = (Button) v.findViewById(R.id.editdate);



        editcomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchrunsetup2();
            }
        });


        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDateFragmentInteraction(uri);
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
        void onDateFragmentInteraction(Uri uri);
    }

    private void launchrunsetup2() {
        Intent intent = new Intent(getActivity(), Runsetup2.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        int compday = MyPreferenceSaver.get(getContext()).getInt(COMP_DAY);
        int compmonth = MyPreferenceSaver.get(getContext()).getInt(COMP_MONTH);
        int compyear = MyPreferenceSaver.get(getContext()).getInt(COMP_YEAR);
        String eventsrunningstring = "";


        String calendarString;
        if((!(compday == -1)) && (!(compmonth == -1)) && (!(compyear == -1))) {
            Calendar daterep = Calendar.getInstance();
            Date currentdate = daterep.getTime();
            daterep.set(Calendar.DAY_OF_MONTH, compday);
            daterep.set(Calendar.MONTH, compmonth);
            daterep.set(Calendar.YEAR, compyear);
            Date compdate = daterep.getTime();
            long daysuntilcomp = (compdate.getTime() - currentdate.getTime());
            daysuntilcomp = daysuntilcomp /(24*60*60*1000);
            if (daysuntilcomp == 1)
                calendarString = "Your Next Competition is Tomorrow";
            else if (daysuntilcomp == 0)
                calendarString = "Your Competition is Today";
            else if (daysuntilcomp < 0)
                calendarString = "Invalid date entered for Competition: Please Reset";
            else calendarString = "Your Next Competition is in " + Long.toString(daysuntilcomp) + " days";
        }
        else calendarString = "Click to Schedule a Competition";
        daycountdown.setText(calendarString);



        String eventsarr[] = {EVENT_400M, EVENT_800M, EVENT_1600M, EVENT_3200M, EVENT_5000M};

        for(String x: eventsarr)
            eventsrunningstring += MyPreferenceSaver.get(getContext()).getString(x) + " ";

        events.setText("Events Running: " + eventsrunningstring);

    }
}
