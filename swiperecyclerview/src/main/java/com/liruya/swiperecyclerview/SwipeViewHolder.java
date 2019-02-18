package com.liruya.swiperecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SwipeViewHolder extends RecyclerView.ViewHolder
{
    public SwipeViewHolder( @NonNull SwipeLayout itemView)
    {
        super( itemView );
    }

    protected final View getContentView()
    {
        return ( (SwipeLayout) itemView ).getContentView();
    }

    protected final View getActionView()
    {
        return ( (SwipeLayout) itemView ).getActionView();
    }

    protected final int getActionViewWidth()
    {
        return ( (SwipeLayout) itemView ).getActionViewWidth();
    }

    protected final int getSwipeMode()
    {
        return ( (SwipeLayout) itemView ).getSwipeMode();
    }

    protected final int getSwipeDirection()
    {
        return ( (SwipeLayout) itemView ).getSwipeDirection();
    }
}
