package com.liruya.swiperecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeViewHolder extends RecyclerView.ViewHolder
{
    private int mViewType;

    public SwipeViewHolder( @NonNull View itemView )
    {
        super( itemView );
    }

    public SwipeViewHolder( @NonNull View itemView, int viewType )
    {
        super( itemView );
        mViewType = viewType;
    }

    public static SwipeViewHolder create( @NonNull Context context, @LayoutRes int layoutid, @NonNull ViewGroup parent )
    {
        return create( context, layoutid, parent, 0 );
    }

    public static SwipeViewHolder create( @NonNull Context context, @LayoutRes int layoutid, @NonNull ViewGroup parent, int viewType )
    {
        View rootView = LayoutInflater.from( context ).inflate( layoutid, parent, false );
        if ( rootView instanceof SwipeLayout )
        {
            return new SwipeViewHolder( rootView, viewType );
        }
        return null;
    }

    public boolean isSwipeLayout()
    {
        return (itemView instanceof SwipeLayout);
    }

    public View getContentView()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getContentView();
        }
        return null;
    }

    public View getActionView()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getActionView();
        }
        return null;
    }

    public int getActionViewWidth()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getActionViewWidth();
        }
        return 0;
    }

    public int getSwipeMode()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getSwipeMode();
        }
        return -1;
    }

    public int getSwipeDirection()
    {
        if ( itemView instanceof SwipeLayout )
        {
            return ( (SwipeLayout) itemView ).getSwipeDirection();
        }
        return -1;
    }
}
