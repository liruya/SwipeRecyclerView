package com.liruya.swiperecyclerview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SimpleSwipeAdapter<T> extends RecyclerView.Adapter<SwipeViewHolder>
{
    private Context mContext;
    private @LayoutRes int mLayoutResID;
    private List<T> mList;

    public SimpleSwipeAdapter( @NonNull Context context, @LayoutRes int layoutResID, List< T > list )
    {
        mContext = context;
        mLayoutResID = layoutResID;
        mList = list;
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
    {
        SwipeViewHolder holder = SwipeViewHolder.create( mContext, mLayoutResID, parent );
        return holder;
    }

    @Override
    public int getItemCount()
    {
        return mList == null ? 0 : mList.size();
    }

    public T getItem( int position )
    {
        if ( position >= 0 && position < getItemCount() )
        {
            return mList.get( position );
        }
        return null;
    }
}
