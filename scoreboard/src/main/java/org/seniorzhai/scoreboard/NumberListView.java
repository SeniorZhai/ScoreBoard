package org.seniorzhai.scoreboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by zhaitao on 15/5/20.
 */
public class NumberListView extends ListView {
    private Context mContext;
    private int width;
    private int type;

    public NumberListView(Context context, int width, int type) {
        super(context);
        mContext = context;
        this.width = width;
        this.type = type;
        init();
    }


    private int firstItemId;

    public void setFirstItemId(int firstItemId, int dur) {
        this.firstItemId = firstItemId;
        this.smoothScrollToPositionFromTop(firstItemId, 0, dur);
    }

    private float density;

    private void init() {
        density = mContext.getResources().getDisplayMetrics().density;
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    if (firstItemId != getFirstVisiblePosition()) {
                        setSelection(firstItemId);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        setLayoutParams(new LinearLayout.LayoutParams(width, width / 2, Gravity.CENTER));
        setDivider(new ColorDrawable());
        setVerticalScrollBarEnabled(false);
        setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(mContext) {
                        @Override
                        public boolean onTouchEvent(MotionEvent event) {
                            return true;
                        }
                    };
                    convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, width / 2));
                    TextView tv = (TextView) convertView;
                    if (type == 0) {
                        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                    } else {
                        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    }
                    tv.setTextSize((float) (width / 2 / density / 1.3));
                    tv.setTextColor(Color.WHITE);
                    tv.setGravity(Gravity.CENTER);
                }
                TextView tv = (TextView) convertView;
                tv.setText(String.valueOf(position));
                return convertView;
            }
        });
    }

    private int mPosition;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int actionMasked = ev.getActionMasked() & MotionEvent.ACTION_MASK;

        if (actionMasked == MotionEvent.ACTION_DOWN) {
            // 记录手指按下时的位置
            mPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            return super.dispatchTouchEvent(ev);
        }

        if (actionMasked == MotionEvent.ACTION_MOVE) {
            // 最关键的地方，忽略MOVE 事件
            // ListView onTouch获取不到MOVE事件所以不会发生滚动处理
            return true;
        }

        // 手指抬起时
        if (actionMasked == MotionEvent.ACTION_UP
                || actionMasked == MotionEvent.ACTION_CANCEL) {
            // 手指按下与抬起都在同一个视图内，交给父控件处理，这是一个点击事件
            if (pointToPosition((int) ev.getX(), (int) ev.getY()) == mPosition) {
                super.dispatchTouchEvent(ev);
            } else {
                // 如果手指已经移出按下时的Item，说明是滚动行为，清理Item pressed状态
                setPressed(false);
                invalidate();
                return true;
            }
        }

        return super.dispatchTouchEvent(ev);
    }
}
