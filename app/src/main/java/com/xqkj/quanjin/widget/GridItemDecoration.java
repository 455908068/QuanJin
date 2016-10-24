package com.xqkj.quanjin.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import com.xqkj.quanjin.helper.LogHelper;


/**
 * GridLayoutManager之分割线（网格布局）
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = GridItemDecoration.class.getSimpleName();

    private Paint mPaint;
    private float decorationSize = 10f;

    public GridItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
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
        for (int i = 0; i < childCount-2; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int right = (int) (child.getRight() + params.rightMargin + decorationSize);
            final int bottom = (int) (top + decorationSize);
            c.drawRect(left, top, right, bottom, mPaint);
            if (i == 0) {
                LogHelper.e(TAG, "parent.getLeft=" + parent.getLeft());
                LogHelper.e(TAG, "parent.getPaddingLeft=" + parent.getPaddingLeft());
                LogHelper.e(TAG, "getLeft=" + child.getLeft());
                LogHelper.e(TAG, "getPaddingLeft=" + child.getPaddingLeft());
                LogHelper.e(TAG, "params.leftMargin=" + params.leftMargin);
                LogHelper.e(TAG, "getRight=" + child.getRight());
                LogHelper.e(TAG, "getPaddingRight=" + child.getPaddingRight());
                LogHelper.e(TAG, "params.rightMargin=" + params.rightMargin);
            }
        }
    }

    /**
     * 绘制垂直分割线
     */
    public void drawVertical(Canvas canvas, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount-1; i++) {
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

/*    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
*//*        // 如果是最后一行，则不需要绘制底部
        if (isLastRaw(view, parent)) {
            outRect.set(0, 0, (int) decorationSize, 0);
            return;
        }
        // 如果是最后一列，则不需要绘制右边
        if (isLastColum(view, parent)) {
            outRect.set(0, 0, 0, (int) decorationSize);
            return;
        }*//*
//        outRect.set(0, 0, (int) decorationSize, (int) decorationSize);
    }*/

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
        {
            outRect.set(0, 0, (int) decorationSize, 0);
        } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
        {
            outRect.set(0, 0, 0, (int) decorationSize);
        } else {
            outRect.set(0, 0, (int) decorationSize, (int) decorationSize);
        }
    }

    private boolean isLastColum(int itemPosition, RecyclerView parent) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        int orientation = layoutManager.getOrientation();
        int childCount = parent.getAdapter().getItemCount();
        int tail = childCount % spanCount;
        int firstPositionLastRow = childCount - childCount % spanCount;

        // 如果是最后一列，则不需要绘制右边
        return GridLayoutManager.VERTICAL == orientation && (itemPosition + 1) % spanCount == 0 || GridLayoutManager.HORIZONTAL == orientation && itemPosition >= firstPositionLastRow;

    }

    private boolean isLastRaw(int itemPosition, RecyclerView parent) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        int orientation = layoutManager.getOrientation();
        int childCount = parent.getAdapter().getItemCount() - 1;
        int tail = childCount % spanCount;
        int firstPositionLastRow = tail != 0 ? childCount - tail : childCount - spanCount;
        LogHelper.e(TAG, "itemPosition=" + itemPosition);
        LogHelper.e(TAG, "firstPositionLastRow=" + firstPositionLastRow);
        LogHelper.e(TAG, "childCount=" + childCount);
//        LogHelper.e(TAG, "spanCount=" + spanCount);

        // 如果是最后一行，则不需要绘制底部
        if (GridLayoutManager.VERTICAL == orientation && itemPosition >= firstPositionLastRow)
            return true;
        return GridLayoutManager.HORIZONTAL == orientation && (itemPosition + 1) % spanCount == 0;

    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
