package com.sensoria.sensorialibraryapp.ViewController.MainMenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sensoria.sensorialibraryapp.R;
import com.sensoria.sensorialibraryapp.ViewController.DiagnosticsPage.DiagnosticsActivity;
import com.sensoria.sensorialibraryapp.ViewController.MonitorPage.MonitorActivity;
import com.sensoria.sensorialibraryapp.ViewController.TrainingPage.TrainingActivity;

/**
 * Created by Cherry_Zhang on 2015-01-16.
 *
 * This class/activity has three buttons and looks amazing.
 * Click Diagnostics to go to Diagnostics page.
 * Click Monitor to go to Monitor Page.
 * Click Training to go to Training Page.
 */
public class MenuActivity extends Activity
{

    RelativeLayout diagnostics, monitor, training;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        diagnostics = (RelativeLayout) findViewById(R.id.diagnostics);
        diagnostics.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, DiagnosticsActivity.class);
                startActivity(intent);
            }
        });

        monitor = (RelativeLayout) findViewById(R.id.monitor);
        monitor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MenuActivity.this, MonitorActivity.class);
                startActivity(intent);
            }
        });

        training = (RelativeLayout) findViewById(R.id.training);
        training.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(MenuActivity.this);
                dialog.setTitle("Start Training");
                dialog.setContentView(R.layout.training_popup_dialog);
                Button startTrainingButton = (Button) dialog.findViewById(R.id.button_startTraining);
                final EditText numberOfStepsForTrainingEditText = (EditText) dialog.findViewById(R.id.numberOfStepsForTrainingEditText);

                startTrainingButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (numberOfStepsForTrainingEditText.getText().equals(""))
                        {
                            Toast.makeText(MenuActivity.this, "Error: Please Input number of steps", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Intent intent = new Intent(MenuActivity.this, TrainingActivity.class);
                            intent.putExtra("numberOfSteps", Integer.parseInt(numberOfStepsForTrainingEditText.getText().toString()));
                            dialog.dismiss();
                            startActivity(intent);
                        }
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
