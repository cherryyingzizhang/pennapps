package com.sensoria.sensorialibraryapp.UsefulClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Cherry_Zhang on 2015-01-18.
 */
public class AlgorithmMethods
{
    private Context mContext;

    public AlgorithmMethods(Context context)
    {
        mContext = context;
    }

    //returns -1, 0, 1, 2, or 3. -1 means it doesn't match any pose
    public int matchesPose(int mtb1, int mtb5, int heel)
    {
        SharedPreferences preferences= mContext.getSharedPreferences("temp", mContext.getApplicationContext().MODE_PRIVATE);

        if (withinMeanAngRange(mtb1, preferences.getInt("cal0_mtb1_mean", 0), preferences.getInt("cal0_mtb1_range",0))
                && withinMeanAngRange(mtb5, preferences.getInt("cal0_mtb5_mean", 0), preferences.getInt("cal0_mtb5_range",0))
                && withinMeanAngRange(heel, preferences.getInt("cal0_heel_mean", 0), preferences.getInt("cal0_heel_range",0)))
        {
            Log.e("","" + 0);
            return 0;
        }
        else if (withinMeanAngRange(mtb1, preferences.getInt("cal1_mtb1_mean", 0), preferences.getInt("cal1_mtb1_range",0))
                && withinMeanAngRange(mtb5, preferences.getInt("cal1_mtb5_mean", 0), preferences.getInt("cal1_mtb5_range",0))
                && withinMeanAngRange(heel, preferences.getInt("cal1_heel_mean", 0), preferences.getInt("cal1_heel_range",0)))
        {
            Log.e("","" + 1);
            return 1;
        }
        else if (withinMeanAngRange(mtb1, preferences.getInt("cal2_mtb1_mean", 0), preferences.getInt("cal2_mtb1_range",0))
                && withinMeanAngRange(mtb5, preferences.getInt("cal2_mtb5_mean", 0), preferences.getInt("cal2_mtb5_range",0))
                && withinMeanAngRange(heel, preferences.getInt("cal2_heel_mean", 0), preferences.getInt("cal2_heel_range",0)))
        {
            Log.e("","" + 2);
            return 2;
        }
        else if (withinMeanAngRange(mtb1, preferences.getInt("cal3_mtb1_mean", 0), preferences.getInt("cal3_mtb1_range",0))
                && withinMeanAngRange(mtb5, preferences.getInt("cal3_mtb5_mean", 0), preferences.getInt("cal3_mtb5_range",0))
                && withinMeanAngRange(heel, preferences.getInt("cal3_heel_mean", 0), preferences.getInt("cal3_heel_range",0)))
        {
            Log.e("","" + 3);
            return 3;
        }
        else
        {
            Log.e("","-1");
            return -1;
        }
    }

    public boolean withinMeanAngRange(int number, int mean, int range)
    {
        if (Math.abs(mean - number) <= 75)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
