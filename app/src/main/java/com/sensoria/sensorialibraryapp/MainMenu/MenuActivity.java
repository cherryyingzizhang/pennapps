package com.sensoria.sensorialibraryapp.MainMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sensoria.sensorialibraryapp.DiagnosticsActivity.DiagnosticsActivity;
import com.sensoria.sensorialibraryapp.MonitorPage.MonitorActivity;
import com.sensoria.sensorialibraryapp.R;

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
                //todo
                Toast.makeText(MenuActivity.this, "todo", Toast.LENGTH_SHORT).show();
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
