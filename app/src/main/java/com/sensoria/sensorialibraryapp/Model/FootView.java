package com.sensoria.sensorialibraryapp.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sensoria.sensorialibraryapp.R;

/**
 * Created by Cherry_Zhang on 2015-01-17.
 *
 * This class makes a visual view of the person's foot and draws which parts of the foot
 * has lots of pressure on it based off of the sock sensors.
 */
public class FootView extends View
{
    public enum ColorOfArea
    {
        GREEN, YELLOW, RED
    }

    ColorOfArea mtb1Color, mtb5Color, heelColor;

    Context mContext;
    Bitmap footImage;
    Integer mtb1, mtb5, heel;
    int mtb1X, mtb1Y, mtb5X, mtb5Y, heelX, heelY;
    SharedPreferences preferences;

    public FootView(Context context) {
        super(context);
        init(context);
    }

    public FootView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public FootView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        footImage = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.footprint);
        //todo change these arbitrary numbers to fit the foot view... also this is only for
        //left foot because we dont have right sensoria sock as we were only provided a left one
        mtb1X = 50;
        mtb1Y = 50;
        mtb5X = footImage.getWidth()-70;
        mtb5Y = 50;
        heelX = footImage.getWidth()/2+30;
        heelY = footImage.getHeight()-90;
        preferences = mContext.getSharedPreferences("temp", mContext.getApplicationContext().MODE_PRIVATE);
    }

    public ColorOfArea getMtb1Color()
    {
        return mtb1Color;
    }

    public ColorOfArea getMtb5Color()
    {
        return mtb5Color;
    }

    public ColorOfArea getHeelColor()
    {
        return heelColor;
    }

    public void setMtb1(int mtb1)
    {
        this.mtb1 = mtb1;
    }

    public void setMtb5(int mtb5)
    {
        this.mtb5 = mtb5;
    }

    public void setHeel(int heel)
    {
        this.heel = heel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        footImage = Bitmap.createScaledBitmap(footImage, canvas.getWidth(), canvas.getHeight(), false);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);

        canvas.drawBitmap(footImage, 0, 0, paint);

        if (mtb1 != null && mtb5 != null && heel != null)
        {
            //mtb1
            if (mtb1 <= preferences.getInt("cal1_mtb1_mean", 0))
            {
                paint.setColor(Color.parseColor("#5000FF00"));
                mtb1Color = ColorOfArea.GREEN;
            }
            else if (mtb1 <= preferences.getInt("cal3_mtb1_mean", 0))
            {
                paint.setColor(Color.parseColor("#50FFFF00"));
                mtb1Color = ColorOfArea.YELLOW;
            }
            else
            {
                paint.setColor(Color.parseColor("#50FF0040"));
                mtb1Color = ColorOfArea.RED;
            }

            canvas.drawCircle(mtb1X, mtb1Y+90, 50, paint);

            //mtb5
            if (mtb5 <= preferences.getInt("cal1_mtb5_mean", 0))
            {
                paint.setColor(Color.parseColor("#5000FF00"));
                mtb5Color = ColorOfArea.GREEN;
            }
            else if (mtb5 <= preferences.getInt("cal3_mtb5_mean", 0))
            {
                paint.setColor(Color.parseColor("#50FFFF00"));
                mtb5Color = ColorOfArea.YELLOW;
            }
            else
            {
                paint.setColor(Color.parseColor("#50FF0040"));
                mtb5Color = ColorOfArea.RED;
            }
            canvas.drawCircle(mtb5X, mtb5Y+80, 50, paint);

            //heel
            if (heel <= preferences.getInt("cal2_heel_mean", 0))
            {
                paint.setColor(Color.parseColor("#5000FF00"));
                heelColor = ColorOfArea.GREEN;
            }
            else if (heel <= preferences.getInt("cal0_heel_mean", 0))
            {
                paint.setColor(Color.parseColor("#50FFFF00"));
                heelColor = ColorOfArea.YELLOW;
            }
            else
            {
                paint.setColor(Color.parseColor("#50FF0040"));
                heelColor = ColorOfArea.RED;
            }
            canvas.drawCircle(heelX/2+18, heelY, 50, paint);
        }
    }
}
