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

import static io.github.goobynater.runningperformance.LogTime.CONST_1600M;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView fivekview, twomview, mview, hmview, qmview;
    Button editTimes;
    static RecordsFragment sInstance;

    static final String CONST_400M = "400mtime";
    static final String CONST_800M = "800mtime";
    static final String CONST_1600M = "1600mtime";
    static final String CONST_3200M = "3200mtime";
    static final String CONST_5000M = "5ktime";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecordsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordsFragment newInstance(String param1, String param2) {
        RecordsFragment fragment = new RecordsFragment();
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
        sInstance = this;











    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_records, container, false);
        fivekview = (TextView) v.findViewById(R.id.fivekview);
        twomview = (TextView) v.findViewById(R.id.twomview);
        mview = (TextView) v.findViewById(R.id.mview);
        hmview= (TextView) v.findViewById(R.id.hmview);
        qmview = (TextView) v.findViewById(R.id.qmview);
        editTimes = (Button) v.findViewById(R.id.editTimes);


        String fivektime = MyPreferenceSaver.get(getContext()).getString(CONST_5000M);
        String twomtime = MyPreferenceSaver.get(getContext()).getString(CONST_3200M);
        String mtime = MyPreferenceSaver.get(getContext()).getString(CONST_1600M);
        String hmtime = MyPreferenceSaver.get(getContext()).getString(CONST_800M);
        String qmtime = MyPreferenceSaver.get(getContext()).getString(CONST_400M);


        fivekview.setText("5000m Record: " + fivektime);
        twomview.setText("3200m Record: " + twomtime);
        mview.setText("1600m Record: " + mtime);
        hmview.setText("800m Record: " + hmtime);
        qmview.setText("400m Record: " + qmtime);

        editTimes.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View x) {
                launchLogTime();
            }
        });



        return v;

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRecordFragmentInteraction(uri);
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



    private void launchLogTime() {
        Intent intent = new Intent(getActivity(), LogTime.class);
        startActivity(intent);
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
        void onRecordFragmentInteraction(Uri uri);
    }

    public static RecordsFragment get() {
        return sInstance;
    }

}
