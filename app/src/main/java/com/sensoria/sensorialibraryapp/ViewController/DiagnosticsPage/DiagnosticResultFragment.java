package com.sensoria.sensorialibraryapp.ViewController.DiagnosticsPage;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sensoria.sensorialibraryapp.R;

public class DiagnosticResultFragment extends Fragment
{

    public DiagnosticResultFragment()
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
        View convertView = inflater.inflate(R.layout.fragment_diagnostic_result, container, false);

        TextView results = (TextView) convertView.findViewById(R.id.tv_result);

        Bundle arguments = getArguments();

        if (arguments != null)
        {
            results.setText(arguments.getString("result"));
        }

        // Inflate the layout for this fragment
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
