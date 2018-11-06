package com.liruya.swiperecyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeViewHolder extends RecyclerView.ViewHolder
{
    public SwipeViewHolder( @NonNull View itemView )
    {
        super( itemView );
    }

    protected final boolean isSwipeLayout()
    {
        return (itemView instanceof SwipeLayout);
    }

    protected final View getContentView()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getContentView();
        }
        return null;
    }

    protected final View getActionView()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getActionView();
        }
        return null;
    }

    protected final int getActionViewWidth()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getActionViewWidth();
        }
        return 0;
    }

    protected final int getSwipeMode()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getSwipeMode();
        }
        return -1;
    }

    protected final int getSwipeDirection()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getSwipeDirection();
        }
        return -1;
    }
}
