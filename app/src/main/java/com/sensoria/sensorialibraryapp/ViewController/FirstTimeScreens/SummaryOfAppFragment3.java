package com.sensoria.sensorialibraryapp.ViewController.FirstTimeScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.sensoria.sensorialibraryapp.R;
import com.sensoria.sensorialibraryapp.ViewController.CalibrationsPage.CalibrationsActivity;


/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class SummaryOfAppFragment3 extends Fragment
{

    ShimmerTextView TV_text1;
    Button next_page_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_section_3, container, false);

        TV_text1 = (ShimmerTextView) rootView.findViewById(R.id.shimmer_tv);
        Shimmer shimmer = new Shimmer();
        shimmer.start(TV_text1);

        next_page_button = (Button) rootView.findViewById(R.id.next_page_button);
        next_page_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SummaryOfAppFragment3.this.getActivity(), CalibrationsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}