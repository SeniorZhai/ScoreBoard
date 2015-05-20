package org.seniorzhai.scoreboard;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhaitao on 15/5/19.
 */
public class ScoreBoard extends FrameLayout {

    private static final int defaultColor = Color.WHITE;
    private static float density = 1f;
    private static float lineWidth = 10f;
    private Context mContext;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScoreBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    public ScoreBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public ScoreBoard(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ScoreBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private Paint mPaint;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(defaultColor);
        mPaint.setStrokeWidth(lineWidth);              //线宽
        mPaint.setStyle(Paint.Style.STROKE);
        setWillNotDraw(false);
        density = mContext.getResources().getDisplayMetrics().density;
        lineWidth = density * 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        customLayout();
        RectF rectF = new RectF();
        rectF.left = 0 + lineWidth / 2;
        rectF.top = 0 + lineWidth / 2;
        rectF.right = getWidth() - lineWidth / 2;
        rectF.bottom = getHeight() - lineWidth / 2;
        canvas.drawArc(rectF, 0, act, false, mPaint);
    }

    private int width;
    private int itemHeight;

    private void customLayout() {
        if (linearLayout != null) {
            return;
        }
        width = (int) (Math.sqrt(getWidth() * getWidth() / 2) - lineWidth);
        linearLayout = new LinearLayout(mContext);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(new LayoutParams(width, width, Gravity.CENTER));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout topLinear = new LinearLayout(mContext);
        topLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, width / 2, 1f));
        topLinear.setGravity(Gravity.CENTER);
        topLinear.setOrientation(LinearLayout.HORIZONTAL);
        listView1 = new NumberListView(mContext, width, 0);
        listView1.setLayoutParams(new LinearLayout.LayoutParams(width / 5, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        listView2 = new NumberListView(mContext, width, 1);
        listView2.setLayoutParams(new LinearLayout.LayoutParams(width / 5, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        topLinear.addView(listView1);
        topLinear.addView(listView2);
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width / 2, 1f));
        tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        tv.setTextSize((float) (width / 2 / density / 2));
        tv.setText("PASSES");
        tv.setTextColor(Color.WHITE);
        linearLayout.addView(topLinear);
        linearLayout.addView(tv);
        addView(linearLayout);
    }

    private LinearLayout linearLayout;
    private NumberListView listView1, listView2;


    public void to() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "act", 360);
        objectAnimator.setDuration(360);
        objectAnimator.start();
    }


    public void changer(int i) {
        listView1.setFirstItemId(i / 10, 300);
        listView2.setFirstItemId(i % 10, 400);
    }

    public void setAct(int act) {
        this.act = act;
        invalidate();
    }

    public int getAct() {
        return act;
    }

    private int act = 0;
}
