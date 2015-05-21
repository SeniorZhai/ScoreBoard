package org.seniorzhai.scoreboard;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
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

    private static final int defaultForecolor = Color.WHITE;
    private static final int defaultBackcolor = 0X33000000;
    private static float density = 1f;
    private static final float defaultLineWidth = 3f;

    private Context mContext;
    private Paint mPaint;
    private int forecolor;
    private int backcolor;
    private float lineWidth;
    private int width;
    private int contentWidth;
    private String contentText;

    private LinearLayout customLayout;
    private LinearLayout topLinear;
    private TextView contentTV;
    private NumberListView numberListView1, numberListView2;

    public ScoreBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public ScoreBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScoreBoard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        setWillNotDraw(false);
        density = mContext.getResources().getDisplayMetrics().density;
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.ScoreBoard);
        try {
            backcolor = array.getColor(R.styleable.ScoreBoard_backcolor, defaultBackcolor);
            forecolor = array.getColor(R.styleable.ScoreBoard_forecolor, defaultForecolor);
            lineWidth = array.getDimensionPixelSize(R.styleable.ScoreBoard_lineWidth, (int) (defaultLineWidth * density));
            contentText = array.getString(R.styleable.ScoreBoard_contentText);
        } finally {
            array.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(lineWidth);              //线宽
        mPaint.setStyle(Paint.Style.STROKE);
        initCustomLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = Math.min(w, h);
        contentWidth = (int) (Math.sqrt(w * w / 2) - lineWidth);
        sizeChanged();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(backcolor);
        canvas.drawCircle(width / 2, width / 2, width / 2 - lineWidth / 2, mPaint);
        RectF rectF = new RectF();
        rectF.left = 0 + lineWidth / 2;
        rectF.top = 0 + lineWidth / 2;
        rectF.right = getWidth() - lineWidth / 2;
        rectF.bottom = getHeight() - lineWidth / 2;
        mPaint.setColor(forecolor);
        canvas.drawArc(rectF, 270, angle, false, mPaint);
    }

    private void sizeChanged() {
        customLayout.setLayoutParams(new LayoutParams(contentWidth, contentWidth, Gravity.CENTER));
        topLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, contentWidth / 2, 1f));
        contentTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, contentWidth / 2, 1f));
        contentTV.setTextSize(contentWidth / 14);
        if (numberListView1 == null && numberListView2 == null) {
            numberListView1 = new NumberListView(mContext, contentWidth, 0);
            numberListView2 = new NumberListView(mContext, contentWidth, 1);
            numberListView1.setLayoutParams(new LinearLayout.LayoutParams(contentWidth / 5, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            numberListView2.setLayoutParams(new LinearLayout.LayoutParams(contentWidth / 5, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            topLinear.addView(numberListView1);
            topLinear.addView(numberListView2);
        }
    }

    private void initCustomLayout() {
        if (customLayout != null) {
            return;
        }
        customLayout = new LinearLayout(mContext);
        customLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        customLayout.setOrientation(LinearLayout.VERTICAL);
        topLinear = new LinearLayout(mContext);
        topLinear.setGravity(Gravity.CENTER);
        topLinear.setOrientation(LinearLayout.HORIZONTAL);
        contentTV = new TextView(mContext);
        contentTV.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        contentTV.setText(contentText);
        contentTV.setSingleLine(true);
        contentTV.setTextColor(Color.WHITE);
        customLayout.addView(topLinear);
        customLayout.addView(contentTV);
        addView(customLayout);
    }

    public void setText(CharSequence text) {
        contentTV.setText(text);
    }

    public CharSequence getText() {
        return contentTV.getText();
    }


    public void start() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "angle", 0, 360);
        objectAnimator.setDuration(360);
        objectAnimator.start();
    }

    public void start(float percentage) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "angle", 0, (int) (360 * percentage));
        objectAnimator.setDuration(360);
        objectAnimator.start();
    }

    public void change(int i) {
        numberListView1.setFirstItemId(i / 10, 300);
        numberListView2.setFirstItemId(i % 10, 400);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        invalidate();
    }

    public int getAngle() {
        return angle;
    }

    private int angle = 0;
}
