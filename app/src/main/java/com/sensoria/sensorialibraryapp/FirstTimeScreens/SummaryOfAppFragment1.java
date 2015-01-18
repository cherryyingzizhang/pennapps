package com.sensoria.sensorialibraryapp.FirstTimeScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sensoria.sensorialibraryapp.R;


public class SummaryOfAppFragment1 extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.info_section_1, container, false);

        return rootView;
    }

}
