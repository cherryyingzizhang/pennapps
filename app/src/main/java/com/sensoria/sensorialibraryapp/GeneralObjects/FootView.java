package com.sensoria.sensorialibraryapp.GeneralObjects;

import android.content.Context;
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
    Context mContext;
    Bitmap footImage;
    Integer mtb1, mtb5, heel;
    int mtb1X, mtb1Y, mtb5X, mtb5Y, heelX, heelY;

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
        //todo change these arbitrary numbers to fit the foot view
        mtb1X = footImage.getWidth()-5;
        mtb1Y = footImage.getHeight()-5;
        mtb5X = 10;
        mtb5Y = 10;
        heelX = footImage.getWidth()/2;
        heelY = footImage.getHeight()-5;
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

        footImage = Bitmap.createScaledBitmap(footImage, 200, 400, false);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);

        canvas.drawBitmap(footImage, 0, 0, paint);

        if (mtb1 != null && mtb5 != null && heel != null)
        {
            //mtb1
            if (mtb1 <= 525)
            {
                paint.setColor(Color.GREEN);
            }
            else if (mtb1 <= 550)
            {
                paint.setColor(Color.YELLOW);
            }
            else
            {
                paint.setColor(Color.RED);
            }

            canvas.drawCircle(mtb1X / 2, mtb1Y / 2, 50, paint);

            //mtb5
            if (mtb5 <= 555)
            {
                paint.setColor(Color.GREEN);
            }
            else if (mtb5 <= 575)
            {
                paint.setColor(Color.YELLOW);
            }
            else
            {
                paint.setColor(Color.RED);
            }
            canvas.drawCircle(mtb5X / 2, mtb5Y / 2, 50, paint);

            //heel
            if (heel <= 550)
            {
                paint.setColor(Color.GREEN);
            }
            else if (heel <= 600)
            {
                paint.setColor(Color.YELLOW);
            }
            else
            {
                paint.setColor(Color.RED);
            }
            canvas.drawCircle(heelX / 2, heelY / 2, 50, paint);
        }
    }
}
