package com.sensoria.sensorialibraryapp.FirstTimeScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sensoria.sensorialibraryapp.R;
import com.sensoria.sensorialibraryapp.ViewController.MainMenu.MenuActivity;


/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class SummaryOfAppFragment3 extends Fragment
{

    Button next_page_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_section_3, container, false);

        next_page_button = (Button) rootView.findViewById(R.id.next_page_button);
        next_page_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SummaryOfAppFragment3.this.getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}