package com.sensoria.sensorialibraryapp.LoginAndRegistration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.sensoria.sensorialibraryapp.R;


/**
 * Created by Cherry_Zhang on 2014-11-16.
 */
public class SummaryOfAppFragment2 extends Fragment
{

    ShimmerTextView TV_text1, TV_text2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_section_2, container, false);

        TV_text1 = (ShimmerTextView) rootView.findViewById(R.id.shimmer_tv);

        Shimmer shimmer = new Shimmer();
        shimmer.start(TV_text1);

        return rootView;
    }
}