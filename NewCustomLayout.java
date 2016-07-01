package net.opentrends.expandableFab;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by xlagunas on 1/07/16.
 */
public class NewCustomLayout extends RelativeLayout implements View.OnClickListener {

    private View anchor;
    private boolean initialized = false;
    private boolean isExpanded = true;

    private final static double MAXIMUM_ANGLE = Math.PI / 2;
    private int computedWidth, computedHeight;
    private double angleOffset = 0;

    private Rect[] computedPositions;

    private double angle;
    //TODO PICK FROM RESOURCES
    private int radius;

    public NewCustomLayout(Context context) {
        super(context);
    }

    public NewCustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewCustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public NewCustomLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getChildAt(0).setOnClickListener(this);
    }

    private void initMeasurements() {
        angle = MAXIMUM_ANGLE / (getChildCount() -1);
        computedPositions = new Rect[getChildCount()-1];
        //TODO CHANGE LATER
        radius = getMeasuredWidth() - anchor.getLeft() - anchor.getWidth() /2;
        initialized = true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutAnchor();

        for (int i=1;i<getChildCount();i++){
            layoutSubElements(i);
        }
    }

    private void layoutAnchor() {
        anchor = getChildAt(0);
        computedWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        computedHeight = getMeasuredHeight();

        anchor.measure(MeasureSpec.makeMeasureSpec(computedWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(computedHeight, MeasureSpec.AT_MOST));

        if (anchor.getVisibility() != GONE) {
            RelativeLayout.LayoutParams st =
                    (RelativeLayout.LayoutParams) anchor.getLayoutParams();

            anchor.layout(st.leftMargin,
                    getMeasuredHeight() - st.topMargin - st.height -st.bottomMargin,
                    st.width + st.leftMargin -st.rightMargin,
                    getMeasuredHeight() - st.bottomMargin - st.topMargin);
        }
    }

    private void layoutSubElements(int childrenPosition) {
        View view = getChildAt(childrenPosition);

        if (!initialized){
            initMeasurements();
        }

        //MEASURE THE CURRENT VIEW
        view.measure(MeasureSpec.makeMeasureSpec(computedWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(computedHeight, MeasureSpec.AT_MOST));

        RelativeLayout.LayoutParams st =
                (RelativeLayout.LayoutParams) view.getLayoutParams();

        //calculated position of the element
        double xPos = radius * Math.cos((childrenPosition-1)*angle + angleOffset);
        double yPos = radius * Math.sin((childrenPosition-1)*angle + angleOffset);

        //new (0,0) coordinates
        double anchorXPos = anchor.getX() + anchor.getWidth() / 2;
        double anchorYPos = anchor.getY() + anchor.getHeight() / 2;

        int left    =    (int) (anchorXPos + xPos - st.width/2);
        int top     =    (int) (anchorYPos -yPos -st.height/2);
        int right   =    (int) (xPos + anchorXPos +st.width / 2);
        int bottom  =    (int) (anchorYPos -yPos + st.height/2);

        Rect rect = new Rect(left, top, right, bottom);
        computedPositions[childrenPosition-1] = rect;

        view.layout(rect.left, rect.top, rect.right, rect.bottom);

    }

    @Override
    public void onClick(View v) {
        if (isExpanded){
            collapse();
        } else {
            expand();
        }

        isExpanded = !isExpanded;
    }

    private void expand() {
        anchor.animate().rotation(45*4).setDuration(200);

        for (int i = getChildCount() - 1; i >= 1; i--) {
            final View child = getChildAt(i);
            child.animate().x(computedPositions[i-1].left).y(computedPositions[i-1].top).setStartDelay(i * 100).withStartAction(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(VISIBLE);
                }
            });
        }
    }

    private void collapse() {
        anchor.animate().rotation(-45*3).setDuration(200);

        for (int i = getChildCount() - 1; i >= 1; i--) {
            final View child = getChildAt(i);
            child.animate().y(anchor.getY() + child.getHeight() / 2).x(anchor.getX() + child.getWidth() / 2).setStartDelay(i * 100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(INVISIBLE);
                }
            });
        }
    }
}
