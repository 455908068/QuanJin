package com.xqkj.quanjin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * GridLayoutManager之分割线（线性布局）
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = LinearItemDecoration.class.getSimpleName();

    private Context context;
    private Paint mPaint;
    protected float decorationSize = 0.5f;

    public LinearItemDecoration(Context context) {
        this.context = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(0xffe9e9e9);
        mPaint.setColor(Color.TRANSPARENT);
        decorationSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, decorationSize, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        draw(c, parent);
    }

    private void draw(Canvas c, RecyclerView parent) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (LinearLayoutManager.VERTICAL == layoutManager.getOrientation()) {
            drawHorizontal(c, parent);
        }
        if (LinearLayoutManager.VERTICAL == layoutManager.getOrientation()) {
            drawVertical(c, parent);
        }
    }

    /**
     * 绘制水平分割线
     */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int right = (int) (child.getRight() + params.rightMargin + decorationSize);
            final int bottom = (int) (top + decorationSize);
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制垂直分割线
     */
    public void drawVertical(Canvas canvas, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int top = child.getTop() - params.topMargin;
            final int right = (int) (left + decorationSize);
            final int bottom = (int) (child.getBottom() + params.bottomMargin + decorationSize);
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int orientation = layoutManager.getOrientation();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (LinearLayoutManager.VERTICAL == orientation) {
            if (childAdapterPosition == 0) {
                outRect.set(0, 0, 0, (int) decorationSize);
            } else {
                outRect.set(0, 0, 0, (int) decorationSize);
            }
        }
        if (LinearLayoutManager.HORIZONTAL == orientation) {
            if (childAdapterPosition == adapter.getItemCount() - 1) {
                super.getItemOffsets(outRect, view, parent, state);
            } else {
                outRect.set(0, 0, (int) decorationSize, 0);
            }
        }
    }

    public void setDecorationSize(float decorationSize) {
        this.decorationSize = decorationSize;
        this.decorationSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, decorationSize, context.getResources().getDisplayMetrics());
    }

    public Paint getPaint() {
        return mPaint;
    }
}
