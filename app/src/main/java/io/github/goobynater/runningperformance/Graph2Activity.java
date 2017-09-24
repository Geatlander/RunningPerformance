package io.github.goobynater.runningperformance;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static java.lang.Math.abs;

public class Graph2Activity extends AppCompatActivity {
    PieChart workoutchart;
    Calendar workoutcal = Calendar.getInstance();
    String[] xData;
    float[] yData;
    String year = Integer.toString(workoutcal.get(workoutcal.YEAR));
    String month = Integer.toString(workoutcal.get(workoutcal.MONTH) + 1);
    String day = Integer.toString(workoutcal.get(workoutcal.DAY_OF_MONTH));
    private static Graph2Activity sInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph2);
        workoutchart = (PieChart) findViewById(R.id.workoutChart);
        workoutchart.setRotationEnabled(true);
        workoutchart.setUsePercentValues(true);
        workoutchart.setDescription(null);
        workoutchart.setHoleRadius(50);
        workoutchart.setCenterText("Status of Workouts (Past 2 Weeks)");
        workoutchart.setCenterTextSize(15);



        final String mileageondate = "mileage"+year+month+day;
        String workoutondate = "workout"+year+month+day;
        final String statusondate = "status"+year+month+day;

        workoutgraphsetup();



    }

    private int maxday(int month) {
        int[] thirtyone = new int[]{1, 3, 5, 7, 8, 10, 12};
        int[] thirty = new int[] {4, 6, 9, 11};

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
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Reasons for bad workouts");
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



    private void workoutgraphsetup() {
        String todaysworkout = MyPreferenceSaver.get(this).getString("status"+year+month+day);

        int dynamicday = Integer.parseInt(day);
        int dynamicmonth = Integer.parseInt(month);
        int dynamicyear = Integer.parseInt(year);

        String unfilteredworkouts[] = new String[14];

        for(int i = 0; i < 14; ) {

            if(dynamicday - 1 > 0) {
                year = Integer.toString(dynamicyear);
                month = Integer.toString(dynamicmonth);
                day = Integer.toString(dynamicday);
                unfilteredworkouts[i] = MyPreferenceSaver.get(this).getString("status"+year+month+day);
                String workout = unfilteredworkouts[i];
                dynamicday -= 1;
                i++;

            }
            else {
                year = Integer.toString(dynamicyear);
                month = Integer.toString(dynamicmonth);
                day = Integer.toString(dynamicday);
                unfilteredworkouts[i] = MyPreferenceSaver.get(this).getString("status"+year+month+day);
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
        int skipby = 0;
        float tempdata[] = new float[14];
        int totalrealelems = 0;
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

                    if (elem.equals("Base Run") && count == 1)
                        System.out.println(percent);

                    tempdata[x + skipby] = percent;
                    filteredworkouts[x + skipby] = elem;
                    System.out.println(tempdata[0]);
                }
                else skipby--;
            }
            else skipby--;

        }

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
                Toast.makeText(Graph2Activity.this, "Workout Status: " + workout + "\n" + "Percentage: " + (Float.parseFloat(percentage) * 100) + "%", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected() {

            }
        });
        resetdate();


    }

    private void resetdate() {
        year = Integer.toString(workoutcal.get(workoutcal.YEAR));
        month = Integer.toString(workoutcal.get(workoutcal.MONTH) + 1);
        day = Integer.toString(workoutcal.get(workoutcal.DAY_OF_MONTH));
    }

    public static Graph2Activity get() {
        return sInstance;
    }

}
