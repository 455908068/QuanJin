package com.xqkj.quanjin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

/**
 * StaggeredGridLayoutManager之分割线（瀑布流）
 */
public class StaggeredItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = GridItemDecoration.class.getSimpleName();

    private Paint mPaint;
    private float decorationSize = 0.5f;

    public StaggeredItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffe9e9e9);
        decorationSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, decorationSize, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
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
        // 如果是最后一行，则不需要绘制底部
        if (isLastRaw(view, parent)) {
            outRect.set(0, 0, (int) decorationSize, 0);
            return;
        }
        // 如果是最后一列，则不需要绘制右边
        if (isLastColum(view, parent)) {
            outRect.set(0, 0, 0, (int) decorationSize);
            return;
        }
        outRect.set(0, 0, (int) decorationSize, (int) decorationSize);
    }

    private boolean isLastColum(View view, RecyclerView parent) {
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        int orientation = layoutManager.getOrientation();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int childCount = parent.getChildCount();
        int firstPositionLastRow = childCount - childCount % spanCount;

        //1.最后一列不绘制右边
        if (StaggeredGridLayoutManager.VERTICAL == orientation && (childAdapterPosition + 1) % spanCount == 0)
            return true;
        //2.最后一列不绘制右边
        return StaggeredGridLayoutManager.HORIZONTAL == orientation && childAdapterPosition >= firstPositionLastRow;

    }

    private boolean isLastRaw(View view, RecyclerView parent) {
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        int orientation = layoutManager.getOrientation();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int childCount = parent.getChildCount();
        int firstPositionLastRow = childCount - childCount % spanCount;

        //1.最后一行不需要绘制底部
        if (StaggeredGridLayoutManager.VERTICAL == orientation && childAdapterPosition >= firstPositionLastRow)
            return true;
        //2.最后一行不需要绘制底部
        return StaggeredGridLayoutManager.HORIZONTAL == orientation && (childAdapterPosition + 1) % spanCount == 0;

    }
}
