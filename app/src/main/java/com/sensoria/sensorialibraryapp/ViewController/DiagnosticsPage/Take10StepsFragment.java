package com.sensoria.sensorialibraryapp.ViewController.DiagnosticsPage;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sensoria.sensorialibraryapp.R;

public class Take10StepsFragment extends Fragment
{

    TextToSpeech tts;
    public TextView stepsLeft;

    public Take10StepsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_take10_steps, container, false);
        stepsLeft = (TextView) container.findViewById(R.id.tv_numberOfStepsLeft);
        return convertView;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

}
