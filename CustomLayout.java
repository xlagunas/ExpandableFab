package net.opentrends.expandableFab;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xlagunas on 9/05/16.
 */
public class CustomLayout extends ViewGroup {

    private boolean isExpanded = false;

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getChildAt(0).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    foldChilds();
                } else {
                    expandChilds();
                }
                isExpanded = !isExpanded;
            }
        });
    }

    private void expandChilds() {
        float percentSpacing = (getHeight())/getChildCount();

        for (int i=1;i<getChildCount();i++){
            View view = getChildAt(i);
            view.animate().y(i*(percentSpacing-view.getHeight()/2));
        }
    }

    private void foldChilds() {
        for (int i=1;i<getChildCount(); i++){
            getChildAt(i).animate().y(getChildAt(0).getY());
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;


        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));

            curWidth = child.getMeasuredWidth()+child.getPaddingLeft()-child.getPaddingRight();
            curHeight = child.getMeasuredHeight();

            if (i!=0){
                curWidth=curWidth/2;
                curHeight=curHeight/2;
            }

            child.layout(childWidth/2 -curWidth/2, childHeight-curHeight, childWidth/2 +curWidth/2, childBottom);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
