package com.sensoria.sensorialibraryapp.ViewController.DiagnosticsPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.YLabels;
import com.parse.Parse;
import com.parse.ParseObject;
import com.sensoria.sensorialibraryapp.R;
import com.sensoria.sensorialibraryapp.ViewController.MainMenu.MenuActivity;

import java.util.ArrayList;

public class DiagnosticResultsActivity extends ActionBarActivity
{

    String result = null;
    ArrayList<Integer> poses = new ArrayList<Integer>();
    LineChart chart;
    TextView resulttv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_results);
        chart = (LineChart) findViewById(R.id.chart);
        chart.setDescription("The different poses of the feet vs. time");
        chart.setTouchEnabled(true);
        chart.setHighlightEnabled(true);
        chart.setStartAtZero(false);
        Intent intent = getIntent();
        if (intent != null)
        {
            poses.addAll(intent.getIntegerArrayListExtra("poses"));
            result = intent.getStringExtra("result");

            Parse.initialize(this, "p6UR5hKjTNIa78j8HykqFR2zsvI5nbrwZAJvvWlC", "3dfrd51jLIiGjYTIirGKwl6GtQpfupZg30LaPzBI");
            ParseObject posesObject = new ParseObject("Poses");
            posesObject.put("Poses",poses);
            posesObject.saveInBackground();
            setData();
        }

        resulttv = (TextView) findViewById(R.id.result);
        resulttv.setText(result);
    }

    private void setData() {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < poses.size(); i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < poses.size(); i++) {
            yVals.add(new Entry(poses.get(i), i));
        }

        YLabels yl = chart.getYLabels();
        yl.setLabelCount(4);
        yl.setPosition(YLabels.YLabelPosition.BOTH_SIDED);

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Diagnosis Poses");
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        chart.setData(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diagnostic_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        Intent intent = new Intent(DiagnosticResultsActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
