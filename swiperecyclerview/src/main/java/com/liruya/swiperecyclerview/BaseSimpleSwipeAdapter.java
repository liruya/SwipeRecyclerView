package com.liruya.swiperecyclerview;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;

public abstract class BaseSimpleSwipeAdapter<T, VH extends SwipeViewHolder> extends BaseSwipeAdapter<VH>
{
    private List<T> mList;

    public BaseSimpleSwipeAdapter( @NonNull Context context, List< T > list )
    {
        super( context );
        mList = list;
    }

    protected T getItem( int position )
    {
        if ( position >= 0 && position < getItemCount() )
        {
            return mList.get( position );
        }
        return null;
    }

    @Override
    public int getItemCount()
    {
        return mList == null ? 0 : mList.size();
    }
}
